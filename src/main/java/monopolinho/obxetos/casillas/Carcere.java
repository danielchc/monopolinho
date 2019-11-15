package monopolinho.obxetos.casillas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Avatar;
import monopolinho.tipos.TipoCasilla;

public class Carcere extends Casilla {
    public Carcere() {
        super("CARCERE", TipoCasilla.CARCERE);
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        xogo.getTurno().setPosicion(this);
        return "Só de visita...";
    }

    @Override
    public String toString() {
        String xogadoresCarcere="";
        for (Avatar a : this.getAvatares()){
            if (a.getXogador().estaNoCarcere())
                xogadoresCarcere += "\n\t\t[" + a.getXogador().getNome() + "," + a.getXogador().getTurnosNoCarcere() + "],";
        }

        return "{\n\t" +
            "Tipo: "+this.getTipoCasilla()+",\n\t" +
            "Salir:"+ Valor.SAIR_CARCERE+",\n\t" +
            "Xogadores:["+xogadoresCarcere+"\n\t]" +
            "\n}";
    }
}
