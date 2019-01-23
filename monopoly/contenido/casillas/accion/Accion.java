package monopoly.contenido.casillas.accion;

import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Avatar;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.contenido.casillas.Casilla;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Valor;

public class Accion extends Casilla {

    public Accion(String nombre, int posicion){
        super(nombre,posicion);
    }

    public void accionCaer(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar {
        jugador.modificarDinero(Valor.getDineroAcumulado());
        jugador.modificarPremiosInversionesOBote(Valor.getDineroAcumulado());
        if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
            ((Esfinge)jugador.getAvatar()).modificarHistorialPremios(Valor.getDineroAcumulado());
        if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
            ((Sombrero)jugador.getAvatar()).modificarHistorialPremios(Valor.getDineroAcumulado());
        Juego.consola.anhadirTexto("El jugador " + jugador.getNombre() + "recibe " + Valor.getDineroAcumulado() + "€, el bote de la banca");
        Valor.setDineroAcumulado(0);
    }
    @Override
    public String toString(){
        if(this instanceof CasillasCarta) return super.toString();
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux +="Bote: "+Valor.getDineroAcumulado()+"\n"+
                "Jugadores: ";
        for (Avatar a: avatares){
            aux+="["+a.getJugador().getNombre()+"] ";
        }
        aux+="\n";
        aux+="}\n";
        return aux;
    }

}
