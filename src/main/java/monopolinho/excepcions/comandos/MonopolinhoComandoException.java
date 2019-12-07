package monopolinho.excepcions.comandos;

import monopolinho.excepcions.MonopolinhoException;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public abstract class MonopolinhoComandoException extends MonopolinhoException {

    public MonopolinhoComandoException(String mensaxe) {
        super(mensaxe);
    }

}
