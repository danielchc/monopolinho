package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.ArrayList;

public class Xogador {
    /*
        Atributos da case Xogador.
     */
    private String nome;
    private Avatar avatar;
    private float fortuna;
    private float dineroGastado;
    private ArrayList<Casilla> propiedades;
    private boolean tenTurno;
    private int turnosNaCarcel;
    private boolean podeLanzar;
    private int vecesTiradas;
    private boolean enBancarrota;


    ///////////CONSTRUCTORES/////////////

    /*
        O constructor sen argumentos crea a banca.
     */
    public Xogador(){
        this.nome="Banca";
        this.avatar=null;
        this.dineroGastado=0.0f;
        this.fortuna=0.0f;
        this.turnosNaCarcel=0;
        this.propiedades=new ArrayList<>();
        this.podeLanzar=false;
        this.enBancarrota=false;
    }

    /*
        Este constructor crea os xogadores que van "xogar" a partida.
     */
    public Xogador(String nome, Avatar.TipoMovemento tipoMovemento){
        this.nome=nome;
        this.fortuna= Valor.FORTUNA_INCIAL;
        this.dineroGastado=0; //SIN IMPLEMENTAR
        this.avatar=new Avatar(tipoMovemento,this);
        this.propiedades=new ArrayList<>(); //lookoooo
        this.podeLanzar=true;
        this.vecesTiradas=0;
        this.enBancarrota=false;
    }


    ///////////////////metodos/////////////////////

    /*
        Este metodo saca a un xogador da carcere.
     */
    public boolean salirCarcel(){
        if(this.quitarDinheiro(Valor.SAIR_CARCERE)){
            setTurnosNaCarcel(0);
            return true;
        }
        return false;
    }

    /*
        Este metodo aumenta en 1 as veces que tirou un xogador. Serve para saber se un xogador sacou triples.
     */
    public void aumentarVecesTiradas() {
        this.vecesTiradas++;
    }

    /*
        Este metodo permite saber se un xogador esta no carcere.
     */
    public boolean estaNaCarcel(){
        return (this.turnosNaCarcel!=0);
    }

    /*
        Este metodo engade unha casilla as propiedades do xogador.
     */
    public void engadirPropiedade(Casilla casilla){
        if(casilla!=null){
            this.propiedades.add(casilla);
        }
    }

    /*
        Este metodo elimina unha casilla das propiedades do xogador.
     */
    public void eliminarPropiedade(Casilla casilla){
        if(casilla!=null)this.propiedades.remove(casilla);
    }

    /*
        Este metodo engade diñeiro ao xogador.
     */
    public void engadirDinheiro(float dinero){
        fortuna+=dinero;
    }

    /*
        Este metodo quita diñeiro ao xogador.
     */
    public boolean quitarDinheiro(float dinero){
        if(fortuna<dinero)return false;
        this.fortuna-=dinero;
        this.dineroGastado+=dinero;
        return true;
    }


    /*
        Este metodo devolve un String coa informacion dun xogador.
     */
    public String describir(){
        String listaprop="[";
        if(this.propiedades.size()!=0)
            for(Casilla c:this.propiedades)listaprop+="\n\t\t"+c.getNome()+",";
        listaprop+="\n\t]";
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ ((this.enBancarrota)?"BANCARROTA":this.fortuna)+ ",\n"+
                "\tGastos:"+  this.dineroGastado +",\n"+
                "\tPropiedades:"+listaprop+"\n"+
                "}";
    }

    ////////////////getters e setters//////////////


    public boolean getEnBancarrota() {
        return enBancarrota;
    }

    public void setEnBancarrota(boolean enBancarrota) {
        this.enBancarrota = enBancarrota;
    }

    public int getTurnosNaCarcel() {
        return turnosNaCarcel;
    }

    public int getVecesTiradas() {
        return vecesTiradas;
    }

    public void setVecesTiradas(int vecesTiradas) {
        this.vecesTiradas = vecesTiradas;
    }

    public void setTurnosNaCarcel(int turnosNaCarcel) {
        this.turnosNaCarcel = turnosNaCarcel;
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

    public void setPosicion(Casilla c){
        if(c!=null)this.avatar.setPosicion(c);
    }

    public Casilla getPosicion(){
        return this.avatar.getPosicion();
    }

    public boolean getPodeLanzar() {
        return podeLanzar;
    }

    public void setPodeLanzar(boolean podeLanzar) {
       this.podeLanzar=podeLanzar;
    }


    public void setDineroGastado(float dineroGastado) {
        this.dineroGastado = dineroGastado;
    }





    ////////////////overrides/////////////////////
    @Override
    public String toString(){
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ ((this.enBancarrota)?"BANCARROTA":this.fortuna) + ",\n"+
                "}";
    }

    public boolean equals(Object obj){
        if(obj instanceof Xogador){
            if(this.nome.equals(((Xogador) obj).nome))return true;
        }
        return false;
    }
}
