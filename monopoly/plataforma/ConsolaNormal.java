package monopoly.plataforma;

import interfazgrafica.PanelTexto;

import java.util.Scanner;

public class ConsolaNormal implements Consola {

    private PanelTexto panelTexto;

    public void imprimir (String mensaje){
        System.out.println(mensaje);
    }

    public void setPanelTexto(PanelTexto panelTexto){
        this.panelTexto = panelTexto;
    }

    public void anhadirTexto(String str){
        this.panelTexto.addTexto(str);
    }

    public String leer(String descripcion){
        Scanner scanner = new Scanner(System.in);
        System.out.print(descripcion);
        return scanner.nextLine();
    }

}
