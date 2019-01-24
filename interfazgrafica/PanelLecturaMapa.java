package interfazgrafica;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelLecturaMapa extends JFrame {

    private JFileChooser fc;
    public PanelLecturaMapa(){
        super("Escoge un mapa");
        initComponents();
        setupComponents();
        layoutComponents();
    }
    private void initComponents(){
        fc=new JFileChooser();
    }
    private void setupComponents(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(200,200);
        fc.setFileFilter(new FileNameExtensionFilter(".CSV","csv"));
        fc.setAcceptAllFileFilterUsed(false);
        this.fc.showOpenDialog(this);
    }
    private void layoutComponents(){

    }
    public String getSelectedFile(){
        return fc.getSelectedFile().getName();
    }
}
