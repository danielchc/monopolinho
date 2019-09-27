package monopolinho;

public class Xogador {
    private String nome;
    private Avatar avatar;
    private float fortuna;

    public Xogador(String nome,Avatar avatar){
        this.avatar=avatar;
        this.nome=nome;
        this.fortuna=Valores.FORTUNA_INCIAL;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public float getFortuna() {
        return fortuna;
    }

    @Override
    public String toString(){
        return String.format("{\n\tnombre:%s,\n\tavatar:%s\n}",getNome(),getAvatar().getRepresentacion());
    }
}
