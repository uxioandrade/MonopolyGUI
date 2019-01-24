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
	private JButton botonSalirCarcel;

	public PanelBotones(InterfazGrafica interfaz) {
		this.interfaz = interfaz;
		this.setLayout(new GridLayout(2,1));
		this.initComponents();
		this.setUp();
		this.addEventHandlers();
	}

	private void initComponents(){
		botonSalir = new JButton();
		setButtonImage(botonSalir,"/resources/salir.png","Salir");

		botonSalirCarcel = new JButton();
		setButtonImage(botonSalirCarcel,"/resources/salirCarcel.png","SalirCarcel");

	}

	private void setUp() {
		this.add(botonSalir);
		this.add(botonSalirCarcel);
	}

	public JButton getBotonSalir(){
		return this.botonSalir;
	}

	public JButton getBotonSalirCarcel(){
		return this.botonSalirCarcel;
	}

	public InterfazGrafica getInterfaz(){
		return this.interfaz;
	}

	private void addEventHandlers() {
		this.botonSalir.addActionListener(new GestionBotones(this));
		this.botonSalirCarcel.addActionListener(new GestionBotones(this));
	}
	private void setButtonImage(JButton boton, String path, String nombre){
		try {
			BufferedImage img = ImageIO.read(getClass().getResource(path));
			img = InterfazGrafica.resize( img,40,80);
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
}
