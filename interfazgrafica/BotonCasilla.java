package interfazgrafica;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import monopoly.contenido.casillas.*;

@SuppressWarnings("serial")
public class BotonCasilla extends JButton{
	static Integer IMG_WIDTH = 45;
	static Integer IMG_HEIGHT = 55;
	static Integer MAX_PLAYERS = 6;
	
	private String url;
	private BufferedImage[] fichasCasilla;
	private Integer nFichas;
	private Casilla casilla;
	
	
	public BotonCasilla(String url, Casilla casilla) throws IOException {
		this.url = url;
		this.fichasCasilla = new BufferedImage[MAX_PLAYERS];
		for(int i=0; i<MAX_PLAYERS; i++) {
			this.fichasCasilla[i] = null;
		}
		this.nFichas = 0;
		this.casilla = casilla;
		this.setUp();
	}

	public Casilla getCasilla(){return this.casilla;}
	
	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

	private void setUp() throws IOException{
		BufferedImage img = ImageIO.read(getClass().getResource(this.url));
		img = InterfazGrafica.resize(img, IMG_HEIGHT, IMG_WIDTH);
		this.setIcon(new ImageIcon(img));
		this.setPreferredSize(new Dimension(IMG_WIDTH + 1, IMG_HEIGHT + 1));
	}
	
	public String getURL() {
		return url;
	}
	
	public void addFicha(BufferedImage ficha, Integer jugadorID) {
		this.fichasCasilla[jugadorID] = ficha;
		this.nFichas++;
	}
	
	public void deleteFicha(Integer jugadorID) {
		this.fichasCasilla[jugadorID] = null;
		this.nFichas--;
	}
	
	public void actualizarCasilla(){
		BufferedImage bg = null;
		try{
			bg = ImageIO.read(getClass().getResource(this.url));
		}catch(IOException ex) {
			ex.getMessage();
		}
		bg = InterfazGrafica.resize(bg,  IMG_HEIGHT,  IMG_WIDTH);
		Graphics2D g = bg.createGraphics();
		int x=0, y=0;
		for(int i=0; i<MAX_PLAYERS; i++) {
			if(fichasCasilla[i] != null) {
				g.drawImage(fichasCasilla[i], x%40, y%54, null);
				x+=20;
				y+=18;
			}
		}
		g.dispose();
		this.setIcon(new ImageIcon(bg));
		
	}
	

	public void setFicha(BufferedImage ficha, Integer x, Integer y) throws IOException {
		BufferedImage img = ImageIO.read(getClass().getResource(this.url));
		img = InterfazGrafica.resize(img, IMG_HEIGHT, IMG_WIDTH);
		Graphics2D g = img.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.drawImage(ficha, x, y, null);
		g.dispose();
		this.setIcon(new ImageIcon(img));
	}
	
	/*
	public Casilla getCasilla() {
		return casilla;
	}*/
}
