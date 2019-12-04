package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Hotel extends Edificio{
    public Hotel(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.HOTEL);
        super.setId(TipoEdificio.HOTEL + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.HOTEL) + 1));

    }

    @Override
    public float getPrecio() {
        return Valor.FACTOR_VALOR_HOTEL*super.getPosicion().getValor();
    }
}
