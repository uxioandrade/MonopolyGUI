package monopoly.contenido.cartas;

import monopoly.contenido.Jugador;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Tablero;

public abstract class Carta{ //La clase raíz de una jerarquía no se debería instanciar, por eso es abstracta

    public Carta(String descripcion){
        this.descripcion=descripcion;
    }

    protected String descripcion;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public abstract void  accionCarta(Jugador jugador, Tablero tablero) throws ExcepcionRestriccionHipotecar, ExcepcionNumeroPartesComando, ExcepcionDineroDeuda,ExcepcionRestriccionEdificar;
}
