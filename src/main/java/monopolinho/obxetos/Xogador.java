package monopolinho.obxetos;

import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasXogador;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoGasto;
import monopolinho.tipos.TipoMovemento;

import java.util.ArrayList;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Xogador {

    private String nome;
    private Avatar avatar;
    private float fortuna;
    private ArrayList<Casilla> propiedades;
    private int turnosNoCarcere;
    private int turnosInvalidado;
    //private boolean podeLanzar;
    //private int vecesTiradas;
    private EstadoXogador estadoXogador;
    private EstadisticasXogador estadisticas;


    /**
     *  Constructor sen argumentos crea a banca.
     */
    public Xogador(){
        this.nome="Banca";
        this.avatar=null;
        this.fortuna=0.0f;
        this.turnosNoCarcere =0;
        this.turnosInvalidado=0;
        this.propiedades=new ArrayList<>();
        this.estadoXogador=EstadoXogador.ESPECIAL;
        this.estadisticas=new EstadisticasXogador();
    }

    /**
     * Este constructor crea os xogadores que van "xogar" a partida.
     * @param nome Nome usuario
     * @param tipoMovemento Tipo de movemento no taboeiro
     */
    public Xogador(String nome, TipoMovemento tipoMovemento){
        this.nome=nome;
        this.fortuna= Valor.FORTUNA_INCIAL;
        this.avatar=new Avatar(tipoMovemento,this);
        this.propiedades=new ArrayList<>();
        this.turnosInvalidado=0;
        this.turnosNoCarcere=0;
        this.estadoXogador=EstadoXogador.NORMAL;
        this.estadisticas=new EstadisticasXogador();
    }

    /**
     * @return Saber se un xogador esta no carcere.
     */
    public boolean estaNoCarcere(){
        return (this.turnosNoCarcere!=0);
    }

    /**
     * @param casilla Casilla a engadir
     */
    public void engadirPropiedade(Casilla casilla){
        if(casilla!=null){
            this.propiedades.add(casilla);
        }
    }

    /**
     * Este metodo elimina unha casilla das propiedades do xogador.
     * @param casilla Casilla a eliminar do usuario
     */
    public void eliminarPropiedade(Casilla casilla){
        if(casilla!=null)this.propiedades.remove(casilla);
    }

    /**
     * Este metodo engade diñeiro ao xogador.
     * @param dinero Engade diñeiro a un usuario
     */
    public void engadirDinheiro(float dinero, TipoGasto tipoGasto){
        switch (tipoGasto){
            case BOTE_PREMIO:
                this.estadisticas.engadirPremiosInversionesOBote(dinero);
                break;
            case VOLTA_COMPLETA:
                this.estadisticas.engadirPasarPolaCasillaDeSalida(dinero);
                break;
            case ALQUILER:
                this.estadisticas.engadirCobroDeAlquileres(dinero);
        }
        fortuna+=dinero;
    }

    /**
     * Este metodo quita diñeiro ao xogador.
     * Engadese a dinheiro gastado
     * @param dinero diñeiro a quitar
     * @return Delvolve se ten cartos para gastar
     */
    public boolean quitarDinheiro(float dinero,TipoGasto tipoGasto){
        if(fortuna<dinero)
            return false;
        switch (tipoGasto){
            case IMPOSTO:
            case TASAS:
                this.estadisticas.engadirPagoTasasEImpuestos(dinero);
                break;
            case COMPRA:
            case EDIFICAR:
                this.estadisticas.engadirDineroInvertido(dinero);
                break;
            case ALQUILER:
                this.estadisticas.engadirPagoDeAlquileres(dinero);
                break;
        }
        this.estadisticas.engadirDineroGastado(dinero);
        this.fortuna-=dinero;
        return true;
    }


    /**
     * Este metodo permite saber o número de casillas do tipo @tipo en propiedade
     * @return Retorna o numero de propiedades de tipo @tipo en propiedade do xogador
     */
    public int numTipoCasillaPosesion(TipoCasilla tipo){
        int numCasillas=0;
        for(Casilla c:this.propiedades)
            if(c.getTipoCasilla() == tipo)
                numCasillas++;
        return numCasillas;
    }

    /**
     * @return Devolve se o xogador está en bancarrota
     */
    public boolean enBancarrota(){
        return (this.estadoXogador==EstadoXogador.BANCARROTA);
    }

    /**
     * Mete un xogador no cárcere
     */
    public void meterNoCarcere(){
        this.turnosNoCarcere =3;
        this.estadisticas.engadirVecesNoCarcere();
    }

    public void restarTurnosInvalidado(){
        if(this.turnosInvalidado>0)this.turnosInvalidado--;
    }

    /**
     * Saca un xogador do cárcere
     */
    public void sairDoCarcere(){
        this.turnosNoCarcere =0;
    }

    /**
     * @return Fai unha descripción completa do xogador
     */
    public String describir(){
        String listaprop="[";
        String listaHipotecas="[";
        String edificios="[";


        if(this.propiedades.size()!=0)
            for(Casilla c:this.propiedades) {
                if (!c.getEstaHipotecada()) listaprop += c.getNome() + ", ";
                else listaHipotecas += c.getNome() + ", ";
                for(Edificio e:c.getEdificios()){
                    edificios+=e+" ("+e.getPosicion().getNome()+"), ";
                }
            }

        listaHipotecas=(listaHipotecas.length()==1)?"[]":listaHipotecas.substring(0,listaHipotecas.length()-2)+"]";
        listaprop=(listaprop.length()==1)?"[]":listaprop.substring(0,listaprop.length()-2)+"]";
        edificios=(edificios.length()==1)?"[]":edificios.substring(0,edificios.length()-2)+"]";

        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ ((this.enBancarrota())?"BANCARROTA":this.fortuna)+ ",\n"+
                "\tGastos:"+  this.estadisticas.getDineroGastado() +",\n"+
                "\tPropiedades:"+listaprop+"\n"+
                "\tHipotecas:"+listaHipotecas+"\n"+
                "\tEdificios:"+edificios+"\n"+
                "}";
    }

    /**
     * @return Nome do xogador
     */
    public String getNome() {
        return nome;
    }

    /**
     * Establece o nome do xogador
     * @param nome
     */
    public void setNome(String nome) {
        if(nome!=null)this.nome = nome;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Establece o avatar do xogador
     * @param avatar
     */
    public void setAvatar(Avatar avatar) {
        if(nome!=null)this.avatar = avatar;
    }

    /**
     * @return
     */
    public float getFortuna() {
        return fortuna;
    }

    /**
     * Establece a forturna do xogador
     * @param fortuna
     */
    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }

    /**
     * @return Delvolve unha lista cas propiedades do xogador
     */
    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    /**
     * Establece as propiedades do xogador
     * @param propiedades
     */
    public void setPropiedades(ArrayList<Casilla> propiedades) {
        if(propiedades!=null){
            for(Casilla casilla:propiedades)
                if (casilla==null)return;
            this.propiedades = propiedades;
        }
    }


    /**
     * @return Número de turnos na cárcel
     */
    public int getTurnosNoCarcere() {
        return turnosNoCarcere;
    }

    /**
     * Establece o número de turnos na cárcel
     * @param turnosNoCarcere E
     */
    public void setTurnosNoCarcere(int turnosNoCarcere) {
        this.turnosNoCarcere = turnosNoCarcere;
    }

    /**
     * @return O xogador está en bancarrota
     */
    public EstadoXogador estadoXogador() {
        return estadoXogador;
    }

    /**
     * Establece se o xogoador está ou non en bancarrota
     * @param estadoXogador
     */
    public void setEstadoXogador(EstadoXogador estadoXogador) {
        this.estadoXogador = estadoXogador;
    }

    /**
     * @param c Casilla donde colocar o Xogador
     */
    public void setPosicion(Casilla c){
        if(c!=null)this.avatar.setPosicion(c);
    }

    /**
     * Devolve a posición do Xogador
     * @return Casilla posición
     */
    public Casilla getPosicion(){
        return this.avatar.getPosicion();
    }

    /**
     * @return Devolve as estadisticas dun xogador
     */
    public EstadisticasXogador getEstadisticas() {
        return estadisticas;
    }

    /**
     * Establece as estadísticas dun xogador
     * @param estadisticas
     */
    public void setEstadisticas(EstadisticasXogador estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * @return Devolve os turnos nos que o xogador non pode realizar ningunha acción
     */
    public int getTurnosInvalidado() {
        return turnosInvalidado;
    }

    /**
     * Establece os turnos no que o xogador non pode realizar ningunha acción
     * @param turnosInvalidado
     */
    public void setTurnosInvalidado(int turnosInvalidado) {
        this.turnosInvalidado = turnosInvalidado+1;
    }

    /**
     * Devolve a información do usuario como unha String
     * @return Información usuario string
     */
    @Override
    public String toString(){

        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ ((this.enBancarrota())?"BANCARROTA":this.fortuna) + ",\n"+
                "}";
    }

    /**
     * Compara se os obxetos son iguais
     * @param obj Obxeto a comparar
     * @return Son iguais os obxectos
     */
    public boolean equals(Object obj){
        if(obj instanceof Xogador){
            if(this.nome.equals(((Xogador) obj).nome))return true;
        }
        return false;
    }
}
