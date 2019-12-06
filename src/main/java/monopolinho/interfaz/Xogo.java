package monopolinho.interfaz;

import monopolinho.axuda.Auxiliar;
import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.estadisticas.EstadisticasXogo;
import monopolinho.obxetos.*;
import monopolinho.obxetos.accions.*;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.edificios.Edificio;
import monopolinho.obxetos.excepcions.*;
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
        comprobarCarcere();
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
        if(!xogador.quitarDinheiro(comprar.valor(), TipoTransaccion.COMPRA)){
            throw new MonopolinhoGeneralException("Non tes suficiente diñeiro");
        }
        if(xogador.getAvatar().getTipo()==TipoMovemento.COCHE && xogador.getAvatar().getModoXogo()==ModoXogo.AVANZADO && turno.getCompradasTurno()>=1){
            throw new MonopolinhoGeneralException("Non podes facer máis compras neste turno");
        }

        comprar.comprar(this.turno.getXogador());
        turno.engadirAccion(new AccionComprar(comprar));
        consola.imprimir("O usuario "+xogador.getNome() +" comprou "+comprar.getNome() +" por "+comprar.valor());
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
        comprobarCarcere();
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade c;

        if(target==null)
            throw new MonopolinhoCasillaInexistente(nome);
        if(!(target instanceof Propiedade))
            throw new MonopolinhoGeneralException("Non se pode hipotecar este tipo de casilla");
        c=(Propiedade)target;
        if(!c.pertenceXogador(turno.getXogador()))
            throw new MonopolinhoGeneralException("Non eres dono de esta casilla");
        if(c.getEstaHipotecada())
            throw new MonopolinhoGeneralException("Esta casilla xa está hipotecada");
        if((c instanceof Solar) && (((Solar)c).getEdificios().size()!=0))
            throw new MonopolinhoGeneralException(c.getNome()+" conten edificios, tes que vendelos antes de hipotecar.");


        c.setEstaHipotecada(true);
        c.getDono().engadirDinheiro(c.getHipoteca(), TipoTransaccion.OTROS);
        turno.engadirAccion(new AccionHipotecar(c));
        consola.imprimir("Acabas de hipotecar a casilla "+c.getNome()+" e recibes "+c.getHipoteca());

    }


    /**
     * Metodo que deshipoteca unha casilla.
     * @param nome Nome casilla
     */
    @Override
    public void deshipotecarCasilla(String nome) throws MonopolinhoException {
        comprobarCarcere();
        Casilla target=this.taboeiro.buscarCasilla(nome);
        Propiedade c;

        if(target==null)
            throw new MonopolinhoCasillaInexistente(nome);
        if(!(target instanceof Propiedade))
            throw new MonopolinhoGeneralException("Non se pode deshipotecar unha casilla que non sexa unha propiedade");
        c=(Propiedade)target;
        if(!c.pertenceXogador(turno.getXogador()))
            throw new MonopolinhoGeneralException("Non eres dono de esta casilla");
        if(!c.getEstaHipotecada())
            throw new MonopolinhoGeneralException("Esta casilla non está hipotecada");
        if(!c.getDono().quitarDinheiro(c.getHipoteca()*1.1f, TipoTransaccion.OTROS))
            throw new MonopolinhoGeneralException("Non tes o suficiente diñeiro para deshipotecar a propiedade");


        c.setEstaHipotecada(false);
        turno.engadirAccion(new AccionDeshipotecar(c));
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
        consola.imprimirAuto("O xogador "+this.turno.getXogador().getNome()+" declarouse en bancarrota.");
        pasarTurno();
    }

    /**
     * Este metodo permite construir
     * @param tipo Tipo de edificio
     */
    @Override
    public void edificar(TipoEdificio tipo) throws MonopolinhoException {
        comprobarCarcere();
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
        if(!turno.getXogador().quitarDinheiro(actual.getPrecioEdificio(tipo), TipoTransaccion.EDIFICAR))
            throw new MonopolinhoGeneralException("Non tes suficiente diñeiro para edificar");

        actual.edificar(tipo,turno);
        turno.engadirAccion(new AccionEdificar(actual,tipo));
        consola.imprimir("O usuario "+turno.getXogador().getNome() +" edificou en "+actual.getNome()+": "+tipo+". A súa fortuna redúcese en "+actual.getPrecioEdificio(tipo));
    }


    /**
     * Vende un edificio
     * @param tipo tipo de edificio
     * @param casilla Casilla
     * @param numero Número de edificios a vender
     */
    @Override
    public void venderEdificio(TipoEdificio tipo, String casilla, int numero) throws MonopolinhoException {
        comprobarCarcere();
        Casilla target=taboeiro.buscarCasilla(casilla);
        Solar c;

        if(target==null)
            throw new MonopolinhoCasillaInexistente(casilla);
        if(!(target instanceof Solar))
            throw new MonopolinhoGeneralException("Non se pode vender edificios dunha casilla que non sexa un SOLAR");
        c=(Solar)target;
        if(tipo==null)
            throw new MonopolinhoGeneralException("Tipo de edificio incorrecto.");
        if(!c.pertenceXogador(turno.getXogador()))
            throw new MonopolinhoGeneralException("Non podes vender edificios en "+c.getNome()+" porque non é túa.");
        if(c.getEstaHipotecada())
            throw new MonopolinhoGeneralException("Non podes vender edificios en "+c.getNome()+" porque está hipotecada");
        if(c.getNumeroEdificiosTipo(tipo)<numero)
            throw new MonopolinhoGeneralException("Non podes vender edificios de tipo "+tipo+". Solamente tes "+c.getNumeroEdificiosTipo(tipo)+" de tipo "+tipo);

        float valor = numero*c.getPrecioEdificio(tipo)/2.0f;
        c.eliminarNumeroEdificios(tipo,numero);
        turno.getXogador().engadirDinheiro(valor, TipoTransaccion.OTROS);
        turno.engadirAccion(new AccionVender(c,tipo,numero));
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
                consola.imprimirNegrita("Estás no CÁRCERE");
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
                turno.engadirAccion(new AccionIrCarcere());
                turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCERE
                turno.setPodeLanzar(false);
                throw new MonopolinhoGeneralException(mensaxe+" Saion triples e vas para o cárcere.");
            }else{
                turno.setPodeLanzar(false);
            }
        }
        consola.imprimir(mensaxe);
        turno.engadirAccion(new AccionLanzarDados(turno.getPosicion()));
        if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.NORMAL){
            moverModoNormal(dados.valorLanzar());
        }else{
            moverModoAvanzado(dados.valorLanzar());
        }
    }

    /**
     * Metodo que permite o xogador actual cambiar o modo de xogo
     */
    @Override
    public void cambiarModoXogo(){
        if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.NORMAL)
            turno.getXogador().getAvatar().setModoXogo(ModoXogo.AVANZADO);
        else if(turno.getXogador().getAvatar().getModoXogo()==ModoXogo.AVANZADO)
            turno.getXogador().getAvatar().setModoXogo(ModoXogo.NORMAL);

        consola.imprimir("O avatar "+turno.getXogador().getAvatar().getId() + " de tipo "+
                turno.getXogador().getAvatar().getTipo() +" cambia de modo "+
                turno.getXogador().getAvatar().getModoXogo()
        );
    }

    /**
     * Este método permite propoñer un trato.
     */
    @Override
    public void proponerTrato(String[] comandos) throws MonopolinhoGeneralException { //trato nombre: cambiar (solar1, solar2 y noalquiler(solar3, 5))
        String texto="".join(" ",comandos);
        String[] cmds=texto.split(":");
        if (cmds.length!=2) return;
        if(cmds[0].split(" ").length!=2){
            throw new MonopolinhoGeneralException("Non podes ofrecer un trato a mais de un xogador ao mesmo tempo");
        }
        String xogadorDestino=cmds[0].split(" ")[1];
        String opcionsTrato=cmds[1].replace(" ","");
        String[] partesTrato=opcionsTrato.split(",",2);

        Xogador emisor=this.turno.getXogador();
        Xogador destinatario=buscarXogadorPorNome(xogadorDestino);
        if(destinatario==null){
            throw new MonopolinhoGeneralException("Non podes propoñer ese trato porque " + xogadorDestino + " non é un xogador da partida.");
        }
        if(emisor.equals(destinatario)){
            throw new MonopolinhoGeneralException("Non podes propoñer un trato a ti mismo.");
        }

        if(partesTrato.length==1){ //esto vale pa poder facer trato jose: cambiar (avatares)
            String segmentoLimpo=partesTrato[0].replace("("," ").replace(")"," ").replace(","," ").replace("-"," ");
            String[] comUnico=segmentoLimpo.split(" ");
            if(comUnico[1].equals("avatares")){
                Trato tratoAvatar=new Trato(emisor,destinatario,true);
                consola.imprimir(tratoAvatar);
                destinatario.engadirTrato(tratoAvatar);
                return;
            }
            else{
                throw new MonopolinhoGeneralException("Sintaxe: trato <nome>: cambiar (avatares)");
            }
        }
        String parteOferta=partesTrato[0];
        String parteDemanda=partesTrato[1];

        Trato trato=new Trato(emisor,destinatario);

        for(String s:parteOferta.split("y")){
            String segmentoLimpo=s.replace("("," ").replace(")"," ").replace(","," ").replace("-"," ");
            String[] elementosOferta=segmentoLimpo.split(" ");
            for(int i=0;i<elementosOferta.length;i++){
                if(elementosOferta[i].equals("cambiar")) continue;
                else if(elementosOferta[i].equals("avatares")){
                    trato.setCambioAvatares(true);
                }
                else if(elementosOferta[i].equals("noalquiler")){
                    if(!Auxiliar.isNumeric(elementosOferta[i+1]) && Auxiliar.isNumeric(elementosOferta[i+2])){
                        Casilla x=this.taboeiro.buscarCasilla(elementosOferta[i+1]);
                        comprobarOfrecerTrato(x,emisor,elementosOferta[i+1]);
                        trato.engadirNoAlquilerOferta((Propiedade)x,Integer.valueOf(elementosOferta[i+2]));
                    }
                    else throw new MonopolinhoGeneralException("Sintaxe noalquiler incorrecta: noalquiler(solar, turnos)");
                    i=i+3;
                    if(i==elementosOferta.length) break;
                }
                else if(Auxiliar.isNumeric(elementosOferta[i])){
                    trato.setDinheiroOferta(Float.valueOf(elementosOferta[i]));
                }
                else{
                    Casilla c=this.taboeiro.buscarCasilla(elementosOferta[i]);
                    comprobarOfrecerTrato(c,emisor,elementosOferta[i]);
                    trato.engadirPropiedadeOferta((Propiedade)c);
                }
            }
        }

        for(String s:parteDemanda.split("y")){
            String segmentoLimpo=s.replace("("," ").replace(")"," ").replace(","," ").replace("-"," ");
            String[] elementosDemanda=segmentoLimpo.split(" ");
            for(int i=0;i<elementosDemanda.length;i++){
                if(elementosDemanda[i].equals("avatares")) continue;
                else if(elementosDemanda[i].equals("noalquiler")){
                    if(!Auxiliar.isNumeric(elementosDemanda[i+1]) && Auxiliar.isNumeric(elementosDemanda[i+2])){
                        Casilla x=this.taboeiro.buscarCasilla(elementosDemanda[i+1]);
                        comprobarOfrecerTrato(x,destinatario,elementosDemanda[i+1]);
                        trato.engadirNoAlquilerDemanda((Propiedade)x,Integer.valueOf(elementosDemanda[i+2]));
                    }
                    else throw new MonopolinhoGeneralException("Sintaxe noalquiler incorrecta: noalquiler(solar, turnos)");
                    i=i+3;
                    if(i==elementosDemanda.length) break;
                }
                else if(Auxiliar.isNumeric(elementosDemanda[i])){
                    trato.setDinheiroDemanda(Float.valueOf(elementosDemanda[i]));
                }
                else{
                    Casilla c=this.taboeiro.buscarCasilla(elementosDemanda[i]);
                    comprobarOfrecerTrato(c,destinatario,elementosDemanda[i]);
                    trato.engadirPropiedadeDemanda((Propiedade)c);
                }
            }
        }
        consola.imprimir(trato);
        destinatario.engadirTrato(trato);
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
        String mensaxe=trato.aceptarTrato();
        consola.imprimir(mensaxe);
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
    private void moverModoAvanzado(int valorDados) throws MonopolinhoException {
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
                    if(p.pertenceXogador(banca) && (c instanceof Solar)){
                        p.setValor(p.valor()*1.05f);
                    }
                }
            }
        }
    }


    /**
     * Este método comproba se se cumplen os requisitos dunha casilla para un trato.
     * @param c Casilla a comprobar
     * @param x xogador a comprobar
     * @param nome nome da casilla a comprobar
     * @return true se se pode realizar o trato, false se non
     */
    private  void comprobarOfrecerTrato(Casilla c,Xogador x,String nome) throws MonopolinhoGeneralException {
        if(c==null)throw new MonopolinhoGeneralException(nome+" non é unha casilla válida. Trato cancelado.");
        if(!(c instanceof Propiedade))throw new MonopolinhoGeneralException(nome+" non é unha propiedade. Trato cancelado.");
        if(!((Propiedade) c).pertenceXogador(x))throw new MonopolinhoGeneralException("Trato cancelado: Non podes ofrecer "+nome+" porque non é "+ (x.equals(this.turno.getXogador())? "túa.":"de "+x.getNome()));
        if(c instanceof Solar && ((Solar) c).getNumeroEdificios() != 0)throw new MonopolinhoGeneralException(nome+" ten edificios construidos. Trato cancelado.");
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
    private void comprobarCarcere() throws MonopolinhoXogadorCarcere {
        if(this.turno.getXogador().estaNoCarcere())
            throw new MonopolinhoXogadorCarcere();
    }

    /**
     * @return Comproba se acabou a partida
     */
    private boolean comprobarFinPartida(){
        return ((this.xogadores.size()-1)==this.xogadores.stream().filter(xogador -> xogador.estadoXogador()==EstadoXogador.BANCARROTA).count());
    }


    /**
     * Comproba se existe un avatar repetido
     * @param avatar
     * @return
     */
    public boolean comprobarAvatarRepetido(Avatar avatar){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().equals(avatar))return true;
        }
        return false;
    }


    /* ZONA DE FUNCIONS DE DEBUG */

    public void listarAccions(){
        consola.imprimir(turno.listarAccions());
    }

    public void mov(int i) throws MonopolinhoException {
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        turno.engadirAccion(new AccionLanzarDados(turno.getPosicion()));
        moverModoNormal(i);
    }

    public void mova(int i) throws MonopolinhoException{
        turno.aumentarVecesTiradas();
        turno.setPodeLanzar(false);
        turno.engadirAccion(new AccionLanzarDados(turno.getPosicion()));
        moverModoAvanzado(i);
    }

    public void desfacer() throws MonopolinhoException{
        Xogo.consola.imprimirNegrita(this.turno.desfacer(this));
    }

    /* FIN ZONA DEBUG */

}
