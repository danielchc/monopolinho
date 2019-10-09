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

    public Xogo(ArrayList<Xogador> xogadores){
        this.xogadores=xogadores;
        this.turno=this.xogadores.get(0);
        taboeiro=new Taboeiro();
        banca=new Xogador();
        dados=new Dados();
        engadirCasillasBanca();
        for (Xogador t:this.xogadores) t.getAvatar().setPosicion(this.taboeiro.getCasilla(0));
        //mostrarTaboeiro();
    }

    public void consola(){
        Scanner scanner= new Scanner(System.in);
        while(true){
            System.out.println("$> ");
            interpretarComando(scanner.nextLine());
        }
    }
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
                }
                lanzarDados();
                break;
            ///////////////////////////////////////////////////////////////////////////////////////////// BORRAR ESTOOOOOOOOO!!!!!!!!!!!!!!!!
            case "mov": //BORRAR ISTOOOO!!!!
                Casilla current=turno.getPosicion();
                interpretarAccion(current,Integer.parseInt(cmds[1]));
                break;
            ///////////////////////////////////////////////////////////////////////////////////////////////
            case "acabar":
                if(cmds.length!=2) {
                    System.out.println("Sintaxe: acabar turno");
                    return;
                }
                pasarTurno();
                break;
            case "salir":
                //IMPLEMENTAR, COMPROBAR SE ESTA NA CARCEL
                if(cmds.length!=2){
                    System.out.println("Sintaxe: acabar turno");
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
                if(cmds.length!=2){
                    System.out.println("Sintaxe: comprar <casilla>");
                    return;
                }
                comprarCasilla(cmds);
                break;
            case "ver":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: ver tableiro");
                    return;
                }
                mostrarTaboeiro();
                break;
            case "exit":
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Comando non recoñecido");
        }
        
    }

    //FUNCIONS AUXILIARES

    private void engadirCasillasBanca(){
        for(ArrayList<Casilla> zona:this.taboeiro.getCasillas()){
            for(Casilla c:zona)
                this.banca.engadirPropiedade(c);
        }
    }

    private void interpretarAccion(Casilla current,int newPos){
        String mensaxe="";
        if((current.getPosicionIndex()+newPos)>39) {
            mensaxe="O xogador "+turno.getNome()+" recibe "+ Valor.VOLTA_COMPLETA + " por completar unha volta o taboeiro";
            turno.getAvatar().voltaTaboeiro();
        }
        Casilla next=this.taboeiro.getCasilla((current.getPosicionIndex()+newPos)%40);
        switch (next.getTipoCasilla()){
            case IRCARCEL:
                mensaxe="O avatar colocase na casilla CARCEL(TEIXEIRO)";
                turno.setTurnosNaCarcel(3);
                next=this.taboeiro.getCasilla(10); //CASILLA CARCEL
                //pasarTurno(); PODE PAGAR PA SALIR DA CARCEL
                break;
            case IMPOSTO:
                //Añadir o bote o imposto
                //this.taboeiro.engadirBote(next.getIMPOSTO);
                if(next.getPosicionIndex()==4){
                    mensaxe+="O xogador "+ turno.getNome() +  " ten que pagar "+next.getNome();
                    if(turno.quitarDinheiro(Valor.IMPOSTO_BARATO)){
                        taboeiro.engadirBote(Valor.IMPOSTO_BARATO);
                    }
                    else{
                        System.out.println("O xogador "+turno.getNome()+" non ten suficiente dinheiro para pagar o imposto");
                    }
                }
                else{
                    mensaxe+="O xogador "+ turno.getNome() +  " ten que pagar "+next.getNome();
                    if(turno.quitarDinheiro(Valor.IMPOSTO_CARO)){
                        taboeiro.engadirBote(Valor.IMPOSTO_CARO);
                    }
                    else{
                        System.out.println("O xogador "+turno.getNome()+" non ten suficiente dinheiro para pagar o imposto");
                    }
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
        }
        turno.setPosicion(next);
        mensaxe="O avatar "  +turno.getAvatar().getId() +" avanza " +newPos+" posiciones, desde "+current.getNome()+" hasta " +next.getNome()+" \n"+mensaxe;
        mostrarTaboeiro();
        System.out.println("\n"+mensaxe);
    }

    //FUNCIONS SWITCH

    private void describirCasilla(String[] cmds){
        Casilla c=this.taboeiro.buscarCasilla(cmds[1]);
        if(c!=null)System.out.println(c);
        else System.out.println("Non existe esta casilla");
    }

    private void describirXogador(String[] cmds){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(cmds[2].toLowerCase())){
                System.out.println(x.describir());
                return;
            }
        }
        System.out.println("Non se atopou o xogador "+cmds[2]);
    }
    private void describirAvatar(String[] cmds){
        for(Xogador x:this.xogadores){
            if(x.getAvatar().getId().equals(cmds[2])){
                System.out.println(x.getAvatar());
                return;
            }
        }
        System.out.println("Non se atopou o avatar "+ cmds[2]);
    }
    private void listarAvatares(){
        for(Xogador x:this.xogadores)
            System.out.println(x.getAvatar());
    }
    private void listarXogadores(){
        for(Xogador x:this.xogadores)
            System.out.println(x.describir());
    }
    private void listarCasillaEnVenta(){
       for(ArrayList<Casilla> zona:this.taboeiro.getCasillas())
           for(Casilla c:zona)
               if(c.getTipoCasilla()== Casilla.TipoCasilla.SOLAR || c.getTipoCasilla() == Casilla.TipoCasilla.INFRAESTRUCTURA || c.getTipoCasilla()== Casilla.TipoCasilla.TRANSPORTE)
                   System.out.println(c);
    }
    private void lanzarDados(){
        String quereComprar;
        Scanner input=new Scanner(System.in);

        dados.lanzarDados();
        if(turno.estaNaCarcel()){
            System.err.println("Estás no cárcere non podes lanzar os dados se non pagas");
            return;
        }
        System.out.println("\nSaiu o "+dados.getDados()[0]+" e o "+dados.getDados()[1]);
        Casilla current=turno.getPosicion();
        interpretarAccion(current,dados.valorLanzar());


        /*REVISAR ESTO */
        if (dados.sonDobles()){
            System.out.println("\nAo sair dobles, o xogador "+turno.getNome()+" volve tirar.");

            System.out.println("\nQueres comprar a casilla na que estas antes de anvanzar outra vez? si (s) ou no (n)");
            quereComprar=input.nextLine();
            if(quereComprar.equals("s")){
                turno.getPosicion().comprar(turno);
                System.out.println("Compra realizada.");
            }

            dados.lanzarDados();
            System.out.println("\nSaiu o "+dados.getDados()[0]+" e o "+dados.getDados()[1]);
            Casilla current1=turno.getPosicion();
            interpretarAccion(current1,dados.valorLanzar());
            if (dados.sonDobles()){
                System.out.println("\nAo sair dobles, o xogador "+turno.getNome()+" volve tirar.");

                System.out.println("\nQueres comprar a casilla na que estas antes de anvanzar outra vez? si (s) ou no (n)");
                quereComprar=input.nextLine();
                if(quereComprar.equals("s")){
                    turno.getPosicion().comprar(turno);
                    System.out.println("Compra realizada.");
                }

                dados.lanzarDados();
                if (dados.sonDobles()){
                    System.out.println("\nSairon triples,"+turno.getNome()+" vai para a carcel");
                    /////////////////////////MANDALO PA CARCEL//////////////////////////////////////////////////////////////////////////////////////
                }
                else{
                    System.out.println("\nSaiu o "+dados.getDados()[0]+" e o "+dados.getDados()[1]);
                    Casilla current2=turno.getPosicion();
                    interpretarAccion(current2,dados.valorLanzar());
                }
            }
        }
    }

    private void pasarTurno(){
        Xogador actual=turno;
        int novoTurno=(this.xogadores.indexOf(turno)+1)%this.xogadores.size();
        turno=this.xogadores.get(novoTurno);
        if(novoTurno==0){
            //RESTAR CARCEL VOLTAS
        }
        System.out.println("Tiña o turno "+actual.getNome()+", agora teno "+turno.getNome());
    }
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
    private void comprarCasilla(String[] cmds){
        //Casilla c=this.taboeiro.buscarCasilla(cmds[1]);
        //TA MAL IMPLEMENTADA??????
        if (turno.getPosicion().getDono().getNome().equals(banca.getNome())){   //como banca non ten avatar non podo usar equals entre o xogador e a banca
            turno.getPosicion().comprar(turno);
        }
        else{
            System.err.println("Esta casilla pertence a "+turno.getPosicion().getDono().getNome()+". Non a podes comprar");
            return;
        }

        //quitarlle a propiedade á banca
        //engadirlla ao xogador
        //restar fortuna ao xogador
    }

    private void mostrarTaboeiro(){
        System.out.println(taboeiro);
    }
}
