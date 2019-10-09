package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.Random;

public class Avatar {
    public enum TipoMovemento {
        PELOTA,
        COCHE,
        ESFINXE,
        SOMBREIRO,
    }
    private Xogador xogador;
    private TipoMovemento tipo; //String tipo;
    private String id;
    private Casilla posicion; //CASILLA na que esta
    private int voltasTaboeiro=0;

    /*
        Constructor de avatar.
        Parametros: tipo de movemento e xogador ao que representa
        Xera un id aleatorio para o avatar.
     */
    public Avatar(TipoMovemento tipo, Xogador xogador){

        if (tipo!=null && xogador!=null){
            this.tipo=tipo;
            setXogador(xogador);
            xerarId();
        }
        else{
            System.err.println("Error creando avatar");
            System.exit(1);
        }
    }

    ////////// Metodos//////////////

    /*
        Este metodo engade 1 ao contador de voltas do xogador e engade o diñeiro de pasar pola salida
     */
    public void voltaTaboeiro(){
        this.voltasTaboeiro++;
        this.xogador.engadirDinheiro(Valor.VOLTA_COMPLETA);
    }

    /*
        Este metodo xera un id para o avatar de forma aleatoria
     */
    private void xerarId(){
        Random aleatorio=new Random(System.nanoTime());
        this.id=""+(char) (aleatorio.nextInt(20)+65);
    }




    ///////// Getters e setters//////////////

    public Casilla getPosicion() {
        return posicion;
    }

    public TipoMovemento getTipo() {
        return tipo;
    }

    public Xogador getXogador() {
        return xogador;
    }

    public String getId() {
        return id;
    }

    public int getVoltasTaboeiro() {
        return voltasTaboeiro;
    }

    public void setPosicion(Casilla posicion) {
        if(posicion!=null){
            if (this.posicion!=null)this.posicion.eliminarAvatar(this);
            this.posicion = posicion;
            posicion.engadirAvatar(this);
        }
    }

    public void setXogador(Xogador xogador) {
        if(xogador!=null)this.xogador = xogador;
        else{
            System.err.println("Error: xogador non inicializado");
            System.exit(1);
        }
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
        String texto="{"+"\n\tId: "+this.id+"\n\tTipo: "+this.tipo.toString()+"\n\tXogador: "+this.xogador.getPosicion().getNome()+"\n\tXogador: "+this.xogador.getNome()+"\n}";
        return texto;
    }
}
