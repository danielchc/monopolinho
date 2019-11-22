package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;

public class Piscina extends Edificio{
    public Piscina(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.PISCINA);
        super.setId(TipoEdificio.PISCINA + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.PISCINA) + 1));
        super.setPrecio(Valor.FACTOR_VALOR_PISCINA*posicion.getValor());
    }
}
