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

public class Comunidade5 extends CartaComunidade {
    private final float PREMIO=50000;
    public Comunidade5() {
        //4. Hacienda 50000€
        super("Hacienda estaba generosa, saleu a devolver. Recibes 50000€");
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
        return "Quitaronseche "+ PREMIO+ " da declaración da Renta";
    }
}
