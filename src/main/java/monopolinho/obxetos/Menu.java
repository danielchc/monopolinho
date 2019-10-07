package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Xogo xogo;
    ArrayList<Xogador> xogadores;
    public Menu(){
        xogadores=new ArrayList<>();
    }
    public void iniciar(){
        System.out.println(ReprTab.debuxoSimple());
        preguntarXogadores();
        xogo=new Xogo(this.xogadores);
        xogo.consola();
    }
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
                crearXogador(nome,interpretarMov(mov));
            }
        }else {
            System.err.println("Debe haber polo menos dous xogadores");
            System.exit(1);
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
    private void crearXogador(String nombre, Avatar.TipoMovemento tipoMov){
        Xogador xogador=new Xogador(nombre, tipoMov);
        this.xogadores.add(xogador);
        System.out.println(xogador);
    }
}
