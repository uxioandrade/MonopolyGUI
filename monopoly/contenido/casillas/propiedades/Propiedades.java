package monopoly.contenido.casillas.propiedades;

import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;

import java.util.HashMap;

public abstract class Propiedades extends Casilla { //Es una clase intermedia pero que nunca se instancia, por eso es abstract

    protected double precio;
    protected boolean hipotecado;
    protected Jugador propietario;
    protected double rentabilidad;
    protected HashMap<Jugador,Integer> jugadoresExcluidos;

    public Propiedades(String nombre, int posicion){
        super(nombre,posicion);
        this.jugadoresExcluidos = new HashMap<>();
    }

    public Jugador getPropietario(){
        return this.propietario;
}
    public void setPropietario(Jugador propietario){
        if(propietario != null){
            if(this.propietario != null) {
                if (!this.propietario.getNombre().equals("Banca"))
                    this.propietario.borrarPropiedad(this);
            }
            this.propietario=propietario;
            if(!propietario.getPropiedades().contains(this))
            this.propietario.anhadirPropiedad(this);
        }
    }

    public double getHipoteca(){
        return this.precio*0.5;
    }

    public boolean getHipotecado(){ return this.hipotecado; }

    public void setHipotecado(boolean hipotecado){
        this.hipotecado = hipotecado;
    }

    public void setPrecio(double precio){
        if(precio > 0.00000)
            this.precio = precio;
    }

    public double getRentabilidad(){
        return this.rentabilidad;
    }

    public void sumarRentabilidad(double valor){
        if(valor > 0)
            this.rentabilidad += valor;
    }

    public double getPrecio(){
        return this.precio;
    }

    public abstract double alquiler(int tirada);

    public abstract void pagarAlquiler(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionDineroDeuda;

    public void accionCaer(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionDineroDeuda{
        if (!propietario.equals(operacion.getTablero().getBanca()) && !propietario.equals(jugador) && !this.hipotecado){
            if(this.jugadoresExcluidos.containsKey(jugador)){
                Juego.consola.imprimir("El jugador " + jugador.getNombre() + " no tiene que pagar este alquiler durante " + this.jugadoresExcluidos.get(jugador) + " turnos.");
            }else
                this.pagarAlquiler(jugador, tirada, operacion);

        }
    }

    public HashMap<Jugador,Integer> getJugadoresExcluidos(){
        return this.jugadoresExcluidos;
    }

    public void anhadirJugadorExcluido(Jugador jugador, int turnos){
        if(!this.jugadoresExcluidos.containsKey(jugador))
            this.jugadoresExcluidos.put(jugador,turnos);
        else
            this.jugadoresExcluidos.replace(jugador,turnos + this.jugadoresExcluidos.get(jugador) + turnos);
    }

    public void reducirTurnosTratos(Jugador jugador){
        if(this.jugadoresExcluidos.containsKey(jugador)){
            this.jugadoresExcluidos.replace(jugador,this.jugadoresExcluidos.get(jugador)-1);
            if(this.jugadoresExcluidos.get(jugador) == 0)
                this.jugadoresExcluidos.remove(jugador);
        }
    }

    public boolean perteneceAJugador(Jugador jugador){
        return this.propietario.equals(jugador);
    }

    public double valor(){return this.precio;}

    public void comprar(Jugador jugador) throws ExcepcionDineroVoluntario{
        //Caso en el que el jugador tiene menos dinero que el precio del solar
        if (this.getPrecio() > jugador.getDinero()) {
            throw new ExcepcionDineroVoluntario("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades o a declararte en bancarrota");
        }
        //Se disminuye el dinero del jugador
        jugador.modificarDinero(-this.getPrecio());
        jugador.modificarDineroInvertido(this.getPrecio());
        //Añade la casilla a sus propiedades
        jugador.anhadirPropiedad(this);
        //Añade el propietario a la casilla
        this.setPropietario(jugador);
    }

}
