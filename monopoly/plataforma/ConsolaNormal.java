package monopoly.plataforma;

import java.util.Scanner;

public class ConsolaNormal implements Consola {
    public void imprimir (String mensaje){
        System.out.println(mensaje);
    }
    public String leer(String descripcion){
        Scanner scanner = new Scanner(System.in);
        System.out.print(descripcion);
        return scanner.nextLine();
    }

}
