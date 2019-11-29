package test;

import monopolinho.interfaz.Menu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Menu menu=new Menu();
        menu.leerComandosArchivo(System.getProperty("user.dir")+"/test.txt");
        //menu.leerComandosArchivo(System.getProperty("user.dir")+"/autocorrecion.txt");
    }
}