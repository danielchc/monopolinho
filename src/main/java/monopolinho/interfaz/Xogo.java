package monopolinho.interfaz;

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
    private Xogador turno;
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
        Xogador actual=turno;
        if(turno.getVecesTiradas()==0 && turno.estadoXogador()!=EstadoXogador.BANCARROTA){
            System.err.println("Tes que tirar unha vez antes de pasar turno.");
            return;
        }
        int novoTurno;
        do{
            novoTurno=(this.xogadores.indexOf(turno)+1)%this.xogadores.size();
            turno=this.xogadores.get(novoTurno);
        }while(turno.enBancarrota());
        if(comprobarFinPartida()){
            System.out.println("\nFin da partida!!");
            System.out.println("O xogador "+turno.getNome()+ " gañou a partida");
            System.exit(0);
        }
        actual.setVecesTiradas(0);
        actual.setPodeLanzar(true);
        System.out.println("Tiña o turno "+actual.getNome()+", agora teno "+turno.getNome());
    }

    /**
     * Este metodo saca a un xogador da carcere.
     */
    public void salirCarcel(){
        if (!turno.estaNaCarcel()){
            System.out.println("O xogador non está no cárcere");
            return;
        }
        if(turno.quitarDinheiro(Valor.SAIR_CARCERE,TipoGasto.OTROS)){
            System.out.println(turno.getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Pode lanzar os dados.");
            turno.sairDoCarcere();
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
        Casilla comprar=turno.getPosicion();
        if(!comprar.equals(this.taboeiro.buscarCasilla(cmds[1]))){
            System.err.println("Non te atopas nesta casilla");
            return;
        }
        if(!comprar.podeseComprar()){
            System.err.println("Non podes comprar esta casilla");
            return;
        }
        if (!comprar.getDono().equals(banca)){
            System.err.println("Esta casilla pertence a " + turno.getPosicion().getDono().getNome()+". Non a podes comprar");
            return;
        }
        if(!turno.quitarDinheiro(comprar.getValor(),TipoGasto.COMPRA)){
            System.err.println("Non tes suficiente diñeiro");
            return ;
        }

        comprar.getDono().engadirDinheiro(comprar.getValor(),TipoGasto.VENTA);
        comprar.setDono(turno);
        System.out.println("O usuario "+turno.getNome() +" comprou "+comprar.getNome() +" por "+comprar.getValor());
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

        if(c.getEdificios().size()!=0){
            System.err.println(c.getNome()+" conten edificios, tes que vendelos antes de hipotecar.");
            return;
        }

        if(c!=null && c.podeseComprar() && c.getDono().equals(this.turno) && !c.getEstaHipotecada()){
            c.setEstaHipotecada(true);
            c.getDono().engadirDinheiro(c.getHipoteca(),TipoGasto.OTROS);
            System.out.println("\nAcabas de hipotecar a casilla "+c.getNome());
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


        if(c!=null && c.podeseComprar() && c.getDono().equals(this.turno) && c.getEstaHipotecada()){
            c.setEstaHipotecada(false);
            c.getDono().quitarDinheiro(c.getHipoteca(),TipoGasto.OTROS);
            System.out.println("\nAcabas de deshipotecar a casilla "+c.getNome()+". Recibes "+c.getHipoteca());
        }
        else{
            System.err.println("Non se pode deshipotecar esa casilla");
        }
    }

    /**
     * Metodo bancarrota.
     */
    public void declararBancarrota(){
        this.turno.setEstadoXogador(EstadoXogador.BANCARROTA);
        for(Casilla c:this.turno.getPropiedades()){
            c.setDono(this.banca);
        }
        System.out.println("\nO xogador "+this.turno.getNome()+" declarouse en bancarrota.");
        pasarTurno();
    }

    /**
     * Este metodo permite construir unha casa nun solar.
     */
    public void edificarCasa(){
        Casilla actual=turno.getPosicion();
        if(!comprobarConstruir(actual,TipoEdificio.CASA)){
            return;
        }

        if(!actual.getGrupo().tenTodoGrupo(turno) && actual.numeroVecesCaidas(turno.getAvatar())<2){
            System.err.println("Para edificar unha casa debes ter todo o grupo ou caer 2 veces en "+actual.getNome());
            return;
        }


        if(!turno.quitarDinheiro(actual.getPrecioEdificio(TipoEdificio.CASA),TipoGasto.EDIFICAR)){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.CASA,turno,actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" unha casa. A súa fortuna redúcese en "+e.getPrecio());
    }


    /**
     * Este metodo permite construir un hotel nun solar.
     */
    public void edificarHotel(){
        Casilla actual=turno.getPosicion();

        if(!comprobarConstruir(actual,TipoEdificio.HOTEL)){
            return;
        }

        if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<4){
            System.err.println("Necesitas 4 casas en "+actual.getNome()+" para edificar un hotel");
            return;
        }
        else{
            int casasEliminadas=0;
            ArrayList<Edificio> aBorrar=new ArrayList<>();
            for(Edificio e:actual.getEdificios()){
                if((e.getTipoEdificio() == TipoEdificio.CASA) && (casasEliminadas<4)){
                    aBorrar.add(e);
                    casasEliminadas++;
                }
            }
            for(Edificio e:aBorrar){
                actual.eliminarEdificio(e);
            }
        }

        if(!turno.quitarDinheiro(actual.getPrecioEdificio(TipoEdificio.HOTEL),TipoGasto.EDIFICAR)){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.HOTEL,turno,actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" un hotel. A súa fortuna redúcese en "+e.getPrecio());
    }

    /**
     * Este metodo permite construir unha piscina nun solar.
     */
    public void edificarPiscina(){
        Casilla actual=turno.getPosicion();

        if(!comprobarConstruir(actual,TipoEdificio.PISCINA)){
            return;
        }

        if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<2 || actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<1){
            System.err.println("Necesitas polo menos 2 casas e 1 hotel en "+actual.getNome()+" para edificar unha piscina.");
            return;
        }

        if(!turno.quitarDinheiro(actual.getPrecioEdificio(TipoEdificio.PISCINA),TipoGasto.EDIFICAR)){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.PISCINA,turno,actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" unha piscina. A súa fortuna redúcese en "+e.getPrecio());
    }

    /**
     * Este metodo permite construir unha pista de deportes nun solar.
     */
    public void edificarPistaDeportes(){
        Casilla actual=turno.getPosicion();

        if(!comprobarConstruir(actual,TipoEdificio.PISTA_DEPORTES)){
            return;
        }

        if(actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2){
            System.err.println("Necesitas polo menos 2 hoteles en "+actual.getNome()+" para edificar unha pista de deportes.");
            return;
        }

        if(!turno.quitarDinheiro(actual.getPrecioEdificio(TipoEdificio.PISTA_DEPORTES),TipoGasto.EDIFICAR)){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.PISTA_DEPORTES,turno,actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" unha pista de deportes. A súa fortuna redúcese en "+e.getPrecio());
    }


    /**
     * Vende un edificio
     * @param tipoEdif tipo de edificio
     * @param casilla Casilla
     * @param numero Número de edificios a vender
     */
    public void venderEdificio(String tipoEdif,String casilla,int numero){
        TipoEdificio tipo=interpretarEdificio(tipoEdif);
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
        if(!c.getDono().equals(turno)){
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

        turno.engadirDinheiro(valor,TipoGasto.OTROS);
        System.out.println("Vendiches "+totalEdifs+" edificios de tipo "+tipo+" e recibes "+valor);
    }

    /**
     * Mostra a información do turno actual
     */
    public void mostrarTurno() {
        if (turno!=null)System.out.println(turno);
        else System.err.println("Non hai xogadores");
    }

    /**
     * Este metodo interpreta a accion a realizar segundo a casilla na que se cae.
     * @param current Casilla actual
     * @param valorDados Movemento no taboeiro respecto a casilla actual
     * @return Mensaxe da acción interpretada
     */
    public void interpretarAccion(Casilla current,int valorDados){
        String mensaxe="";
        int nPos=Math.floorMod((current.getPosicionIndex()+valorDados), 40);
        Casilla next=this.taboeiro.getCasilla(nPos);
        if(next.getTipoCasilla()!=TipoCasilla.IRCARCEL) {
            if((current.getPosicionIndex()+valorDados)>39) {
                mensaxe="O xogador "+turno.getNome()+" recibe "+ Valor.VOLTA_COMPLETA + " por completar unha volta o taboeiro.\n";
                turno.getAvatar().voltaTaboeiro();
                turno.engadirDinheiro(Valor.VOLTA_COMPLETA,TipoGasto.VOLTA_COMPLETA);
            }
        }

        mensaxe+=next.interpretarCasilla(this,valorDados);

        mensaxe="O avatar "  +turno.getAvatar().getId() +" avanza " +valorDados+" posiciones, desde "+current.getNome()+" ata " + next.getNome() + " \n"+mensaxe;
        System.out.println(mensaxe);

        if(deronTodosCatroVoltas()){
            aumentarPrecioCasillas();
            System.out.println("Os precios dos solares en venta aumentaron un 5%.");
        }

        if (next.getTipoCasilla()==TipoCasilla.SORTE || next.getTipoCasilla()==TipoCasilla.COMUNIDADE) {
            System.out.println(preguntarCarta(next.getTipoCasilla()));
        }

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
        if(this.xogadores.indexOf(xogador)==0)this.turno=this.xogadores.get(0);
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
        if(turno.estadoXogador()==EstadoXogador.TEN_DEBEDAS){
            System.err.println("O xogador ten debedas, ten que declarase en bancarrota ou hipotecar propiedades");
            return;
        }
        if(!turno.podeLanzar()){
            System.err.println("O xogador xa lanzou os dados. Non se poden lanzar de novo");
            return;
        }
        dados.lanzarDados();
        turno.aumentarVecesTiradas();   //1 vez tirada

        String mensaxe="\nSaiu o "+dados.getDado1()+" e o "+dados.getDado2();

        if(turno.estaNaCarcel()){
            if(!dados.sonDobles()){
                System.err.println(mensaxe+". Tes que sacar dobles ou pagar para saír do cárcere.");
                turno.setPodeLanzar(false);
                return;
            }else{
                turno.sairDoCarcere();
                turno.setVecesTiradas(0);
                System.out.println("Sacasches dados dobles. Tes que volver a lanzar.");
                return;
            }
        }else{
            if (dados.sonDobles() && turno.getVecesTiradas()<3){
                mensaxe+="\nAo sair dobles, o xogador "+turno.getNome()+" volve tirar.";
            }
            else if(dados.sonDobles() && turno.getVecesTiradas()==3){
                turno.meterNoCarcere();
                turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCEL
                System.err.println("Saion triples e vas para o cárcere.");
                turno.setPodeLanzar(false);
                return;
            }
            else{
                turno.setPodeLanzar(false);
            }
        }
        System.out.println(mensaxe);
        interpretarAccion(turno.getPosicion(),dados.valorLanzar());
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
     * @return Devolve o xogador actual
     */
    public Xogador getTurno() {
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
     * Funcions privadas
     */

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

        if(!c.getDono().equals(turno)){
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
    private String preguntarCarta(TipoCasilla tipoCasilla) {
        Baralla b=(tipoCasilla==TipoCasilla.SORTE)?this.cartasSorte:this.cartasComunidade;
        b.barallar();
        int nCarta=0;
        do{
            System.out.print("Elixe unha carta (1-6): ");
            nCarta=new Scanner(System.in).nextInt();
        }while(nCarta<1 || nCarta>6);
        return b.getCarta(nCarta-1).interpretarCarta(this);
    }

    /**
     * Convirte un String a un TipoEdificio
     * @param tipo String edificio a convertir
     * @return Tipo de Edificio
     */
    private TipoEdificio interpretarEdificio(String tipo){
        switch (tipo){
            case "casa":
                return TipoEdificio.CASA;
            case "hotel":
                return TipoEdificio.HOTEL;
            case "piscina":
                return TipoEdificio.PISCINA;
            case "pista":
                return TipoEdificio.PISTA_DEPORTES;
        }
        return null;
    }

    private boolean comprobarFinPartida(){
        int xogadoresEnBancarrota=0;
        for(Xogador x:this.xogadores){
            if(x.estadoXogador()==EstadoXogador.BANCARROTA)xogadoresEnBancarrota++;
        }
        return ((this.xogadores.size()-1)==xogadoresEnBancarrota);
    }
    //BORRAR
    public void mov(int i){
        interpretarAccion(turno.getPosicion(),i);
        turno.aumentarVecesTiradas();
    }
}
