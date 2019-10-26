package monopolinho.obxetos;

import monopolinho.tipos.ModoXogo;
import monopolinho.tipos.TipoMovemento;

import java.util.Random;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */


public class Avatar {
    private Xogador xogador;
    private TipoMovemento tipo; //String tipo;
    private String id;
    private Casilla posicion; //CASILLA na que esta
    private int voltasTaboeiro=0;
    private ModoXogo modoXogo;

    /**
     * Constructor da clase Avatar
     * @param tipo tipo de movemento do xogador
     * @param xogador xogador que ten o avatar
     */
    public Avatar(TipoMovemento tipo, Xogador xogador){
        if (tipo!=null && xogador!=null){
            this.tipo=tipo;
            this.modoXogo=ModoXogo.NORMAL;
            xerarId();
            setXogador(xogador);
        }
        else{
            System.err.println("Error creando avatar");
            System.exit(1);
        }
    }


    /**
     * Constructor para a clase Avatar
     * @param tipo tipo de movemento
     * @param xogador xogador que ten o avatar
     * @param id identificador do avatar
     */
    public Avatar(TipoMovemento tipo, Xogador xogador, String id){
        this(tipo,xogador);
        this.id=id;
    }


    /**
     * Este método engade 1 ás voltas ao taboeiro.
     */
    public void voltaTaboeiro(){
        this.voltasTaboeiro++;
    }

    /**
     * Este método xera un identificador aleatorio
     */
    private void xerarId(){
        Random aleatorio=new Random(System.nanoTime());
        this.id=""+(char) (aleatorio.nextInt(20)+65);
    }

    /**
     * @return posicion do avatar
     */
    public Casilla getPosicion() {
        return posicion;
    }

    /**
     * @return tipo de movemento do avatar
     */
    public TipoMovemento getTipo() {
        return tipo;
    }

    /**
     * @return xogador que ten o avatar
     */
    public Xogador getXogador() {
        return xogador;
    }


    /**
     * @return identificador do avatar
     */
    public String getId() {
        return id;
    }


    /**
     * @return voltas dadas ao taboeiro
     */
    public int getVoltasTaboeiro() {
        return voltasTaboeiro;
    }

    /**
     * Establece a posicion dun avatar
     * @param posicion casilla na que se vai colocar o avatar
     */
    public void setPosicion(Casilla posicion) {
        if(posicion!=null){
            if (this.posicion!=null)this.posicion.eliminarAvatar(this);
            this.posicion = posicion;
            posicion.engadirAvatar(this);
        }
    }

    /**
     * Establece o xogador dun avatar
     * @param xogador xogador que vai ter o avatar
     */
    public void setXogador(Xogador xogador) {
        if(xogador!=null)this.xogador = xogador;
        else{
            System.err.println("Error: xogador non inicializado");
            System.exit(1);
        }
    }

    /**
     * Establece o tipo de movemento dun avatar
     * @param tipo tipo de movemento
     */
    public void setTipo(TipoMovemento tipo) {
        if(tipo!=null){
            this.tipo = tipo;
        }
    }

    /**
     * Establece o identificador dun avatar
     * @param id identificador do avatar
     */
    public void setId(String id) {
        if(id!=null){
            this.id = id;
        }
    }

    /**
     * Estable as voltas ao taboeiro
     * @param voltasTaboeiro voltas ao taboeiro
     */
    public void setVoltasTaboeiro(int voltasTaboeiro) {
        this.voltasTaboeiro = voltasTaboeiro;
    }

    /**
     * @return Devolve o modo de xogo do xogador
     */
    public ModoXogo getModoXogo() {
        return modoXogo;
    }

    /**
     * Establece o modo de xogo
     * @param modoXogo
     */
    public void setModoXogo(ModoXogo modoXogo) {
        this.modoXogo = modoXogo;
    }

    /**
     * Permite comparar se dous avatares son iguais en funcion de se os seus id son os memos
     * @param obj avatar
     * @return devolve verdadeiro ou falso
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Avatar){
            if(this.id.equals(((Avatar) obj).id))return true;
        }
        return false;
    }

    /**
     * Permite imprimir a información dun avatar en formato textual
     * @return devolve a información dun avatar nun String
     */
    @Override
    public String toString(){
        String texto="{"+"\n\t" +
                "Id: "+this.id+"\n\t" +
                "Tipo: "+this.tipo.toString()+"\n\t" +
                "Posicion: "+this.xogador.getPosicion().getNome()+"\n\t" +
                "Xogador: "+this.xogador.getNome()+"\n}";
        return texto;
    }
}
