package monopoly.excepciones;

import monopoly.plataforma.Valor;

public abstract class ExcepcionMonopoly extends Exception{ //La clase raíz de una jerarquía no se debería instanciar, por eso es abstracta

    private final String mensaje;

    public ExcepcionMonopoly(String mensaje) {
        this.mensaje =  mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
