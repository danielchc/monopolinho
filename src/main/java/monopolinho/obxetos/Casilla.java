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
            System.exit(1);
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
     * @param valor Imposto da casilla IMPOSTO ou Valor da Casilla SERVIZO ou TRANSPORTE
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
        Casilla next=this;
        Turno turno=xogo.getTurno();
         switch (tipoCasilla){
             case SOLAR:
                 return interpretarSOLAR(next,turno,xogo);
             case TRANSPORTE:
                 return interpretarTRANSPORTE(next,turno,xogo);
             case SERVIZO:
                 return interpretarSERVIZO(next,turno,xogo,valorDados);
             case CARCEL:
                 turno.setPosicion(next);
                 return "Só de visita...";
             case IRCARCEL:
                 return interpretarIRCARCEL(turno,xogo);
             case PARKING:
                 return interpretarPARKING(turno,next,xogo);
             case IMPOSTO:
                 return interpretarIMPOSTO(turno,next,xogo);
             case SORTE:
             case COMUNIDADE:
             case SALIDA:
                 turno.setPosicion(next);
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
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado((this.tipoCasilla==TipoCasilla.SOLAR)?this.getValor()+"$":this.nome)),
            ReprTab.colorear(this.colorCasilla, ReprTab.bordeTextoCentrado(avataresCasilla)),
            ReprTab.colorear(this.colorCasilla, ReprTab.borde())
        };
    }

    /**
     * Engade un edificio a unha casilla
     * @param e Edificio a engadir á casilla
     */
    public void engadirEdificio(Edificio e){
        if(e!=null){
            this.edificios.add(e);
        }
    }

    /**
     * Elimina un edificio dunha casilla
     * @param e Edificio a eliminar
     */
    public void eliminarEdificio(Edificio e){
        if(e!=null){
            this.edificios.remove(e);
        }
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
        if  (((tipo==TipoEdificio.CASA)&&(this.edificios.size() == this.getGrupo().getNumeroSolares() * 4)) || ((tipo!=TipoEdificio.CASA)&&(this.getNumeroEdificiosTipo(tipo)==this.getGrupo().getNumeroSolares()))) {
                System.err.println("Non podes construir máis edificios do tipo "+tipo+" en "+this.nome);
                return false;
        }
        return true;
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
     * Este metodo permite calcular o total a pagar de alquiler
     * @return Total alquiler a pagar
     */
    private float totalPagoAlquiler(){
        float aPagar=0;
        if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)>4)aPagar+=Valor.FACTOR_ALQUILER_EDIFICIOS[4]*this.alquiler;
        else aPagar+=Valor.FACTOR_ALQUILER_EDIFICIOS[this.getNumeroEdificiosTipo(TipoEdificio.CASA)]*this.alquiler;

        aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*this.alquiler*70;
        aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*this.alquiler*25;
        aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*this.alquiler*25;
        return aPagar;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer nun solar
     * @param next Casilla na que se cae
     * @param turno Xogador que cae
     * @return String co mensaxe
     */
    private String interpretarSOLAR(Casilla next,Turno turno,Xogo xogo){
        Xogador xogador=turno.getXogador();
        String mensaxe="";
        if(next.getEstaHipotecada()){
            mensaxe+="Caiche na casila "+next.getNome()+", pero está hipotecada, non pagas.";
            return mensaxe;
        }else{
            if((!next.getDono().equals(xogador))&&(!next.getDono().equals(xogo.getBanca()))){
                float aPagar;
                if(next.getGrupo().tenTodoGrupo(next.getDono())){
                    aPagar=next.totalPagoAlquiler()* Valor.FACTOR_PAGO_ALQUILER;
                }else{
                    aPagar=next.totalPagoAlquiler();
                }
                if(xogador.quitarDinheiro(aPagar, TipoGasto.ALQUILER)){
                    this.estadisticasCasilla.engadirAlquilerPagado(aPagar);
                    next.getDono().engadirDinheiro(aPagar,TipoGasto.ALQUILER);
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome();
                    return mensaxe;
                }else{
                    //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                    mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                    xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                    return mensaxe;
                }
            }
        }
        turno.setPosicion(next);
        return mensaxe;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer nun servizo
     * @param next Casilla na que se cae
     * @param turno Xogador que cae
     * @return String co mensaxe
     */
    private String interpretarSERVIZO(Casilla next,Turno turno,Xogo xogo,int valorDados){
        Xogador xogador=turno.getXogador();
        String mensaxe="";
        if((!next.getDono().equals(xogador)) && (!next.getDono().equals(xogo.getBanca()))){
            float aPagar=valorDados*next.getUsoServizo();
            if(xogador.numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 1){
                aPagar*=4.0f;
            }else if(xogador.numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 2){
                aPagar*=10.0f;
            }
            if(xogador.quitarDinheiro(aPagar,TipoGasto.OTROS)){
                next.getDono().engadirDinheiro(aPagar,TipoGasto.OTROS);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome() +" por usar "+next.getNome();
            }else{
                //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                return mensaxe;
            }
        }
        turno.setPosicion(next);
        return mensaxe;
    }


    /**
     * Este metodo interpreta as accions a realizar ao caer nun transporte
     * @param next Casilla na que se cae
     * @param turno Xogador que cae
     * @return String co mensaxe
     */
    private String interpretarTRANSPORTE(Casilla next,Turno turno,Xogo xogo){
        Xogador xogador=turno.getXogador();
        String mensaxe="";
        if((!next.getDono().equals(xogador)) && (!next.getDono().equals(xogo.getBanca()))){
            float aPagar=0;
            aPagar=next.getUsoServizo()*(next.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
            if(xogador.quitarDinheiro(aPagar,TipoGasto.OTROS)){
                next.getDono().engadirDinheiro(aPagar,TipoGasto.OTROS);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome() +" por usar "+next.getNome();
            }else{
                //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
                return mensaxe;
            }
        }
        turno.setPosicion(next);
        return mensaxe;
    }


    /**
     * Este metodo interpreta as accions a realizar ao caer en ir carcel
     * @param turno Xogador que cae
     * @return String co mensaxe
     */
    private String interpretarIRCARCEL(Turno turno,Xogo xogo){
        String mensaxe="O avatar colocase na casilla CARCEL(TEIXEIRO)";
        turno.getXogador().meterNoCarcere();
        turno.setPosicion(xogo.getTaboeiro().getCasilla(10));
        return mensaxe;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer en parking
     * @param turno Xogador que cae
     * @return String co mensaxe
     */
    private String interpretarPARKING(Turno turno,Casilla next,Xogo xogo){
        String mensaxe="";
        mensaxe="O xogador "+ turno.getXogador().getNome() + " recibe "+xogo.getTaboeiro().getBote()+", do bote.";
        turno.getXogador().engadirDinheiro(xogo.getTaboeiro().getBote(),TipoGasto.BOTE_PREMIO);
        xogo.getTaboeiro().setBote(0);
        turno.setPosicion(next);
        return mensaxe;
    }

    /**
     * Este metodo interpreta as accions a realizar ao caer nun imposto
     * @param turno Xogador que cae
     * @return String co mensaxe
     */
    private String interpretarIMPOSTO(Turno turno,Casilla next,Xogo xogo){
        Xogador xogador=turno.getXogador();
        String mensaxe="";
        mensaxe="O xogador "+ xogador.getNome() +  " ten que pagar "+next.getImposto() + " por caer en "+next.getNome();
        if(xogador.quitarDinheiro(next.getImposto(),TipoGasto.IMPOSTO)){
            xogo.getTaboeiro().engadirBote(next.getImposto());
        }else{
            System.err.println("O xogador "+xogador.getNome()+" non ten suficiente dinheiro para pagar o imposto");
            //E QUE PASA SE NON TEN CARTOS????????????????????
        }
        turno.setPosicion(next);
        return mensaxe;
    }


    /**
     * @return Devolve a información dunha casilla
     */
    @Override
    public String toString(){
        String xogadores="";
        String xogadoresCarcel="";
        String edificios="[";

        for(Edificio e:this.edificios){
            edificios+=e+", ";
        }
        edificios=(edificios.length()==1)?"[]":edificios.substring(0,edificios.length()-2)+"]";

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
                texto="";
                break;
            case SORTE:
                texto="";
                break;
            case SALIDA:
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
