package monopoly.excepciones.comandos;

public final class ExcepcionComandoIncorrecto extends ExcepcionComandos{ //Las clases hoja de una jerarquía deberían ser finales

    public ExcepcionComandoIncorrecto(String mensaje) {
        super(mensaje);
    }

}
