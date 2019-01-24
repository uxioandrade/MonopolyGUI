package interfazgrafica;

import javax.swing.*;
import java.awt.*;

public class PanelBancarrota extends JPanel {

    private InterfazGrafica interfaz;
    private PanelHipotecar panelHipotecar;
    private JButton botonBancarrota;

    public PanelBancarrota(InterfazGrafica interfaz, PanelHipotecar panelHipotecar){
        this.interfaz = interfaz;
        this.panelHipotecar = panelHipotecar;
        this.setLayout(new GridLayout(1,3));
    }

    public void setUp(){
        this.botonBancarrota = new JButton("Bancarrota");
        this.add(panelHipotecar);
        this.add(botonBancarrota);
    }

    public JButton getBotonBancarrota(){
        return this.botonBancarrota;
    }

}
