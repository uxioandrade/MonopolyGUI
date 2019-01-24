package monopoly.contenido.casillas;
import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Avatar;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Operacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public abstract class Casilla { //La clase raíz de una jerarquía no se debería instanciar, por eso es abstracta

    protected int posicion;
    protected String nombre;
    protected ArrayList<Avatar> avatares;
    protected HashMap<Avatar,Integer> visitasAvatares;

    public Casilla(String nombre, int posicion){
        if(posicion >=0 && posicion<40)
            this.posicion = posicion;
        this.nombre = nombre;
        this.avatares = new ArrayList<>();
        this.visitasAvatares = new HashMap<>();
    }

    public String getNombre(){
        return this.nombre;
    }
    //No son necesarios los setters de nombre y posicion, ya que no se modifican en el programa
    public int getPosicion(){
        return this.posicion;
    }
    //el setter de avatares no tiene sentido pues solo se pueden anhadir o quitar avatares pero no cambiar el arraylist completo
    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }

    protected void anhadirVisita(Avatar avatar){
        if(this.visitasAvatares.containsKey(avatar))
            this.visitasAvatares.replace(avatar,this.visitasAvatares.get(avatar) + 1);
        else
            this.visitasAvatares.put(avatar,1);
    }

    public void anhadirAvatar(Avatar a){
        if(a!=null){
            avatares.add(a);
        }
        this.anhadirVisita(a);
    }

    public void quitarAvatar(Avatar a){
        if(this.avatares.contains(a)) this.avatares.remove(a);
    }

    public HashMap getVisitasAvatares(){
        return this.visitasAvatares;
    }

    public int frecuenciaVisita(Avatar av){
        if(this.visitasAvatares.containsKey(av))
            return this.visitasAvatares.get(av);
        else
            return 0;
    }

    public int numVisitas(){
        int total = 0;
        Iterator<Integer> vis_i = this.visitasAvatares.values().iterator();
        while(vis_i.hasNext()){
            total += vis_i.next();
        }
        return total;
    }

    public abstract void accionCaer(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar;

    public boolean estaAvatar(Avatar av){
        return this.avatares.contains(av);
    }

    @Override
    public String toString(){
        //En los criterios pone que no es necesario describir las casillas especiales
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                "Posición: " + this.posicion + "\n";
        aux += "}\n";
        return aux;
    }
}
