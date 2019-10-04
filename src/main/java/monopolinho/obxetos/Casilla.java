package monopolinho.obxetos;

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
    private int posicion=-1; //VAYA PUTA ÑAPA CHAVAL
    private Xogador dono;
    private TipoCasilla tipoCasilla;
    private ReprTab.ReprColor colorCasilla;
    ArrayList<Avatar> avatares;
 //F
    public Casilla(String nome,TipoCasilla tipoCasilla){
        this.nome=nome;
        this.tipoCasilla=tipoCasilla;
        this.avatares=new ArrayList<Avatar>();
        this.grupo=null;//HABER COMO SOLUCIONAMOS ESTO XD
        switch (tipoCasilla){
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
    }
    public Casilla(String nome,Grupo grupo,TipoCasilla tipoCasilla, float valor){
        this(nome,tipoCasilla);
        this.grupo=grupo;
        this.colorCasilla=this.getGrupo().getColor();
        this.valor=valor;
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

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void comprar(Xogador comprador){
        if(comprador.quitarDinheiro(this.valor)){
            comprador.engadirPropiedade(this);
            this.dono.engadirDinheiro(this.valor);
            this.dono.eliminarPropiedade(this);
            this.dono=comprador;
        }
    }

    public String[] getRepresentacion(){
        String avataresCasilla="";
        for(Avatar a:this.avatares)avataresCasilla+="&"+a.getId()+" ";
        return new String[]{
                ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.nome:"")),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?valor+"$":this.nome)),
                //ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(Integer.toString(this.posicion))),
                ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
                ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }

    @Override
    public String toString(){
        String texto;
        switch (this.tipoCasilla){
            case CARCEL:
                texto="{\n\tCarcel Salir BLABLALABLBALALBLALBLALAL\n}"; //IMPLEMENTAR ESTO
            case IMPOSTO:
                texto="{\n\tPagar BLABLALABLBALALBLALBLALAL\n}"; //IMPLEMENTAR ESTO
            case PARKING:
                texto="{\n\tBote BLABLALABLBALALBLALBLALAL\n}"; //IMPLEMENTAR ESTO
            default:
                texto="{"+"\n\tNome: "+this.nome+"\n\tGrupo: "+this.grupo+"\n\tPrecio: "+this.valor+"\n\tPropietario"+"\n}";
                break;

        }
        return texto;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            //TA MAL IMPLEMENTAO
            if(this.posicion==((Casilla) obj).posicion)return true;
        }
        return false;
    }
}
