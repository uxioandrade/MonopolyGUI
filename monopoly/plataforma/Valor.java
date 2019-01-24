package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import monopoly.contenido.*;
import monopoly.contenido.cartas.Carta;
import monopoly.contenido.cartas.CartaMovimientoCaja;
import monopoly.contenido.cartas.CartaMovimientoSuerte;
import monopoly.contenido.cartas.CartaPagarSuerte;
import monopoly.contenido.cartas.CartaPagarCaja;
import monopoly.contenido.casillas.*;
import monopoly.contenido.casillas.accion.Accion;
import monopoly.contenido.casillas.accion.CasillasCarta;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.casillas.propiedades.Servicio;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.contenido.casillas.propiedades.Transporte;

import java.text.DecimalFormat;


public class Valor {

    private static HashMap<String,Grupo> grupos;
    private static double dineroAcumulado;

    private int posicionSalida;

    private Integer[] biyeccion;

    //Colores
    public static final String ANSI_NEGRO = "\u001b[30m";
    public static final String ANSI_DARK_GREY = "\u001b[37m";
    public static final String ANSI_ROJO = "\u001b[31m";
    public static final String ANSI_VERDE = "\u001b[32m";
    public static final String ANSI_NARANJA = "\u001b[33m";
    public static final String ANSI_AMARILLO = "\033[0;33m";
    public static final String ANSI_AZUL = "\u001b[34m";
    public static final String ANSI_ROSA = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_GRIS = "\u001b[37m";
    
    public double PRECIO_G1 = 120;

    public static DecimalFormat df2 = new DecimalFormat(".##");
    
    public final ArrayList<Carta> cartasSuerte = new ArrayList<>(Arrays.asList(
            new CartaMovimientoCaja(25,true,0,"Ve a la EstaciÃ³n 3 y coge un avioÌ�n. Si pasas por la casilla de Salida, cobra ", this),
            new CartaMovimientoCaja(31,false,1,"Decides hacer un viaje de placer. Avanza hasta Pelennor Fields", this),
            new CartaPagarCaja(-500,true,"Vendes tu billete de avioÌ�n para Argonath en una subasta por Internet. Cobra 500â‚¬.", this),
            new CartaMovimientoCaja(26,true,1,"Ve a Isengard Caverns. Si pasas por la casilla de Salida, cobra ", this),
            new CartaMovimientoCaja(30,false,0,"Los acreedores te persiguen por impago. Ve a la caÌ�rcel. Ve directamente sin pasar por la casilla de salida y sin cobrar", this),
            new CartaPagarCaja(-10000,true,"Â¡Has ganado el bote de la loteriÌ�a! Recibe 10000â‚¬.", this),
            new CartaPagarCaja(150,true,"Paga 150â‚¬ por la matrÃ­cula del colegio privado", this),
            new CartaPagarCaja(0,true,"El aumento del impuesto sobre bienes inmbuebles afecta a todas tus propiedades. Paga 40â‚¬ por casa, 115â‚¬ por hotel, 200â‚¬ por piscina y 75â‚¬ por pista de deportes", this),
            new CartaMovimientoCaja(32,true,1,"Ve a Osgiliath. Si pasas por la casilla de Salida, cobra ", this),
            new CartaPagarCaja(250,false,"Has sido elegido presidente de la junta directiva. Paga a cada jugador 250â‚¬", this),
            new CartaMovimientoCaja(-1,false,1,"!Hora punta de trÃ¡fico! Retrocede tres casillas", this),
            new CartaPagarCaja(150,true,"Te multan por usar el mÃ³vil mientras conduces. Paga 150â‚¬", this),
            new CartaPagarCaja(-1500,true,"Beneficio por la venta de tus acciones. Recibes 1500â‚¬", this),
            new CartaMovimientoCaja(-2,false,2,"Avanza hasta la casilla de transporte mÃ¡s cercana. Si no tiene dueÃ±o, puedes comprÃ¡rsela a la banca. Si tiene dueÃ±o, paga el doble de la operaciÃ³n indicada", this)
    ));

    public final ArrayList<Carta> cartasCajaComunidad = new ArrayList<>(Arrays.asList(
            new CartaPagarSuerte(500,true,"Paga 500 por un fin de semana en un balneario de 5 estrellas", this),
            new CartaMovimientoSuerte(30,false,0,"Te investigan por fraude de identidad. Ve a la CÃ¡rcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los ", this),
            new CartaMovimientoSuerte(0,true,0,"ColÃ³cate en la casilla de Salida. Cobra ", this),
            new CartaPagarSuerte(-2000,true,"Tu compaÃ±Ã­a de Internet obtiene beneficios. Recibe 2000â‚¬.", this),
            new CartaPagarSuerte(1000,true,"Paga 1000â‚¬ por invitar a todos tus amigos a un viaje a Gondor", this),
            new CartaPagarSuerte(-500,true,"DevoluciÃ³n de Hacienda. Cobra 500â‚¬", this),
            new CartaMovimientoSuerte(1,false,1,"Retrocede hasta Bag End para comprar antigÃ¼edades exÃ³ticas", this),
            new CartaPagarSuerte(200,false,"Alquilas a todos tus amigos una villa en The Shire durante una semana. Paga 200â‚¬ a cada jugador.", this),
            new CartaPagarSuerte(-1000,true,"Recibe 1000â‚¬ de beneficios por alquilar los servicios de tu jet privado.", this),
            new CartaMovimientoSuerte(21,true, 1,"Ve a Fall of Rauros a disfrutar de las vistas. Si pasas por la casilla de Salida, cobra ", this)
    ));
    
    private ArrayList<Casilla> casillasStandard = new ArrayList<>(Arrays.asList(
            new Especiales("Salida",0, this),
            new Solar("Bag End",1),
            new CasillasCarta("Caja1",2, cartasCajaComunidad),
            new Solar("Farmer Maggot's",3),
            new Impuesto("Impuesto1",4),
            new Transporte("Estacion1",5, this),
            new Solar("Chetwood Forest",6),
            new CasillasCarta("Suerte1",7,cartasSuerte),
            new Solar("Weathertop",8),
            new Solar("Ford of Bruinen",9),
            new Especiales("Carcel",10, this),
            new Solar("Rivendell",11),
            new Servicio("Servicio1",12, this),
            new Solar("Council of Elrond",13),
            new Solar("Caras Galadon",14),
            new Transporte("Estacion2",15, this),
            new Solar("Fall of Rauros",16),
            new CasillasCarta("Caja2",17, cartasCajaComunidad),
            new Solar("Nen Hithoel",18),
            new Solar("Argonath",19),
            new Accion("Parking",20),
            new Solar("Golden Hall",21),
            new CasillasCarta("Suerte2",22,cartasSuerte),
            new Solar("Edoras",23),
            new Solar("Helm's Deep",24),
            new Transporte("Estacion3",25, this),
            new Solar("Isengard Caverns",26),
            new Solar("Fords of Isen",27),
            new Servicio("Servicio2",28, this),
            new Solar("Tower of Orthanc",29),
            new Especiales("VeCarcel",30, this),
            new Solar("Pelennor Fields",31),
            new Solar("Osgiliath",32),
            new CasillasCarta("Caja3",33, cartasCajaComunidad),
            new Solar("Minas Tirith",34),
            new Transporte("Estacion4",35, this),
            new CasillasCarta("Suerte3",36,cartasSuerte),
            new Solar("Barad-dÃ»r",37),
            new Impuesto("Impuesto2",38),
            new Solar("Mt. Doom",39)
    ));
    
    private ArrayList<Casilla> casillas;
    
    public void crearGrupos(int[] permutacionCasillas, double[] precios) {
        grupos = new HashMap<>();
        /*
        if(precios[0] < 0) {
        	grupos.put("Marron", new Grupo("Marron", ANSI_DARK_GREY, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(1), casillasStandard.get(3))), PRECIO_G1));
	        grupos.put("Cyan", new Grupo("Cyan", ANSI_CYAN, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(6), casillasStandard.get(8), casillasStandard.get(9))), PRECIO_G1 * 1.3));
	        grupos.put("Rosa", new Grupo("Rosa", ANSI_ROSA, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(11), casillasStandard.get(13), casillasStandard.get(14))), grupos.get("Cyan").getPrecio() * 1.3));
	        grupos.put("Amarillo", new Grupo("Amarillo", ANSI_NARANJA, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(16), casillasStandard.get(18), casillasStandard.get(19))), grupos.get("Rosa").getPrecio() * 1.3));
	        grupos.put("Rojo", new Grupo("Rojo", ANSI_ROJO, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(21), casillasStandard.get(23), casillasStandard.get(24))), grupos.get("Amarillo").getPrecio() * 1.3));
	        grupos.put("Naranja", new Grupo("Naranja", ANSI_AMARILLO, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(26), casillasStandard.get(27), casillasStandard.get(29))), grupos.get("Rojo").getPrecio() * 1.3));
	        grupos.put("Verde", new Grupo("Verde", ANSI_VERDE, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(31), casillasStandard.get(32), casillasStandard.get(34))), grupos.get("Naranja").getPrecio() * 1.3));
	        grupos.put("Azul", new Grupo("Azul", ANSI_AZUL, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(37), casillasStandard.get(39))), grupos.get("Verde").getPrecio() * 1.3));
	        setValoresEspeciales();
        }else {
        	PRECIO_G1 = precios[0];
        	grupos.put("Marron", new Grupo("Marron", ANSI_DARK_GREY, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(1), casillasStandard.get(3))), precios[0]));
	        grupos.put("Cyan", new Grupo("Cyan", ANSI_CYAN, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(6), casillasStandard.get(8), casillasStandard.get(9))), precios[1]));
	        grupos.put("Rosa", new Grupo("Rosa", ANSI_ROSA, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(11), casillasStandard.get(13), casillasStandard.get(14))), precios[2]));
	        grupos.put("Amarillo", new Grupo("Amarillo", ANSI_NARANJA, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(16), casillasStandard.get(18), casillasStandard.get(19))), precios[3]));
	        grupos.put("Rojo", new Grupo("Rojo", ANSI_ROJO, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(21), casillasStandard.get(23), casillasStandard.get(24))), precios[4]));
	        grupos.put("Naranja", new Grupo("Naranja", ANSI_AMARILLO, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(26), casillasStandard.get(27), casillasStandard.get(29))), precios[5]));
	        grupos.put("Verde", new Grupo("Verde", ANSI_VERDE, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(31), casillasStandard.get(32), casillasStandard.get(34))), precios[6]));
	        grupos.put("Azul", new Grupo("Azul", ANSI_AZUL, new ArrayList<Casilla>(Arrays.asList(casillasStandard.get(37), casillasStandard.get(39))), precios[7]));
	        setValoresEspeciales();
        }*/
        if(precios[0] < 0) {
        	grupos.put("Marron", new Grupo("Marron", ANSI_DARK_GREY, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[1]-1), casillas.get(permutacionCasillas[3]-1))), PRECIO_G1));
	        grupos.put("Cyan", new Grupo("Cyan", ANSI_CYAN, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[6]-1), casillas.get(permutacionCasillas[8]-1), casillas.get(permutacionCasillas[9]-1))), PRECIO_G1 * 1.3));
	        grupos.put("Rosa", new Grupo("Rosa", ANSI_ROSA, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[11]-1), casillas.get(permutacionCasillas[13]-1), casillas.get(permutacionCasillas[14]-1))), grupos.get("Cyan").getPrecio() * 1.3));
	        grupos.put("Amarillo", new Grupo("Amarillo", ANSI_NARANJA, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[16]-1), casillas.get(permutacionCasillas[18]-1), casillas.get(permutacionCasillas[19]-1))), grupos.get("Rosa").getPrecio() * 1.3));
	        grupos.put("Rojo", new Grupo("Rojo", ANSI_ROJO, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[21]-1), casillas.get(permutacionCasillas[23]-1), casillas.get(permutacionCasillas[24]-1))), grupos.get("Amarillo").getPrecio() * 1.3));
	        grupos.put("Naranja", new Grupo("Naranja", ANSI_AMARILLO, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[26]-1), casillas.get(permutacionCasillas[27]-1), casillas.get(permutacionCasillas[29]-1))), grupos.get("Rojo").getPrecio() * 1.3));
	        grupos.put("Verde", new Grupo("Verde", ANSI_VERDE, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[31]-1), casillas.get(permutacionCasillas[32]-1), casillas.get(permutacionCasillas[34]-1))), grupos.get("Naranja").getPrecio() * 1.3));
	        grupos.put("Azul", new Grupo("Azul", ANSI_AZUL, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[37]-1), casillas.get(permutacionCasillas[39]-1))), grupos.get("Verde").getPrecio() * 1.3));
	        setValoresEspeciales();
        }else {
        	grupos.put("Marron", new Grupo("Marron", ANSI_DARK_GREY, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[1]-1), casillas.get(permutacionCasillas[3]-1))), precios[0]));
	        grupos.put("Cyan", new Grupo("Cyan", ANSI_CYAN, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[6]-1), casillas.get(permutacionCasillas[8]-1), casillas.get(permutacionCasillas[9]-1))), precios[1]));
	        grupos.put("Rosa", new Grupo("Rosa", ANSI_ROSA, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[11]-1), casillas.get(permutacionCasillas[13]-1), casillas.get(permutacionCasillas[14]-1))), precios[2]));
	        grupos.put("Amarillo", new Grupo("Amarillo", ANSI_NARANJA, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[16]-1), casillas.get(permutacionCasillas[18]-1), casillas.get(permutacionCasillas[19]-1))), precios[3]));
	        grupos.put("Rojo", new Grupo("Rojo", ANSI_ROJO, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[21]-1), casillas.get(permutacionCasillas[23]-1), casillas.get(permutacionCasillas[24]-1))), precios[4]));
	        grupos.put("Naranja", new Grupo("Naranja", ANSI_AMARILLO, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[26]-1), casillas.get(permutacionCasillas[27]-1), casillas.get(permutacionCasillas[29]-1))), precios[5]));
	        grupos.put("Verde", new Grupo("Verde", ANSI_VERDE, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[31]-1), casillas.get(permutacionCasillas[32]-1), casillas.get(permutacionCasillas[34]-1))), precios[6]));
	        grupos.put("Azul", new Grupo("Azul", ANSI_AZUL, new ArrayList<Casilla>(Arrays.asList(casillas.get(permutacionCasillas[37]-1), casillas.get(permutacionCasillas[39]-1))), precios[7]));
	        setValoresEspeciales();
        }
        
    }
    
    
    private Casilla salida;
    private Casilla casilla4;
    private Casilla casilla12;
    private Casilla casilla28;
    private Casilla casilla38;
    private Casilla casilla5;
    private Casilla casilla15;
    private Casilla casilla25;
    private Casilla casilla35;
    
    public void reordenarCasillas(int[] permutacionCasillas) {
    	casillas = new ArrayList<Casilla>();
    	Casilla[] aux = new Casilla[40];
    	this.biyeccion = new Integer[40];
    	for(int i=0; i<40; i++) {
    		this.biyeccion[permutacionCasillas[i]-1] = i;
    		switch(i) {
    			case 0:
    				salida = new Especiales("Salida", permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = salida;
    				this.posicionSalida = permutacionCasillas[i]-1;
    				break;
    			case 1:
    				aux[permutacionCasillas[i]-1] = new Solar("Bag End", permutacionCasillas[i]-1);
    				break;
    			case 2:
    				aux[permutacionCasillas[i]-1] = new CasillasCarta("Caja1", permutacionCasillas[i]-1, cartasCajaComunidad);
    				break;
    			case 3:
    				aux[permutacionCasillas[i]-1] = new Solar("Farmer Maggot's", permutacionCasillas[i]-1);
    				break;
    			case 4:
    				casilla4 = new Impuesto("Impuesto1", permutacionCasillas[i]-1);
    				aux[permutacionCasillas[i]-1] = casilla4;
    				break;
    			case 5:
    				casilla5 = new Transporte("Estacion1", permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = casilla5;
    				break;
    			case 6:
    				aux[permutacionCasillas[i]-1] = new Solar("Chetwood Forest", permutacionCasillas[i]-1);
    				break;
    			case 7:
    				aux[permutacionCasillas[i]-1] = new CasillasCarta("Suerte1", permutacionCasillas[i]-1, cartasSuerte);
    				break;
    			case 8:
    				aux[permutacionCasillas[i]-1] = new Solar("Weathertop", permutacionCasillas[i]-1);
    				break;
    			case 9:
    				aux[permutacionCasillas[i]-1] = new Solar("Ford of Bruinen", permutacionCasillas[i]-1);
    				break;
    			case 10:
    				aux[permutacionCasillas[i]-1] = new Especiales("Carcel", permutacionCasillas[i]-1, this);
    				break;
    			case 11:
    				aux[permutacionCasillas[i]-1] = new Solar("Rivendell", permutacionCasillas[i]-1);
    				break;
    			case 12:
    				casilla12 = new Servicio("Servicio1", permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = casilla12;
    				break;
    			case 13:
    				aux[permutacionCasillas[i]-1] = new Solar("Council of Elrond", permutacionCasillas[i]-1);
    				break;
    			case 14:
    				aux[permutacionCasillas[i]-1] = new Solar("Caras Galagon", permutacionCasillas[i]-1);
    				break;
    			case 15:
    				casilla15 = new Transporte("Estacion2", permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = casilla15;
    				break;
    			case 16:
    				aux[permutacionCasillas[i]-1] = new Solar("Fall of Rauros", permutacionCasillas[i]-1);
    				break;
    			case 17:
    				aux[permutacionCasillas[i]-1] = new CasillasCarta("Caja2", permutacionCasillas[i]-1, cartasCajaComunidad);
    				break;
    			case 18:
    				aux[permutacionCasillas[i]-1] = new Solar("Nen Hithoel", permutacionCasillas[i]-1);
    				break;
    			case 19:
    				aux[permutacionCasillas[i]-1] = new Solar("Argonath", permutacionCasillas[i]-1);
    				break;
    			case 20:
    				aux[permutacionCasillas[i]-1] = new Accion("Parking", permutacionCasillas[i]-1);
    				break;
    			case 21:
    				aux[permutacionCasillas[i]-1] = new Solar("Golden Hall", permutacionCasillas[i]-1);
    				break;
    			case 22:
    				aux[permutacionCasillas[i]-1] = new CasillasCarta("Suerte2", permutacionCasillas[i]-1, cartasSuerte);
    				break;
    			case 23:aux[permutacionCasillas[i]-1] = new Solar("Edoras", permutacionCasillas[i]-1);
    				break;
    			case 24:
    				aux[permutacionCasillas[i]-1] = new Solar("Helm's Deep", permutacionCasillas[i]-1);
    				break;
    			case 25:
    				casilla25 = new Transporte("Estacion3", permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = casilla25;
    				break;
    			case 26:
    				aux[permutacionCasillas[i]-1] = new Solar("Isengard Caverns", permutacionCasillas[i]-1);
    				break;
    			case 27:
    				aux[permutacionCasillas[i]-1] = new Solar("Fords of Isen", permutacionCasillas[i]-1);
    				break;
    			case 28:
    				casilla28 = new Servicio("Servicio2",permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = casilla28;
    				break;
    			case 29:
    				aux[permutacionCasillas[i]-1] = new Solar("Tower of Orthanc", permutacionCasillas[i]-1);
    				break;
    			case 30:
    				aux[permutacionCasillas[i]-1] = new Especiales("VeCarcel",permutacionCasillas[i]-1, this);
    				break;
    			case 31:
    				aux[permutacionCasillas[i]-1] = new Solar("Pelennor Fields", permutacionCasillas[i]-1);;
    				break;
    			case 32:
    				aux[permutacionCasillas[i]-1] = new Solar("Osgiliath", permutacionCasillas[i]-1);
    				break;
    			case 33:
    				aux[permutacionCasillas[i]-1] = new CasillasCarta("Caja3",permutacionCasillas[i]-1, cartasCajaComunidad);
    				break;
    			case 34:
    				aux[permutacionCasillas[i]-1] = new Solar("Minas Tirith", permutacionCasillas[i]-1);
    				break;
    			case 35:
    				casilla35 = new Transporte("Estacion4",permutacionCasillas[i]-1, this);
    				aux[permutacionCasillas[i]-1] = casilla35;
    				break;
    			case 36:
    				aux[permutacionCasillas[i]-1] = new CasillasCarta("Suerte3",permutacionCasillas[i]-1, cartasSuerte);
    				break;
    			case 37:
    				aux[permutacionCasillas[i]-1] = new Solar("Barad-Dur", permutacionCasillas[i]-1);
    				break;
    			case 38:
    				casilla38 = new Impuesto("Impuesto2",permutacionCasillas[i]-1);
    				aux[permutacionCasillas[i]-1] = casilla38;
    				break;
    			case 39:
    				aux[permutacionCasillas[i]-1] = new Solar("Mt. Doom", permutacionCasillas[i]-1);
    				break;
    			default:
    				System.out.println("ERROR METODO REORDENAR CASILLAS EN valor.java");
    				System.out.println("===> "+permutacionCasillas[i]+" "+i);
    				return;
    		}
    	}
    	for(int j = 0; j < 40; j++) {
			casillas.add(aux[j]);
		}
    }
    
    public ArrayList<Casilla> getCasillas(){
    	return casillas;
    }
    
    public Casilla getSalida() {
    	return salida;
    }

    public ArrayList<Propiedades> getComprables(){
        ArrayList<Propiedades> comprables = new ArrayList<>();
        for(Casilla c: casillas){
            if(c instanceof Propiedades)
                comprables.add((Propiedades)c);
        }
        return comprables;
    }

    private double precioTotalSolares = PRECIO_G1*23.85769;
    public final double FORTUNA_INICIAL = precioTotalSolares /3.0;
    private double dineroVuelta = precioTotalSolares /22;
    private double dineroSalirCarcel = 0.25* dineroVuelta;
    private double precioServicio = 0.75*dineroVuelta;

    private static int edificios=1; //Contador que almacena el id de todos los edificios construidos
    private static int tratos=1; //Contador que almacena el id de todos los tratos creados

    //Multiplicadores
    public static final double MULTIPLICADOR_INICIAL_CASA = 0.60;
    public static final double MULTIPLICADOR_INICIAL_HOTEL = 0.60;
    public static final double MULTIPLICADOR_INICIAL_PISTA = 1.25;
    public static final double MULTIPLICADOR_INICIAL_PISCINA = 1.25;
    public static final double MULTIPLICADOR_HIPOTECA_CASILLA = 0.5;
    public static final double MULTIPLICADOR_HIPOTECA_EDIFICIO= 0.5;
    public static final double MULTIPLICADOR_CARGO_DESHIPOTECAR= 1.1;
    
    private void setValoresEspeciales(){
    	((Transporte)casilla5).setPrecio(dineroVuelta);
    	((Transporte)casilla15).setPrecio(dineroVuelta);
    	((Transporte)casilla25).setPrecio(dineroVuelta);
    	((Transporte)casilla35).setPrecio(dineroVuelta);
        ((Servicio)casilla12).setPrecio(precioServicio);
        ((Servicio)casilla28).setPrecio(precioServicio);
        ((Impuesto)casilla4).setApagar(dineroVuelta/2);
        ((Impuesto)casilla38).setApagar(dineroVuelta);
    }
    public static void setDineroAcumulado(double valor){
        if(valor >= 0)
            dineroAcumulado = valor;
    }

    public static double getDineroAcumulado(){
        return dineroAcumulado;
    }

    public static void actualizarDineroAcumulado(double valor){
        if(valor > 0)
            dineroAcumulado += valor;
    }

    public static HashMap<String,Grupo> getGrupos(){
        return grupos;
    }

    public double getPrecioTotalSolares(){
        return precioTotalSolares;
    }

    public double getDineroVuelta(){
        return dineroVuelta;
    }

    public double getDineroSalirCarcel(){
        return dineroSalirCarcel;
    }

    public static int getEdificios(){return edificios;}
    public static void incrementarEdificios(){edificios++;}

    public static int getTratos(){return tratos;}
    public static void incrementarTratos(){tratos++;}

    public void actualizarVuelta(){
        Iterator<Grupo> grup_i = Valor.getGrupos().values().iterator();
        while(grup_i.hasNext()) {
            Grupo grup = grup_i.next();
            grup.actualizarPrecio(0.05);
        }
        PRECIO_G1 += PRECIO_G1*0.05;
        precioTotalSolares = PRECIO_G1*23.85769;
        dineroVuelta = precioTotalSolares /22;
        dineroSalirCarcel = 0.25* dineroVuelta;
        precioServicio = dineroVuelta*0.75;
        setValoresEspeciales();
    }

    public void setPosiscionCartas(){
    	for(Carta c: this.cartasCajaComunidad){
    		if(c instanceof CartaMovimientoCaja) {
				if(((CartaMovimientoCaja)c).getPosicion() > 0)
					((CartaMovimientoCaja) c).setPosicion(this.biyeccion[((CartaMovimientoCaja) c).getPosicion()]);
			}
		}

		for(Carta c: this.cartasSuerte){
			if(c instanceof CartaMovimientoSuerte) {
				if (((CartaMovimientoSuerte) c).getPosicion() > 0)
					((CartaMovimientoSuerte) c).setPosicion(this.biyeccion[((CartaMovimientoSuerte) c).getPosicion()]);
			}
		}
	}

    public int getPosicionSalida(){
    	return this.posicionSalida;
	}

	public Integer[] getBiyeccion() {
		return biyeccion;
	}
}
