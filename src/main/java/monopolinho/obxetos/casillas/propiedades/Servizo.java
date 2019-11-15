package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Xogador;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;

public class Servizo extends Propiedade {
    private float usoServizo;
    /**
     * Contructor para casilla TRANSPORTE OU SERVIZO
     * @param nome Nome transporte
     * @param valor Valor de compra do servizo
     * @param usoServizo
     */
    public Servizo(String nome, float valor, float usoServizo){
        super(nome,TipoCasilla.SERVIZO);
        super.setColorCasilla(Valor.ReprColor.ANSI_BLUE_BOLD);
        this.setValor(valor);
        this.usoServizo=usoServizo;
    }
    public float getUsoServizo() {
        return usoServizo;
    }

    public void setUsoServizo(float usoServizo) {
        this.usoServizo = usoServizo;
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.getDono().equals(xogador)) && (!this.getDono().equals(xogo.getBanca()))){
            float aPagar=valorDados*this.getUsoServizo();

            aPagar*=(this.getDono().numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 1)?4.0f:10.0f;

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

    @Override
    public String toString() {
        return super.toString()+
                "\n\tPrezo: "+this.getValor()+
                "\n}";
    }
}
