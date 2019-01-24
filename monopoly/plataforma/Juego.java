package monopoly.plataforma;

import interfazgrafica.PanelTexto;

import monopoly.contenido.*;

import java.util.ArrayList;

import monopoly.contenido.avatares.*;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.casillas.Impuesto;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.comandos.ExcepcionComandoIncorrecto;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinamicas.ExcepcionDinamicaModoMovimiento;
import monopoly.excepciones.dinamicas.ExcepcionesDinamicaEncarcelamiento;
import monopoly.excepciones.dinamicas.ExcepcionesDinamicaTurno;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionPropiedades;


public class Juego implements Comando{

    public static ConsolaNormal consola;
    private Tablero tablero;
    private Valor valor;
    private Dados dados;
    private int countTiradas;
    private int vecesDobles;
    private int tirada;
    private int turno;
    private Operacion operacion;
    private Boolean salir;
    private PanelTexto panelTexto;
    private Jugador jugadorActual;
    private ArrayList<String> turnosJugadores;//ArrayList que guarda el orden de los jugadores (0-primero)...
    //Tiene acceso a paquete

    public Juego(Valor valor){
    	//Posible edicion de mapa
    	this.valor = valor;
        this.turnosJugadores = new ArrayList<>();
        this.tablero = new Tablero(valor);
        this.operacion = new Operacion(this.tablero, valor);
        consola=new ConsolaNormal();
        //this.operacion.crearJugadores();//antes de empezar el menu de partida creamos los jugadores

        //this.tablero.imprimirTablero();
        this.dados = new Dados();

        this.turno = 0;
        //this.jugadorActual = this.tablero.getJugadores().get(turnosJugadores.get(0)); //set el jugador del primer turno al primer jugador
        this.salir = false;
        String auxCasilla = "";
        this.countTiradas = 0;
        this.vecesDobles = 0;
        this.tirada = 0;
        //Juego.consola.imprimir(" El jugador actual es " + jugadorActual.getNombre());
       /* while (!salir) {
            try {
                String orden = consola.leer("$> ");
                String[] partes = orden.split(" ");
                String comando = partes[0];
                // Acciones en función del comando introducido
                switch (comando) {
                    case "describir":
                        try {
                            this.describir(partes);
                        } catch (ExcepcionNumeroPartesComando ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "comprar":
                        try {
                            this.comprar();
                        } catch (ExcepcionDineroVoluntario | ExcepcionRestriccionComprar ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "edificar":
                        try {
                            this.edificar(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionDineroVoluntario | ExcepcionRestriccionEdificar ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "vender":
                        try {
                            this.vender(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionEdificar ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "hipotecar":
                        try {
                            this.hipotecar(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionHipotecar ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "deshipotecar":
                        try {
                            this.deshipotecar(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionHipotecar | ExcepcionDineroVoluntario ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "lanzar":
                        try {
                            this.lanzar(partes);
                        } catch (ExcepcionesDinamicaTurno | ExcepcionNumeroPartesComando | ExcepcionesDinamicaEncarcelamiento | ExcepcionRestriccionHipotecar | ExcepcionDineroVoluntario | ExcepcionRestriccionComprar ex) {
                            consola.imprimir(ex.getMensaje());
                        } catch (ExcepcionDineroDeuda | ExcepcionRestriccionEdificar ex2) {
                            consola.imprimir(ex2.getMensaje());
                            if (jugadorActual.getAvatar().getCasilla() instanceof Impuesto) {
                                try {
                                    if (operacion.menuHipotecar(jugadorActual, operacion.getTablero(), ((Impuesto) jugadorActual.getAvatar().getCasilla()).getApagar()))
                                        ((Impuesto) jugadorActual.getAvatar().getCasilla()).pagarImpuesto(jugadorActual, operacion);
                                } catch (ExcepcionDineroDeuda | ExcepcionRestriccionHipotecar | ExcepcionNumeroPartesComando | ExcepcionRestriccionEdificar ex3) {
                                    consola.imprimir(ex3.getMensaje());
                                }
                            } else if (jugadorActual.getAvatar().getCasilla() instanceof Propiedades) {
                                try {
                                    if (operacion.menuHipotecar(jugadorActual, operacion.getTablero(), ((Propiedades) jugadorActual.getAvatar().getCasilla()).alquiler(tirada))) {
                                        ((Propiedades) jugadorActual.getAvatar().getCasilla()).pagarAlquiler(jugadorActual, tirada, operacion);
                                    }
                                } catch (ExcepcionDineroDeuda | ExcepcionRestriccionHipotecar | ExcepcionNumeroPartesComando | ExcepcionRestriccionEdificar ex3) {
                                    consola.imprimir(ex3.getMensaje());
                                }
                            }
                        }
                        break;
                    case "acabar":
                        try {
                            this.acabar(partes);
                        } catch (ExcepcionesDinamicaTurno | ExcepcionNumeroPartesComando ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "jugador"://describe al jugador actual
                        try {
                            this.jugador(partes);
                        } catch (ExcepcionNumeroPartesComando ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "salir":
                        try {
                            this.salir(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionHipotecar | ExcepcionRestriccionEdificar | ExcepcionDineroVoluntario | ExcepcionesDinamicaEncarcelamiento ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "listar":
                        try {
                            this.listar(partes);
                        } catch (ExcepcionNumeroPartesComando ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "ver":
                        try {
                            this.ver(partes);
                        } catch (ExcepcionNumeroPartesComando ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "cambiar":
                        try {
                            this.cambiar(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionDinamicaModoMovimiento ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "estadisticas":
                        try {
                            this.estadisticas(partes);
                        } catch (ExcepcionNumeroPartesComando ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "trato":
                        try {
                            this.trato(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionPropiedades | ExcepcionDineroVoluntario ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "aceptar":
                        try {
                            this.aceptarTrato(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionPropiedades | ExcepcionDineroVoluntario ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    case "eliminar":
                        try {
                            this.borrarTrato(partes);
                        } catch (ExcepcionNumeroPartesComando | ExcepcionRestriccionPropiedades ex) {
                            consola.imprimir(ex.getMensaje());
                        }
                        break;
                    default:
                        throw new ExcepcionComandoIncorrecto("\nComando incorrecto.");
                }
            }catch(ExcepcionComandoIncorrecto ex) {
                consola.imprimir(ex.getMensaje());
            }
        }*/
    }

    public void setPanelTexto(PanelTexto panelTexto){
        this.panelTexto = panelTexto;
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public void setJugadorActual(Jugador jugador){
        this.jugadorActual = jugador;
    }

    public Jugador getJugadorActual(){
        return this.jugadorActual;
    }

    public Dados getDados() {
        return this.dados;
    }

    public static ConsolaNormal getConsola() {
        return consola;
    }

    public ArrayList<String> getTurnosJugadores() {
        return this.turnosJugadores;
    }

    public void describir(String[] partes) throws ExcepcionNumeroPartesComando{
        String auxCasilla;
        if(partes.length<3) throw new ExcepcionNumeroPartesComando("Comando incorrecto");
        else if(partes[1].equals("jugador")) {
            Jugador jugadorDescribir = tablero.getJugadores().get(partes[2]);//buscamos el jugador en el hashmap de jugadores de tablero
            if (jugadorDescribir != null) this.panelTexto.addTexto(jugadorDescribir.toString());//si existe lo imprimimos
            else consola.anhadirTexto("\nEl jugador no existe");
        }else if(partes[1].equals("casilla")){
            auxCasilla = "";
            for(int i = 2; i < partes.length - 1;i++) {
                auxCasilla += partes[i] + " ";
            }
            Casilla casilla = tablero.getCasillas().get(auxCasilla + partes[partes.length-1]);//buscamos la casilla en el hashmap de casillas del tablero
            if(casilla!=null) consola.anhadirTexto(casilla.toString());//si existe la imprimimos
            else this.panelTexto.addTexto("\nLa casilla no existe");
        }else if(partes[1].equals("avatar")){
            Avatar avatar = tablero.getAvatares().get(partes[2]);//buscamos el avatar en el hashmap de avatares del tablero
            if(avatar!=null) this.panelTexto.addTexto(avatar.toString());//si existe lo imprimimos
            else this.panelTexto.addTexto("\nEl avatar no existe.");
            System.out.flush();
        }else this.panelTexto.addTexto("\nNo existe el tipo de elemento " + partes[1]);
    }

    public void comprar() throws ExcepcionDineroVoluntario, ExcepcionRestriccionComprar{
        this.operacion.comprar(jugadorActual);
    }

    public void edificar(String tipo) throws ExcepcionDineroVoluntario, ExcepcionRestriccionEdificar{
            operacion.edificar(jugadorActual,tipo);
    }

    public void vender(String[] partes) throws ExcepcionNumeroPartesComando , ExcepcionRestriccionEdificar{
        String auxCasilla = "";
        if(partes.length >= 4){
            auxCasilla = "";
            for(int i = 2; i < partes.length - 2;i++) {
                auxCasilla += partes[i] + " ";
            }
            operacion.venderConstrucciones(jugadorActual,tablero.getCasillas().get(auxCasilla + partes[partes.length-2]),partes[1],partes[partes.length-1].toCharArray()[0] - '0');
        }else{
            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
        }
    }

    public void hipotecar(String casilla) throws ExcepcionRestriccionHipotecar{
        if(this.tablero.getCasillas().get(casilla)!=null)
            operacion.hipotecar(tablero.getCasillas().get(casilla),jugadorActual);
        else throw new ExcepcionRestriccionHipotecar("La casilla que quieres hipotecar no existe");
    }

    public void deshipotecar(String casilla) throws ExcepcionRestriccionHipotecar, ExcepcionDineroVoluntario{
        if (tablero.getCasillas().get(casilla) != null)//si existe la casilla
            operacion.desHipotecar(tablero.getCasillas().get(casilla), jugadorActual);
        else this.panelTexto.addTexto("La casilla que quieres hipotecar no existe");
    }

    public void lanzar() throws ExcepcionesDinamicaTurno, ExcepcionDineroDeuda, ExcepcionesDinamicaEncarcelamiento, ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        if (this.countTiradas == 0) {//si tienes tiradas pendientes te muestra la tirada
            this.dados.lanzarDados();
            this.jugadorActual.anhadirVecesDados();
            this.tirada = dados.getSuma();
            this.dados.getDescripcion();
            if (jugadorActual.getAvatar().getEncarcelado() == 0) {//si no esta encarcelado
                if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                    if(jugadorActual.getAvatar().getNumTiradas() > 0){
                        jugadorActual.getAvatar().moverCasilla(tirada);
                    }else if(jugadorActual.getAvatar().getNumTiradas() == 0) {
                        this.panelTexto.addTexto("El coche ya ha realizado todas las tiradas que podía en su turno");
                    }else{
                        jugadorActual.getAvatar().moverCasilla(tirada);
                        this.panelTexto.addTexto(jugadorActual.getNombre() + " aún le quedan " + ( jugadorActual.getAvatar().getNumTiradas()*-1 + 1) + " turnos para poder volver a tirar");
                    }
                    if(jugadorActual.getAvatar().getNumTiradas() <= 0) countTiradas++;
                    if(jugadorActual.getAvatar().getEncarcelado() != 0){
                        this.countTiradas++;
                        jugadorActual.getAvatar().setNumTiradas(0);
                    }
                }else if((jugadorActual.getAvatar() instanceof Esfinge || jugadorActual.getAvatar() instanceof Sombrero)&& jugadorActual.getAvatar().getModoAvanzado()){
                    if(jugadorActual.getAvatar().getNumTiradas() > 0 && jugadorActual.getAvatar().getEncarcelado() == 0) {
                        jugadorActual.getAvatar().moverCasilla(tirada);
                        jugadorActual.getAvatar().restarNumTiradas();
                        if(jugadorActual.getAvatar().getEncarcelado() != 0){
                            this.countTiradas++;
                            jugadorActual.getAvatar().setNumTiradas(0);
                        }
                    }else if (jugadorActual.getAvatar().getEncarcelado() != 0){
                        this.countTiradas++;
                        jugadorActual.getAvatar().setNumTiradas(0);
                    }else if(jugadorActual.getAvatar().getNumTiradas() == 0) {
                        this.panelTexto.addTexto("El avatar ya ha realizado todas las tiradas que podía en su turno");
                    }
                    if(jugadorActual.getAvatar().getNumTiradas() <= 0) countTiradas++;
                }else {
                    if (!dados.sonDobles())
                        this.countTiradas++;//si no son dobles aumentamos una tirada si fuesen dobles no se aumenta porque tendria derecho a volver a tirar
                    else vecesDobles++;//si son dobles incrementamos veces dobles
                    if (vecesDobles >= 3) {//si saco dobles 3 veces va a la carcel y se cancelan sus acciones pendientes
                        operacion.irCarcel(jugadorActual);
                        this.countTiradas++;
                        this.panelTexto.addTexto("El jugador " + jugadorActual.getNombre() + " ha sacado dobles 3 veces.");
                    } else {
                        if(jugadorActual.getAvatar() instanceof Pelota && jugadorActual.getAvatar().getModoAvanzado() && tirada <= 4){
                            consola.anhadirTexto("El avatar " + jugadorActual.getAvatar().getId() + " retrocede " + tirada + " posiciones");
                        }else {
                        	ArrayList<Casilla> casillasAux = valor.getCasillas();
                        	this.panelTexto.addTexto("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + casillasAux.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
                        }
                        jugadorActual.getAvatar().moverCasilla(tirada);
                        if(!(jugadorActual.getAvatar() instanceof Pelota && jugadorActual.getAvatar().getModoAvanzado()))
                            jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada, operacion);
                        if (jugadorActual.getAvatar().getEncarcelado() != 0) countTiradas++;
                    }
                }
            }else {
                if (dados.sonDobles()) {
                    jugadorActual.getAvatar().setEncarcelado(0);
                    if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                        if (((Coche) jugadorActual.getAvatar()).getNumTiradas() > 0) {
                            ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                        } else if (((Coche) jugadorActual.getAvatar()).getNumTiradas() == 0) {
                            this.panelTexto.addTexto("El coche ya ha realizado todas las tiradas que podía en su turno");
                        } else {
                            ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                            this.panelTexto.addTexto(jugadorActual.getNombre() + " aún le quedan " + (((Coche) jugadorActual.getAvatar()).getNumTiradas() * -1 + 1) + " turnos para poder volver a tirar");
                        }
                        if (((Coche) jugadorActual.getAvatar()).getNumTiradas() <= 0)
                            this.countTiradas++;
                        if (jugadorActual.getAvatar().getEncarcelado() != 0) {
                            this.countTiradas++;
                            ((Coche) jugadorActual.getAvatar()).setNumTiradas(0);
                        }
                    }else if((jugadorActual.getAvatar() instanceof Esfinge || jugadorActual.getAvatar() instanceof Sombrero )&& jugadorActual.getAvatar().getModoAvanzado())
                        jugadorActual.getAvatar().setNumTiradas(3);
                    jugadorActual.getAvatar().moverCasilla(tirada);
                    jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada, operacion);
                    this.countTiradas++;
                } else {
                    jugadorActual.getAvatar().modificarEncarcelado(1);
                    this.countTiradas++;
                    if (jugadorActual.getAvatar().getEncarcelado() > 3) {
                        this.countTiradas = 0;
                        operacion.salirCarcel(jugadorActual);
                        //System.out.println("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + this.jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + Valor.casillas.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
                        jugadorActual.getAvatar().moverCasilla(tirada);
                        if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()){
                            if(this.tirada > 4)
                                ((Coche) jugadorActual.getAvatar()).setNumTiradas(3);
                        }
                        countTiradas++;
                        jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada, operacion);
                    }
                    else
                        throw new ExcepcionesDinamicaEncarcelamiento("El jugador " + jugadorActual.getNombre() + " sigue en la cárcel");
                }
            }
            //tablero.imprimirTablero();
        } else
            throw new ExcepcionesDinamicaTurno("No puedes tirar más veces en este turno");
    }

    public void acabar() throws ExcepcionesDinamicaTurno{
        if (this.countTiradas != 0) {
            this.turno++;
            if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                ((Coche) jugadorActual.getAvatar()).setPoderComprar(true);
                ((Coche) jugadorActual.getAvatar()).sumarNumTirada();
                if(jugadorActual.getAvatar().getEncarcelado() != 0)
                    ((Coche) jugadorActual.getAvatar()).setNumTiradas(1);
             }else if((jugadorActual.getAvatar() instanceof Esfinge || jugadorActual.getAvatar() instanceof Sombrero) && jugadorActual.getAvatar().getModoAvanzado()){
                jugadorActual.getAvatar().setNumTiradas(3);
            }
            for(Propiedades p: valor.getComprables()){
               p.reducirTurnosTratos(jugadorActual);
            }
            jugadorActual = tablero.getJugadores().get(turnosJugadores.get(this.turno % turnosJugadores.size()));
            this.countTiradas = 0;
            this.vecesDobles = 0;
            consola.anhadirTexto("El jugador actual es " + jugadorActual.getNombre());
            jugadorActual.listarTratosPendientes();
        } else {
            throw new ExcepcionesDinamicaTurno("No se puede finalizar el turno sin haber tirado los dados");
        }
    }

    public void salir(String partes[]) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionesDinamicaEncarcelamiento{
        if(partes.length==2 && partes[1].equals("carcel")){
            if(countTiradas == 0){
                operacion.salirCarcel(jugadorActual);
                this.countTiradas = 0;
            }else{
                if(jugadorActual.getAvatar().getEncarcelado()>0)
                    this.panelTexto.addTexto("No puedes pagar para salir de la cárcel una vez que has lanzado los dados");
                else
                    this.panelTexto.addTexto("No estás en la cárcel");
            }
        }
        else if(partes.length==1){
            this.panelTexto.addTexto("\nGracias por jugar.");
            salir = true;
        }else{
            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
        }
    }

    public void listar(String partes[]) throws ExcepcionNumeroPartesComando{
        if(partes.length>=2){
            if(partes[1].equals("jugadores")){
                tablero.listarJugadores();
                tablero.imprimirTablero();
            }else if(partes[1].equals("avatares")) {
                tablero.listarAvatares();
                tablero.imprimirTablero();
            }else if(partes[1].equals("tratos")) {
                jugadorActual.listarTratosPendientes();
            }else if(partes[1].equals("edificios")){
                if(partes.length == 2)
                    tablero.listarCasillasEdificadas();
                else
                if(Valor.getGrupos().containsKey(partes[2]))
                    Valor.getGrupos().get(partes[2]).listarEdificiosGrupo();
                else
                    this.panelTexto.addTexto("El grupo introducido no existe");
            }
            if(partes[1].equals("enventa")){
                tablero.listarPropiedades();
                tablero.imprimirTablero();
            }
        }else
            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
    }

    public void jugador(String partes[]) throws ExcepcionNumeroPartesComando{
        if(partes.length == 1)
            consola.anhadirTexto(jugadorActual.getDescripcionInicial());
        else
            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
    }

    public void ver(String partes[]) throws ExcepcionNumeroPartesComando{
        if(partes.length==2){
            if(partes[1].equals("tablero")) tablero.imprimirTablero();
        }else
            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
    }

    public void cambiar() throws ExcepcionDinamicaModoMovimiento{
        if(this.countTiradas==0) {
            if(jugadorActual.getAvatar().getEncarcelado() == 0) {
                tablero.cambiarModo(jugadorActual);
                if (jugadorActual.getAvatar().getModoAvanzado())
                    this.panelTexto.addTexto("El jugador " + jugadorActual.getNombre() + " está ahora en modo avanzado de tipo " + jugadorActual.getAvatar().getTipo());
                else
                    this.panelTexto.addTexto("El jugador " + jugadorActual.getNombre() + " ya no está en modo avanzado");
            }else
                throw new ExcepcionDinamicaModoMovimiento("El modo de movimiento no puede ser cambiado en la cárcel");
        }else{
            throw new ExcepcionDinamicaModoMovimiento("El modo de movimiento debe ser cambiado antes de tirar los dados");
        }
    }

    public String estadisticasGlobales(){
        return tablero.obtenerEstadisticas();
    }

    public void estadisticasJugadores(Jugador jugador){
        jugador.imprimirEstadisticas();
    }

    public void trato(String el1,String el2, String el3, String jugadorPropuesto,int caso) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionPropiedades, ExcepcionDineroVoluntario{
        if(!this.tablero.getJugadores().containsKey(jugadorPropuesto))
            throw new ExcepcionNumeroPartesComando("El jugador " + jugadorPropuesto + " no existe");
        if(this.jugadorActual.getNombre().equals(jugadorPropuesto))
            throw new ExcepcionDineroVoluntario("Un jugador no puede ofrecerse un trato a si mismo");
        switch (caso){
            case 1:
                if (!tablero.getCasillas().containsKey(el1))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if (!tablero.getCasillas().containsKey(el2))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                if (jugadorActual.getPropiedades().contains((Propiedades) tablero.getCasillas().get(el1))) {
                    if (!((Propiedades) tablero.getCasillas().get(el2)).perteneceAJugador(tablero.getJugadores().get(jugadorPropuesto)))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                    Trato trato = new Trato((Propiedades) tablero.getCasillas().get(el1), (Propiedades) tablero.getCasillas().get(el2), "cambiar");
                }else {
                    if (!((Propiedades) tablero.getCasillas().get(el1)).perteneceAJugador(jugadorActual))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                    Trato trato = new Trato((Propiedades) tablero.getCasillas().get(el2), (Propiedades) tablero.getCasillas().get(el1), "cambiar");
                }
                break;
            case 2:
                if(!tablero.getCasillas().containsKey(el1) || !((Propiedades) tablero.getCasillas().get(el1)).perteneceAJugador(jugadorActual))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if(el2.charAt(0) <= 47 || el2.charAt(0) >= 58)
                    throw new ExcepcionRestriccionPropiedades(el1 + " no es un número válido");
                Trato trato = new Trato(Integer.parseInt(el2),tablero.getJugadores().get(jugadorPropuesto), (Propiedades) tablero.getCasillas().get(el1),"cambiar");
                break;
            case 3:
                //Caso dinero x propiedad
                if(!tablero.getCasillas().containsKey(el2) || !((Propiedades) tablero.getCasillas().get(el2)).perteneceAJugador(tablero.getJugadores().get(jugadorPropuesto)))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                if(el1.charAt(0) <= 47 || el1.charAt(0) >= 58)
                    throw new ExcepcionRestriccionPropiedades(el1 + " no es un número válido");
                if(jugadorActual.getDinero() < Integer.parseInt(el1))
                    throw new ExcepcionDineroVoluntario("El jugador no posee el dinero suficiente para proponer ese trato");
                Trato trato2 = new Trato(jugadorActual,Integer.parseInt(el1), (Propiedades) tablero.getCasillas().get(el2),"cambiar");
                break;
            case 4:
                if(!tablero.getCasillas().containsKey(el1))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if(!tablero.getCasillas().containsKey(el2))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(el1)).perteneceAJugador(jugadorActual))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(el2)).perteneceAJugador(tablero.getJugadores().get(jugadorPropuesto)))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                if(el3.charAt(0) <= 47 || el3.charAt(0) >= 58)
                    throw new ExcepcionRestriccionPropiedades(el3 + " no es un número válido");
                Trato trato3 = new Trato((Propiedades) tablero.getCasillas().get(el1),Integer.parseInt(el3),(Propiedades) tablero.getCasillas().get(el2),"cambiar");
                break;
            case 5:
                if(!tablero.getCasillas().containsKey(el1) || !((Propiedades) tablero.getCasillas().get(el1)).perteneceAJugador(jugadorActual))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if(el2.charAt(0) <= 47 || el2.charAt(0) >= 58)
                    throw new ExcepcionRestriccionPropiedades(el2 + " no es un número válido");
                if(!tablero.getCasillas().containsKey(el3) || !((Propiedades) tablero.getCasillas().get(el3)).perteneceAJugador(this.tablero.getJugadores().get(jugadorPropuesto)))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el3 + " no se puede proponer para un trato");
                Trato trato4 = new Trato((Propiedades) tablero.getCasillas().get(el1),Integer.parseInt(el2),(Propiedades) tablero.getCasillas().get(el3),"cambiar");
                break;
            case 6:
                if(!tablero.getCasillas().containsKey(el1))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if(!tablero.getCasillas().containsKey(el2))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                if(!tablero.getCasillas().containsKey(el3))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el3 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(el1)).perteneceAJugador(jugadorActual))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el1 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(el2)).perteneceAJugador(tablero.getJugadores().get(jugadorPropuesto)))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el2 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(el3)).perteneceAJugador(tablero.getJugadores().get(jugadorPropuesto)))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + el3 + " no se puede proponer para un trato");
                Trato trato6 = new Trato((Propiedades) tablero.getCasillas().get(el1),(Propiedades) tablero.getCasillas().get(el2),(Propiedades) tablero.getCasillas().get(el3),4,"cambiar");
                break;
        }
            /*if(parentesis){
                if(parte3.equals("")) {
                    if (parte2.charAt(0) >= 48 && parte2.charAt(0) <= 57) {
                        //Caso propiedad x dinero
                        if(!tablero.getCasillas().containsKey(parte1) || !((Propiedades) tablero.getCasillas().get(parte1)).perteneceAJugador(jugadorActual))
                            throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                        Trato trato = new Trato(Integer.parseInt(parte2),tablero.getJugadores().get(partes[1].substring(0,partes[1].length()-1)), (Propiedades) tablero.getCasillas().get(parte1),descripcion);
                    } else {
                        if (parte1.charAt(0) >= 48 && parte1.charAt(0) <= 57) {
                            //Caso dinero x propiedad
                            if(!tablero.getCasillas().containsKey(parte2) || !((Propiedades) tablero.getCasillas().get(parte2)).perteneceAJugador(tablero.getJugadores().get(partes[1].substring(0,partes[1].length()-1))))
                                throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                            if(jugadorActual.getDinero() < Integer.parseInt(parte1))
                                throw new ExcepcionDineroVoluntario("El jugador ofertor no dispone de dinero para ofrecer el trato");
                            Trato trato = new Trato(jugadorActual,Integer.parseInt(parte1), (Propiedades) tablero.getCasillas().get(parte2),descripcion);
                        } else {
                            if(parte1.contains("y")){
                                String[] partesParte1 = parte1.split(" ");
                                parte1 = partesParte1[0];
                                for(int i = 1; i < partesParte1.length-2; i++){
                                    parte1 += " " + partesParte1[i];
                                }
                                parte3 = partesParte1[partesParte1.length-1];
                                if(!tablero.getCasillas().containsKey(parte1))
                                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                                if(!tablero.getCasillas().containsKey(parte2))
                                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                                if(!((Propiedades) tablero.getCasillas().get(parte1)).perteneceAJugador(jugadorActual))
                                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                                if(!((Propiedades) tablero.getCasillas().get(parte2)).perteneceAJugador(tablero.getJugadores().get(partes[1].substring(0,partes[1].length()-1))))
                                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                                Trato trato = new Trato((Propiedades) tablero.getCasillas().get(parte1),Integer.parseInt(parte3),(Propiedades) tablero.getCasillas().get(parte2),descripcion);

                            }else {
                                if (!tablero.getCasillas().containsKey(parte1))
                                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                                if (!tablero.getCasillas().containsKey(parte2))
                                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                                if (jugadorActual.getPropiedades().contains((Propiedades) tablero.getCasillas().get(parte1))) {
                                    if (!((Propiedades) tablero.getCasillas().get(parte2)).perteneceAJugador(tablero.getJugadores().get(partes[1].substring(0, partes[1].length() - 1))))
                                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                                    Trato trato = new Trato((Propiedades) tablero.getCasillas().get(parte1), (Propiedades) tablero.getCasillas().get(parte2), descripcion);
                                } else {
                                    if (!((Propiedades) tablero.getCasillas().get(parte1)).perteneceAJugador(jugadorActual))
                                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                                    Trato trato = new Trato((Propiedades) tablero.getCasillas().get(parte2), (Propiedades) tablero.getCasillas().get(parte1), descripcion);
                                }
                            }
                        }
                    }
                }else{
                    if(!tablero.getCasillas().containsKey(parte1))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                    if(!tablero.getCasillas().containsKey(parte2))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                    if(!tablero.getCasillas().containsKey(parte3))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte3 + " no se puede proponer para un trato");
                    if(!((Propiedades) tablero.getCasillas().get(parte1)).perteneceAJugador(jugadorActual))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                    if(!((Propiedades) tablero.getCasillas().get(parte2)).perteneceAJugador(tablero.getJugadores().get(partes[1].substring(0,partes[1].length()-1))))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                    if(!((Propiedades) tablero.getCasillas().get(parte3)).perteneceAJugador(tablero.getJugadores().get(partes[1].substring(0,partes[1].length()-1))))
                        throw new ExcepcionRestriccionPropiedades("La propiedad " + parte3 + " no se puede proponer para un trato");
                    Trato trato = new Trato((Propiedades) tablero.getCasillas().get(parte1),(Propiedades) tablero.getCasillas().get(parte2),(Propiedades) tablero.getCasillas().get(parte3),turnos,descripcion);
                }
            }else{
                if(!tablero.getCasillas().containsKey(parte1))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                if(!tablero.getCasillas().containsKey(parte2))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(parte1)).perteneceAJugador(jugadorActual))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte1 + " no se puede proponer para un trato");
                if(!((Propiedades) tablero.getCasillas().get(parte2)).perteneceAJugador(tablero.getJugadores().get(partes[1].substring(0,partes[1].length()-1))))
                    throw new ExcepcionRestriccionPropiedades("La propiedad " + parte2 + " no se puede proponer para un trato");
                Trato trato = new Trato((Propiedades) tablero.getCasillas().get(parte1),Integer.parseInt(parte3),(Propiedades) tablero.getCasillas().get(parte2),descripcion);
            }
        }else{
            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
        }*/
    }

    public void aceptarTrato(String trato) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionPropiedades, ExcepcionDineroVoluntario{
        int idTrato = Integer.parseInt(trato.substring(5,6));
        for (Trato t : jugadorActual.getTratosPendientes()) {
            if (t.getId() == idTrato)
                t.aceptar();
            return;
        }
        throw new ExcepcionRestriccionPropiedades("El jugador " + jugadorActual.getNombre() + " no tiene ese trato pendiente");
    }

    public void borrarTrato(String trato){
        int idTrato = Integer.parseInt(trato.substring(5,6));
        for(Trato t: jugadorActual.getTratosPropuestos()){
            if(t.getId() == idTrato)
                t.eliminar();
            return;
        }
    }

    public Operacion getOperacion() {
        return operacion;
    }
}