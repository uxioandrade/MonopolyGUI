package monopoly.contenido.edificios;

import monopoly.contenido.casillas.propiedades.Solar;

public final class Casa extends Edificios { //Las clases hoja de una jerarquía deberían ser finales

    public Casa(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.6);
    }

    public String getNombre(){return "Casa-"+super.nombre;}

    }
