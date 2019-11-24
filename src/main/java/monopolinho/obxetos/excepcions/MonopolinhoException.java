package monopolinho.obxetos.excepcions;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;

public abstract class MonopolinhoException extends Exception {
    public MonopolinhoException(String mensaxe) {
        super(mensaxe);
    }

    public void imprimirErro() {
        Xogo.consola.imprimirErro(this.getMessage());
    }
}
