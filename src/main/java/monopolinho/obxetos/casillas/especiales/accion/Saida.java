package monopolinho.obxetos.casillas.especiales.accion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.especiales.Especial;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Saida extends Especial {
    public Saida() {
        super("SAIDA");
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    /**
     * Establece as accións a realizar ao caer na casilla de saída
     * @param xogo
     * @param valorDados
     * @return
     */
    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        return "";
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.SAIDA;
    }

}
