package monopolinho.obxetos;

import java.util.ArrayList;

public class Turno {
    private Xogador xogador;
    private int vecesTiradas;
    private boolean podeLanzar;
    private int compradasTurno;
    private ArrayList<Casilla> historial;

    public Turno(Xogador x){
        this.xogador=x;
        this.vecesTiradas=0;
        this.podeLanzar=true;
        this.compradasTurno=0;
    }

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

    public void engadirHistorial(Casilla c){
        this.historial.add(c);
    }

    public Xogador getXogador() {
        return xogador;
    }

    public void setXogador(Xogador xogador) {
        this.xogador = xogador;
    }

    public int getVecesTiradas() {
        return vecesTiradas;
    }

    public void setVecesTiradas(int vecesTiradas) {
        this.vecesTiradas = vecesTiradas;
    }

    public boolean podeLanzar() {
        return podeLanzar;
    }

    public void setPodeLanzar(boolean podeLanzar) {
        this.podeLanzar = podeLanzar;
    }

    public int getCompradasTurno() {
        return compradasTurno;
    }

    public void setCompradasTurno(int compradasTurno) {
        this.compradasTurno = compradasTurno;
    }

    public ArrayList<Casilla> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Casilla> historial) {
        this.historial = historial;
    }

    /**
     * @return Comproba se o xogador pode lanzar
     */

    /**
     * Establece se o xogador pode lanzar
     * @param podeLanzar
     */

    /**
     * @return Número de veces que tirou un xogador
     */

    /**
     * Establece o número de veces que tirou un xogador
     * @param vecesTiradas
     */

}
