package interfazgrafica;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Ejecucion.Ejecutar;
import evento.GestionBotonEditorMapa;

public class PanelEditarMapa extends JFrame{
	private JLabel fotoCasilla;
	private JLabel descripcion;
	private JTextField posicion;
	private JButton ok;
	private JScrollPane scrollPane;
	private JButton botonSeleccionar;
	private JList jListaCasillas;
	private DefaultListModel<Integer> modeloLista;
	private Integer contador = 0;
	private ArrayList<JTextField> jTextFields;
	private ArrayList<JLabel> jLabels;
	private int[] permutacionCasillas;
	private double[] precios;
	private JButton botonListo;
	private Mapa map;
	
	private static final Integer IMG_W = 230;
	private static final Integer IMG_H = 200;
	
	public PanelEditarMapa() throws IOException {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(new Dimension(650, 800));
		this.scrollPane = new JScrollPane();
		this.jListaCasillas = new JList();
		this.jTextFields = new ArrayList();
		this.jLabels = new ArrayList();
		this.scrollPane.setViewportView(jListaCasillas);
		this.botonSeleccionar = new JButton("Seleccionar");
		this.modeloLista = new DefaultListModel();
		for(int i = 1; i < 41; i++) {
			this.modeloLista.addElement(i);
		}
		this.jListaCasillas.setModel(modeloLista);
		try {
			this.setUp();
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void setMap(Mapa map) {
		this.map = map;
	}

	public JButton getBotonSeleccionar(){
		return this.botonSeleccionar;
	}
	
	private void setUp() throws IOException {
		permutacionCasillas = new int[40]; // 40 casillas
		precios = new double[8]; // 8 grupos de casillas
		
		Image img = ImageIO.read(getClass().getResource("/resources/0.jpg"));
		img = img.getScaledInstance(IMG_H, IMG_W, Image.SCALE_SMOOTH);
		fotoCasilla = new JLabel();
		fotoCasilla.setIcon(new ImageIcon(img));
		fotoCasilla.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(fotoCasilla);
		JFrame myFrame = this;
		descripcion = new JLabel("Introduzca la posici�n deseada de la casilla mostrada (1-40)");
		this.add(descripcion);
		this.add(scrollPane);
		this.add(botonSeleccionar);
		this.botonListo = new JButton("Listo");
		this.addEventHandlers();
	}
		
	public void cambiarImagen() {
		int index = jListaCasillas.getSelectedIndex();
		if(index > -1) {
			int posicion = modeloLista.get(index);
			this.permutacionCasillas[40-modeloLista.size()] = posicion;
			modeloLista.remove(index);
			Image img = null;
			if(modeloLista.size()<=0) {
				this.jListaCasillas.setVisible(false);
				this.botonSeleccionar.setVisible(false);
				for(int i = 0; i < 8; i++) {
					this.jLabels.add(new JLabel("Valor Grupo " + i));
					this.add(this.jLabels.get(i));
					this.jTextFields.add(new JTextField());
					this.add(this.jTextFields.get(i));
				}
				this.add(botonListo);
				return;
			}
			try {
				img = ImageIO.read(getClass().getResource("/resources/"+(40-modeloLista.size())+".jpg"));
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			this.fotoCasilla.setIcon(new ImageIcon(img));}	
	}
		/*
		JFrame myFrame = this;
		ok = new JButton("OK");
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = -1;
				if(contador<40) {
					try {
						pos = Integer.parseInt(posicion.getText());
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(myFrame.getContentPane(), "Posicion incorrecta", "Introduzca una posicion correcta (entre 1 y 40) "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(pos <= 0 || pos > 40) {
						JOptionPane.showMessageDialog(myFrame.getContentPane(), "Posicion incorrecta", "Introduzca una posicion correcta (entre 1 y 40)", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						permutacionCasillas[contador] = pos;
						contador++;
						Image img = null;
						if(contador<40) {
							try {
								img = ImageIO.read(getClass().getResource("/resources/"+contador.toString()+".jpg"));
							} catch (IOException e1) {
								e1.printStackTrace();
								return;
							}
							img = img.getScaledInstance(IMG_H, IMG_W, Image.SCALE_SMOOTH);
							fotoCasilla = new JLabel();
							fotoCasilla.setIcon(new ImageIcon(img));
							fotoCasilla.setAlignmentX(Component.CENTER_ALIGNMENT);
						}else {
							descripcion = new JLabel("Introduzca el precio de los solares MARRONES");
						}
					}
				}else {
					try {
						pos = Integer.parseInt(posicion.getText());
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(myFrame.getContentPane(), "Cantidad incorrecta", "Introduzca una cantidad entera correcta "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(pos < 0) {
						JOptionPane.showMessageDialog(myFrame.getContentPane(), "Cantidad incorrecta", "Introduzca una cantidad entera positiva correcta ", JOptionPane.ERROR_MESSAGE);
						return;
					}
					precios[contador-40] = pos;
					contador++;
					switch(contador) {
					case 41: 
						descripcion = new JLabel("Introduzca el precio de los solares CYAN");
						break;
					case 42:
						descripcion = new JLabel("Introduzca el precio de los solares ROSA");
						break;
					case 43:
						descripcion = new JLabel("Introduzca el precio de los solares AMARILLO");
						break;
					case 44:
						descripcion = new JLabel("Introduzca el precio de los solares ROJO");
						break;
					case 45:
						descripcion = new JLabel("Introduzca el precio de los solares NARANJA");
						break;
					case 46:
						descripcion = new JLabel("Introduzca el precio de los solares VERDE");
						break;
					case 47:
						descripcion = new JLabel("Introduzca el precio de los solares AZUL");
						break;
					case 48:
						try {
							Ejecutar.ejecucion(permutacionCasillas, precios);
							break;
						} catch (IOException e1) {
							e1.printStackTrace();
							return;
						}
					default:
						return;
					}
				}
				
			}
		});
		this.add(ok);
		*/
	public void ponerValor() {
		double acumulado = 100;
		for(int i = 0; i < 8; i++) {
			if(Double.parseDouble(this.jTextFields.get(i).getText()) > 0) {
				this.precios[i] = Double.parseDouble(this.jTextFields.get(i).getText());
				acumulado += this.precios[i];
			}else {
				this.precios[i] = acumulado + 100;
			}
		}
		try {
			Ejecutar.ejecucion(permutacionCasillas, this.precios);
			this.map.imprimir("/com.company/test.csv");
			return;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public JButton getBotonListo() {
		return this.botonListo;
	}
	
	public void addEventHandlers() {
		this.botonSeleccionar.addActionListener(new GestionBotonEditorMapa(this));
		this.botonListo.addActionListener(new GestionBotonEditorMapa(this));
	}
}
