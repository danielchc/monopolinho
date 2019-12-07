package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class AccionEdificar extends Accion {
    private Solar solar;
    private TipoEdificio tipoEdificio;
    public AccionEdificar(Solar c, TipoEdificio tipoEdificio){
        this.solar=c;
        this.tipoEdificio=tipoEdificio;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        if(this.solar.getNumeroEdificiosTipo(tipoEdificio)>0){
            this.solar.eliminarNumeroEdificios(tipoEdificio,1);
            if(tipoEdificio==TipoEdificio.HOTEL){
                for(int i=0;i<4;i++)this.solar.engadirEdificio(TipoEdificio.CASA);
            }
            super.getTurno().getXogador().engadirDinheiro(this.solar.getPrecioEdificio(tipoEdificio), TipoTransaccion.OTROS);
            super.getTurno().getXogador().getEstadisticas().restarDineroGastado(this.solar.getPrecioEdificio(tipoEdificio));
            return "Destruese un edificio de tipo "+tipoEdificio;
        }
        return "";
    }
}
