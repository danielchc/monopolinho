package monopolinho.obxetos.casillas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Xogador;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoTransaccion;

public class Imposto extends Casilla {
    private float imposto;
    public Imposto(String nome,float imposto) {
        super(nome, TipoCasilla.IMPOSTO);
        this.setColorCasilla(Valor.ReprColor.ANSI_GREEN_BOLD);
        this.imposto=imposto;
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        mensaxe="O xogador "+ xogador.getNome() +  " ten que pagar "+this.getImposto() + " por caer en "+this.getNome();
        if(xogador.quitarDinheiro(this.getImposto(), TipoTransaccion.IMPOSTO)){
            xogo.getTaboeiro().engadirBote(this.getImposto());
        }else{
            System.err.println("O xogador "+xogador.getNome()+" non ten suficiente dinheiro para pagar o imposto");
            xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
            return "";
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    @Override
    public String toString() {
        return "{\n\t" +
        "Tipo: "+this.getTipoCasilla()+",\n\t" +
        "Pagar:"+ getImposto() +
        "\n}";
    }

    public float getImposto() {
        return imposto;
    }

    public void setImposto(float imposto) {
        this.imposto = imposto;
    }
}
