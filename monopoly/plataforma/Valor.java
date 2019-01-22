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

    public static DecimalFormat df2 = new DecimalFormat(".##");

    public static void crearGrupos() {
        grupos = new HashMap<>();
        //Inicializar hashmap de grupos
        grupos.put("Marron", new Grupo("Marron", ANSI_DARK_GREY, new ArrayList<Casilla>(Arrays.asList(casillas.get(1), casillas.get(3))), PRECIO_G1));
        grupos.put("Cyan", new Grupo("Cyan", ANSI_CYAN, new ArrayList<Casilla>(Arrays.asList(casillas.get(6), casillas.get(8), casillas.get(9))), PRECIO_G1 * 1.3));
        grupos.put("Rosa", new Grupo("Rosa", ANSI_ROSA, new ArrayList<Casilla>(Arrays.asList(casillas.get(11), casillas.get(13), casillas.get(14))), grupos.get("Cyan").getPrecio() * 1.3));
        grupos.put("Amarillo", new Grupo("Amarillo", ANSI_NARANJA, new ArrayList<Casilla>(Arrays.asList(casillas.get(16), casillas.get(18), casillas.get(19))), grupos.get("Rosa").getPrecio() * 1.3));
        grupos.put("Rojo", new Grupo("Rojo", ANSI_ROJO, new ArrayList<Casilla>(Arrays.asList(casillas.get(21), casillas.get(23), casillas.get(24))), grupos.get("Amarillo").getPrecio() * 1.3));
        grupos.put("Naranja", new Grupo("Naranja", ANSI_AMARILLO, new ArrayList<Casilla>(Arrays.asList(casillas.get(26), casillas.get(27), casillas.get(29))), grupos.get("Rojo").getPrecio() * 1.3));
        grupos.put("Verde", new Grupo("Verde", ANSI_VERDE, new ArrayList<Casilla>(Arrays.asList(casillas.get(31), casillas.get(32), casillas.get(34))), grupos.get("Naranja").getPrecio() * 1.3));
        grupos.put("Azul", new Grupo("Azul", ANSI_AZUL, new ArrayList<Casilla>(Arrays.asList(casillas.get(37), casillas.get(39))), grupos.get("Verde").getPrecio() * 1.3));
        setValoresEspeciales();
    }

    public final static ArrayList<Carta> cartasSuerte = new ArrayList<>(Arrays.asList(
            new CartaMovimientoCaja(25,true,0,"Ve a la Estación 3 y coge un avión. Si pasas por la casilla de Salida, cobra "),
            new CartaMovimientoCaja(31,false,1,"Decides hacer un viaje de placer. Avanza hasta Pelennor Fields"),
            new CartaPagarCaja(-500,true,"Vendes tu billete de avión para Argonath en una subasta por Internet. Cobra 500€."),
            new CartaMovimientoCaja(26,true,1,"Ve a Isengard Caverns. Si pasas por la casilla de Salida, cobra "),
            new CartaMovimientoCaja(30,false,0,"Los acreedores te persiguen por impago. Ve a la cárcel. Ve directamente sin pasar por la casilla de salida y sin cobrar"),
            new CartaPagarCaja(-10000,true,"¡Has ganado el bote de la lotería! Recibe 10000€."),
            new CartaPagarCaja(150,true,"Paga 150€ por la matrícula del colegio privado"),
            new CartaPagarCaja(0,true,"El aumento del impuesto sobre bienes inmbuebles afecta a todas tus propiedades. Paga 40€ por casa, 115€ por hotel, 200€ por piscina y 75€ por pista de deportes"),
            new CartaMovimientoCaja(32,true,1,"Ve a Osgiliath. Si pasas por la casilla de Salida, cobra "),
            new CartaPagarCaja(250,false,"Has sido elegido presidente de la junta directiva. Paga a cada jugador 250€"),
            new CartaMovimientoCaja(-1,false,1,"!Hora punta de tráfico! Retrocede tres casillas"),
            new CartaPagarCaja(150,true,"Te multan por usar el móvil mientras conduces. Paga 150€"),
            new CartaPagarCaja(-1500,true,"Beneficio por la venta de tus acciones. Recibes 1500€"),
            new CartaMovimientoCaja(-2,false,2,"Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprársela a la banca. Si tiene dueño, paga el doble de la operación indicada")
    ));

    public final static ArrayList<Carta> cartasCajaComunidad = new ArrayList<>(Arrays.asList(
            new CartaPagarSuerte(500,true,"Paga 500 por un fin de semana en un balneario de 5 estrellas"),
            new CartaMovimientoSuerte(30,false,0,"Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los "),
            new CartaMovimientoSuerte(0,true,0,"Colócate en la casilla de Salida. Cobra "),
            new CartaPagarSuerte(-2000,true,"Tu compañía de Internet obtiene beneficios. Recibe 2000€."),
            new CartaPagarSuerte(1000,true,"Paga 1000€ por invitar a todos tus amigos a un viaje a Gondor"),
            new CartaPagarSuerte(-500,true,"Devolución de Hacienda. Cobra 500€"),
            new CartaMovimientoSuerte(1,false,1,"Retrocede hasta Bag End para comprar antigüedades exóticas"),
            new CartaPagarSuerte(200,false,"Alquilas a todos tus amigos una villa en The Shire durante una semana. Paga 200€ a cada jugador."),
            new CartaPagarSuerte(-1000,true,"Recibe 1000€ de beneficios por alquilar los servicios de tu jet privado."),
            new CartaMovimientoSuerte(21,true, 1,"Ve a Fall of Rauros a disfrutar de las vistas. Si pasas por la casilla de Salida, cobra ")
    ));

    public final static ArrayList<Casilla> casillas = new ArrayList<>(Arrays.asList(
            new Especiales("Salida",0),
            new Solar("Bag End",1),
            new CasillasCarta("Caja1",2, cartasCajaComunidad),
            new Solar("Farmer Maggot's",3),
            new Impuesto("Impuesto1",4),
            new Transporte("Estacion1",5),
            new Solar("Chetwood Forest",6),
            new CasillasCarta("Suerte1",7,cartasSuerte),
            new Solar("Weathertop",8),
            new Solar("Ford of Bruinen",9),
            new Especiales("Carcel",10),
            new Solar("Rivendell",11),
            new Servicio("Servicio1",12),
            new Solar("Council of Elrond",13),
            new Solar("Caras Galadon",14),
            new Transporte("Estacion2",15),
            new Solar("Fall of Rauros",16),
            new CasillasCarta("Caja2",17, cartasCajaComunidad),
            new Solar("Nen Hithoel",18),
            new Solar("Argonath",19),
            new Accion("Parking",20),
            new Solar("Golden Hall",21),
            new CasillasCarta("Suerte2",22,cartasSuerte),
            new Solar("Edoras",23),
            new Solar("Helm's Deep",24),
            new Transporte("Estacion3",25),
            new Solar("Isengard Caverns",26),
            new Solar("Fords of Isen",27),
            new Servicio("Servicio2",28),
            new Solar("Tower of Orthanc",29),
            new Especiales("VeCarcel",30),
            new Solar("Pelennor Fields",31),
            new Solar("Osgiliath",32),
            new CasillasCarta("Caja3",33, cartasCajaComunidad),
            new Solar("Minas Tirith",34),
            new Transporte("Estacion4",35),
            new CasillasCarta("Suerte3",36,cartasSuerte),
            new Solar("Barad-dûr",37),
            new Impuesto("Impuesto2",38),
            new Solar("Mt. Doom",39)
    ));

    public static ArrayList<Propiedades> getComprables(){
        ArrayList<Propiedades> comprables = new ArrayList<>();
        for(Casilla c: casillas){
            if(c instanceof Propiedades)
                comprables.add((Propiedades)c);
        }
        return comprables;
    }

    public static double PRECIO_G1 = 120;
    private static double precioTotalSolares = PRECIO_G1*23.85769;
    public final static double FORTUNA_INICIAL = precioTotalSolares /3.0;
    private static double dineroVuelta = precioTotalSolares /22;
    private static double dineroSalirCarcel = 0.25* dineroVuelta;
    private static double precioServicio = 0.75*dineroVuelta;

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

    private static void setValoresEspeciales(){
        for(int i=5;i<40;i+=10){
            ((Transporte)casillas.get(i)).setPrecio(dineroVuelta);
        }
        ((Servicio)casillas.get(12)).setPrecio(precioServicio);
        ((Servicio)casillas.get(28)).setPrecio(precioServicio);
        ((Impuesto)casillas.get(4)).setApagar(dineroVuelta/2);
        ((Impuesto)casillas.get(38)).setApagar(dineroVuelta);
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

    public static double getPrecioTotalSolares(){
        return precioTotalSolares;
    }

    public static double getDineroVuelta(){
        return dineroVuelta;
    }

    public static double getDineroSalirCarcel(){
        return dineroSalirCarcel;
    }

    public static int getEdificios(){return edificios;}
    public static void incrementarEdificios(){edificios++;}

    public static int getTratos(){return tratos;}
    public static void incrementarTratos(){tratos++;}

    public static void actualizarVuelta(){
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
}
