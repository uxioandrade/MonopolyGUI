package monopoly.contenido.casillas;

import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Avatar;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Valor;

public final class Especiales extends Casilla{ //Las clases hoja de una jerarquía deberían ser finales

    public Especiales(String nombre, int posicion) {
        super(nombre, posicion);
    }

    public void accionCaer(Jugador jugador, int tirada, Operacion operacion){
        if(super.getPosicion() == 30) {
            operacion.irCarcel(jugador);
        }
    }
    @Override
    public String toString() {
        String aux = super.toString().substring(0, super.toString().length() - 2);
        if (this.posicion == 10) {
            aux += "salir: " + Valor.getDineroSalirCarcel() + "\n" +
                    "Jugadores:";
            for (Avatar a : avatares) {
                if (a.getEncarcelado() > 0)
                    aux += "[" + a.getJugador().getNombre() + ", " + a.getEncarcelado() + "] ";
            }
            aux += "\n";
            aux += "}\n";
            return aux;
        }
        return super.toString();
    }
}
