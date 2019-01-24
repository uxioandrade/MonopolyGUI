package monopoly.contenido.edificios;

import monopoly.contenido.casillas.propiedades.Solar;

public final class PistaDeporte extends Edificios { //Las clases hoja de una jerarquía deberían ser finales

    public PistaDeporte(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.6);
    }

    public  String getNombre(){return "Pista de Deporte-"+this.nombre;}

}
