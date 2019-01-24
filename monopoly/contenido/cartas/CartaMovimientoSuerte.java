package monopoly.contenido.cartas;

import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Avatar;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.Iterator;

public final class CartaMovimientoSuerte extends CartaSuerte{ //Las clases hoja de una jerarquía deberían ser finales

    private int posicion;
    private boolean accionFinanciera;
    private int alquiler; //0 si no hay alquiler, 1 si hay alquiler y 2 si hay que pagar el doble del alquiler

    public CartaMovimientoSuerte(int posicion, boolean accionFinanciera, int alquiler, String descripcion, Valor valor){
        super(descripcion, valor);
        if(posicion >= -2 && posicion <= 39)
            this.posicion = posicion;
        this.accionFinanciera = accionFinanciera;
        if(alquiler >= 0 && alquiler <= 2)
            this.alquiler = alquiler;
    }

    public int getPosicion(){
        return this.posicion;
    }

    public boolean getAccionFinanciera(){
        return this.accionFinanciera;
    }

    public int getAlquiler(){
        return this.alquiler;
    }

    //Los setters no son necesarios, pues cada carta es inmutable una vez creada

    private void cobrarAccion(Jugador jugador, Tablero tablero, double cantidad){
        //Se resta el alquiler del jugador que ha caído en el servicio
        jugador.modificarDinero(-cantidad);
        jugador.modificarPagoAlquileres(cantidad);
        Juego.consola.anhadirTexto("Se han pagado " + cantidad + "€ de alquiler.");
        //Se aumenta el dinero del propietario
        Propiedades casillaComprable;
        casillaComprable = (Propiedades) jugador.getAvatar().getCasilla();
        casillaComprable.getPropietario().modificarDinero(cantidad);
        if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
            ((Esfinge)jugador.getAvatar()).modificarHistorialAlquileres(cantidad);
        if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
            ((Sombrero)jugador.getAvatar()).modificarHistorialAlquileres(cantidad);
    }

    private void actualizarVueltaAvanzado(Jugador jugador, Tablero tablero){
        jugador.modificarDinero(valor.getDineroVuelta());
        jugador.modificarPasarPorCasilla(valor.getDineroVuelta());
        jugador.getAvatar().sumarNumTirada();
        Juego.consola.anhadirTexto("El jugador " + jugador.getNombre() + " recibe " + valor.getDineroVuelta() + "€ por haber cruzado la salida.");
        //Se recorren los avatares para comprobar si es necesario actualizar el dinero de pasar por la casilla de salida
        Iterator<Avatar> avatar_i = tablero.getAvatares().values().iterator();
        while(avatar_i.hasNext()) {
            Avatar avatar = avatar_i.next();
            if(avatar.getNumVueltas() <= tablero.getVueltas() + 3) {
                return;
            }
        }
        tablero.modificarVueltas(4);
        valor.actualizarVuelta();
        if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
            ((Esfinge)jugador.getAvatar()).modificarHistorialSalida(valor.getDineroVuelta());
        if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
            ((Sombrero)jugador.getAvatar()).modificarHistorialSalida(valor.getDineroVuelta());
    }

    public void accionCarta(Jugador jugador, Tablero tablero) throws ExcepcionDineroDeuda, ExcepcionNumeroPartesComando, ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar {
        if(this.posicion == -1) {
            if(jugador.getAvatar().getCasilla().getPosicion() - 3 >= 0){
                this.posicion = jugador.getAvatar().getCasilla().getPosicion() - 3;
            }else{
                this.posicion = jugador.getAvatar().getCasilla().getPosicion() - 3 + 40;
            }
        }else if(this.posicion == -2){
            if(jugador.getAvatar().getCasilla().getPosicion() >= 35 || jugador.getAvatar().getCasilla().getPosicion() <= 4){
                this.posicion = 5;
            }else if(jugador.getAvatar().getCasilla().getPosicion() >= 5 && jugador.getAvatar().getCasilla().getPosicion() <= 14){
                this.posicion = 15;
            }else if(jugador.getAvatar().getCasilla().getPosicion() >= 15 && jugador.getAvatar().getCasilla().getPosicion() <= 24){
                this.posicion = 25;
            }else if(jugador.getAvatar().getCasilla().getPosicion() >= 25 && jugador.getAvatar().getCasilla().getPosicion() <= 34){
                this.posicion = 35;
            }
        }

        if(accionFinanciera)
            Juego.consola.anhadirTexto(super.getDescripcion() + " " + valor.getDineroVuelta() + "€.");
        else
            Juego.consola.anhadirTexto(super.getDescripcion());

        Propiedades casillaComprable;
        Operacion operacion = new Operacion(tablero, valor);
        //Siempre se cae en una casilla que tiene un alquiler asociado
        if(this.accionFinanciera && this.posicion <= jugador.getAvatar().getCasilla().getPosicion()) {
            this.actualizarVueltaAvanzado(jugador,operacion.getTablero());
        }
        jugador.getAvatar().setCasilla(valor.getCasillas().get(this.posicion));
        if(jugador.getAvatar().getCasilla() instanceof Propiedades) {
            casillaComprable = (Propiedades) jugador.getAvatar().getCasilla();
            switch (alquiler) {
                case 0:
                    break;
                case 1:
                    casillaComprable.accionCaer(jugador, 1, operacion);
                    break;
                case 2:
                    double cantidadDoble = casillaComprable.alquiler(1) * 2;
                    if (jugador.getDinero() >= cantidadDoble) {
                        cobrarAccion(jugador, tablero, cantidadDoble);
                    } else {
                        Juego.consola.anhadirTexto("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                        if (operacion.menuHipotecar(jugador, tablero, casillaComprable.alquiler(1) * 2)) {
                            cobrarAccion(jugador, tablero, cantidadDoble);
                        }
                    }
                    break;
            }
        }else{
            jugador.getAvatar().getCasilla().accionCaer(jugador,1, operacion);
        }
    }

    public void setPosicion(int pos){
        this.posicion = pos;
    }

}

