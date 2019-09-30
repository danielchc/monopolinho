package monopolinho;

import java.util.Scanner;

public class Xogo {
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
                if(cmds.length!=3){
                    System.out.println("Sintaxe: crear <nome> <avatar>");
                }else{
                    Xogador x=new Xogador(cmds[1],Avatar.TipoAvatar.COCHE);
                    System.out.println(x.toString());
                }
                break;
            case "sair":
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Comando non recoñecido");
        }
    }
}
