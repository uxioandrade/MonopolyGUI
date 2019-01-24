package interfazgrafica;

import evento.GestionBotonesListaHipotecar;
import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.ExcepcionMonopoly;

import javax.swing.*;
import java.awt.*;
import java.net.JarURLConnection;
import java.util.ArrayList;

public class PanelHipotecar extends JPanel{

    private InterfazGrafica interfaz;
    private DefaultListModel<String> modeloLista;
    private DefaultListModel<String> modeloListaDeshipotecar;
    private Jugador jugador;
    private JButton botonSalirHipotecar;
    private JButton botonHipotecarLista;
    private JButton botonDeshipotecar;
    private JList jListaHipotecados;
    private JList jListaDeshipotecar;
    private ArrayList<Propiedades> propiedadesHipotecables;
    private ArrayList<Propiedades> propiedadesHipotecadas;

    public PanelHipotecar(InterfazGrafica interfaz, Jugador jugador){
        this.interfaz = interfaz;
        this.jugador = jugador;
        this.botonHipotecarLista = new JButton("Hipotecar");
        this.botonSalirHipotecar = new JButton("Salir Hipotecar");
        this.botonDeshipotecar = new JButton("Deshipotecar");
        this.setLayout(new GridLayout(5, 1));
        this.propiedadesHipotecables = new ArrayList();
        this.propiedadesHipotecadas = new ArrayList<>();
        this.modeloLista = new DefaultListModel();
        this.modeloListaDeshipotecar = new DefaultListModel<>();
        if(!modeloLista.isEmpty())
            this.jListaHipotecados.setModel(modeloLista);
        else
            this.jListaHipotecados = new JList();

        this.jListaDeshipotecar = new JList();
        this.setUp();
        addEventHandlers();
    }

    public JButton getBotonHipotecarLista(){
        return this.botonHipotecarLista;
    }

    public JButton getBotonSalirHipotecar(){
        return this.botonSalirHipotecar;
    }

    public JButton getBotonDeshipotecar(){
        return this.botonDeshipotecar;
    }

    private void setUp(){
        this.add(botonHipotecarLista);
        this.add(jListaHipotecados);
        this.add(botonDeshipotecar);
        this.add(jListaDeshipotecar);
        this.add(botonSalirHipotecar);
    }

    public void hipotecarElemento(){
        int pos = jListaHipotecados.getSelectedIndex();
        if(pos > -1) {
            int respuesta = JOptionPane.showConfirmDialog(this, "Desea hipotecar la casilla " + this.modeloLista.getElementAt(pos));
            if (respuesta == 0) {
                try {
                    this.interfaz.getJuego().hipotecar(this.modeloLista.getElementAt(pos));
                    this.modeloLista.remove(pos);
                } catch (ExcepcionMonopoly ex) {
                    this.interfaz.getPanelTexto().addTexto(ex.getMensaje());
                }
            }
        }
    }

    public void desHipotecarElemento(){
        int pos = jListaDeshipotecar.getSelectedIndex();
        if(pos > -1) {
            try {
                this.interfaz.getJuego().deshipotecar(this.modeloListaDeshipotecar.getElementAt(pos));
                this.modeloListaDeshipotecar.remove(pos);
            } catch (ExcepcionMonopoly ex) {
                this.interfaz.getPanelTexto().addTexto(ex.getMensaje());
            }
        }
    }

    private void addEventHandlers(){
        this.botonHipotecarLista.addActionListener(new GestionBotonesListaHipotecar(this.interfaz,this));
        this.botonDeshipotecar.addActionListener(new GestionBotonesListaHipotecar(this.interfaz,this));
        this.botonSalirHipotecar.addActionListener(new GestionBotonesListaHipotecar(this.interfaz,this));
    }

    public void resetHipotecar(){
        //this.interfaz.getPanelTexto().setVisible(false);
        this.interfaz.getPanelMenu().setVisible(false);
        this.interfaz.getPanelBotones().setVisible(false);
        this.interfaz.getPanelJugadores().setVisible(false);
        this.interfaz.getPanelInfo().setVisible(false);
        this.interfaz.getPanelTablero().setVisible(false);
        this.jugador = this.interfaz.getJuego().getJugadorActual();
        this.propiedadesHipotecables = new ArrayList();
        this.propiedadesHipotecadas = new ArrayList();
        this.modeloLista = new DefaultListModel();
        this.modeloListaDeshipotecar = new DefaultListModel<>();
        for(Propiedades c: this.jugador.getPropiedades()){
            if(!c.getHipotecado()){
                propiedadesHipotecables.add(c);
                modeloLista.addElement(c.getNombre());
            }else{
                this.propiedadesHipotecadas.add(c);
                modeloListaDeshipotecar.addElement(c.getNombre());
            }
        }
        jListaDeshipotecar.setModel(modeloListaDeshipotecar);
        jListaHipotecados.setModel(modeloLista);
    }

}
