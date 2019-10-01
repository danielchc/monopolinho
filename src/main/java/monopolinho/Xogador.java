package monopolinho;

import java.util.ArrayList;
import java.util.Locale;

public class Xogador {
    private String nome;
    private Avatar avatar;
    private float fortuna;
    private float dineroGastado;
    private ArrayList<Casilla> propiedades;
    private boolean tenTurno;

    public Xogador(){
        this.nome="Banca";
        this.avatar=null;
        this.dineroGastado=0;
        this.fortuna=Integer.MAX_VALUE; //1000000000
        this.propiedades=new ArrayList<>(); //lookoooo
    }

    public Xogador(String nome, Avatar.TipoAvatar tipoAvatar){
        this.nome=nome;
        this.fortuna= Valores.FORTUNA_INCIAL;
        this.dineroGastado=0;
        this.avatar=new Avatar(tipoAvatar,this);
        this.propiedades=new ArrayList<>(); //lookoooo

    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ArrayList<Casilla> propiedades) {
        if(propiedades==null){
            System.err.println("Propiedades non inicializada");
            return;
        }
        for(Casilla casilla:propiedades){
            if (casilla==null){
                System.err.println("Casilla non inicializada");
                return;
            }
        }
        this.propiedades=propiedades;
    }
    public void engadirPropiedade(Casilla casilla){
        if(casilla!=null)this.propiedades.add(casilla);
    }

    public void setTenTurno(boolean tenTurno) {
        this.tenTurno = tenTurno;
    }

    public boolean getTenTurno() {
        return tenTurno;
    }

    public void setNome(String nome) {
        if(nome!=null)this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public float getFortuna() {
        return fortuna;
    }

    public float getDineroGastado() {
        return dineroGastado;
    }

    //ESTO PROBLABLEMENTE HAXA QUE FACER UN QUE AUTOINCREMENTE O ACTUALS
    public void setDineroGastado(float dineroGastado) {
        this.dineroGastado = dineroGastado;
    }

    @Override
    public String toString(){
        String listaprop="[";
        if(this.propiedades.size()!=0)
            for(Casilla c:this.propiedades)
                listaprop+="\n\t\t"+c.getNome()+",";
        listaprop+="\n\t]";
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"NON TEN")+ ",\n"+
                "\tFortuna:"+ this.fortuna+ ",\n"+
                "\tGastos:"+  this.dineroGastado +",\n"+
                "\tPropiedades:"+listaprop+"\n"+
                "}";
    }
}