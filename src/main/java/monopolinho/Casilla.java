package monopolinho;

import java.util.ArrayList;

public class Casilla {
    private String nome;
    private Grupo grupo;
    private float valor;
    private int posicion;
    private Xogador dono;
    private ReprASCII.ColorCasilla colorCasilla;
    ArrayList<Avatar> avatares;
    public Casilla(String nome,Grupo grupo,String tipo,int posicion, float valor){
        this.nome=nome;
        this.grupo=grupo;
        this.posicion=posicion;
        this.valor=valor;
        this.colorCasilla=this.getGrupo().getColor();
        this.avatares=new ArrayList<Avatar>();
    }
    public Casilla(String nome, Grupo grupo, String tipo, int posicion, float valor, ReprASCII.ColorCasilla colorCasilla){
        this.nome=nome;
        this.grupo=grupo;
        this.posicion=posicion;
        this.valor=valor;
        this.colorCasilla=colorCasilla;
        this.avatares=new ArrayList<Avatar>();
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

    public Grupo getGrupo() {
        return grupo;
    }

    //FALTA IMPLEMENTAR GRUPO ANTES

    public String[] getRepresentacion(){
        String avataresCasilla="";
        for(Avatar a:this.avatares)avataresCasilla+="&"+a.getId()+" ";
        return new String[]{
                ReprASCII.colorear(this.colorCasilla, ReprASCII.centrarTexto("")),
                ReprASCII.colorear(this.colorCasilla, ReprASCII.centrarTexto(this.nome)),
                ReprASCII.colorear(this.colorCasilla, ReprASCII.centrarTexto(avataresCasilla)),
        };
    }

    @Override
    public String toString(){
        String texto="{"+"\n\tGrupo: "+this.grupo+"\n\tPrecio: "+this.valor+"\n\tPropietario"+"\n}";
        return texto;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            if(this.posicion==((Casilla) obj).posicion)return true;
        }
        return false;
    }
}
