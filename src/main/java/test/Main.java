package test;
import monopolinho.obxetos.*;

import java.util.ArrayList;
import java.util.ListIterator;

public class Main {


    public static void main(String[] args) {
        ArrayList<Xogador> xogadores=new ArrayList<>();
        xogadores.add(new Xogador("Dani", Avatar.TipoMovemento.COCHE));
        xogadores.add(new Xogador("David", Avatar.TipoMovemento.COCHE));
        Xogo menu=new Xogo(xogadores);
        menu.consola();
    }
}