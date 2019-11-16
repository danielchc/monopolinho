package monopolinho.obxetos.casillas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.tipos.TipoCasilla;

public class Ir_Carcere extends Casilla {
    public Ir_Carcere() {
        super("IR_CARCERE");
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        String mensaxe="O avatar colocase na casilla CÁRCERE";
        xogo.getTurno().getXogador().meterNoCarcere();
        xogo.getTurno().setPosicion(xogo.getTaboeiro().getCasilla(10));
        return mensaxe;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.IRCARCERE;
    }
}
