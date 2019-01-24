package com.company;

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

public class LecturaDatos {

    public static ArrayList<String[]> Leer(String archivo) {
        ArrayList<String[]> documento = new ArrayList<>();
        String linea = "";
        String separador = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            while ((linea = br.readLine()) != null) {
                String[] descripcion = linea.split(separador);
                documento.add(descripcion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documento;
    }

    public static boolean ImprimirCSV(String archivo, ArrayList<Casilla> casillas) {
        try {
            PrintWriter pw = new PrintWriter(new File(archivo));
            for(Casilla c : casillas){
                StringBuilder sb = new StringBuilder();
                if(c instanceof Accion) {
                    if (c instanceof CasillasCarta) sb.append("CasillasCarta");
                    else sb.append("Accion");
                }
                else if (c instanceof Servicio)sb.append("Servicio");
                else if (c instanceof Solar)sb.append("Solar");
                else if (c instanceof Transporte)sb.append("Transporte");
                else if (c instanceof Especiales)sb.append("Especiales");
                else if (c instanceof Impuesto)sb.append("Impuesto");

                sb.append(",");
                sb.append(c.getNombre());
                sb.append(",");
                sb.append(c.getPosicion());
                sb.append('\n');
                pw.write(sb.toString());
            }
            pw.close();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

}
