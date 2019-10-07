package monopolinho.obxetos;


import monopolinho.obxetos.Avatar;
import monopolinho.obxetos.Dados;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Xogador;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Xogo {

    private ArrayList<Xogador> xogadores;   //lista dos xogadores da partida
    //private ArrayList<Avatar> avatares; //lista de avatares
    private Taboeiro taboeiro;
    private Xogador turno;
    Dados dados;

    public Xogo(){
        this.xogadores=new ArrayList<>();
        //this.avatares=new ArrayList<>();
        taboeiro=new Taboeiro();
        dados=new Dados();
    }

    private Avatar.TipoMovemento interpretarMov(String tipomov){
        switch (tipomov.toLowerCase()){
            case "pelota":
                return Avatar.TipoMovemento.PELOTA;
            case "esfinxe":
                return Avatar.TipoMovemento.ESFINXE;
            case "sombreiro":
                return Avatar.TipoMovemento.SOMBREIRO;
            case "coche":
            default:
                return Avatar.TipoMovemento.COCHE;
        }
    }



    //FUNCIONES DINERITO//////////////////////////////////////////////

    public float fortunaInicialXogador(){
        float suma=0;
        for (int i=0;i<40;i++){
            if(taboeiro.getCasilla(i).getTipoCasilla()== Casilla.TipoCasilla.SOLAR){
                suma+=taboeiro.getCasilla(i).getValor();
            }
        }
        return suma/3;
    }

    public void cobrarSalida(Xogador x){
        float suma=0;
        int contador=0;
        for (int i=0;i<40;i++) {
            if (taboeiro.getCasilla(i).getTipoCasilla() == Casilla.TipoCasilla.SOLAR) {
                suma += taboeiro.getCasilla(i).getValor();
                contador++;
            }
        }
        x.engadirDinheiro(suma/contador);   //engadeselle a media do valor dos solares
    }

    public float valorInicialTransporte(){
        float suma=0;
        int contador=0;
        for (int i=0;i<40;i++) {
            if (taboeiro.getCasilla(i).getTipoCasilla() == Casilla.TipoCasilla.SOLAR) {
                suma += taboeiro.getCasilla(i).getValor();
                contador++;
            }
        }
        return suma/contador;
    }

    public float valorInicialServicio(){
        return 0.75f*valorInicialTransporte();
    }

    /*  SIN COMPLETAR, FALTA O DE VALOR INICIAL DOS SOLARES
    public float valorInicialCasaHotel(Casilla c){
        return 0.6f*valorInicialSolar();
    }
    */

    /*  SIN COMPLETAR, FALTA O DE VALOR INICIAL DOS SOLARES
    public float valorInicialPiscina(Casilla c){
        return 0.4f*valorInicialSolar();
    }
    */

    /*  SIN COMPLETAR, FALTA O DE VALOR INICIAL DOS SOLARES
    public float valorInicialPistaDeporte(Casilla c){
        return 1.25f*valorInicialSolar();
    }
    */











    ////////////////////////////////////////////////////////////////////

    public void iniciar(){
        System.out.println("Bem vindo o Monopolinho: ");
        consola();
    }
    public void consola(){
        Scanner scanner= new Scanner(System.in);
        while(true){
            System.out.println("$> ");
            interpretarComando(scanner.nextLine());
        }
    }
    public void interpretarComando(String comando) {
        String[] cmds=comando.split(" ");
        if(cmds[0].toLowerCase().equals("crear")){
            if(cmds.length!=4){
                System.out.println("Sintaxe: crear xogador <nome> <avatar>");
                return;
            }
            crearXogador(cmds);
            mostrarTaboeiro();
            return;
        }
        if(this.xogadores.size()==0){
            System.err.println("Non se creou ningún xogador");
            System.out.println("Para crear un xogador: crear xogador <nome> <avatar>");
            return;
        }
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

    //ÑAPAAAAAAAAAAAAAAAAAA

    private void interpretarAccion(Casilla current,int newPos){
        String mensaxe="";
        /*PUTA ÑAPA ESTO NON QUEDA ASI */
        if(current.getTipoCasilla()== Casilla.TipoCasilla.CARCEL){
            //AQUI HAI QUE FACER O DOS TURNOS
            System.out.println("MEO DEOS VOCE ESTA PRESO!");
            return;
        }
        System.out.println(current.getPosicionIndex()+newPos);
        Casilla next=this.taboeiro.getCasilla((current.getPosicionIndex()+newPos)%40);
        switch (next.getTipoCasilla()){
            case IRCARCEL:
                System.out.println("MEO DEOS voce ficou presso");
                //METER NA CARCEL!!
                //Casilla carcel=.....
                //turno.setPosicion();
                pasarTurno();
                return;
            case CARCEL:
                //QUE SE SUPON QUE FAS SE CAES NA CASILLA CARCEL???
                //System.out.println("Acabas de caer en la carcel");
                break;
            case IMPOSTO:
                //Añadir o bote o imposto
                //this.taboeiro.engadirBote(next.getIMPOSTO);
                //break;
            case SORTE:
            case PARKING:
                turno.engadirDinheiro(this.taboeiro.getBote());
                mensaxe+="O xogador "+ turno.getNome() + " recibe "+this.taboeiro.getBote()+", do bote da banca.";
            default: //DE AQUI PARA ABAIXO VAISE EJECUTAR SEMPRE
                //if(next.getDono()!=turno) BLABLABLALABLALBLA
                turno.setPosicion(next);
                mensaxe="O avatar "  +turno.getAvatar().getId() +" avanza " +newPos+" posiciones, desde "+current.getNome()+" hasta " +next.getNome()+" \n"+mensaxe;
                break;
        }
        mostrarTaboeiro();
        System.out.println("\n"+mensaxe);
    }
    private void crearXogador(String[] cmds){
        Xogador xogador=new Xogador(cmds[2], interpretarMov(cmds[3]));
        this.xogadores.add(xogador);              //añado o xogador creado á lista de xogadores
        System.out.println(xogador);
        if(this.xogadores.get(0).equals(xogador)){
            xogador.setTenTurno(true); //ATA QUE PUNTO FAI FALLA ISTO
            turno=xogador;
        }
        xogador.getAvatar().setPosicion(this.taboeiro.getCasilla(0));
    }

    private void describirCasilla(String[] cmds){
        Casilla c=this.taboeiro.buscarCasilla(cmds[1]);
        if(c!=null)System.out.println(c);
        else System.out.println("Non existe esta casilla");
    }

    public void describirXogador(String[] cmds){
        for(Xogador x:this.xogadores){
            if(x.getNome().toLowerCase().equals(cmds[2].toLowerCase())){
                System.out.println(x.describir());
                return;
            }
        }
        System.out.println("Non se atopou o xogador "+cmds[2]);
    }
    public void describirAvatar(String[] cmds){
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
        turno.setTenTurno(false);  //ATA QUE PUNTO FAI FALLA ISTO?
        turno=this.xogadores.get((this.xogadores.indexOf(turno)+1)%this.xogadores.size());
        turno.setTenTurno(true); //ATA QUE PUNTO FAI FALLA ISTO?
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
