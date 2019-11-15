package monopolinho.obxetos.casillas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.tipos.TipoCasilla;

public class Saida extends Casilla {
    public Saida() {
        super("SAIDA", TipoCasilla.SAIDA);
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        xogo.getTurno().setPosicion(this);
        return "";
    }
}
