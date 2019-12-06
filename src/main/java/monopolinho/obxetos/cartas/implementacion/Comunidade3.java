package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Comunidade3 extends CartaComunidade {
    private final float PREMIO=200000f;
    public Comunidade3() {
        //4. Tu compañía de Internet obtiene beneficios. Recibe 2000000€.
        super("Google comprouche o Monopolinho. Recibe 200000€.");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        xogador.engadirDinheiro( PREMIO, TipoTransaccion.BOTE_PREMIO);
        turno.engadirAccion(new AccionCarta(this));
        return getMensaxe();
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().quitarDinheiro(PREMIO, TipoTransaccion.OTROS);
        return "Quitaronseche "+ PREMIO+ " de vender o Monopolinho a Google";
    }
}
