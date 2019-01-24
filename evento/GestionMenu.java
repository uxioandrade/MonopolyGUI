package evento;

import interfazgrafica.InterfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfazgrafica.PanelVender;
import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.excepciones.*;
import monopoly.plataforma.Juego;

import javax.swing.*;

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
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMensaje());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonAcabar())){
            try{
                this.interfazGrafica.getJuego().acabar();
            }catch (ExcepcionMonopoly ex) {
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMensaje());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonEstadisticas())){
            try{
                JOptionPane.showMessageDialog(this.interfazGrafica,this.interfazGrafica.getJuego().estadisticasGlobales());
            }catch (Exception ex){
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonComprar())){
            try{
                this.interfazGrafica.getJuego().comprar();
            }catch(ExcepcionMonopoly ex){
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMensaje());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonHipotecar())) {
            try {
                this.interfazGrafica.getPanelHipotecar().setVisible(true);
                this.interfazGrafica.getPanelHipotecar().resetHipotecar();
            } catch (Exception ex) {
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonVender())){
            try{
                this.interfazGrafica.getJuego().getJugadorActual().setDinero(5);
                this.interfazGrafica.getJuego().getJugadorActual().getAvatar().setCasilla(this.interfazGrafica.getValor().getCasillas().get(38));
                this.interfazGrafica.getPanelTexto().setVisible(false);
                this.interfazGrafica.getPanelMenu().setVisible(false);
                this.interfazGrafica.getPanelBotones().setVisible(false);
                this.interfazGrafica.getPanelJugadores().setVisible(false);
                this.interfazGrafica.getPanelInfo().setVisible(false);
                this.interfazGrafica.getPanelTablero().setVisible(false);
                this.interfazGrafica.getPanelVender().setVisible(true);
                this.interfazGrafica.getPanelVender().reset(interfazGrafica.getJuego().getJugadorActual());
            }catch(Exception ex){
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonEdificar())){
            try {
                Jugador jugadorActual = this.interfazGrafica.getJuego().getJugadorActual();
                Casilla casillaActual = jugadorActual.getAvatar().getCasilla();
                if (!(casillaActual instanceof Solar)) {
                    this.interfazGrafica.getPanelTexto().addTexto("No se puede edificar en esta casilla");
                    return;
                }
                Solar solarActual = (Solar) casillaActual;
                if (!jugadorActual.getPropiedades().contains(solarActual)){
                    this.interfazGrafica.getPanelTexto().addTexto("El jugador actual no posee la casilla " + casillaActual.getNombre());
                    return;
                }
                if(!(solarActual.frecuenciaVisita(jugadorActual.getAvatar()) > 2 || solarActual.getPropietario().poseeGrupoCompleto(solarActual.getGrupo()))){
                    this.interfazGrafica.getPanelTexto().addTexto("El jugador no verifica las condiciones para edificar");
                    return;
                }
                //this.interfazGrafica.getPanelTexto().setVisible(false);
                this.interfazGrafica.getPanelMenu().setVisible(false);
                this.interfazGrafica.getPanelBotones().setVisible(false);
                this.interfazGrafica.getPanelJugadores().setVisible(false);
                this.interfazGrafica.getPanelInfo().setVisible(false);
                this.interfazGrafica.getPanelTablero().setVisible(false);
                this.interfazGrafica.getPanelEdificar().setVisible(true);
            }catch(Exception ex){
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonCambiarModo())){
            try{
                this.interfazGrafica.getJuego().cambiar();
            }catch(Exception ex){
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelMenu().getBotonTratos())){
            try{
                this.interfazGrafica.getPanelTratos().setVisible(true);
                this.interfazGrafica.getPanelTratos().resetTratos();
            }catch(Exception ex) {
                this.interfazGrafica.getPanelTexto().addTexto(ex.getMessage());
            }
        }
    }
}