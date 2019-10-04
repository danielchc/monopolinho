package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;

public class Grupo {

    private String tipo; //solar ou especial
    private String posicion;    //norte sure este ou oeste
    private ReprTab.ReprColor color;
    private int numeroSolares;



    public Grupo(String tipo, ReprTab.ReprColor color, String posicion){
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

    public void setColor(ReprTab.ReprColor color) {
        if (color!=null)this.color = color;
    } //NON CREO QUE SE USE

    public ReprTab.ReprColor getColor() {
        return color;
    }
}
