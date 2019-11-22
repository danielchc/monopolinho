package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Accion;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoTransaccion;

public class Sorte6 extends CartaComunidade {
    private final float PREMIO=1000000f;
    public Sorte6() {
        //6. ¡Has ganado el bote de la lotería! Recibe 1000000€.
        super( "Tocou a rifa da excursión da túa filla, recibes 100000€");
    }

    @Override
    public String accion(Xogo xogo) {
        xogo.getTurno().getXogador().engadirDinheiro(PREMIO, TipoTransaccion.BOTE_PREMIO);
        xogo.getTurno().engadirAccion(new Accion(TipoAccion.RECIBIR_PREMIO,PREMIO));
        return getMensaxe();
    }
}
