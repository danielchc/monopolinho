package monopolinho.obxetos;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.accions.Accion;
import monopolinho.obxetos.accions.AccionComprar;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.excepcions.MonopolinhoException;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Turno {
    private Xogador xogador;
    private int vecesTiradas;
    private boolean podeLanzar;
    private ArrayList<Casilla> historial;
    private Stack<Accion> historialAccion;

    /**
     * Crea un obxecto turno
     * @param x
     */
    public Turno(Xogador x){
        this.xogador=x;
        this.vecesTiradas=0;
        this.podeLanzar=true;
        this.historial=new ArrayList<>();
        this.historialAccion=new Stack<>();
    }

    /**
     * @param a Engade unha acción ao turno
     */
    public void engadirAccion(Accion a){
        if(a!=null){
            a.setTurno(this);
            historialAccion.add(a);
        }
    }


    /**
     * Desfai as accións realizadas no turno
     * @param xogo
     * @throws MonopolinhoException
     */
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        String mensaxe="";
        while(!this.historialAccion.empty()){
            Accion a=historialAccion.pop();
            mensaxe+="\t-> "+a.desfacer(xogo)+"\n";
        }
        return mensaxe;
    }

    /**
     * Este metodo aumenta en 1 as veces que tirou un xogador. Serve para saber se un xogador sacou triples.
     */
    public void aumentarVecesTiradas() {
        this.xogador.getEstadisticas().engadirLazamentosDeDadosTotales();
        this.vecesTiradas++;
    }

    /**
     * Establece a posición do xogador do turno actual e añadeo a historial
     * @param c
     */
    public void setPosicion(Casilla c){
        this.xogador.setPosicion(c);
        this.historial.add(c);
    }

    /**
     * @return Obtén a posición do xogador actual
     */
    public Casilla getPosicion(){
        return this.xogador.getPosicion();
    }

    /**
     * @return Obten o xogador que está xogando no turno
     */
    public Xogador getXogador() {
        return xogador;
    }

    /**
     * Establece o xogador que está xogando no neste turno
     * @param xogador
     */
    public void setXogador(Xogador xogador) {
        this.xogador = xogador;
    }

    /**
     * @return Obtén o número de veces tiradas no turno
     */
    public int getVecesTiradas() {
        return vecesTiradas;
    }

    /**
     * Establece o número de veces tiradas no turno
     * @param vecesTiradas
     */
    public void setVecesTiradas(int vecesTiradas) {
        this.vecesTiradas = vecesTiradas;
    }

    /**
     * @return Comproba se o xogador pode lanzar
     */
    public boolean podeLanzar() {
        return podeLanzar;
    }

    /**
     * Establece se o xogador pode lanzar
     * @param podeLanzar
     */
    public void setPodeLanzar(boolean podeLanzar) {
        this.podeLanzar = podeLanzar;
    }

    /**
     * @return Obtén o numero de compras que se fixo nun turno
     */
    public int getCompradasTurno() {
        int compradasTurno=0;
        for(Accion a:historialAccion){
            if(a instanceof AccionComprar)
                compradasTurno++;
        }
        return compradasTurno;
    }

    public String listarAccions(){
        String mensaxe="";
        for(Accion h:historialAccion){
            mensaxe+=h.getClass().getSimpleName()+"\n";
        }
        return mensaxe;
    }

    /**
     * @return Obten o historial de casillas nas que se pasou nun turno
     */
    public ArrayList<Casilla> getHistorial() {
        return historial;
    }

    /**
     * Establce o historial de casillas que se pasou nun turno
     * @param historial
     */
    public void setHistorial(ArrayList<Casilla> historial) {
        this.historial = historial;
    }


    /**
     * @return Obten o historial de accions
     */
    public Stack<Accion> getHistorialAccion() {
        return historialAccion;
    }

    /**
     * Establece o historial de accións dun turno
     * @param historialAccion
     */
    public void setHistorialAccion(Stack<Accion> historialAccion) {
        this.historialAccion = historialAccion;
    }
}
