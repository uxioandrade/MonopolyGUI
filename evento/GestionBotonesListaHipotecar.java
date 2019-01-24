package evento;

import interfazgrafica.InterfazGrafica;
import interfazgrafica.PanelHipotecar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionBotonesListaHipotecar implements ActionListener {

    private InterfazGrafica interfazGrafica;
    private PanelHipotecar panelHipotecar;

    public GestionBotonesListaHipotecar(InterfazGrafica interfazGrafica,PanelHipotecar panelHipotecar){
        this.interfazGrafica = interfazGrafica;
        this.panelHipotecar = panelHipotecar;
    }

    public void actionPerformed(ActionEvent evento){
        if(evento.getSource().equals(this.panelHipotecar.getBotonHipotecarLista())){
            panelHipotecar.hipotecarElemento();
        }else if(evento.getSource().equals(this.panelHipotecar.getBotonSalirHipotecar())){
            this.interfazGrafica.getPanelInfo().setVisible(true);
            //this.interfazGrafica.getPanelTexto().setVisible(true);
            this.interfazGrafica.getPanelMenu().setVisible(true);
            this.interfazGrafica.getPanelTablero().setVisible(true);
            this.interfazGrafica.getPanelBotones().setVisible(true);
            this.interfazGrafica.getPanelJugadores().setVisible(true);
            this.panelHipotecar.setVisible(false);
        }else if(evento.getSource().equals(this.panelHipotecar.getBotonDeshipotecar())){
            panelHipotecar.desHipotecarElemento();
        }
    }

}
