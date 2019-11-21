package monopolinho.obxetos.cartas;

import monopolinho.tipos.TipoCarta;

public abstract class CartaComunidade extends Carta {
    public CartaComunidade(String mensaxe) {
        super(mensaxe);
    }

    @Override
    public TipoCarta getTipoCarta() {
        return TipoCarta.COMUNIDADE;
    }
}
