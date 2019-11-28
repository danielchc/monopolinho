package monopolinho.interfaz;

import com.sun.org.apache.bcel.internal.generic.CodeExceptionGen;
import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasXogo;
import monopolinho.obxetos.*;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.edificios.Edificio;
import monopolinho.obxetos.excepcions.MonopolinhoCasillaInexistente;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.obxetos.excepcions.MonopolinhoNonSePodeConstruir;
import monopolinho.tipos.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Xogo implements Comandos {

    private ArrayList<Xogador> xogadores;
    private Taboeiro taboeiro;
    private Turno turno;
    private Dados dados;
    private Xogador banca;
    private boolean partidaComezada;
    private EstadisticasXogo estadisticasXogo;
    private int numTratos;
    public static final Consola consola = new ConsolaNormal();

    /**
     * Constructor da clase Xogo.
     * Determina o turno.
     * Instancia o taboeiro.
     * Instancia a banca.
     * Instancia os dados.
     * Engade as todas as casillas a banca.
     */
    public Xogo(){
        taboeiro=new Taboeiro();
        banca=new Xogador();
        dados=new Dados();
        engadirCasillasBanca();
        partidaComezada=false;
        xogadores=new ArrayList<Xogador>();
        estadisticasXogo=new EstadisticasXogo(this);
        this.numTratos=0;
    }

    /**
     * Carga o xogo con uns xogadores creados fora
     * @param xogadores
     */
    public Xogo(ArrayList<Xogador> xogadores){
        this();
        this.xogadores=xogadores;
    }

    /**
     * Funcións públicas.
     */

    /**
     * Este metdo imprime a info dunha casilla.
     * @param nome Nome da casilla
     */
    @Override
    public void describirCasilla(String nome) throws MonopolinhoException {
        Casilla c=this.taboeiro.buscarCasilla(nome);
        if(c!=null)consola.imprimir(c.toString());
        else throw new MonopolinhoCasillaInexistente(nome);

    }

    /**
     * Este metodo imprime a info dun xogador.
     * @param nome Nome do xogador
     */
    @Override
    public void describirXogador(String nome) throws MonopolinhoException {
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                consola.imprimir(x.describir());
                return;
            }
        }
        throw new MonopolinhoGeneralException("O xogador " + nome + "non existe");
    }

    /**
     * Este metodo imprime a info dun avatar.
     * @param avatarId Argumentos do usuario
     */
    @Override
    public void describirAvatar(String avatarId) throws MonopolinhoException{
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getId().equals(avatarId)){
                consola.imprimir(x.getAvatar());
                return;
            }
        }
        throw new MonopolinhoGeneralException("O avatar " + avatarId + "non existe");
    }

    /**
     * Este metodo lista todos os edificios
     */
    @Override
    public void listarEdificios(){
        for(Grupo g:taboeiro.getGrupos()){
            for(Solar s:g.getSolares()){
                for(Edificio e:s.getEdificios()){
                    consola.imprimir(e.describirEdificio());
                }
            }
        }
    }

    /**
     * Este metodo lista todos os edificios dun grupo
     */
    @Override
    public void listarEdificiosGrupo(String cmds) throws MonopolinhoGeneralException {
        Grupo grupo=taboeiro.buscarGrupo(cmds);
        if(grupo==null){
            throw new MonopolinhoGeneralException("O grupo "+cmds+" non existe");
        }
        if(grupo.getNumeroEdificios()==0){
            throw new MonopolinhoGeneralException("O grupo "+cmds+" non ten edificios");
        }
        for(Solar c:grupo.getSolares()){
            consola.imprimir(c.describirEdificios());
        }
    }


    /**
     * Este metodo imprime todos os avatares.
     */
    @Override
    public void listarAvatares(){
        for(Xogador x:this.xogadores)
            consola.imprimir(x.getAvatar());
    }

    /**
     * Este metodo imprime todos os xogadores.
     */
    @Override
    public void listarXogadores(){
        for(Xogador x:this.xogadores)
            consola.imprimir(x.describir());
    }

    /**
     * Este metodo imprime todas as casillas en venta.
     */
    @Override
    public void listarCasillaEnVenta(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas())
            for(Casilla c:zona)
                if(c instanceof Propiedade)
                    consola.imprimir(c);
    }

    /**
     * Este metodo acaba o turno e pasallo ao seguinte.
     */
    @Override
    public void pasarTurno() throws MonopolinhoGeneralException {
        Xogador actual=turno.getXogador();
        int novoTurno;
        if(turno.podeLanzar() && turno.getXogador().estadoXogador()!=EstadoXogador.BANCARROTA && turno.getXogador().getTurnosInvalidado()==0){
            throw new MonopolinhoGeneralException("Tes que tirar antes de pasar turno.");
        }
        if(turno.getXogador().estadoXogador() == EstadoXogador.TEN_DEBEDAS){
            throw new MonopolinhoGeneralException("Non podes pasar de turno ata que saldes as débedas ou te declares en bancarrota.");
        }

        do{
            novoTurno=(this.xogadores.indexOf(turno.getXogador())+1)%this.xogadores.size();
            turno=new Turno(this.xogadores.get(novoTurno));
        }while(turno.getXogador().enBancarrota());

        if(comprobarFinPartida()){
            consola.imprimir("\nFin da partida!!");
            consola.imprimir("O xogador "+turno.getXogador().getNome()+ " gañou a partida");
            System.exit(0);
        }
        if(actual.estaNoCarcere())
            actual.restarTurnoCarcere();

        if(actual.getTurnosNoCarcere()==0){
            if(turno.getXogador().quitarDinheiro(Valor.SAIR_CARCERE, TipoTransaccion.OTROS)){
                consola.imprimir("O xogador"+turno.getXogador().getNome()+" leva 3 turnos no carcere  paga "+ Valor.SAIR_CARCERE + " e sae da cárcere. E pode lanzar os dados.");
                turno.getXogador().sairDoCarcere();
                taboeiro.engadirBote(Valor.SAIR_CARCERE);
                return;
            }else{
                turno.getXogador().setEstadoXogador(EstadoXogador.BANCARROTA);
                throw new MonopolinhoGeneralException("O xogador leva 3 turnos no cárcere e non ten cartos para saír, o xogador debe declararse en bancarrota");
            }
        }
        actual.restarTurnosInvalidado();
        consola.imprimir("Tiña o turno "+actual.getNome()+", agora teno "+turno.getXogador().getNome());
        System.out.println(turno.getXogador().listarTratos());
    }

    /**
     * Este metodo saca a un xogador da carcere.
     */
    @Override
    public void sairCarcere() throws MonopolinhoGeneralException {
        if (!turno.getXogador().estaNoCarcere()){
            throw new MonopolinhoGeneralException("O xogador non está no cárcere");
        }
        if(turno.getXogador().quitarDinheiro(Valor.SAIR_CARCERE, TipoTransaccion.OTROS)){
            consola.imprimir(turno.getXogador().getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Podes lanzar os dados.");
            turno.getXogador().sairDoCarcere();
            taboeiro.engadirBote(Valor.SAIR_CARCERE);
        }else{
            throw new MonopolinhoGeneralException("Non tes o suficiente diñeiro para saír do cárcere");
        }
    }

    /**
     * Este metodo engade unha casilla as propiedades do xogador.
     * @param nome Argumentos da función
     */
    @Override
    public void comprarCasilla(String nome) throws MonopolinhoException {
        if(comprobarCarcere())return;
        Xogador xogador=turno.getXogador();
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade comprar;
        if(target==null) throw new MonopolinhoCasillaInexistente(nome);

        if(!(target instanceof Propiedade)){
            throw new MonopolinhoGeneralException("Este tipo de casilla non se pode comprar esta casilla");
        }
        comprar=(Propiedade)target;
        if(turno.getVecesTiradas()==0){
            throw new MonopolinhoGeneralException("Non podes comprar unha casilla se non lanzaches os dados");
        }
        if(this.turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO){
            if(!this.turno.getHistorial().contains(comprar)){
                throw new MonopolinhoGeneralException("Non pasaches por esta casilla neste turno");
            }
        }else{
            if(!this.turno.getPosicion().equals(comprar)){
                throw new MonopolinhoGeneralException("Non estás nesta casilla, non a podes comprar");
            }
        }
        if (!comprar.pertenceXogador(banca)){
            throw new MonopolinhoGeneralException("Esta casilla pertence a " + comprar.getDono().getNome()+". Non a podes comprar");
        }
        if(!xogador.quitarDinheiro(comprar.getValor(), TipoTransaccion.COMPRA)){
            throw new MonopolinhoGeneralException("Non tes suficiente diñeiro");
        }
        if(xogador.getAvatar().getTipo()==TipoMovemento.COCHE && xogador.getAvatar().getModoXogo()==ModoXogo.AVANZADO && turno.getCompradasTurno()>=1){
            throw new MonopolinhoGeneralException("Non podes facer máis compras neste turno");
        }

        comprar.comprar(this.turno.getXogador());
        turno.engadirAccion(new Accion(TipoAccion.COMPRAR,comprar));
        consola.imprimir("O usuario "+xogador.getNome() +" comprou "+comprar.getNome() +" por "+comprar.getValor());
    }


    /**
     * Este metodo imprime o tableiro.
     */
    @Override
    public void mostrarTaboeiro(){
        consola.imprimir(taboeiro);
    }

    /**
     * Mostra as estadisticas para un xogador
     * @param nome
     */
    @Override
    public void mostrarEstadisticasXogador(String nome) throws MonopolinhoGeneralException {
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                consola.imprimir(x.getEstadisticas());
                return;
            }
        }
        throw new MonopolinhoGeneralException("Non se atopou o xogador "+nome);
    }

    /**
     * Mostra as estadisticas do Xogo
     */
    @Override
    public void mostrarEstadisticasXogo(){
        consola.imprimir(estadisticasXogo);
    }

    /**
     * Metodo que hipoteca unha casilla.
     * @param nome Nome casilla
     */
    @Override
    public void hipotecarCasilla(String nome) throws MonopolinhoException {
        if(comprobarCarcere())return;
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade c=null;
        if(target==null)throw new MonopolinhoCasillaInexistente(nome);

        if(!(target instanceof Propiedade)){
            throw new MonopolinhoGeneralException("Non se pode hipotecar este tipo de casilla");
        }
        c=(Propiedade)target;
        if(!c.pertenceXogador(turno.getXogador())){
            throw new MonopolinhoGeneralException("Non eres dono de esta casilla");
        }
        if(c.getEstaHipotecada()){
            throw new MonopolinhoGeneralException("Esta casilla xa está hipotecada");
        }
        if((c instanceof Solar) && (((Solar)c).getEdificios().size()!=0)){
            throw new MonopolinhoGeneralException(c.getNome()+" conten edificios, tes que vendelos antes de hipotecar.");
        }

        c.setEstaHipotecada(true);
        c.getDono().engadirDinheiro(c.getHipoteca(), TipoTransaccion.OTROS);
        turno.engadirAccion(new Accion(TipoAccion.HIPOTECAR,c));
        consola.imprimir("Acabas de hipotecar a casilla "+c.getNome()+" e recibes "+c.getHipoteca());

    }


    /**
     * Metodo que deshipoteca unha casilla.
     * @param nome Nome casilla
     */
    @Override
    public void deshipotecarCasilla(String nome) throws MonopolinhoException {
        if(comprobarCarcere())return;
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade c=null;
        if(target==null)throw new MonopolinhoCasillaInexistente(nome);

        if(!(target instanceof Propiedade)){
            throw new MonopolinhoGeneralException("Non se pode deshipotecar este tipo de casilla");
        }
        c=(Propiedade)target;
        if(!c.pertenceXogador(turno.getXogador())){
            throw new MonopolinhoGeneralException("Non eres dono de esta casilla");
        }
        if(!c.getEstaHipotecada()){
            throw new MonopolinhoGeneralException("Esta casilla non está hipotecada");
        }

        if(!c.getDono().quitarDinheiro(c.getHipoteca()*1.1f, TipoTransaccion.OTROS)){
            throw new MonopolinhoGeneralException("Non tes o suficiente diñeiro para deshipotecar a propiedade");
        }
        c.setEstaHipotecada(false);
        turno.engadirAccion(new Accion(TipoAccion.DESHIPOTECAR,c));
        consola.imprimir("Acabas de deshipotecar a casilla "+c.getNome()+". Pagas "+c.getHipoteca()*1.1f);

    }

    /**
     * Metodo bancarrota.
     */
    @Override
    public void declararBancarrota() throws MonopolinhoGeneralException {
        this.turno.getXogador().setEstadoXogador(EstadoXogador.BANCARROTA);
        for(Propiedade c:this.turno.getXogador().getPropiedades()){
            c.setEstaHipotecada(false);
            c.setDono(this.banca);
            //PA O XOGADOR QUE TES A DEBEDA, normalmente vas ter a débeda co xogador da casilla na que estás
            //c.setDono(this.turno.getPosicion().getDono());
        }
        consola.imprimir("\nO xogador "+this.turno.getXogador().getNome()+" declarouse en bancarrota.");
        pasarTurno();
    }

    /**
     * Este metodo permite construir
     * @param tipo Tipo de edificio
     */
    @Override
    public void edificar(TipoEdificio tipo) throws MonopolinhoNonSePodeConstruir {
        if(comprobarCarcere())return;
        Solar actual;
        Casilla target=turno.getPosicion();
        if(!(target instanceof Solar))
            throw new MonopolinhoNonSePodeConstruir("Non se pode construir nunha casilla que non sexa un SOLAR");
        actual=(Solar)target;
        if(tipo==null)
            throw new MonopolinhoNonSePodeConstruir("Tipo de edificio incorrecto");
        if(actual.getEstaHipotecada())
            throw new MonopolinhoNonSePodeConstruir("Non podes construir nunha casilla hipotecada");
        if(!actual.pertenceXogador(turno.getXogador()))
            throw new MonopolinhoNonSePodeConstruir("Esta casilla non é túa, non a podes edificar.");
        if(!actual.podeseEdificarMais(tipo))
            throw new MonopolinhoNonSePodeConstruir("Non podes construir máis edificios do tipo "+tipo+" en "+actual.getNome());

        switch (tipo){
            case CASA:
                if(!actual.getGrupo().tenTodoGrupo(turno.getXogador()) && actual.numeroVecesCaidas(turno.getXogador().getAvatar())<2){
                    throw new MonopolinhoNonSePodeConstruir("Para edificar unha casa debes ter todo o grupo ou caer 2 veces en "+actual.getNome());
                }
                break;
            case HOTEL:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<4){
                    throw new MonopolinhoNonSePodeConstruir("Necesitas 4 casas en "+actual.getNome()+" para edificar un hotel");
                }
                int casasEliminadas=0;
                ArrayList<Edificio> aBorrar=new ArrayList<>();
                for(Edificio e:actual.getEdificios()){
                    if((e.getTipoEdificio() == TipoEdificio.CASA) && (casasEliminadas<4)){
                        aBorrar.add(e);
                        casasEliminadas++;
                    }
                }
                for(Edificio e:aBorrar)actual.eliminarEdificio(e);
                actual.renombrarEdificios();
                break;
            case PISCINA:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<2 || actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<1){
                    throw new MonopolinhoNonSePodeConstruir("Necesitas polo menos 2 casas e 1 hotel en "+actual.getNome()+" para edificar unha piscina.");
                }
                break;
            case PISTA_DEPORTES:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2){
                    throw new MonopolinhoNonSePodeConstruir("Necesitas polo menos 2 hoteles en "+actual.getNome()+" para edificar unha pista de deportes.");
                }
                break;
        }
        if(!turno.getXogador().quitarDinheiro(actual.getPrecioEdificio(tipo), TipoTransaccion.EDIFICAR)){
            Xogo.consola.imprimirErro("Non tes suficiente diñeiro para edificar");
            return ;
        }
        actual.edificar(tipo);
        turno.engadirAccion(new Accion(TipoAccion.EDIFICAR,actual));
        consola.imprimir("O usuario "+turno.getXogador().getNome() +" edificou en "+actual.getNome()+": "+tipo+". A súa fortuna redúcese en "+actual.getPrecioEdificio(tipo));
    }


    /**
     * Vende un edificio
     * @param tipo tipo de edificio
     * @param casilla Casilla
     * @param numero Número de edificios a vender
     */
    @Override
    public void venderEdificio(TipoEdificio tipo, String casilla, int numero) throws MonopolinhoCasillaInexistente, MonopolinhoGeneralException {
        if(comprobarCarcere())return;
        Casilla target=taboeiro.buscarCasilla(casilla);
        Solar c;
        if(target==null)throw new MonopolinhoCasillaInexistente(casilla);

        if(!(target instanceof Solar)){
            throw new MonopolinhoGeneralException("Non se pode vender edificios dunha casilla que non sexa un SOLAR");
        }
        c=(Solar)target;
        if(tipo==null){
            throw new MonopolinhoGeneralException("Tipo de edificio incorrecto.");
        }

        if(!c.pertenceXogador(turno.getXogador())){
            throw new MonopolinhoGeneralException("Non podes vender edificios en "+c.getNome()+" porque non é túa.");
        }
        if(c.getEstaHipotecada()){
            throw new MonopolinhoGeneralException("Non podes vender edificios en "+c.getNome()+" porque está hipotecada");
        }

        int totalEdifs=c.getNumeroEdificiosTipo(tipo);
        float valor = numero*c.getPrecioEdificio(tipo)/2.0f;
        if(totalEdifs<numero){
            throw new MonopolinhoGeneralException("Non podes vender edificios de tipo "+tipo+". Solamente tes "+totalEdifs+" de tipo "+tipo+" por valor de "+valor);
        }

        int edifsEliminados=0;
        ArrayList<Edificio> aBorrar=new ArrayList<>();
        for(Edificio e:c.getEdificios()){
            if((e.getTipoEdificio() == tipo) && (edifsEliminados<numero)){
                aBorrar.add(e);
                edifsEliminados++;
            }
        }
        for(Edificio e:aBorrar){
            c.eliminarEdificio(e);
        }

        turno.getXogador().engadirDinheiro(valor, TipoTransaccion.OTROS);
        consola.imprimir("Vendiches "+numero+" edificios de tipo "+tipo+" e recibes "+valor);
    }

    /**
     * Mostra a información do turno actual
     */
    @Override
    public void mostrarTurno() throws MonopolinhoGeneralException {
        if (turno!=null)consola.imprimir(turno.getXogador());
        else throw new MonopolinhoGeneralException("Non hai xogadores");
    }

    /**
     * Este metodo permite crear un xogador
     * @param nombre nombre nome do xogador
     * @param tipoMov tipoMov tipo de movemento do avatar do xogador
     * @return true si se creou o xogador ou false se o xogador xa existe
     */
    @Override
    public boolean crearXogador(String nombre, TipoMovemento tipoMov){
        if(this.partidaComezada){
            try{
                throw new MonopolinhoGeneralException("Non se pode crear un xogador durante a partida");
            }catch (MonopolinhoGeneralException e){
                e.imprimirErro();
            }
            return false;
        }
        Xogador xogador=new Xogador(nombre, tipoMov);
        if(this.xogadores.contains(xogador)){
            return false; //Comproba se existe o usuario o método equal compara nomes!
        }
        while(comprobarAvatarRepetido(xogador.getAvatar()))
            xogador.getAvatar().xerarId();

        this.xogadores.add(xogador);
        if(this.xogadores.indexOf(xogador)==0)
            this.turno=new Turno(this.xogadores.get(0));
        xogador.setPosicion(this.taboeiro.getCasilla(0));
        consola.imprimir(xogador);
        return true;
    }

    /**
     * Este metodo lanza os dados.
     * - Se saen dobles permite volver a tirar.
     * - Se volven sair dobles permite volver tirar.
     * - Se saen triples manda ao xogador para a carcere.
     */
    @Override
    public void lanzarDados() throws MonopolinhoException {
        if(turno.getXogador().estadoXogador()==EstadoXogador.TEN_DEBEDAS){
            throw new MonopolinhoGeneralException("O xogador ten debedas, ten que declarase en bancarrota ou hipotecar propiedades");
        }
        if(!turno.podeLanzar()){
            throw new MonopolinhoGeneralException("O xogador xa lanzou os dados. Non se poden lanzar de novo");
        }
        if(turno.getXogador().getTurnosInvalidado()>0){
            throw new MonopolinhoGeneralException("O xogador ten que esperar "+turno.getXogador().getTurnosInvalidado()+" turno para volver a lanzar");
        }
        dados.lanzarDados();
        turno.aumentarVecesTiradas();   //1 vez tirada

        String mensaxe="\nSaiu o "+ ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado1()))+
                        " e o "+
                        ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado2()));

        if(turno.getXogador().estaNoCarcere()){
            if(!dados.sonDobles()){
                consola.imprimir("Estás no CÁRCERE");
                turno.setPodeLanzar(false);
                throw new MonopolinhoGeneralException(mensaxe+". Tes que sacar dobles ou pagar para saír do cárcere.");
            }else{
                turno.getXogador().sairDoCarcere();
                turno.setVecesTiradas(0);
                consola.imprimir(mensaxe+". Sacaches dados dobles, saíches do cárcere. Tes que volver a lanzar.");
                return;
            }
        }else{
            if (dados.sonDobles() && turno.getVecesTiradas()<3){
                mensaxe+="\nAo sair dobles, o xogador "+turno.getXogador().getNome()+" volve tirar.";
            }else if(dados.sonDobles() && turno.getVecesTiradas()==3){
                turno.getXogador().meterNoCarcere();
                turno.engadirAccion(new Accion(TipoAccion.IR_CARCEL));
                turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCERE
                turno.setPodeLanzar(false);
                throw new MonopolinhoGeneralException(mensaxe+" Saion triples e vas para o cárcere.");
            }else{
                turno.setPodeLanzar(false);
            }
        }
        consola.imprimir(mensaxe);
        turno.engadirAccion(new Accion(TipoAccion.LANZAR_DADOS,turno.getPosicion()));
        if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.NORMAL){
            moverModoNormal(dados.valorLanzar());
        }else{
            moverModoAvanzado(dados.valorLanzar());
        }
    }

    @Override
    public void cambiarModoXogo(){
        if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.NORMAL)
            turno.getXogador().getAvatar().setModoXogo(ModoXogo.AVANZADO);
        else if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO)
            turno.getXogador().getAvatar().setModoXogo(ModoXogo.NORMAL);

        consola.imprimir("O avatar "+turno.getXogador().getAvatar().getId() + " de tipo "+turno.getXogador().getAvatar().getTipo() +" cambia de modo "+turno.getXogador().getAvatar().getModoXogo());
    }



    /**
     * Este método permite propoñer un trato.
     * @param comandos comandos do trato
     */
    @Override
    public void proponerTrato(String[] comandos) throws MonopolinhoGeneralException { //trato nombre: cambiar (solar1, solar2 y noalquiler(solar3, 5))
        String texto="".join(" ",comandos);
        String[] cmds=texto.split(":");
        if (cmds.length!=2) return;
        String xogadorDestino=cmds[0].split(" ")[1];
        String opcionsTrato=cmds[1].replace(" ","");
        String[] partesTrato=opcionsTrato.split(",",2);

        String parteOferta=partesTrato[0];
        String parteDemanda=partesTrato[1];

        for(String s:parteOferta.split("y")){ //revisar esto
            String segmentoLimpo=s.replace("("," ").replace(")"," ").replace(","," ");
            String[] elementosOferta=segmentoLimpo.split(" ");
            for(int i=0;i<elementosOferta.length;i++){
                if(elementosOferta[i].equals("cambiar")) continue;
                if(elementosOferta[i].equals("noalquiler")){
                    System.out.println("noalquiler "+elementosOferta[i+1]);
                    System.out.println("noalquiler "+elementosOferta[i+2]);
                    i=i+3;
                    if(i==elementosOferta.length) break;
                }
                System.out.println("oferta "+elementosOferta[i]);
            }
        }
        for(String s:parteDemanda.split("y")){
            String segmentoLimpo=s.replace("("," ").replace(")"," ").replace(","," ");
            String[] elementosDemanda=segmentoLimpo.split(" ");
            for(int i=0;i<elementosDemanda.length;i++){
                if(elementosDemanda[i].equals("noalquiler")){
                    System.out.println("noalquiler "+elementosDemanda[i+1]);
                    System.out.println("noalquiler "+elementosDemanda[i+2]);
                    i=i+3;
                    if(i==elementosDemanda.length) break;
                }
                System.out.println("demanda "+elementosDemanda[i]);
            }
        }
        /*
        Xogador emisor=this.turno.getXogador();
        Xogador destinatario=buscarXogadorPorNome(cmds[1].substring(0,cmds[1].length()-1));


        if(destinatario==null){
            throw new MonopolinhoGeneralException("Non podes propoñer ese trato porque " + cmds[1] + " non é un xogador da partida.");
        }
        if(emisor.equals(destinatario)){
            throw new MonopolinhoGeneralException("Non podes propoñer un trato a ti mismo.");
        }

        Trato trato=new Trato(emisor,destinatario,generarID());

        String[] limpo=new String[cmds.length];
        int limiteOferta=-1;
        int limiteDemanda=-1;
        for (int i=0;i<cmds.length;i++){
            if(cmds[i].charAt(cmds[i].length()-1) == ','){
                if(limiteOferta==-1) limiteOferta=i;
            }
            else if(cmds[i].charAt(cmds[i].length()-1)== ')'){
                if(limiteDemanda==-1) limiteDemanda=i;
            }
            limpo[i]=cmds[i].replace("(","").replace(")","").replace(",","");
        }


        for (int i=3;i<=limiteOferta;i++){
            if(isNumeric(limpo[i])){
                trato.setDinheiroOferta(Float.valueOf(limpo[i]));
            }
            else if(!isNumeric(limpo[i]) && !limpo[i].equals("y") && !limpo[i].equals("")){
                Casilla c=this.taboeiro.buscarCasilla(limpo[i]);
                if(!comprobarOfrecerTrato(c,emisor,limpo[i])){
                    return;
                }
                trato.engadirPropiedadeOferta((Propiedade)c);
            }
        }
        for (int i=limiteOferta+1;i<cmds.length;i++){
            if(isNumeric(limpo[i])){
                trato.setDinheiroDemanda(Float.valueOf(limpo[i]));
            }
            else if(!isNumeric(limpo[i]) && !limpo[i].equals("y") && !limpo[i].equals("")){
                Casilla c=this.taboeiro.buscarCasilla(limpo[i]);
                if(!comprobarOfrecerTrato(c,destinatario,limpo[i])){
                    return;
                }
                trato.engadirPropiedadeDemanda((Propiedade)c);
            }
        }
        consola.imprimir(trato);
        destinatario.engadirTrato(trato);
        this.numTratos++;

         */
    }

    /**
     * Este método permite listar os tratos do xogador que ten o turno.
     */
    @Override
    public void listarTratos(){
        consola.imprimir(this.turno.getXogador().listarTratos());
    }

    /**
     * Este método permite aceptar un trato
     * @param id trato aceptar
     */
    @Override
    public void aceptarTrato(String id) throws MonopolinhoGeneralException {
        Trato trato=this.turno.getXogador().buscarTrato(id);
        if(trato==null){
            throw new MonopolinhoGeneralException("Trato inválido: non se puido aceptar o trato "+id+" porque non existe nos teus tratos.");
        }
        else{
            Xogador emisor=trato.getEmisorTrato();
            Xogador destinatario=trato.getDestinatarioTrato();
            if(trato.getDinheiroOferta()!=-1){
                if(!emisor.quitarDinheiro(trato.getDinheiroOferta(),TipoTransaccion.COMPRA)){
                    throw new MonopolinhoGeneralException(emisor.getNome()+" non dispón de  "+trato.getDinheiroOferta()+" para este trato.");
                }
                else{
                    destinatario.engadirDinheiro(trato.getDinheiroOferta(),TipoTransaccion.VENTA);
                }
            }
            if(trato.getDinheiroDemanda()!=-1){
                if(!destinatario.quitarDinheiro(trato.getDinheiroDemanda(),TipoTransaccion.COMPRA)){
                    throw new MonopolinhoGeneralException(destinatario.getNome()+" non dispón de "+trato.getDinheiroDemanda()+" para este trato.");
                }
                else{
                    emisor.engadirDinheiro(trato.getDinheiroDemanda(),TipoTransaccion.VENTA);
                }
            }
            for(Propiedade p:trato.getPropiedadesOferta()){
                emisor.eliminarPropiedade(p);
                destinatario.engadirPropiedade(p);
            }
            for (Propiedade p:trato.getPropiedadesDemanda()){
                destinatario.eliminarPropiedade(p);
                emisor.engadirPropiedade(p);
            }
            consola.imprimir("Aceptouse o trato "+id+" con "+emisor.getNome()+": ( "+trato+" )");
            this.turno.getXogador().eliminarTrato(id);
        }
    }

    /**
     * Este método permite eliminar un trato
     * @param id trato eliminar
     */
    @Override
    public void eliminarTrato(String id) throws MonopolinhoGeneralException {
        for(Xogador x:this.xogadores){
            for(Trato t:x.getTratos().values()){
                if(t.getEmisorTrato().equals(this.turno.getXogador()) && t.getID().equals(id.toLowerCase())) {
                    if (x.eliminarTrato(id.toLowerCase())) {
                        consola.imprimir(id + " eliminado correctamente.");
                        return;
                    }
                }
            }
        }
        throw new MonopolinhoGeneralException("Non se puido eliminar o "+id);
    }


    public void listarAccions(){
        consola.imprimir(turno.listarAccions());
    }


    /**
     * GETTERS AND SETTERS
     */

    /**
     * @return Devolve o taboeiro
     */

    public Taboeiro getTaboeiro() {
        return taboeiro;
    }

    /**
     * @return Devolve os datos
     */

    public Dados getDados() {
        return dados;
    }

    /**
     * @return Devolve o xogador banca
     */

    public Xogador getBanca() {
        return banca;
    }

    /**
     * @return Devolve os xogadores
     */

    public ArrayList<Xogador> getXogadores() {
        return xogadores;
    }

    /**
     * @return Devolve o Turno actual
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @return Devolve o número de xogadores
     */
    public int getNumeroXogadores(){
        int nXogadores=0;
        for(Xogador x:xogadores)
            if(!x.enBancarrota())nXogadores++;

        return nXogadores;
    }

    /**
     * @return Compraba se se comezou a partida
     */
    public boolean partidaComezada() {
        return partidaComezada;
    }

    /**
     * Establece se se comezou a partida
     */
    @Override
    public void comezarPartida() {
        this.partidaComezada = true;
    }

    /**
     * Este metodo interpreta a accion a realizar segundo a casilla na que se cae.
     * @param valorDados Movemento no taboeiro respecto a casilla actual
     * @return Mensaxe da acción interpretada
     */

    public void moverModoNormal(int valorDados) throws MonopolinhoException {
        consola.imprimir(turno.getXogador().getAvatar().moverEnBasico(this,valorDados));
    }

    /**
     * Move os avatares de en modo avanzado
     */
    private void moverModoAvanzado(int valorDados){
        try{
            turno.getXogador().getAvatar().moverEnAvanzado(this,valorDados);
        }catch (MonopolinhoException e){
            e.imprimirErro();
        }
    }

    /**
     * Este método permite saber si todos os xogadores deron un número de voltas múltiplo de 4
     * @return true si deron todos un múltiplo de 4 voltas, false se non.
     */
    public boolean deronTodosCatroVoltas(){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getVoltasTaboeiro()==0 || x.getAvatar().getVoltasTaboeiro()%4 !=0)
                return false;
        }
        return true;
    }


    /**
     * Este método aumenta o precio das casillas que están en venta nun 5%
     */
    public void aumentarPrecioCasillas(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas()){
            for(Casilla c:zona){
                if(c instanceof Propiedade){
                    Propiedade p=(Propiedade)c;
                    if(p.pertenceXogador(banca) && (c instanceof Solar)){
                        p.setValor(p.getValor()*1.05f);
                    }
                }
            }
        }
    }


    /**
     * Este método genera un Id correlativo
     * @return Id do trato
     */
    private String generarID(){
        String id="trato"+(this.numTratos+1);
        return id;
    }

    /**
     * Este método comproba se se cumplen os requisitos dunha casilla para un trato.
     * @param c Casilla a comprobar
     * @param x xogador a comprobar
     * @param nome nome da casilla a comprobar
     * @return true se se pode realizar o trato, false se non
     */
    private boolean comprobarOfrecerTrato(Casilla c,Xogador x,String nome){
        try{
            if(c==null){
                throw new MonopolinhoGeneralException(nome+" non é unha casilla válida. Trato cancelado.");
            }
            if(!(c instanceof Propiedade)){
                throw new MonopolinhoGeneralException(nome+" non é unha propiedade. Trato cancelado.");
            }
            if(!((Propiedade) c).pertenceXogador(x)){
                throw new MonopolinhoGeneralException("Trato cancelado: Non podes ofrecer "+nome+" porque non é "+ (x.equals(this.turno.getXogador())? "túa.":"de "+x.getNome()));
            }
        }catch (MonopolinhoGeneralException e) {
            e.imprimirErro();
            return false;
        }
        return true;
    }


    /**
     * Este metodo comproba si un string é un número
     * @param str string a comprobar
     * @return true si é un número, false se non
     */
    private boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Este metodo busca un xogador do xogo polo nome
     * @param nome nome a buscar
     * @return Xogador
     */
    private Xogador buscarXogadorPorNome(String nome){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                return x;
            }
        }
        return null;
    }
    /**
     * Este metodo engade todas as casillas a banca. Despois os xogadores compranlle as propiedades a banca.
     */
    private void engadirCasillasBanca(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas()){
            for(Casilla c:zona)
                if(c instanceof Propiedade)
                    ((Propiedade)c).setDono(banca);
        }
    }

    /**
     * @return Non permite realizar a acción se está no cárcere
     */
    private boolean comprobarCarcere(){
        if(this.turno.getXogador().estaNoCarcere()){
            try{
                throw new MonopolinhoGeneralException("Non podes realizar está acción porque estás no cárcere");
            }
            catch (MonopolinhoGeneralException e){
                e.imprimirErro();
                return true;
            }
        }
        return false;
    }

    /**
     * @return Comproba se acabou a partida
     */
    private boolean comprobarFinPartida(){
        int xogadoresEnBancarrota=0;
        for(Xogador x:this.xogadores){
            if(x.estadoXogador()==EstadoXogador.BANCARROTA)
                xogadoresEnBancarrota++;
        }
        return ((this.xogadores.size()-1)==xogadoresEnBancarrota);
    }


    public boolean comprobarAvatarRepetido(Avatar avatar){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().equals(avatar))return true;
        }
        return false;
    }


    //BORRAR
    public void mov(int i) throws MonopolinhoException {
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        turno.engadirAccion(new Accion(TipoAccion.LANZAR_DADOS,turno.getPosicion()));
        moverModoNormal(i);
    }

    public void mova(int i){
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        turno.engadirAccion(new Accion(TipoAccion.LANZAR_DADOS,turno.getPosicion()));
        moverModoAvanzado(i);
    }

    public void desfacer(){
        this.turno.desfacer(this);
    }

}
