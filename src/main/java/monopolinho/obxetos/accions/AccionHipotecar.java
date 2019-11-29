package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionHipotecar extends Accion {
    Casilla casilla;
    public AccionHipotecar(Casilla c){
        this.casilla=c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        if(casilla instanceof Propiedade){
            ((Propiedade)casilla).setEstaHipotecada(false);
            super.getTurno().getXogador().quitarDinheiro(((Propiedade)casilla).getHipoteca(),TipoTransaccion.OTROS);
            return "A propiedade "+ casilla.getNome() +" xa non está hipotecada. Quitaronse os "+((Propiedade)casilla).getHipoteca() +" da hipoteca";
        }
        throw new MonopolinhoGeneralException("Erro descoñecido");
    }
}
