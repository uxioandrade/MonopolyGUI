package monopoly.contenido.casillas.accion;

import interfazgrafica.InterfazGrafica;
import monopoly.contenido.cartas.Carta;
import monopoly.contenido.Jugador;
import monopoly.excepciones.ExcepcionMonopoly;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Consola;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public final class CasillasCarta extends Accion { //Las clases hoja de una jerarquía deberían ser finales

    private ArrayList<Carta> cartas;

    public CasillasCarta(String nombre, int posicion, ArrayList<Carta> cartas){
        super(nombre,posicion);
        this.cartas = cartas;
    }

    private ArrayList<Carta> barajarCartas(ArrayList<Carta> cartas){
        ArrayList<Carta> aux = new ArrayList<>(cartas);
        java.util.Collections.shuffle(aux);
        return aux;
    }

    private int elegirCarta(){
            boolean valido = false;
            int num = 0;
            while(!valido) {
                String n = JOptionPane.showInputDialog(null,"Indique un número del 1 al " + this.cartas.size() + " para escoger carta",JOptionPane.INFORMATION_MESSAGE);
                if(n.charAt(0) - 47 > 1 && n.charAt(0) - 47 <= this.cartas.size()) {
                    valido = true;
                    num = Integer.parseInt(n);
                }
            }
                return num;
    }

    public void accionCaer(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar {
        ArrayList<Carta> casCaja = barajarCartas(this.cartas);
        Carta cartaEscogida = casCaja.get(elegirCarta()-1);
        try {
            cartaEscogida.accionCarta(jugador, operacion.getTablero());
        }catch (ExcepcionMonopoly ex){
        }
    }
}
