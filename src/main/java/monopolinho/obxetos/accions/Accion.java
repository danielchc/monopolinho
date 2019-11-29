package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.excepcions.MonopolinhoException;

public abstract class Accion {
    private Turno turno;

    public abstract String desfacer(Xogo xogo) throws MonopolinhoException;

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}
