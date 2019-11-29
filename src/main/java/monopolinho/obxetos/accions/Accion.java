package monopolinho.obxetos.accions;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.Imposto;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoTransaccion;

public abstract class Accion {
    private Turno turno;
    public abstract String desfacer(Xogo xogo) throws MonopolinhoException;

    /*private TipoAccion tipo;
    private Casilla casilla;
    private float dinheiro;

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

    public void desfacer(Xogo xogo){
        Propiedade p=null;
        if(this.casilla instanceof Propiedade){
            p=(Propiedade)this.casilla;
        }
        switch (tipo){
            case COMPRAR:

                break;
            case VENDER:
                break;
            case HIPOTECAR:

                break;
            case DESHIPOTECAR:
                p.setEstaHipotecada(true);
                this.turno.getXogador().engadirDinheiro(p.getHipoteca()*1.1f,TipoTransaccion.OTROS);
                break;
            case EDIFICAR:
                break;
            case VENDER_EDIFICIOS:
                break;
            case RECIBIR_PREMIO:
                this.turno.getXogador().quitarDinheiro(this.dinheiro,TipoTransaccion.OTROS);
                break;
            case PAGAR_IMPOSTO:

                break;
            case PAGAR_ALQUILER:
            case PAGAR_XOGADORES:
                this.turno.getXogador().engadirDinheiro(this.dinheiro,TipoTransaccion.OTROS);
                break;
            case IR_CARCEL:
                turno.getXogador().sairDoCarcere();
                break;
            case PAGAR_CARTA:
                break;
            case LANZAR_DADOS:
                turno.getXogador().setPosicion(casilla);
            case MOVERSE:
                break;
        }
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

    public float getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(float dinheiro) {
        this.dinheiro = dinheiro;
    }
*/
    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}