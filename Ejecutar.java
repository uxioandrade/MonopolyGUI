import java.io.IOException;

import com.company.LecturaDatos;
import monopoly.plataforma.Valor;


public class Ejecutar {
	
	public static void main(String[] args) throws IOException {
		//InterfazGrafica interfaz = new InterfazGrafica(new Juego());
		//interfaz.setVisible(false);
		//new LoginPanel(interfaz).setVisible(true);
		//CreacionTablero cr=new CreacionTablero();
		//cr.setVisible(true);
		LecturaDatos.ImprimirCSV("/resources/test.csv", Valor.casillas);
	}
}
