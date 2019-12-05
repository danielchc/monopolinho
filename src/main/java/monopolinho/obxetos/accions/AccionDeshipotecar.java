package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

public class AccionDeshipotecar extends Accion {
    private Propiedade propiedade;
    public AccionDeshipotecar(Propiedade c){
        this.propiedade =c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoGeneralException {
        propiedade.setEstaHipotecada(true);
        super.getTurno().getXogador().engadirDinheiro(propiedade.getHipoteca()*1.1f,TipoTransaccion.OTROS);
        return "A propiedade "+ propiedade.getNome() +" está hipotecada. Devolveronche os "+propiedade.getHipoteca()*1.1f +" da hipoteca";
    }
}
