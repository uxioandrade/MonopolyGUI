package monopoly.contenido.cartas;

import java.util.ArrayList;
import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

public abstract class Carta{ //La clase raíz de una jerarquía no se debería instanciar, por eso es abstracta
	
    public Carta(String descripcion, Valor valor){
        this.descripcion=descripcion;
        this.valor = valor;
        this.casillasAux = valor.getCasillas();
    }

    protected String descripcion;
    protected Valor valor;
    protected ArrayList<Casilla> casillasAux; 

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public abstract void  accionCarta(Jugador jugador, Tablero tablero) throws ExcepcionRestriccionHipotecar, ExcepcionNumeroPartesComando, ExcepcionDineroDeuda,ExcepcionRestriccionEdificar;
}
