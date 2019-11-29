package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.excepcions.MonopolinhoException;

public class AccionIrCarcere extends Accion{
    public AccionIrCarcere(){

    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().sairDoCarcere();
        return "Saiches do carcere";
    }
}