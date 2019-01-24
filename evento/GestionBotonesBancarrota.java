package evento;

import interfazgrafica.InterfazGrafica;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.contenido.edificios.Edificios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBotonesBancarrota implements ActionListener {

    private InterfazGrafica interfazGrafica;

    public GestionBotonesBancarrota(InterfazGrafica interfazGrafica){
        this.interfazGrafica = interfazGrafica;
    }

    public void actionPerformed(ActionEvent evento){
        if(evento.getSource().equals(this.interfazGrafica.getPanelBancarrota().getBotonBancarrota())){
            Propiedades comprable;
            for (Propiedades cas : this.interfazGrafica.getJuego().getJugadorActual().getPropiedades()) {
                if (cas instanceof Solar) {
                    for (Edificios ed : ((Solar) cas).getConstrucciones()) {
                        this.interfazGrafica.getJuego().getTablero().borrarEdificio(ed);
                        ((Solar) cas).getConstrucciones().remove(ed);
                    }
                }
                cas.setPropietario(this.interfazGrafica.getJuego().getTablero().getBanca());
                cas.setHipotecado(false);
            }
            this.interfazGrafica.getPanelTablero().getCasillas().get(this.interfazGrafica.getJuego().getJugadorActual().getAvatar().getCasilla().getPosicion()).deleteFicha(this.interfazGrafica.getJuego().getTurnosJugadores().indexOf(this.interfazGrafica.getJuego().getJugadorActual()));
            this.interfazGrafica.getJuego().getTablero().getAvatares().remove(this.interfazGrafica.getJuego().getJugadorActual().getAvatar().getId());
            this.interfazGrafica.getJuego().getTablero().getJugadores().remove(this.interfazGrafica.getJuego().getJugadorActual().getNombre());

            this.interfazGrafica.getJuego().getTurnosJugadores().remove(this.interfazGrafica.getJuego().getJugadorActual().getNombre());

            if (this.interfazGrafica.getJuego().getTurnosJugadores().size() == 1) {
                this.interfazGrafica.getPanelTexto().addTexto("Partida acabada!\n Enhorabuena " + this.interfazGrafica.getJuego().getTurnosJugadores().get(0) + ", eres el ganador!!!!");
                System.exit(0);
            }
        }
    }

}
