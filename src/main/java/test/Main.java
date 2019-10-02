package test;
import monopolinho.*;

public class Main {
    /**
     * @param args the command line arguments
     */
    ;
    public static void main(String[] args) {
        Taboeiro t=new Taboeiro();
        Grupo p=new Grupo("KK", ReprASCII.ColorCasilla.VERMELLO,"Norte");
        Grupo s=new Grupo("KK", ReprASCII.ColorCasilla.CYAN,"Norte");
        Grupo d=new Grupo("KK", ReprASCII.ColorCasilla.VERDE,"Norte");

        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("CARCEL",p,"",10000,3000, ReprASCII.ColorCasilla.GRIS));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("LUGO",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("CORUNA",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("FERROL",d,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("ORENSE",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("PONTEVEDRA",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TUI",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("VIGO",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("SANTIAGO",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("PACARCEL",p,"",10000,3000, ReprASCII.ColorCasilla.GRIS));

        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SALIDA",s,"",10000,3000,ReprASCII.ColorCasilla.GRIS));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("ELISARDINHO",d,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("DORA",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("BARJITA",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LOSADA",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LAMA",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("GAGO",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LADRA",d,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("PARDO",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.SUR,new Casilla("PARKING",d,"",10000,3000, ReprASCII.ColorCasilla.GRIS));

        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TOCOU",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("DITE AI",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("QUIRIAS",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("NON HAI",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("FICOU",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("NAO",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SIM",d,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("cousa",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("cousa",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("cousa",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("cousa",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));

        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",s,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",d,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COUSA",p,"",10000,3000));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("cousa",d,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("cousa",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("cousa",s,"",10000,3000, ReprASCII.ColorCasilla.CYAN));
        t.engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("cousa",p,"",10000,3000, ReprASCII.ColorCasilla.CYAN));


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