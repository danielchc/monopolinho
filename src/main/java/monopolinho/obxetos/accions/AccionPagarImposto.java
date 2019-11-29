package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.Imposto;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionPagarImposto extends Accion {
    Casilla casilla;
    public AccionPagarImposto(Casilla c){
        this.casilla=c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        if(casilla instanceof Imposto){
            float imposto=((Imposto)casilla).getImposto();
            super.getTurno().getXogador().engadirDinheiro(imposto,TipoTransaccion.OTROS);
            xogo.getTaboeiro().setBote(xogo.getTaboeiro().getBote()-imposto);
            return "Devolveronseche os " +imposto+ " de caer na casilla " + casilla.getNome();
        }
        throw new MonopolinhoGeneralException("Erro descoñecido");
    }
}
