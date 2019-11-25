package monopolinho.interfaz;

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
import monopolinho.obxetos.excepcions.MonopolinhoNonSePodeConstruir;
import monopolinho.tipos.*;

import java.util.ArrayList;
import java.util.Collection;

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
    public void describirXogador(String nome){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                consola.imprimir(x.describir());
                return;
            }
        }
        Xogo.consola.imprimirErro("O xogador " + nome + "non existe");
    }

    /**
     * Este metodo imprime a info dun avatar.
     * @param avatarId Argumentos do usuario
     */
    @Override
    public void describirAvatar(String avatarId){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getId().equals(avatarId)){
                consola.imprimir(x.getAvatar());
                return;
            }
        }
        Xogo.consola.imprimirErro("O avatar " + avatarId + "non existe");
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
    public void listarEdificiosGrupo(String cmds){
        Grupo grupo=taboeiro.buscarGrupo(cmds);
        if(grupo==null){
            Xogo.consola.imprimirErro("Ese nome de grupo non existe.");
            return;
        }
        if(grupo.getNumeroEdificios()==0){
            Xogo.consola.imprimirErro("Este grupo non ten edificios.");
            return;
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
    public void pasarTurno(){
        Xogador actual=turno.getXogador();
        int novoTurno;
        if(turno.podeLanzar() && turno.getXogador().estadoXogador()!=EstadoXogador.BANCARROTA && turno.getXogador().getTurnosInvalidado()==0){
            Xogo.consola.imprimirErro("Tes que tirar antes de pasar turno.");
            return;
        }
        if(turno.getXogador().estadoXogador() == EstadoXogador.TEN_DEBEDAS){
            Xogo.consola.imprimirErro("Non podes pasar de turno ata que saldes as débedas ou te declares en bancarrota.");
            return;
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
                Xogo.consola.imprimirErro("O xogador leva 3 turnos no cárcere e non ten cartos para saír, o xogador debe declararse en bancarrota");
                turno.getXogador().setEstadoXogador(EstadoXogador.BANCARROTA);
                return;
            }
        }
        actual.restarTurnosInvalidado();
        consola.imprimir("Tiña o turno "+actual.getNome()+", agora teno "+turno.getXogador().getNome());
        turno.getXogador().listarTratos();
    }

    /**
     * Este metodo saca a un xogador da carcere.
     */
    @Override
    public void sairCarcere(){
        if (!turno.getXogador().estaNoCarcere()){
            consola.imprimir("O xogador non está no cárcere");
            return;
        }
        if(turno.getXogador().quitarDinheiro(Valor.SAIR_CARCERE, TipoTransaccion.OTROS)){
            consola.imprimir(turno.getXogador().getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Podes lanzar os dados.");
            turno.getXogador().sairDoCarcere();
            taboeiro.engadirBote(Valor.SAIR_CARCERE);
        }else{
            Xogo.consola.imprimirErro("Non tes o suficiente diñeiro para saír do cárcere");
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
        if(target==null)throw new MonopolinhoCasillaInexistente(nome);

        if(!(target instanceof Propiedade)){
            Xogo.consola.imprimirErro("Este tipo de casilla non se pode comprar esta casilla");
            return;
        }
        comprar=(Propiedade)target;
        if(turno.getVecesTiradas()==0){
            consola.imprimir("Non podes comprar unha casilla se non lanzaches os dados");
            return;
        }
        if(this.turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO){
            if(!this.turno.getHistorial().contains(comprar)){
                Xogo.consola.imprimirErro("Non pasaches por esta casilla neste turno");
                return;
            }
        }else{
            if(!this.turno.getPosicion().equals(comprar)){
                Xogo.consola.imprimirErro("Non estás nesta casilla, non a podes comprar");
                return;
            }
        }
        if (!comprar.pertenceXogador(banca)){
            Xogo.consola.imprimirErro("Esta casilla pertence a " + comprar.getDono().getNome()+". Non a podes comprar");
            return;
        }
        if(!xogador.quitarDinheiro(comprar.getValor(), TipoTransaccion.COMPRA)){
            Xogo.consola.imprimirErro("Non tes suficiente diñeiro");
            return ;
        }
        if(xogador.getAvatar().getTipo()==TipoMovemento.COCHE && xogador.getAvatar().getModoXogo()==ModoXogo.AVANZADO && turno.getCompradasTurno()>=1){
            Xogo.consola.imprimirErro("Non podes facer máis compras neste turno");
            return;
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
    public void mostrarEstadisticasXogador(String nome){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                consola.imprimir(x.getEstadisticas());
                return;
            }
        }
        consola.imprimir("Non se atopou o xogador "+nome);
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
            Xogo.consola.imprimirErro("Non se pode hipotecar este tipo de casilla");
            return;
        }
        c=(Propiedade)target;
        if(!c.pertenceXogador(turno.getXogador())){
            Xogo.consola.imprimirErro("Non eres dono de esta casilla");
            return;
        }
        if(c.getEstaHipotecada()){
            Xogo.consola.imprimirErro("Esta casilla xa está hipotecada");
            return;
        }
        if((c instanceof Solar) && (((Solar)c).getEdificios().size()!=0)){
            Xogo.consola.imprimirErro(c.getNome()+" conten edificios, tes que vendelos antes de hipotecar.");
            return;
        }

        c.setEstaHipotecada(true);
        c.getDono().engadirDinheiro(c.getHipoteca(), TipoTransaccion.OTROS);
        turno.engadirAccion(new Accion(TipoAccion.HIPOTECAR,c));
        consola.imprimir("\nAcabas de hipotecar a casilla "+c.getNome()+" e recibes "+c.getHipoteca());

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
            Xogo.consola.imprimirErro("Non se pode deshipotecar este tipo de casilla");
            return;
        }
        c=(Propiedade)target;
        if(!c.pertenceXogador(turno.getXogador())){
            Xogo.consola.imprimirErro("Non eres dono de esta casilla");
            return;
        }
        if(!c.getEstaHipotecada()){
            Xogo.consola.imprimirErro("Esta casilla non está hipotecada");
            return;
        }

        if(!c.getDono().quitarDinheiro(c.getHipoteca()*1.1f, TipoTransaccion.OTROS)){
            Xogo.consola.imprimirErro("Non tes o suficiente diñeiro para deshipotecar a propiedade");
            return;
        }
        c.setEstaHipotecada(false);
        turno.engadirAccion(new Accion(TipoAccion.DESHIPOTECAR,c));
        consola.imprimir("\nAcabas de deshipotecar a casilla "+c.getNome()+". Pagas "+c.getHipoteca()*1.1f);

    }

    /**
     * Metodo bancarrota.
     */
    @Override
    public void declararBancarrota(){
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
    public void venderEdificio(TipoEdificio tipo, String casilla, int numero) throws MonopolinhoCasillaInexistente {
        if(comprobarCarcere())return;
        Casilla target=taboeiro.buscarCasilla(casilla);
        Solar c;
        if(target==null)throw new MonopolinhoCasillaInexistente(casilla);

        if(!(target instanceof Solar)){
            Xogo.consola.imprimirErro("Non se pode vender edificios dunha casilla que non sexa un SOLAR");
            return;
        }
        c=(Solar)target;
        if(tipo==null){
            Xogo.consola.imprimirErro("Tipo de edificio incorrecto.");
            return;
        }

        if(!c.pertenceXogador(turno.getXogador())){
            Xogo.consola.imprimirErro("Non podes vender edificios en "+c.getNome()+" porque non é túa.");
            return;
        }
        if(c.getEstaHipotecada()){
            Xogo.consola.imprimirErro("Non podes vender edificios en "+c.getNome()+" porque está hipotecada");
            return;
        }

        int totalEdifs=c.getNumeroEdificiosTipo(tipo);
        float valor = numero*c.getPrecioEdificio(tipo)/2.0f;
        if(totalEdifs<numero){
            Xogo.consola.imprimirErro("Non podes vender edificios de tipo "+tipo+". Solamente tes "+totalEdifs+" de tipo "+tipo+" por valor de "+valor);
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
    public void mostrarTurno() {
        if (turno!=null)consola.imprimir(turno.getXogador());
        else Xogo.consola.imprimirErro("Non hai xogadores");
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
            Xogo.consola.imprimirErro("Non se pode crear un xogador durante a partida");
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
    public void lanzarDados(){
        if(turno.getXogador().estadoXogador()==EstadoXogador.TEN_DEBEDAS){
            Xogo.consola.imprimirErro("O xogador ten debedas, ten que declarase en bancarrota ou hipotecar propiedades");
            return;
        }
        if(!turno.podeLanzar()){
            Xogo.consola.imprimirErro("O xogador xa lanzou os dados. Non se poden lanzar de novo");
            return;
        }
        if(turno.getXogador().getTurnosInvalidado()>0){
            Xogo.consola.imprimirErro("O xogador ten que esperar "+turno.getXogador().getTurnosInvalidado()+" turno para volver a lanzar");
            return;
        }
        dados.lanzarDados();
        turno.aumentarVecesTiradas();   //1 vez tirada

        String mensaxe="\nSaiu o "+ ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado1()))+
                        " e o "+
                        ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado2()));

        if(turno.getXogador().estaNoCarcere()){
            if(!dados.sonDobles()){
                consola.imprimir("Estás no CÁRCERE");
                Xogo.consola.imprimirErro(mensaxe+". Tes que sacar dobles ou pagar para saír do cárcere.");
                turno.setPodeLanzar(false);
                return;
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
                Xogo.consola.imprimirErro(mensaxe+" Saion triples e vas para o cárcere.");
                return;
            }else{
                turno.setPodeLanzar(false);
            }
        }
        consola.imprimir(mensaxe);
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
     * @param cmds comandos do trato
     */
    @Override
    public void proponerTrato(String[] cmds){ //FALTA IMPLEMENTAR O DE NOALQUILER

        Xogador emisor=this.turno.getXogador();
        Xogador destinatario=buscarXogadorPorNome(cmds[1].substring(0,cmds[1].length()-1));


        if(destinatario==null){
            Xogo.consola.imprimirErro("Non podes propoñer ese trato porque " + cmds[1] + " non é un xogador da partida.");
            return;
        }
        if(emisor.equals(destinatario)){
            Xogo.consola.imprimirErro("Non podes propoñer un trato a ti mismo.");
            return;
        }

        Trato trato=new Trato(emisor,destinatario,generarID());

        String[] limpo=new String[cmds.length];
        int limiteOferta=-1;
        for (int i=0;i<cmds.length;i++){
            if(cmds[i].charAt(cmds[i].length()-1) == ','){
                limiteOferta=i;
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
    public void aceptarTrato(String id){

    }

    /**
     * Este método permite eliminar un trato
     * @param id trato eliminar
     */
    @Override
    public void eliminarTrato(String id){
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
        consola.imprimir("Non se puido eliminar o "+id);
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

    public void moverModoNormal(int valorDados){
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
        if(c==null){
            Xogo.consola.imprimirErro(nome+" non é unha casilla válida. Trato cancelado.");
            return false;
        }
        if(!(c instanceof Propiedade)){
            Xogo.consola.imprimirErro(nome+" non é unha propiedade. Trato cancelado.");
            return false;
        }
        if(!((Propiedade) c).pertenceXogador(x)){
            Xogo.consola.imprimirErro("Trato cancelado: Non podes ofrecer "+nome+" porque non é "+ (x.equals(this.turno.getXogador())? "túa.":"de "+x.getNome()));
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
            Xogo.consola.imprimirErro("Non podes realizar está acción porque estás no cárcere");
            return true;
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

    public void mov(int i){
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        moverModoNormal(i);
    }

    public void mova(int i){
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        moverModoAvanzado(i);
    }

}
