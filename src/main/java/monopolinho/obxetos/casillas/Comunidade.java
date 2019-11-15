package monopolinho.obxetos.casillas;

import monopolinho.interfaz.Xogo;
import monopolinho.tipos.TipoCasilla;

public class Comunidade extends Casilla {
    public Comunidade() {
        super("COMUNIDADE", TipoCasilla.COMUNIDADE);
    }

    @Override
    public boolean haiQueCollerCarta(){
        return true;
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        xogo.getTurno().setPosicion(this);
        return "";
    }
}
