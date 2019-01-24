package interfazgrafica;

import evento.GestionBotonesListaEdificar;
import evento.GestionBotonesVender;
import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.excepciones.ExcepcionMonopoly;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;

import javax.swing.*;
import java.awt.*;

public class PanelVender extends JPanel {

    private InterfazGrafica interfaz;
    private Choice elegirEdificio;
    private JButton botonSalirVender;
    private JButton botonVender;
    private Choice propiedades;
    private JTextField numero;
    private Jugador jug;

    public PanelVender(InterfazGrafica interfaz, Jugador jugador){
        this.interfaz = interfaz;
        this.botonSalirVender = new JButton("Salir");
        this.botonVender = new JButton("Vender");
        this.numero=new JTextField();
        this.elegirEdificio = new Choice();
        elegirEdificio.addItem("Casa");
        elegirEdificio.addItem("Hotel");
        elegirEdificio.addItem("Piscina");
        elegirEdificio.addItem("Pista de deporte");
        jug=jugador;
        propiedades = new Choice();
        if (jugador!=null) {
            for (Propiedades p : jugador.getPropiedades()) {
                if (p instanceof Solar) {
                    propiedades.addItem(p.getNombre());
                }
            }
        }
        setUp();
        addEventHandlers();
    }

    public JButton getBotonSalirVender(){
        return this.botonSalirVender;
    }

    private void setUp(){
        this.setLayout(new GridLayout(2, 3));
        this.add(numero);
        this.add(elegirEdificio);
        this.add(propiedades);
        this.add(botonVender);
        this.add(botonSalirVender);
    }

    public JButton getBotonVender(){
        return this.botonVender;
    }
    public void reset(Jugador jugador){
        this.jug=jugador;
        propiedades.removeAll();
        if (jug!=null) {
            for (Propiedades p : jug.getPropiedades()) {
                if (p instanceof Solar) {
                    propiedades.addItem(p.getNombre());
                }
            }
        }
    }

    public void vender(){
        int num = Integer.valueOf(this.numero.getText());
        if(num > 0) {
            try {
                this.interfaz.getJuego().getOperacion().venderConstrucciones(jug,this.interfaz.getJuego().getTablero().getCasillas().get(this.propiedades.getSelectedItem()),this.elegirEdificio.getSelectedItem(),num);

            }catch (ExcepcionRestriccionEdificar e){
                this.interfaz.getPanelTexto().addTexto(e.getMensaje());
            }
        }
        else this.interfaz.getPanelTexto().addTexto("No se vendio ningún edificio :(");
    }

    private void addEventHandlers(){
        this.botonVender.addActionListener(new GestionBotonesVender(this.interfaz));
        this.botonSalirVender.addActionListener(new GestionBotonesVender(this.interfaz));
    }

}
