package evento;

import interfazgrafica.InterfazGrafica;
import interfazgrafica.PanelBotones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBotones implements ActionListener{

    private PanelBotones botones;

    public GestionBotones(PanelBotones botones){
        this.botones = botones;
    }

    @Override
    public void actionPerformed(ActionEvent evento){
        if(evento.getSource().equals(botones.getBotonSalir())){
            int confirm = JOptionPane.showConfirmDialog(this.botones.getInterfaz(), "¿Desea acabar la partida?", "Salir", JOptionPane.YES_NO_OPTION);
            if(confirm == 0)
                System.exit(0);
        }else if(evento.getSource().equals(botones.getBotonSalirCarcel()))
        if(this.botones.getInterfaz().getJuego().getJugadorActual().getAvatar().getEncarcelado()>0) {
            if(this.botones.getInterfaz().getJuego().getJugadorActual().getDinero()>=this.botones.getInterfaz().getValor().getDineroSalirCarcel()) {
                try {
                    JOptionPane.showMessageDialog(this.botones.getInterfaz(), "Pagas " + this.botones.getInterfaz().getValor().getDineroSalirCarcel() + " y sales de la carcel");
                    this.botones.getInterfaz().getJuego().getOperacion().salirCarcel(this.botones.getInterfaz().getJuego().getJugadorActual());
                } catch (Exception ex) {
                    this.botones.getInterfaz().getPanelTexto().addTexto(ex.getMessage());
                }
            }
            else{
                try {
                    JOptionPane.showMessageDialog(this.botones.getInterfaz(), "No tienes dinero suficiente, Sigues en la carcel");
                } catch (Exception ex) {
                    this.botones.getInterfaz().getPanelTexto().addTexto(ex.getMessage());
                }
            }
        }else{
            try {
                JOptionPane.showMessageDialog(this.botones.getInterfaz(), "No puedes salir de la carcel si no estás encarcelado");
            } catch (Exception ex) {
                this.botones.getInterfaz().getPanelTexto().addTexto(ex.getMessage());
            }
        }
    }

}
