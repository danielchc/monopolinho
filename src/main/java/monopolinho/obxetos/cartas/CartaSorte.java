package monopolinho.obxetos.cartas;

import monopolinho.tipos.TipoCarta;

public abstract class CartaSorte extends Carta {
    public CartaSorte(String mensaxe) {
        super(mensaxe);
    }

    @Override
    public TipoCarta getTipoCarta() {
        return TipoCarta.SORTE;
    }
}
