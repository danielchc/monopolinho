package monopolinho.obxetos;

import monopolinho.axuda.Valor;

import java.util.ArrayList;
import java.util.Scanner;

public class Xogo {

    private ArrayList<Xogador> xogadores;   //lista dos xogadores da partida
    private Taboeiro taboeiro;
    private Xogador turno;
    private Dados dados;
    private Xogador banca;

    /*
        Constructor da clase Xogo.
        Determina o turno.
        Instancia o taboeiro.
        Instancia a banca.
        Instancia os dados.
        Engade as todas as casillas a banca.
        Coloca todos os avatares na salida.
     */
    public Xogo(ArrayList<Xogador> xogadores){
        this.xogadores=xogadores;
        this.turno=this.xogadores.get(0);
        taboeiro=new Taboeiro();
        banca=new Xogador();
        dados=new Dados();
        engadirCasillasBanca();
        for (Xogador t:this.xogadores) t.getAvatar().setPosicion(this.taboeiro.getCasilla(0));
    }



    ///////////////////////METODOS////////////////////////

    /*
        Este metodo sirve como consola de comandos.
     */
    public void consola(){
        mostrarComandos();
        Scanner scanner= new Scanner(System.in);
        while(true){
            System.out.print("$> ");
            interpretarComando(scanner.nextLine());
        }
    }


    /*
        Este metodo interpreta o comando escrito e chama as funcions necesarias para realizar a accion do comando.
     */
    private void interpretarComando(String comando) {

        String[] cmds=comando.split(" ");
        switch (cmds[0].toLowerCase()){
            case "xogador":
                if (turno!=null)System.out.println(turno);
                else System.err.println("Non hai xogadores");
                break;
            case "listar":
                switch (cmds[1].toLowerCase()){
                    case "xogadores":
                        listarXogadores();
                        break;
                    case "avatares":
                        listarAvatares();
                        break;
                    case "enventa":
                        listarCasillaEnVenta();
                        break;
                    default:
                        System.out.println("\nOpción de listaxe non válida");
                        break;
                }
                break;
            case "lanzar":  //falta acabalo
                if(cmds.length!=2){
                    System.out.println("Sintaxe: lanzar dados");
                    return;
                }
                lanzarDados();
                break;

            case "acabar":
                if(cmds.length!=2) {
                    System.out.println("Sintaxe: acabar turno");
                    return;
                }
                pasarTurno();
                break;
            case "salir":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: salir carcel");
                    return;
                }
                salirCarcel();
                break;
            case "describir":
                if(cmds.length<2){
                    System.out.println("Sintaxe describir <xogador/avatar> <nome>");
                    System.out.println("Sintaxe describir <casilla>");
                    return;
                }
                switch (cmds[1]){
                    case "xogador":
                    case "jugador":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir xogador <nome>");
                            return;
                        }
                        describirXogador(cmds);
                        break;
                    case "avatar":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir avatar <id>");
                            return;
                        }
                        describirAvatar(cmds);
                        break;
                    default:
                        describirCasilla(cmds);
                        break;
                }
                break;
            case "comprar":
                comprarCasilla(cmds);
                break;
            case "ver":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: ver tableiro");
                    return;
                }
                mostrarTaboeiro();
                break;
            case "comandos":
                mostrarComandos();
                break;
            case "exit":
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Comando non recoñecido");
        }

    }

    //METODOS AUXILIARES

    /*
        Este metodo engade todas as casillas a banca. Despois os xogadores compranlle as propiedades a banca.
     */
    private void engadirCasillasBanca(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas()){
            for(Casilla c:zona)
                this.banca.engadirPropiedade(c);
        }
    }


    /*
        Este metodo lanza os dados.
        Se saen dobles permite volver a tirar.
        Se volven sair dobles permite volver tirar.
        Se saen triples manda ao xogador para a carcere.
     */
    private void lanzarDados(){
        if(!turno.getPodeLanzar()){
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
                turno.salirCarcel();
                mensaxe+=". Sacasches dados dobles, podes xogar";
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
        mensaxe+=interpretarAccion(turno.getPosicion(),dados.valorLanzar());
        System.out.println(mensaxe);
    }


    /*
        Este metodo interpreta a accion a realizar segundo a casilla na que se cae.
     */
    private String interpretarAccion(Casilla current,int newPos){
        String mensaxe="";

        Casilla next=this.taboeiro.getCasilla((current.getPosicionIndex()+newPos)%40);

        //Mover avatar
        if(next.getTipoCasilla()==Casilla.TipoCasilla.IRCARCEL) {
            mensaxe="O avatar colocase na casilla CARCEL(TEIXEIRO)";
            turno.setTurnosNaCarcel(3);
            turno.setPosicion(this.taboeiro.getCasilla(10)); //CASILLA CARCEL
        }else{
            if((current.getPosicionIndex()+newPos)>39) {
                mensaxe="O xogador "+turno.getNome()+" recibe "+ Valor.VOLTA_COMPLETA + " por completar unha volta o taboeiro.\n";
                turno.getAvatar().voltaTaboeiro();
                turno.engadirDinheiro(Valor.VOLTA_COMPLETA);
            }
            switch (next.getTipoCasilla()){
                case IMPOSTO:
                    mensaxe+="O xogador "+ turno.getNome() +  " ten que pagar "+next.getImposto() + " por caer en "+next.getNome();
                    if(turno.quitarDinheiro(next.getImposto())){
                        taboeiro.engadirBote(next.getImposto());
                    }else{
                        System.err.println("O xogador "+turno.getNome()+" non ten suficiente dinheiro para pagar o imposto");
                        //E QUE PASA SE NON TEN CARTOS????????????????????
                    }
                    break;
                case SORTE:
                    break;
                case PARKING:
                    turno.engadirDinheiro(this.taboeiro.getBote());
                    mensaxe+="O xogador "+ turno.getNome() + " recibe "+this.taboeiro.getBote()+", do bote.";
                    taboeiro.setBote(0);
                    break;
                case CARCEL:
                    mensaxe+="So de visita...";
                    break;
                case SOLAR:
                    if((!next.getDono().equals(turno))&&(!next.getDono().equals(banca))){
                        if(turno.quitarDinheiro(next.getAlquiler())){
                            mensaxe+="Tes que pagarlle "+next.getAlquiler()+" a "+next.getDono().getNome()exit;
                        }else{
                            System.err.println("Non tes suficiente diñeiro para pagar o alquiler");
                            return "";
                        }
                    }
                    break;
            }
            turno.setPosicion(next);
        }
        
        mensaxe="O avatar "  +turno.getAvatar().getId() +" avanza " +newPos+" posiciones, desde "+current.getNome()+" ata " + next.getNome() + " \n"+mensaxe;
        return "\n"+mensaxe;
    }


    /*
        Este metodo mostra por pantalla todos os comandos dispoñibles.
     */
    private void mostrarComandos(){
        String comandos="\n\nComandos dispoñibles:\n\t+ xogador   (indica quen ten turno)\n\t+ listar <xogadores/avatares/enventa>\n\t+ lanzar dados"+
                        "\n\t+ acabar turno\n\t+ salir carcel\n\t+ describir <casilla>\n\t+ describir xogador <nome>\n\t+ describir avatar <avatar>"+
                        "\n\t+ comprar <casilla>\n\t+ ver taboeiro\n\t+ exit  (sae do xogo)\n\t+ comandos  (mostra todos os comandos)";
        System.out.println(comandos);
    }

    /*
        Este metdo imprime a info dunha casilla.
     */
    private void describirCasilla(String[] cmds){
        Casilla c=this.taboeiro.buscarCasilla(cmds[1]);
        if(c!=null)System.out.println(c);
        else System.out.println("Non existe esta casilla");
    }

    /*
        Este metodo imprime a info dun xogador.
     */
    private void describirXogador(String[] cmds){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(cmds[2].toLowerCase())){
                System.out.println(x.describir());
                return;
            }
        }
        System.out.println("Non se atopou o xogador "+cmds[2]);
    }

    /*
        Este metodo imprime a info dun avatar.
     */
    private void describirAvatar(String[] cmds){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getId().equals(cmds[2])){
                System.out.println(x.getAvatar());
                return;
            }
        }
        System.out.println("Non se atopou o avatar "+ cmds[2]);
    }

    /*
        Este metodo imprime todos os avatares.
     */
    private void listarAvatares(){
        for(Xogador x:this.xogadores)
            System.out.println(x.getAvatar());
    }

    /*
        Este metodo imprime todos os xogadores.
     */
    private void listarXogadores(){
        for(Xogador x:this.xogadores)
            System.out.println(x.describir());
    }

    /*
        Este metodo imprime todas as casillas en venta.
     */
    private void listarCasillaEnVenta(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas())
            for(Casilla c:zona)
                if(c.podeseComprar())
                    System.out.println(c);
    }


    /*
        Este metodo acaba o turno e pasallo ao seguinte.
     */
    private void pasarTurno(){
        Xogador actual=turno;
        if(turno.getVecesTiradas()==0){
            System.err.println("Tes que tirar unha vez antes de tirar turno.");
            return;
        }
        int novoTurno=(this.xogadores.indexOf(turno)+1)%this.xogadores.size();
        turno=this.xogadores.get(novoTurno);
        if(novoTurno==0){
            //RESTAR CARCEL VOLTAS
        }
        actual.setVecesTiradas(0);
        actual.setPodeLanzar(true);
        System.out.println("Tiña o turno "+actual.getNome()+", agora teno "+turno.getNome());
    }

    /*
        Este metodo saca a un xogador da carcere.
     */
    private void salirCarcel(){
        if (!turno.estaNaCarcel()){
            System.out.println("O xogador non está no cárcere");
            return;
        }
        if(turno.salirCarcel()){
            System.out.println(turno.getNome()+" paga "+ Valor.SAIR_CARCERE + " e sae da cárcel. Pode lanzar os dados.");
        }else{
            System.err.println("Non tes o suficiente diñeiro para saír do cárcere");
        }
    }

    /*
        Este metodo engade unha casilla as propiedades do xogador.
     */
    private void comprarCasilla(String[] cmds){
        Casilla comprar=turno.getPosicion();
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
        comprar.getDono().eliminarPropiedade(comprar);
        turno.engadirPropiedade(comprar);
        comprar.setDono(turno);
        System.out.println("O usuario "+turno.getNome() +" comprou "+comprar.getNome() +" por "+comprar.getValor());
    }

    /*
        Este metodo imprime o tableiro.
     */
    private void mostrarTaboeiro(){
        System.out.println(taboeiro);
    }
}
