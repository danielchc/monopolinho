package monopolinho.obxetos.edificios;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoEdificio;

public class Pistadeportes extends Edificio{
    public Pistadeportes(Solar posicion){
        super(posicion);
        super.setTipoEdificio(TipoEdificio.PISTA_DEPORTES);
        super.setId(TipoEdificio.PISTA_DEPORTES + String.valueOf(posicion.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES) + 1));
        super.setPrecio(Valor.FACTOR_VALOR_PISTADEPORTES*posicion.getValor());
    }
}
