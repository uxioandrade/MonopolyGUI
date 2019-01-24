package interfazgrafica;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import monopoly.contenido.Jugador;
import java.util.ArrayList;
import monopoly.plataforma.Juego;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class InterfazGrafica extends JFrame{
	private PanelTablero panelTablero;
	private PanelInfo info;
	private PanelMenu menu;
	private PanelBotones botones;
	private PanelJugadores panelJugadores;
	private PanelTexto texto;
	private ArrayList<Jugador> jugadores;
	private ArrayList<BufferedImage> fichas;
	private Juego juego;
	private PanelHipotecar panelHipotecar;
	private PanelEdificar panelEdificar;
	private  PanelVender panelVender;
	private PanelTratos panelTratos;

	static final Integer MAX_CASILLAS = 40;
	static final Integer MAX_FICHAS = 6;
	static final Integer ficha_W = 20;
	static final Integer ficha_H = 18;

	public InterfazGrafica(Juego juego) throws IOException {
		//this.setBackground(Color.cyan);
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(1300, 1000));
		fichas = new ArrayList<BufferedImage>();
		this.juego = juego;
		this.setUp();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.addEventHandlers();
	}
	
	private void setUp() throws IOException {
		GridBagConstraints c = new GridBagConstraints();

		for(Integer i=0; i<6; i++) { // Fichas
			BufferedImage img = ImageIO.read(getClass().getResource("/resources/ficha"+i.toString()+".jpg"));
			img = InterfazGrafica.resize(img,  ficha_H,  ficha_W);
			fichas.add(img);
		}

		// Hay que crear PanelInfo antes que el panelTablero
		// NullPointerException otherwise
		info = new PanelInfo(this);
		c.gridx = 1500;
		c.gridy = 0;
		//c.gridheight = 2;
		//c.gridwidth = 1;
		this.add(info, c);
		
		panelTablero = new PanelTablero(this);
		c.gridx = 500;
		c.gridy = 0;
		//c.gridheight = 2;
		//c.gridwidth = 2;
		this.add(panelTablero, c);
		
		menu = new PanelMenu(this);
		c.gridx = 0;
		c.gridy = 2000;
		//c.gridheight = 1;
		//c.gridwidth = 2;
		this.add(menu, c);
		
		botones = new PanelBotones(this);
		c.gridx = 1500;
		c.gridy = 2000;
		//c.gridheight = 1;
		//c.gridwidth = 1;
		this.add(botones, c);

		texto = new PanelTexto();
		c.gridx = 0;
		c.gridy = 0;
		this.add(texto, c);
		this.juego.setPanelTexto(texto);
		this.juego.getTablero().setPanelTexto(texto);
		this.juego.getConsola().setPanelTexto(texto);

		this.panelJugadores = new PanelJugadores(this);
		c.gridx = 500;
		c.gridy = 2000;
		this.add(this.panelJugadores,c);

		this.panelHipotecar = new PanelHipotecar(this,this.juego.getJugadorActual());
		this.panelHipotecar.setVisible(false);
		c.gridx=1500;
		c.gridy=0;
		this.add(this.panelHipotecar,c);

		this.panelVender = new PanelVender(this,this.juego.getJugadorActual());
		this.panelVender.setVisible(false);
		c.gridx=1500;
		c.gridy=0;
		this.add(this.panelVender,c);

		this.panelEdificar = new PanelEdificar(this);
		this.panelEdificar.setVisible(false);
		c.gridx=1500;
		c.gridy=0;
		this.add(this.panelEdificar,c);

		this.panelTratos = new PanelTratos(this);
		this.panelTratos.setVisible(false);
		c.gridx=1500;
		c.gridy=0;
		this.add(this.panelTratos,c);
	}
	
	public PanelTablero getPanelTablero() {
		return panelTablero;
	}
	
	public PanelInfo getPanelInfo() {
		return info;
	}
	
	public PanelMenu getPanelMenu() {
		return menu;
	}

	public PanelEdificar getPanelEdificar(){
		return this.panelEdificar;
	}

	public PanelHipotecar getPanelHipotecar(){return this.panelHipotecar;}

	public PanelVender getPanelVender(){ return this.panelVender;}

	public PanelTexto getPanelTexto(){
		return this.texto;
	}

	public PanelTratos getPanelTratos(){
		return this.panelTratos;
	}

	public PanelBotones getPanelBotones() {
		return botones;
	}

	public ArrayList<BufferedImage> getFichas(){
		return this.fichas;
	}

	public Juego getJuego() {
		return juego;
	}

	public PanelJugadores getPanelJugadores(){
		return this.panelJugadores;
	}

	public static BufferedImage resize(BufferedImage img, int h, int w) {
		Image tmp = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}
	
	public void cargarFichasIniciales() throws IOException {
		panelTablero.cargarFichasIniciales();
	}
	
	public void cargarJugadoresIniciales(ArrayList<String> nombres, ArrayList<String> avatares) throws IOException{
		for(int i=0; i<nombres.size(); i++){
			Jugador jugadorIntroducir = new Jugador(nombres.get(i), avatares.get(i), this.juego.getTablero(),fichas.get(i));
			//Se añade los jugadores al HashMap de tableros
			this.juego.getTablero().addJugadores(jugadorIntroducir);
			this.juego.getTurnosJugadores().add(jugadorIntroducir.getNombre());
		}
		this.juego.setJugadorActual(this.juego.getTablero().getJugadores().get(this.juego.getTurnosJugadores().get(0)));
		this.panelJugadores.cargarJugadoresIniciales();
	}

}
