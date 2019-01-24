package interfazgrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Ejecucion.Ejecutar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PanelSeleccionarJuego extends JFrame{

	private JLabel titulo;
	private JButton editar;
	private JButton editado;
	private JButton normal;
	private Mapa m;
	private PanelEditarMapa editarMapa;
	private PanelLecturaMapa panelLecturaMapa;
	
	public PanelSeleccionarJuego(PanelEditarMapa editarMapa) {
		this.editarMapa = editarMapa;
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(new Dimension(300, 200));
		this.setUp();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setUp() {
		titulo = new JLabel("SELECCIONE TIPO DE MAPA");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(titulo);
		editar = new JButton("Editar mi propio mapa");
		editar.setAlignmentX(Component.CENTER_ALIGNMENT);
		Mapa map = this.m;
		editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((JButton) e.getSource()).getParent().setVisible(false);
				editarMapa.setVisible(true);
				editarMapa.setMap(map);
			}
		});
		this.add(editar);
		editado = new JButton("Jugar con mapa ya editado");
		editado.setAlignmentX(Component.CENTER_ALIGNMENT);
		editado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelLecturaMapa = new PanelLecturaMapa();
				Mapa m = new Mapa(panelLecturaMapa.getSelectedFile());
				titulo.setVisible(false);
				editar.setVisible(false);
				editado.setVisible(false);
				normal.setVisible(false);
				ArrayList<Double> precios = m.getValorGrupo();
				ArrayList<Integer> perm = m.getEnteros();
				double [] prize = new double[8];
				int[] p = new int[40];
				for(int i = 0; i < 40;i++)
					p[i] = perm.get(i);
				for(int i = 0; i < 8;i++){
					prize[i] = precios.get(i);
				}
				try {
					Ejecutar.ejecucion(p, prize);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// 	FALTA LEER DE UN FICHERO LOS DATOS DE UN MAPA YA EDITADO
		// AQUI IRIA EL EVENT HANDLER DE DICHO BOTON
		
		
		this.add(editado);
		normal = new JButton("Jugar con el mapa estandar");
		normal.setAlignmentX(Component.CENTER_ALIGNMENT);
		normal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				titulo.setVisible(false);
				editar.setVisible(false);
				editado.setVisible(false);
				normal.setVisible(false);
				int[] perm = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
			                    26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
				double[] precios = {-1,-1,-1,-1,-1,-1,-1,-1};
				try {
					Ejecutar.ejecucion(perm, precios);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.add(normal);
	}
	public Mapa getMapa(){
		return m;
	}
}
