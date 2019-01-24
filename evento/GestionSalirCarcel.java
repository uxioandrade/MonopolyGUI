package evento;

import interfazgrafica.InterfazGrafica;
import interfazgrafica.PanelBotones;
import monopoly.plataforma.Valor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionSalirCarcel implements ActionListener {
    private InterfazGrafica interfazGrafica;

    public GestionSalirCarcel(InterfazGrafica interfazGrafica){
        this.interfazGrafica = interfazGrafica;
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if(this.interfazGrafica.getJuego().getJugadorActual().getAvatar().getEncarcelado()>0) {
            if(this.interfazGrafica.getJuego().getJugadorActual().getDinero()>=Valor.getDineroSalirCarcel()) {
                try {
                    JOptionPane.showMessageDialog(this.interfazGrafica, "Pagas " + Valor.getDineroSalirCarcel() + " y sales de la carcel");
                    this.interfazGrafica.getJuego().getOperacion().salirCarcel(this.interfazGrafica.getJuego().getJugadorActual());
                } catch (Exception ex) {
                    this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
                }
            }
            else{
                try {
                    JOptionPane.showMessageDialog(this.interfazGrafica, "No tienes dinero suficiente, Sigues en la carcel");
                } catch (Exception ex) {
                    this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
                }
            }
        }else{
            try {
                JOptionPane.showMessageDialog(this.interfazGrafica, "No puedes salir de la carcel si no estás encarcelado");
            } catch (Exception ex) {
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }
    }
}
