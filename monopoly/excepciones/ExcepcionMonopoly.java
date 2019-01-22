package monopoly.excepciones;

import monopoly.plataforma.Valor;

public abstract class ExcepcionMonopoly extends Exception{ //La clase raíz de una jerarquía no se debería instanciar, por eso es abstracta

    private final String mensaje;

    public ExcepcionMonopoly(String mensaje) {
        this.mensaje = Valor.ANSI_ROJO + mensaje + "\033[0;1m";
    }

    public String getMensaje() {
        return mensaje;
    }

}
