package monopolinho.obxetos.casillas.especiales.accion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.accions.AccionIrCarcere;
import monopolinho.obxetos.casillas.especiales.Especial;
import monopolinho.tipos.TipoCasilla;

public class Ir_Carcere extends Especial {
    public Ir_Carcere() {
        super("IR_CARCERE");
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        String mensaxe="O avatar colocase na casilla CÁRCERE";
        xogo.getTurno().getXogador().meterNoCarcere();
        xogo.getTurno().setPosicion(xogo.getTaboeiro().getCasilla(10));
        xogo.getTurno().engadirAccion(new AccionIrCarcere());
        return mensaxe;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.IRCARCERE;
    }
}
