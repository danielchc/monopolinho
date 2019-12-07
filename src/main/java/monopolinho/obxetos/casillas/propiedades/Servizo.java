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
public class Servizo extends Propiedade {
    /**
     * Contructor para casilla TRANSPORTE OU SERVIZO
     * @param nome Nome transporte
     */
    public Servizo(String nome){
        super(nome);
        super.setColorCasilla(Valor.ReprColor.ANSI_BLUE_BOLD);
        this.setValor(Valor.VALOR_SERVIZO);
    }

    /**
     * Interpreta a accion ao caer nunha casilla Servizo
     * @param xogo
     * @param valorDados
     * @return Texto cas accións interpretadas
     * @throws MonopolinhoSinDinheiro Non tes diñeiro para pagar o alquiler
     */
    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        super.interpretarCasilla(xogo, valorDados);
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.pertenceXogador(xogador)) && (!this.pertenceXogador(xogo.getBanca()))){
            if(xogador.restarVecesNonAlquiler(this)){
                mensaxe="Un trato fixo que non tiveras que pagar alquiler en "+super.getNome();
            }else {
                float aPagar = valorDados * this.alquiler();
                aPagar *= (this.getDono().numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 1) ? 4.0f : 10.0f;

                if (xogador.quitarDinheiro(aPagar, TipoTransaccion.OTROS)) {
                    this.getDono().engadirDinheiro(aPagar, TipoTransaccion.OTROS);
                    xogo.getTurno().engadirAccion(new AccionAlquiler(this,aPagar));
                    mensaxe += "Tes que pagarlle " + aPagar + " a " + this.getDono().getNome() + " por usar " + this.getNome();
                } else {
                    throw new MonopolinhoSinDinheiro("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.", xogador);
                }
            }
        }
        return mensaxe;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.SERVIZO;
    }

    @Override
    public float alquiler() {
        return Valor.USO_SERVIZO;
    }

    @Override
    public String toString() {
        return super.toString()+
                "\n\tPrezo: "+this.valor()+
                "\n}";
    }
}
