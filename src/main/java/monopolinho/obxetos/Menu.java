package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Xogo xogo;
    ArrayList<Xogador> xogadores;

    ///////////CONSTRUCTOR//////////////

    public Menu(){
        xogadores=new ArrayList<>();
    }


    ///////////////////METODOS//////////////////////

    /*
        Este metodo incia a partida, preguntando o numero de xogadores e inciando a consola de comandos
     */
    public void iniciar(){
        System.out.println(ReprTab.debuxoSimple());
        preguntarXogadores();
        xogo=new Xogo(this.xogadores);
        xogo.consola();
    }

    /*
        Este metodo pregunta os xogadores que van participar e instaciaos
     */
    private void preguntarXogadores(){
        System.out.println("\n\nInserta o numero de xogadores");
        Scanner input=new Scanner(System.in);
        while (!input.hasNextInt()) input.next();
        int numXogadores = input.nextInt();
        input.nextLine();
        if (numXogadores>1){
            for (int i=1;i<=numXogadores;i++){
                System.out.println("Introduce o nome do xogador "+i);
                String nome=input.nextLine();
                System.out.println("Introduce o tipo de movemento do xogador "+i);
                String mov=input.nextLine();
                if(!crearXogador(nome,interpretarMov(mov))){
                    System.err.println("Xa existe un usuario que se chama asi");
                    i--;
                }
            }
        }else {
            System.err.println("Debe haber polo menos dous xogadores");
            System.exit(1);
        }
    }

    /*
        Este metodo interpreta que tipo de movemento vai ter cada xogador
     */
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

    /*
        Este metodo instancia cada xogador
     */
    private boolean crearXogador(String nombre, Avatar.TipoMovemento tipoMov){
        Xogador xogador=new Xogador(nombre, tipoMov);
        if(this.xogadores.contains(xogador))return false; //Comproba se existe o usuario o método equal compara nomes!
        this.xogadores.add(xogador);
        System.out.println(xogador);
        return true;
    }
}
