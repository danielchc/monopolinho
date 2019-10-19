package monopolinho;

import monopolinho.interfaz.Menu;

import java.io.IOException;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class Main {
    private static boolean correccion=true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Menu menu=new Menu();
        if(!correccion){
            menu.preguntarXogadores();
            menu.consola();
        }else{
            menu.leerComandosArchivo(System.getProperty("user.dir")+"/usuarios.txt");
        }
    }
}
