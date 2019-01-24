package monopoly.contenido.casillas.propiedades;

import java.util.ArrayList;

import monopoly.contenido.*;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.contenido.edificios.*;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;
import java.text.DecimalFormat;

import monopoly.contenido.casillas.Casilla;

public final class Solar extends Propiedades { //Las clases hoja de una jerarquía deberían ser finales

    private Grupo grupo;
    private ArrayList<Edificios> construcciones;

    public Solar(String nombre, int posicion){
        super(nombre,posicion);
        this.construcciones = new ArrayList<>();
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public double alquiler(int valor){
        if(super.getPropietario().getNombre().contains("Banca")) return 0.1*super.getPrecio();
        double alquiler=0;
        if(this.construcciones.size()==0) alquiler=0.1*super.getPrecio();
        else{
            switch (this.getConstrucciones("casa").size()){
                case 1:
                    alquiler+=5*super.getPrecio()*0.1;
                    break;
                case 2:
                    alquiler+=15*super.getPrecio()*0.1;
                    break;
                case 3:
                    alquiler+=35*super.getPrecio()*0.1;
                    break;
                case 4:
                    alquiler+=50*super.getPrecio()*0.1;
                    break;
                default:

            }
            alquiler+=70*this.getConstrucciones("hotel").size()*super.getPrecio()*0.1;
            alquiler+=25*this.getConstrucciones("piscina").size()*super.getPrecio()*0.1;
            alquiler+=25*this.getConstrucciones("pista").size()*super.getPrecio()*0.1;
        }

        if(this.getPropietario().poseeGrupoCompleto(this.grupo)){
            return 2*alquiler;
        }
        return alquiler;
    }

    public void anhadirEdificio(Edificios edificio){
        if(edificio != null)
            this.construcciones.add(edificio);
    }

    public void borrarEdificio(Edificios edificio){
        if(this.construcciones.contains(edificio))
            this.construcciones.remove(edificio);
        else
            Juego.consola.imprimir("Este solar no tiene el edificio indicado");
    }

    public ArrayList<Edificios> getConstrucciones(){
        return this.construcciones;
    }

    public ArrayList<Edificios> getConstrucciones(String tipo){
        ArrayList<Edificios> construccionesTipo = new ArrayList<>();

        switch (tipo){
            case "casa":
            case "Casa":
                for(Edificios c : this.construcciones){
                    if(c instanceof Casa)
                        construccionesTipo.add(c);
                }
                break;
            case "hotel":
            case "Hotel":
                for(Edificios c : this.construcciones){
                    if(c instanceof Hotel)
                        construccionesTipo.add(c);
                }
                break;
            case "piscina":
            case "Piscina":
                for(Edificios c : this.construcciones){
                    if(c instanceof Piscina)
                        construccionesTipo.add(c);
                }
                break;
            case "pista":
            case "Pista":
                for(Edificios c : this.construcciones){
                    if(c instanceof PistaDeporte)
                        construccionesTipo.add(c);
                }
                break;
            default:
                return construccionesTipo;
        }
        return  construccionesTipo;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionDineroDeuda{
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
                ((Esfinge)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
                ((Sombrero)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            Juego.consola.imprimir("Se han pagado " + this.alquiler(tirada) + "€ de alquiler.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
            this.grupo.sumarRentabilidad(this.alquiler(tirada));
        } else {
            try {
                operacion.menuHipotecar(jugador,operacion.getTablero(),this.alquiler(tirada));
            }catch(Exception ex){
                Juego.consola.anhadirTexto(ex.getMessage());
            }
            throw new ExcepcionDineroDeuda("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
        }
    }

    public void edificar(String tipo, Tablero tablero) throws ExcepcionDineroVoluntario , ExcepcionRestriccionEdificar {
        if( this.getPropietario().getPropiedades().contains(this)){
            if(this.frecuenciaVisita( this.getPropietario().getAvatar()) > 2 ||  this.getPropietario().poseeGrupoCompleto(this.getGrupo())){
                switch (tipo){
                    case "casa":
                    case "Casa":
                        if(this.getConstrucciones("Casa").size() < 4) {
                            if(this.getGrupo().getHotlesGrupo().size()>=this.getGrupo().getCasillas().size() && this.getGrupo().getCasasGrupo().size()>=this.getGrupo().getCasillas().size()){
                                throw new ExcepcionRestriccionEdificar("Operación Cancelada: Si construyes esta casa sobrepasarás el límite de edificaciones del grupo");
                            }
                            if ( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio()) {
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio());
                                Casa nuevaCasa = new Casa(Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio(), this);
                                tablero.anhadirEdificio(nuevaCasa);
                                this.anhadirEdificio(nuevaCasa);
                                Juego.consola.anhadirTexto("Se ha edificado una casa en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio() + "€.");
                            } else
                                throw new ExcepcionDineroVoluntario("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una casa");
                        }else{
                            Juego.consola.imprimir("No se pueden construir más de 4 casas en cada solar");
                        }
                        break;
                    case "hotel":
                    case "Hotel":
                        if( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_HOTEL *(this.getPrecio())){
                            if(((Solar) this).getConstrucciones("Casa").size() >= 4) {
                                if(this.getGrupo().getHotlesGrupo().size()>=this.getGrupo().getCasillas().size()){
                                    throw new ExcepcionRestriccionEdificar("Operación Cancelada: Si construyes este hotel sobrepasarás el límite de edificaciones del grupo");
                                }
                                for(int i = 0;i <4;i++){
                                    Casa casaBorrar = ((Casa) this.getConstrucciones("Casa").get(0));
                                    this.getConstrucciones().remove(casaBorrar);
                                    tablero.borrarEdificio(casaBorrar);
                                }
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio());
                                Hotel nuevoHotel = new Hotel(Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio(), this);
                                this.anhadirEdificio(nuevoHotel);
                                tablero.anhadirEdificio(nuevoHotel);
                                Juego.consola.imprimir("Se ha edificado un hotel en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio() + "€.");
                            }else{
                                throw new ExcepcionRestriccionEdificar("Tienes que tener un mínimo de 4 casas para construir un hotel");
                            }
                        }else
                            throw new ExcepcionDineroVoluntario("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una casa");
                        break;
                    case "piscina":
                    case "Piscina":
                        if( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_PISCINA*this.getPrecio()){
                            if(this.getConstrucciones("Casa").size() >= 2 && this.getConstrucciones("Hotel").size() >= 1) {
                                if(this.getGrupo().getPiscinasGrupo().size()>=this.getGrupo().getCasillas().size()){
                                    throw new ExcepcionRestriccionEdificar("Operación Cancelada: Si construyes esta piscina sobrepasarás el límite de edificaciones del grupo");
                                }
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_PISCINA * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_PISCINA * this.getPrecio());
                                Piscina nuevaPiscina = new Piscina(Valor.MULTIPLICADOR_INICIAL_PISCINA* this.getPrecio(), this);
                                this.anhadirEdificio(nuevaPiscina);
                                tablero.anhadirEdificio(nuevaPiscina);
                                Juego.consola.imprimir("Se ha edificado una piscina en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_PISCINA * this.getPrecio() + "€.");
                            }else{
                                throw new ExcepcionRestriccionEdificar("Tienes que tener un mínimo de 2 casas y un hotel para construir una piscina");
                            }
                        }else
                            throw new ExcepcionDineroVoluntario("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una piscina");
                        break;
                    case "pista":
                    case "Pista":
                        if( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_PISTA*this.getPrecio()){
                            if(this.getConstrucciones("Hotel").size() >= 2) {
                                if(this.getGrupo().getPistaDeportesGrupo().size()>=this.getGrupo().getCasillas().size()){
                                    throw new ExcepcionRestriccionEdificar("Operación Cancelada: Si construyes esta pista de deporte sobrepasarás el límite de edificaciones del grupo");
                                }
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_PISTA * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_PISTA * this.getPrecio());
                                PistaDeporte nuevaPista = new PistaDeporte(Valor.MULTIPLICADOR_INICIAL_PISTA*this.getPrecio(), this);
                                this.anhadirEdificio(nuevaPista);
                                tablero.anhadirEdificio(nuevaPista);
                                Juego.consola.anhadirTexto("Se ha edificado una piscina en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_PISTA * this.getPrecio() + "€.");
                            }else{
                                throw new ExcepcionRestriccionEdificar("Tienes que tener un mínimo de 2 hoteles para construir una pista de deporte");
                            }
                        }else
                            throw new ExcepcionDineroVoluntario("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una pista de deporte");
                        break;
                    default:
                        throw new ExcepcionRestriccionEdificar("El tipo de edificación indicado no existe.");
                }
            }else{
                throw new ExcepcionRestriccionEdificar( this.getPropietario().getNombre() + " no cumple los requisitos necesarios para edificar en la casilla " + this.getNombre());
            }
        }else{
            throw new ExcepcionRestriccionEdificar( this.getPropietario().getNombre() + " no posee la casilla " + this.getNombre());
        }
    }
    @Override
    public String toString(){
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux+="Tipo: Solar\n" +
             "Grupo: " + ((Solar) this).getGrupo().getNombre() + "\n" +
             "Precio: " + Valor.df2.format(((Solar) this).getPrecio()) + "€\n" +
             "Alquiler Actual: " +  Valor.df2.format(((Solar) this).alquiler(1)) + "€\n" +
             "Alquiler Básico: " +  Valor.df2.format(((Solar) this).getPrecio() * 0.1) + "€\n" +
             "Casas: " + ((Solar) this).getConstrucciones("casa").size() +  " | valor casa " +  Valor.df2.format(Valor.MULTIPLICADOR_INICIAL_CASA * ((Solar) this).getPrecio()) + "€\n" +
             "Hoteles: " + ((Solar) this).getConstrucciones("hotel").size() + " | valor hotel " +  Valor.df2.format(Valor.MULTIPLICADOR_INICIAL_HOTEL * ((Solar) this).getPrecio()) + "€\n" +
             "Piscinas: " + ((Solar) this).getConstrucciones("piscina").size() +  " | valor piscina " +  Valor.df2.format(Valor.MULTIPLICADOR_INICIAL_PISCINA * ((Solar) this).getPrecio()) + "€\n" +
             "Pistas de Deporte: " + ((Solar) this).getConstrucciones("pista").size() + " | valor pista de deporte " +  Valor.df2.format(Valor.MULTIPLICADOR_INICIAL_PISTA* ((Solar) this).getPrecio()) + "€\n" +
             "Alquiler una casa: " +  Valor.df2.format(5*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Alquiler dos casas: " +  Valor.df2.format(15*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Alquiler tres casas: " +  Valor.df2.format(35*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Alquiler cuatro casas: " +  Valor.df2.format(50*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Alquiler hotel: " +  Valor.df2.format(70*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Alquiler piscina: " +  Valor.df2.format(25*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Alquiler pista de deporte: " +  Valor.df2.format(25*((Solar) this).getPrecio()*0.1) + "€\n" +
             "Hipoteca: " +  Valor.df2.format(((Solar) this).getHipoteca()) + "€\n";
        if(!super.getPropietario().getNombre().equals("Banca"))
            aux += "Propietario: " + super.getPropietario().getNombre() + "\n";
        if(super.getHipotecado())
            aux += "Solar hipotecado, paga " + Valor.df2.format(1.1*super.getHipoteca()) + " para deshipotecar" + "\n";
        aux+="}\n";
        return aux;
    }
}
