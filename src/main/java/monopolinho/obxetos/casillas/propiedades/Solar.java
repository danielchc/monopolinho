package monopolinho.obxetos.casillas.propiedades;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Grupo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionAlquiler;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.edificios.*;
import monopolinho.obxetos.excepcions.MonopolinhoNonSePodeConstruir;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    public String interpretarCasilla(Xogo xogo, int valorDados) throws MonopolinhoSinDinheiro {
        Xogador xogador=xogo.getTurno().getXogador();
        String mensaxe="";
        if(this.getEstaHipotecada()){
            mensaxe+="Caiche na casila "+this.getNome()+", pero está hipotecada, non pagas.";
        }else{
            if((!this.pertenceXogador(xogador))&&(!this.pertenceXogador(xogo.getBanca()))){
                if(xogador.restarVecesNonAlquiler(this)){
                    mensaxe="Un trato fixo que non tiveras que pagar alquiler en "+super.getNome();
                }else {
                    float aPagar=this.getAlquiler();
                    if(xogador.quitarDinheiro(aPagar, TipoTransaccion.ALQUILER)){
                        this.getEstadisticas().engadirAlquilerPagado(aPagar);
                        this.getDono().engadirDinheiro(aPagar, TipoTransaccion.ALQUILER);
                        xogo.getTurno().engadirAccion(new AccionAlquiler(this,aPagar));
                        mensaxe+="Tes que pagarlle "+aPagar+" a "+this.getDono().getNome();
                    }else{
                        throw new MonopolinhoSinDinheiro("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.",xogador);
                    }
                }
            }
        }
        xogo.getTurno().setPosicion(this);
        return mensaxe;
    }

    public void edificar(TipoEdificio tipo, Turno turno) throws MonopolinhoNonSePodeConstruir {
        switch (tipo){
            case CASA:
                if(!this.getGrupo().tenTodoGrupo(turno.getXogador()) && this.numeroVecesCaidas(turno.getXogador().getAvatar())<2){
                    throw new MonopolinhoNonSePodeConstruir("Para edificar unha casa debes ter todo o grupo ou caer 2 veces en "+this.getNome());
                }
                break;
            case HOTEL:
                if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)<4){
                    throw new MonopolinhoNonSePodeConstruir("Necesitas 4 casas en "+this.getNome()+" para edificar un hotel");
                }
                this.eliminarNumeroEdificios(TipoEdificio.CASA,4);
                break;
            case PISCINA:
                if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)<2 || this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<1){
                    throw new MonopolinhoNonSePodeConstruir("Necesitas polo menos 2 casas e 1 hotel en "+this.getNome()+" para edificar unha piscina.");
                }
                break;
            case PISTA_DEPORTES:
                if(this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2){
                    throw new MonopolinhoNonSePodeConstruir("Necesitas polo menos 2 hoteles en "+this.getNome()+" para edificar unha pista de deportes.");
                }
                break;
        }
        this.engadirEdificio(tipo);
    }

    public void eliminarNumeroEdificios(TipoEdificio tipo,int numero){
        int edifsEliminados=0;
        ArrayList<Edificio> aBorrar=new ArrayList<>();
        for(Edificio e:this.getEdificios()){
            if((e.getTipoEdificio() == tipo) && (edifsEliminados<numero)){
                aBorrar.add(e);
                edifsEliminados++;
            }
        }
        for(Edificio e:aBorrar){
            this.eliminarEdificio(e);
        }
        this.renombrarEdificios();
    }

    /**
     * Engade un edificio a unha casilla
     * @param e Edificio a engadir á casilla
     */
    public void engadirEdificio(TipoEdificio e){
        switch (e){
            case PISTA_DEPORTES:
                this.edificios.add(new PistaDeportes(this));
                break;
            case HOTEL:
                this.edificios.add(new Hotel(this));
                break;
            case PISCINA:
                this.edificios.add(new Piscina(this));
                break;
            case CASA:
                this.edificios.add(new Casa(this));
                break;
        }
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
    private void renombrarEdificios(){
        int contador=1;
        for(Edificio e:this.edificios){
            if(e instanceof Casa){
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
        String[] edificiosTexto=new String[4];
        for(TipoEdificio k:TipoEdificio.values()){
            edificiosTexto[k.ordinal()]="["+this.edificios.stream().filter(x->(x.getTipoEdificio()==k)).map(Edificio::getId).collect(Collectors.joining(", "))+"]";
        }

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

    private float getAlquilerEdificioTipo(TipoEdificio tipo) {
        return this.alquiler*Valor.FACTOR_ALQUILER_REDIFICIOS[tipo.ordinal()];
    }

    /**
     * Este metodo permite calcular o total a pagar de alquiler
     * @return Total alquiler a pagar
     */
    @Override
    public float getAlquiler() {
        float aPagar=0;
        if(this.getNumeroEdificios()!=0){
            if(this.getNumeroEdificiosTipo(TipoEdificio.CASA)>4)
                aPagar+=Valor.FACTOR_ALQUILER_CASA[4]*this.alquiler;
            else
                aPagar+=Valor.FACTOR_ALQUILER_CASA[this.getNumeroEdificiosTipo(TipoEdificio.CASA)]*this.alquiler;

            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*getAlquilerEdificioTipo(TipoEdificio.HOTEL);
            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*getAlquilerEdificioTipo(TipoEdificio.PISCINA);
            aPagar+=this.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*getAlquilerEdificioTipo(TipoEdificio.PISTA_DEPORTES);
        }else{
            aPagar=this.alquiler; //REVISAR ESTO
        }
        aPagar*=(this.getGrupo().tenTodoGrupo(this.getDono()))?Valor.FACTOR_PAGO_ALQUILER:1f;
        return aPagar;
    }

    @Override
    public String toString() {

        String listaEdificios="["+this.edificios.stream().map(Edificio::getId).collect(Collectors.joining(", "))+"]";
        return super.toString()+
            "\n\tGrupo: "+this.getGrupo().getNome() +
            "\n\tValor: "+this.getValor() +
            "\n\tAlquiler: "+this.getAlquiler()+
            "\n\tValor casa: "+this.getValor()*Valor.FACTOR_VALOR_CASA+
            "\n\tValor hotel: "+this.getValor()*Valor.FACTOR_VALOR_HOTEL+
            "\n\tValor piscina: "+this.getValor()*Valor.FACTOR_VALOR_PISCINA+
            "\n\tValor pista deportes: "+this.getValor()*Valor.FACTOR_VALOR_PISTADEPORTES+
            "\n\tAlquiler 1 casa: "+this.alquiler*5+
            "\n\tAlquiler 2 casa: "+this.alquiler*15+
            "\n\tAlquiler 3 casa: "+this.alquiler*35+
            "\n\tAlquiler 4 casa: "+this.alquiler*50+
            "\n\tAlquiler hotel: "+getAlquilerEdificioTipo(TipoEdificio.HOTEL)+
            "\n\tAlquiler piscina: "+getAlquilerEdificioTipo(TipoEdificio.PISCINA)+
            "\n\tAlquiler pista de deportes: "+getAlquilerEdificioTipo(TipoEdificio.PISTA_DEPORTES)+
            "\n\tTotal a pagar de alquiler actualmente: "+(!this.getDono().getNome().equals("Banca")?this.getAlquiler():"")+
            "\n\tEdificios: "+listaEdificios+
            "\n}";
    }
}
