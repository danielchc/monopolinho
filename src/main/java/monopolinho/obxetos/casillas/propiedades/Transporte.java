package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionAlquiler;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.excepcions.xogador.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Transporte extends Propiedade {
    public Transporte(String nome){
        super(nome);
        super.setColorCasilla(Valor.ReprColor.ANSI_PURPLE_BOLD);
        this.setValor(Valor.VALOR_TRANSPORTE);
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.pertenceXogador(xogador)) && (!this.pertenceXogador(xogo.getBanca()))){
            if(xogador.restarVecesNonAlquiler(this)){
                mensaxe="Un trato fixo que non tiveras que pagar alquiler en "+super.getNome();
            }else{
                float aPagar=0;
                aPagar=this.alquiler()*(this.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
                if(xogador.quitarDinheiro(aPagar, TipoTransaccion.OTROS)){
                    this.getDono().engadirDinheiro(aPagar, TipoTransaccion.OTROS);
                    xogo.getTurno().engadirAccion(new AccionAlquiler(this,aPagar));
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome() +" por usar "+this.getNome();
                }else{
                    throw new MonopolinhoSinDinheiro("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.",xogador);
                }
            }
        }
        return mensaxe;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.TRANSPORTE;
    }

    @Override
    public float alquiler() {
        return Valor.USO_TRANSPORTE;
    }

    @Override
    public String toString() {
        return super.toString()+
                "\n\tPrezo: "+this.valor()+
                "\n}";
    }
}
