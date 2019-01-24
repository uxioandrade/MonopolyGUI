package interfazgrafica;

import evento.GestionBotonesListaEdificar;
import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.ExcepcionMonopoly;

import javax.swing.*;
import java.util.ArrayList;

public class PanelEdificar extends JPanel {

    private InterfazGrafica interfaz;
    private DefaultListModel<String> modeloLista;
    private JButton botonSalirEdificar;
    private JButton botonEdificarLista;
    private JList jListaEdificaciones;

    public PanelEdificar(InterfazGrafica interfaz){
        this.interfaz = interfaz;
        this.botonSalirEdificar = new JButton("Salir");
        this.botonEdificarLista = new JButton("Edificar");
        modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Casa");
        modeloLista.addElement("Hotel");
        modeloLista.addElement("Piscina");
        modeloLista.addElement("Pista de deporte");
        jListaEdificaciones = new JList();
        jListaEdificaciones.setModel(modeloLista);
        setUp();
        addEventHandlers();
    }

    public JButton getBotonSalirEdificar(){
        return this.botonSalirEdificar;
    }

    private void setUp(){
        this.add(botonEdificarLista);
        this.add(botonSalirEdificar);
        this.add(jListaEdificaciones);
    }

    public JButton getBotonEdificarLista(){
        return this.botonEdificarLista;
    }

    public void edificarTipo(){
        int pos = jListaEdificaciones.getSelectedIndex();
        if(pos > -1) {
            try {
                this.interfaz.getJuego().edificar(modeloLista.get(pos));
            } catch (ExcepcionMonopoly ex) {
                this.interfaz.getPanelTexto().addTexto(ex.getMensaje());
            }
        }
    }

    private void addEventHandlers(){
        this.botonEdificarLista.addActionListener(new GestionBotonesListaEdificar(this.interfaz));
        this.botonSalirEdificar.addActionListener(new GestionBotonesListaEdificar(this.interfaz));
    }
}
