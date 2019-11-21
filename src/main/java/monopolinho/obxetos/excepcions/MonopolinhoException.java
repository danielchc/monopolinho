package monopolinho.obxetos.excepcions;

import monopolinho.axuda.ReprTab;

public abstract class MonopolinhoException extends Exception {
    public MonopolinhoException(String mensaxe) {
        super(mensaxe);
    }

    public void imprimirErro() {
        ReprTab.imprimirErro(this.getMessage());
    }
}
