package monopolinho.obxetos;

import monopolinho.tipos.TipoCarta;
import monopolinho.tipos.TipoCartaAccion;

public class Carta {
    private TipoCarta tipoCarta;
    private TipoCartaAccion tipoCartaAccion;
    private String mensaxe;

    public Carta(TipoCarta tipoCarta, TipoCartaAccion tipoCartaAccion, String mensaxe) {
        this.tipoCarta = tipoCarta;
        this.tipoCartaAccion = tipoCartaAccion;
        this.mensaxe = mensaxe;
    }

    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(TipoCarta tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public TipoCartaAccion getTipoCartaAccion() {
        return tipoCartaAccion;
    }

    public void setTipoCartaAccion(TipoCartaAccion tipoCartaAccion) {
        this.tipoCartaAccion = tipoCartaAccion;
    }

    public String getMensaxe() {
        return mensaxe;
    }

    public void setMensaxe(String mensaxe) {
        this.mensaxe = mensaxe;
    }
}
