package interfazgrafica;

import evento.GestionBotones;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelBotones extends JPanel{
	private InterfazGrafica interfaz;
	private JButton botonSalir;
	private JButton botonOtro;

	public PanelBotones(InterfazGrafica interfaz) {
		this.interfaz = interfaz;
		this.setLayout(new GridLayout(0,1));
		this.initComponents();
		this.setUp();
		this.addEventHandlers();
	}

	private void initComponents(){
		botonSalir = new JButton();
		setButtonImage(this.botonSalir,"/resources/salir.png/","SALIR");

		botonOtro = new JButton();
		setButtonImage(this.botonOtro,"/resources/otro.png/","otro");
	}

	private void setUp() {
		this.add(botonSalir);
		this.add(botonOtro);
	}
	public void setButtonImage(JButton boton, String path, String nombre){
		try {
			BufferedImage img = ImageIO.read(getClass().getResource(path));
			img = InterfazGrafica.resize( img,45,90);
			//boton.setIcon(new ImageIcon((Image) img));
			boton.setOpaque(false);
			boton.setContentAreaFilled(false);
			boton.setBorderPainted(false);
			boton.setIcon(new ImageIcon(img));
			//boton.setMaximumSize(new Dimension(45,85));
		} catch (Exception ex) {
			//System.out.println(ex);
			boton = new JButton(nombre);
			boton.setMaximumSize(new Dimension(40,80));
		}
	}

	public JButton getBotonSalir(){
		return this.botonSalir;
	}

	public JButton getBotonOtro(){
		return this.botonOtro;
	}

	public InterfazGrafica getInterfaz(){
		return this.interfaz;
	}

	private void addEventHandlers() {
		this.botonSalir.addActionListener(new GestionBotones(this.interfaz.getPanelBotones()));
	}
}
