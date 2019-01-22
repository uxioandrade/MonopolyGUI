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
        if(evento.getActionCommand().equals("Salir")){
            int confirm = JOptionPane.showConfirmDialog(this.botones.getInterfaz(), "¿Desea acabar la partida?", "Salir", JOptionPane.YES_NO_OPTION);
            if(confirm > 0)
                this.botones.getInterfaz().setVisible(false);
        }
    }

}
