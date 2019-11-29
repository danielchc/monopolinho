package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionAlquiler extends Accion {
    Propiedade propiedade;
    float alquiler;
    public AccionAlquiler(Propiedade c,float alquiler){
        this.propiedade =c;
        this.alquiler=alquiler;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        xogo.getTurno().getXogador().engadirDinheiro(this.alquiler,TipoTransaccion.OTROS);
        this.propiedade.getDono().quitarDinheiro(this.alquiler,TipoTransaccion.OTROS);
        return "Devolveronseche os "+this.alquiler +" por caer na casilla "+this.propiedade.getNome();
    }
}
