package monopoly.plataforma;

import monopoly.excepciones.comandos.ExcepcionNumeroPartesComando;
import monopoly.excepciones.dinamicas.ExcepcionDinamicaModoMovimiento;
import monopoly.excepciones.dinamicas.ExcepcionesDinamicaEncarcelamiento;
import monopoly.excepciones.dinamicas.ExcepcionesDinamicaTurno;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.excepciones.dinero.ExcepcionDineroVoluntario;
import monopoly.excepciones.restricciones.ExcepcionRestriccionComprar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionHipotecar;
import monopoly.excepciones.restricciones.ExcepcionRestriccionPropiedades;

public interface Comando {

    public void describir(String[] partes) throws ExcepcionNumeroPartesComando;
    public void comprar() throws ExcepcionDineroVoluntario, ExcepcionRestriccionComprar;
    public void edificar(String tipo) throws ExcepcionDineroVoluntario, ExcepcionRestriccionEdificar;
    public void vender(String[] partes) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionEdificar;
    public void hipotecar(String casilla) throws ExcepcionRestriccionHipotecar;
    public void deshipotecar(String casilla) throws ExcepcionRestriccionHipotecar, ExcepcionDineroVoluntario;
    public void lanzar() throws ExcepcionesDinamicaTurno,ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionesDinamicaEncarcelamiento, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar ;
    public void acabar() throws ExcepcionesDinamicaTurno;
    public void salir(String partes[]) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionesDinamicaEncarcelamiento;
    public void listar(String partes[]) throws ExcepcionNumeroPartesComando;
    public void jugador(String partes[]) throws ExcepcionNumeroPartesComando;
    public void ver(String partes[]) throws ExcepcionNumeroPartesComando;
    public void cambiar() throws ExcepcionDinamicaModoMovimiento;
    public String estadisticasGlobales();
    public void trato(String el1, String el2, String el3, String jugadorPropuesto, int caso) throws ExcepcionNumeroPartesComando , ExcepcionRestriccionPropiedades, ExcepcionDineroVoluntario;
    public void aceptarTrato(String trato) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionPropiedades, ExcepcionDineroVoluntario;
    public void borrarTrato(String trato);

}
