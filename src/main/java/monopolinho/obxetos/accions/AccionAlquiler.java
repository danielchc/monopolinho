package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class AccionAlquiler extends Accion {
    private Propiedade propiedade;
    private float alquiler;
    public AccionAlquiler(Propiedade c,float alquiler){
        this.propiedade =c;
        this.alquiler=alquiler;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        super.getTurno().getXogador().engadirDinheiro(this.alquiler,TipoTransaccion.OTROS);
        super.getTurno().getXogador().getEstadisticas().restarDineroGastado(this.alquiler);
        this.propiedade.getDono().quitarDinheiro(this.alquiler,TipoTransaccion.OTROS);

        return "Devolveronseche os "+this.alquiler +" por caer na casilla "+this.propiedade.getNome();
    }
}
