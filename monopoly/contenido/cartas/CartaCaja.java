package monopoly.contenido.cartas;

import monopoly.contenido.Jugador;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Tablero;

public abstract class CartaCaja extends Carta{

    public CartaCaja(String descripcion) {
        super(descripcion);
    }

}
