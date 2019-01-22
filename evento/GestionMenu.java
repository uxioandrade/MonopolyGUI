package evento;

import interfazgrafica.InterfazGrafica;
import interfazgrafica.PanelMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import monopoly.excepciones.*;
import monopoly.excepciones.dinamicas.*;
import monopoly.excepciones.dinero.*;
import monopoly.excepciones.restricciones.*;

public class GestionMenu implements ActionListener {

    private InterfazGrafica interfazGrafica;

    public GestionMenu(InterfazGrafica interfazGrafica){
        this.interfazGrafica = interfazGrafica;
    }


    public void actionPerformed(ActionEvent evento) {
        if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonLanzar())){
            try {
                int posInicial = this.interfazGrafica.getJuego().getJugadorActual().getAvatar().getCasilla().getPosicion();
                this.interfazGrafica.getJuego().lanzar();
                this.interfazGrafica.getPanelTablero().actualizarFicha(posInicial,this.interfazGrafica.getJuego().getJugadorActual());
            } catch (ExcepcionMonopoly ex) {
                System.out.println(ex.getMensaje());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonAcabar())){
            try{
                this.interfazGrafica.getJuego().acabar();
            }catch (ExcepcionMonopoly ex) {
                System.out.println(ex.getMensaje());
            }
        }
    }

}
