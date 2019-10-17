package monopolinho.obxetos;
import java.util.Random;


/**
 * @author Daniel Chenel
 * @author David Carracedo
 */


public class Dados {
    private int dado1;
    private int dado2;


    /**
     * Constructor de parametros vacios.
     * Inicia os dados a 0
     */
    public Dados(){
        this.dado1=0;
        this.dado2=0;
    }


    /**
     * Este metodo permite lanzar os dados.
     * Xera dous valores entre 1 e 6 para cada dado.
     */
    public void lanzarDados(){
        this.dado1=new Random().nextInt(6)+1;
        this.dado2=new Random().nextInt(6)+1;
    }


    /**
     * @return valor dos dous dados sumados
     */
    public int valorLanzar(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return (this.dado1+this.dado2);
    }


    /**
     * Permite saber se sairon dobles nos dados.
     * @return verdadeiro se sairon dobles, falseo se non
     */
    public boolean sonDobles(){
        return (this.dado1==this.dado2);
    }


    /**
     * @return valor do dado 1
     */
    public int getDado1(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return dado1;
    }


    /**
     * @return valor do dado 2
     */
    public int getDado2(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return dado2;
    }


    /**
     * Permite establecer o valor do dado 1
     * @param dado1 valor do dado1
     */
    public void setDado1(int dado1) {
        this.dado1 = dado1;
    }

    /**
     * Permite establecer o valor do dado 1
     * @param dado2 valor do dado1
     */
    public void setDado2(int dado2) {
        this.dado2 = dado2;
    }
}
