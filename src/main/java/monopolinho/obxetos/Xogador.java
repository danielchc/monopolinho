package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.ArrayList;

public class Xogador {
    private String nome;
    private Avatar avatar;
    private float fortuna;
    private float dineroGastado;
    private ArrayList<Casilla> propiedades;
    private boolean tenTurno;
    private boolean enCarcel;

    public Xogador(){
        this.nome="Banca";
        this.avatar=null;
        this.dineroGastado=0;
        this.fortuna=Integer.MAX_VALUE; //1000000000
        this.propiedades=new ArrayList<>(); //lookoooo
    }

    // En todo caso faltalle o tipo de movemento
    /*public Xogador(String nome, float fortuna){     // Este é o constructor para o parking, xa que ten que acumular o diñeiro
        this.nome=nome;
        this.fortuna=fortuna;
        this.dineroGastado=0;
        this.avatar=null;
        this.propiedades=new ArrayList<>(); //lookoooo

    }*/

    public Xogador(String nome, Avatar.TipoMovemento tipoMovemento){
        this.nome=nome;
        this.fortuna= Valor.FORTUNA_INCIAL;
        this.dineroGastado=0;
        this.avatar=new Avatar(tipoMovemento,this);
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

    public void eliminarPropiedade(Casilla casilla){
        if(casilla!=null)this.propiedades.remove(casilla);
    }

    public void engadirDinheiro(float dinero){
        fortuna+=dinero;
    }
    public boolean quitarDinheiro(float dinero){
        if(fortuna>=dinero)return false;
        fortuna-=dinero;
        return true;
    }
    public void setTenTurno(boolean tenTurno) {
        this.tenTurno = tenTurno;
    }

    public boolean getTenTurno() {
        return tenTurno;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public boolean getEnCarcel() {
        return enCarcel;
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

    public String describir(){
        String listaprop="[";
        if(this.propiedades.size()!=0)
            for(Casilla c:this.propiedades)
                listaprop+="\n\t\t"+c.getNome()+",";
        listaprop+="\n\t]";
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ this.fortuna+ ",\n"+
                "\tGastos:"+  this.dineroGastado +",\n"+
                "\tPropiedades:"+listaprop+"\n"+
                "}";
    }
    @Override
    public String toString(){
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "}";
    }

    public boolean equals(Object obj){
        if(obj instanceof Xogador){
            if(this.avatar.getId().equals(((Xogador) obj).avatar.getId()))return true;
        }
        return false;
    }
}