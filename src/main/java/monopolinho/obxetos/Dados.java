package monopolinho.obxetos;
import java.util.Random;

public class Dados {
    private int dado1;
    private int dado2;

    //// constructor ////
    public Dados(){
        this.dado1=0;
        this.dado2=0;
    }

    ////// Métodos //////
    /*
        Este metodo xera dous valores aleatorios para os dados.
     */
    public void lanzarDados(){
        this.dado1=new Random().nextInt(6)+1;
        this.dado2=new Random().nextInt(6)+1;
    }

    /*
        Este metodo devolve o valor dos datos sumado.
     */
    public int valorLanzar(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return (this.dado1+this.dado2);
    }

    /*
        Este metodo permite saber se sairon dobles.
     */
    public boolean sonDobles(){
        return (this.dado1==this.dado2);
    }


    ////////// getter //////////////////

    public int getDado1(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return dado1;
    }
    public int getDado2(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return dado2;
    }

}
