package monopolinho;

import java.util.ArrayList;

public class Casilla {
    private String nome;
    private String tipo;
    private float valor;
    private int posicion;
    private Xogador dono;
    ArrayList<Avatar> avatares;
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

    public float getValor() {
        return valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public String getTipo() {
        return tipo;
    }

    //FALTA IMPLEMENTAR GRUPO ANTES
    /*public String[] getRepresentacion(){
        String avataresCasilla="";
        for(Avatar a:this.avatares)avataresCasilla+="&"+a.getId();
        return new String[]{
                color + centrarTexto("") + Valor.ANSI_RESET,
                color + centrarTexto(this.nome) + Valor.ANSI_RESET,
                color + centrarTexto(avataresCasilla) + Valor.ANSI_RESET ,
        };
    }*/

    @Override
    public String toString(){
        String texto="{"+"\n\tTipo: "+this.tipo+"\n\tPrecio: "+this.valor+"\n\tPropietario"+"\n}";
        return texto;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            if(this.nome.equals(((Casilla) obj).nome))return true;
        }
        return false;
    }
}
