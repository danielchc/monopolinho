package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionAlquiler extends Accion {
    Casilla casilla;
    float alquiler;
    public AccionAlquiler(Casilla c,float alquiler){
        this.casilla=c;
        this.alquiler=alquiler;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        if(casilla instanceof Propiedade){
            xogo.getTurno().getXogador().engadirDinheiro(this.alquiler,TipoTransaccion.OTROS);
            ((Propiedade)this.casilla).getDono().quitarDinheiro(this.alquiler,TipoTransaccion.OTROS);
            return "Devolveronseche os "+this.alquiler +" por caer na casilla "+this.casilla.getNome();
        }
        throw new MonopolinhoGeneralException("Erro descoñecido");
    }
}
