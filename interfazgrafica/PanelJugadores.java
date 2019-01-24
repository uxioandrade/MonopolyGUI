package interfazgrafica;

import evento.GestionJugadores;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PanelJugadores extends JPanel {

    private InterfazGrafica interfazGrafica;

    private ArrayList<JButton> jugadores;

    public PanelJugadores(InterfazGrafica interfazGrafica){
        this.interfazGrafica = interfazGrafica;
        this.jugadores = new ArrayList<>();
    }


    public void cargarJugadoresIniciales() throws IOException {
        GridBagConstraints c = new GridBagConstraints();
        ArrayList<String> nombres = LoginPanel.getNombres();

        if(nombres.size() <= 3)
            this.setLayout(new GridLayout(0,nombres.size()));
        else if(nombres.size() == 4)
            this.setLayout(new GridLayout(2,2));
        else
            this.setLayout(new GridLayout(2,3));

        for(Integer k=0; k<nombres.size(); k++) {
            jugadores.add(new JButton(nombres.get(k)));
            jugadores.get(k).setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(jugadores.get(k));
        }
        this.addEventHandlers();
    }

    private void addEventHandlers(){
        for(JButton b: jugadores){
            b.addActionListener(new GestionJugadores(this.interfazGrafica));
        }
    }

}
