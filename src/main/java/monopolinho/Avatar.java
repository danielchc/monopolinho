package monopolinho;

import java.util.Random;

public class Avatar {
    public enum TipoAvatar{
        PELOTA,
        COCHE,
        ESFINXE,
        SOMBREIRO,
    }
    private Xogador xogador;
    TipoAvatar tipo; //String tipo;
    private String id;
    private Casilla posicion; //CASILLA
    public Avatar(TipoAvatar tipo, Xogador xogador){
        this.tipo=tipo;
        setXogador(xogador);
        xerarId();
    }

    public Casilla getPosicion() {
        return posicion;
    }

    public TipoAvatar getTipo() {
        return tipo;
    }

    public Xogador getXogador() {
        return xogador;
    }

    public String getId() {
        return id;
    }

    public void setPosicion(Casilla posicion) {
        if(posicion!=null)this.posicion = posicion;
    }

    public void setXogador(Xogador xogador) {
        if(xogador!=null)this.xogador = xogador;
        else{
            System.err.println("Error: xogador non inicializado");
            System.exit(1);
        }
    }

    private void xerarId(){
        Random aleatorio=new Random(System.nanoTime());
        this.id=""+(char) (aleatorio.nextInt(20)+65);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Avatar){
            if(this.id.equals(((Avatar) obj).id))return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return String.format("{\n" +
                "\tid: %s\n " +
                "\ttipo: %s\n" +
                "\txogador: %s\n" +
                "}",this.id,this.tipo.toString(),this.xogador.getNome());
    }
    /*
    private String[] avatares={"O","\uD83D\uDE97","L"};
    private TipoAvatar tipoAvatar;
    public String getRepresentacion(){
        return avatares[this.tipoAvatar.ordinal()];
    }

    public Avatar(TipoAvatar tipoAvatar){
        this.tipoAvatar =tipoAvatar;
    }

    public void setTipoAvatar(TipoAvatar tipoAvatar) {
        this.tipoAvatar = tipoAvatar;
    }

    public TipoAvatar getTipoAvatar() {
        return tipoAvatar;
    */
}
