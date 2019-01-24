package monopoly.excepciones.dinero;

import monopoly.excepciones.ExcepcionMonopoly;

public abstract class ExcepcionDinero extends ExcepcionMonopoly { //Es una clase intermedia pero que nunca se instancia, por eso es abstract

    public ExcepcionDinero(String mensaje) {
        super(mensaje);
    }

}
