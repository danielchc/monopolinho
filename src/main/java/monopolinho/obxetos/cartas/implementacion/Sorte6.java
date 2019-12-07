package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Sorte6 extends CartaSorte {
    private final float PREMIO=1000000f;
    public Sorte6() {
        //6. ¡Has ganado el bote de la lotería! Recibe 1000000€.
        super( "Tocou a rifa da excursión da túa filla, recibes 100000€");
    }

    @Override
    public String accion(Xogo xogo) {
        xogo.getTurno().getXogador().engadirDinheiro(PREMIO, TipoTransaccion.BOTE_PREMIO);
        xogo.getTurno().engadirAccion(new AccionCarta(this));
        return getMensaxe();
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().engadirDinheiro( PREMIO, TipoTransaccion.BOTE_PREMIO);
        return "Tua filla roubouche "+PREMIO+ " da súa rifa";
    }
}
