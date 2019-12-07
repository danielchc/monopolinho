package monopolinho.excepcions.xogador;

import monopolinho.excepcions.MonopolinhoException;

public abstract class MonopolinhoXogadorException extends MonopolinhoException {
    public MonopolinhoXogadorException(String mensaxe) {
        super(mensaxe);
    }
}
