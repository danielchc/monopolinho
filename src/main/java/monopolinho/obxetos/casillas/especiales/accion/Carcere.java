package monopolinho.obxetos.casillas.especiales.accion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.casillas.especiales.Especial;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Carcere extends Especial {
    public Carcere() {
        super("CARCERE");
        this.setColorCasilla(Valor.ReprColor.ANSI_BLACK_BOLD);
    }

    /**
     * Establece a acción ao caer na casilla carcere
     * @param xogo
     * @param valorDados
     * @return
     */
    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        return "Só de visita...";
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.CARCERE;
    }

    /**
     * @return Devolve os xogadores no carcere
     */
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
