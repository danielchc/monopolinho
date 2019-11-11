package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasXogo;
import monopolinho.obxetos.*;
import monopolinho.tipos.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Xogo {

    private ArrayList<Xogador> xogadores;
    private Taboeiro taboeiro;
    //private Xogador turno;
    private Turno turno;
    private Dados dados;
    private Xogador banca;
    private Baralla cartasSorte;
    private Baralla cartasComunidade;
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
        cartasComunidade=new Baralla(TipoCarta.COMUNIDADE);
        cartasSorte=new Baralla(TipoCarta.SORTE);
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
    public void describirCasilla(String nome){
        Casilla c=this.taboeiro.buscarCasilla(nome);
        if(c!=null)System.out.println(c);
        else System.out.println("Non existe esta casilla");
    }

    /**
     * Este metodo imprime a info dun xogador.
     * @param nome Nome do xogador
     */
    public void describirXogador(String nome){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(nome.toLowerCase())){
                System.out.println(x.describir());
                return;
            }
        }
        System.out.println("Non se atopou o xogador "+nome);
    }

    /**
     * Este metodo imprime a info dun avatar.
     * @param avatarId Argumentos do usuario
     */
    public void describirAvatar(String avatarId){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getId().equals(avatarId)){
                System.out.println(x.getAvatar());
                return;
            }
        }
        System.out.println("Non se atopou o avatar "+ avatarId);
    }


    /**
     * Este metodo lista todos os edificios
     */
    public void listarEdificios(){
        for(ArrayList<Casilla> zona:taboeiro.getCasillas()){
            for(Casilla c:zona){
                if(c.getTipoCasilla()==TipoCasilla.SOLAR){
                    for (Edificio e:c.getEdificios()){
                        System.out.println(e.describirEdificio());
                    }
                }
            }
        }
    }

    /**
     * Este metodo lista todos os edificios dun grupo
     */
    public void listarEdificiosGrupo(String cmds){
        Grupo grupo=taboeiro.buscarGrupo(cmds);
        if(grupo==null){
            System.err.println("Ese nome de grupo non existe.");
            return;
        }
        for(Casilla c:grupo.getSolares()){
            for(Edificio e:c.getEdificios()){
                System.out.println(e.describirEdificio());
            }
        }
    }


    /**
     * Este metodo imprime todos os avatares.
     */
    public void listarAvatares(){
        for(Xogador x:this.xogadores)
            System.out.println(x.getAvatar());
    }

    /**
     * Este metodo imprime todos os xogadores.
     */
    public void listarXogadores(){
        for(Xogador x:this.xogadores)
            System.out.println(x.describir());
    }

    /**
     * Este metodo imprime todas as casillas en venta.
     */
    public void listarCasillaEnVenta(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas())
            for(Casilla c:zona)
                if(c.podeseComprar())
                    System.out.println(c);
    }

    /**
     * Este metodo acaba o turno e pasallo ao seguinte.
     */
    public void pasarTurno(){
        Xogador actual=turno.getXogador();
        if(turno.podeLanzar() && turno.getXogador().estadoXogador()!=EstadoXogador.BANCARROTA && turno.getXogador().getTurnosInvalidado()==0){
            System.err.println("Tes que tirar antes de pasar turno.");
            return;
        }
        if(turno.getXogador().estadoXogador() == EstadoXogador.TEN_DEBEDAS){
            System.err.println("Non podes pasar de turno ata que saldes as débedas ou te declares en bancarrota.");
            return;
        }
        int novoTurno;
        do{
            novoTurno=(this.xogadores.indexOf(turno.getXogador())+1)%this.xogadores.size();
            turno=new Turno(this.xogadores.get(novoTurno));
        }while(turno.getXogador().enBancarrota());

        if(comprobarFinPartida()){
            System.out.println("\nFin da partida!!");
            System.out.println("O xogador "+turno.getXogador().getNome()+ " gañou a partida");
            System.exit(0);
        }
        actual.restarTurnosInvalidado();
        System.out.println("Tiña o turno "+actual.getNome()+", agora teno "+turno.getXogador().getNome());
    }

    /**
     * Este metodo saca a un xogador da carcere.
     */
    public void salirCarcere(){
        if (!turno.getXogador().estaNoCarcere()){
            System.out.println("O xogador non está no cárcere");
            return;
        }
        if(turno.getXogador().quitarDinheiro(Valor.SAIR_CARCERE,TipoGasto.OTROS)){
            System.out.println(turno.getXogador().getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Pode lanzar os dados.");
            turno.getXogador().sairDoCarcere();
            taboeiro.engadirBote(Valor.SAIR_CARCERE);
        }else{
            System.err.println("Non tes o suficiente diñeiro para saír do cárcere");
        }
    }

    /**
     * Este metodo engade unha casilla as propiedades do xogador.
     * @param cmds Argumentos da función
     */
    public void comprarCasilla(String[] cmds){
        Xogador xogador=turno.getXogador();
        Casilla comprar=this.taboeiro.buscarCasilla(cmds[1]);
        if(turno.getVecesTiradas()==0){
            System.out.println("Non podes comprar unha casilla se non lanzaches os dados");
            return;
        }
        if(this.turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO){
            if(!this.turno.getHistorial().contains(comprar)){
                System.err.println("Non paseches por esta casilla neste turno");
                return;
            }
        }else{
            if(!this.turno.getPosicion().equals(comprar)){
                System.err.println("Non estás nesta casilla, non a podes comprar");
                return;
            }
        }

        if(!comprar.podeseComprar()){
            System.err.println("Este tipo de casilla non se pode comprar esta casilla");
            return;
        }
        if (!comprar.getDono().equals(banca)){
            System.err.println("Esta casilla pertence a " + turno.getPosicion().getDono().getNome()+". Non a podes comprar");
            return;
        }
        if(!xogador.quitarDinheiro(comprar.getValor(),TipoGasto.COMPRA)){
            System.err.println("Non tes suficiente diñeiro");
            return ;
        }
        if(xogador.getAvatar().getTipo()==TipoMovemento.COCHE && xogador.getAvatar().getModoXogo()==ModoXogo.AVANZADO && turno.getCompradasTurno()>=1){
            System.err.println("Non podes facer máis compras neste turno");
            return;
        }

        comprar.getDono().engadirDinheiro(comprar.getValor(),TipoGasto.VENTA);
        comprar.setDono(xogador);
        turno.aumentarCompras();
        System.out.println("O usuario "+xogador.getNome() +" comprou "+comprar.getNome() +" por "+comprar.getValor());
    }


    /**
     * Este metodo imprime o tableiro.
     */
    public void mostrarTaboeiro(){
        System.out.println(taboeiro);
    }

    /**
     * Mostra as estadisticas para un xogador
     * @param nome
     */
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
    public void mostrarEstadisticasXogo(){
        System.out.println(estadisticasXogo);
    }

    /**
     * Metodo que hipoteca unha casilla.
     * @param nome Nome casilla
     */
    public void hipotecarCasilla(String nome){
        Casilla c=this.taboeiro.buscarCasilla(nome);

        if(c!=null && c.podeseComprar() && c.getDono().equals(turno.getXogador()) && !c.getEstaHipotecada()){
            if(c.getEdificios().size()!=0){
                System.err.println(c.getNome()+" conten edificios, tes que vendelos antes de hipotecar.");
                return;
            }
            c.setEstaHipotecada(true);
            c.getDono().engadirDinheiro(c.getHipoteca(),TipoGasto.OTROS);
            System.out.println("\nAcabas de hipotecar a casilla "+c.getNome()+" e recibes "+c.getHipoteca());
        }
        else{
            System.err.println("Non se pode hipotecar esa casilla");
        }
    }


    /**
     * Metodo que deshipoteca unha casilla.
     * @param nome Nome casilla
     */
    public void deshipotecarCasilla(String nome){
        Casilla c=this.taboeiro.buscarCasilla(nome);


        if(c!=null && c.podeseComprar() && c.getDono().equals(this.turno.getXogador()) && c.getEstaHipotecada()){
            c.setEstaHipotecada(false);
            c.getDono().quitarDinheiro(c.getHipoteca(),TipoGasto.OTROS);
            System.out.println("\nAcabas de deshipotecar a casilla "+c.getNome()+". Pagas "+c.getHipoteca());
        }
        else{
            System.err.println("Non se pode deshipotecar esa casilla");
        }
    }

    /**
     * Metodo bancarrota.
     */
    public void declararBancarrota(){
        this.turno.getXogador().setEstadoXogador(EstadoXogador.BANCARROTA);

        for(Casilla c:this.turno.getXogador().getPropiedades()){
            c.setEstaHipotecada(false);
            c.setDono(this.banca);
        }
        System.out.println("\nO xogador "+this.turno.getXogador().getNome()+" declarouse en bancarrota.");
        pasarTurno();
    }

    /**
     * Este metodo permite construir
     * @param tipo Tipo de edificio
     */
    public void edificar(TipoEdificio tipo){
        Casilla actual=turno.getPosicion();
        if(tipo==null){
            System.err.println("Tipo de edificio incorrecto");
            return;
        }
        if(!comprobarConstruir(actual,tipo))return;
        switch (tipo){
            case CASA:
                if(!actual.getGrupo().tenTodoGrupo(turno.getXogador()) && actual.numeroVecesCaidas(turno.getXogador().getAvatar())<2){
                    System.err.println("Para edificar unha casa debes ter todo o grupo ou caer 2 veces en "+actual.getNome());
                    return;
                }
                break;
            case HOTEL:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<4){
                    System.err.println("Necesitas 4 casas en "+actual.getNome()+" para edificar un hotel");
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
                    System.err.println("Necesitas polo menos 2 casas e 1 hotel en "+actual.getNome()+" para edificar unha piscina.");
                    return;
                }
                break;
            case PISTA_DEPORTES:
                if(actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2){
                    System.err.println("Necesitas polo menos 2 hoteles en "+actual.getNome()+" para edificar unha pista de deportes.");
                    return;
                }
                break;
        }
        if(!turno.getXogador().quitarDinheiro(actual.getPrecioEdificio(tipo),TipoGasto.EDIFICAR)){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(tipo,turno.getXogador(),actual);
        actual.engadirEdificio(e);
        System.out.println("O usuario "+turno.getXogador().getNome() +" edificou en "+actual.getNome()+": "+tipo+". A súa fortuna redúcese en "+e.getPrecio());
    }


    /**
     * Vende un edificio
     * @param tipo tipo de edificio
     * @param casilla Casilla
     * @param numero Número de edificios a vender
     */
    public void venderEdificio(TipoEdificio tipo,String casilla,int numero){
        Casilla c=taboeiro.buscarCasilla(casilla);
        if(tipo==null){
            System.err.println("Tipo de edificio incorrecto.");
            return;
        }
        if(c==null){
            System.err.println("Esa casilla non existe.");
            return;
        }
        if(c.getTipoCasilla()!=TipoCasilla.SOLAR){
            System.err.println("Só podes vender edificios de solares.");
            return;
        }
        if(!c.getDono().equals(turno.getXogador())){
            System.err.println("Non podes vender edificios en "+c.getNome()+" porque non é túa.");
            return;
        }
        if(c.getEstaHipotecada()){
            System.err.println("Non podes vender edificios en "+c.getNome()+" porque está hipotecada");
            return;
        }

        int totalEdifs=c.getNumeroEdificiosTipo(tipo);
        float valor = totalEdifs*c.getPrecioEdificio(tipo)/2.0f;
        if(totalEdifs<numero){
            System.err.println("Non podes vender edificios de tipo "+tipo+". Solamente tes "+totalEdifs+" de tipo "+tipo+" por valor de "+valor);
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

        turno.getXogador().engadirDinheiro(valor,TipoGasto.OTROS);
        System.out.println("Vendiches "+totalEdifs+" edificios de tipo "+tipo+" e recibes "+valor);
    }

    /**
     * Mostra a información do turno actual
     */
    public void mostrarTurno() {
        if (turno!=null)System.out.println(turno.getXogador());
        else System.err.println("Non hai xogadores");
    }

    /**
     * Este metodo permite crear un xogador
     * @param nombre nombre nome do xogador
     * @param tipoMov tipoMov tipo de movemento do avatar do xogador
     * @return true si se creou o xogador ou false se o xogador xa existe
     */
    public boolean crearXogador(String nombre, TipoMovemento tipoMov){
        if(this.partidaComezada){
            System.err.println("Non se pode crear un xogador durante a partida");
            return false;
        }
        Xogador xogador=new Xogador(nombre, tipoMov);
        if(this.xogadores.contains(xogador)){
            return false; //Comproba se existe o usuario o método equal compara nomes!
        }
        this.xogadores.add(xogador);
        //FACER ESTO
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
    public void lanzarDados(){
        if(turno.getXogador().estadoXogador()==EstadoXogador.TEN_DEBEDAS){
            System.err.println("O xogador ten debedas, ten que declarase en bancarrota ou hipotecar propiedades");
            return;
        }
        if(!turno.podeLanzar()){
            System.err.println("O xogador xa lanzou os dados. Non se poden lanzar de novo");
            return;
        }
        if(turno.getXogador().getTurnosInvalidado()>0){
            System.err.println("O xogador ten que esperar "+turno.getXogador().getTurnosInvalidado()+" turno para volver a lanzar");
            return;
        }
        dados.lanzarDados();
        turno.aumentarVecesTiradas();   //1 vez tirada

        String mensaxe="\nSaiu o "+ ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado1()))+
                        " e o "+
                        ReprTab.colorear(Valor.ReprColor.ANSI_RED_BOLD,Integer.toString(dados.getDado2()));

        if(turno.getXogador().estaNoCarcere()){
            if(!dados.sonDobles()){
                System.err.println(mensaxe+". Tes que sacar dobles ou pagar para saír do cárcere.");
                turno.setPodeLanzar(false);
                return;
            }else{
                turno.getXogador().sairDoCarcere();
                turno.setVecesTiradas(0);
                System.out.println("Sacasches dados dobles, saíches do cárcere. Tes que volver a lanzar.");
                return;
            }
        }else{
            if (dados.sonDobles() && turno.getVecesTiradas()<3){
                mensaxe+="\nAo sair dobles, o xogador "+turno.getXogador().getNome()+" volve tirar.";
            }else if(dados.sonDobles() && turno.getVecesTiradas()==3){
                turno.getXogador().meterNoCarcere();
                turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCERE
                System.err.println(mensaxe+" Saion triples e vas para o cárcere.");
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

    public void cambiarModoXogo(){
        if(turno.getXogador().getAvatar().getTipo()!=TipoMovemento.PELOTA && turno.getXogador().getAvatar().getTipo()!=TipoMovemento.COCHE ){
            System.err.println("Este tipo de avatar non pode cambiar de modo\n");
            return;
        }
        if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.NORMAL)turno.getXogador().getAvatar().setModoXogo(ModoXogo.AVANZADO);
        else if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO)turno.getXogador().getAvatar().setModoXogo(ModoXogo.NORMAL);
        System.out.println("O avatar "+turno.getXogador().getAvatar().getId() + " de tipo "+turno.getXogador().getAvatar().getTipo() +" cambia de modo "+turno.getXogador().getAvatar().getModoXogo());
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
        return this.xogadores.size();
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
    public void comezarPartida() {
        this.partidaComezada = true;
    }

    /**
     * @return Obten as cartas de Sorte
     */
    public Baralla getCartasSorte() {
        return cartasSorte;
    }

    /**
     * @return Obten as cartas de Comunidade
     */
    public Baralla getCartasComunidade() {
        return cartasComunidade;
    }


    /**
     * Este metodo interpreta a accion a realizar segundo a casilla na que se cae.
     * @param valorDados Movemento no taboeiro respecto a casilla actual
     * @return Mensaxe da acción interpretada
     */

    public void moverModoNormal(int valorDados){
        String mensaxe="";
        Casilla current=turno.getPosicion();
        int nPos=current.getPosicionIndex()+valorDados;
        Casilla next=this.taboeiro.getCasilla(nPos);

        if(next.getTipoCasilla()!=TipoCasilla.IRCARCERE) {
            if(nPos>39) {
                mensaxe="O xogador "+turno.getXogador().getNome()+" recibe "+ Valor.VOLTA_COMPLETA + " por completar unha volta o taboeiro.\n";
                turno.getXogador().getAvatar().voltaTaboeiro();
                turno.getXogador().engadirDinheiro(Valor.VOLTA_COMPLETA,TipoGasto.VOLTA_COMPLETA);
            }
        }

        mensaxe+=next.interpretarCasilla(this,valorDados);
        mensaxe="O avatar "  +turno.getXogador().getAvatar().getId() +" avanza " +valorDados+" posiciones, desde "+current.getNome()+" ata " + next.getNome() + " \n"+mensaxe;
        System.out.println(mensaxe);

        if(deronTodosCatroVoltas()){
            aumentarPrecioCasillas();
            System.out.println("Os precios dos solares en venta aumentaron un 5%.");
        }

        if (next.haiQueCollerCarta()) preguntarCarta(next.getTipoCasilla());

    }

    /**
     * Move os avatares de en modo avanzado
     */
    private void moverModoAvanzado(int valorDados){
        Casilla next;
        int cPos=turno.getPosicion().getPosicionIndex();
        System.out.println("Encontraste en "+ turno.getPosicion().getNome());
        switch (turno.getXogador().getAvatar().getTipo()){
            case PELOTA:
                /**
                 * si el valor de los dados es mayor que 4, avanza tantas casillas como dicho valor; mientras
                 * que,  si  el  valor  es  menor  o  igual  que  4,  retrocede  el  número  de  casillas  correspondiente.  En
                 * cualquiera  de  los  dos  casos,  el  avatar  se  parará  en  las  casillas  por  las  que  va  pasando  y  cuyos
                 * valores son impares contados desde el número 4. Por ejemplo, si el valor del dado es 9, entonces
                 * el avatar avanzará hasta la casilla 5, de manera que si pertenece a otro jugador y es una casilla de
                 * propiedad deberá pagar el alquiler, y después avanzará hasta la casilla 7, que podrá comprar si no
                 * pertenece a ningún jugador, y finalmente a la casilla 9, que podrá comprar o deberá pagar alquiler
                 * si  no  pertenece  al  jugador.  Si  una  de  esas  casillas  es  Ir  a  Cárcel,  entonces  no  se  parará  en  las
                 * subsiguientes casillas.
                 */
                //Hai que facer tipo un historial de casillas polas que pasaches
                if(valorDados>4){
                    for(int i=5;i<=valorDados;i+=2){
                        next=this.taboeiro.getCasilla(cPos+i);
                        System.out.println("Avanzaches "+i+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(this,i));
                        if(next.getTipoCasilla()==TipoCasilla.IRCARCERE)return;
                        if(this.turno.getXogador().estadoXogador()==EstadoXogador.TEN_DEBEDAS)return;
                        if (next.haiQueCollerCarta())preguntarCarta(next.getTipoCasilla());
                    }
                }else{
                    for(int i=1;i<=valorDados;i+=2){
                        next=this.taboeiro.getCasilla(cPos-i);
                        System.out.println("Retrocedeches "+i+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(this,i));
                        if(next.getTipoCasilla()==TipoCasilla.IRCARCERE)return;
                        if(this.turno.getXogador().estadoXogador()==EstadoXogador.TEN_DEBEDAS)return;
                        if (next.haiQueCollerCarta())preguntarCarta(next.getTipoCasilla());
                    }
                }
                break;
            case COCHE:
                /**
                * Si el valor  de los  dados es mayor  que  4, avanza  tantas casillas como  dicho valor y  puede
                * seguir lanzando los dados tres veces más mientras el valor sea mayor que 4. Durante el turno solo
                * se puede realizar UNA SOLA COMPRA DE PROPIEDADES, servicios o transportes, aunque se podría hacer
                * en cualesquiera de los 4 intentos posibles. Sin embargo, se puede edificar cualquier tipo de edificio
                * en cualquier intento. Si el valor de los dados es menor que 4, el avatar retrocederá el número de
                * casillas correspondientes y además no puede volver a lanzar los dados en los siguientes dos turnos.
                */
                if(turno.getVecesTiradas()>4){
                    System.err.println("Non podes lanzar máis veces.Tes que pasar de turno");
                    turno.setPodeLanzar(false);
                    return;
                }
                if(valorDados>4){
                    turno.setPodeLanzar(true);
                    next=this.taboeiro.getCasilla(cPos+valorDados);
                    System.out.println("Avanzaches "+valorDados+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(this,valorDados));
                    if (next.haiQueCollerCarta())preguntarCarta(next.getTipoCasilla());
                    if(turno.getVecesTiradas()<4)System.out.println("Podes lanzar outra vez");
                }else{
                    turno.setPodeLanzar(false);
                    turno.getXogador().setTurnosInvalidado(2);
                    next=this.taboeiro.getCasilla(cPos-valorDados);
                    System.out.println("Retrocediches "+valorDados+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(this,valorDados));
                    if (next.haiQueCollerCarta())preguntarCarta(next.getTipoCasilla());
                    System.out.println("Non podes lanzar nos seguintes dous turnos.");
                }
                break;
        }
    }

    /**
     * Este método permite saber si todos os xogadores deron un número de voltas múltiplo de 4
     * @return true si deron todos un múltiplo de 4 voltas, false se non.
     */
    private boolean deronTodosCatroVoltas(){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getVoltasTaboeiro()==0 || x.getAvatar().getVoltasTaboeiro()%4 != 0){
                return false;
            }
        }
        return true;
    }


    /**
     * Este método aumenta o precio das casillas que están en venta nun 5%
     */
    private void aumentarPrecioCasillas(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas()){
            for(Casilla c:zona){
                if(c.getDono().equals(banca) && c.getTipoCasilla()== TipoCasilla.SOLAR){
                    c.setValor(c.getValor()*1.05f);
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
                c.setDono(banca);
        }
    }

    /**
     * Este método comproba se se cumplen as condicións básicas de edificación.
     * @param c Casilla
     * @return True si se pode edificar, false se non
     */
    private boolean comprobarConstruir(Casilla c,TipoEdificio tipo){
        if(c.getEstaHipotecada()){
            System.err.println("Non podes construir nunha casilla hipotecada");
            return false;
        }

        if(c.getTipoCasilla()!= TipoCasilla.SOLAR){
            System.err.println("Non podes edificar esta casilla");
            return false;
        }

        if(!c.getDono().equals(turno.getXogador())){
            System.err.println("Esta casilla non é túa, non a podes edificar.");
            return false;
        }

        if(!c.podeseEdificarMais(tipo)){
            return false;
        }
        return true;
    }

    /**
     * Interpreta as cartas de Comunidade e Sorte
     * @param tipoCasilla
     * @return
     */
    private void preguntarCarta(TipoCasilla tipoCasilla) {
        Baralla b=(tipoCasilla==TipoCasilla.SORTE)?this.cartasSorte:this.cartasComunidade;
        b.barallar();
        int nCarta=0;
        do{
            System.out.print("Escolle unha carta (1-6): ");
            nCarta=new Scanner(System.in).nextInt();
        }while(nCarta<1 || nCarta>6);
        System.out.println(b.getCarta(nCarta-1).interpretarCarta(this));
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
