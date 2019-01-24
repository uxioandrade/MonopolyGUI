package interfazgrafica;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;

import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import monopoly.plataforma.Valor;

import evento.GestionInfo;

public class PanelTablero extends JPanel{
	private InterfazGrafica interfaz;
	
	private ArrayList<BotonCasilla> casillas;
	private ArrayList<JLabel> nombreJugadores;

	private int[] permutacionCasillas;
	private Image backgroundImg;
	private Valor valor;
	
	public PanelTablero(InterfazGrafica interfaz, int[] permutacionCasillas, Valor valor) throws IOException {
		this.interfaz = interfaz;
		this.valor = valor;
		this.permutacionCasillas = permutacionCasillas;
		this.backgroundImg = new ImageIcon("/resources/lotrMap.png").getImage();
		this.setLayout(new GridBagLayout());
		this.setUp();
		this.addEventHandlers();
	}
	
	private void setUp() throws IOException {
		Integer i, index;
		GridBagConstraints c = new GridBagConstraints();

		casillas = new ArrayList<BotonCasilla>();
		ArrayList<Casilla> casillasAux = valor.getCasillas();
		Integer[] p = this.valor.getBiyeccion();
		for(i=0; i<11; i++) { // Primera fila
			casillas.add(new BotonCasilla("/resources/"+p[i]+".jpg",casillasAux.get(i)));
			c.gridx = 200*i;
			c.gridy = 0;
			this.add(casillas.get(i), c);
		}
		for(i=0; i<10; i++) { // Primera columna
			index = 11+i;
			casillas.add(new BotonCasilla("/resources/"+p[index]+".jpg",casillasAux.get(index)));
			c.gridx = 2000;
			c.gridy = 200*(i+1);
			this.add(casillas.get(index), c);
		}
		for(i=0; i<10; i++) { // Segunda fila
			index = 21+i;
			casillas.add(new BotonCasilla("/resources/"+(p[index])+".jpg",casillasAux.get(index)));
			c.gridx = 2000 - 200*(i+1);
			c.gridy = 2000;
			this.add(casillas.get(index), c);
		}
		for(i=0; i<9; i++) { // Segunda columna
			index = 31+i;
			casillas.add(new BotonCasilla("/resources/"+(p[index])+".jpg",casillasAux.get(index)));
			c.gridx = 0;
			c.gridy = 2000 - 200*(i+1);
			this.add(casillas.get(index), c);
		}
	}
	
	private void addEventHandlers() {
		for(BotonCasilla bc: casillas) {
			bc.addActionListener(new GestionInfo(this.interfaz.getPanelInfo()));
		}
	}
	
	public void cargarFichasIniciales(){
		GridBagConstraints c = new GridBagConstraints();
		Integer nJugadores = LoginPanel.getNJugadores();
		
		for(int k=0; k<nJugadores; k++) {
			casillas.get(valor.getPosicionSalida()).addFicha(interfaz.getFichas().get(k), k);
		}
		casillas.get(valor.getPosicionSalida()).actualizarCasilla();
		this.valor.setPosiscionCartas();


	}

	public void actualizarFicha(int posInicial, Jugador jugador){
		casillas.get(posInicial).deleteFicha(this.interfaz.getJuego().getTurnosJugadores().indexOf(jugador.getNombre()));
		casillas.get(posInicial).actualizarCasilla();
		//Añadiendo una ficha estratégicamente
		casillas.get(jugador.getAvatar().getCasilla().getPosicion()).addFicha(jugador.getAvatar().getFicha(),this.interfaz.getJuego().getTurnosJugadores().indexOf(jugador.getNombre()));
		casillas.get(jugador.getAvatar().getCasilla().getPosicion()).actualizarCasilla();
	}

	public ArrayList<BotonCasilla> getCasillas(){
		return this.casillas;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.backgroundImg, 50, 50, null);
	}

}
