package monopolinho.obxetos.excepcions;

import monopolinho.obxetos.Xogador;
import monopolinho.tipos.EstadoXogador;

public class MonopolinhoSinDinheiroException extends MonopolinhoException {

    public MonopolinhoSinDinheiroException(String mensaxe, Xogador x) {
        super(mensaxe);
        x.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
    }
}
