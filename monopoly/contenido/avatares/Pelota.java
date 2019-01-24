package monopoly.contenido.avatares;

import monopoly.contenido.Jugador;
import monopoly.contenido.casillas.Casilla;
import monopoly.contenido.casillas.propiedades.Propiedades;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.awt.image.BufferedImage;

public final class Pelota extends Avatar{ //Las clases hoja de una jerarquía deberían ser finales

    public Pelota(Jugador jug, Tablero tablero, BufferedImage ficha, Valor valor, Casilla salida){
        super(jug,tablero,ficha, valor, salida);
        super.numTiradas = 1;
    }

    private void accionRebote(int valor) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        Operacion operacion = new Operacion(this.getTablero(), super.valor);
        Juego.consola.anhadirTexto("El jugador " + this.getJugador().getNombre() + " ha rebotado a " + this.getCasilla().getNombre());
        super.getTablero().imprimirTablero();
        this.getCasilla().accionCaer(this.getJugador(),valor, operacion);
        if(this.getCasilla() instanceof Propiedades){
            Propiedades comprable = (Propiedades) this.getCasilla();
            if(comprable.getPropietario().getNombre().equals("Banca")){
                Juego.consola.anhadirTexto("Desea comprar la propiedad " + this.getCasilla().getNombre() + " ? (Si/No)");
                //System.out.print("$> ");
                //Scanner scanner = new Scanner(System.in);
                //String orden = scanner.nextLine();
                String orden =Juego.consola.leer("$> ");
                if(orden.equals("Si") || orden.equals("si") || orden.equals("SI")) {
                    if (this.getJugador().getDinero() >= comprable.getPrecio()) {
                        operacion.comprar(this.getJugador());
                    }else{
                       Juego.consola.anhadirTexto("No tienes dinero suficiente para adquirir esta propiedad");
                    }
                }
            }
        }
    }

    public void moverEnAvanzado(int valor)throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar , ExcepcionDineroVoluntario, ExcepcionRestriccionComprar {
        if (valor > 4) {
            super.moverEnBasico(5);
            this.accionRebote(valor);
            for (int i = 7; i <= valor; i = i + 2) {
                if (this.getEncarcelado() > 0)
                    return;
                super.moverEnBasico(2);
                this.accionRebote(valor);
            }
            if (this.getEncarcelado() > 0)
                return;
            if (valor % 2 == 0) {
                moverEnBasico(1);
                this.accionRebote(valor);
            }
        } else {
            retrocederCasillas(1);
            this.accionRebote(valor);
            if (valor > 2) {
                if (this.getEncarcelado() > 0)
                    return;
                this.retrocederCasillas(2);
                this.accionRebote(valor);
            }
            if (this.getEncarcelado() > 0)
                return;
            if (valor % 2 == 0) {
                this.retrocederCasillas(1);
                this.accionRebote(valor);
            }
        }
        Juego.consola.anhadirTexto("La pelota ha dejado de rebotar. Volviendo al menú principal");
    }
    public String getTipo(){ return "Pelota";}
}
