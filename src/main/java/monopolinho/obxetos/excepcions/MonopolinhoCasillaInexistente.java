package monopolinho.obxetos.excepcions;

import monopolinho.interfaz.Xogo;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class MonopolinhoCasillaInexistente extends MonopolinhoException {
    public MonopolinhoCasillaInexistente(String casilla) {
        super(casilla);
    }

    @Override
    public void imprimirErro() {
        Xogo.consola.imprimirErro("A casilla "+ super.getMessage() + " non existe");
    }
}
