package monopolinho.tipos;

import monopolinho.obxetos.Casilla;
import monopolinho.obxetos.Xogador;

public enum TipoEdificio{
    CASA(){
        public String edificar(Casilla actual, Xogador turno){
            return "RESULTADO";
        }
    },
    HOTEL(){
        public String edificar(Casilla actual, Xogador turno){
            return "RESULTADO";
        }
    },
    PISCINA(){
        public String edificar(Casilla actual, Xogador turno){
            return "RESULTADO";
        }
    },
    PISTA_DEPORTES(){
        public String edificar(Casilla actual, Xogador turno){
            return "RESULTADO";
        }
    },
    DEFAULT(){
        public String edificar(Casilla actual, Xogador turno){
            return "RESULTADO";
        }
    };
    public abstract String edificar(Casilla actual, Xogador turno);
}