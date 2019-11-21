package test;
import monopolinho.interfaz.Menu;
import java.io.IOException;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        Menu menu=new Menu();
        menu.leerComandosArchivo(System.getProperty("user.dir")+"/usuarios.txt");
        //menu.leerComandosArchivo(System.getProperty("user.dir")+"/autocorrecion.txt");
    }
}