package monopolinho.obxetos.casillas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.tipos.TipoCasilla;

public class Sorte extends Casilla {
    public Sorte() {
        super("SORTE", TipoCasilla.SORTE);
        this.setColorCasilla(Valor.ReprColor.ANSI_RED_BOLD);
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
