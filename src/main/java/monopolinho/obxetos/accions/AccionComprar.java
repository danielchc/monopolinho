package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.tipos.TipoTransaccion;

public class AccionComprar extends Accion {
    Propiedade propiedade;
    public AccionComprar(Propiedade c){
        this.propiedade=c;
    }
    @Override
    public String desfacer(Xogo xogo) {
        propiedade.setDono(xogo.getBanca());
        super.getTurno().getXogador().engadirDinheiro(propiedade.getValor(), TipoTransaccion.OTROS);
        return "A propiedade "+ propiedade.getNome() +" xa non che pertenece";
    }
}
