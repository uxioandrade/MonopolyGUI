package Ejecucion;
import java.io.IOException;
import java.util.ArrayList;

import interfazgrafica.*;
import monopoly.contenido.casillas.Casilla;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;


public class Ejecutar {

	public static void main(String[] args) throws IOException {
		PanelEditarMapa editarMapa = new PanelEditarMapa();
		editarMapa.setVisible(false);
		PanelSeleccionarJuego psj = new PanelSeleccionarJuego(editarMapa);
		psj.setVisible(true);
	}

	public static void ejecucion(int[] permutacionCasillas, double[] precios) throws IOException {
		Valor valor = new Valor();
		valor.reordenarCasillas(permutacionCasillas);
		valor.crearGrupos(permutacionCasillas, precios);
        Casilla salida = valor.getSalida();
		InterfazGrafica interfaz = new InterfazGrafica(new Juego(valor), permutacionCasillas, valor);
		interfaz.setVisible(false);
		LoginPanel login = new LoginPanel(interfaz, salida);
		login.setVisible(true);
	}
}
