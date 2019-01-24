package evento;

import interfazgrafica.InterfazGrafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBotonesVender implements ActionListener {
    private InterfazGrafica interfazGrafica;

    public GestionBotonesVender(InterfazGrafica interfaz){
        this.interfazGrafica = interfaz;
    }

    public void actionPerformed(ActionEvent evento){
        if(evento.getSource().equals(this.interfazGrafica.getPanelVender().getBotonVender())){
            this.interfazGrafica.getPanelVender().vender();
            this.interfazGrafica.getPanelInfo().setVisible(true);
            this.interfazGrafica.getPanelTexto().setVisible(true);
            this.interfazGrafica.getPanelMenu().setVisible(true);
            this.interfazGrafica.getPanelTablero().setVisible(true);
            this.interfazGrafica.getPanelBotones().setVisible(true);
            this.interfazGrafica.getPanelJugadores().setVisible(true);
            this.interfazGrafica.getPanelVender().setVisible(false);
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelVender().getBotonSalirVender())){
            this.interfazGrafica.getPanelInfo().setVisible(true);
            this.interfazGrafica.getPanelTexto().setVisible(true);
            this.interfazGrafica.getPanelMenu().setVisible(true);
            this.interfazGrafica.getPanelTablero().setVisible(true);
            this.interfazGrafica.getPanelBotones().setVisible(true);
            this.interfazGrafica.getPanelJugadores().setVisible(true);
            this.interfazGrafica.getPanelVender().setVisible(false);
        }
    }

}