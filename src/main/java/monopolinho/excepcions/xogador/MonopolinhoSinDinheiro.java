package monopolinho.excepcions.xogador;
import monopolinho.obxetos.Xogador;
import monopolinho.tipos.EstadoXogador;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public final class MonopolinhoSinDinheiro extends MonopolinhoXogadorException {

    public MonopolinhoSinDinheiro(String mensaxe, Xogador x) {
        super(mensaxe);
        x.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
    }
}
