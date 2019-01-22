package evento;

import interfazgrafica.InterfazGrafica;
import monopoly.contenido.Jugador;
import monopoly.plataforma.Juego;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionJugadores implements ActionListener {

    private InterfazGrafica interfazGrafica;

    public GestionJugadores(InterfazGrafica interfazGrafica){
        this.interfazGrafica = interfazGrafica;
    }

    @Override
    public void actionPerformed(ActionEvent evento){
        try {
            JOptionPane.showMessageDialog(this.interfazGrafica, this.interfazGrafica.getJuego().getTablero().getJugadores().get(evento.getActionCommand()).toString());
        }catch (Exception ex){
            this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
        }
    }

}
