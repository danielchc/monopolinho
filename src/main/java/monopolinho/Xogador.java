package monopolinho;

public class Xogador {
    private String nome;
    private Avatar avatar;
    private float fortuna;
    private float dineroGastado;

    public Xogador(){
        this.nome="Banca";
        this.avatar=null;
        this.dineroGastado=0;
        this.fortuna=Integer.MAX_VALUE; //1000000000
    }

    public Xogador(String nome, Avatar.TipoAvatar tipoAvatar){
        this.nome=nome;
        this.fortuna=Valores.FORTUNA_INCIAL;
        this.dineroGastado=0;
        this.avatar=new Avatar(tipoAvatar,this);
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
        return String.format("{\n\tnombre:%s,\n\tavatar:%s\n}",getNome(),getAvatar().getId());
    }
}
