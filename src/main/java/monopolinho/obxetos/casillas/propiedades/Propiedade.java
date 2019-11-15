package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.Grupo;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.tipos.TipoCasilla;

public abstract class Propiedade extends Casilla {
    private Xogador dono;
    private float valor;
    private boolean estaHipotecada;

    public Propiedade(String nome, TipoCasilla tipoCasilla) {
        super(nome, tipoCasilla);
    }

    /**
     * @return Devolve o prezo a pagar pola hipoteca
     */
    public float getHipoteca(){
        return getValor()* Valor.FACTOR_HIPOTECA;
    }

    /**
     * @return Devolve o dono da casilla
     */
    public Xogador getDono() {
        return dono;
    }

    /**
     * Establece o xogador dono de esta casilla
     * @param dono Xogador dono de esta casilla
     */
    public void setDono(Xogador dono) {
        if(dono!=null){
            if(this.dono!=null)
                dono.eliminarPropiedade(this);
            this.dono = dono;
            dono.engadirPropiedade(this);
        }
    }

    /**
     * @return Devolve o valor dunha casilla
     */
    public float getValor() {
        return valor;
    }

    /**
     * Establece o valor dun servicio se a casilla é un servicio
     * @param valor
     */
    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * @return Devolve se unha casilla está hipotecada
     */
    public boolean getEstaHipotecada() {
        return estaHipotecada;
    }

    /**
     * Establece se unha casilla está hipotecada
     * @param estaHipotecada
     */
    public void setEstaHipotecada(boolean estaHipotecada) {
        this.estaHipotecada = estaHipotecada;
    }

    @Override
    public String toString() {
        return super.toString()+
                ((!this.getDono().getNome().equals("Banca"))?"\n\tPropietario: "+this.getDono().getNome():"");
    }
}
