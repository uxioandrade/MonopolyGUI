import java.io.IOException;

import interfazgrafica.InterfazGrafica;
import interfazgrafica.LoginPanel;
import monopoly.plataforma.Juego;


public class Ejecutar {
	
	public static void main(String[] args) throws IOException {
		InterfazGrafica interfaz = new InterfazGrafica(new Juego());
		interfaz.setVisible(false);
		new LoginPanel(interfaz).setVisible(true);
	}
}
