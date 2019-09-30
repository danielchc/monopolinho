package monopolinho;


import java.util.ArrayList;

public class Casilla {
    private String nome;
    private String tipo;
    private float valor;
    private int posicion;
    private Xogador dono;
    public Casilla(String nome,String tipo,int posicion, float valor){
        this.nome=nome;
        this.tipo=tipo;
        this.posicion=posicion;
        this.valor=valor;
    }

    public String getNome() {
        return nome;
    }

    public Xogador getDono() {
        return dono;
    }

    public void setDono(Xogador dono) {
        if(dono!=null)this.dono = dono;
    }
}
