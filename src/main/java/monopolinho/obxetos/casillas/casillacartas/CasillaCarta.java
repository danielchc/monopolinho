package monopolinho.obxetos.casillas.casillacartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Carta;
import monopolinho.obxetos.casillas.Casilla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public  abstract class CasillaCarta extends Casilla {
    private ArrayList<Carta> cartas;

    /**
     * Crea unha nova instancia de casilla
     * @param nome Nome da casilla
     */
    public CasillaCarta(String nome) {
        super(nome);
        cartas=new ArrayList<>();
        crearCartas();
    }

    public abstract void crearCartas();

    public String pedirCarta(Xogo xogo){
        this.barallar();
        this.listarCartas();
        int nCarta=0;
        do{
            System.out.print("Escolle unha carta (1-6): ");
            nCarta=new Scanner(System.in).nextInt();
        }while(nCarta<1 || nCarta>6);
        return cartas.get(nCarta-1).interpretarCarta(xogo);
    }

    public void engadirCarta(Carta c){
        if(c!=null)this.cartas.add(c);
    }

    /**
     * Baralla as cartas
     */
    public void barallar(){
        Collections.shuffle(cartas);
    }

    /**
     * Lista as cartas dispoñibles
     */
    public void listarCartas(){
        for(Carta c:this.cartas)
            System.out.println(c.getTipoCartaAccion());
    }

}
