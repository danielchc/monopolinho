package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.Edificio;
import monopolinho.obxetos.Grupo;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiroException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

import java.util.ArrayList;

public class Solar extends Propiedade {
    private Grupo grupo;
    private ArrayList<Edificio> edificios;
    private float alquiler;
    /**
     * Crea unha nova instancia de SOLAR
     * @param nome Nome do SOLAR
     * @param grupo Grupo do solar
     */
    public Solar(String nome, Grupo grupo){
        super(nome);
        this.edificios=new ArrayList<>();
        this.grupo=grupo;
        this.grupo.engadirSolar(this);
        super.setValor((float) Math.ceil((this.grupo.getValor()/this.grupo.getNumeroSolares())));
        this.alquiler=this.getValor()* Valor.FACTOR_ALQUILER;
        this.setColorCasilla(this.getGrupo().getColor());
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoSinDinheiroException {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if(this.getEstaHipotecada()){
            mensaxe+="Caiche na casila "+this.getNome()+", pero está hipotecada, non pagas.";
            return mensaxe;
        }else{
            if((!this.pertenceXogador(xogador))&&(!this.pertenceXogador(xogo.getBanca()))){
                float aPagar=this.totalPagoAlquiler();

                aPagar*=(this.getGrupo().tenTodoGrupo(this.getDono()))?Valor.FACTOR_PAGO_ALQUILER:1f;

                if(xogador.quitarDinheiro(aPagar, TipoTransaccion.ALQUILER)){
                    this.getEstadisticas().engadirAlquilerPagado(aPagar);
                    this.getDono().engadirDinheiro(aPagar, TipoTransaccion.ALQUILER);
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome();
                    return mensaxe;
                }else{
                    throw new MonopolinhoSinDinheiroException("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.",xogador);
                }
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    public void edificar(TipoEdificio tipo){
        this.engadirEdificio(new Edificio(tipo,this));
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
                "\n\tPropiedade: " + this.getNome() +
                ",\n\tCasas: " + edificiosTexto[TipoEdificio.CASA.ordinal()]+
                ",\n\tHoteles: " + edificiosTexto[TipoEdificio.HOTEL.ordinal()]+
                ",\n\tPiscinas: " + edificiosTexto[TipoEdificio.PISCINA.ordinal()]+
                ",\n\tPistas de deportes: " + edificiosTexto[TipoEdificio.PISTA_DEPORTES.ordinal()]+
                ",\n\tAlquiler: " + getAlquiler()+
                "\n}"+
                "\n" + queSePodeConstruir();
        return text;
    }

    public String[] getRepresentacion(){
        String avataresCasilla="";
        if(!this.getAvatares().isEmpty())avataresCasilla="&";
        for(Avatar a:this.getAvatares())avataresCasilla+=a.getId()+" ";
        return new String[]{
                ReprTab.colorear(this.getColorCasilla(), ReprTab.borde()),
                ReprTab.colorear(this.getColorCasilla(), ReprTab.bordeTextoCentrado(this.getNome())),
                ReprTab.colorear(this.getColorCasilla(), ReprTab.bordeTextoCentrado(ReprTab.formatearNumeros(this.getValor()))),
                ReprTab.colorear(this.getColorCasilla(), ReprTab.bordeTextoCentrado(avataresCasilla)),
                ReprTab.colorear(this.getColorCasilla(), ReprTab.borde())
        };
    }


    /**
     * Este metodo permite saber o precio según o tipo de edificio
     * @param tipo Input do usuario
     * @return Precio do edificio.
     */
    public float getPrecioEdificio(TipoEdificio tipo){
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
    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.SOLAR;
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
                aPagar+=Valor.FACTOR_ALQUILER_EDIFICIOS[4]*getAlquiler();
            else
                aPagar+=Valor.FACTOR_ALQUILER_EDIFICIOS[this.getNumeroEdificiosTipo(TipoEdificio.CASA)]*getAlquiler();

            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*getAlquiler()*70;
            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*getAlquiler()*25;
            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*getAlquiler()*25;
        }else{
            aPagar=getAlquiler(); //REVISAR ESTO
        }
        return aPagar;
    }

    @Override
    public float getAlquiler() {
        return alquiler;
    }

    @Override
    public String toString() {
        String listaEdificios="[";

        for(Edificio e:this.edificios){
            listaEdificios+=e+", ";
        }
        listaEdificios=(listaEdificios.length()==1)?"[]":listaEdificios.substring(0,listaEdificios.length()-2)+"]";
        
        return super.toString()+
            "\n\tGrupo: "+this.getGrupo().getNome() +
            "\n\tValor: "+this.getValor() +
            "\n\tAlquiler: "+this.getAlquiler()+
            "\n\tValor casa: "+this.getValor()*Valor.FACTOR_VALOR_CASA+
            "\n\tValor hotel: "+this.getValor()*Valor.FACTOR_VALOR_HOTEL+
            "\n\tValor piscina: "+this.getValor()*Valor.FACTOR_VALOR_PISCINA+
            "\n\tValor pista deportes: "+this.getValor()*Valor.FACTOR_VALOR_PISTADEPORTES+
            "\n\tAlquiler 1 casa: "+this.getAlquiler()*5+
            "\n\tAlquiler 2 casa: "+this.getAlquiler()*15+
            "\n\tAlquiler 3 casa: "+this.getAlquiler()*35+
            "\n\tAlquiler 4 casa: "+this.getAlquiler()*50+
            "\n\tAlquiler hotel: "+this.getAlquiler()*70+
            "\n\tAlquiler piscina: "+this.getAlquiler()*25+
            "\n\tAlquiler pista de deportes: "+this.getAlquiler()*25+
            "\n\tTotal a pagar de alquiler actualmente: "+((this.getGrupo().tenTodoGrupo(this.getDono()) && !this.getDono().getNome().equals("Banca"))?this.totalPagoAlquiler()*Valor.FACTOR_PAGO_ALQUILER:this.totalPagoAlquiler())+
            "\n\tEdificios: "+listaEdificios+
            "\n}";
    }
}
