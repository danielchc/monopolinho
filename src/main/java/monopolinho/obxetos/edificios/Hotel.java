package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;

public class Hotel extends Edificio{
    public Hotel(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.HOTEL);
        super.setId(TipoEdificio.HOTEL + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.HOTEL) + 1));
        super.setPrecio(Valor.FACTOR_VALOR_HOTEL*posicion.getValor());
    }
}
