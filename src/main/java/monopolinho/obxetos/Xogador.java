package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.ArrayList;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Xogador {

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


    /**
     *  Constructor sen argumentos crea a banca.
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

    /**
     * Este constructor crea os xogadores que van "xogar" a partida.
     * @param nome Nome usuario
     * @param tipoMovemento Tipo de movemento no taboeiro
     */
    public Xogador(String nome, Avatar.TipoMovemento tipoMovemento){
        this.nome=nome;
        this.fortuna= Valor.FORTUNA_INCIAL;
        this.dineroGastado=0;
        this.avatar=new Avatar(tipoMovemento,this);
        this.propiedades=new ArrayList<>();
        this.podeLanzar=true;
        this.vecesTiradas=0;
        this.enBancarrota=false;
    }

    /**
     * Este metodo saca a un xogador da carcere pagando.
     * @return Se ten cartos para pagar para salir do cárcere
     */
    public boolean salirCarcel(){
        if(this.quitarDinheiro(Valor.SAIR_CARCERE)){
            setTurnosNaCarcel(0);
            return true;
        }
        return false;
    }

    /**
     * @return Saber se un xogador esta no carcere.
     */
    public boolean estaNaCarcel(){
        return (this.turnosNaCarcel!=0);
    }

    /**
     * Este metodo aumenta en 1 as veces que tirou un xogador. Serve para saber se un xogador sacou triples.
     */
    public void aumentarVecesTiradas() {
        this.vecesTiradas++;
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
    public void engadirDinheiro(float dinero){
        fortuna+=dinero;
    }

    /**
     * Este metodo quita diñeiro ao xogador.
     * Engadese a dinheiro gastado
     * @param dinero diñeiro a quitar
     * @return Delvolve se ten cartos para gastar
     */
    public boolean quitarDinheiro(float dinero){
        if(fortuna<dinero)return false;
        this.fortuna-=dinero;
        this.dineroGastado+=dinero;
        return true;
    }

    /**
     * @return Fai unha descripción completa do xogador
     */
    public String describir(){
        String listaprop="[";
        String listaHipotecas="[";
        if(this.propiedades.size()!=0)
            for(Casilla c:this.propiedades)
                if(!c.getEstaHipotecada())listaprop+="\n\t\t"+c.getNome()+",";
                else listaHipotecas+="\n\t\t"+c.getNome()+",";
        listaprop+="\n\t]";
        listaHipotecas+="\n\t]";
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ ((this.enBancarrota)?"BANCARROTA":this.fortuna)+ ",\n"+
                "\tGastos:"+  this.dineroGastado +",\n"+
                "\tPropiedades:"+listaprop+"\n"+
                "\tHipotecas:"+listaHipotecas+"\n"+
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
     * @return Obtén o diñeiro gastado polo xogador
     */
    public float getDineroGastado() {
        return dineroGastado;
    }

    /**
     * @param dineroGastado Establece o diñeiro gastado polo xogador
     */
    public void setDineroGastado(float dineroGastado) {
        this.dineroGastado = dineroGastado;
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
     * @return Devolve se o xogador ten turno ou non
     */
    public boolean isTenTurno() {
        return tenTurno;
    }

    /**
     * Establece se o xogador ten turno
     * @param tenTurno
     */
    public void setTenTurno(boolean tenTurno) {
        this.tenTurno = tenTurno;
    }

    /**
     * @return Número de turnos na cárcel
     */
    public int getTurnosNaCarcel() {
        return turnosNaCarcel;
    }

    /**
     * Establece o número de turnos na cárcel
     * @param turnosNaCarcel E
     */
    public void setTurnosNaCarcel(int turnosNaCarcel) {
        this.turnosNaCarcel = turnosNaCarcel;
    }

    /**
     * @return Comproba se o xogador pode lanzar
     */
    public boolean podeLanzar() {
        return podeLanzar;
    }

    /**
     * Establece se o xogador pode lanzar
     * @param podeLanzar
     */
    public void setPodeLanzar(boolean podeLanzar) {
        this.podeLanzar = podeLanzar;
    }

    /**
     * @return Número de veces que tirou un xogador
     */
    public int getVecesTiradas() {
        return vecesTiradas;
    }

    /**
     * Establece o número de veces que tirou un xogador
     * @param vecesTiradas
     */
    public void setVecesTiradas(int vecesTiradas) {
        this.vecesTiradas = vecesTiradas;
    }

    /**
     * @return O xogador está en bancarrota
     */
    public boolean isEnBancarrota() {
        return enBancarrota;
    }

    /**
     * Establece se o xogoador está ou non en bancarrota
     * @param enBancarrota
     */
    public void setEnBancarrota(boolean enBancarrota) {
        this.enBancarrota = enBancarrota;
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
     * Devolve a información do usuario como unha String
     * @return Información usuario string
     */
    @Override
    public String toString(){
        return "{\n\tNome:" +this.nome+ ",\n"+
                "\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+ ",\n"+
                "\tFortuna:"+ ((this.enBancarrota)?"BANCARROTA":this.fortuna) + ",\n"+
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
