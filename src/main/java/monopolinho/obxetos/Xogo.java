package monopolinho.obxetos;


import monopolinho.obxetos.Avatar;
import monopolinho.obxetos.Dados;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Xogador;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Xogo {

    private ArrayList<Xogador> xogadores;   //lista dos xogadores da partida
    private ArrayList<Avatar> avatares; //lista de avatares
    private Taboeiro taboeiro;
    private Xogador turno;
    Dados dados;

    public Xogo(){
        this.xogadores=new ArrayList<>();
        this.avatares=new ArrayList<>();
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
        switch (cmds[0].toLowerCase()){
            case "crear":
                if(cmds.length!=4){
                    System.out.println("Sintaxe: crear xogador <nome> <avatar>");
                }else{
                    Xogador x=new Xogador(cmds[2], interpretarMov(cmds[3]));
                    this.xogadores.add(x);              //añado o xogador creado á lista de xogadores
                    this.avatares.add(x.getAvatar());   //añado o avatar do xogador á lista de avatares //NON SEI SE ESTO FAI FALLA
                    System.out.println(x);
                    if(this.xogadores.get(0).equals(x)){
                        x.setTenTurno(true); //ATA QUE PUNTO FAI FALLA ISTO
                        turno=x;
                    }
                    x.getAvatar().setPosicion(this.taboeiro.getCasilla(0));
                    System.out.println(taboeiro);
                }
                break;
            case "xogador":
                if (turno!=null)System.out.println(turno);
                else System.err.println("Non hai xogadores");
                break;
            case "listar":
                switch (cmds[1]){
                    case "xogadores":
                        for(Xogador x:this.xogadores){
                            System.out.println(x.describir());
                        }
                        break;
                    case "avatares":
                        for(int i=0;i<avatares.size();i++){
                            System.out.println(avatares.get(i));
                        }
                        break;
                    case "enventa":
                        /*for(int i=0;i<banca.propiedades.size();i++){
                            System.out.println(banca.propiedades.get(i));
                        }*/
                        break;
                    default:
                        System.out.println("\nOpción de listaxe non válida");
                        break;
                }

                break;
            case "lanzar":  //falta acabalo
                if(cmds.length!=2){
                    System.out.println("Sintaxe: lanzar dados");
                }else{
                    dados.lanzarDados();
                    System.out.println("\nSaiu o "+dados.getDados()[0]+" e o "+dados.getDados()[1]);


                    /*PUTA ÑAPA ESTO NON QUEDA ASI */
                    Casilla current=turno.getPosicion();
                    Casilla next=this.taboeiro.getCasilla((current.getPosicionIndex()+dados.valorLanzar())%40);
                    turno.setPosicion(next);
                    System.out.println("O avatar "  +turno.getAvatar().getId() +" avanza " +dados.valorLanzar()+" posiciones, desde "+current.getNome()+" hasta " +next.getNome()+" \n");
                    /*PUTA ÑAAPA ESTO NON QUEDA ASI */


                    System.out.println(taboeiro);
                }
                break;
            case "acabar":
                if(cmds.length!=2) {
                    System.out.println("Sintaxe: acabar turno");
                }else{
                    Xogador actual=turno;
                    pasarTurno();
                    System.out.println("Tiña o turno "+actual.getNome()+" agora teno "+turno.getNome());
                }
                break;
            case "salir":
                //IMPLEMENTAR, COMPROBAR SE ESTA NA CARCEL
                break;
            case "describir":
                switch (cmds[1]){
                    case "xogador":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir xogador <nome>");
                        }else{
                            for(Xogador x:this.xogadores){
                                if(x.getNome().equals(cmds[2])){
                                    System.out.println(x.describir());
                                }
                            }
                        }
                        break;
                    case "avatar":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir avatar <id>");
                        }else{
                            for(Avatar a:this.avatares){
                                if(a.getId().equals(cmds[2])){
                                    System.out.println(a);
                                }
                            }
                        }
                        break;
                    default: //describir casilla
                        //for(this.taboeiro.getCasillas())
                        break;
                }
                break;
            case "comprar":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: comprar <casilla>");
                }else{
                    turno.getPosicion().comprar(turno);
                    //quitarlle a propiedade á banca
                    //engadirlla ao xogador
                    //restar fortuna ao xogador
                }
                break;
            case "ver":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: ver tableiro");
                }else {
                    System.out.println(taboeiro);
                }
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
    public void pasarTurno(){
        turno.setTenTurno(false);  //ATA QUE PUNTO FAI FALLA ISTO?
        turno=this.xogadores.get((this.xogadores.indexOf(turno)+1)%this.xogadores.size());
        turno.setTenTurno(true); //ATA QUE PUNTO FAI FALLA ISTO?
    }
}
