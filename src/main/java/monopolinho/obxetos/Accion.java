package monopolinho.obxetos;

import monopolinho.obxetos.casillas.Casilla;
import monopolinho.tipos.TipoAccion;

public class Accion {
    TipoAccion tipo;
    Casilla casilla;
    float dinheiro;


    public Accion(TipoAccion tipoAccion){
        this.tipo=tipoAccion;
    }

    public Accion(TipoAccion tipoAccion, Casilla c){
        this(tipoAccion);
        this.casilla=c;
    }

    public Accion(TipoAccion tipoAccion,float dinheiro){
        this(tipoAccion);
        this.dinheiro=dinheiro;
    }

    public Accion(TipoAccion tipoAccion,Casilla c, float dinheiro){
        this(tipoAccion,c);
        this.dinheiro=dinheiro;
    }

    public TipoAccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoAccion tipo) {
        this.tipo = tipo;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }
}
