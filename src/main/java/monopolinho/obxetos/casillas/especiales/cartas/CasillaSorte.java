package monopolinho.obxetos.casillas.especiales.cartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.implementacion.*;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class CasillaSorte extends CasillaCarta {
    public CasillaSorte() {
        super("SORTE");
    }

    /**
     * Establece a posición do xogador e pide a carta
     * @param xogo
     * @param valorDados
     * @return
     * @throws MonopolinhoException
     */
    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        Xogo.consola.imprimirAuto("Caiches na casilla de SORTE");
        return super.pedirCarta(xogo);
    }

    /**
     * @return Devole o tipo de casilla
     */
    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.SORTE;
    }

    @Override
    public void crearCartas() {
        engadirCarta(new Sorte1());
        engadirCarta(new Sorte2());
        engadirCarta(new Sorte3());
        engadirCarta(new Sorte4());
        engadirCarta(new Sorte5());
        engadirCarta(new Sorte6());
    }
}
