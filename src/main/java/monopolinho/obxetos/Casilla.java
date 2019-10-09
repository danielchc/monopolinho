package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.axuda.Valor;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
public class Casilla {
    public enum TipoCasilla{
        SOLAR,
        CARCEL,
        IRCARCEL,
        PARKING,
        SALIDA,
        IMPOSTO,
        SORTE,
        INFRAESTRUCTURA,
        COMUNIDADE,
        TRANSPORTE
    }
    private String nome;
    private Grupo grupo;
    private float valor;
    private float alquiler;
    private int posicion=-1;
    private Xogador dono;
    private TipoCasilla tipoCasilla;
    private Valor.ReprColor colorCasilla;
    private ArrayList<Avatar> avatares;
 //F


    /////////CONSTRUCTORES/////////////////

    public Casilla(String nome,TipoCasilla tipoCasilla){
        if(nome==null || tipoCasilla==null){
            System.err.println("Error creando a casilla");
            System.exit(1);
        }
        this.nome=nome;
        this.tipoCasilla=tipoCasilla;
        this.avatares=new ArrayList<Avatar>();
        this.grupo=null;
        switch (tipoCasilla){
            case SORTE:
                this.colorCasilla= Valor.ReprColor.ANSI_RED_BOLD;
                break;
            case PARKING:
            case CARCEL:
            case SALIDA:
            case IRCARCEL:
                this.colorCasilla= Valor.ReprColor.ANSI_BLACK_BOLD;
                break;
            case IMPOSTO:
                this.colorCasilla= Valor.ReprColor.ANSI_GREEN_BOLD;
                break;
            case TRANSPORTE:
                this.colorCasilla=Valor.ReprColor.ANSI_PURPLE_BOLD;
                break;
            case INFRAESTRUCTURA:
                this.colorCasilla= Valor.ReprColor.ANSI_BLUE_BOLD;
                break;
            default:
                this.colorCasilla= Valor.ReprColor.ANSI_BLACK;
        }
        this.tipoCasilla=tipoCasilla;
    }

    public Casilla(String nome,TipoCasilla tipoCasilla,float valor){
        this(nome,tipoCasilla);
        this.valor=valor;
    }

    public Casilla(String nome,Grupo grupo,TipoCasilla tipoCasilla){
        this(nome,tipoCasilla);
        this.grupo=grupo;
        this.grupo.engadirSolar(this);
        this.colorCasilla=this.getGrupo().getColor();
    }


    ////////METODOS/////////////
    /*
        Este metodo engade un avatar á casilla.
     */
    public void engadirAvatar(Avatar a){
        if(!avatares.contains(a)){
            avatares.add(a);
        }
    }

    /*
        Este método elimina un avatar da casilla.
     */
    public void eliminarAvatar(Avatar a){
        if(avatares.contains(a)){
            avatares.remove(a);
        }
    }

    /*
        Este metodo permite que un xogador compre unha casilla.
     */
    public void comprar(Xogador comprador){
        if(this.tipoCasilla!=TipoCasilla.SOLAR && this.tipoCasilla!=TipoCasilla.INFRAESTRUCTURA){
            System.err.println("Non podes comprar esta casilla");
            return;
        }

        if(!comprador.quitarDinheiro(this.getValor())){
            System.err.println("Non tes suficiente diñeiro");
            return;
        }

        if (!this.dono.getNome().equals("Banca")){  //se é doutro xogador enton non se pode comprar
            System.err.println("Esta casilla pertence a outro xogador. Non a podes comprar");
            return;
        }

        this.dono.engadirDinheiro(this.getValor());
        this.dono.eliminarPropiedade(this);
        comprador.engadirPropiedade(this);
        this.dono=comprador;
    }

    /*
        Este metodo permite representar aos avatares nunha casilla
     */
    public String[] getRepresentacion(){
        String avataresCasilla="";
        for(Avatar a:this.avatares)avataresCasilla+="&"+a.getId()+" ";
        return new String[]{
                ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.nome:"")),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.getValor()+"$":this.nome)),
                //ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(Integer.toString(this.posicion))),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
                ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }


    ///////// Getters e Setters //////////

    public TipoCasilla getTipoCasilla() {
        return tipoCasilla;
    }

    public float getAlquiler() {
        return alquiler;
    }

    public String getNome() {
        return nome;
    }

    public Xogador getDono() {
        return dono;
    }

    public float getValor() {
        if(this.grupo!=null)return this.grupo.getValor()/this.grupo.getSolares().size();
        else return valor;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public int getPosicionIndex() {
        return posicion;
    }

    public void setAlquiler(float alquiler) {
        this.alquiler = alquiler;
    }

    public void setDono(Xogador dono) {
        if(dono!=null){
            this.dono = dono;
        }
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }



    /////////////////////////////////////overrides//////////////////////////////

    @Override
    public String toString(){
        String xogadores="";
        if(this.avatares!=null)for(Avatar a:this.avatares)xogadores+="\t"+a.getXogador().getNome()+",\n";
        String texto;
        switch (this.tipoCasilla){
            case CARCEL:
                texto="{\n\ttipo: Carcel,\n\tsalir:"+ Valor.SAIR_CARCERE+",\n\txogadores:[\n"+xogadores+"\n\t]\n}"; //IMPLEMENTAR ESTO
                break;
            case IMPOSTO:
                texto="{\n\tPagar BLABLALABLBALALBLALBLALAL\n}"; //IMPLEMENTAR ESTO
                break;
            case PARKING:
                texto="{\n\tBote BLABLALABLBALALBLALBLALAL\n}"; //IMPLEMENTAR ESTO
                break;
            case INFRAESTRUCTURA:
            case TRANSPORTE:
                texto="{"+"\n\tNome: "+this.nome+"\n\tTipo: "+this.tipoCasilla+"\n\tPrecio: "+this.getValor()+"\n\tPropietario: "+this.dono.getNome()+"\n}";
                break;
            default:
                texto="{"+"\n\tNome: "+this.nome+"\n\tTipo: "+this.tipoCasilla+"\n\tGrupo: "+this.getGrupo().getNome()+"\n\tPrecio: "+this.getValor()+"\n\tPropietario: "+this.dono.getNome()+"\n}";
                break;

        }
        return texto;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            if(this.posicion==((Casilla) obj).posicion && this.nome==((Casilla) obj).nome)return true;
        }
        return false;
    }
}
