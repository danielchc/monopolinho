package monopolinho.interfaz;

import monopolinho.axuda.Valor;
import monopolinho.obxetos.*;
import monopolinho.tipos.*;

import java.util.ArrayList;

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
    private boolean partidaComezada;
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
    }
    public Xogo(ArrayList<Xogador> xogadores){
        this();
        this.xogadores=xogadores;
    }


    /**
     * Este metodo permite construir unha casa nun solar.
     */
    public void edificarCasa(){
        Casilla actual=turno.getPosicion();
        if(actual.getTipoCasilla()!= TipoCasilla.SOLAR){
            System.err.println("Non podes edificar esta casilla");
            return;
        }

        if(!actual.getDono().equals(turno)){
            System.err.println("Esta casilla non é túa, non a podes edificar.");
            return;
        }

        if(!actual.podeseEdificarMais()){
            System.err.println("Alcanzache o numero maximo de edificios neste solar.");
            return;
        }

        if(!actual.getGrupo().tenTodoGrupo(turno)){  // FALTA COMPROBAR QUE SE CAERA POLO MENOS DUAS VECES NA CASILLA
            System.err.println("Para edificar unha casa debes ter todo o grupo ou caer 2 veces en "+actual.getNome());
            return;
        }

        if(!turno.quitarDinheiro(interpretarPrecioEdif(TipoEdificio.CASA,actual))){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.CASA,turno,interpretarPrecioEdif(TipoEdificio.CASA,actual),actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" unha casa. A súa fortuna redúcese en "+e.getPrecio());
    }


    /**
     * Este metodo permite construir un hotel nun solar.
     */
    public void edificarHotel(){
        Casilla actual=turno.getPosicion();
        if(actual.getTipoCasilla()!= TipoCasilla.SOLAR){
            System.err.println("Non podes edificar esta casilla");
            return;
        }

        if(!actual.getDono().equals(turno)){
            System.err.println("Esta casilla non é túa, non a podes edificar.");
            return;
        }

        if(!actual.podeseEdificarMais()){
            System.err.println("Alcanzache o numero maximo de edificios neste solar.");
            return;
        }

        if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<4){
            System.err.println("Necesitas 4 casas en "+actual.getNome()+" para edificar un hotel");
            return;
        }
        else{
            int casasEliminadas=0;
            for(int i=0;i<actual.getEdificios().size();i++){    //ta asi porque o equals de edificio ainda ta pocho
                if((actual.getEdificios().get(i).getTipoEdificio() == TipoEdificio.CASA) && (casasEliminadas<4)){ //elimino 4 casas e poño o edificio
                    //actual.getEdificios().remove(i);
                    actual.eliminarEdificio(actual.getEdificios().get(i)); //esto non elimna ben as casas
                    casasEliminadas++;
                }
            }
        }

        if(!turno.quitarDinheiro(interpretarPrecioEdif(TipoEdificio.HOTEL,actual))){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.HOTEL,turno,interpretarPrecioEdif(TipoEdificio.HOTEL,actual),actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" un hotel. A súa fortuna redúcese en "+e.getPrecio());
    }

    /**
     * Este metodo permite construir unha piscina nun solar.
     */
    public void edificarPiscina(){
        Casilla actual=turno.getPosicion();
        if(actual.getTipoCasilla()!= TipoCasilla.SOLAR){
            System.err.println("Non podes edificar esta casilla");
            return;
        }

        if(!actual.getDono().equals(turno)){
            System.err.println("Esta casilla non é túa, non a podes edificar.");
            return;
        }

        if(!actual.podeseEdificarMais()){
            System.err.println("Alcanzache o numero maximo de edificios neste solar.");
            return;
        }

        if(actual.getNumeroEdificiosTipo(TipoEdificio.CASA)<2 && actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<1){
            System.err.println("Necesitas polo menos 2 casas e 1 hotel en "+actual.getNome()+" para edificar unha piscina.");
            return;
        }

        if(!turno.quitarDinheiro(interpretarPrecioEdif(TipoEdificio.PISCINA,actual))){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.PISCINA,turno,interpretarPrecioEdif(TipoEdificio.PISCINA,actual),actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" unha piscina. A súa fortuna redúcese en "+e.getPrecio());
    }

    /**
     * Este metodo permite construir unha pista de deportes nun solar.
     */
    public void edificarPistaDeportes(){
        Casilla actual=turno.getPosicion();
        if(actual.getTipoCasilla()!= TipoCasilla.SOLAR){
            System.err.println("Non podes edificar esta casilla");
            return;
        }

        if(!actual.getDono().equals(turno)){
            System.err.println("Esta casilla non é túa, non a podes edificar.");
            return;
        }

        if(!actual.podeseEdificarMais()){
            System.err.println("Alcanzache o numero maximo de edificios neste solar.");
            return;
        }

        if(actual.getNumeroEdificiosTipo(TipoEdificio.HOTEL)<2){
            System.err.println("Necesitas polo menos 2 hoteles en "+actual.getNome()+" para edificar unha pista de deportes.");
            return;
        }

        if(!turno.quitarDinheiro(interpretarPrecioEdif(TipoEdificio.PISTA_DEPORTES,actual))){
            System.err.println("Non tes suficiente diñeiro para edificar");
            return ;
        }
        Edificio e=new Edificio(TipoEdificio.PISTA_DEPORTES,turno,interpretarPrecioEdif(TipoEdificio.PISTA_DEPORTES,actual),actual);
        actual.engadirEdificio(e);

        System.out.println("O usuario "+turno.getNome() +" edificou en "+actual.getNome()+" unha pista de deportes. A súa fortuna redúcese en "+e.getPrecio());
    }



    /**
     * Este metodo permite saber o precio según o tipo de edificio
     * @param tipo Input do usuario
     * @return Precio do edificio.
     */
    private float interpretarPrecioEdif(TipoEdificio tipo,Casilla c){
        float precio=0;
        switch (tipo){
            case HOTEL:
                precio=Valor.FACTOR_VALOR_HOTEL*c.getValor();
                break;
            case CASA:
                precio=Valor.FACTOR_VALOR_CASA*c.getValor();
                break;
            case PISCINA:
                precio=Valor.FACTOR_VALOR_PISCINA*c.getValor();
                break;
            case PISTA_DEPORTES:
                precio=Valor.FACTOR_VALOR_PISTADEPORTES*c.getValor();
                break;
        }
        return precio;
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
                return;
            }else{
                turno.setTurnosNaCarcel(0);
                turno.setVecesTiradas(0);
                System.out.println("Sacasches dados dobles. Tes que volver a lanzar.");
                return;
            }
        }else{
            if (dados.sonDobles() && turno.getVecesTiradas()<3){
                mensaxe+="\nAo sair dobles, o xogador "+turno.getNome()+" volve tirar.";
            }
            else if(dados.sonDobles() && turno.getVecesTiradas()==3){
                turno.setTurnosNaCarcel(3);
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
     * Este metodo interpreta a accion a realizar segundo a casilla na que se cae.
     * @param current Casilla actual
     * @param valorDados Movemento no taboeiro respecto a casilla actual
     * @return Mensaxe da acción interpretada
     */
    public void interpretarAccion(Casilla current,int valorDados){
        String mensaxe="";

        Casilla next=this.taboeiro.getCasilla((current.getPosicionIndex()+valorDados)%40);

        //Mover avatar
        if(next.getTipoCasilla()==TipoCasilla.IRCARCEL) {
            mensaxe="O avatar colocase na casilla CARCEL(TEIXEIRO)";
            turno.setTurnosNaCarcel(3);
            turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCEL
        }
        else{
            if((current.getPosicionIndex()+valorDados)>39) {
                mensaxe="O xogador "+turno.getNome()+" recibe "+ Valor.VOLTA_COMPLETA + " por completar unha volta o taboeiro.\n";
                turno.getAvatar().voltaTaboeiro();
                turno.engadirDinheiro(Valor.VOLTA_COMPLETA);
            }
            switch (next.getTipoCasilla()){
                case IMPOSTO:
                    mensaxe+=interpretarIMPOSTO(turno,next);
                    break;
                case SORTE:
                    break;
                case PARKING:
                    mensaxe+=interpretarPARKING(turno,next);
                    break;
                case CARCEL:
                    mensaxe+="So de visita...";
                    break;
                case SERVIZO:
                    mensaxe+=interpretarSERVIZO(turno,next,valorDados);
                    break;
                case TRANSPORTE:
                    mensaxe+=interpretarTRANSPORTE(turno,next);
                    break;
                case SOLAR:
                    mensaxe+=interpretarSOLAR(turno,next);
                    break;
            }
            turno.setPosicion(next);
        }
        mensaxe="O avatar "  +turno.getAvatar().getId() +" avanza " +valorDados+" posiciones, desde "+current.getNome()+" ata " + next.getNome() + " \n"+mensaxe;
        System.out.println(mensaxe);
        if(deronTodosCatroVoltas()){
            aumentarPrecioCasillas();
            System.out.println("Os precios dos solares en venta aumentaron un 5%.");
        }
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

    ////FUNCIÓNS INTERPRETAR SEGUN TIPO CASILLA

    /**
     * Este método realiza a acción correspondente a caer nun imposto
     * @param turno Xogador que ten turno
     * @param next Casilla na que cae
     * @return Mensaxe coa accion realizada
     */
    private String interpretarIMPOSTO(Xogador turno,Casilla next){
        String mensaxe="O xogador "+ turno.getNome() +  " ten que pagar "+next.getImposto() + " por caer en "+next.getNome();
        if(turno.quitarDinheiro(next.getImposto())){
            taboeiro.engadirBote(next.getImposto());
        }else{
            System.err.println("O xogador "+turno.getNome()+" non ten suficiente dinheiro para pagar o imposto");
            //E QUE PASA SE NON TEN CARTOS????????????????????
        }
        return mensaxe;
    }

    /**
     * Este método realiza a acción correspondente a caer en PARKING
     * @param turno Xogador que ten turno
     * @param next Casilla na que cae
     * @return Mensaxe coa accion realizada
     */
    private String interpretarPARKING(Xogador turno,Casilla next){
        turno.engadirDinheiro(this.taboeiro.getBote());
        String mensaxe="O xogador "+ turno.getNome() + " recibe "+this.taboeiro.getBote()+", do bote.";
        taboeiro.setBote(0);
        return mensaxe;
    }

    /**
     * Este método realiza a acción correspondente a caer nun servicio
     * @param turno Xogador que ten turno
     * @param next Casilla na que cae
     * @param valorDados Valor que saiu nos dados
     * @return Mensaxe coa accion realizada
     */
    private String interpretarSERVIZO(Xogador turno,Casilla next,int valorDados){
        String mensaxe="";
        if((!next.getDono().equals(turno)) && (!next.getDono().equals(banca))){
            float aPagar=valorDados*next.getUsoServizo();
            if(turno.numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 1){
                aPagar*=4.0f;
            }else if(turno.numTipoCasillaPosesion(TipoCasilla.SERVIZO) == 2){
                aPagar*=10.0f;
            }
            if(turno.quitarDinheiro(aPagar)){
                next.getDono().engadirDinheiro(aPagar);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome() +" por usar "+next.getNome();
            }else{
                //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                return mensaxe;
            }
        }
        return mensaxe;
    }

    /**
     * Este método realiza a acción correspondente a caer nun transporte
     * @param turno Xogador que ten turno
     * @param next Casilla na que cae
     * @return Mensaxe coa accion realizada
     */
    private String interpretarTRANSPORTE(Xogador turno,Casilla next){
        String mensaxe="";
        if((!next.getDono().equals(turno)) && (!next.getDono().equals(banca))){
            float aPagar=0;
            aPagar=next.getUsoServizo()*(next.getDono().numTipoCasillaPosesion(TipoCasilla.TRANSPORTE)/4.0f);
            if(turno.quitarDinheiro(aPagar)){
                next.getDono().engadirDinheiro(aPagar);
                mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome() +" por usar "+next.getNome();
            }else{
                //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                return mensaxe;
            }
        }
        return mensaxe;
    }

    /**
     * Este método realiza a acción correspondente a caer nun solar
     * @param turno Xogador que ten turno
     * @param next Casilla na que cae
     * @return Mensaxe coa accion realizada
     */
    private String interpretarSOLAR(Xogador turno,Casilla next){
        String mensaxe="";
        if(next.getEstaHipotecada()){
            mensaxe+="Caiche na casila "+next.getNome()+", pero está hipotecada, non pagas.";
            return mensaxe;
        }else{
            if((!next.getDono().equals(turno))&&(!next.getDono().equals(banca))){
                float aPagar;
                if(next.getGrupo().tenTodoGrupo(next.getDono())){
                    aPagar=next.getAlquiler()*Valor.FACTOR_PAGO_ALQUILER;
                }else{
                    aPagar=next.getAlquiler();
                }

                if(turno.quitarDinheiro(aPagar)){
                    next.getDono().engadirDinheiro(aPagar);
                    mensaxe+="Tes que pagarlle "+aPagar+" a "+next.getDono().getNome();
                    return mensaxe;
                }else{
                    //System.err.println("Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.");
                    mensaxe+="Non tes suficiente diñeiro para pagar o alquiler, teste que declarar en bancarrota ou hipotecar unha propiedade.";
                    return mensaxe;
                }
            }
        }
        return mensaxe;
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
        if(turno.getVecesTiradas()==0){
            System.err.println("Tes que tirar unha vez antes de pasar turno.");
            return;
        }
        int novoTurno;
        do{
            novoTurno=(this.xogadores.indexOf(turno)+1)%this.xogadores.size();
            turno=this.xogadores.get(novoTurno);
        }while(turno.isEnBancarrota());
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
        if(turno.quitarDinheiro(Valor.SAIR_CARCERE)){
            System.out.println(turno.getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Pode lanzar os dados.");
            turno.setTurnosNaCarcel(0);
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
        if(!turno.quitarDinheiro(comprar.getValor())){
            System.err.println("Non tes suficiente diñeiro");
            return ;
        }

        comprar.getDono().engadirDinheiro(comprar.getValor());
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
     * Metodo que hipoteca unha casilla.
     * @param nome Nome casilla
     */
    public void hipotecarCasilla(String nome){
        Casilla c=this.taboeiro.buscarCasilla(nome);
        if(c!=null && c.podeseComprar() && c.getDono().equals(this.turno)){
            c.setEstaHipotecada(true);
            c.getDono().engadirDinheiro(c.getHipoteca());
            System.out.println("\nAcabas de hipotecar a casilla "+c.getNome());
        }
        else System.err.println("Non se pode hipotecar esa casilla");
    }

    /**
     * Metodo bancarrota.
     */
    public void declararBancarrota(){
        this.turno.setEnBancarrota(true);
        for(Casilla c:this.turno.getPropiedades()){
            c.setDono(this.banca);
        }
        System.err.println("\nO xogador "+this.turno.getNome()+" declarouse en bancarrota.");
        pasarTurno();
    }

    /**
     * Mostra a información do turno actual
     */
    public void mostrarTurno() {
        if (turno!=null)System.out.println(turno);
        else System.err.println("Non hai xogadores");
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

    public Taboeiro getTaboeiro() {
        return taboeiro;
    }

    public Dados getDados() {
        return dados;
    }

    public Xogador getBanca() {
        return banca;
    }

    public ArrayList<Xogador> getXogadores() {
        return xogadores;
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

    //BORRAR
    public void mov(int i){
        interpretarAccion(turno.getPosicion(),i);
        turno.aumentarVecesTiradas();
    }



}
