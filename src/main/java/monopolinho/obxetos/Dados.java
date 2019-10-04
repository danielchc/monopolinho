package monopolinho.obxetos;
import java.util.Random;

public class Dados {
    private int dado1;
    private int dado2;
    public Dados(){
        this.dado1=0;
        this.dado2=0;
    }

    public void lanzarDados(){
        this.dado1=new Random().nextInt(6)+1;
        this.dado2=new Random().nextInt(6)+1;
    }

    public int valorLanzar(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        return (this.dado1+this.dado2);
    }

    public int[] getDados(){
        if((this.dado1==0) || (this.dado2==0))System.err.println("Non se tiraron os dados");
        int[] arr={dado1,dado2};
        return arr;
    }

    public boolean sonDobles(){
        return (this.dado1==this.dado2);
    }
}
