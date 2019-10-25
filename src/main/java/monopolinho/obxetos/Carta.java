package monopolinho.obxetos;

import monopolinho.interfaz.Xogo;
import monopolinho.tipos.TipoCarta;
import monopolinho.tipos.TipoCartaAccion;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoGasto;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Carta {
    private TipoCarta tipoCarta;
    private TipoCartaAccion tipoCartaAccion;
    private String mensaxe;
    public Carta(TipoCarta tipoCarta, TipoCartaAccion tipoCartaAccion, String mensaxe) {
        this.tipoCarta = tipoCarta;
        this.tipoCartaAccion = tipoCartaAccion;
        this.mensaxe = mensaxe;
    }

    /**
     * Interpreta a acción ao levantar esa carta
     * @param xogo
     * @return Devolve unha String coa información interpretada
     */
    public String interpretarCarta(Xogo xogo){
        Xogador turno=xogo.getTurno();
        float dinheiroPagar;
        switch (this.tipoCartaAccion){
            //2. Decides hacer un viaje de placer. Avanza hasta Cádiz.
            case S_VIAXE_PLACER:
                turno.setPosicion((xogo.getTaboeiro().getCasilla(13)));
                return mensaxe+" "+(xogo.getTurno().getPosicion().interpretarCasilla(xogo,0));

            //3. Vendes tu billete de avión para Terrassa en una subasta por Internet. Cobra 500000€.
            //6. Devolución de Hacienda. Cobra 500000€.
            case S_VENDER_BILLETE:
            case C_DEVOLUCION_HACIENDA:
                turno.engadirDinheiro( 500000, TipoGasto.BOTE_PREMIO); //CTE?
                return mensaxe;

            //6. ¡Has ganado el bote de la lotería! Recibe 1000000€.
            case S_BOTE:
                turno.engadirDinheiro(1000000, TipoGasto.BOTE_PREMIO); //CTE?
                return mensaxe;

            //8. El  aumento  del  impuesto  sobre  bienes  inmuebles  afecta  a  todas  tus  propiedades.  Paga  400000€  por casa, 1150000M€ por hotel, 200.000€ por piscina y 750000€ por pista de deportes.
            case S_AUMENTO_BENS_IMUEBLES:
                float aPagar=0;
                for (Casilla c:turno.getPropiedades()){
                    aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.CASA)*400000;
                    aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*1150000;
                    aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*200000;
                    aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*750000;
                }
                if(!turno.quitarDinheiro(aPagar,TipoGasto.TASAS)){
                    System.err.println("Non tes suficiente diñeiro para pagar");
                    return "";
                }
                return mensaxe;

            //10. Has sido elegido presidente de la junta directiva. Paga a cada jugador 250000€.
            case S_PRESIDENTE:
                dinheiroPagar=250000f;
                if(!turno.quitarDinheiro(dinheiroPagar*xogo.getNumeroXogadores(),TipoGasto.TASAS)){
                    System.err.println("Non tes suficiente diñeiro para pagar os xogadores");
                    return "";
                }
                for(Xogador x:xogo.getXogadores()){
                    x.engadirDinheiro(dinheiroPagar,TipoGasto.OTROS);
                }
                return mensaxe;

            //11. ¡Hora punta de tráfico! Retrocede tres casillas.
            case S_RETROCEDER3:
                xogo.interpretarAccion(turno.getPosicion(),-3); //REVISAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRR
                return mensaxe;

            //2. Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2000000€.
            case C_FRAUDE_IDENTIDAD:
                turno.setPosicion(xogo.getTaboeiro().getCasilla(10));
                turno.meterNoCarcere();
                return mensaxe;

            //8. Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 200000€ a cada jugador. 4
            case C_ALQUILAR_VILLA:
                dinheiroPagar=200000f;
                if(!turno.quitarDinheiro(dinheiroPagar*xogo.getNumeroXogadores(),TipoGasto.TASAS)){
                    System.err.println("Non tes suficiente diñeiro para pagar os xogadores");
                    return mensaxe;
                }
                for(Xogador x:xogo.getXogadores()){
                    x.engadirDinheiro(dinheiroPagar,TipoGasto.OTROS);
                }
                return mensaxe;

            //4. Tu compañía de Internet obtiene beneficios. Recibe 2000000€.
            case C_BENEFICIOS_INTERNET:
                turno.engadirDinheiro( 2000000,TipoGasto.BOTE_PREMIO);
                return mensaxe;

            //7. Retrocede hasta Valencia para comprar antigüedades exóticas.
            case C_RETROCEDER_PONTEPEDRA:
                turno.setPosicion((xogo.getTaboeiro().getCasilla(8)));
                return mensaxe + turno.getPosicion().interpretarCasilla(xogo,0);
            case C_TERMAS:
                if(!turno.quitarDinheiro(500000, TipoGasto.TASAS)){
                    System.err.println("Non tes suficiente diñeiro para pagar a acción");
                    return mensaxe;
                }
                return mensaxe;
        }
        return mensaxe;
    }

    /**
     * @return Devolve o Tipo de carta
     */
    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    /**
     * Establece o tipo de Carta
     * @param tipoCarta
     */
    public void setTipoCarta(TipoCarta tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    /**
     * Establece a acción da carta
     * @return
     */
    public TipoCartaAccion getTipoCartaAccion() {
        return tipoCartaAccion;
    }

    /**
     * Establece o tipo de acción da carta
     * @param tipoCartaAccion
     */
    public void setTipoCartaAccion(TipoCartaAccion tipoCartaAccion) {
        this.tipoCartaAccion = tipoCartaAccion;
    }

    /**
     * @return Devolve o mensaxe da Carta
     */
    public String getMensaxe() {
        return mensaxe;
    }

    /**
     * Establece o mensaxe da carta
     * @param mensaxe
     */
    public void setMensaxe(String mensaxe) {
        this.mensaxe = mensaxe;
    }
}
