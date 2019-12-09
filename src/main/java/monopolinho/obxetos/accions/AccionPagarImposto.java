package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Imposto;
import monopolinho.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author David Carracedo
 * @author Daniel Chenel
 */

public final class AccionPagarImposto extends Accion {
    private Imposto casillaImposto;
    public AccionPagarImposto(Imposto c){
        this.casillaImposto =c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        float imposto= casillaImposto.getImposto();
        super.getTurno().getXogador().engadirDinheiro(imposto,TipoTransaccion.OTROS);
        super.getTurno().getXogador().getEstadisticas().restarDineroGastado(imposto);
        xogo.getTaboeiro().setBote(xogo.getTaboeiro().getBote()-imposto);
        return "Devolveronseche os " +imposto+ " de caer na casilla " + casillaImposto.getNome();
    }
}
