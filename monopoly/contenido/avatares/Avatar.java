package monopoly.contenido.avatares;

import java.util.Iterator;
import java.util.Random;
import java.awt.image.*;

import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.Jugador;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;
public abstract class Avatar {  //La clase raíz de una jerarquía no se debería instanciar, por eso es abstracta

    private BufferedImage ficha;
    protected String id;
    protected Casilla casilla;
    protected Jugador jugador;
    protected Tablero tablero;
    protected int numVueltas;
    protected int encarcelado;
    protected boolean modoAvanzado;
    protected int numTiradas;

    public Avatar(Jugador jug, Tablero tablero, BufferedImage ficha){
        //Inicializamos el tipo a un valor arbitrario
        this.jugador = jug;
        this.tablero = tablero;
        this.ficha = ficha;
        this.casilla = Valor.casillas.get(0);
        asignarId();
        this.tablero.getAvatares().put(this.id,this);
        this.encarcelado = 0;
        this.numVueltas = 0;
        this.modoAvanzado = false;
    }

    //Función privada que asigna un id aleatorio no repetido a un avatar
    private void asignarId(){
        Random rnd = new Random();
        String aux = "";
        boolean repetida = true;
        while(repetida){
            aux =  "" + (char)(rnd.nextInt('Z' - 'A' + 1) + 'A');
            if(tablero.getAvatares() != null) {
                if (!tablero.getAvatares().containsKey(aux)) {
                    repetida = false;
                    this.id = aux;
                }
            }else{
                repetida = false;
                this.id = aux;
            }
        }
    }

    public String getId(){
        return this.id;
    }

    //No tiene sentido el setter de Id, pues es único e invariable a lo largo de la partida

    public BufferedImage getFicha(){
        return this.ficha;
    }

    public Casilla getCasilla(){
        return this.casilla;
    }

    public void setCasilla(Casilla cas){
        if(cas!=null){
            this.casilla.quitarAvatar(this);
            this.casilla = cas;
            this.casilla.anhadirAvatar(this);
        }
    }

    public int getNumTiradas() {
        return this.numTiradas;
    }

    public void setNumTiradas(int tiradas){
        if(tiradas >= -2)
            this.numTiradas = tiradas;
    }

    public void restarNumTiradas(){
        this.numTiradas--;
    }

    public void sumarNumTirada(){
        this.numTiradas++;
    }

    public Jugador getJugador(){
        return this.jugador;
    }

    //No tiene sentido crear el setter de jugador, ya que es el avatar se crea para un jugador en concreto, no tiene sentido modificarlo

    public Tablero getTablero() {
        return this.tablero;
    }
    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva

    public int getEncarcelado(){
        return this.encarcelado;
    }

    public void setEncarcelado(int valor){
        this.encarcelado = valor;
    }

    public void modificarEncarcelado(int valor){
        this.encarcelado += valor;
    }

    public int getNumVueltas(){
        return this.numVueltas;
    }

    public abstract String getTipo();

    //No es necesario el setter de numvueltas, ya que este solo se puede modificar de 1 en 1 cuando se completa una vuelta

    public boolean getModoAvanzado(){
        return modoAvanzado;
    }

    public void setModoAvanzado(boolean valor){
        this.modoAvanzado = valor;
    }

    public void switchMode(){
        this.modoAvanzado = !this.modoAvanzado;
    }

    public void moverCasilla(int valor) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        if(this.modoAvanzado)
            this.moverEnAvanzado(valor);
        else
            this.moverEnBasico(valor);
    }

    public abstract void moverEnAvanzado(int valor) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar  ;

    public void retrocederCasillas(int valor){
        this.getCasilla().quitarAvatar(this);
        if(this.getCasilla().getPosicion() - valor < 0){
            this.setCasilla(Valor.casillas.get(40 + this.getCasilla().getPosicion() - valor));
        }else{
            this.setCasilla(Valor.casillas.get(this.getCasilla().getPosicion() - valor));
        }
        this.getCasilla().anhadirAvatar(this);
    }

    void moverEnBasico(int valor) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar { //Acceso a paquete
            this.casilla.quitarAvatar(this);
            //Caso en el que el movimiento suponga completar una vuelta
            if(this.casilla.getPosicion() + valor > 39){
                this.casilla= Valor.casillas.get(this.casilla.getPosicion() + valor - 40);
                //Se le ingresa al jugador el dinero correspondiente a completar la vuelta
                this.jugador.modificarDinero(Valor.getDineroVuelta());
                this.jugador.modificarPasarPorCasilla(Valor.getDineroVuelta());
                this.numVueltas++;
                Juego.consola.anhadirTexto("El jugador " + this.jugador.getNombre() + " recibe " + Valor.getDineroVuelta() + "€ por haber cruzado la salida.");
                //Se recorren los avatares para comprobar si es necesario actualizar el dinero de pasar por la casilla de salida
                Iterator<Avatar> avatar_i = this.tablero.getAvatares().values().iterator();
                while(avatar_i.hasNext()) {
                    Avatar avatar = avatar_i.next();
                    if(avatar.numVueltas <= this.tablero.getVueltas() + 3) {
                        return;
                    }
                }
                this.tablero.modificarVueltas(4);
                Valor.actualizarVuelta();
            }else{
                this.casilla= Valor.casillas.get(this.casilla.getPosicion() + valor);
            }
            this.casilla.anhadirAvatar(this);
    }

    @Override
    public String toString(){
        String aux = "{\n" +
                "Id: " + this.id + "\n" +
                "Tipo: " + this.getTipo() + "\n" +
                "Jugador: " + this.jugador.getNombre() + "\n" +
                "Casilla: " + this.casilla.getNombre() + "\n" +
                "}\n";
        return aux;
    }
}
