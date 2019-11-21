package monopolinho.obxetos.casillas;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasCasilla;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.*;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.tipos.*;

import java.util.ArrayList;

/**
 * Clase casilla
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class Casilla {
    private String nome;
    private int posicion;
    private Zona zona;
    private Valor.ReprColor colorCasilla;
    private ArrayList<Avatar> avatares;
    private Taboeiro taboeiro;
    private EstadisticasCasilla estadisticasCasilla;

    /**
     * Crea unha nova instancia de casilla
     * @param nome Nome da casilla
     */
    public Casilla(String nome){
        if(nome==null){
            ReprTab.imprimirErro("Error creando a casilla");
            return;
        }
        this.nome=nome;
        this.posicion=-1;
        this.avatares=new ArrayList<>();
        this.estadisticasCasilla=new EstadisticasCasilla();
        this.colorCasilla=Valor.ReprColor.ANSI_BLACK;
    }


    /**
     * Engade un avatar a lista de avatares que se atopan nunha casilla
     * NON CHAMAR ESTA FUNCIÓN FORA DE XOGADOR
     * @param a Avatar a colocar
     */
    public void engadirAvatar(Avatar a){
        if(a==null)return;
        if(!avatares.contains(a)){
            avatares.add(a);
        }
        this.estadisticasCasilla.engadirAvatarHistorial(a);
    }

    /**
     * Elimina un avatar a lista de avatares que se atopan nunha casilla
     * NON CHAMAR ESTA FUNCIÓN FORA DE XOGADOR
     * @param a Avatar a eliminar da casilla
     */
    public void eliminarAvatar(Avatar a){
        if(a==null)return;
        if(avatares.contains(a)){
            avatares.remove(a);
        }
    }

    /**
     * Este método conta as veces que caeu un avatar nunha casilla
     * @param a Avatar a buscar
     * @return Número de veces caídas
     */
    public int numeroVecesCaidas(Avatar a){
        int contador=0;
        for(Avatar x:this.estadisticasCasilla.getHistorial())
            if(x.equals(a))
                contador++;
        return contador;
    }

    public boolean estaAvatar(Avatar a){
        return this.avatares.contains(a);
    }

    //TA BEN IMPLEMENTAO ESTO???
    public int frecuenciaCasilla(){
        return this.estadisticasCasilla.getVisitas();
    }

    /**
     * Esta función interpreta a acción que ten que facer o caer na casilla
     * @param xogo
     * @param valorDados
     * @return Información da acción interpretada
     */
    public abstract String interpretarCasilla(Xogo xogo, int valorDados);


    /**
     * @return Devolve as liñas que representan unha casilla
     */
    public String[] getRepresentacion(){
        String avataresCasilla="";
        if(!this.avatares.isEmpty())avataresCasilla="&";
        for(Avatar a:this.avatares)avataresCasilla+=a.getId()+" ";
        return new String[]{
            ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado("")),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(this.nome)),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
            ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }


    /**
     * @return Obten as estadisticas dunha casilla
     */
    public EstadisticasCasilla getEstadisticas() {
        return estadisticasCasilla;
    }

    /**
     * Establece a estadisticas dunha casilla
     * @param estadisticasCasilla
     */
    public void setEstadisticas(EstadisticasCasilla estadisticasCasilla) {
        this.estadisticasCasilla = estadisticasCasilla;
    }

    /**
     * @return Devolve o tipo de casilla
     */
    public abstract TipoCasilla getTipoCasilla();

    /**
     * @return Devolve o nome da casilla
     */
    public String getNome() {
        return nome;
    }

    /**
     * Establece o nome dunha casilla
     * @param nome
     */
    public void setNome(String nome) {
        if(nome!=null)this.nome = nome;
    }

    /**
     * @return Devolve a posición da casilla no taboeiro, do 0 o 39
     */
    public int getPosicionIndex() {
        return posicion;
    }

    /**
     * Establece o indice no taboeiro da casilla
     * @param posicion
     */
    public void setPosicionIndex(int posicion) {
        this.posicion = posicion;
    }

    /**
     * @return Devolve o valor dunha casilla
     */
    public Valor.ReprColor getColorCasilla() {
        return colorCasilla;
    }

    /**
     * @param colorCasilla Establece a cor dunha casilla
     */
    public void setColorCasilla(Valor.ReprColor colorCasilla) {
        this.colorCasilla = colorCasilla;
    }

    /**
     * @return Devolve o taboeiro no que se atopa a casilla
     */
    public Taboeiro getTaboeiro() {
        return taboeiro;
    }

    /**
     * Establece o taboeiro no que se atopa a casilla
     * @param taboeiro
     */
    public void setTaboeiro(Taboeiro taboeiro) {
        if(taboeiro!=null)this.taboeiro = taboeiro;
    }

    /**
     * @return Devolve os avatares que se atopan na casilla
     */
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    /**
     * Establece os avatares dunha casilla
     * @param avatares
     */
    public void setAvatares(ArrayList<Avatar> avatares) {
        if(avatares!=null)this.avatares = avatares;
    }

    public void setZona(Zona z) {
        this.zona=z;
    }

    public Zona getZona(){
        return zona;
    }
    @Override
    public String toString() {
        return "{\n"+
                "\n\tNome: "+this.nome +
                "\n\tTipo: "+this.getTipoCasilla();
    }

    /**
     * Compara se os obxetos son iguais
     * @param obj Obxeto a comparar
     * @return Son iguais os obxectos
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            if(this.posicion==((Casilla) obj).posicion && this.nome==((Casilla) obj).nome)return true;
        }
        return false;
    }
}
