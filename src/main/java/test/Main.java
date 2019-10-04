package test;
import monopolinho.*;

public class Main {
    /**
     * @param args the command line arguments
     */
    ;
    public static void main(String[] args) {
        Taboeiro t=new Taboeiro();

        for(int i=0;i<40;i++){
            System.out.println(t.getCasilla(i));
        }
        System.out.println(t);
        /*
        Dados d=new Dados();
        d.lanzarDados();
        System.out.printf("%d %d\n",d.getDados()[0],d.getDados()[1]);
        System.out.printf("%b",d.sonDobles());
        return;
        Xogador banca=new Xogador();
        Xogador xogador=new Xogador("David", Avatar.TipoAvatar.COCHE);
        Xogador xogador1=new Xogador("Daniel", Avatar.TipoAvatar.ESFINXE);
        Xogador xogador2=new Xogador("Helena", Avatar.TipoAvatar.COCHE);
        Xogador xogador3=new Xogador("Pablo", Avatar.TipoAvatar.COCHE);
        Casilla viladoconde=new Casilla("VILA DO CONDE","SOLAR",1,1000);
        Casilla matosinhos=new Casilla("MATOSINHOS","SOLAR",1,1000);
        xogador3.engadirPropiedade(viladoconde);
        xogador2.engadirPropiedade(matosinhos);
        System.out.println(banca);
        System.out.println(xogador);
        System.out.println(xogador1);
        System.out.println(xogador2);
        System.out.println(xogador3);
        System.out.println(xogador.getAvatar().toString());*/
    }


}