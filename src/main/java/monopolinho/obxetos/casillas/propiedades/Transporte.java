package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Xogador;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;

public class Transporte extends Propiedade {
    private float usoServizo;
    public Transporte(String nome, float valor, float usoServizo){
        super(nome);
        super.setColorCasilla(Valor.ReprColor.ANSI_PURPLE_BOLD);
        this.setValor(valor);
        this.usoServizo=usoServizo;
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.getDono().equals(xogador)) && (!this.getDono().equals(xogo.getBanca()))){
            float aPagar=0;
            aPagar=this.getUsoServizo()*(this.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
            if(xogador.quitarDinheiro(aPagar, TipoTransaccion.OTROS)){
                this.getDono().engadirDinheiro(aPagar, TipoTransaccion.OTROS);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome() +" por usar "+this.getNome();
            }else{
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                return mensaxe;
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    public float getUsoServizo() {
        return usoServizo;
    }

    public void setUsoServizo(float usoServizo) {
        this.usoServizo = usoServizo;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.TRANSPORTE;
    }

    @Override
    public String toString() {
        return super.toString()+
                "\n\tPrezo: "+this.getValor()+
                "\n}";
    }
}
