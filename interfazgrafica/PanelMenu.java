package interfazgrafica;

import evento.GestionMenu;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel{
	private InterfazGrafica interfaz;

	private JButton botonComprar;
	private JButton botonHipotecar;
	private JButton botonEdificar;
	private JButton botonAcabar;
	private JButton botonVender;
	private JButton botonLanzar;
	private JButton botonEstadisticas;
	private JButton botonCambiarModo;
	private JButton botonTratos;

	public PanelMenu(InterfazGrafica interfaz) {
		this.interfaz = interfaz;
		this.setLayout(new GridLayout(3,3));
		this.setUp();
		this.addEventHandlers();
	}

	private void setUp() {
		botonComprar = new JButton();
		setButtonImage(botonComprar,"/resources/compra.png","COMPRAR");
		botonAcabar = new JButton();
		setButtonImage(botonAcabar,"/resources/acabar.png","ACABAR");
		//setButtonImage(botonComprar,"/resources/compra.png");
		botonHipotecar = new JButton();
		setButtonImage(botonHipotecar,"/resources/hipotecar.png","HIPOTECAR");
		//setButtonImage(botonEdificar,"/resources/edificar.png");
		botonEdificar = new JButton();
		setButtonImage(botonEdificar,"/resources/edificar.png","EDIFICAR");
		//setButtonImage(botonAcabar,"/resources/acabar.png");
		//botonEdificar = new JButton("Edificar");
		//setButtonImage(botonVender,"/resources/vender.png");
		botonLanzar = new JButton();
		setButtonImage(botonLanzar,"/resources/lanzar.png","LANZAR");
		//setButtonImage(botonLanzar,"/resources/lanzar.png");
		botonEstadisticas = new JButton();
		setButtonImage(botonEstadisticas,"/resources/estadisticas.png","ESTADISTICAS");
		//setButtonImage(botonEstadisticas,"/resources/estadisticas.png");

		botonCambiarModo = new JButton();
		setButtonImage(botonCambiarModo,"/resources/modo.png","MODO");

		botonTratos = new JButton();
		setButtonImage(botonTratos,"/resources/trato.png","TRATO");

		botonVender= new JButton();
		setButtonImage(botonVender,"/resources/vender.png","VENDER");

		this.add(botonComprar);
		this.add(botonHipotecar);
		this.add(botonAcabar);
		this.add(botonEdificar);
		this.add(botonLanzar);
		this.add(botonEstadisticas);
		this.add(botonTratos);
		this.add(botonCambiarModo);
		this.add(botonVender);
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

	public JButton getBotonComprar(){
		return this.botonComprar;
	}

	public JButton getBotonEdificar() {
		return this.botonEdificar;
	}

	public JButton getBotonHipotecar(){
		return this.botonHipotecar;
	}

	public JButton getBotonVender(){
		return this.botonVender;
	}

	public JButton getBotonEstadisticas() {
		return this.botonEstadisticas;
	}

	public JButton getBotonAcabar() { return this.botonAcabar; }

	public JButton getBotonCambiarModo(){
		return this.botonCambiarModo;
	}

	public JButton getBotonTratos() {
		return this.botonTratos;
	}

	public JButton getBotonLanzar(){
		return this.botonLanzar;
	}

	public InterfazGrafica getInterfaz(){
		return this.interfaz;
	}

	private void addEventHandlers(){
		this.botonComprar.addActionListener(new GestionMenu(this.interfaz));
		this.botonVender.addActionListener(new GestionMenu(this.interfaz));
		this.botonLanzar.addActionListener(new GestionMenu(this.interfaz));
		this.botonHipotecar.addActionListener(new GestionMenu(this.interfaz));
		this.botonEstadisticas.addActionListener(new GestionMenu(this.interfaz));
		this.botonAcabar.addActionListener(new GestionMenu(this.interfaz));
		this.botonCambiarModo.addActionListener(new GestionMenu(this.interfaz));
		this.botonTratos.addActionListener(new GestionMenu(this.interfaz));
		this.botonEdificar.addActionListener(new GestionMenu(this.interfaz));
		//this.botonHipotecar.addActionListener(new GestionMenu(this.interfaz));
	}
}