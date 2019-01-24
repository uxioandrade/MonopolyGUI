package interfazgrafica;

import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.casillas.Especiales;
import monopoly.contenido.casillas.Impuesto;
import monopoly.contenido.casillas.accion.Accion;
import monopoly.contenido.casillas.accion.CasillasCarta;
import monopoly.contenido.casillas.propiedades.Servicio;
import monopoly.contenido.casillas.propiedades.Solar;
import monopoly.contenido.casillas.propiedades.Transporte;

import java.io.*;
import java.util.ArrayList;

public class Mapa {
    private ArrayList<Integer> enteros;
    private ArrayList<Double> valorGrupo;
    public Mapa(ArrayList<Integer> enteros ,ArrayList<Double> valorGrupo){
        this.enteros=enteros;
        this.valorGrupo=valorGrupo;
    }
    public Mapa(String path){
        enteros=new ArrayList<>();
        valorGrupo=new ArrayList<>();
        String linea = "";
        //String separador = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int i=0;
            while ((linea = br.readLine()) != null) {
                //String[] descripcion = linea.split(separador);
                if(i<40) {
                    enteros.add(Integer.valueOf(linea));
                    i++;
                }else {
                    valorGrupo.add(Double.valueOf(linea));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getEnteros() {
        return enteros;
    }
    public ArrayList<Double> getValorGrupo(){
        return valorGrupo;
    }
    public void setEnteros(ArrayList<Integer> enteros){
        this.enteros=enteros;
    }
    public void setValorGrupo(ArrayList<Double> valorGrupo){
        this.valorGrupo=valorGrupo;
    }
    public void imprimir(String nombre){
        FileWriter fw = null;
        PrintWriter pw=null;
        try {
            fw=new FileWriter(nombre+".csv");
            pw= new PrintWriter(fw);
            for(Integer i : enteros){
                pw.write(i.toString()+'\n');
            }
            for(Double d: valorGrupo) {
                pw.write(d.toString()+'\n');
            }
            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
