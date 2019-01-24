package monopoly.contenido;

import monopoly.contenido.avatares.*;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.edificios.Edificios;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Trato;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Jugador {

    private Avatar avatar;
    private String nombre;
    private double dinero;
    private ArrayList<Propiedades> propiedades;
    private double dineroInvertido;
    private double pagoAlquileres;
    private double cobroAlquileres;
    private double pasarPorCasilla;
    private double premiosInversionesOBote;
    private double pagoImpuestos;
    private int vecesCarcel;
    private int vecesDados;
    private ArrayList<Trato> tratosPendientes;
    private ArrayList<Trato> tratosPropuestos;
    private Valor valor;
    private ArrayList<Casilla> casillasAux;

    public Jugador(Valor valor){
    	this.valor = valor;
    	this.casillasAux = valor.getCasillas();
        this.avatar = null;
        this.nombre = "Banca";
        this.dinero = Double.POSITIVE_INFINITY;
        this.propiedades = new ArrayList<>();
        for(Casilla c : casillasAux){
            if(c instanceof Propiedades)
                this.propiedades.add((Propiedades)c);
        }
        for(Propiedades c : this.propiedades){
            c.setPropietario(this);
        }
        this.tratosPendientes = new ArrayList<>();
        this.tratosPropuestos = new ArrayList<>();
    }

    public Jugador(String nombre, String tipo, Tablero tablero, BufferedImage ficha, Valor valor, Casilla salida){
    	this.valor = valor;
    	this.avatar = generarAvatar(tipo,tablero,ficha, salida);
    	this.casillasAux = valor.getCasillas();
        this.dinero = valor.FORTUNA_INICIAL;
        this.propiedades = new ArrayList<>();
        this.nombre = nombre;
        this.tratosPendientes = new ArrayList<>();
        this.tratosPropuestos = new ArrayList<>();
    }

    private Avatar generarAvatar(String tipo, Tablero tablero,BufferedImage ficha, Casilla salida){
        Avatar avatar;
        switch (tipo){
            case "Coche":
            case "coche":
                avatar = new Coche(this,tablero,ficha, valor, salida);
                break;
            case "esfinge":
            case "Esfinge":
                avatar = new Esfinge(this,tablero,ficha, valor, salida);
                break;
            case "pelota":
            case "Pelota":
                avatar = new Pelota(this,tablero,ficha, valor, salida);
                break;
            case "sombrero":
            case "Sombrero":
                avatar = new Sombrero(this,tablero,ficha, valor, salida);
                break;
            default:
                avatar = generarAvatar(asignarTipoAleatorio(),tablero,ficha, salida);
        }
        return avatar;
    }

    private final ArrayList<String> listaTipos = new ArrayList<String>() {{
        add("Sombrero");
        add("Coche");
        add("Esfinge");
        add("Pelota");
    }};

    private String asignarTipoAleatorio(){
        Random rnd = new Random();
        return this.listaTipos.get(rnd.nextInt(4));
    }

    public Avatar getAvatar(){
        return this.avatar;
    }

    //El setter de avatar es omitible, pues un avatar asociado a un jugador no debería modificarse

    public String getNombre(){
        return this.nombre;
    }

    //El setter de nombre no es necesario, pues un jugador no puede cambiar de nombre una vez creado

    public double getDinero(){
        return this.dinero;
    }
    public void setDinero(double dinero){
        if(dinero > 0)
            this.dinero=dinero;
    }
    public void modificarDinero(double cantidad){
        this.dinero+=cantidad;
    }

    public Tablero getTablero() { return this.getTablero(); }

    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva


    public ArrayList<Propiedades> getPropiedades(){
        return this.propiedades;
    }

    public void setPropiedades(ArrayList<Propiedades> casillas){//Podría ser interesante en las próximas entregas
        this.propiedades = casillas;
    }

    public void anhadirPropiedad(Propiedades casilla){
        if(casilla != null){
            this.propiedades.add(casilla);
            if(!casilla.getPropietario().equals(this))
                casilla.setPropietario(this);
        }
    }

    public void borrarPropiedad(Propiedades casilla){
        if(casilla != null){
                this.propiedades.remove(casilla);
        }
    }

    public ArrayList<Trato> getTratosPendientes(){
        return this.tratosPendientes;
    }

    public void anhadirTratoPendiente(Trato trato){
        this.tratosPendientes.add(trato);
        trato.getOfertor().tratosPropuestos.add(trato);
    }

    public void borrarTratoPendiente(Trato trato){
        this.tratosPendientes.remove(trato);
        trato.getOfertor().tratosPropuestos.remove(trato);
    }

    //Todos los atributos siguientes no necesitan setters, pues el único momento en el que se les asigna un valor independientemente de su anterior es al crear el jugador, después las modificaciones se hacen a partir de su valor previo

    public double getDineroInvertido(){
        return this.dineroInvertido;
    }

    public void modificarDineroInvertido(double valor){
        this.dineroInvertido += valor;
    }

    public double getPagoAlquileres(){
        return this.pagoAlquileres;
    }

    public void modificarPagoAlquileres(double valor){
        this.pagoAlquileres += valor;
    }

    public double getCobroAlquileres(){
        return this.cobroAlquileres;
    }

    public void modificarCobroAlquileres(double valor){
        this.cobroAlquileres += valor;
    }

    public double getPasarPorCasilla(){
        return this.pasarPorCasilla;
    }

    public void modificarPasarPorCasilla(double valor){
        this.pasarPorCasilla += valor;
    }

    public double getPremiosInversionesOBote(){
        return this.premiosInversionesOBote;
    }

    public void modificarPremiosInversionesOBote(double valor){
        this.premiosInversionesOBote += valor;
    }

    public double getPagoImpuestos(){
        return this.pagoImpuestos;
    }

    public void modificarPagoImpuestos(double valor){
        this.pagoImpuestos += valor;
    }

    public int getVecesCarcel(){
        return this.vecesCarcel;
    }

    public int getVecesDados(){return this.vecesDados;}

    public void anhadirVecesDados(){
        this.vecesDados++;
    }

    public void anhadirVecesCarcel(){
        this.vecesCarcel++;
    }

    public ArrayList<Trato> getTratosPropuestos() {
        return this.tratosPropuestos;
    }

    public void anhadirTratoPropuesto(Trato t){
        this.tratosPropuestos.add(t);
    }

    public void borrarTratosPropuesto(Trato t){
        this.tratosPropuestos.remove(t);
    }

    public String getDescripcionInicial(){
        String aux = "\t{\n" +
                "\t\tNombre: " + this.nombre + "\n" +
                "\t\tAvatar: " + this.avatar.getId() + "\n\t}";
        return aux;
    }
    public int numSolaresGrupo(Grupo grupo){
        int i=0;
        for (Propiedades cas: this.getPropiedades()){//recorremos las propiedades del jugador
            if(cas instanceof Solar) {
                Solar sol = (Solar) cas;
                if (sol.getGrupo().equals(grupo)) {//si el grupo de la casilla coincide con el que buscamos sumamos 1
                    i++;
                }
            }
        }
        return i;
    }
    public boolean poseeGrupoCompleto(Grupo grupo){
        return (this.numSolaresGrupo(grupo)==grupo.getCasillas().size());//comparamos que el numero de casillas que posee del grupo coincidan con el numero de casillas del grupo
    }

    public double calcularFortuna(){
        double total=dinero;
        for(Propiedades c: this.propiedades){
            if(!c.getHipotecado()) {
                total += c.getPrecio();
                if(c instanceof Solar && ((Solar)c).getConstrucciones() != null){
                    for(Edificios ed : ((Solar) c).getConstrucciones()){
                        total += ed.getPrecio();
                    }
                }
            }
        }
        return total;
    }

    public void imprimirEstadisticas(){

        String aux = "{" +
                "\nDinero Invertido: " + this.getDineroInvertido() +
                "\nPago de Alquileres: " + this.getPagoAlquileres() +
                "\nPago Impuestos: " + this.getPagoImpuestos() +
                "\nCobro de Alquileres: " + this.getCobroAlquileres() +
                "\nPasar por Casillas Salida:" + this.getPasarPorCasilla() +
                "\nPremios Inversiones o Botes:" + this.getPremiosInversionesOBote() +
                "\nVeces en la Cárcel: " + this.getVecesCarcel() + "\n}";
        Juego.consola.anhadirTexto(aux);
    }

    public String listarTratosPendientes(){
        String aux = "";
        for(Trato t: this.tratosPendientes){
            aux += "trato" + t.getId() + ":{\n" + t + "\n},\n";
        }
        return aux;
    }

    public void listarTratosPropuestos(){
        for(Trato t: this.tratosPropuestos){
            Juego.consola.anhadirTexto("trato" + t.getId() + ":{\n" + t + "\n},\n");
        }
    }

    @Override
    public String toString(){
        int casas=0;
        int hoteles=0;
        int piscinas=0;
        int pistas=0;
        ArrayList<Propiedades> hipotecados = new ArrayList<>();
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                //"Avatar: " + this.avatar.getId() + "\n" +
                "Tipo: " + this.avatar.getTipo() + "\n" +
                "Dinero Actual: " + this.dinero + "\n" +
                "Propiedades: {";
        if(this.propiedades.size()!=0) {//si el jugador tiene propiedades las añadimos al string
            aux +="\n";
            for (Casilla prop : propiedades) {
                if (!((Propiedades) prop).getHipotecado()) {
                    if (prop instanceof Solar) {
                        casas += ((Solar) prop).getConstrucciones("casa").size();
                        hoteles += ((Solar) prop).getConstrucciones("hotel").size();
                        piscinas += ((Solar) prop).getConstrucciones("piscina").size();
                        pistas += ((Solar) prop).getConstrucciones("pista").size();
                        aux += prop.getNombre(); /*+
                                ". casas: " + ((Solar) prop).getConstrucciones("casa").size() +
                                ", hoteles: " + ((Solar) prop).getConstrucciones("hotel").size() +
                                ", piscinas: " + ((Solar) prop).getConstrucciones("piscina").size() +
                                ", pistas: " + ((Solar) prop).getConstrucciones("pista").size() +
                                "]\n";*/
                    } else {
                        aux += "\t" + prop.getNombre() + "\n";
                    }
                }else{
                    hipotecados.add((Propiedades)prop);
                }
            }
        }
        aux += "}\n";
        aux += "Hipotecas: [";
        for(Propiedades comp : hipotecados){
            aux += comp.getNombre() + ", ";
        }
        aux += "]\n";
        aux+="Total de Casas: " + casas+ "\n" +
                "Total de Hoteles: " + hoteles+ "\n" +
                "Total de Piscinas: " + piscinas + "\n" +
                "Total de Pistas de Deporte: "+ pistas+"\n";
        aux += "\n}\n";
        aux += "Estadísticas de jugador\n";
        aux +=  "{" +
                "\nDinero Invertido: " + this.getDineroInvertido() +
                "\nPago de Alquileres: " + this.getPagoAlquileres() +
                "\nPago Impuestos: " + this.getPagoImpuestos() +
                "\nCobro de Alquileres: " + this.getCobroAlquileres() +
                "\nPasar por Casillas Salida:" + this.getPasarPorCasilla() +
                "\nPremios Inversiones o Botes:" + this.getPremiosInversionesOBote() +
                "\nVeces en la Cárcel: " + this.getVecesCarcel() + "\n}";
        return aux;
    }
}
