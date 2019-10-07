package monopolinho.obxetos;

import monopolinho.axuda.Valor;

public class Grupo {

    private String tipo; //solar ou especial  //TIPOS???
    private String posicion;    //norte sure este ou oeste
    private Valor.ReprColor color;
    private int numeroSolares;



    public Grupo(String tipo, Valor.ReprColor color, String posicion){
        if(tipo==null || color==null || posicion==null){
            System.err.println("Error: algun elemento non inicializado");
            return;
        }
        this.tipo=tipo;
        this.color=color;

    }

    public void setNumeroSolares(int numeroSolares) {
        this.numeroSolares = numeroSolares;
    }

    public int getNumeroSolares() {
        return numeroSolares;
    }

    public void setPosicion(String posicion) {
        if(posicion!=null)this.posicion = posicion;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setTipo(String tipo) {
        if (tipo!=null)this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setColor(Valor.ReprColor color) {
        if (color!=null)this.color = color;
    } //NON CREO QUE SE USE

    public Valor.ReprColor getColor() {
        return color;
    }

    @Override
    public String toString(){
        return "\n{\n\tTipo:"+this.tipo+"}";
    }
}
