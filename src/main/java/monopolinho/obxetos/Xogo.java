package monopolinho.obxetos;


import monopolinho.axuda.ReprTab;
import monopolinho.obxetos.Avatar;
import monopolinho.obxetos.Dados;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Xogador;

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
            case "acabar":
                if(cmds.length!=2) {
                    System.out.println("Sintaxe: acabar turno");
                    return;
                }
                pasarTurno();
                break;
            case "salir":
                //IMPLEMENTAR, COMPROBAR SE ESTA NA CARCEL
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
            case "mov": //BORRAR ISTOOOO!!!!
                Casilla current=turno.getPosicion();
                interpretarAccion(current,Integer.parseInt(cmds[1]));
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
        /*PUTA ÑAPA ESTO NON QUEDA ASI */
        if(current.getTipoCasilla()== Casilla.TipoCasilla.CARCEL){
            //AQUI HAI QUE FACER O DOS TURNOS
            System.out.println("MEO DEOS VOCE ESTA PRESO!");
            return;
        }
        Casilla next=this.taboeiro.getCasilla((current.getPosicionIndex()+newPos)%40);
        switch (next.getTipoCasilla()){
            case IRCARCEL:
                System.out.println("MEO DEOS voce ficou presso");
                //METER NA CARCEL!!
                //Casilla carcel=.....
                //turno.setPosicion();
                pasarTurno();
                return;

            case IMPOSTO:
                //Añadir o bote o imposto
                //this.taboeiro.engadirBote(next.getIMPOSTO);
                mensaxe+="O xogador de "+ turno.getNome() +  " que pagar IMPOSTO de IMPOSTO";
                break;
            case SORTE:
                break;
            case PARKING:
                turno.engadirDinheiro(this.taboeiro.getBote());
                mensaxe+="O xogador "+ turno.getNome() + " recibe "+this.taboeiro.getBote()+", do bote.";
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
                System.out.println(x.describir());
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
        //POR FASER
//        for(this.taboeiro)
//            for(Casilla c:ac)
//                System.out.println(c);
    }
    private void lanzarDados(){
        dados.lanzarDados();
        System.out.println("\nSaiu o "+dados.getDados()[0]+" e o "+dados.getDados()[1]);
        Casilla current=turno.getPosicion();
        interpretarAccion(current,dados.valorLanzar());
    }

    private void pasarTurno(){
        Xogador actual=turno;
        turno=this.xogadores.get((this.xogadores.indexOf(turno)+1)%this.xogadores.size());
        System.out.println("Tiña o turno "+actual.getNome()+" agora teno "+turno.getNome());
    }

    private void comprarCasilla(String[] cmds){
        //Casilla c=this.taboeiro.buscarCasilla(cmds[1]);
        //TA MAL IMPLEMENTADA??????
        turno.getPosicion().comprar(turno);
        //quitarlle a propiedade á banca
        //engadirlla ao xogador
        //restar fortuna ao xogador
    }

    private void mostrarTaboeiro(){
        System.out.println(taboeiro);
    }
}
