package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.Carta;
import monopolinho.excepcions.MonopolinhoException;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class AccionCarta extends Accion {
    private Carta carta;
    public AccionCarta(Carta carta){
        this.carta=carta;
    }
    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        return carta.desfacer(xogo);
    }
}
