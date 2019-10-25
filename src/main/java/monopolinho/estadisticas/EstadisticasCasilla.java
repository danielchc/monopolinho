package monopolinho.estadisticas;

import monopolinho.obxetos.Avatar;
import monopolinho.obxetos.Xogador;

import java.util.ArrayList;

public class EstadisticasCasilla {
    private ArrayList<Avatar> historial;
    private float alquileresPagados;

    public EstadisticasCasilla(){
        this.historial=new ArrayList<>();
        this.alquileresPagados=0f;
    }

    public void engadirAvatarHistorial(Avatar a){
        if(a!=null)historial.add(a);
    }

    public void engadirAlquilerPagado(float dinheiro){
        alquileresPagados+=dinheiro;
    }

    public int getVisitas(){
        return historial.size();
    }
    public ArrayList<Avatar> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Avatar> historial) {
        this.historial = historial;
    }

    public float getAlquileresPagados() {
        return alquileresPagados;
    }

    public void setAlquileresPagados(float alquileresPagados) {
        this.alquileresPagados = alquileresPagados;
    }
}
