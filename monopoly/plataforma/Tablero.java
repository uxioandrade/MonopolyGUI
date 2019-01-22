package monopoly.plataforma;
import monopoly.contenido.*;
import monopoly.contenido.avatares.Avatar;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.edificios.Casa;
import monopoly.contenido.edificios.Edificios;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.contenido.edificios.Hotel;
import monopoly.contenido.edificios.Piscina;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;

public class Tablero {

    public final static int LONGITUDCASILLA = 20;
    private HashMap<String, Casilla> casillas;
    private HashMap<String,Jugador> jugadores;
    private HashMap<String, Avatar> avatares;
    private ArrayList<ArrayList<Edificios>> edificios;
    private Jugador banca;
    private int vueltas;

    public Tablero(){
        this.jugadores = new HashMap<>();
        this.avatares = new HashMap<>();
        this.casillas = new HashMap<>();
        this.banca = new Jugador();
        this.edificios = new ArrayList<>(); //0-> Casas 1-> Hoteles 2->Piscinas 3->Pistas Deportes
        for(int i = 0; i <4 ; i++) {
            this.edificios.add(new ArrayList<Edificios>());
        }
        for(Casilla cas: Valor.casillas){
            this.casillas.put(cas.getNombre(),cas);
        }
        this.vueltas = 0;
    }

    public Tablero(HashMap<String,Jugador> jugadores){
        this.jugadores = jugadores;
        this.avatares = new HashMap<>();
        this.casillas = new HashMap<>();
        this.edificios = new ArrayList<>();
        for(int i = 0; i <4 ; i++) {
            this.edificios.add(new ArrayList<Edificios>());
        }
        for(Casilla cas: Valor.casillas){
            this.casillas.put(cas.getNombre(),cas);
        }
        this.banca = new Jugador();
        this.vueltas = 0;
    }

    public HashMap<String, Casilla> getCasillas(){
        return this.casillas;
    }

    //No tiene sentido un setter de casillas, pues estas no se modifican a lo largo de un programa

    public int getVueltas(){
        return this.vueltas;
    }

    public void setVueltas(int vueltas){
        this.vueltas = vueltas;
    }

    public void modificarVueltas(int vueltas){
        this.vueltas += vueltas;
    }

    public HashMap<String,Jugador> getJugadores(){ return this.jugadores; }

    public void setJugadores(HashMap<String,Jugador> jugs){
        if(jugs != null)
            this.jugadores = jugs;
    }

    public void addJugadores(Jugador jug){
        if(jug != null)
            this.jugadores.put(jug.getNombre(),jug);
    }

    public HashMap<String,Avatar> getAvatares(){
        return this.avatares;
    }

    public Jugador getBanca(){
        return this.banca;
    }

    //No tiene sentido el setter de Banca, ya que este jugador no se ve modificado a lo largo del programa

    public ArrayList<Edificios> getEdificios(int tipo){
        if(tipo >= 0 && tipo <=3)
            return this.edificios.get(tipo);
        else
            return null;
    }

    public ArrayList<ArrayList<Edificios>> getEdificios(){
        return this.edificios;
    }

    public void anhadirEdificio(Edificios ed) {
        if (ed instanceof Casa) {
            this.edificios.get(0).add(ed);
        } else if (ed instanceof Hotel) {
            this.edificios.get(1).add(ed);
        } else if (ed instanceof Piscina) {
            this.edificios.get(2).add(ed);
        } else{
            this.edificios.get(3).add(ed);
        }
    }

    public void borrarEdificio(Edificios ed){
        if (ed instanceof Casa) {
            this.edificios.get(0).remove(ed);
        } else if (ed instanceof Hotel) {
            this.edificios.get(1).remove(ed);
        } else if (ed instanceof Piscina) {
            this.edificios.get(2).remove(ed);
        } else{
            this.edificios.get(3).remove(ed);
        }
    }

    public void cambiarModo(){
        Iterator<Avatar> av_it = this.avatares.values().iterator();
        while(av_it.hasNext()){
            Avatar av = av_it.next();
            av.switchMode();
        }
    }

    public void cambiarModo(Jugador jugador){
        jugador.getAvatar().switchMode();
    }

    private void printBarraBaja(){
        System.out.printf("_");
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < LONGITUDCASILLA; j++){
                System.out.printf("_");
            }
            System.out.printf(" ");
        }
        System.out.printf("\n");
    }

    private String rellenarCasilla(Casilla casilla){
        StringBuilder aux = new StringBuilder();
//        Iterator<Avatar> it = (Iterator) this.avatares.entrySet().iterator();

        Iterator<Avatar> avatar_i = this.avatares.values().iterator();
        while(avatar_i.hasNext()) {
            Avatar avatar = avatar_i.next();
            if(avatar.getCasilla().equals(casilla)){
                aux.append("&" + avatar.getId());
            }
        }

        return aux.toString();
    }

    private void printCasilla(int i){
        StringBuilder aux = new StringBuilder();
        int lenAux;
        String color = "";
        if(Valor.casillas.get(i) instanceof Solar){
            Solar solar = (Solar) Valor.casillas.get(i);
            color = solar.getGrupo().getColor();
        }
        System.out.printf("\033[0;1m%s",color + Valor.casillas.get(i).getNombre());

        aux.append(" " + rellenarCasilla(Valor.casillas.get(i)));
        lenAux = LONGITUDCASILLA - aux.length() - Valor.casillas.get(i).getNombre().length();
        for(int j = 0; j < lenAux ; j++) {
            aux.append(" ");
        }
        System.out.printf("\033[0;1m%s|", aux.toString());
    }
    public void imprimirTablero(){

        //Imprimir primera línea barras bajas
        printBarraBaja();

        System.out.printf("|");
        //Imprimir primeras líneas
        for(int i = 20; i < 31;i++){
            printCasilla(i);
        }
        System.out.printf("\n");
        printBarraBaja();

        int k=31;
        for(int i=19;i> 10;i--){
            System.out.printf("|");
            printCasilla(i);
            for(int j = 0 ; j<LONGITUDCASILLA*9+8;j++){
                System.out.printf(" ");
            }
            System.out.printf("|");
            printCasilla(k);
            k++;
            System.out.printf("\n");
            if(i != 11) {
                for (int j = 0; j < LONGITUDCASILLA + 1; j++) {
                    System.out.printf("_");
                }
                for (int t = 0; t < LONGITUDCASILLA * 9 + 9; t++) {
                    System.out.printf(" ");
                }
                for (int j = 0; j < LONGITUDCASILLA + 1; j++) {
                    System.out.printf("_");
                }
                System.out.printf("\n");
            }
        }

        printBarraBaja();
        System.out.printf("|");
        for(int i = 10; i >= 0 ;i--){
            printCasilla(i);
        }
        System.out.printf("\n");
        printBarraBaja();
    }

    public void listarJugadores(){
        Iterator<Jugador> jug_i = this.jugadores.values().iterator();
        while(jug_i.hasNext()) {
            Jugador jug = jug_i.next();
            Juego.consola.imprimir(jug.toString());
        }
    }
    public void listarAvatares(){
        Iterator<Avatar> ava_i = this.avatares.values().iterator();
        while(ava_i.hasNext()) {
            Avatar ava = ava_i.next();
            Juego.consola.imprimir(ava.toString());
        }
    }

    public void listarCasillasEdificadas(){
        for(int i =0;i<4;i++){
            for(int j = 0;j < this.edificios.get(i).size();j++){
                Juego.consola.imprimir("{\n" + "id: " +this.edificios.get(i).get(j).getNombre()+ ",\npropietario: "
                        + this.edificios.get(i).get(j).getComprable().getPropietario().getNombre() + ",\ncasilla: "
                        + this.edificios.get(i).get(j).getComprable().getNombre() + ",\ngrupo: "
                        + this.edificios.get(i).get(j).getComprable().getGrupo().getNombre() + ",\ncoste: "
                        + this.edificios.get(i).get(j).getComprable().getPrecio() + "\n}");
            }
        }
    }

    public void listarPropiedades(){
        for(Propiedades cas: Valor.getComprables()){
            if(cas.getPropietario().getNombre().equals("Banca") && cas.getPrecio()>0){
                Juego.consola.imprimir(cas.toString());
            }
        }
    }

    private String getCasillaMasRentable(){
        double max = 0;
        String aux = "";
        for(Propiedades c: Valor.getComprables()){
            if(c.getRentabilidad() > max){
                aux = "[" + c.getNombre();
                max = c.getRentabilidad();
            }else if(c.getRentabilidad() == max){
                aux += ", " + c.getNombre();
            }
        }
        if(max > 0)
            return aux + "]";
        else
            return "Ninguna casilla ha reportado beneficios";
    }

    private String getGrupoMasRentable(){
        double max = 0;
        String aux = "";
        Iterator<Grupo> it_g = Valor.getGrupos().values().iterator();
        while(it_g.hasNext()){
            Grupo gAux = it_g.next();
            if(gAux.getRentabilidad() > max){
                aux = "[" + gAux.getNombre();
                max = gAux.getRentabilidad();
            }else if(gAux.getRentabilidad() == max){
                aux += ", " + gAux.getNombre();
            }
        }
        if(max > 0)
            return aux + "]";
        else
            return "Ninguna casilla de ningún grupo ha reportado beneficios";
    }

    private String getCasillaMasFrecuentada(){
        String aux = "";
        double max = 0;
        for(Casilla c: Valor.casillas){
            if(c.numVisitas() > max){
                aux = "[" + c.getNombre();
                max = c.numVisitas();
            }else if(c.numVisitas() == max){
                aux += ", " + c.getNombre();
            }
        }
        return aux + "]";
    }

    private String getJugadorMasVueltas(){
        double max = 0;
        String aux = "";
        Iterator<Avatar> av_it = this.getAvatares().values().iterator();
        while(av_it.hasNext()){
            Avatar avAux = av_it.next();
            if(avAux.getNumVueltas() > max){
                aux = "[" + avAux.getJugador().getNombre();
                max = avAux.getNumVueltas();
            }else if(avAux.getNumVueltas() == max){
                aux += ", " + avAux.getJugador().getNombre();
            }
        }
        return aux + "]";
    }

    private String getMaxVecesDados(){
        int max = 0;
        String aux = "";
        Iterator<Jugador> jug_it = this.getJugadores().values().iterator();
        while(jug_it.hasNext()){
            Jugador jAux = jug_it.next();
            if(jAux.getVecesDados() > max){
                aux = "[" + jAux.getNombre();
                max = jAux.getVecesDados();
            }else if(jAux.getVecesDados() == max){
                aux += ", " + jAux.getNombre();
            }
        }
        return aux + "]";
    }

    private String getJugadorCabeza(){
        double max = 0;
        String aux = "";
        Iterator<Jugador> jug_it = this.getJugadores().values().iterator();
        while(jug_it.hasNext()){
            Jugador jAux = jug_it.next();
            if(jAux.calcularFortuna() > max){
                aux = "[" + jAux.getNombre();
                max = jAux.calcularFortuna();
            }else if(jAux.calcularFortuna() == max){
                aux += ", " + jAux.getNombre();
            }
        }
        return aux + "]";
    }

    public void obtenerEstadisticas(){
        String aux = "{\n" +
                "casillaMasRentable: " + this.getCasillaMasRentable() + ",\n" +
                "grupoMasRentable: " + this.getGrupoMasRentable() + ",\n" +
                "casillaMasFrecuentada: " + this.getCasillaMasFrecuentada() + ",\n" +
                "jugadorMasVueltas: " + this.getJugadorMasVueltas() + ",\n" +
                "jugadorMasVecesDados: " + this.getMaxVecesDados() + ",\n" +
                "jugadorEnCabeza: " + this.getJugadorCabeza() + "\n}";
        Juego.consola.imprimir(aux);
    }
}
