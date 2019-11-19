package monopolinho.obxetos.avatares;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Xogador;
import monopolinho.tipos.TipoMovemento;

public class Esfinxe extends Avatar{

    public Esfinxe(Xogador xogador) {
        super(xogador);
    }

    public void moverEnAvanzado(Xogo xogo, int valorDados) {

    }

    @Override
    public TipoMovemento getTipo() {
        return TipoMovemento.ESFINXE;
    }
}
