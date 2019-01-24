package evento;

import interfazgrafica.InterfazGrafica;
import monopoly.excepciones.ExcepcionMonopoly;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBotonesTratos implements ActionListener {

    private InterfazGrafica interfaz;

    public GestionBotonesTratos(InterfazGrafica interfaz){
        this.interfaz = interfaz;
    }

    @Override
    public void actionPerformed(ActionEvent evento){

        if(evento.getSource().equals(this.interfaz.getPanelTratos().getBotonEliminarTrato())){
            this.interfaz.getPanelTratos().eliminarTrato();
        }else if(evento.getSource().equals(this.interfaz.getPanelTratos().getBotonSalirTratos())){
            this.interfaz.getPanelInfo().setVisible(true);
            this.interfaz.getPanelTexto().setVisible(true);
            this.interfaz.getPanelMenu().setVisible(true);
            this.interfaz.getPanelTablero().setVisible(true);
            this.interfaz.getPanelBotones().setVisible(true);
            this.interfaz.getPanelJugadores().setVisible(true);
            this.interfaz.getPanelTratos().setVisible(false);
        }else if(evento.getSource().equals(this.interfaz.getPanelTratos().getBotonAceptarTrato())){
            this.interfaz.getPanelTratos().aceptarTrato();
        }else if(evento.getSource().equals(this.interfaz.getPanelTratos().getBotonProponerTrato())){
            int caso = 1;
            for(int i = 0;i<this.interfaz.getPanelTratos().getBotonesTipoTrato().size();i++){
                if(this.interfaz.getPanelTratos().getBotonesTipoTrato().get(i).isSelected())
                    caso = i + 1;
            }
            String str1,str2,str3,str4;
            str1 = this.interfaz.getPanelTratos().getTextFields().get(0).getText();
            str2 = this.interfaz.getPanelTratos().getTextFields().get(1).getText();
            str3 = this.interfaz.getPanelTratos().getTextFields().get(2).getText();
            str4 = this.interfaz.getPanelTratos().getTextFields().get(3).getText();
            try{
                this.interfaz.getJuego().trato(str1,str2,str3,str4,caso);
            }catch (ExcepcionMonopoly ex){
                this.interfaz.getPanelTexto().addTexto(ex.getMensaje());
            }
        }else if(evento.getSource().equals(this.interfaz.getPanelTratos().getBotonVerTratos())){
            try {
                JOptionPane.showMessageDialog(this.interfaz, this.interfaz.getJuego().getJugadorActual().listarTratosPendientes());
            }catch (Exception ex){
                this.interfaz.getPanelTexto().addTexto(ex.getMessage());
            }
        }

    }
}
