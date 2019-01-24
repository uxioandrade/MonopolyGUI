package interfazgrafica;

import evento.GestionBotonesTratos;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.ExcepcionMonopoly;
import monopoly.plataforma.Trato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PanelTratos extends JPanel {

    private InterfazGrafica interfaz;
    private JButton botonSalirTratos;
    private JButton botonAceptarTrato;
    private JButton botonEliminarTrato;
    private JButton botonVerTratos;
    private JList jListaTratos;
    private ButtonGroup tipoTratos;
    private DefaultListModel<String> modeloLista;
    private DefaultListModel<String> modeloListaPropuestos;
    private JList jListaTratosPropuestos;
    private ArrayList<JRadioButton> botonesTipoTrato;
    private ArrayList<JTextField> textFields;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JButton botonProponerTrato;
    private ArrayList<Trato> tratosRecibidos;
    private ArrayList<Trato> tratosPropuestos;

    public PanelTratos(InterfazGrafica interfaz){
        this.interfaz = interfaz;
        this.setLayout(new GridBagLayout());
        this.modeloLista = new DefaultListModel();
        this.modeloLista.addElement("No hay tratos pendientes");
        this.modeloListaPropuestos = new DefaultListModel<>();
        this.modeloListaPropuestos.addElement("No hay tratos propuestos");
        this.jListaTratos = new JList();
        this.jListaTratosPropuestos = new JList();
        if(!modeloLista.isEmpty())
            this.jListaTratos.setModel(modeloLista);
        else
            this.jListaTratos = new JList();

        if(!modeloListaPropuestos.isEmpty())
            this.jListaTratosPropuestos.setModel(modeloListaPropuestos);
        else
            this.jListaTratosPropuestos = new JList();

        this.tratosRecibidos = new ArrayList<>();
        this.tratosPropuestos = new ArrayList<>();

        this.setUp();
        this.addEventHandlers();
    }

    public JButton getBotonSalirTratos(){
        return this.botonSalirTratos;
    }

    public ArrayList<JRadioButton> getBotonesTipoTrato() {
        return botonesTipoTrato;
    }

    public JButton getBotonAceptarTrato() {
        return botonAceptarTrato;
    }

    public JButton getBotonProponerTrato() {
        return botonProponerTrato;
    }

    public JButton getBotonEliminarTrato() {
        return botonEliminarTrato;
    }

    public JButton getBotonVerTratos() {
        return botonVerTratos;
    }

    public ButtonGroup getTipoTratos(){
        return tipoTratos;
    }

    public ArrayList<JTextField> getTextFields(){
        return this.textFields;
    }

    private void setUp(){

        this.tipoTratos = new ButtonGroup();
        this.botonesTipoTrato = new ArrayList<>();
        this.botonesTipoTrato.add(new JRadioButton("Propiedad por Propiedad",false));
        this.botonesTipoTrato.add(new JRadioButton("Propiedad por Dinero",false));
        this.botonesTipoTrato.add(new JRadioButton("Dinero por Propiedad",false));
        this.botonesTipoTrato.add(new JRadioButton("Propiedad por Propiedad y Dinero",false));
        this.botonesTipoTrato.add(new JRadioButton("Propiedad y Dinero por Propiedad",false));
        this.botonesTipoTrato.add(new JRadioButton("Propiedad por Propiedad y No Alquiler",false));
        botonesTipoTrato.get(0).setSelected(true);
        for(JRadioButton b: botonesTipoTrato) {
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    b.setSelected(true);
                }
            });
        }

        GridBagConstraints c = new GridBagConstraints();

        int aux = 0;
        for(JRadioButton b: botonesTipoTrato) {
            c.gridx = 0;
            c.gridy = aux;
            this.add(b,c);
            aux += 20;
        }

        tipoTratos = new ButtonGroup();
        for(JRadioButton b: botonesTipoTrato) {
            tipoTratos.add(b);
        }

        label1 = new JLabel("Elemento 1:");
        label2 = new JLabel("Elemento 2:");
        label3 = new JLabel("Elemento 3:");
        label4 = new JLabel("Jugador a proponer:");

        textFields = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            textFields.add(new JTextField());
            this.textFields.get(i).setPreferredSize(new Dimension(100,20));
        }
        c.gridy += 20;
        this.add(label1,c);
        c.gridy += 20;
        this.add(textFields.get(0),c);
        c.gridy += 20;
        this.add(label2,c);
        c.gridy += 20;
        this.add(textFields.get(1),c);
        c.gridy += 20;
        this.add(label3,c);
        c.gridy += 20;
        this.add(textFields.get(2),c);
        c.gridy += 20;
        this.add(label4,c);
        c.gridy += 20;
        this.add(textFields.get(3),c);


        c.gridx = 0;
        c.gridy += 20;
        this.botonProponerTrato = new JButton("Proponer Trato");
        this.add(this.botonProponerTrato,c);
        c.gridx = 0;
        c.gridy = 1500;
        this.botonSalirTratos = new JButton("Salir");
        this.add(this.botonSalirTratos,c);
        c.gridx = 500;
        c.gridy = 140;
        this.botonAceptarTrato = new JButton("Aceptar");
        this.add(this.botonAceptarTrato,c);
        c.gridx = 500;
        c.gridy = 180;
        this.botonVerTratos = new JButton("Tratos Pendientes");
        this.add(this.botonVerTratos,c);

        c.gridx = 500;
        c.gridy = 0;
        this.add(new JLabel("Selecciona el trato a gestionar:"),c);

        c.gridx = 500;
        c.gridy = 1000;
        this.add(jListaTratosPropuestos,c);

        c.gridx = 500;
        c.gridy = 1200;
        this.botonEliminarTrato = new JButton("Eliminar");
        this.add(this.botonEliminarTrato,c);

        c.gridx = 500;
        c.gridy = 1;
        this.add(jListaTratos,c);
    }

    public void eliminarTrato(){
        int pos = jListaTratosPropuestos.getSelectedIndex();
        if(pos > -1){
            try{
                if(modeloListaPropuestos.get(pos).contains("No"))
                    return;
                this.interfaz.getJuego().borrarTrato(modeloListaPropuestos.get(pos));
                this.modeloListaPropuestos.remove(pos);
            }catch(Exception ex){
                this.interfaz.getPanelTexto().addTexto(ex.getMessage());
            }
        }
    }

    public void aceptarTrato(){
        int pos = jListaTratos.getSelectedIndex();
        if(pos > -1){
            if(modeloLista.get(pos).contains("No"))
                return;
            try{
                this.interfaz.getJuego().aceptarTrato(modeloLista.get(pos));
                this.modeloLista.remove(pos);
            }catch(Exception ex){
                this.interfaz.getPanelTexto().addTexto(ex.getMessage());
            }
        }
    }

    public void resetTratos(){
        //this.interfaz.getPanelTexto().setVisible(false);
        this.interfaz.getPanelMenu().setVisible(false);
        this.interfaz.getPanelBotones().setVisible(false);
        this.interfaz.getPanelJugadores().setVisible(false);
        this.interfaz.getPanelInfo().setVisible(false);
        this.interfaz.getPanelTablero().setVisible(false);
        this.tratosRecibidos = this.interfaz.getJuego().getJugadorActual().getTratosPendientes();
        this.tratosPropuestos = this.interfaz.getJuego().getJugadorActual().getTratosPropuestos();
        modeloLista = new DefaultListModel<>();
        modeloListaPropuestos = new DefaultListModel<>();

        for(Trato t: this.interfaz.getJuego().getJugadorActual().getTratosPropuestos()){
            modeloListaPropuestos.addElement("trato" + t.getId());
        }
        if(!modeloListaPropuestos.isEmpty())
            jListaTratosPropuestos.setModel(modeloListaPropuestos);
        else{
            this.modeloListaPropuestos.addElement("No hay tratos propuestos");
            jListaTratosPropuestos.setModel(modeloListaPropuestos);
        }

        for(Trato t: this.interfaz.getJuego().getJugadorActual().getTratosPendientes()){
            modeloLista.addElement("trato" + t.getId());
        }
        if(!modeloLista.isEmpty())
            jListaTratos.setModel(modeloLista);
        else{
            this.modeloLista.addElement("No hay tratos pendientes");
            jListaTratos.setModel(modeloLista);
        }
    }

    private void addEventHandlers(){
        this.botonVerTratos.addActionListener(new GestionBotonesTratos(this.interfaz));
        this.botonSalirTratos.addActionListener(new GestionBotonesTratos(this.interfaz));
        this.botonProponerTrato.addActionListener(new GestionBotonesTratos(this.interfaz));
        this.botonAceptarTrato.addActionListener(new GestionBotonesTratos(this.interfaz));
        this.botonEliminarTrato.addActionListener(new GestionBotonesTratos(this.interfaz));
    }

}
