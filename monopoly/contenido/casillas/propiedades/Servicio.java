package monopoly.contenido.casillas.propiedades;

import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Operacion;

public final class Servicio extends Propiedades { //Las clases hoja de una jerarquía deberían ser finales

    private static final double MULTIPLICADOR_ALQUILER_SERVICIO = 200;
    private static final double MULTIPLICADOR_PRECIO_SERVICIO = 0.75;
    private Valor valor;
    
    public Servicio(String nombre, int posicion, Valor valor) {
       super(nombre,posicion);
       super.setPrecio(MULTIPLICADOR_PRECIO_SERVICIO *valor.getDineroVuelta());
       this.valor = valor;
    }

    public double alquiler(int tirada){
        return valor.getDineroVuelta()*tirada/ MULTIPLICADOR_ALQUILER_SERVICIO;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionDineroDeuda {
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
                ((Esfinge)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
                ((Sombrero)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            Juego.consola.imprimir("Se han pagado " + this.alquiler(tirada) + "€ de servicio.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
        } else {
            try {
                operacion.menuHipotecar(jugador,operacion.getTablero(),this.alquiler(tirada));
            }catch(Exception ex){
                Juego.consola.anhadirTexto(ex.getMessage());
            }
            throw new ExcepcionDineroDeuda("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
        }
    }
    @Override
    public String toString(){
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux += "Tipo: " + "Servicios" + "\n" +
                "Precio: " +  Valor.df2.format(((Servicio) this).getPrecio()) + "\n" +
                "Uso Servicio: " + ((Servicio) this).alquiler(1)+" x suma de los dados" + "\n" +
                "Hipoteca: " +  Valor.df2.format(((Servicio) this).getHipoteca()) + "\n";
        if(!super.getPropietario().getNombre().equals("Banca"))
            aux += "Propietario: " + super.getPropietario().getNombre() + "\n";
        if(super.getHipotecado())
            aux += "Servicio hipotecado, paga " +  Valor.df2.format(1.1*super.getHipoteca()) + " para deshipotecar" + "\n";
        aux+="}\n";
        return aux;
    }
}
