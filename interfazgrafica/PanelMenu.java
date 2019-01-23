package interfazgrafica;

import evento.GestionMenu;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Image;
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
		this.setLayout(new GridLayout(2, 4));
		this.setUp();
		this.addEventHandlers();
	}
	
	private void setUp() {
		/***
		 * IMPORTANTE
		//Importante: para poner las imágenes en los JButton quitar de los constructores del JButton
		//el texto
		//es decir, new JButton("Comprar") - > new JButton()**
		**/
		botonComprar = new JButton("Comprar");
		//setButtonImage(botonComprar,"/resources/comprar.png");
		botonHipotecar = new JButton("Hipotecar");
		//setButtonImage(botonEdificar,"/resources/edificar.png");
		botonAcabar = new JButton("Acabar");
		//setButtonImage(botonAcabar,"/resources/acabar.png");
		botonEdificar = new JButton("Edificar");
		//setButtonImage(botonVender,"/resources/vender.png");
		botonLanzar = new JButton("Lanzar");
		//setButtonImage(botonLanzar,"/resources/lanzar.png");
		botonEstadisticas = new JButton("Estadisticas");
		//setButtonImage(botonEstadisticas,"/resources/estadisticas.png");

        botonCambiarModo = new JButton("Modo");
        botonTratos = new JButton("Tratos");

		this.add(botonComprar);
		this.add(botonHipotecar);
		this.add(botonAcabar);
		this.add(botonEdificar);
		this.add(botonLanzar);
		this.add(botonEstadisticas);
		this.add(botonTratos);
		this.add(botonCambiarModo);
	}

	private void setButtonImage(JButton boton, String path){
		try {
			BufferedImage img = ImageIO.read(getClass().getResource(path));
			img = InterfazGrafica.resize( img,40,80);
			boton.setIcon(new ImageIcon((Image) img));
		} catch (Exception ex) {
			System.out.println(ex);
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
