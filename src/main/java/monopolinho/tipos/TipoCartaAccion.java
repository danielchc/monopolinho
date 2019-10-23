package monopolinho.tipos;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Casilla;
import monopolinho.obxetos.Xogador;

public enum  TipoCartaAccion {
    //2. Decides hacer un viaje de placer. Avanza hasta Cádiz.
    S_VIAXE_PLACER(){
        public void interpretarCarta(Xogo xogo){
            xogo.getTurno().setPosicion(xogo.getTaboeiro().getCasilla(3)); //CASILLA A QUE MOVERSE CAMBIAAR!!!!
        }
    },
    //3. Vendes tu billete de avión para Terrassa en una subasta por Internet. Cobra 500000€.
    S_VENDER_BILLETE(){
        public void interpretarCarta(Xogo xogo){
            xogo.getTurno().engadirDinheiro( 500000); //CTE?
        }
    },
    //6. ¡Has ganado el bote de la lotería! Recibe 1000000€.
    S_BOTE(){
        public void interpretarCarta(Xogo xogo){
            xogo.getTurno().engadirDinheiro(  1000000); //CTE?
        }
    },
    //8. El  aumento  del  impuesto  sobre  bienes  inmuebles  afecta  a  todas  tus  propiedades.  Paga  400000€  por
    //casa, 1150000M€ por hotel, 200.000€ por piscina y 750000€ por pista de deportes.
    S_AUMENTO_BENS_IMUEBLES(){
        public void interpretarCarta(Xogo xogo){
            Xogador turno=xogo.getTurno();
            float aPagar=0;
            for (Casilla c:turno.getPropiedades()){
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.CASA)*400000;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*1150000;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*200000;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*750000;
            }
            if(!turno.quitarDinheiro(aPagar)){
                System.err.println("Non tes suficiente diñeiro para pagar");
                return;
            }
        }
    },
    //10. Has sido elegido presidente de la junta directiva. Paga a cada jugador 250000€.
    S_PRESIDENTE(){
        public void interpretarCarta(Xogo xogo){
            Xogador turno=xogo.getTurno();
            float dinheiroPagar=250000f;
            if(!turno.quitarDinheiro(dinheiroPagar*xogo.getNumeroXogadores())){
                System.err.println("Non tes suficiente diñeiro para pagar os xogadores");
                return;
            }
            for(Xogador x:xogo.getXogadores()){
                x.engadirDinheiro(dinheiroPagar);
            }
        }
    },
    //11. ¡Hora punta de tráfico! Retrocede tres casillas.
    S_RETROCEDER3(){
        public void interpretarCarta(Xogo xogo){
            xogo.interpretarAccion(xogo.getTurno().getPosicion(),-3);
        }
    };

    public abstract void interpretarCarta(Xogo xogo);
}
