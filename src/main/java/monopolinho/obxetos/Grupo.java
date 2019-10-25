package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.ArrayList;


/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Grupo {

    private String grupo_nome;
    private float valor;
    private Valor.ReprColor color;
    private ArrayList<Casilla> solares;
    private int numeroSolares;

    /**
     * Constructor para a clase Grupo de parámetros vacíos.
     * Inicializa o ArrayList de solares do grupo.
     */
    public Grupo(){
        this.solares=new ArrayList<>();
    }


    /**
     * Constructor para a clase Grupo
     * @param grupo_nome nome do grupo
     * @param color color para a representación no taboeiro do grupo
     * @param valor valor total dos solares do grupo
     * @param numeroSolares numero de solares do grupo
     */
    public Grupo(String grupo_nome, Valor.ReprColor color,float valor,int numeroSolares){
        if(grupo_nome==null || color==null){
            System.err.println("Error: algun elemento non inicializado");
            return;
        }
        this.solares=new ArrayList<>();
        this.grupo_nome=grupo_nome;
        this.color=color;
        this.valor=valor;
        this.numeroSolares=numeroSolares;
    }


    /**
     * Este método permite engadir un solar a un grupo
     * @param c casilla a engadir
     */
    public void engadirSolar(Casilla c){
        if(c!=null)this.solares.add(c);
    }


    /**
     * Este método comproba se un xogador ten todo o grupo
     * @param x Xogador que propietario
     * @return Devolve se o xogador ten todo o solar ou non
     */
    public boolean tenTodoGrupo(Xogador x){
        for(Casilla c:this.solares){
            if(!c.getDono().equals(x)){
                return false;
            }
        }
        return true;
    }

    /**
     * @return solares pertencentes a un grupo
     */
    public ArrayList<Casilla> getSolares() {
        return solares;
    }


    /**
     * @return color de representación do grupo
     */
    public Valor.ReprColor getColor() {
        return this.color;
    }


    /**
     * @return nome do grupo
     */
    public String getNome(){
        return this.grupo_nome;
    }


    /**
     * @return valor do grupo
     */
    public float getValor() {
        return valor;
    }


    /**
     * Establece o valor dun grupo
     * @param valor valor total dos solares do grupo
     */
    public void setValor(float valor) {
        this.valor = valor;
    }


    /**
     * @return nome do grupo
     */
    public String getGrupo_nome() {
        return grupo_nome;
    }


    /**
     * Establece o nome do grupo
     * @param grupo_nome nome do grupo
     */
    public void setGrupo_nome(String grupo_nome) {
        if(grupo_nome!=null){
            this.grupo_nome = grupo_nome;
        }
    }


    /**
     * Establece o color do grupo
     * @param color color de representacion do grupo
     */
    public void setColor(Valor.ReprColor color) {
        this.color = color;
    }


    /**
     * Establece os solares do grupo
     * @param solares solares do grupo
     */
    public void setSolares(ArrayList<Casilla> solares) {
        if(solares!=null){
            this.solares = solares;
        }
    }

    /**
     * @return numero de solares do grupo
     */
    public int getNumeroSolares() {
        return numeroSolares;
    }


    /**
     * @param numeroSolares establece o numero de solares
     */
    public void setNumeroSolares(int numeroSolares) {
        this.numeroSolares = numeroSolares;
    }

    /**
     * Permite representar en texto a informacion dun grupo
     * @return String coa informacion do grupo
     */
    @Override
    public String toString(){
        String stSolares="";
        for(Casilla s:this.solares)stSolares+="\t\t"+s.getNome() +",\n";
        return "\n{\n\tTipo:"+this.grupo_nome+"\n\tSolares:[\n"+stSolares+"\n\t]\n}";
    }


    /**
     * Permite saber se dous grupo son iguais comparando se os seus nomes son iguais
     * @param obj Grupo
     * @return true se son iguais ou falso se non
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Grupo){
            return (this.grupo_nome.equals(((Grupo) obj).grupo_nome));
        }
        return false;
    }
}
