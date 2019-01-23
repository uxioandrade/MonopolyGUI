package evento;

import interfazgrafica.InterfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBotonesListaEdificar implements ActionListener {

    private InterfazGrafica interfazGrafica;

    public GestionBotonesListaEdificar(InterfazGrafica interfaz){
        this.interfazGrafica = interfaz;
    }

    public void actionPerformed(ActionEvent evento){
        if(evento.getSource().equals(this.interfazGrafica.getPanelEdificar().getBotonEdificarLista())){
            this.interfazGrafica.getPanelEdificar().edificarTipo();
        }else if(evento.getSource().equals(this.interfazGrafica.getPanelEdificar().getBotonSalirEdificar())){
            this.interfazGrafica.getPanelInfo().setVisible(true);
            this.interfazGrafica.getPanelTexto().setVisible(true);
            this.interfazGrafica.getPanelMenu().setVisible(true);
            this.interfazGrafica.getPanelTablero().setVisible(true);
            this.interfazGrafica.getPanelBotones().setVisible(true);
            this.interfazGrafica.getPanelJugadores().setVisible(true);
            this.interfazGrafica.getPanelEdificar().setVisible(false);
        }
    }

}
