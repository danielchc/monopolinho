package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.edificios.Casa;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

public class AccionVender extends Accion {
    Solar solar;
    TipoEdificio tipoEdificio;
    int numeroEdificios=0;
    public AccionVender(Solar c, TipoEdificio tipoEdificio,int numero){
        this.solar =c;
        this.tipoEdificio=tipoEdificio;
        this.numeroEdificios=numero;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        for(int i=0;i<numeroEdificios;i++)
            solar.engadirEdificio(tipoEdificio);

        return "Recuperonse os edificios de tipo "+tipoEdificio;
    }
}
