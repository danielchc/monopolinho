package monopolinho.obxetos.casillas.especiales.cartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.implementacion.*;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class CasillaComunidade extends CasillaCarta {
    public CasillaComunidade() {
        super("COMUNIDADE");
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados)  throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        Xogo.consola.imprimirAuto("Caiches na casilla de COMUNIDADE");
        return super.pedirCarta(xogo);
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.COMUNIDADE;
    }


    @Override
    public void crearCartas() {
        engadirCarta(new Comunidade1());
        engadirCarta(new Comunidade2());
        engadirCarta(new Comunidade3());
        engadirCarta(new Comunidade4());
        engadirCarta(new Comunidade5());
        engadirCarta(new Comunidade6());
    }
}
