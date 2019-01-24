package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Scanner;

import interfazgrafica.InterfazGrafica;
import monopoly.contenido.*;
import monopoly.contenido.avatares.Coche;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.edificios.Edificios;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinamicas.ExcepcionesDinamicaEncarcelamiento;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;

import javax.swing.*;

public class Operacion {
    private Tablero tablero;
    private Valor valor;
    private InterfazGrafica interfazGrafica;
    private ArrayList<Casilla> casillasAux;

    public Operacion(Tablero tablero, Valor valor) {
        if (tablero != null) {
            this.tablero = tablero;
        }
        if(valor != null) {
        	this.valor = valor;
        	casillasAux = valor.getCasillas();
        }
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva

    /*public void crearJugadores() {
        int empezar = 1; //Almacena el número de jugadores
        Juego.turnosJugadores = new ArrayList<>();
        while (empezar <= 6) {
            String orden = Juego.consola.leer("$>");
            String[] partes = orden.split(" ");
            String comando = partes[0];
            switch (comando) {
                case "introducir":
                    if (partes.length == 4 && partes[1].equals("jugador")) {
                        if (this.tablero.getJugadores().containsKey(partes[2])) {
                            Juego.consola.imprimir("Error: No puede haber dos jugadores con el mismo nombre");
                        } else {
                            Jugador jugadorIntroducir = new Jugador(partes[2], partes[3], this.tablero);
                            //Se añade los jugadores al HashMap de tableros
                            this.tablero.addJugadores(jugadorIntroducir);
                            //Se añade el jugador al ArrayList que almacena el orden de los jugadores
                            Juego.turnosJugadores.add(jugadorIntroducir.getNombre());
                            //Se aumenta el número de jugadores
                            empezar++;
                            Juego.consola.imprimir(partes[2] + " se ha unido a la partida");
                            Juego.consola.imprimir("Informacion sobre el Jugador");
                            Juego.consola.imprimir(jugadorIntroducir.getDescripcionInicial());
                            this.tablero.imprimirTablero();
                            if(empezar >= 7)
                                Juego.consola.imprimir("El número máximo de participantes ha sido alcanzado, la partida, si es menester de todos los jugadores involucrados, será dada comenzamiento");
                        }
                        break;
                    }
                case "empezar":
                    if (partes.length == 2 && partes[1].equals("partida")) {
                        if (empezar <= 2)
                            Juego.consola.imprimir("El número mínimo de jugadores es 2");
                        else
                            empezar = 7; //Modifica el valor de empezar para romper el bucle
                        break;
                    }
                default:
                    Juego.consola.imprimir("Comando incorrecto, prueba con:");
                    Juego.consola.imprimir(">introducir jugador <nombre> <tipo> para crear un jugador");
                    Juego.consola.imprimir(">empezar partida    para comenzar la partida");
            }
        }

    }*/

    public void setInterfazGrafica(InterfazGrafica interfaz){
        this.interfazGrafica = interfaz;
    }

    public InterfazGrafica getInterfazGrafica(){
        return this.interfazGrafica;
    }

    public void comprar(Jugador jugador) throws ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        Propiedades comprable;
        if(!(jugador.getAvatar().getCasilla() instanceof Propiedades)){
            throw new ExcepcionRestriccionComprar("Esta casilla no se puede comprar");
        }

        //Caso especial en el que el avatar sea un coche
        if(jugador.getAvatar() instanceof Coche && jugador.getAvatar().getModoAvanzado()){
            if(!((Coche)jugador.getAvatar()).getPoderComprar()){
                throw new ExcepcionRestriccionComprar("Un coche no puede comprar más de una vez cada turno");
            }
        }

        comprable = (Propiedades) jugador.getAvatar().getCasilla();
        //Caso en el que la propiedad ya está adquirida
        if (!comprable.getPropietario().equals(this.tablero.getBanca())) {
            throw new ExcepcionRestriccionComprar("Error. Propiedad ya adquirida, para comprarla debes negociar con " + comprable.getPropietario().getNombre());
        }

        //Caso en el que el jugador tiene menos dinero que el precio del solar
        if (comprable.getPrecio() > jugador.getDinero()) {
            throw new ExcepcionDineroVoluntario("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades o a declararte en bancarrota");
        }

        comprable.comprar(jugador);

        if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado()) {
            ((Esfinge) jugador.getAvatar()).modificarHistorialCompras(comprable.getPrecio());
            ((Esfinge) jugador.getAvatar()).anhadirHistorialCompradas(comprable);
        }
        if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado()) {
            ((Sombrero) jugador.getAvatar()).modificarHistorialCompras(comprable.getPrecio());
            ((Sombrero) jugador.getAvatar()).anhadirHistorialCompradas(comprable);
        }
        //Caso en el que el avatar es un coche: no puede volver a comprar en el turno
        if(jugador.getAvatar() instanceof Coche){
            ((Coche)jugador.getAvatar()).setPoderComprar(false);
        }

        this.tablero.getPanelTexto().addTexto("Operación realizada con éxito");
        this.tablero.getPanelTexto().addTexto(  jugador.getAvatar().getCasilla().getNombre() + " se ha anadido a tu lista de propiedades" + "\n" + "Tu saldo actual es de: " + Valor.df2.format(jugador.getDinero()) + "€" + "\n" + "El jugador " + jugador.getNombre() + " compra la casilla " + jugador.getAvatar().getCasilla().getNombre() + " por " + Valor.df2.format(comprable.getPrecio()) + "€.");
    }

    public void edificar(Jugador jugador, String tipo) throws ExcepcionDineroVoluntario, ExcepcionRestriccionEdificar {
        if(jugador.getAvatar().getCasilla() instanceof Solar)
            ((Solar)jugador.getAvatar().getCasilla()).edificar(tipo, this.tablero);
        else
            throw new ExcepcionRestriccionEdificar("No es un solar");
    }

    public void venderConstrucciones(Jugador vendedor, Casilla propiedad, String tipo, int num) throws ExcepcionRestriccionEdificar {
        int vendidas = 0;
        double total = 0;
        int i = 0;
        if(!(propiedad instanceof Solar)){
            throw new ExcepcionRestriccionEdificar("Esta casilla no puede contener edificaciones");
        }
        if((vendedor.getPropiedades().contains((Propiedades)propiedad))){
            if(((Solar) propiedad).getConstrucciones(tipo)==null){
                throw new ExcepcionRestriccionEdificar("Ese tipo de construcciones no existe");
            }
            int tamanho = ((Solar) propiedad).getConstrucciones(tipo).size();
            for(i = 0; i<tamanho && i<num;i++){
                total += ((Solar) propiedad).getConstrucciones(tipo).get(0).getPrecio()*0.5;
                vendedor.modificarDinero(((Solar) propiedad).getConstrucciones(tipo).get(0).getPrecio()*0.5);
                this.tablero.borrarEdificio(((Solar) propiedad).getConstrucciones(tipo).remove(0));
                ((Solar) propiedad).getConstrucciones().remove(((Solar) propiedad).getConstrucciones(tipo).remove(0));
            }
            if(i == 0)
                this.tablero.getPanelTexto().addTexto(vendedor.getNombre() + " no ha vendido " + tipo + " en " + propiedad.getNombre() + " porque no ha construido");
            else if(i==num)
                this.tablero.getPanelTexto().addTexto(vendedor.getNombre() + " ha vendido " + num + " " + tipo + " en " + propiedad.getNombre() +
                        ", recibiendo " + Valor.df2.format(total) + "€. En la propiedad queda" + ((Solar) propiedad).getConstrucciones(tipo).size() + " " + tipo + ".");
            else
                this.tablero.getPanelTexto().addTexto(vendedor.getNombre() + " solamente ha podido vender " + i + " " + tipo + " en " + propiedad.getNombre() +
                    ", recibiendo " + Valor.df2.format(total) + "€. ");

        }else{
            throw new ExcepcionRestriccionEdificar("EL jugador "+ vendedor.getNombre() + " no posee la casilla "+propiedad.getNombre());
        }
    }

    public void hipotecar(Casilla propiedad, Jugador jugActual) throws ExcepcionRestriccionHipotecar {
        Propiedades comprable;
        if(!(propiedad instanceof Propiedades)){
            throw new ExcepcionRestriccionHipotecar("Esta casilla no se puede hipotecar");
        }

        comprable = (Propiedades) propiedad;
        if(!jugActual.getPropiedades().contains(comprable)){
            throw new ExcepcionRestriccionHipotecar("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre()+" porque no le pertenece");
        }
        if(comprable.getHipotecado()){
            throw new ExcepcionRestriccionHipotecar("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre()+" porque ya está hipotecada");
        }
        if(propiedad instanceof Solar){
            if(((Solar)propiedad).getConstrucciones().size()>0){
                throw new ExcepcionRestriccionHipotecar("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre()+", antes debe vender los edificios de la misma");
            }
        }
        jugActual.modificarDinero(comprable.getHipoteca());
        comprable.setHipotecado(true);
        Juego.consola.anhadirTexto("El jugador " + jugActual.getNombre() + " ha hipotecado la propiedad " + comprable.getNombre()+ " recibiendo así "+ Valor.df2.format(comprable.getHipoteca())+"$");

    }

    public void desHipotecar(Casilla propiedad, Jugador jugActual) throws ExcepcionRestriccionHipotecar, ExcepcionDineroVoluntario{
        Propiedades comprable;
        if(!(propiedad instanceof Propiedades)){
            throw new ExcepcionRestriccionHipotecar("Esta casilla no se puede deshipotecar");
        }
        comprable = (Propiedades) propiedad;
        //Comprueba que el jugador tenga la propiedad actual
        if (jugActual.getPropiedades().contains(comprable) && comprable.getHipotecado()) {
            if(jugActual.getDinero() >= comprable.getHipoteca()*1.1) {
                jugActual.modificarDinero(-1.1 * comprable.getHipoteca());
                comprable.setHipotecado(false);
                Juego.consola.anhadirTexto("El jugador " + jugActual.getNombre() + " ha deshipotecado la propiedad " + comprable.getNombre() + ", aportando un capital de " + 1.1 * comprable.getHipoteca() + "€.");
            }else
               throw new ExcepcionDineroVoluntario("El jugador " + jugActual.getNombre() + " no tiene dinero suficiente para deshipotecar la propiedad " + comprable.getNombre());
        } else {
            throw new ExcepcionRestriccionHipotecar("El jugador " + jugActual.getNombre() + " no puede deshipotecar la propiedad " + propiedad.getNombre());
        }
    }

    public boolean menuHipotecar(Jugador jugador,Tablero tablero, double deuda) throws ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar{ //Devuelve true si el jugador ya ha afrontado su deuda
        Juego.consola.anhadirTexto("El jugador " + jugador.getNombre() + " no tiene dinero suficiente");
        Juego.consola.anhadirTexto("Serás declarado en banca rota y eliminado de la partida");

        Propiedades comprable;
        for (Propiedades cas : this.interfazGrafica.getJuego().getJugadorActual().getPropiedades()) {
            if (cas instanceof Solar) {
                for (Edificios ed : ((Solar) cas).getConstrucciones()) {
                    this.interfazGrafica.getJuego().getTablero().borrarEdificio(ed);
                    ((Solar) cas).getConstrucciones().remove(ed);
                }
            }
            cas.setPropietario(this.interfazGrafica.getJuego().getTablero().getBanca());
            cas.setHipotecado(false);
        }
        this.interfazGrafica.getPanelTablero().getCasillas().get(this.interfazGrafica.getJuego().getJugadorActual().getAvatar().getCasilla().getPosicion()).deleteFicha(this.interfazGrafica.getJuego().getTurnosJugadores().indexOf(this.interfazGrafica.getJuego().getJugadorActual()));
        this.interfazGrafica.getJuego().getTablero().getAvatares().remove(this.interfazGrafica.getJuego().getJugadorActual().getAvatar().getId());
        this.interfazGrafica.getJuego().getTablero().getJugadores().remove(this.interfazGrafica.getJuego().getJugadorActual().getNombre());

        this.interfazGrafica.getJuego().getTurnosJugadores().remove(this.interfazGrafica.getJuego().getJugadorActual().getNombre());

        if (this.interfazGrafica.getJuego().getTurnosJugadores().size() == 1) {
            this.interfazGrafica.getPanelTexto().addTexto("Partida acabada!\n Enhorabuena " + this.interfazGrafica.getJuego().getTurnosJugadores().get(0) + ", eres el ganador!!!!");
            System.exit(0);
        }
        /*
        Juego.consola.anhadirTexto(jugador.getNombre() + " entró en el menú hipotecar");
        Operacion operacion = new Operacion(tablero, valor);
        while(true) {
            String orden = Juego.consola.leer("$>");
            String[] partes = orden.split(" ");
            String comando = partes[0];
            try {
                switch (comando) {
                    case "hipotecar":
                        String auxCasilla = "";
                        for (int i = 1; i < partes.length - 1; i++) {
                            auxCasilla += partes[i] + " ";
                        }
                        if (partes.length < 2 || partes.length > 4) throw new ExcepcionNumeroPartesComando("\n Comando incorrecto");
                        else if (this.tablero.getCasillas().get(auxCasilla + partes[partes.length - 1]) != null) {//si existe la casilla
                            operacion.hipotecar(this.tablero.getCasillas().get(auxCasilla + partes[partes.length - 1]), jugador);
                            if (jugador.getDinero() >= deuda) {
                                Juego.consola.anhadirTexto("El jugador " + jugador.getNombre() + " ya tiene dinero suficiente para afrontar su deuda");
                                return true;
                            } else {
                                Juego.consola.anhadirTexto("El jugador " + jugador.getNombre() + " aún no tiene dinero suficiente");
                            }
                        } else throw new ExcepcionRestriccionHipotecar("La casilla que quieres hipotecar no existe");
                        break;
                    case "vender":
                        if (partes.length >= 4) {
                            auxCasilla = "";
                            for (int i = 2; i < partes.length - 2; i++) {
                                auxCasilla += partes[i] + " ";
                            }
                            operacion.venderConstrucciones(jugador, this.tablero.getCasillas().get(auxCasilla + partes[partes.length - 2]), partes[1], partes[partes.length - 1].toCharArray()[0] - '0');
                        } else {
                            throw new ExcepcionNumeroPartesComando("Comando incorrecto");
                        }
                        break;
                    case "listar":
                        for (Propiedades c : jugador.getPropiedades()) {
                            Juego.consola.anhadirTexto(c.toString());
                        }
                        break;
                    case "bancarrota":
                        Propiedades comprable;
                        for (Propiedades cas : jugador.getPropiedades()) {
                            if (cas instanceof Solar) {
                                for (Edificios ed : ((Solar) cas).getConstrucciones()) {
                                    this.tablero.borrarEdificio(ed);
                                    ((Solar) cas).getConstrucciones().remove(ed);
                                }
                            }
                            cas.setPropietario(this.tablero.getBanca());
                            cas.setHipotecado(false);
                        }
                        casillasAux.get(jugador.getAvatar().getCasilla().getPosicion()).quitarAvatar(jugador.getAvatar());
                        this.tablero.getAvatares().remove(jugador.getAvatar().getId());
                        this.tablero.getJugadores().remove(jugador.getNombre());

                       // Juego.turnosJugadores.remove(jugador.getNombre());
                      //  if (Juego.turnosJugadores.size() == 1) {
                      //      Juego.consola.imprimir("Partida acabada!\n Enhorabuena " + Juego.turnosJugadores.get(0) + ", eres el ganador!!!!");
                       //     System.exit(0);
                      //  }
                        return false;
                }
            }catch(ExcepcionRestriccionHipotecar | ExcepcionNumeroPartesComando ex2){
                Juego.consola.anhadirTexto(ex2.getMensaje());
            }
        }
        */
        return true;
    }

    public void salirCarcel(Jugador jugador) throws ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionesDinamicaEncarcelamiento {
        //Comprueba que el jugador esté en la cárcel
        if(jugador.getAvatar().getEncarcelado()>0 && jugador.getAvatar().getEncarcelado()<3){
            //Comprueba que el jugador tenga dinero para pagar la fianza
            if(jugador.getDinero()>valor.getDineroSalirCarcel()){
                jugador.getAvatar().setEncarcelado(0);
                jugador.modificarDinero(-valor.getDineroSalirCarcel());
                Juego.consola.anhadirTexto(jugador.getNombre() + " paga " + valor.getDineroSalirCarcel() +"€ y sale de la cárcel. Puede lanzar los dados");
            }else {
                throw new ExcepcionDineroVoluntario(jugador.getNombre() + " no tiene dinero suficiente para salir de la cárcel");
            }
        //Caso en el que el jugador ya superó los 3 turnos en la cárcel
        }else if(jugador.getAvatar().getEncarcelado() >= 4){
            if(jugador.getDinero() >= valor.getDineroSalirCarcel()) {
                jugador.getAvatar().setEncarcelado(0);
                jugador.modificarDinero(-valor.getDineroSalirCarcel());
                Juego.consola.anhadirTexto("El jugador " + jugador.getNombre() + " paga " + valor.getDineroSalirCarcel() + "€ y sale de la cárcel.");
            }else{
                if(this.menuHipotecar(jugador,this.tablero,valor.getDineroSalirCarcel()))
                    salirCarcel(jugador);
            }
        }else{
            throw new ExcepcionesDinamicaEncarcelamiento("El jugador " + jugador.getNombre() + " no está en la cárcel");
        }
    }

    public void irCarcel(Jugador jugador) {
        //Modifica la posicion del jugador
        jugador.getAvatar().setCasilla(casillasAux.get(10));
        //Modifica el estado del jugador
        jugador.getAvatar().setEncarcelado(1);
        jugador.anhadirVecesCarcel();
        Juego.consola.anhadirTexto(jugador.getNombre() + " va a la cárcel.");
    }

}
