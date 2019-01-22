package monopoly.plataforma;

import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.contenido.Jugador;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;

import java.util.ArrayList;

public class Trato {
    private ArrayList<Propiedades> propiedadesOfertadas; //Ofertada->0 Recibida->1 No Pagar->2
    private Jugador ofertor;
    private Jugador receptor;
    private double cantidad;
    private int turnos;
    private int id;
    private String descripcion;

    public Trato(Propiedades ofertada, Propiedades recibida, String descripcion){ //Propiedad x propiedad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(ofertada);
        this.propiedadesOfertadas.add(recibida);
        this.ofertor = ofertada.getPropietario();
        this.receptor = recibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
        this.descripcion = descripcion;
        Juego.consola.imprimir(this.receptor.getNombre() + ", ¿te doy " + ofertada.getNombre() + " y tú me das " + recibida.getNombre() + "?");
    }

    public Trato(Jugador ofertor, double cantidadOfertada,Propiedades propiedadRecibida,String descripcion){ //Cantidad x propiedad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = ofertor;
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.cantidad = cantidadOfertada;
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
        this.descripcion = descripcion;
        Juego.consola.imprimir(this.receptor.getNombre() + ", ¿te doy " + cantidadOfertada + "€ y tú me das " + propiedadRecibida.getNombre() + "?");
    }
    public Trato(double cantidadRecibida,Jugador receptor, Propiedades propiedadOfertada,String descripcion){ //Propiedad x cantidad
        cantidadRecibida = cantidadRecibida*-1;
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = receptor;
        this.receptor.anhadirTratoPendiente(this);
        this.cantidad = cantidadRecibida;
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
        this.descripcion = descripcion;
        Juego.consola.imprimir(this.receptor.getNombre() + ", ¿te doy " + propiedadOfertada.getNombre() + " y tú me das " + cantidadRecibida*-1 + "€?");
    }

    public Trato(Propiedades propiedadOfertada, double cantidad, Propiedades propiedadRecibida,String descripcion){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.cantidad = cantidad;
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
        this.descripcion = descripcion;
        Juego.consola.imprimir(this.receptor.getNombre() + ", ¿te doy " + propiedadOfertada.getNombre() + " y tú me das " + propiedadRecibida.getNombre() + " y " + cantidad + "€?");
    }

    public Trato(Propiedades propiedadOfertada, Propiedades propiedadRecibida, Propiedades propiedadNoAlquiler, int turnos,String descripcion){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.propiedadesOfertadas.add(propiedadNoAlquiler);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
        if(turnos > 0)
            this.turnos = turnos;
        propiedadNoAlquiler.anhadirJugadorExcluido(this.ofertor,this.turnos);
        this.descripcion = descripcion;
        Juego.consola.imprimir(this.receptor.getNombre() + ", ¿te doy " + propiedadOfertada.getNombre() + " y tú me das " + propiedadRecibida.getNombre() + " y no pago alquiler en " + propiedadNoAlquiler.getNombre()  + "?");
    }

    public Jugador getOfertor() {
        return this.ofertor;
    }

    public Jugador getReceptor(){
        return this.receptor;
    }

    private void swapPropiedades(){
        if(this.propiedadesOfertadas.size()>=2){
            this.propiedadesOfertadas.get(0).setPropietario(this.receptor);
            this.propiedadesOfertadas.get(1).setPropietario(this.ofertor);
        }else {
            if (this.ofertor.getPropiedades().contains(this.propiedadesOfertadas.get(0))){
                this.propiedadesOfertadas.get(0).setPropietario(this.receptor);
            }else{
                this.propiedadesOfertadas.get(0).setPropietario(this.ofertor);
            }
        }
    }

    public int getId(){
        return this.id;
    }

    public void aceptar() throws ExcepcionDineroVoluntario {
       if(this.receptor.getDinero()<cantidad){
           throw new ExcepcionDineroVoluntario("El trato no puede ser aceptado: " + this.receptor.getNombre() + " no dispone de " + -1*cantidad + "€.");
       }
       if(this.receptor.getPropiedades().contains(this.propiedadesOfertadas.get(0)))
           throw new ExcepcionDineroVoluntario("El trato no puede ser aceptado: " + this.propiedadesOfertadas.get(1).getNombre() + " no pertence a " + this.receptor.getNombre());
       Juego.consola.imprimir("Se ha aceptado trato" + this.getId() + " aceptado:\n" + this.descripcion);
       this.swapPropiedades();
       this.receptor.modificarDinero(cantidad);
       this.ofertor.modificarDinero(-cantidad);
       this.receptor.borrarTratoPendiente(this);
       this.ofertor.borrarTratosPropuesto(this);
    }

    public void eliminar(){
        this.receptor.borrarTratoPendiente(this); //Dentro de este arraylist ya se borra el ofertor
        this.ofertor.borrarTratosPropuesto(this);
        Juego.consola.imprimir("Se ha eliminado el trato" + this.id + ".");
    }

    @Override
    public String toString(){
        return "{\n" + "jugadorPropone: " + this.ofertor.getNombre() + ",\ntrato: " + this.descripcion + "\n}";
    }
}
