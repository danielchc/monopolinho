package monopolinho.obxetos.excepcions;
import monopolinho.interfaz.Xogo;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class MonopolinhoXogadorCarcere extends MonopolinhoException {
    public MonopolinhoXogadorCarcere() {
        super("");
    }
    @Override
    public void imprimirErro() {
        Xogo.consola.imprimirErro("Non pode realizar está acción porque estás no cárcere\nPaga o para saír do cárcere ou saca triples");
    }
}
