package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.excepcions.MonopolinhoException;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public abstract class Accion {
    private Turno turno;

    /**
     * Desfai unha acción realizada no xogo
     * @param xogo
     * @return
     * @throws MonopolinhoException
     */
    public abstract String desfacer(Xogo xogo) throws MonopolinhoException;

    /**
     * @return Obten o xogador da carta
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @param turno Establece o turno do xogador
     */
    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}
