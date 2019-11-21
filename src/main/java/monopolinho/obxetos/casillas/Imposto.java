package monopolinho.obxetos.casillas;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiroException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;

public class Imposto extends Casilla {
    private float imposto;
    public Imposto(String nome,float imposto) {
        super(nome);
        this.setColorCasilla(Valor.ReprColor.ANSI_GREEN_BOLD);
        this.imposto=imposto;
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoException {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        mensaxe="O xogador "+ xogador.getNome() +  " ten que pagar "+this.getImposto() + " por caer en "+this.getNome();
        if(xogador.quitarDinheiro(this.getImposto(), TipoTransaccion.IMPOSTO)){
            xogo.getTaboeiro().engadirBote(this.getImposto());
        }else{
            throw new MonopolinhoSinDinheiroException("O xogador "+xogador.getNome()+" non ten suficiente dinheiro para pagar o imposto",xogador);
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    public float getImposto() {
        return imposto;
    }

    public void setImposto(float imposto) {
        this.imposto = imposto;
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.IMPOSTO;
    }

    @Override
    public String toString() {
        return "{\n\t" +
        "Tipo: "+this.getTipoCasilla()+",\n\t" +
        "Pagar:"+ getImposto() +
        "\n}";
    }

}
