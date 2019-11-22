package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Accion;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiroException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;

public class Transporte extends Propiedade {
    public Transporte(String nome){
        super(nome);
        super.setColorCasilla(Valor.ReprColor.ANSI_PURPLE_BOLD);
        this.setValor(Valor.VALOR_TRANSPORTE);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoSinDinheiroException {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.pertenceXogador(xogador)) && (!this.pertenceXogador(xogo.getBanca()))){
            float aPagar=0;
            aPagar=this.getAlquiler()*(this.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
            if(xogador.quitarDinheiro(aPagar, TipoTransaccion.OTROS)){
                this.getDono().engadirDinheiro(aPagar, TipoTransaccion.OTROS);
                xogo.getTurno().engadirAccion(new Accion(TipoAccion.PAGAR_ALQUILER,this,aPagar));
                mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome() +" por usar "+this.getNome();
            }else{
                throw new MonopolinhoSinDinheiroException("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.",xogador);
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.TRANSPORTE;
    }

    @Override
    public float getAlquiler() {
        return Valor.USO_TRANSPORTE;
    }

    @Override
    public String toString() {
        return super.toString()+
                "\n\tPrezo: "+this.getValor()+
                "\n}";
    }
}
