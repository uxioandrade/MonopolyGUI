package monopoly.contenido.avatares;

import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public final class Esfinge extends Avatar{ //Las clases hija de una jerarquía deberían ser finales

    private double historialAlquileres; //Revisa
    private double historialCompras;
    private ArrayList<Propiedades> historialCompradas;
    private double historialSalida;
    private double historialImpuestos;
    private double historialPremios;

    public Esfinge(Jugador jug, Tablero tablero, BufferedImage ficha, Valor valor, Casilla salida){
        super(jug,tablero,ficha, valor, salida);
        super.numTiradas = 3;
        this.historialCompradas= new ArrayList<>();
    }

    public double getHistorialCompras() {
        return this.historialCompras;
    }
    public void setHistorialAlquileres(double alquiler){
        if(alquiler>0) this.historialAlquileres=alquiler;
    }

    public void modificarHistorialCompras(double valor){
        this.historialCompras += valor;
    }

    public void modificarHistorialImpuestos(double valor){
        this.historialImpuestos += valor;
    }

    public void modificarHistorialAlquileres(double valor){
        this.historialAlquileres += valor;
    }

    public void modificarHistorialPremios(double valor){
        this.historialPremios += valor;
    }

    public void modificarHistorialSalida(double valor){
        this.historialSalida += valor;
    }

    public void anhadirHistorialCompradas(Propiedades p){
        this.historialCompradas.add(p);
    }

    public void resetHistorial(){
        this.historialAlquileres=0; //Revisa
        this.historialCompras=0;
        this.historialSalida=0;
        this.historialPremios = 0;
        this.historialImpuestos = 0;
        this.historialCompradas.clear();
    }

    public void moverEnAvanzado(int valor) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        Operacion operacion = new Operacion(super.getTablero(), super.valor);
        if(valor > 4){
            this.resetHistorial();
            this.moverZigZag(valor);
            this.getCasilla().accionCaer(this.getJugador(), valor, operacion);
        }else {
            if(super.numTiradas==3){
                this.deshacerHistorial();
            }
            super.numTiradas = 0;
            Juego.consola.anhadirTexto("La esfinge ya ha acabado sus tiradas este turno");
        }
    }

    private void moverACasilla(int valor){
        this.getCasilla().quitarAvatar(this);
        this.setCasilla(casillasAux.get(valor));
        this.getCasilla().anhadirAvatar(this);
    }

    private void actualizarVueltaAvanzado(){
        this.jugador.modificarDinero(valor.getDineroVuelta());
        this.jugador.modificarPasarPorCasilla(valor.getDineroVuelta());
        this.numVueltas++;
        Juego.consola.anhadirTexto("El jugador " + this.jugador.getNombre() + " recibe " + valor.getDineroVuelta() + "€ por haber cruzado la salida.");
        //Se recorren los avatares para comprobar si es necesario actualizar el dinero de pasar por la casilla de salida
        Iterator<Avatar> avatar_i = this.tablero.getAvatares().values().iterator();
        while(avatar_i.hasNext()) {
            Avatar avatar = avatar_i.next();
            if(avatar.numVueltas <= this.tablero.getVueltas() + 3) {
                return;
            }
        }
        this.tablero.modificarVueltas(4);
        valor.actualizarVuelta();
        this.historialSalida += valor.getDineroVuelta();
    }

    private void moverZigZag(int valor) {
        if (this.getCasilla().getPosicion() < 10){
            if(valor + this.getCasilla().getPosicion() > 10){
                this.actualizarVueltaAvanzado();
            }
            if(valor % 2 == 0)
                this.moverACasilla((valor + this.getCasilla().getPosicion()) % 10);
            else
                this.moverACasilla(30 - ((valor + this.getCasilla().getPosicion()) % 10));
        } else if (this.getCasilla().getPosicion() < 20) {
            this.moverACasilla(21);
            if(valor % 2 == 0)
                this.moverACasilla(20 + ((valor - 1 + this.getCasilla().getPosicion() - 20) % 10));
            else
                this.moverACasilla(10 - ((valor - 1 + this.getCasilla().getPosicion() -20 ) % 10));
        } else if (this.getCasilla().getPosicion() < 30) {
            if(valor % 2 == 0)
                this.moverACasilla(20 + ((valor + this.getCasilla().getPosicion() - 20) % 10));
            else
                this.moverACasilla(10 - ((valor + this.getCasilla().getPosicion() -20 ) % 10));
        } else {
            this.moverACasilla(1);
            if(valor % 2 == 0)
                this.moverACasilla((valor - 1 + this.getCasilla().getPosicion()) % 10);
            else
                this.moverACasilla(30 - ((valor - 1 + this.getCasilla().getPosicion()) % 10));
        }
    }

    private void deshacerHistorial(){
        Juego.consola.anhadirTexto("Se desharán las acciones realizadas en la tirada anterior:");
        if(this.historialAlquileres>0) {
            super.getJugador().modificarDinero(this.historialAlquileres);
            super.getJugador().modificarPagoAlquileres(-this.historialAlquileres);
            Juego.consola.anhadirTexto("Se ha deshecho la accion pagar alquiler.");
            Juego.consola.anhadirTexto("Recuperas "+this.historialAlquileres+ ", tu fortuna aumenta a "+super.getJugador().getDinero());
        }
        if(this.historialSalida>0) {
            super.getJugador().modificarDinero(-this.historialSalida);
            super.getJugador().modificarPasarPorCasilla(-this.historialSalida);
            Juego.consola.anhadirTexto("Se ha deshecho la accion pasar por la casilla de salida.");
            Juego.consola.anhadirTexto("Pierdes "+ this.historialSalida+ ", tu fortuna se reduce a "+super.getJugador().getDinero());
        }
        if(this.historialImpuestos>0) {
            super.getJugador().modificarDinero(this.historialImpuestos);
            super.getJugador().modificarPagoImpuestos(-this.historialImpuestos);
            Juego.consola.anhadirTexto("Se ha deshecho la accion pagar impuesto.");
            Juego.consola.anhadirTexto("Recuperas "+this.historialImpuestos+ ", tu fortuna aumenta a "+super.getJugador().getDinero());
        }
        if(this.historialCompradas.size() >= 1){
            for(Propiedades p: this.historialCompradas) {
                super.getJugador().borrarPropiedad(p);
                Juego.consola.anhadirTexto("Se ha retirado la casilla " +  p.getNombre());
            }
            super.getJugador().modificarDinero(this.historialCompras);
            Juego.consola.anhadirTexto("Se han devuelto "  + this.historialCompras + "€.");
        }

        if(this.historialPremios != 0){
            if(this.historialPremios > 0) {
                super.getJugador().modificarDinero(-this.historialPremios);
                super.getJugador().modificarPremiosInversionesOBote(-this.historialPremios);
                Juego.consola.anhadirTexto("Se ha deshecho la accion pagar premio.");
                Juego.consola.anhadirTexto("Pierdes " + this.historialPremios + ", tu fortuna disminuie en " + super.getJugador().getDinero());
            }else{
                super.getJugador().modificarDinero(-this.historialPremios);
                super.getJugador().modificarPremiosInversionesOBote(-this.historialPremios);
                Juego.consola.anhadirTexto("Se ha deshecho la accion cobrar premio.");
                Juego.consola.anhadirTexto("Recuperas " + this.historialPremios + ", tu fortuna aumenta a " + super.getJugador().getDinero());

            }
        }

        resetHistorial();
    }

    public String getTipo(){ return "Esfinge";}

}
