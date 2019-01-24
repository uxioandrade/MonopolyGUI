package monopoly.contenido.avatares;

import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Valor;

import java.awt.image.BufferedImage;

public final class Coche extends Avatar{  //Las clases hoja de una jerarquía deberían ser finales

    private boolean poderComprar;

    public Coche(Jugador jug, Tablero tablero, BufferedImage ficha, Valor valor, Casilla salida){
        super(jug,tablero,ficha, valor, salida);
        super.numTiradas = 3;
        this.poderComprar = true;
    }

    public void moverEnAvanzado(int valor) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        Operacion operacion = new Operacion(this.getTablero(), super.valor);
        if(super.numTiradas > 0) {
            if (valor > 4) {
                if (super.numTiradas == 1) {
                    super.numTiradas = 4;
                    Juego.consola.anhadirTexto("El coche aún puede realizar " + (super.numTiradas - 1) + " tiradas este turno");
                }else if (super.numTiradas == 2){
                    super.numTiradas = 0;
                    Juego.consola.anhadirTexto("El coche ha acabado sus tiradas en este turno");
                }else{
                    super.numTiradas--;
                    Juego.consola.anhadirTexto("El coche aún puede realizar " + (super.numTiradas - 1) + " tiradas este turno");
                }
                Juego.consola.anhadirTexto("El avatar " + this.getId() + " avanza " + valor + " posiciones, desde " + this.getCasilla().getNombre() + " hasta " + casillasAux.get((this.getCasilla().getPosicion() + valor) % 40).getNombre());
                this.moverEnBasico(valor);
                this.getCasilla().accionCaer(this.getJugador(), valor, operacion);
            } else {
                if (super.numTiradas == 1) {
                    this.retrocederCasillas(valor);
                    this.getCasilla().accionCaer(this.getJugador(), valor, operacion);
                    super.numTiradas = -2;
                    Juego.consola.anhadirTexto("El coche ha obtenido un valor menor o igual de 4, retrocede " + valor + " casillas y estará 2 turnos sin poder tirar");
                } else {
                    super.numTiradas = 0;
                    Juego.consola.anhadirTexto("El valor obtenido ha sido menor de 4, el coche no puede tirar más veces");
                }
            }
        }else
            Juego.consola.anhadirTexto(this.getJugador().getNombre() + " sigue sin poder tirar");
    }

    public boolean getPoderComprar(){
        return this.poderComprar;
    }

    public void setPoderComprar(boolean valor){
        this.poderComprar = valor;
    }

    public String getTipo(){ return "Coche";}

}
