package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.excepcions.MonopolinhoException;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class AccionIrCarcere extends Accion{
    public AccionIrCarcere(){

    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        super.getTurno().getXogador().sairDoCarcere();
        return "Saiches do carcere";
    }
}
