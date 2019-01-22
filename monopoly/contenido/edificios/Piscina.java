package monopoly.contenido.edificios;

import monopoly.contenido.casillas.propiedades.Solar;

public final class Piscina extends Edificios { //Las clases hoja de una jerarquía deberían ser finales

    public Piscina(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.4);
    }

    public  String getNombre(){return "Piscina-"+this.nombre;}
}
