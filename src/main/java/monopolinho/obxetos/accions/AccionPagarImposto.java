package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Imposto;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionPagarImposto extends Accion {
    private Imposto casillaImposto;
    public AccionPagarImposto(Imposto c){
        this.casillaImposto =c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        float imposto= casillaImposto.getImposto();
        super.getTurno().getXogador().engadirDinheiro(imposto,TipoTransaccion.OTROS);
        xogo.getTaboeiro().setBote(xogo.getTaboeiro().getBote()-imposto);
        return "Devolveronseche os " +imposto+ " de caer na casilla " + casillaImposto.getNome();
    }
}
