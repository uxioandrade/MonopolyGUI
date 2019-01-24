package interfazgrafica;

import evento.GestionBotones;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;

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
		botonSalir = new JButton("Salir");
		botonOtro = new JButton("Otro");
	}

	private void setUp() {
		this.add(botonSalir);
		this.add(botonOtro);
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
