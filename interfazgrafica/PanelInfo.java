package interfazgrafica;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelInfo extends JPanel{
	private InterfazGrafica interfaz;

	ArrayList<JButton> jugadores = new ArrayList<JButton>();
	private JLabel casilla;
	private PanelTexto panelTexto;

	static Integer IMG_W = 230;
	static Integer IMG_H = 200;

	public PanelInfo(InterfazGrafica interfaz) throws IOException {
		this.interfaz = interfaz;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Image img = ImageIO.read(getClass().getResource("/resources/0.jpg"));
		img = img.getScaledInstance(IMG_H, IMG_W, Image.SCALE_SMOOTH);
		casilla = new JLabel();
		casilla.setIcon(new ImageIcon(img));
		casilla.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(casilla);
		
		this.panelTexto = new PanelTexto();
		this.panelTexto.setEditorBackground(Color.WHITE);
		this.panelTexto.setPreferredSize(new Dimension(200,300));
		this.panelTexto.setFont("Arial", 0, 9);
		this.add(panelTexto);
	}
	
	public void setCasillaInfo(BotonCasilla boton) throws IOException {
		Image img = ImageIO.read(getClass().getResource(boton.getURL()));
		img = img.getScaledInstance(IMG_H, IMG_W, Image.SCALE_SMOOTH);
		casilla.setIcon(new ImageIcon(img));
		this.panelTexto.setTexto(boton.getCasilla().toString());
	}
	

}
