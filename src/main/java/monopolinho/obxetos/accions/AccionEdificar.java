package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

public class AccionEdificar extends Accion {
    private Solar solar;
    private TipoEdificio tipoEdificio;
    public AccionEdificar(Solar c, TipoEdificio tipoEdificio){
        this.solar =c;
        this.tipoEdificio=tipoEdificio;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        if(this.solar.getNumeroEdificiosTipo(tipoEdificio)>0){
            this.solar.eliminarNumeroEdificios(tipoEdificio,1);
            if(tipoEdificio==TipoEdificio.HOTEL){
                for(int i=0;i<4;i++)this.solar.engadirEdificio(TipoEdificio.CASA);
            }
            xogo.getTurno().getXogador().engadirDinheiro(this.solar.getPrecioEdificio(tipoEdificio), TipoTransaccion.OTROS);
            return "Destruese un edificio de tipo "+tipoEdificio;
        }
        throw new MonopolinhoGeneralException("MEO DEOS");
    }
}
