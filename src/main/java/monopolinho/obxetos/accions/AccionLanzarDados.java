package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.excepcions.MonopolinhoException;

public class AccionLanzarDados extends Accion {
    private Casilla casilla;
    public AccionLanzarDados(Casilla c){
        this.casilla=c;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().setPosicion(this.casilla);
        return "Situaste en "+this.casilla.getNome();
    }
}
