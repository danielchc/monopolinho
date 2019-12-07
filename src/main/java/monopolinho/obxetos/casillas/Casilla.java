package monopolinho.obxetos.casillas;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasCasilla;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.Zona;

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
            Xogo.consola.imprimirErro("Error creando a casilla");
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
    public final void engadirAvatar(Avatar a){
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
    public final void eliminarAvatar(Avatar a){
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
    public final int numeroVecesCaidas(Avatar a){
        int contador=0;
        for(Avatar x:this.estadisticasCasilla.getHistorial())
            if(x.equals(a))
                contador++;
        return contador;
    }

    public final boolean estaAvatar(Avatar a){
        return this.avatares.contains(a);
    }

    public final int frecuenciaCasilla(){
        return this.estadisticasCasilla.getVisitas();
    }

    /**
     * Esta función interpreta a acción que ten que facer o caer na casilla
     * @param xogo
     * @param valorDados
     * @return Información da acción interpretada
     * Sobrescribese en Fillos da Casilla
     */
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException{
        xogo.getTurno().setPosicion(this);
        return "";
    }


    /**
     * @return Devolve as liñas que representan unha casilla
     * Sobrescribese en Solar
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
    public final EstadisticasCasilla getEstadisticas() {
        return estadisticasCasilla;
    }

    /**
     * Establece a estadisticas dunha casilla
     * @param estadisticasCasilla
     */
    public final void setEstadisticas(EstadisticasCasilla estadisticasCasilla) {
        this.estadisticasCasilla = estadisticasCasilla;
    }

    /**
     * @return Devolve o tipo de casilla
     */
    public abstract TipoCasilla getTipoCasilla();

    /**
     * @return Devolve o nome da casilla
     */
    public final String getNome() {
        return nome;
    }

    /**
     * Establece o nome dunha casilla
     * @param nome
     */
    public final void setNome(String nome) {
        if(nome!=null)this.nome = nome;
    }

    /**
     * @return Devolve a posición da casilla no taboeiro, do 0 o 39
     */
    public final int getPosicionIndex() {
        return posicion;
    }

    /**
     * Establece o indice no taboeiro da casilla
     * @param posicion
     */
    public final void setPosicionIndex(int posicion) {
        this.posicion = posicion;
    }

    /**
     * @return Devolve o valor dunha casilla
     */
    public final Valor.ReprColor getColorCasilla() {
        return colorCasilla;
    }

    /**
     * @param colorCasilla Establece a cor dunha casilla
     */
    public final void setColorCasilla(Valor.ReprColor colorCasilla) {
        this.colorCasilla = colorCasilla;
    }

    /**
     * @return Devolve o taboeiro no que se atopa a casilla
     */
    public final Taboeiro getTaboeiro() {
        return taboeiro;
    }

    /**
     * Establece o taboeiro no que se atopa a casilla
     * @param taboeiro
     */
    public final void setTaboeiro(Taboeiro taboeiro) {
        if(taboeiro!=null)this.taboeiro = taboeiro;
    }

    /**
     * @return Devolve os avatares que se atopan na casilla
     */
    public final ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    /**
     * Establece os avatares dunha casilla
     * @param avatares
     */
    public final void setAvatares(ArrayList<Avatar> avatares) {
        if(avatares!=null)this.avatares = avatares;
    }

    public final void setZona(Zona z) {
        this.zona=z;
    }

    public final Zona getZona(){
        return zona;
    }
    @Override
    public String toString() {
        return "{\n\tNome: "+this.nome +
                ",\n\tTipo: "+this.getTipoCasilla();
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
