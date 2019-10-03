package monopolinho;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Scanner;

public class Xogo {

    private ArrayList<Xogador> xogadores;   //lista dos xogadores da partida
    private ArrayList<Avatar> avatares; //lista de avatares
    private Taboeiro taboeiro;



    public void iniciar(){
        System.out.println("Bem vindo o Monopolinho: ");
        this.xogadores=new ArrayList<>();
        this.avatares=new ArrayList<>();
        taboeiro=new Taboeiro();
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

                    //REVISAARRR!!
                    /*
                    //(xogadores.get(0).equals(x))
                    boolean nonPrimeiro=true;   //variable bandeira
                    for(int i=0;i<xogadores.size();i++) {
                        if (xogadores.get(i).equals(x) && i == 0) { //se é o primeiro enton ten o primeiro turno
                            x.setTenTurno(true);
                            nonPrimeiro=false;
                        }
                    }
                    if (nonPrimeiro==true){     //se bandeira indica que non se fijou como primeiro, enton o turno non lle toca
                        x.setTenTurno(false);   //senon non ten turno
                    }*/

                    //Esto o mellor safabase mellor creando unha variable Xogador tenTurno aqui e pacasa
                    //Esta liña fai o mesmo que todo o que pos arriba :D
                    x.setTenTurno(xogadores.get(0).equals(x));

                    //REPINTAR O TABLEIRO
                }
                break;
            case "xogador":
                for(Xogador x:this.xogadores){
                    if(x.getTenTurno()){
                        System.out.println(x);
                        break;
                    }
                }
                //System.out.println("Non hai xogadores"); //COMPROBAR ANTES
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
                //IMPLEMENTAR, COMPROBAR SE ESTÁ NA CARCEL
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
                System.out.println(taboeiro);
                break;
            case "exit":
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Comando non recoñecido");
        }
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
}
