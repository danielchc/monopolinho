package monopolinho.obxetos;

import jdk.nashorn.internal.runtime.JSONListAdapter;
import monopolinho.axuda.ReprTab;

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
        INFRAESTRUCTURA
    }
    private String nome;
    private Grupo grupo;
    private float valor;
    private int posicion;
    private Xogador dono;
    private TipoCasilla tipoCasilla;
    private ReprTab.ReprColor colorCasilla;
    ArrayList<Avatar> avatares;

    public Casilla(String nome,Grupo grupo,TipoCasilla tipoCasilla,int posicion, float valor){
        this.nome=nome;
        this.grupo=grupo;
        this.posicion=posicion;
        this.valor=valor;
        this.tipoCasilla=tipoCasilla;
        switch (tipoCasilla){
            case SOLAR:
                this.colorCasilla=this.getGrupo().getColor();
                break;
            case SORTE:
                this.colorCasilla= ReprTab.ReprColor.ANSI_RED_BOLD;
                break;
            case CARCEL:
                this.colorCasilla= ReprTab.ReprColor.ANSI_WHITE_BACKGROUND;
                break;
            case PARKING:
                this.colorCasilla= ReprTab.ReprColor.ANSI_HIGH_WHITE_BACKGROUND;
                break;
            case IMPOSTO:
                this.colorCasilla= ReprTab.ReprColor.ANSI_GREEN_BOLD;
                break;
            case INFRAESTRUCTURA:
                this.colorCasilla= ReprTab.ReprColor.ANSI_BLUE_BOLD;
                break;
            default:
                this.colorCasilla= ReprTab.ReprColor.ANSI_BLACK;
        }
        this.avatares=new ArrayList<Avatar>();
    }

    public void engadirAvatar(Avatar a){
        a.setPosicion(this);
        if(!avatares.contains(a))avatares.add(a);
    }

    public void eliminarAvatar(Avatar a){
        if(avatares.contains(a))avatares.remove(a);
    }

    public String getNome() {
        return nome;
    }

    public Xogador getDono() {
        return dono;
    }

    public void setDono(Xogador dono) {
        if(dono!=null)this.dono = dono;
    }

    public float getValor() {
        return valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public Grupo getGrupo() {
        return grupo;
    }


    public String[] getRepresentacion(){
        String avataresCasilla="";
        for(Avatar a:this.avatares)avataresCasilla+="&"+a.getId()+" ";
        return new String[]{
                ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(this.nome)),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?valor+"$":"")),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
                ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
        };
    }

    @Override
    public String toString(){
        String texto="{"+"\n\tNome: "+this.nome+"\n\tGrupo: "+this.grupo+"\n\tPrecio: "+this.valor+"\n\tPropietario"+"\n}";
        return texto;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            if(this.posicion==((Casilla) obj).posicion)return true;
        }
        return false;
    }
}
