package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.*;

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
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        float dinheiroPagar;
        switch (this.tipoCartaAccion){
            //2. Decides hacer un viaje de placer. Avanza hasta Cádiz.
            case S_VIAXE_PLACER:
                turno.setPosicion((xogo.getTaboeiro().getCasilla(13)));
                return mensaxe+" "+(turno.getPosicion().interpretarCasilla(xogo,0));

            //3. Vendes tu billete de avión para Terrassa en una subasta por Internet. Cobra 500000€.
            //6. Devolución de Hacienda. Cobra 500000€.
            case S_VENDER_BILLETE:
            case C_DEVOLUCION_HACIENDA:
                xogador.engadirDinheiro( 50000, TipoTransaccion.BOTE_PREMIO);
                return mensaxe;

            //6. ¡Has ganado el bote de la lotería! Recibe 1000000€.
            case S_BOTE:
                xogador.engadirDinheiro(100000, TipoTransaccion.BOTE_PREMIO);
                return mensaxe;

            //8. El  aumento  del  impuesto  sobre  bienes  inmuebles  afecta  a  todas  tus  propiedades.  Paga  400000€  por casa, 1150000M€ por hotel, 200.000€ por piscina y 750000€ por pista de deportes.
            case S_AUMENTO_BENS_IMUEBLES:
                float aPagar=0;
                for (Propiedade p:xogador.getPropiedades()){
                    if(p instanceof Solar){
                        Solar c=(Solar)p;
                        aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.CASA)*40000.0f;
                        aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*115000.0f;
                        aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*20000.0f;
                        aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*75000.0f;
                    }
                }
                if(!xogador.quitarDinheiro(aPagar, TipoTransaccion.TASAS)){
                    ReprTab.imprimirErro(mensaxe+". Non tes suficiente diñeiro para pagar "+aPagar);
                    xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                    return "";
                }
                return mensaxe + " Tes que pagar "+aPagar;

            //10. Has sido elegido presidente de la junta directiva. Paga a cada jugador 250000€.
            case S_PRESIDENTE:
                dinheiroPagar=25000f;
                if(!xogador.quitarDinheiro(dinheiroPagar*(xogo.getNumeroXogadores()-1), TipoTransaccion.TASAS)){
                    ReprTab.imprimirErro( mensaxe + ". Non tes suficiente diñeiro para pagar os xogadores");
                    return "";
                }
                for(Xogador x:xogo.getXogadores()){
                    if(!x.equals(turno.getXogador()))
                        x.engadirDinheiro(dinheiroPagar, TipoTransaccion.OTROS);
                }
                return mensaxe;

            //11. ¡Hora punta de tráfico! Retrocede tres casillas.
            case S_RETROCEDER3:
                System.out.println(mensaxe);
                xogo.moverModoNormal(-3);
                return "";

            //2. Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2000000€.
            case C_FRAUDE_IDENTIDAD:
                turno.setPosicion(xogo.getTaboeiro().getCasilla(10));
                xogador.meterNoCarcere();
                turno.setPodeLanzar(false);
                return mensaxe;

            //8. Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 200000€ a cada jugador. 4
            case C_ALQUILAR_VILLA:
                dinheiroPagar=20000f;
                if(!xogador.quitarDinheiro(dinheiroPagar*(xogo.getNumeroXogadores()-1), TipoTransaccion.TASAS)){
                    ReprTab.imprimirErro(mensaxe+ ". Non tes suficiente diñeiro para pagar os xogadores");
                    xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                    return "";
                }
                for(Xogador x:xogo.getXogadores()){
                    if(!x.equals(turno.getXogador()))
                        x.engadirDinheiro(dinheiroPagar, TipoTransaccion.OTROS);
                }
                return mensaxe;

            //4. Tu compañía de Internet obtiene beneficios. Recibe 2000000€.
            case C_BENEFICIOS_INTERNET:
                xogador.engadirDinheiro( 200000, TipoTransaccion.BOTE_PREMIO);
                return mensaxe;

            //7. Retrocede hasta Valencia para comprar antigüedades exóticas.
            case C_RETROCEDER_PONTEPEDRA:
                turno.setPosicion((xogo.getTaboeiro().getCasilla(8)));
                return mensaxe + turno.getPosicion().interpretarCasilla(xogo,0);
            case C_TERMAS:
                if(!xogador.quitarDinheiro(50000, TipoTransaccion.TASAS)){
                    ReprTab.imprimirErro(mensaxe+ ". Non tes suficiente diñeiro para pagar a acción");
                    xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                    return "";
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
