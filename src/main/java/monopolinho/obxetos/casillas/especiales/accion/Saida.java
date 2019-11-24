package monopolinho.obxetos.casillas.especiales.accion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.especiales.Especial;
import monopolinho.tipos.TipoCasilla;

public class Saida extends Especial {
    public Saida() {
        super("SAIDA");
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        xogo.getTurno().setPosicion(this);
        return "";
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.SAIDA;
    }

}
