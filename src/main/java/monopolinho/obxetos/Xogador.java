package monopolinho.obxetos;

import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasXogador;
import monopolinho.obxetos.avatares.*;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.edificios.Edificio;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoMovemento;
import monopolinho.tipos.TipoTransaccion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Xogador {

    private String nome;
    private Avatar avatar;
    private float fortuna;
    private ArrayList<Propiedade> propiedades;
    private int turnosNoCarcere;
    private int turnosInvalidado;
    private EstadoXogador estadoXogador;
    private EstadisticasXogador estadisticas;
    private HashMap<String,Trato> tratos;
    private HashMap<Propiedade,Integer> nonAlquiler;

    /**
     *  Constructor sen argumentos crea a banca.
     */
    public Xogador(){
        this.nome="Banca";
        this.avatar=null;
        this.fortuna=0.0f;
        this.turnosNoCarcere=-1;
        this.turnosInvalidado=0;
        this.propiedades=new ArrayList<>();
        this.estadoXogador=EstadoXogador.ESPECIAL;
        this.estadisticas=new EstadisticasXogador();
        this.tratos=new HashMap<>();
        this.nonAlquiler=new HashMap<>();
    }

    /**
     * Este constructor crea os xogadores que van "xogar" a partida.
     * @param nome Nome usuario
     * @param tipoMovemento Tipo de movemento no taboeiro
     */
    public Xogador(String nome, TipoMovemento tipoMovemento){
        this.nome=nome;
        this.fortuna= Valor.FORTUNA_INCIAL;
        switch (tipoMovemento){
            case PELOTA:
                this.avatar=new Pelota(this);
                break;
            case COCHE:
                this.avatar=new Coche(this);
                break;
            case SOMBREIRO:
                this.avatar=new Sombreiro(this);
                break;
            case ESFINXE:
                this.avatar=new Esfinxe(this);
                break;
        }
        this.propiedades=new ArrayList<>();
        this.turnosInvalidado=0;
        this.turnosNoCarcere=-1;
        this.estadoXogador=EstadoXogador.NORMAL;
        this.estadisticas=new EstadisticasXogador();
        this.tratos=new HashMap<>();
        this.nonAlquiler=new HashMap<>();
    }

    /**
     * @return Saber se un xogador esta no carcere.
     */
    public boolean estaNoCarcere(){
        return (this.turnosNoCarcere!=-1);
    }

    /**
     * @param casilla Casilla a engadir
     */
    public void engadirPropiedade(Propiedade casilla){
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
     * Este método engade un trato ao xogador
     * @param t Trato a engadir
     */
    public void engadirTrato(Trato t){
        if(t!=null){
            this.tratos.put(t.getID(),t);
        }
    }

    /**
     * Este método elimina un trato ao xogador
     * @param Id key do trato
     * @return devolve o Trato eliminado, null si non se eliminou
     */
    public boolean eliminarTrato(String Id ){
        Trato t;
        if(Id!=null){
            t=this.tratos.remove(Id);
            if(t!=null){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Este método busca un trato na lista de tratos do xogador
     * @param id Id do trato
     * @return devolve o trato si o encontra, null se non
     */
    public Trato buscarTrato(String id){
        return this.tratos.get(id.toLowerCase());
    }

    /**
     * Este método lista todos os tratos do xogador
     */
    public String listarTratos(){
        if(this.tratos.isEmpty()) return "Non tes tratos.";
        String tratos="";
        Collection<Trato> col=this.tratos.values().stream().sorted((o1, o2) -> o1.getID().compareTo(o2.getID())).collect(Collectors.toList());
        for (Trato t:col){
            tratos+=t.describirTrato()+"\n";
        }
        return tratos;
    }


    /**
     * Este método engade un trato de non pago de alquiler á lista
     * @param prop propiedade
     * @param veces veces sin pagar
     */
    public void engadirNonAlquiler(Propiedade prop,int veces){
        if(prop!=null){
            this.nonAlquiler.put(prop,new Integer(veces+1));
        }
    }

    /**
     * Este método resta en 1 as veces que quedas sen pagar alquiler
     * @param prop propiedade a verificar
     * @return Se non estás exento nesa propiedade devolve false, e se non se acabaron os turnos, restase 1 e devolve true
     */
    public boolean restarVecesNonAlquiler(Propiedade prop){
        Integer oldVeces=this.nonAlquiler.get(prop);
        if(oldVeces==null) return false;
        oldVeces--;
        if (oldVeces==0){
            this.nonAlquiler.remove(prop);
        }else{
            this.nonAlquiler.replace(prop,oldVeces);
            return true;
        }
        return false;
    }



    /**
     * Este metodo engade diñeiro ao xogador.
     * @param dinero Engade diñeiro a un usuario
     */
    public void engadirDinheiro(float dinero, TipoTransaccion tipoTransaccion){
        switch (tipoTransaccion){
            case BOTE_PREMIO:
                this.estadisticas.engadirPremiosInversionesOBote(dinero);
                break;
            case VOLTA_COMPLETA:
                this.estadisticas.engadirPasarPolaCasillaDeSaida(dinero);
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
    public boolean quitarDinheiro(float dinero, TipoTransaccion tipoTransaccion){
        if(fortuna<dinero)
            return false;
        switch (tipoTransaccion){
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
        this.turnosNoCarcere=4;
        this.estadisticas.engadirVecesNoCarcere();
    }

    public void restarTurnoCarcere(){
        if(this.turnosNoCarcere>0)this.turnosNoCarcere--;
    }

    public void restarTurnosInvalidado(){
        if(this.turnosInvalidado>0)this.turnosInvalidado--;
    }

    /**
     * Saca un xogador do cárcere
     */
    public void sairDoCarcere(){
        this.turnosNoCarcere=-1;
    }

    /**
     * @return Fai unha descripción completa do xogador
     */
    public String describir(){
        String listaPropiedades="[",listaHipotecas="[",listaEdificios="[";

        for(Propiedade c:this.propiedades) {
            if(c instanceof Solar)
                for(Edificio e:((Solar)c).getEdificios()) listaEdificios+=e+" ("+e.getPosicion().getNome()+"), ";
        }


        listaEdificios=(listaEdificios.length()==1)?"[]":listaEdificios.substring(0,listaEdificios.length()-2)+"]";

        listaHipotecas= "["+this.propiedades.stream().filter(x->x.getEstaHipotecada()).map(Propiedade::getNome).collect(Collectors.joining(", "))+"]";
        listaPropiedades= "["+this.propiedades.stream().filter(x->!x.getEstaHipotecada()).map(Propiedade::getNome).collect(Collectors.joining(", "))+"]";

        return "{\n\tNome:" +this.nome+
                ",\n\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+
                ",\n\tFortuna:"+ ((this.enBancarrota())?"BANCARROTA":this.fortuna)+
                ",\n\tGastos:"+  this.estadisticas.getDineroGastado() +
                ",\n\tPropiedades:"+listaPropiedades+
                ",\n\tHipotecas:"+listaHipotecas+
                ",\n\tEdificios:"+listaEdificios+
                "\n}";
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
    public ArrayList<Propiedade> getPropiedades() {
        return propiedades;
    }

    /**
     * Establece as propiedades do xogador
     * @param propiedades
     */
    public void setPropiedades(ArrayList<Propiedade> propiedades) {
        if(propiedades!=null){
            for(Propiedade casilla:propiedades)
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
     * @return Devolve o listado de tratos do xogador
     */
    public HashMap<String, Trato> getTratos() {
        return tratos;
    }

    /**
     * Establece os tratos do xogador
     * @param tratos
     */
    public void setTratos(HashMap<String, Trato> tratos) {
        if(tratos!=null){
            this.tratos = tratos;
        }
    }


    /**
     * Devolve a información do usuario como unha String
     * @return Información usuario string
     */
    @Override
    public String toString(){
        return "{\n\tNome:" +this.nome+
                ",\n\tAvatar:"+ ((getAvatar()!=null)?getAvatar().getId():"-")+
                ",\n\tFortuna:"+ ((this.enBancarrota())?"BANCARROTA":this.fortuna) +
                "\n}";
    }

    /**
     * Compara se os obxetos son iguais
     * @param obj Obxeto a comparar
     * @return Son iguais os obxectos
     */
    public boolean equals(Object obj){
        return (obj instanceof Xogador)&&(this.nome.equals(((Xogador) obj).nome));
    }
}
