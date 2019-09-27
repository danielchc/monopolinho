package monopolinho;

import java.util.Scanner;

public class Xogo {
    public void consola(){
        System.out.println("$> ");
        Scanner scanner= new Scanner(System.in);
        interpretarComando(scanner.nextLine());
    }
    public void interpretarComando(String comando) {
        String[] cmds=comando.split(" ");
        System.out.println("\uD83D\uDE97");
        switch (cmds[0].toLowerCase()){
            case "crear":
                if(cmds.length!=3){
                    System.out.println("Sintaxe: crear <nome> <avatar>");
                }else{
                    Xogador x=new Xogador(cmds[1],new Avatar(Avatar.TipoAvatar.COCHE));
                    //System.out.println(x.getAvatar().getRepresentacion());
                    System.out.println(x.toString());
                }
                break;
            case "sair":
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
        }
    }
}
