package interfazgrafica;

import evento.GestionBotones;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;

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
		botonSalir = new JButton("Salir");
		botonSalirCarcel = new JButton("Salir Carcel");
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
}
