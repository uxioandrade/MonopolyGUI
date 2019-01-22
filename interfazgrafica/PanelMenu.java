package interfazgrafica;

import evento.GestionMenu;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel{
	private InterfazGrafica interfaz;
	
	private JButton botonComprar;
	private JButton botonEdificar;
	private JButton botonAcabar;
	private JButton botonVender;
	private JButton botonLanzar;
	private JButton botonEstadisticas;

	public PanelMenu(InterfazGrafica interfaz) {
		this.interfaz = interfaz;
		this.setLayout(new GridLayout(2, 3));
		this.setUp();
		this.addEventHandlers();
	}
	
	private void setUp() {
		botonComprar = new JButton("Comprar");
		botonEdificar = new JButton("Edificar");
		botonAcabar = new JButton("Acabar Turno");
		botonVender = new JButton("Vender");
		botonLanzar = new JButton("Lanzar");
		botonEstadisticas = new JButton("Estadisticas");
		this.add(botonComprar);
		this.add(botonEdificar);
		this.add(botonAcabar);
		this.add(botonVender);
		this.add(botonLanzar);
		this.add(botonEstadisticas);
	}

	public JButton getBotonComprar(){
		return this.botonComprar;
	}

	public JButton getBotonEdificar() {
		return this.botonEdificar;
	}

	public JButton getBotonHipotecar(){
		return this.botonAcabar;
	}

	public JButton getBotonVender(){
		return this.botonVender;
	}

	public JButton getBotonEstadisticas() {
		return this.botonEstadisticas;
	}

	public JButton getBotonAcabar() { return this.botonAcabar; }

	public JButton getBotonLanzar(){
		return this.botonLanzar;
	}

	public InterfazGrafica getInterfaz(){
		return this.interfaz;
	}

	private void addEventHandlers(){
		this.botonComprar.addActionListener(new GestionMenu(this.interfaz));
		this.botonLanzar.addActionListener(new GestionMenu(this.interfaz));
		this.botonEdificar.addActionListener(new GestionMenu(this.interfaz));
		this.botonEstadisticas.addActionListener(new GestionMenu(this.interfaz));
		this.botonAcabar.addActionListener(new GestionMenu(this.interfaz));
		this.botonVender.addActionListener(new GestionMenu(this.interfaz));
		this.botonEstadisticas.addActionListener(new GestionMenu(this.interfaz));
	}
}
