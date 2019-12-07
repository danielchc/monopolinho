package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class AccionHipotecar extends Accion {
    private Propiedade propiedade;
    public AccionHipotecar(Propiedade c){
        this.propiedade=c;
    }

    /**
     * Desfai o acción da hipotecar
     * @param xogo
     * @return
     * @throws MonopolinhoGeneralException
     */
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        propiedade.setEstaHipotecada(false);
        super.getTurno().getXogador().quitarDinheiro(propiedade.getHipoteca(),TipoTransaccion.OTROS);
        return "A propiedade "+ propiedade.getNome() +" xa non está hipotecada. Quitaronse os "+propiedade.getHipoteca() +" da hipoteca";
    }
}
