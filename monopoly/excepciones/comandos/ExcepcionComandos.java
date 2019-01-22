package monopoly.excepciones.comandos;

import monopoly.excepciones.ExcepcionMonopoly;

public abstract class ExcepcionComandos extends ExcepcionMonopoly { //Es una clase intermedia pero que nunca se instancia, por eso es abstract

    public ExcepcionComandos(String mensaje) {
        super(mensaje);
    }

}
