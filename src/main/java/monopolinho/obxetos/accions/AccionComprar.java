package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.tipos.TipoTransaccion;

public class AccionComprar extends Accion {
    Casilla casilla;
    public AccionComprar(Casilla c){
        this.casilla=c;
    }
    @Override
    public String desfacer(Xogo xogo) {
        if(casilla instanceof Propiedade){
            ((Propiedade)casilla).setDono(xogo.getBanca());
            super.getTurno().getXogador().engadirDinheiro(((Propiedade)casilla).getValor(), TipoTransaccion.OTROS);
        }
        return "A propiedade "+casilla.getNome() +" xa non che pertenece";
    }
}
