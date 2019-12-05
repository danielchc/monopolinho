package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class PistaDeportes extends Edificio{
    public PistaDeportes(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.PISTA_DEPORTES);
        super.setId(TipoEdificio.PISTA_DEPORTES + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES) + 1));
    }

    @Override
    public float getPrecio() {
        return Valor.FACTOR_VALOR_PISTADEPORTES*getPosicion().valor();
    }

}
