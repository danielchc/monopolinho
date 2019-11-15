package monopolinho.obxetos.casillas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Avatar;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;

public class Parking extends Casilla {
    public Parking() {
        super("PARKING", TipoCasilla.PARKING);
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);

    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        String mensaxe="";
        mensaxe="O xogador "+ xogo.getTurno().getXogador().getNome() + " recibe "+xogo.getTaboeiro().getBote()+", do bote.";
        xogo.getTurno().getXogador().engadirDinheiro(xogo.getTaboeiro().getBote(), TipoTransaccion.BOTE_PREMIO);
        xogo.getTaboeiro().setBote(0);
        xogo.getTurno().setPosicion(this);
        return mensaxe;
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
