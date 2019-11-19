package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasXogo;
import monopolinho.obxetos.*;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.*;

import java.util.ArrayList;

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
    public void describirCasilla(String nome){
        Casilla c=this.taboeiro.buscarCasilla(nome);
        if(c!=null)System.out.println(c);
        else ReprTab.imprimirErro("A casilla " + nome + " non existe");
    }

    /**
     * Este metodo imprime a info dun xogador.
     * @param nome Nome do xogador
     */
    @Override
    public void describirXogador(String nome){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                System.out.println(x.describir());
                return;
            }
        }
        ReprTab.imprimirErro("O xogador " + nome + "non existe");
    }

    /**
     * Este metodo imprime a info dun avatar.
     * @param avatarId Argumentos do usuario
     */
    @Override
    public void describirAvatar(String avatarId){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getId().equals(avatarId)){
                System.out.println(x.getAvatar());
                return;
            }
        }
        ReprTab.imprimirErro("O avatar " + avatarId + "non existe");
    }

    /**
     * Este metodo lista todos os edificios
     */
    @Override
    public void listarEdificios(){
        for(Grupo g:taboeiro.getGrupos()){
            for(Solar s:g.getSolares()){
                for(Edificio e:s.getEdificios()){
                    System.out.println(e.describirEdificio());
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
            ReprTab.imprimirErro("Ese nome de grupo non existe.");
            return;
        }
        if(grupo.getNumeroEdificios()==0){
            ReprTab.imprimirErro("Este grupo non ten edificios.");
            return;
        }
        for(Solar c:grupo.getSolares()){
            System.out.println(c.describirEdificios());
        }
    }


    /**
     * Este metodo imprime todos os avatares.
     */
    @Override
    public void listarAvatares(){
        for(Xogador x:this.xogadores)
            System.out.println(x.getAvatar());
    }

    /**
     * Este metodo imprime todos os xogadores.
     */
    @Override
    public void listarXogadores(){
        for(Xogador x:this.xogadores)
            System.out.println(x.describir());
    }

    /**
     * Este metodo imprime todas as casillas en venta.
     */
    @Override
    public void listarCasillaEnVenta(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas())
            for(Casilla c:zona)
                if(c instanceof Propiedade)
                    System.out.println(c);
    }

    /**
     * Este metodo acaba o turno e pasallo ao seguinte.
     */
    @Override
    public void pasarTurno(){
        Xogador actual=turno.getXogador();
        int novoTurno;
        if(turno.podeLanzar() && turno.getXogador().estadoXogador()!=EstadoXogador.BANCARROTA && turno.getXogador().getTurnosInvalidado()==0){
            ReprTab.imprimirErro("Tes que tirar antes de pasar turno.");
            return;
        }
        if(turno.getXogador().estadoXogador() == EstadoXogador.TEN_DEBEDAS){
            ReprTab.imprimirErro("Non podes pasar de turno ata que saldes as débedas ou te declares en bancarrota.");
            return;
        }

        do{
            novoTurno=(this.xogadores.indexOf(turno.getXogador())+1)%this.xogadores.size();
            turno=new Turno(this.xogadores.get(novoTurno));
        }while(turno.getXogador().enBancarrota());

        if(comprobarFinPartida()){
            System.out.println("\nFin da partida!!");
            System.out.println("O xogador "+turno.getXogador().getNome()+ " gañou a partida");
            System.exit(0);
        }
        if(actual.estaNoCarcere())
            actual.restarTurnoCarcere();

        if(actual.getTurnosNoCarcere()==0){
            if(turno.getXogador().quitarDinheiro(Valor.SAIR_CARCERE, TipoTransaccion.OTROS)){
                System.out.println("O xogador"+turno.getXogador().getNome()+" leva 3 turnos no carcere  paga "+ Valor.SAIR_CARCERE + " e sae da cárcere. E pode lanzar os dados.");
                turno.getXogador().sairDoCarcere();
                taboeiro.engadirBote(Valor.SAIR_CARCERE);
                return;
            }else{
                ReprTab.imprimirErro("O xogador leva 3 turnos no cárcere e non ten cartos para saír, o xogador debe declararse en bancarrota");
                turno.getXogador().setEstadoXogador(EstadoXogador.BANCARROTA);
                return;
            }
        }
        actual.restarTurnosInvalidado();
        System.out.println("Tiña o turno "+actual.getNome()+", agora teno "+turno.getXogador().getNome());
    }

    /**
     * Este metodo saca a un xogador da carcere.
     */
    @Override
    public void sairCarcere(){
        if (!turno.getXogador().estaNoCarcere()){
            System.out.println("O xogador non está no cárcere");
            return;
        }
        if(turno.getXogador().quitarDinheiro(Valor.SAIR_CARCERE, TipoTransaccion.OTROS)){
            System.out.println(turno.getXogador().getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Podes lanzar os dados.");
            turno.getXogador().sairDoCarcere();
            taboeiro.engadirBote(Valor.SAIR_CARCERE);
        }else{
            ReprTab.imprimirErro("Non tes o suficiente diñeiro para saír do cárcere");
        }
    }

    /**
     * Este metodo engade unha casilla as propiedades do xogador.
     * @param cmds Argumentos da función
     */
    @Override
    public void comprarCasilla(String[] cmds){
        if(comprobarCarcere())return;
        Xogador xogador=turno.getXogador();
        Casilla target=this.taboeiro.buscarCasilla(cmds[1]);
        Propiedade comprar;
        if(target==null){
            ReprTab.imprimirErro("A casilla "+cmds[1]+" non existe");
            return;
        }
        if(!(target instanceof Propiedade)){
            ReprTab.imprimirErro("Este tipo de casilla non se pode comprar esta casilla");
            return;
        }
        comprar=(Propiedade)target;
        if(turno.getVecesTiradas()==0){
            System.out.println("Non podes comprar unha casilla se non lanzaches os dados");
            return;
        }
        if(this.turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO){
            if(!this.turno.getHistorial().contains(comprar)){
                ReprTab.imprimirErro("Non pasaches por esta casilla neste turno");
                return;
            }
        }else{
            if(!this.turno.getPosicion().equals(comprar)){
                ReprTab.imprimirErro("Non estás nesta casilla, non a podes comprar");
                return;
            }
        }
        if (!comprar.getDono().equals(banca)){
            ReprTab.imprimirErro("Esta casilla pertence a " + comprar.getDono().getNome()+". Non a podes comprar");
            return;
        }
        if(!xogador.quitarDinheiro(comprar.getValor(), TipoTransaccion.COMPRA)){
            ReprTab.imprimirErro("Non tes suficiente diñeiro");
            return ;
        }
        if(xogador.getAvatar().getTipo()==TipoMovemento.COCHE && xogador.getAvatar().getModoXogo()==ModoXogo.AVANZADO && turno.getCompradasTurno()>=1){
            ReprTab.imprimirErro("Non podes facer máis compras neste turno");
            return;
        }

        comprar.getDono().engadirDinheiro(comprar.getValor(), TipoTransaccion.VENTA);
        comprar.setDono(xogador);
        turno.aumentarCompras();
        System.out.println("O usuario "+xogador.getNome() +" comprou "+comprar.getNome() +" por "+comprar.getValor());
    }


    /**
     * Este metodo imprime o tableiro.
     */
    @Override
    public void mostrarTaboeiro(){
        System.out.println(taboeiro);
    }

    /**
     * Mostra as estadisticas para un xogador
     * @param nome
     */
    @Override
    public void mostrarEstadisticasXogador(String nome){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                System.out.println(x.getEstadisticas());
                return;
            }
        }
        System.out.println("Non se atopou o xogador "+nome);
    }

    /**
     * Mostra as estadisticas do Xogo
     */
    @Override
    public void mostrarEstadisticasXogo(){
        System.out.println(estadisticasXogo);
    }

    /**
     * Metodo que hipoteca unha casilla.
     * @param nome Nome casilla
     */
    @Override
    public void hipotecarCasilla(String nome){
        if(comprobarCarcere())return;
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade c=null;
        if(target==null){
            ReprTab.imprimirErro("A casilla "+nome+" non existe");
            return;
        }
        if(!(target instanceof Propiedade)){
            ReprTab.imprimirErro("Non se pode hipotecar este tipo de casilla");
            return;
        }
        c=(Propiedade)target;
        if(!c.getDono().equals(turno.getXogador())){
            ReprTab.imprimirErro("Non eres dono de esta casilla");
            return;
        }
        if(c.getEstaHipotecada()){
            ReprTab.imprimirErro("Esta casilla xa está hipotecada");
            return;
        }
        if((c instanceof Solar) && (((Solar)c).getEdificios().size()!=0)){
            ReprTab.imprimirErro(c.getNome()+" conten edificios, tes que vendelos antes de hipotecar.");
            return;
        }

        c.setEstaHipotecada(true);
        c.getDono().engadirDinheiro(c.getHipoteca(), TipoTransaccion.OTROS);
        System.out.println("\nAcabas de hipotecar a casilla "+c.getNome()+" e recibes "+c.getHipoteca());

    }


    /**
     * Metodo que deshipoteca unha casilla.
     * @param nome Nome casilla
     */
    @Override
    public void deshipotecarCasilla(String nome){
        if(comprobarCarcere())return;
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade c=null;
        if(target==null){
            ReprTab.imprimirErro("A casilla "+nome+" non existe");
            return;
        }
        if(!(target instanceof Propiedade)){
            ReprTab.imprimirErro("Non se pode deshipotecar este tipo de casilla");
            return;
        }
        c=(Propiedade)target;
        if(!c.getDono().equals(turno.getXogador())){
            ReprTab.imprimirErro("Non eres dono de esta casilla");
            return;
        }
        if(!c.getEstaHipotecada()){
            ReprTab.imprimirErro("Esta casilla non está hipotecada");
            return;
        }

        if(!c.getDono().quitarDinheiro(c.getHipoteca()*1.1f, TipoTransaccion.OTROS)){
            ReprTab.imprimirErro("Non tes o suficiente diñeiro para deshipotecar a propiedade");
            return;
        }
        c.setEstaHipotecada(false);
        System.out.println("\nAcabas de deshipotecar a casilla "+c.getNome()+". Pagas "+c.getHipoteca()*1.1f);

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
        System.out.println("\nO xogador "+this.turno.getXogador().getNome()+" declarouse en bancarrota.");
        pasarTurno();
    }

    /**
     * Este metodo permite construir
     * @param tipo Tipo de edificio
     */
    @Override
    public void edificar(TipoEdificio tipo){
        if(comprobarCarcere())return;
        Solar actual;
        Casilla target=turno.getPosicion();
        if(!(target instanceof Solar)){
            ReprTab.imprimirErro("Non se pode construir nunha casilla que non sexa un SOLAR");
            return;
        }
        actual=(Solar)target;
        if(tipo==null){
            ReprTab.imprimirErro("Tipo de edificio incorrecto");
            return;
        }
        if(!comprobarConstruir(actual,tipo))return;
        switch (tipo){
            case CASA:
                if(!actual.getGrupo().tenTodoGrupo(turno.getXogador()) && actual.numeroVecesCaidas(turno.getXogador().getAvatar())<2){
                    ReprTab.imprimirErro("Para edificar unha casa debes ter todo o grupo ou caer 2 veces en "+actual.getNome());
                    return;
                }
                break;
            case HOTEL:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<4){
                    ReprTab.imprimirErro("Necesitas 4 casas en "+actual.getNome()+" para edificar un hotel");
                    return;
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
                    ReprTab.imprimirErro("Necesitas polo menos 2 casas e 1 hotel en "+actual.getNome()+" para edificar unha piscina.");
                    return;
                }
                break;
            case PISTA_DEPORTES:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2){
                    ReprTab.imprimirErro("Necesitas polo menos 2 hoteles en "+actual.getNome()+" para edificar unha pista de deportes.");
                    return;
                }
                break;
        }
        if(!turno.getXogador().quitarDinheiro(actual.getPrecioEdificio(tipo), TipoTransaccion.EDIFICAR)){
            ReprTab.imprimirErro("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(tipo,actual);
        actual.engadirEdificio(e);
        System.out.println("O usuario "+turno.getXogador().getNome() +" edificou en "+actual.getNome()+": "+tipo+". A súa fortuna redúcese en "+e.getPrecio());
    }


    /**
     * Vende un edificio
     * @param tipo tipo de edificio
     * @param casilla Casilla
     * @param numero Número de edificios a vender
     */
    @Override
    public void venderEdificio(TipoEdificio tipo, String casilla, int numero){
        if(comprobarCarcere())return;
        Casilla target=taboeiro.buscarCasilla(casilla);
        Solar c;
        if(target==null){
            ReprTab.imprimirErro("A casilla "+casilla+" non existe.");
            return;
        }
        if(!(target instanceof Solar)){
            ReprTab.imprimirErro("Non se pode vender edificios dunha casilla que non sexa un SOLAR");
            return;
        }
        c=(Solar)target;
        if(tipo==null){
            ReprTab.imprimirErro("Tipo de edificio incorrecto.");
            return;
        }
        if(c.getTipoCasilla()!=TipoCasilla.SOLAR){
            ReprTab.imprimirErro("Só podes vender edificios de solares.");
            return;
        }
        if(!c.getDono().equals(turno.getXogador())){
            ReprTab.imprimirErro("Non podes vender edificios en "+c.getNome()+" porque non é túa.");
            return;
        }
        if(c.getEstaHipotecada()){
            ReprTab.imprimirErro("Non podes vender edificios en "+c.getNome()+" porque está hipotecada");
            return;
        }

        int totalEdifs=c.getNumeroEdificiosTipo(tipo);
        float valor = numero*c.getPrecioEdificio(tipo)/2.0f;
        if(totalEdifs<numero){
            ReprTab.imprimirErro("Non podes vender edificios de tipo "+tipo+". Solamente tes "+totalEdifs+" de tipo "+tipo+" por valor de "+valor);
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
        System.out.println("Vendiches "+numero+" edificios de tipo "+tipo+" e recibes "+valor);
    }

    /**
     * Mostra a información do turno actual
     */
    @Override
    public void mostrarTurno() {
        if (turno!=null)System.out.println(turno.getXogador());
        else ReprTab.imprimirErro("Non hai xogadores");
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
            ReprTab.imprimirErro("Non se pode crear un xogador durante a partida");
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
        System.out.println(xogador);
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
            ReprTab.imprimirErro("O xogador ten debedas, ten que declarase en bancarrota ou hipotecar propiedades");
            return;
        }
        if(!turno.podeLanzar()){
            ReprTab.imprimirErro("O xogador xa lanzou os dados. Non se poden lanzar de novo");
            return;
        }
        if(turno.getXogador().getTurnosInvalidado()>0){
            ReprTab.imprimirErro("O xogador ten que esperar "+turno.getXogador().getTurnosInvalidado()+" turno para volver a lanzar");
            return;
        }
        dados.lanzarDados();
        turno.aumentarVecesTiradas();   //1 vez tirada

        String mensaxe="\nSaiu o "+ ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado1()))+
                        " e o "+
                        ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado2()));

        if(turno.getXogador().estaNoCarcere()){
            if(!dados.sonDobles()){
                System.out.println("Estás no CÁRCERE");
                ReprTab.imprimirErro(mensaxe+". Tes que sacar dobles ou pagar para saír do cárcere.");
                turno.setPodeLanzar(false);
                return;
            }else{
                turno.getXogador().sairDoCarcere();
                turno.setVecesTiradas(0);
                System.out.println(mensaxe+". Sacaches dados dobles, saíches do cárcere. Tes que volver a lanzar.");
                return;
            }
        }else{
            if (dados.sonDobles() && turno.getVecesTiradas()<3){
                mensaxe+="\nAo sair dobles, o xogador "+turno.getXogador().getNome()+" volve tirar.";
            }else if(dados.sonDobles() && turno.getVecesTiradas()==3){
                turno.getXogador().meterNoCarcere();
                turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCERE
                ReprTab.imprimirErro(mensaxe+" Saion triples e vas para o cárcere.");
                turno.setPodeLanzar(false);
                return;
            }else{
                turno.setPodeLanzar(false);
            }
        }
        System.out.println(mensaxe);
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

        System.out.println("O avatar "+turno.getXogador().getAvatar().getId() + " de tipo "+turno.getXogador().getAvatar().getTipo() +" cambia de modo "+turno.getXogador().getAvatar().getModoXogo());
    }
    /**
     * GETTERS AND SETTERS
     */

    /**
     * @return Devolve o taboeiro
     */
    @Override
    public Taboeiro getTaboeiro() {
        return taboeiro;
    }

    /**
     * @return Devolve os datos
     */
    @Override
    public Dados getDados() {
        return dados;
    }

    /**
     * @return Devolve o xogador banca
     */
    @Override
    public Xogador getBanca() {
        return banca;
    }

    /**
     * @return Devolve os xogadores
     */
    @Override
    public ArrayList<Xogador> getXogadores() {
        return xogadores;
    }

    /**
     * @return Devolve o Turno actual
     */
    @Override
    public Turno getTurno() {
        return turno;
    }

    /**
     * @return Devolve o número de xogadores
     */
    @Override
    public int getNumeroXogadores(){
        int nXogadores=0;
        for(Xogador x:xogadores)
            if(!x.enBancarrota())nXogadores++;

        return nXogadores;
    }

    /**
     * @return Compraba se se comezou a partida
     */
    @Override
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

    @Override
    public void moverModoNormal(int valorDados){
        System.out.println(turno.getXogador().getAvatar().moverEnBasico(this,valorDados));
    }

    /**
     * Move os avatares de en modo avanzado
     */
    private void moverModoAvanzado(int valorDados){
        turno.getXogador().getAvatar().moverEnAvanzado(this,valorDados);
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
                    if(p.getDono().equals(banca) && c.getTipoCasilla()== TipoCasilla.SOLAR){
                        p.setValor(p.getValor()*1.05f);
                    }
                }
            }
        }
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
            ReprTab.imprimirErro("Non podes realizar está acción porque estás no cárcere");
            return true;
        }
        return false;
    }
    /**
     * Este método comproba se se cumplen as condicións básicas de edificación.
     * @param c Casilla
     * @return True si se pode edificar, false se non
     */
    private boolean comprobarConstruir(Solar c, TipoEdificio tipo){
        if(c.getEstaHipotecada()){
            ReprTab.imprimirErro("Non podes construir nunha casilla hipotecada");
            return false;
        }
        if(c.getTipoCasilla()!=TipoCasilla.SOLAR){
            ReprTab.imprimirErro("Non podes edificar esta casilla");
            return false;
        }
        if(!c.getDono().equals(turno.getXogador())){
            ReprTab.imprimirErro("Esta casilla non é túa, non a podes edificar.");
            return false;
        }
        if(!c.podeseEdificarMais(tipo)){
            ReprTab.imprimirErro("Non podes construir máis edificios do tipo "+tipo+" en "+c.getNome());
            return false;
        }
        return true;
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

    @Override
    public boolean comprobarAvatarRepetido(Avatar avatar){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().equals(avatar))return true;
        }
        return false;
    }
    //BORRAR
    @Override
    public void mov(int i){
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        moverModoNormal(i);
    }
    @Override
    public void mova(int i){
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        moverModoAvanzado(i);
    }

}
