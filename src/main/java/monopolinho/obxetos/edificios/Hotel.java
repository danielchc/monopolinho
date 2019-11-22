package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;

public class Hotel extends Edificio{
    public Hotel(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.HOTEL);
    }

    @Override
    public float getPrecio() {
        return Valor.FACTOR_VALOR_HOTEL*super.getPosicion().getValor();
    }
}
