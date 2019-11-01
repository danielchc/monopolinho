package monopolinho.obxetos;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Turno {
    private Xogador xogador;
    private int vecesTiradas;
    private boolean podeLanzar;
    private int compradasTurno;
    private ArrayList<Casilla> historial;

    /**
     * Crea un obxecto turno
     * @param x
     */
    public Turno(Xogador x){
        this.xogador=x;
        this.vecesTiradas=0;
        this.podeLanzar=true;
        this.compradasTurno=0;
        this.historial=new ArrayList<>();
    }

    /**
     * Aumentanse as compras feitas neste turno
     */
    public void aumentarCompras() {
        this.compradasTurno++;
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
        return compradasTurno;
    }

    /**
     * Establece o número de compras que se fixo nun turno
     * @param compradasTurno
     */
    public void setCompradasTurno(int compradasTurno) {
        this.compradasTurno = compradasTurno;
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

}
