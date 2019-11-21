package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.TipoTransaccion;

public class Sorte6 extends CartaComunidade {
    public Sorte6() {
        //6. ¡Has ganado el bote de la lotería! Recibe 1000000€.
        super( "Tocou a rifa da excursión da túa filla, recibes 100000€");
    }

    @Override
    public String accion(Xogo xogo) {
        xogo.getTurno().getXogador().engadirDinheiro(100000, TipoTransaccion.BOTE_PREMIO);
        return getMensaxe();
    }
}
