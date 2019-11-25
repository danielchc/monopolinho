package monopolinho.obxetos.excepcions;

import monopolinho.interfaz.Xogo;

public class MonopolinhoCasillaInexistente extends MonopolinhoException {
    String casilla;
    public MonopolinhoCasillaInexistente(String casilla) {
        super(casilla);
    }

    @Override
    public void imprimirErro() {
        Xogo.consola.imprimirErro("A casilla "+ super.getMessage() + " non existe");
    }
}
