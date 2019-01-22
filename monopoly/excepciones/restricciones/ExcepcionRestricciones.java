package monopoly.excepciones.restricciones;

import monopoly.excepciones.ExcepcionMonopoly;

public abstract class ExcepcionRestricciones extends ExcepcionMonopoly { //Es una clase intermedia pero que nunca se instancia, por eso es abstract

    public ExcepcionRestricciones(String mensaje) {
        super(mensaje);
    }

}
