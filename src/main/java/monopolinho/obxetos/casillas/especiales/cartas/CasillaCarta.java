package monopolinho.obxetos.casillas.especiales.cartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.Carta;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.excepcions.MonopolinhoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public  abstract class CasillaCarta extends Casilla {
    private ArrayList<Carta> cartas;
    private Carta ultimaCarta;

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

    public String pedirCarta(Xogo xogo) throws MonopolinhoException {
        this.barallar();
        System.out.println(this.listarCartas());
        int nCarta=0;
        do{
            Xogo.consola.imprimirsl("\tEscolle unha carta (1-6): ");
            nCarta=new Scanner(System.in).nextInt();
        }while(nCarta<1 || nCarta>6);
        this.ultimaCarta=cartas.get(nCarta-1);
        return ultimaCarta.accion(xogo);
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
    public String listarCartas(){
        String mensaxe="";
        int i=1;
        for(Carta c:this.cartas){
            mensaxe+="\n\t"+i+".- "+c.getMensaxe();
            i++;
        }
        return mensaxe;
    }

    public Carta getUltimaCarta() {
        return ultimaCarta;
    }

    public void setUltimaCarta(Carta ultimaCarta) {
        this.ultimaCarta = ultimaCarta;
    }
}
