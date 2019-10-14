package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;

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
        SERVICIO,
        COMUNIDADE,
        TRANSPORTE
    }
    private String nome;
    private Grupo grupo;
    private float valorServicio;
    private float imposto;
    private int posicion=-1;
    private Xogador dono;
    private TipoCasilla tipoCasilla;
    private Valor.ReprColor colorCasilla;
    private ArrayList<Avatar> avatares;
    private Taboeiro taboeiro; //PUTA ÑAPA CHAVAL


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
                this.colorCasilla=Valor.ReprColor.ANSI_BLACK_BOLD;
                break;
            case IMPOSTO:
                this.colorCasilla=Valor.ReprColor.ANSI_GREEN_BOLD;
                break;
            case TRANSPORTE:
                this.colorCasilla=Valor.ReprColor.ANSI_PURPLE_BOLD;
                this.valorServicio=Valor.VALOR_TRANSPORTE;
                break;
            case SERVICIO:
                this.colorCasilla=Valor.ReprColor.ANSI_BLUE_BOLD;
                this.valorServicio=Valor.VALOR_SERVICIO;
                break;
            default:
                this.colorCasilla=Valor.ReprColor.ANSI_BLACK;
        }
        this.tipoCasilla=tipoCasilla;
    }

    public Casilla(String nome,Grupo grupo,TipoCasilla tipoCasilla){
        this(nome,tipoCasilla);
        this.grupo=grupo;
        this.grupo.engadirSolar(this);
        this.colorCasilla=this.getGrupo().getColor();
    }
    //
    public Casilla(String nome,TipoCasilla tipoCasilla,float imposto){
        this(nome,tipoCasilla);
        if (this.getTipoCasilla()==TipoCasilla.IMPOSTO)this.imposto=imposto;
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
        Este metodo permite representar aos avatares nunha casilla
     */
    public String[] getRepresentacion(){
        String avataresCasilla="";
        for(Avatar a:this.avatares)avataresCasilla+="&"+a.getId()+" ";
        return new String[]{
            ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.nome:"")),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.getValor()+"$":this.nome)),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
            ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }

    /*
        Este metodo permite saber se unha casilla se pode comprar ou non.
     */
    public boolean podeseComprar(){
        return (this.tipoCasilla== Casilla.TipoCasilla.SOLAR || this.tipoCasilla == Casilla.TipoCasilla.SERVICIO || this.tipoCasilla== Casilla.TipoCasilla.TRANSPORTE);
    }



    ///////// Getters e Setters //////////

    public TipoCasilla getTipoCasilla() {
        return tipoCasilla;
    }

    public float getAlquiler() {
        return getValor()*Valor.FACTOR_ALQUILER;
    }

    public String getNome() {
        return nome;
    }

    public Xogador getDono() {
        return dono;
    }

    public float getValor() {
        if(this.grupo!=null)return (float) Math.ceil((this.grupo.getValor()/this.grupo.getSolares().size()));
        else return valorServicio;
    }
    public float getImposto() {
        if(this.getTipoCasilla()==TipoCasilla.IMPOSTO)return imposto;
        else return -1;
    }

    public void setImposto(float imposto) {
        if(this.getTipoCasilla()==TipoCasilla.IMPOSTO)this.imposto = imposto;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public int getPosicionIndex() {
        return posicion;
    }

    public void setDono(Xogador dono) {
        if(dono!=null)this.dono = dono;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Taboeiro getTaboeiro() {
        return taboeiro;
    }

    public void setTaboeiro(Taboeiro taboeiro) {
        if(taboeiro!=null)this.taboeiro = taboeiro;
    }



    /////////////////////////////////////overrides//////////////////////////////

    @Override
    public String toString(){
        String xogadores="";
        String xogadoresCarcel="";
        if(this.avatares!=null) {
            for (Avatar a : this.avatares){
                xogadores += "\n\t\t" + a.getXogador().getNome() + ",";
                if (a.getXogador().estaNaCarcel())
                    xogadoresCarcel += "\n\t\t[" + a.getXogador().getNome() + "," + a.getXogador().getTurnosNaCarcel() + "],";
            }
        }
        String texto;
        switch (this.tipoCasilla){
            case PARKING:
                texto="{\n\t" +
                        "Bote: "+this.taboeiro.getBote()+"\n\t" +
                        "Xogadores:["+xogadores+"\n\t]\n}";
                break;

            case CARCEL:
                texto="{\n\t" +
                        "Tipo: "+this.tipoCasilla+",\n\t" +
                        "Salir:"+ Valor.SAIR_CARCERE+",\n\t" +
                        "Xogadores:["+xogadoresCarcel+"\n\t]\n}"; //IMPLEMENTAR ESTO
                break;
            case IMPOSTO:
                texto="{\n\t" +
                        "Tipo: "+this.tipoCasilla+",\n\t" +
                        "Pagar:"+ getImposto() + "\n}";
                break;
            case SERVICIO:
            case TRANSPORTE:
                texto="{"+"\n\tNome: "+this.nome+"\n\t" +
                        "Tipo: "+this.tipoCasilla+"\n\t" +
                        "Precio: "+this.getValor()+
                        ((!this.dono.getNome().equals("Banca"))?"\n\tPropietario: "+this.dono.getNome():"") +
                        "\n}";
                break;
            default:
                texto="{"+"\n\tNome: "+this.nome+"\n\t" +
                        "Tipo: "+this.tipoCasilla+"\n\t" +
                        "Grupo: "+this.getGrupo().getNome()+"\n\t" +
                        "Valor: "+this.getValor()+"\n\t" +
                        "Alquiler: "+this.getAlquiler()+
                        ((!this.dono.getNome().equals("Banca"))?"\n\tPropietario: "+this.dono.getNome():"") +
                        "\n}";
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
