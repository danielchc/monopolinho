package monopolinho.estadisticas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Casilla;
import monopolinho.obxetos.Xogador;

import java.util.ArrayList;

public class EstadisticasXogo {
    Xogo xogo;

    public EstadisticasXogo(Xogo xogo) {
        this.xogo = xogo;
    }

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

    public Casilla getCasillaMaisFrecuentada(){
        Casilla maisFrecuentada=null;
        int visitas=0;
        for(ArrayList<Casilla> zona:xogo.getTaboeiro().getCasillas()){
            for(Casilla c:zona){
                if (c.getHistorial().size()>visitas){
                    visitas=c.getHistorial().size();
                    maisFrecuentada=c;
                }
            }
        }
        return maisFrecuentada;
    }

    @Override
    public String toString() {
        return "{" +
                    "\n\tcasillaMaisFrecuentada:" +((getCasillaMaisFrecuentada()!=null)?getCasillaMaisFrecuentada().getNome():"SEN DATOS")+
                    ",\n\txogadorMaisVoltas:"+((getMaisVoltas()!=null)?getMaisVoltas().getNome():"SEN DATOS")+
                    ",\n\txogadorMaisVecesDados: "+((getMaisLanzamentosDados()!=null)?getMaisLanzamentosDados().getNome():"SEN DATOS")+
                    ",\n\txogadorEnCabeza: "+((getXogadorEnCabeza()!=null)?getXogadorEnCabeza().getNome():"SEN DATOS")+
                "\n}";
    }
}
