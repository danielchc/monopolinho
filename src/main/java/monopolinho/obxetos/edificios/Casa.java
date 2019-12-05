package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Casa extends Edificio{

    public Casa(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.CASA);
        super.setId(TipoEdificio.CASA + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.CASA) + 1));
    }
    @Override
    public float getPrecio() {
        return Valor.FACTOR_VALOR_CASA*getPosicion().valor();
    }
}
