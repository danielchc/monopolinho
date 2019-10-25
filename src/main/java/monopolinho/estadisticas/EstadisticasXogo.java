package monopolinho.estadisticas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Casilla;
import monopolinho.obxetos.Grupo;
import monopolinho.obxetos.Xogador;

import java.util.ArrayList;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class EstadisticasXogo {
    Xogo xogo;

    /**
     * Contructor de estadisticas
     * @param xogo
     */
    public EstadisticasXogo(Xogo xogo) {
        this.xogo = xogo;
    }

    /**
     * @return Devolve a casilla máis rentable
     */
    public Casilla getCasillaMaisRentable(){
        Casilla maisRentable=null;
        float alquileres=0;
        for(ArrayList<Casilla> zona:xogo.getTaboeiro().getCasillas()){
            for(Casilla c:zona){
                if (c.getEstadisticas().getAlquileresPagados()>alquileres){
                    alquileres=c.getEstadisticas().getAlquileresPagados();
                    maisRentable=c;
                }
            }
        }
        return maisRentable;
    }

    /**
     * @return Devolve o grupo máis rentable
     */
    public Grupo getGrupoMaisRentable(){
        float valorAlquilerAcumulado=0,valorAlquilerMaximo=0;

        Grupo grupoMaisRentable=null;
        for(Grupo g:xogo.getTaboeiro().getGrupos()){
            valorAlquilerAcumulado=0;
            for(Casilla c:g.getSolares())valorAlquilerAcumulado+=c.getEstadisticas().getAlquileresPagados();
            if(valorAlquilerAcumulado>valorAlquilerMaximo){
                valorAlquilerMaximo=valorAlquilerAcumulado;
                grupoMaisRentable=g;
            }
        }
        return grupoMaisRentable;
    }

    /**
     * @return Devolve o Xogador coa forturna máis alta
     */
    public Xogador getXogadorEnCabeza(){
        Xogador enCabeza=null;
        float fortunaMaxima=0;
        for (Xogador x:xogo.getXogadores()){
            if(x.getFortuna()>fortunaMaxima){
                fortunaMaxima=x.getFortuna();
                enCabeza=x;
            }
        }
        return enCabeza;
    }

    /**
     * @return Devolve o Xogador que lazou máis veces os dados
     */
    public Xogador getMaisLanzamentosDados(){
        Xogador enCabeza=null;
        int dados=0;
        for (Xogador x:xogo.getXogadores()){
            if(x.getEstadisticas().getVecesLanzamentoDados()>dados){
                dados=x.getEstadisticas().getVecesLanzamentoDados();
                enCabeza=x;
            }
        }
        return enCabeza;
    }

    /**
     * @return Devolve o xogador que deu máis voltas o taboeiro
     */
    public Xogador getMaisVoltas(){
        Xogador enCabeza=null;
        int voltaTaboeiro=0;
        for (Xogador x:xogo.getXogadores()){
            if(x.getAvatar().getVoltasTaboeiro()>voltaTaboeiro) {
                voltaTaboeiro = x.getAvatar().getVoltasTaboeiro();
                enCabeza=x;
            }
        }
        return enCabeza;
    }

    /**
     * @return Devolve a casilla máis frecuentada
     */
    public Casilla getCasillaMaisFrecuentada(){
        Casilla maisFrecuentada=null;
        int visitas=0;
        for(ArrayList<Casilla> zona:xogo.getTaboeiro().getCasillas()){
            for(Casilla c:zona){
                if (c.getEstadisticas().getVisitas()>visitas){
                    visitas=c.getEstadisticas().getVisitas();
                    maisFrecuentada=c;
                }
            }
        }
        return maisFrecuentada;
    }

    /**
     * @return Devolve as estadisticas formatedas
     */
    @Override
    public String toString() {
        return "{" +
            "\n\tcasillaMaisRentable: " +((getCasillaMaisRentable()!=null)?getCasillaMaisRentable().getNome():"SEN DATOS")+
            ",\n\tgrupoMaisRentable:"  +((getGrupoMaisRentable()!=null)?getGrupoMaisRentable().getNome():"SEN DATOS")+
            ",\n\tcasillaMaisFrecuentada: " +((getCasillaMaisFrecuentada()!=null)?getCasillaMaisFrecuentada().getNome():"SEN DATOS")+
            ",\n\txogadorMaisVoltas: "+((getMaisVoltas()!=null)?getMaisVoltas().getNome():"SEN DATOS")+
            ",\n\txogadorMaisVecesDados: "+((getMaisLanzamentosDados()!=null)?getMaisLanzamentosDados().getNome():"SEN DATOS")+
            ",\n\txogadorEnCabeza: "+((getXogadorEnCabeza()!=null)?getXogadorEnCabeza().getNome():"SEN DATOS")+
        "\n}";
    }
}
