package monopoly.contenido;

import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.edificios.Edificios;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.plataforma.Juego;

import java.util.ArrayList;


public class Grupo {

    private ArrayList<Solar> solares;
    private double precio;
    private String nombre;//Nombre asociado, que es el color
    private String color;//String asociado al color
    private double rentabilidad;

    public Grupo(String nombre, String color, ArrayList<Casilla> casillas, double precio){
        this.solares = new ArrayList<>();
        for(Casilla c: casillas) {
            this.solares.add((Solar)c);
        }
        this.nombre = nombre;
        this.color = color;
        this.precio = precio;
        //Recorre el ArrayList de casillas, asigna el grupo a las casillas y inicializa el precio de cada casilla
        for(Solar cas : this.solares){
            cas.setPrecio(precio/casillas.size());
            cas.setGrupo(this);
        }
    }

    public void setPrecio(double precio){
        if(precio>0)
            this.precio = precio;
    }

    public double getPrecio(){
        return this.precio;
    }

    public void actualizarPrecio(Double precio){
        this.precio += this.precio*precio;
        for(Solar cas : this.solares)
            cas.setPrecio(this.precio/this.solares.size());
    }

    //Para los siguientes getters no es necesario crear su setter correspondiente, pues son atributos que no se modifican a lo largo del programa
    public ArrayList<Solar> getCasillas(){
        return this.solares;
    }

    public String getColor(){
        return this.color;
    }

    public String getNombre(){
        return this.nombre;
    }

    public double getRentabilidad(){
        return this.rentabilidad;
    }

    public void sumarRentabilidad(double valor){
        if(valor > 0)
            this.rentabilidad += valor;
    }

    public void listarEdificiosGrupo(){
        String aux = "";
        String[] tipo = new String[]{"Casa","Hotel","Piscina","Pista"};
        int i = 0;
        for(Solar s: this.solares){
            aux += "{\npropiedad: " + s.getNombre() +",\n";
            for(int k = 0;k<4;k++) {
                aux+= tipo[k] + "s: ";
                for (i = 0; i < s.getConstrucciones(tipo[k]).size(); i++) {
                    aux += "[" +s.getConstrucciones(tipo[k]).get(i).getNombre()+ "],";
                }
                if (i == 0) aux += "-,";
                aux += "\n";
            }
            aux += "alquiler: " + s.alquiler(1) + "\n},\n";
        }
        int tamanho=this.getCasillas().size();
        Juego.consola.imprimir(aux);
        if(this.getPistaDeportesGrupo().size()<tamanho)
            Juego.consola.imprimir("Aun se pueden construir "+(tamanho-this.getPistaDeportesGrupo().size())+" Pista de Deporte");
        else Juego.consola.imprimir("Ya no se pueden construir más pistas de deporte.");
        if(this.getPiscinasGrupo().size()<tamanho)
            Juego.consola.imprimir("Aun se pueden construir "+(tamanho-this.getPiscinasGrupo().size())+" Piscinas");
        else Juego.consola.imprimir("Ya no se pueden construir más piscinas.");
        if(this.getHotlesGrupo().size()<tamanho)
            Juego.consola.imprimir("Aun se pueden construir "+(tamanho-this.getHotlesGrupo().size())+" Hoteles");
        else Juego.consola.imprimir("Ya no se pueden construir más hoteles.");
        if(this.getCasasGrupo().size()<tamanho || this.getHotlesGrupo().size()<tamanho)
            Juego.consola.imprimir("Aun se pueden construir casas");
        else Juego.consola.imprimir("Ya no se pueden construir más casas.");
    }
    public ArrayList<Edificios> getEdificiosGrupo(){
        ArrayList<Edificios> edificios =new ArrayList<>();
        for(Solar s: this.solares){
            for(Edificios ed: s.getConstrucciones()){
                edificios.add(ed);
            }
        }
        return edificios;
    }
    public ArrayList<Edificios> getCasasGrupo(){
        ArrayList<Edificios> edificios =new ArrayList<>();
        for(Solar s: this.solares){
            for(Edificios ed: s.getConstrucciones("casa")){
                edificios.add(ed);
            }
        }
        return edificios;
    }
    public ArrayList<Edificios> getHotlesGrupo(){
        ArrayList<Edificios> edificios =new ArrayList<>();
        for(Solar s: this.solares){
            for(Edificios ed: s.getConstrucciones("hotel")){
                edificios.add(ed);
            }
        }
        return edificios;
    }
    public ArrayList<Edificios> getPistaDeportesGrupo(){
        ArrayList<Edificios> edificios =new ArrayList<>();
        for(Solar s: this.solares){
            for(Edificios ed: s.getConstrucciones("pista")){
                edificios.add(ed);
            }
        }
        return edificios;
    }
    public ArrayList<Edificios> getPiscinasGrupo(){
        ArrayList<Edificios> edificios =new ArrayList<>();
        for(Solar s: this.solares){
            for(Edificios ed: s.getConstrucciones("piscina")){
                edificios.add(ed);
            }
        }
        return edificios;
    }


    @Override
    public String toString(){
        String aux = "{\nGrupo: " + this.nombre + "\nPrecio por propiedad:" + this.precio + "\nPrecio por hipoteca" + this.precio/2.0 + "\nPrecio por alquiler" + this.precio*0.1 + "\n";
        for(Solar c : solares){
            aux += "\n";
            aux += c.getNombre();
            if(c.getPropietario() != null)
                aux += " Vendida";
        }
        aux += "\n}";
        return aux;
    }

}
