package monopolinho.obxetos.excepcions;
import monopolinho.obxetos.Xogador;
import monopolinho.tipos.EstadoXogador;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class MonopolinhoSinDinheiro extends MonopolinhoException {

    public MonopolinhoSinDinheiro(String mensaxe, Xogador x) {
        super(mensaxe);
        x.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
    }
}
