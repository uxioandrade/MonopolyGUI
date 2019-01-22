package monopoly.contenido.casillas;

import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;

public final class Impuesto extends Casilla { //Las clases hoja de una jerarquía deberían ser finales

    private double apagar;

    public Impuesto(String nombre, int posicion){
        super(nombre,posicion);
    }
    public void setApagar(double apagar){
        if(apagar>0)
            this.apagar=apagar;
    }

    public double getApagar() {
        return apagar;
    }

    public void pagarImpuesto(Jugador jugador, Operacion operacion) throws ExcepcionDineroDeuda {
        Juego.consola.imprimir(jugador.getNombre() + ",debes pagar un impuesto de " + apagar + " debido a " + jugador.getAvatar().getCasilla().getNombre());
        //Comprueba que el jugador tenga dinero suficiente para pagar
        if (apagar <= jugador.getDinero()) {
            jugador.modificarDinero(-apagar);
            Juego.consola.imprimir("Se han pagado " + apagar + "€ de impuesto");
            if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
                ((Esfinge)jugador.getAvatar()).modificarHistorialImpuestos(apagar);
            if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
                ((Sombrero)jugador.getAvatar()).modificarHistorialImpuestos(apagar);
            Valor.actualizarDineroAcumulado(apagar);
            jugador.modificarPagoImpuestos(apagar);
        } else {
            throw new ExcepcionDineroDeuda("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
        }
    }

    public void accionCaer(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionDineroDeuda{
        this.pagarImpuesto(jugador, operacion);
    }

    @Override
    public String toString(){
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux += "Tipo: " + "Impuestos" + "\n" +
                "A pagar: " + ((Impuesto)this).getApagar() + "\n";
        aux+="}\n";
        return aux;
    }
}
