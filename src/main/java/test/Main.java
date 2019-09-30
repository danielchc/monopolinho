package test;
import monopolinho.Avatar;
import monopolinho.Casilla;
import monopolinho.Xogador;

public class Main {
    /**
     * @param args the command line arguments
     */
    ;
    public static void main(String[] args) {
        Xogador banca=new Xogador();
        Xogador xogador=new Xogador("David", Avatar.TipoAvatar.COCHE);
        Xogador xogador1=new Xogador("Daniel", Avatar.TipoAvatar.LARANJO);
        Xogador xogador2=new Xogador("Helena", Avatar.TipoAvatar.LARANJO);
        Xogador xogador3=new Xogador("Pablo", Avatar.TipoAvatar.LARANJO);
        Casilla viladoconde=new Casilla("VILA DO CONDE","SOLAR",1,1000);
        Casilla matosinhos=new Casilla("MATOSINHOS","SOLAR",1,1000);
        xogador3.engadirPropiedade(viladoconde);
        xogador2.engadirPropiedade(matosinhos);
        System.out.println(banca);
        System.out.println(xogador);
        System.out.println(xogador1);
        System.out.println(xogador2);
        System.out.println(xogador3);
    }


}