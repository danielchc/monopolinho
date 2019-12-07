package monopolinho.excepcions.casilla;

import monopolinho.excepcions.MonopolinhoException;

public abstract class MonopolinhoCasillaException extends MonopolinhoException {
    public MonopolinhoCasillaException(String mensaxe) {
        super(mensaxe);
    }
}
