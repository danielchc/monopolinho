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
    private Taboeiro taboeiro;
    private boolean estaHipotecada;


    /**
     * Crea unha nova instancia de casilla
     * @param nome Nome da casilla
     * @param tipoCasilla Tipo de casilla
     */
    public Casilla(String nome,TipoCasilla tipoCasilla){
        if(nome==null || tipoCasilla==null){
            System.err.println("Error creando a casilla");
            System.exit(1);
        }
        this.nome=nome;
        this.tipoCasilla=tipoCasilla;
        this.avatares=new ArrayList<Avatar>();
        this.grupo=null;
        this.estaHipotecada=false;
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

    /**
     * Crea unha nova instancia de SOLAR
     * @param nome Nome do SOLAR
     * @param grupo Grupo do solar
     */
    public Casilla(String nome,Grupo grupo){
        this(nome,TipoCasilla.SOLAR);
        this.grupo=grupo;
        this.grupo.engadirSolar(this);
        this.colorCasilla=this.getGrupo().getColor();
    }

    /**
     * Crea unha nova casilla de IMPOSTO
     * @param nome Nome da casilla
     * @param tipoCasilla Tipo de Casilla(preterminado IMPOSTO neste constructor)
     * @param imposto Imposto a pagar o pasar pola casilla
     */
    public Casilla(String nome,TipoCasilla tipoCasilla,float imposto){
        this(nome,tipoCasilla);
        if (this.getTipoCasilla()==TipoCasilla.IMPOSTO)this.imposto=imposto;
    }

    /**
     * Engade un avatar a lista de avatares que se atopan nunha casilla
     * NON CHAMAR ESTA FUNCIÓN FORA DE XOGADOR
     * @param a Avatar a colocar
     */
    public void engadirAvatar(Avatar a){
        if(!avatares.contains(a)){
            avatares.add(a);
        }
    }

    /**
     * Elimina un avatar a lista de avatares que se atopan nunha casilla
     * NON CHAMAR ESTA FUNCIÓN FORA DE XOGADOR
     * @param a Avatar a eliminar da casilla
     */
    public void eliminarAvatar(Avatar a){
        if(avatares.contains(a)){
            avatares.remove(a);
        }
    }

    /**
     * @return Devolve as liñas que representan unha casilla
     */
    public String[] getRepresentacion(){
        String avataresCasilla="";
        if(!this.avatares.isEmpty())avataresCasilla="&";
        for(Avatar a:this.avatares)avataresCasilla+=a.getId()+" ";
        return new String[]{
            ReprTab.colorear(this.colorCasilla, ReprTab.borde()),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.nome:"")),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.getValor()+"$":this.nome)),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
            ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }


    /**
     * @return Este metodo permite saber se unha casilla se pode comprar ou non.
     */
    public boolean podeseComprar(){
        return (this.tipoCasilla== Casilla.TipoCasilla.SOLAR || this.tipoCasilla == Casilla.TipoCasilla.SERVICIO || this.tipoCasilla== Casilla.TipoCasilla.TRANSPORTE);
    }

    /**
     * @return Devolve o valor dunha casilla
     */
    public float getValor() {
        if(this.grupo!=null)return (float) Math.ceil((this.grupo.getValor()/this.grupo.getSolares().size()));
        else return valorServicio;
    }

    /**
     * Establece o valor dun servicio se a casilla é un servicio
     * @param valorServicio
     */
    public void setValorServicio(float valorServicio) {
        if(this.getTipoCasilla()==TipoCasilla.SERVICIO)this.valorServicio = valorServicio;
    }

    /**
     * @return Devolve o valor do imposto da casilla actual
     */
    public float getImposto() {
        if(this.getTipoCasilla()==TipoCasilla.IMPOSTO)return imposto;
        else return -1;
    }

    /**
     * Establece o imposto a pagar por caer nesta casilla, se a casilla é un servizo
     * @param imposto
     */
    public void setImposto(float imposto) {
        if(this.getTipoCasilla()==TipoCasilla.IMPOSTO)this.imposto = imposto;
    }

    /**
     * @return Devolve o prezo a pagar pola hipoteca
     */
    public float getHipoteca(){
        if(podeseComprar()){
            return getValor()*Valor.FACTOR_HIPOTECA;
        }
        return -1;
    }

    /**
     * @return Devolve o valor a pagar polo alquiler da casilla
     */
    public float getAlquiler() {
        return getValor()*Valor.FACTOR_ALQUILER;
    }

    /**
     * @return Devolve se unha casilla está hipotecada
     */
    public boolean getEstaHipotecada() {
        return estaHipotecada;
    }

    /**
     * Establece se unha casilla está hipotecada
     * @param estaHipotecada
     */
    public void setEstaHipotecada(boolean estaHipotecada) {
        this.estaHipotecada = estaHipotecada;
    }

    /**
     * @return Devolve o tipo de casilla
     */
    public TipoCasilla getTipoCasilla() {
        return tipoCasilla;
    }

    /**
     * Establece o tipo de casilla
     * @param tipoCasilla
     */
    public void setTipoCasilla(TipoCasilla tipoCasilla) {
        this.tipoCasilla = tipoCasilla;
    }

    /**
     * @return Devolve o nome da casilla
     */
    public String getNome() {
        return nome;
    }

    /**
     * Establece o nome dunha casilla
     * @param nome
     */
    public void setNome(String nome) {
        if(nome!=null)this.nome = nome;
    }

    /**
     * @return Devolve o dono da casilla
     */
    public Xogador getDono() {
        return dono;
    }

    /**
     * Establece o xogador dono de esta casilla
     * @param dono Xogador dono de esta casilla
     */
    public void setDono(Xogador dono) {
        if(dono!=null){
            if(this.dono!=null)
                dono.eliminarPropiedade(this);
            this.dono = dono;
            dono.engadirPropiedade(this);
        }
    }

    /**
     * @return Devolve o grupo que pertenece esta casilla
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * Establece o grupo dunha casilla, solo para solares
     * @param grupo
     */
    public void setGrupo(Grupo grupo) {
        if(grupo!=null)this.grupo = grupo;
    }

    /**
     * @return Devolve a posición da casilla no taboeiro, do 0 o 39
     */
    public int getPosicionIndex() {
        return posicion;
    }

    /**
     * Establece o indice no taboeiro da casilla
     * @param posicion
     */
    public void setPosicionIndex(int posicion) {
        this.posicion = posicion;
    }

    /**
     * @return Devolve o valor dunha casilla
     */
    public Valor.ReprColor getColorCasilla() {
        return colorCasilla;
    }

    /**
     * @param colorCasilla Establece a cor dunha casilla
     */
    public void setColorCasilla(Valor.ReprColor colorCasilla) {
        this.colorCasilla = colorCasilla;
    }

    /**
     * @return Devolve o taboeiro no que se atopa a casilla
     */
    public Taboeiro getTaboeiro() {
        return taboeiro;
    }

    /**
     * Establece o taboeiro no que se atopa a casilla
     * @param taboeiro
     */
    public void setTaboeiro(Taboeiro taboeiro) {
        if(taboeiro!=null)this.taboeiro = taboeiro;
    }

    /**
     * @return Devolve os avatares que se atopan na casilla
     */
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    /**
     * Establece os avatares dunha casilla
     * @param avatares
     */
    public void setAvatares(ArrayList<Avatar> avatares) {
        if(avatares!=null)this.avatares = avatares;
    }

    /**
     * @return Devolve a información dunha casilla
     */
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
                        "Xogadores:["+xogadoresCarcel+"\n\t]" +
                        "\n}";
                break;
            case IMPOSTO:
                texto="{\n\t" +
                            "Tipo: "+this.tipoCasilla+",\n\t" +
                            "Pagar:"+ getImposto() +
                        "\n}";
                break;
            case SERVICIO:
            case TRANSPORTE:
                texto="{"+
                            "\n\tNome: "+this.nome+((this.estaHipotecada)?"(Hipotecada)":"")+"\n\t" +
                            "Tipo: "+this.tipoCasilla+"\n\t" +
                            "Precio: "+this.getValor()+
                            ((!this.dono.getNome().equals("Banca"))?"\n\tPropietario: "+this.dono.getNome():"") +
                        "\n}";
                break;
            default:
                texto="{"+
                            "\n\tNome: "+this.nome+((this.estaHipotecada)?"(Hipotecada)":"")+"\n\t" +
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

    /**
     * Compara se os obxetos son iguais
     * @param obj Obxeto a comparar
     * @return Son iguais os obxectos
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Casilla){
            if(this.posicion==((Casilla) obj).posicion && this.nome==((Casilla) obj).nome)return true;
        }
        return false;
    }
}
