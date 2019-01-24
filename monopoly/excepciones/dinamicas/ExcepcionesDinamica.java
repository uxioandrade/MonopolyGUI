package monopoly.excepciones.dinamicas;

import monopoly.excepciones.ExcepcionMonopoly;

public abstract class ExcepcionesDinamica extends ExcepcionMonopoly { //Es una clase intermedia pero que nunca se instancia, por eso es abstract

    public ExcepcionesDinamica(String mensaje) {
        super(mensaje);
    }

}
