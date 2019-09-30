package monopolinho;

import java.util.Scanner;

/**
 *
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    ;
    public static void main(String[] args) {
        /*Xogo xogo=new Xogo();
        xogo.iniciar();*/
        //Xogador banca=new Xogador();
        Xogador xogador=new Xogador("David", Avatar.TipoAvatar.COCHE);
        Xogador xogador1=new Xogador("Daniel", Avatar.TipoAvatar.LARANJO);
        Xogador xogador2=new Xogador("Helena", Avatar.TipoAvatar.LARANJO);
        System.out.println(xogador);
        System.out.println(xogador1);
        System.out.println(xogador2);
    }


}
