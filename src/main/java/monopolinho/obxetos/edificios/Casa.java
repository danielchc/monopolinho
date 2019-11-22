package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;

public class Casa extends Edificio{

    public Casa(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.CASA);
        super.setId(TipoEdificio.CASA + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.CASA) + 1));
        super.setPrecio(Valor.FACTOR_VALOR_CASA*posicion.getValor());
    }
}
