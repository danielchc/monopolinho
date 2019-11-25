package monopolinho.obxetos.casillas.especiales.cartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.implementacion.*;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;

public class CasillaSorte extends CasillaCarta {
    public CasillaSorte() {
        super("SORTE");
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        Xogo.consola.imprimirAuto("Caiches na casilla de SORTE");
        xogo.getTurno().setPosicion(this);
        return super.pedirCarta(xogo);
    }

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
