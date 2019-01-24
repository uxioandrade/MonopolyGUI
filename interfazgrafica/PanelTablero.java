package interfazgrafica;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;

import evento.GestionSalirCarcel;
import monopoly.contenido.Jugador;

import javax.swing.*;

import monopoly.plataforma.Valor;

import evento.GestionInfo;

@SuppressWarnings("serial")
public class PanelTablero extends JPanel{
	private InterfazGrafica interfaz;
	
	private ArrayList<BotonCasilla> casillas;
	private ArrayList<JLabel> nombreJugadores;
	private JButton salirCarcel;

	private Image backgroundImg;
	
	public PanelTablero(InterfazGrafica interfaz) throws IOException {
		this.interfaz = interfaz;
		this.backgroundImg = new ImageIcon("/resources/lotrMap.png").getImage();
		this.setLayout(new GridBagLayout());
		this.setUp();
		this.addEventHandlers();
	}
	
	private void setUp() throws IOException {
		salirCarcel = new JButton("Salir Carcel");
		this.add(salirCarcel);
		Integer i, index;
		GridBagConstraints c = new GridBagConstraints();

		casillas = new ArrayList<BotonCasilla>();
		
		for(i=0; i<11; i++) { // Primera fila
			casillas.add(new BotonCasilla("/resources/"+i.toString()+".jpg",Valor.casillas.get(i)));
			c.gridx = 200*i;
			c.gridy = 0;
			this.add(casillas.get(i), c);
		}

		for(i=0; i<10; i++) { // Primera columna
			index = 11+i;
			casillas.add(new BotonCasilla("/resources/"+index.toString()+".jpg",Valor.casillas.get(index)));
			c.gridx = 2000;
			c.gridy = 200*(i+1);
			this.add(casillas.get(index), c);
		}
		for(i=0; i<10; i++) { // Segunda fila
			index = 21+i;
			casillas.add(new BotonCasilla("/resources/"+index.toString()+".jpg",Valor.casillas.get(index)));
			c.gridx = 2000 - 200*(i+1);
			c.gridy = 2000;
			this.add(casillas.get(index), c);
		}
		for(i=0; i<9; i++) { // Segunda columna
			index = 31+i;
			casillas.add(new BotonCasilla("/resources/"+index.toString()+".jpg",Valor.casillas.get(index)));
			c.gridx = 0;
			c.gridy = 2000 - 200*(i+1);
			this.add(casillas.get(index), c);
		}

	}
	
	private void addEventHandlers() {
		for(BotonCasilla bc: casillas) {
			bc.addActionListener(new GestionInfo(this.interfaz.getPanelInfo()));
		}
		salirCarcel.addActionListener(new GestionSalirCarcel(this.interfaz));
	}
	
	public void cargarFichasIniciales(){
		GridBagConstraints c = new GridBagConstraints();
		Integer nJugadores = LoginPanel.getNJugadores();
		
		for(int k=0; k<nJugadores; k++) {
			casillas.get(0).addFicha(interfaz.getFichas().get(k), k);
		}
		casillas.get(0).actualizarCasilla();

	}

	public void actualizarFicha(int posInicial, Jugador jugador){
		casillas.get(posInicial).deleteFicha(this.interfaz.getJuego().getTurnosJugadores().indexOf(jugador.getNombre()));
		casillas.get(posInicial).actualizarCasilla();
		//Añadiendo una ficha estratégicamente
		casillas.get(jugador.getAvatar().getCasilla().getPosicion()).addFicha(jugador.getAvatar().getFicha(),this.interfaz.getJuego().getTurnosJugadores().indexOf(jugador.getNombre()));
		casillas.get(jugador.getAvatar().getCasilla().getPosicion()).actualizarCasilla();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.backgroundImg, 50, 50, null);
	}

}
