package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionHipotecar extends Accion {
    Propiedade propiedade;
    public AccionHipotecar(Propiedade c){
        this.propiedade =c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        propiedade.setEstaHipotecada(false);
        super.getTurno().getXogador().quitarDinheiro(propiedade.getHipoteca(),TipoTransaccion.OTROS);
        return "A propiedade "+ propiedade.getNome() +" xa non está hipotecada. Quitaronse os "+propiedade.getHipoteca() +" da hipoteca";
    }
}
