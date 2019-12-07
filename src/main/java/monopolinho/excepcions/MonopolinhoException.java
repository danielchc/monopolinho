package monopolinho.excepcions;
import monopolinho.interfaz.Xogo;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public abstract class MonopolinhoException extends Exception {
    public MonopolinhoException(String mensaxe) {
        super(mensaxe);
    }

    public void imprimirErro() {
        Xogo.consola.imprimirErro(this.getMessage());
    }
}
