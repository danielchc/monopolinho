package monopolinho.obxetos.avatares;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.ModoXogo;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoMovemento;
import monopolinho.tipos.TipoTransaccion;

import java.util.Random;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */


public abstract class Avatar {
    private Xogador xogador;
    private String id;
    private Casilla posicion;
    private int voltasTaboeiro=0;
    private ModoXogo modoXogo;

    /**
     * Constructor da clase Avatar
     * @param xogador xogador que ten o avatar
     */
    public Avatar(Xogador xogador){
        if (xogador!=null){
            this.modoXogo=ModoXogo.NORMAL;
            xerarId();
            setXogador(xogador);
        }
    }


    /**
     * Constructor para a clase Avatar
     * @param xogador xogador que ten o avatar
     * @param id identificador do avatar
     */
    public Avatar(Xogador xogador, String id){
        this(xogador);
        if(this.id!=null)this.id=id;
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
    public void xerarId(){
        Random aleatorio=new Random(System.nanoTime());
        this.id=""+(char) (aleatorio.nextInt(20)+65);
    }

    /**
     * Este metodo interpreta a accion a realizar segundo a casilla na que se cae.
     * @param valorDados Movemento no taboeiro respecto a casilla actual
     * @return Mensaxe da acción interpretada
     */

    public String moverEnBasico(Xogo xogo, int valorDados){
        Turno turno=xogo.getTurno();
        String mensaxe="";
        Casilla current=turno.getPosicion();
        int nPos=current.getPosicionIndex()+valorDados;
        Casilla next=xogo.getTaboeiro().getCasilla(nPos);

        if(next.getTipoCasilla()!= TipoCasilla.IRCARCERE) {
            if(nPos>39) {
                mensaxe="O xogador "+turno.getXogador().getNome()+" recibe "+ Valor.VOLTA_COMPLETA + " por completar unha volta o taboeiro.\n";
                turno.getXogador().getAvatar().voltaTaboeiro();
                turno.getXogador().engadirDinheiro(Valor.VOLTA_COMPLETA, TipoTransaccion.VOLTA_COMPLETA);
            }
        }
        try{
            mensaxe+=next.interpretarCasilla(xogo,valorDados);
        }catch (MonopolinhoException e){
            e.imprimirErro();
        }
        mensaxe="O avatar "  +turno.getXogador().getAvatar().getId() +" avanza " +valorDados+" posiciones, desde "+current.getNome()+" ata " + next.getNome() + " \n"+mensaxe;

        if(xogo.deronTodosCatroVoltas()){
            xogo.aumentarPrecioCasillas();
            mensaxe+="\nOs precios dos solares en venta aumentaron un 5%.";
        }
        return mensaxe;
    }

    public abstract void moverEnAvanzado(Xogo xogo, int valorDados) throws MonopolinhoException;


    /**
     * @return posicion do avatar
     */
    public Casilla getPosicion() {
        return posicion;
    }

    /**
     * @return tipo de movemento do avatar
     */
    public abstract TipoMovemento getTipo();


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
    }

    /**
     * Establece o identificador dun avatar
     * @param id identificador do avatar
     */
    public void setId(String id) {
        if(id!=null)
            this.id = id;
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
        return (obj instanceof Avatar)&&(this.id.equals(((Avatar) obj).id));
    }

    /**
     * Permite imprimir a información dun avatar en formato textual
     * @return devolve a información dun avatar nun String
     */
    @Override
    public String toString(){
        return "{"+"" +
                "\n\tId: "+this.id +
                ",\n\tTipo: "+this.getTipo().toString()+
                ",\n\tPosicion: "+this.xogador.getPosicion().getNome() +
                ",\n\tXogador: "+this.xogador.getNome()+
                "\n}";
    }
}
