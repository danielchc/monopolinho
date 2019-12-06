package monopolinho.obxetos.casillas.especiales.accion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.casillas.especiales.Especial;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Parking extends Especial {
    public Parking() {
        super("PARKING");
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);

    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        String mensaxe="";
        mensaxe="O xogador "+ xogo.getTurno().getXogador().getNome() + " recibe "+xogo.getTaboeiro().getBote()+", do bote.";
        xogo.getTurno().getXogador().engadirDinheiro(xogo.getTaboeiro().getBote(), TipoTransaccion.BOTE_PREMIO);
        xogo.getTaboeiro().setBote(0);
        return mensaxe;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.PARKING;
    }

    @Override
    public String toString() {
        String xogadores="";
        for (Avatar a : this.getAvatares())
            xogadores += "\n\t\t[" + a.getXogador().getNome() + "," + a.getXogador().getTurnosNoCarcere() + "],";
        return  "{\n\t" +
                "Bote: "+this.getTaboeiro().getBote()+"\n\t" +
                "Xogadores:["+xogadores+"\n\t]\n}";
    }
}
