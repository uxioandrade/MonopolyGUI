package monopoly.contenido;

import monopoly.plataforma.Juego;

import java.util.Random;
        

public class Dados {

    private int dado1;
    private int dado2;

    public Dados(){
        this.dado1 = 1;
        this.dado2 = 1;
    }

    public Dados(int valor1,int valor2){
        if(valor1 >= 1 && valor1 <= 6 && valor2 >= 1 && valor2 <= 6) {
            this.dado1 = valor1;
            this.dado2 = valor2;
        }else{
            this.dado1 = 1;
            this.dado2 = 1;
        }
    }

    public void lanzarDados(){
        //Crea instancia de un número aleatorio;
        Random rn = new Random();
        this.dado1 = (rn.nextInt(6) + 1);
        this.dado2 = (rn.nextInt(6) + 1);
    }

    public int getDado1(){
        return this.dado1;
    }

    public int getDado2(){
        return this.dado2;
    }

    //No tiene sentido los setters, ya que los dados siempre deberían tener unos atributos inicializados de forma aleatoria

    public int getSuma(){
        return (this.dado1 + this.dado2);
    }

    public boolean sonDobles(){
        return (this.dado1 == this.dado2);
    }

    public void getDescripcion(){
        Juego.consola.imprimir("Dado1 = " + this.dado1 + " Dado2 = " + this.dado2);
        if(this.sonDobles()) Juego.consola.imprimir(" Son Dobles!! ");
        Juego.consola.imprimir("\n");
    }
}
