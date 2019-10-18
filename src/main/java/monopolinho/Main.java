package monopolinho;

import monopolinho.interfaz.Menu;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Menu menu=new Menu();
        //SE COMENTAS ESTO TES QUE USAR O COMANDO CREAR XOGADOR
        //Se o comentas so podes crear xogadores o principio, cando executes un comando que non sexa crear xogador empeza a partida
        //menu.preguntarXogadores();
        menu.consola();

        //PA CORRECION SERIA
        /*
        * Menu menu=new Menu();
        * menu.archivo("archivo.txt")......
        * */
    }
}
