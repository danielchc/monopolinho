package monopolinho.obxetos.cartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCarta;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public abstract class Carta {
    private String mensaxe;
    private boolean movese;

    public Carta(String mensaxe) {
        this.mensaxe = mensaxe;
        this.movese=false;
    }

    /**
     * Interpreta a acción ao levantar esa carta
     * @param xogo
     * @return Devolve unha String coa información interpretada
     */
    public abstract String accion(Xogo xogo) throws MonopolinhoException;

    public abstract String desfacer(Xogo xogo) throws MonopolinhoException;
    /**
     * Establece a acción da carta
     * @return
     */
    public abstract TipoCarta getTipoCarta();

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

    public boolean getMovese() {
        return movese;
    }

    public void setMovese(boolean movese) {
        this.movese = movese;
    }
}
