package monopolinho;

import java.util.ArrayList;
import java.util.Scanner;

public class Xogo {

    private ArrayList<Xogador> xogadores;   //lista dos xogadores da partida
    private ArrayList<Avatar> avatares; //lista de avatares




    public void iniciar(){
        System.out.println("Bem vindo o Monopolinho: ");
        this.xogadores=new ArrayList<>();
        this.avatares=new ArrayList<>();
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
                if(cmds.length!=3){
                    System.out.println("Sintaxe: crear <nome> <avatar>");
                }else{
                    Xogador x=new Xogador(cmds[1],Avatar.TipoAvatar.COCHE);
                    this.xogadores.add(x);              //añado o xogador creado á lista de xogadores
                    this.avatares.add(x.getAvatar());   //añado o avatar do xogador á lista de avatares
                    System.out.println(x);
                    boolean nonPrimeiro=true;   //variable bandeira
                    for(int i=0;i<xogadores.size();i++) {
                        if (xogadores.get(i).equals(x) && i == 0) { //se é o primeiro enton ten o primeiro turno
                            x.setTenTurno(true);
                            nonPrimeiro=false;
                        }
                    }
                    if (nonPrimeiro==true){     //se bandeira indica que non se fijou como primeiro, enton o turno non lle toca
                        x.setTenTurno(false);   //senon non ten turno
                    }

                }
                break;
            /* FALTA IMPLEMENTAR ESTES COMANDOS, SOLO ESTAN OS CASES */
            case "xogador":
                for(int i=0;i<xogadores.size();i++){
                    if(xogadores.get(i).getTenTurno()){
                        System.out.println("\nTurno de "+xogadores.get(i).getNome());
                    }
                }
                break;
            case "listar":
                switch (cmds[1]){
                    case "xogadores":
                        for(int i=0;i<xogadores.size();i++){
                            System.out.println(xogadores.get(i));
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
                    Dados dados=new Dados();
                    dados.lanzarDados();
                    System.out.println("\nSaiu o "+dados.getDados()[0]+" e o "+dados.getDados()[1]);
                }
                break;
            case "acabar":
                if(cmds.length!=2) {
                    System.out.println("Sintaxe: acabar turno");
                }else{
                    for(int i=0;i<xogadores.size();i++){
                        if(xogadores.get(i).getTenTurno()){
                            xogadores.get(i).setTenTurno(false);                     //o turno do xogador actual pasa a false
                            xogadores.get((i+1)%xogadores.size()).setTenTurno(true); //o turno siguiente é o seiguiente, si ten o turno o ultimo enton tocalle ao primeiro
                            System.out.println("\nTurno de "+xogadores.get(i).getNome()+" , agora tocalle a "+xogadores.get((i+1)%xogadores.size()).getNome());
                            break;
                        }
                    }
                }
                break;
            case "salir":
                break;
            case "describir":
                switch (cmds[1]){
                    case "xogador":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir xogador <nome>");
                        }else{
                            for(int i=0;i<xogadores.size();i++){
                                if(xogadores.get(i).getNome().equals(cmds[2])){
                                    System.out.println(avatares.get(i));
                                }
                            }
                        }
                        break;
                    case "avatar":
                        break;
                    default: //describir casilla

                        break;
                }
                break;
            case "comprar":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: comprar <casilla>");
                }else{
                    //quitarlle a propiedade á banca
                    //engadirlla ao xogador
                    //restar fortuna ao xogador
                }
                break;
            case "ver":
                break;

            case "exit":
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Comando non recoñecido");
        }
    }
}
