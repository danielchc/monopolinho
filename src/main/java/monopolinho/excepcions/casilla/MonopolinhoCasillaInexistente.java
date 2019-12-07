package monopolinho.excepcions.casilla;

import monopolinho.interfaz.Xogo;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class MonopolinhoCasillaInexistente extends MonopolinhoCasillaException {
    public MonopolinhoCasillaInexistente(String casilla) {
        super(casilla);
    }

    @Override
    public void imprimirErro() {
        Xogo.consola.imprimirErro("A casilla "+ super.getMessage() + " non existe");
    }
}
