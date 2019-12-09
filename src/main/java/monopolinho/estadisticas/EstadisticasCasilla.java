package monopolinho.estadisticas;

import monopolinho.obxetos.avatares.Avatar;

import java.util.ArrayList;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public final class EstadisticasCasilla {
    private ArrayList<Avatar> historial;
    private float alquileresPagados;

    /**
     * Estadisticas dunha Casilla
     */
    public EstadisticasCasilla(){
        this.historial=new ArrayList<>();
        this.alquileresPagados=0f;
    }

    /**
     * Engade un avatar no historial de visitas
     * @param a
     */
    public void engadirAvatarHistorial(Avatar a){
        if(a!=null)historial.add(a);
    }

    /**
     * Engadir os cartos pagados no alquiler desta casilla
     * @param dinheiro
     */
    public void engadirAlquilerPagado(float dinheiro){
        alquileresPagados+=dinheiro;
    }

    /**
     * @return Obtén o número de visitas
     */
    public int getVisitas(){
        return historial.size();
    }

    /**
     * @return Devolve o historial de visitas dos xogadores
     */
    public ArrayList<Avatar> getHistorial() {
        return historial;
    }

    /**
     * Establece o historial de visitas
     * @param historial
     */
    public void setHistorial(ArrayList<Avatar> historial) {
        this.historial = historial;
    }

    /**
     * @return Obtén o total de alquileres pagados na casilla
     */
    public float getAlquileresPagados() {
        return alquileresPagados;
    }

    /**
     * Establece o total de alquileres pagados na casilla
     * @param alquileresPagados
     */
    public void setAlquileresPagados(float alquileresPagados) {
        this.alquileresPagados = alquileresPagados;
    }
}
