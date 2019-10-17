package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.obxetos.Avatar;
import monopolinho.obxetos.Xogador;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author Daniel Chenel
 * @author David Carracedo
 */



public class Menu {
    Xogo xogo;
    ArrayList<Xogador> xogadores;


    /**
     * Este é o constructor da clase Menu.
     * Insta o arraylist de xogadores do xogo.
     */
    public Menu(){
        xogadores=new ArrayList<>();
    }


    /**
     * Este metodo inicia a partida.
     * Pregunta polos xogadores, crea o xogo e a consola de comandos.
     */
    public void iniciar(){
        System.out.println(ReprTab.debuxoSimple());
        preguntarXogadores();
        xogo=new Xogo(this.xogadores);
        xogo.consola();
    }


    /**
     * Este metodo permite preguntar o numero de xogadores e crealos.
     */
    private void preguntarXogadores(){
        System.out.print("\n\nInserta o numero de xogadores: ");
        Scanner input=new Scanner(System.in);
        while (!input.hasNextInt()) input.next();
        int numXogadores = input.nextInt();
        input.nextLine();
        if (numXogadores>1){
            for (int i=1;i<=numXogadores;i++){
                System.out.println("Introduce o nome do xogador "+i+": ");
                String nome=input.nextLine();
                System.out.println("Introduce o tipo de movemento do xogador <coche/sombreiro/esfinxe/pelota> "+i+": ");
                String mov=input.nextLine();
                if((nome.toLowerCase().equals("banca"))||!crearXogador(nome,interpretarMov(mov))){
                    System.err.println("Xa existe un usuario que se chama así");
                    i--;
                }
            }
        }else {
            System.err.println("Debe haber polo menos dous xogadores");
            System.exit(1);
        }
    }


    /**
     * Este metodo interpreta que tipo de movemento ten un avatar
     * @param tipomov tipo de movemento do avatar
     * @return TipoMovemento dun avatar
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
                System.out.println("\nAvatar inválido, asignouseche o coche por defecto.");
                return Avatar.TipoMovemento.COCHE;
        }
    }


    /**
     * Este metodo permite crear un xogador
     * @param nombre nome do xogador
     * @param tipoMov tipo de movemento do avatar do xogador
     * @return true si se creou o xogador ou false se o xogador xa existe
     */
    private boolean crearXogador(String nombre, Avatar.TipoMovemento tipoMov){
        Xogador xogador=new Xogador(nombre, tipoMov);
        if(this.xogadores.contains(xogador)){
            return false; //Comproba se existe o usuario o método equal compara nomes!
        }
        this.xogadores.add(xogador);
        System.out.println(xogador);
        return true;
    }
}
