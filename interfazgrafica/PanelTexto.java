package interfazgrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.color.*;

public class PanelTexto extends JPanel{
    private JScrollPane scroll;
    private JEditorPane editor;

    public PanelTexto(){
        this.setLayout(new GridLayout());
        editor = new JEditorPane();
        editor.setEditable(false);
        scroll = new JScrollPane(editor);
        this.add(scroll);
        this.setPreferredSize(new Dimension(400,500));
        editor.setBackground(Color.PINK);
    }

    public void setEditorBackground(Color bg){
        this.editor.setBackground(bg);
    }

    public void setFont(String font, int n, int size){
        editor.setFont(new Font(font, n, size));
    }

    public void setTexto(String str){
        editor.setText(str);
    }

    public void addTexto(String str){
        editor.setText(editor.getText() + "\n->" + str);
    }
}
