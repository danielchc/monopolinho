package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.ArrayList;

public class Grupo {

    private String grupo_nome;
    private float valor;
    private Valor.ReprColor color;
    private ArrayList<Casilla> solares;


    ///////////////CONSTRUCTORES//////////////////////

    public Grupo(){
        this.solares=new ArrayList<>();
    }

    public Grupo(String grupo_nome, Valor.ReprColor color,float valor){
        if(grupo_nome==null || color==null){
            System.err.println("Error: algun elemento non inicializado");
            return;
        }
        this.solares=new ArrayList<>();
        this.grupo_nome=grupo_nome;
        this.color=color;
        this.valor=valor;
    }


    ///////////////METODOS///////////////////////////

    /*
        Este metodo engade unha casilla aos solares dun grupo
     */
    public void engadirSolar(Casilla c){
        if(c!=null)this.solares.add(c);
    }



    ////////////Getters e Setters//////////////////7

    public ArrayList<Casilla> getSolares() {
        return solares;
    }

    public Valor.ReprColor getColor() {
        return this.color;
    }

    public String getNome(){
        return this.grupo_nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }



    //////////////overrides/////////////////////

    @Override
    public String toString(){
        String stSolares="";
        for(Casilla s:this.solares)stSolares+="\t\t"+s.getNome() +",\n";
        return "\n{\n\tTipo:"+this.grupo_nome+"\n\tSolares:[\n"+stSolares+"\n\t]\n}";
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Grupo){
            return (this.grupo_nome.equals(((Grupo) obj).grupo_nome));
        }
        return false;
    }
}
