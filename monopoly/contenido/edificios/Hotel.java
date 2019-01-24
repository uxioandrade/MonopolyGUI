package monopoly.contenido.edificios;

import monopoly.contenido.casillas.propiedades.Solar;

public final class Hotel extends Edificios { //Las clases hoja de una jerarquía deberían ser finales

    public Hotel(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.6);
    }

    public String getNombre(){return "Hotel-"+super.nombre;}
}
