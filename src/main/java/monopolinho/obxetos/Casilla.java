package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasCasilla;
import monopolinho.interfaz.Xogo;
import monopolinho.tipos.*;

import java.util.ArrayList;

/**
 * Clase casilla
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Casilla {
    private String nome;
    private Grupo grupo;
    private float valor;
    private float alquiler;
    private float usoServizo;
    private float imposto;
    private int posicion;
    private Xogador dono;
    private TipoCasilla tipoCasilla;
    private Valor.ReprColor colorCasilla;
    private ArrayList<Avatar> avatares;
    private Taboeiro taboeiro;
    private boolean estaHipotecada;
    private ArrayList<Edificio> edificios;
    private EstadisticasCasilla estadisticasCasilla;

    /**
     * Crea unha nova instancia de casilla
     * @param nome Nome da casilla
     * @param tipoCasilla Tipo de casilla
     */
    public Casilla(String nome,TipoCasilla tipoCasilla){
        if(nome==null || tipoCasilla==null){
            System.err.println("Error creando a casilla");
            return;
        }
        this.nome=nome;
        this.tipoCasilla=tipoCasilla;
        this.posicion=-1;
        this.avatares=new ArrayList<Avatar>();
        this.edificios=new ArrayList<>();
        this.grupo=null;
        this.estaHipotecada=false;
        this.valor=0;
        this.usoServizo=0;
        this.estadisticasCasilla=new EstadisticasCasilla();
        switch (tipoCasilla){
            case SORTE:
                this.colorCasilla= Valor.ReprColor.ANSI_RED_BOLD;
                break;
            case PARKING:
            case CARCERE:
            case SAIDA:
            case IRCARCERE:
                this.colorCasilla=Valor.ReprColor.ANSI_BLACK_BOLD;
                break;
            case IMPOSTO:
                this.colorCasilla=Valor.ReprColor.ANSI_GREEN_BOLD;
                break;
            case TRANSPORTE:
                this.colorCasilla=Valor.ReprColor.ANSI_PURPLE_BOLD;
                break;
            case SERVIZO:
                this.colorCasilla=Valor.ReprColor.ANSI_BLUE_BOLD;
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
        this.valor= (float) Math.ceil((this.grupo.getValor()/this.grupo.getNumeroSolares()));
        this.alquiler=valor*Valor.FACTOR_ALQUILER;
        this.colorCasilla=this.getGrupo().getColor();
    }

    /**
     * Crea unha nova casilla de IMPOSTO
     * @param nome Nome da casilla
     * @param tipoCasilla Tipo de Casilla(preterminado IMPOSTO neste constructor)
     * @param valor Imposto da casilla IMPOSTO
     */
    public Casilla(String nome,TipoCasilla tipoCasilla,float valor){
        this(nome,tipoCasilla);
        if (this.tipoCasilla==TipoCasilla.IMPOSTO)this.imposto=valor;
    }

    /**
     * Contructor para casilla TRANSPORTE OU SERVIZO
     * @param nome Nome transporte
     * @param tipoCasilla Tipo de Casilla
     * @param valor Valor de compra do servizo
     * @param usoServizo
     */
    public Casilla(String nome, TipoCasilla tipoCasilla, float valor, float usoServizo){
        this(nome,tipoCasilla);
        if (this.tipoCasilla==TipoCasilla.SERVIZO || this.tipoCasilla==TipoCasilla.TRANSPORTE){
            this.valor = valor;
            this.usoServizo=usoServizo;
        }
    }

    /**
     * Engade un avatar a lista de avatares que se atopan nunha casilla
     * NON CHAMAR ESTA FUNCIÓN FORA DE XOGADOR
     * @param a Avatar a colocar
     */
    public void engadirAvatar(Avatar a){
        if(a==null)return;
        if(!avatares.contains(a)){
            avatares.add(a);
        }
        this.estadisticasCasilla.engadirAvatarHistorial(a);
    }

    /**
     * Elimina un avatar a lista de avatares que se atopan nunha casilla
     * NON CHAMAR ESTA FUNCIÓN FORA DE XOGADOR
     * @param a Avatar a eliminar da casilla
     */
    public void eliminarAvatar(Avatar a){
        if(a==null)return;
        if(avatares.contains(a)){
            avatares.remove(a);
        }
    }

    /**
     * Este método conta as veces que caeu un avatar nunha casilla
     * @param a Avatar a buscar
     * @return Número de veces caídas
     */
    public int numeroVecesCaidas(Avatar a){
        int contador=0;
        for(Avatar x:this.estadisticasCasilla.getHistorial())
            if(x.equals(a))
                contador++;
        return contador;
    }

    /**
     * Esta función interpreta a acción que ten que facer o caer na casilla
     * @param xogo
     * @param valorDados
     * @return Información da acción interpretada
     */
    public String interpretarCasilla(Xogo xogo, int valorDados){
        Turno turno=xogo.getTurno();
        switch (tipoCasilla){
            case SOLAR:
                return interpretarSOLAR(xogo);
            case TRANSPORTE:
                return interpretarTRANSPORTE(xogo);
            case SERVIZO:
                return interpretarSERVIZO(xogo,valorDados);
            case CARCERE:
                turno.setPosicion(this);
                return "Só de visita...";
            case IRCARCERE:
                return interpretarIRCARCERE(xogo);
            case PARKING:
                return interpretarPARKING(xogo);
            case IMPOSTO:
                 return interpretarIMPOSTO(xogo);
            case SORTE:
            case COMUNIDADE:
            case SAIDA:
                turno.setPosicion(this);
                return "";
        }
        return "";
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
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?ReprTab.formatearNumeros(this.getValor()):this.nome)),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
            ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }

    /**
     * Engade un edificio a unha casilla
     * @param e Edificio a engadir á casilla
     */
    public void engadirEdificio(Edificio e){
        if(e!=null)
            this.edificios.add(e);
    }

    /**
     * Elimina un edificio dunha casilla
     * @param e Edificio a eliminar
     */
    public void eliminarEdificio(Edificio e){
        if(e!=null)
            this.edificios.remove(e);
    }


    /**
     * Este metodo devolve o número de edificios
     * @return Número de edificios
     */
    public int getNumeroEdificios(){
        return edificios.size();
    }

    /**
     * Este metodo devolve o número de edificios dun determinado tipo dunha casilla.
     * @param tipo Tipo de edificio
     * @return Número de edificios
     */
    public int getNumeroEdificiosTipo(TipoEdificio tipo){
        int num=0;
        for(Edificio e:this.edificios){
            if(e.getTipoEdificio()==tipo)num++;
        }
        return num;
    }


    /**
     * Este método permite saber si se pode seguir edificando nun solar
     * @return True si de pode seguir edificando e false se non.
     */
    public boolean podeseEdificarMais(TipoEdificio tipo){
        if((tipo==TipoEdificio.CASA)&&(this.edificios.size() == this.getGrupo().getNumeroSolares() * 4)){
            return false;
        }
        if(tipo!=TipoEdificio.CASA && this.getNumeroEdificiosTipo(tipo)>=this.getGrupo().getNumeroSolares()){
            return false;
        }

        return (!(
                ((tipo!=TipoEdificio.CASA)&&(this.getNumeroEdificiosTipo(tipo)>=this.getGrupo().getNumeroSolares())) ||
                ((tipo!=TipoEdificio.HOTEL) && this.getNumeroEdificios()>=this.getGrupo().getNumeroSolares()*4)
        ));
    }

    /**
     * Esta función renombra as casas para que sempre estean os ids entre 1 e N.
     */
    public void renombrarEdificios(){
        int contador=1;
        for(Edificio e:this.edificios){
            if(e.getTipoEdificio()==TipoEdificio.CASA){
                e.setId(e.getTipoEdificio() + String.valueOf(contador));
                contador++;
            }
        }
    }

    /**
     * @return Devolve os edificios de cada casilla
     */
    public String describirEdificios(){
        String text="";
        String[] edificiosTexto={"[","[","[","["};
        for(Edificio e:this.edificios)
            edificiosTexto[e.getTipoEdificio().ordinal()]+=e+", ";
        for(int i=0;i<4;i++)
            edificiosTexto[i]=((edificiosTexto[i].length()==1)?"[":edificiosTexto[i].substring(0,edificiosTexto[i].length()-2))+"]";
        text+="{\n"+
                "\n\tPropiedade: " + this.nome +
                ",\n\tCasas: " + edificiosTexto[TipoEdificio.CASA.ordinal()]+
                ",\n\tHoteles: " + edificiosTexto[TipoEdificio.HOTEL.ordinal()]+
                ",\n\tPiscinas: " + edificiosTexto[TipoEdificio.PISCINA.ordinal()]+
                ",\n\tPistas de deportes: " + edificiosTexto[TipoEdificio.PISTA_DEPORTES.ordinal()]+
                ",\n\tAlquiler: " + this.alquiler+
                "\n}"+
                "\n" + queSePodeConstruir();
        return text;
    }


    /**
     * Este metodo permite saber o precio según o tipo de edificio
     * @param tipo Input do usuario
     * @return Precio do edificio.
     */
    public float getPrecioEdificio(TipoEdificio tipo){
        if(this.tipoCasilla!=TipoCasilla.SOLAR)
            return -1f;

        switch (tipo){
            case HOTEL:
                return Valor.FACTOR_VALOR_HOTEL*this.getValor();
            case CASA:
                return Valor.FACTOR_VALOR_CASA*this.getValor();
            case PISCINA:
                return Valor.FACTOR_VALOR_PISCINA*this.getValor();
            case PISTA_DEPORTES:
                return Valor.FACTOR_VALOR_PISTADEPORTES*this.getValor();
        }
        return -1f;
    }

    /**
     * Devolve os edificios dunha casilla
     * @return Edificaciones da casilla
     */
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    /**
     * Estabelce os edificios dunha casilla
     * @param edificios Edificaciones na casilla
     */
    public void setEdificios(ArrayList<Edificio> edificios) {
        if(edificios!=null){
            this.edificios = edificios;
        }
    }

    /**
     * @return Obten as estadisticas dunha casilla
     */
    public EstadisticasCasilla getEstadisticas() {
        return estadisticasCasilla;
    }

    /**
     * Establece a estadisticas dunha casilla
     * @param estadisticasCasilla
     */
    public void setEstadisticas(EstadisticasCasilla estadisticasCasilla) {
        this.estadisticasCasilla = estadisticasCasilla;
    }

    /**
     * @return Este metodo permite saber se unha casilla se pode comprar ou non.
     */
    public boolean podeseComprar(){
        return (this.tipoCasilla== TipoCasilla.SOLAR || this.tipoCasilla == TipoCasilla.SERVIZO || this.tipoCasilla== TipoCasilla.TRANSPORTE);
    }

    /**
     * @return Devolve se hai que coller carta
     */
    public boolean haiQueCollerCarta(){
        return (this.tipoCasilla== TipoCasilla.COMUNIDADE || this.tipoCasilla == TipoCasilla.SORTE);
    }

    /**
     * @return Devolve o valor dunha casilla
     */
    public float getValor() {
        return valor;
    }

    /**
     * Establece o valor dun servicio se a casilla é un servicio
     * @param valor
     */
    public void setValor(float valor) {
        this.valor = valor;
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
        return alquiler;
    }

    /**
     * @param alquiler Establece o valor do alquiler
     */
    public void setAlquiler(float alquiler) {
        this.alquiler = alquiler;
    }

    /**
     * @return Valor uso servizo
     */
    public float getUsoServizo() {
        return usoServizo;
    }

    /**
     * Establece o valor de uso dun SERVIZO ou TRANSPORTE
     * @param usoServizo
     */
    public void setUsoServizo(float usoServizo) {
        this.usoServizo = usoServizo;
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
        if(grupo!=null)this.grupo =grupo;
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
     * @return Devolve o tipo de edificios que se poden construir
     */
    private String queSePodeConstruir(){
        String texto="";
        if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)<=this.grupo.getNumeroSolares()*4)
            texto+="Podes construir casas\n";
        if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)>=4 && this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2)
            texto+="Podes construir "+(this.grupo.getNumeroSolares()-this.getNumeroEdificiosTipo(TipoEdificio.HOTEL))+" hoteles\n";
        if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)>=2 && this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)>=1 && this.getNumeroEdificiosTipo(TipoEdificio.PISCINA)<2)
            texto+="Podes construir "+(this.grupo.getNumeroSolares()-this.getNumeroEdificiosTipo(TipoEdificio.PISCINA))+" piscina\n";
        if(this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)>=2 && this.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)<2)
            texto+="Podes construir "+(this.grupo.getNumeroSolares()-this.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES))+" pistas de deportes\n";

        return texto;
    }

    /**
     * Este metodo permite calcular o total a pagar de alquiler
     * @return Total alquiler a pagar
     */
    private float totalPagoAlquiler(){
        float aPagar=0;
        if(this.getNumeroEdificios()!=0){
            if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)>4)
                aPagar+=Valor.FACTOR_ALQUILER_EDIFICIOS[4]*this.alquiler;
            else
                aPagar+=Valor.FACTOR_ALQUILER_EDIFICIOS[this.getNumeroEdificiosTipo(TipoEdificio.CASA)]*this.alquiler;

            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*this.alquiler*70;
            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*this.alquiler*25;
            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*this.alquiler*25;
        }else{
            aPagar=this.alquiler; //REVISAR ESTO
        }
        return aPagar;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer nun solar
     * @return String co mensaxe
     */
    private String interpretarSOLAR(Xogo xogo){
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if(this.getEstaHipotecada()){
            mensaxe+="Caiche na casila "+this.getNome()+", pero está hipotecada, non pagas.";
            return mensaxe;
        }else{
            if((!this.getDono().equals(xogador))&&(!this.getDono().equals(xogo.getBanca()))){
                float aPagar=this.totalPagoAlquiler();

                aPagar*=(this.getGrupo().tenTodoGrupo(this.getDono()))?Valor.FACTOR_PAGO_ALQUILER:1f;
                if(xogador.quitarDinheiro(aPagar, TipoTransaccion.ALQUILER)){
                    this.estadisticasCasilla.engadirAlquilerPagado(aPagar);
                    this.getDono().engadirDinheiro(aPagar, TipoTransaccion.ALQUILER);
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome();
                    return mensaxe;
                }else{
                    mensaxe+="Non tes suficiente diñeiro para pagar o alquiler de "+aPagar+", teste que declarar en bancarrota ou hipotecar unha propiedade.";
                    xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                    return mensaxe;
                }
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer nun servizo
     * @return String co mensaxe
     */
    private String interpretarSERVIZO(Xogo xogo,int valorDados){
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.getDono().equals(xogador)) && (!this.getDono().equals(xogo.getBanca()))){
            float aPagar=valorDados*this.getUsoServizo();

            aPagar*=(this.getDono().numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 1)?4.0f:10.0f;

            if(xogador.quitarDinheiro(aPagar, TipoTransaccion.OTROS)){
                this.getDono().engadirDinheiro(aPagar, TipoTransaccion.OTROS);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome() +" por usar "+this.getNome();
            }else{
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                return mensaxe;
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }


    /**
     * Este metodo interpreta as accions a realizar ao caer nun transporte
     * @return String co mensaxe
     */
    private String interpretarTRANSPORTE(Xogo xogo){
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if((!this.getDono().equals(xogador)) && (!this.getDono().equals(xogo.getBanca()))){
            float aPagar=0;
            aPagar=this.getUsoServizo()*(this.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
            if(xogador.quitarDinheiro(aPagar, TipoTransaccion.OTROS)){
                this.getDono().engadirDinheiro(aPagar, TipoTransaccion.OTROS);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome() +" por usar "+this.getNome();
            }else{
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                return mensaxe;
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }


    /**
     * Este metodo interpreta as accions a realizar ao caer en ir carcel
     * @return String co mensaxe
     */
    private String interpretarIRCARCERE(Xogo xogo){
        String mensaxe="O avatar colocase na casilla CÁRCERE";
        xogo.getTurno().getXogador().meterNoCarcere();
        xogo.getTurno().setPosicion(xogo.getTaboeiro().getCasilla(10));
        return mensaxe;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer en parking
     * @return String co mensaxe
     */
    private String interpretarPARKING(Xogo xogo){
        String mensaxe="";
        mensaxe="O xogador "+ xogo.getTurno().getXogador().getNome() + " recibe "+xogo.getTaboeiro().getBote()+", do bote.";
        xogo.getTurno().getXogador().engadirDinheiro(xogo.getTaboeiro().getBote(), TipoTransaccion.BOTE_PREMIO);
        xogo.getTaboeiro().setBote(0);
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer nun imposto
     * @return String co mensaxe
     */
    private String interpretarIMPOSTO(Xogo xogo){
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        mensaxe="O xogador "+ xogador.getNome() +  " ten que pagar "+this.getImposto() + " por caer en "+this.getNome();
        if(xogador.quitarDinheiro(this.getImposto(), TipoTransaccion.IMPOSTO)){
            xogo.getTaboeiro().engadirBote(this.getImposto());
        }else{
            System.err.println("O xogador "+xogador.getNome()+" non ten suficiente dinheiro para pagar o imposto");
            xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
            return "";
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }


    /**
     * @return Devolve a información dunha casilla
     */
    @Override
    public String toString(){
        String xogadores="";
        String xogadoresCarcere="";
        String edificios="[";

        for(Edificio e:this.edificios){
            edificios+=e+", ";
        }
        edificios=(edificios.length()==1)?"[]":edificios.substring(0,edificios.length()-2)+"]";

        if(this.avatares!=null) {
            for (Avatar a : this.avatares){
                xogadores += "\n\t\t" + a.getXogador().getNome() + ",";
                if (a.getXogador().estaNoCarcere())
                    xogadoresCarcere += "\n\t\t[" + a.getXogador().getNome() + "," + a.getXogador().getTurnosNoCarcere() + "],";
            }
        }
        String texto;
        switch (this.tipoCasilla){
            case PARKING:
                texto="{\n\t" +
                        "Bote: "+this.taboeiro.getBote()+"\n\t" +
                        "Xogadores:["+xogadores+"\n\t]\n}";
                break;
            case CARCERE:
                texto="{\n\t" +
                        "Tipo: "+this.tipoCasilla+",\n\t" +
                        "Salir:"+ Valor.SAIR_CARCERE+",\n\t" +
                        "Xogadores:["+xogadoresCarcere+"\n\t]" +
                        "\n}";
                break;
            case IMPOSTO:
                texto="{\n\t" +
                            "Tipo: "+this.tipoCasilla+",\n\t" +
                            "Pagar:"+ getImposto() +
                        "\n}";
                break;
            case SERVIZO:
            case TRANSPORTE:
                texto="{"+
                            "\n\tNome: "+this.nome+((this.estaHipotecada)?"(Hipotecada)":"")+"\n\t" +
                            "Tipo: "+this.tipoCasilla+"\n\t" +
                            "Precio: "+this.getValor()+
                            ((!this.dono.getNome().equals("Banca"))?"\n\tPropietario: "+this.dono.getNome():"") +
                        "\n}";
                break;
            case COMUNIDADE:
            case SORTE:
            case SAIDA:
                texto="";
                break;
            default:
                texto="{"+
                        "\n\tNome: "+this.nome+((this.estaHipotecada)?"(Hipotecada)":"")+"\n\t" +
                        "Tipo: "+this.tipoCasilla+"\n\t" +
                        "Grupo: "+this.getGrupo().getNome()+"\n\t" +
                        "Valor: "+this.getValor()+"\n\t" +
                        "Alquiler: "+this.getAlquiler()+"\n\t"+
                        "Valor casa: "+this.valor*Valor.FACTOR_VALOR_CASA+"\n\t"+
                        "Valor hotel: "+this.valor*Valor.FACTOR_VALOR_HOTEL+"\n\t"+
                        "Valor piscina: "+this.valor*Valor.FACTOR_VALOR_PISCINA+"\n\t"+
                        "Valor pista deportes: "+this.valor*Valor.FACTOR_VALOR_PISTADEPORTES+"\n\t"+
                        "Alquiler 1 casa: "+this.alquiler*5+"\n\t"+
                        "Alquiler 2 casa: "+this.alquiler*15+"\n\t"+
                        "Alquiler 3 casa: "+this.alquiler*35+"\n\t"+
                        "Alquiler 4 casa: "+this.alquiler*50+"\n\t"+
                        "Alquiler hotel: "+this.alquiler*70+"\n\t"+
                        "Alquiler piscina: "+this.alquiler*25+"\n\t"+
                        "Alquiler pista de deportes: "+this.alquiler*25+"\n\t"+
                        "Total a pagar de alquiler actualmente: "+((this.getGrupo().tenTodoGrupo(this.dono) && !this.dono.getNome().equals("Banca"))?this.totalPagoAlquiler()*Valor.FACTOR_PAGO_ALQUILER:this.totalPagoAlquiler())+"\n\t"+
                        "Edificios: "+edificios+"\n\t"+
                        ((!this.dono.getNome().equals("Banca"))?"Propietario: "+this.dono.getNome():"") +
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
