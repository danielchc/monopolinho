package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public abstract class Propiedade extends Casilla {
    private Xogador dono;
    private float valor;
    private boolean estaHipotecada;

    /**
     * Crea unha propiedade
     * @param nome
     */
    public Propiedade(String nome) {
        super(nome);
    }


    /**
     * @param x
     * @return Determina se a propiedade é dun xogador
     */
    public boolean pertenceXogador(Xogador x){
        return this.dono.equals(x);
    }

    /**
     * @return Metodo abstracto que implementa a función do alquiler
     */
    public abstract float alquiler();

    /**
     * @return Devolve o valor dunha casilla
     */
    public float valor() {
        return valor;
    }

    /**
     * Metodo co cal se compra unha propiedade
     * @param x Xogador destino
     */
    public void comprar(Xogador x){
        this.getDono().engadirDinheiro(this.valor(), TipoTransaccion.VENTA);
        this.setDono(x);
    }


    /**
     * @return Devolve o prezo a pagar pola hipoteca
     */
    public float getHipoteca(){
        return valor()* Valor.FACTOR_HIPOTECA;
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
            if(this.dono!=null){
                this.getDono().eliminarPropiedade(this);
            }
            this.dono = dono;
            dono.engadirPropiedade(this);
        }
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
        return  super.toString()+
                ((!this.getDono().getNome().equals("Banca"))?"\n\tPropietario: "+this.getDono().getNome():"");
    }
}
