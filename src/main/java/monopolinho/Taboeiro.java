package monopolinho;

import java.util.ArrayList;

public class Taboeiro {
    public enum Zona{
        SUR,
        OESTE,
        NORTE,
        ESTE
    }
    private ArrayList<ArrayList<Casilla>> casillas;


    public Taboeiro(){
        this.casillas=new ArrayList<>();
        for(int i=0;i<4;i++)this.casillas.add(new ArrayList<>()); //AÑADIR ZONAS
        xerarCasillas();
    }


    public void engadirCasilla(Zona z,Casilla casilla){
        if(casilla!=null)this.casillas.get(z.ordinal()).add(casilla);
    }

    public ArrayList<ArrayList<Casilla>> getCasillas() {
        return casillas;
    }

    public ArrayList<Casilla> getCasillas(Zona z) {
        return casillas.get(z.ordinal());
    }

    public void xerarCasillas(){
        Grupo marron=new Grupo("casilla", ReprASCII.ColorCasilla.GRIS,"Norte"); //cambiar color
        Grupo cyan=new Grupo("casilla", ReprASCII.ColorCasilla.CYAN,"Norte");
        Grupo violeta=new Grupo("casilla", ReprASCII.ColorCasilla.VIOLETA,"Norte");
        Grupo ocre=new Grupo("casilla", ReprASCII.ColorCasilla.GRIS,"Norte");   //cambiar color
        Grupo rojo=new Grupo("casilla", ReprASCII.ColorCasilla.VERMELLO,"Norte");
        Grupo amarillo=new Grupo("casilla", ReprASCII.ColorCasilla.AMARELO,"Norte");
        Grupo verde=new Grupo("casilla", ReprASCII.ColorCasilla.VERDE,"Norte");
        Grupo azul=new Grupo("casilla", ReprASCII.ColorCasilla.AZUL,"Norte");

        Grupo p=new Grupo("casilla", ReprASCII.ColorCasilla.GRIS,"Norte");  //provisional

        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("PARKING",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("LUGO",rojo,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("SORTE",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("FERROL",rojo,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("ORENSE",rojo,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TRENSIÑO",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TUI",amarillo,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("VIGO",amarillo,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("S.AUGA",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("A_CARCEL",p,"",10000,3000));

        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("CARCEL",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("ELISARDINHO",cyan,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("DORA",cyan,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SORTE",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LOSADA",cyan,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TRENSIÑO",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("PAGA",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LADRA",marron,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("COMUNIDADE",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SALIDA",p,"",10000,3000));

        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TOCOU",verde,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("DITE AI",verde,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("COMUNIDADE",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("DITE",verde,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TRENSIÑO",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SORTE",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SIM",azul,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("IMPOSTO",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("cousa",azul,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("cousa",azul,"",10000,3000)); //sobraver


        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,"",10000,3000)); //sobra
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COMUNIDADE",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("TRENSIÑO",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",violeta,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COUSA",violeta,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("S.LUZ",p,"",10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("cousa",violeta,"",10000,3000));


    }

    @Override
    public String toString(){
        String taboeiro="";

        //NORTE
        String[] ncasillas=new String[]{"","","",""};
        for(int i=0;i<10;i++)
            ReprASCII.unirCasilla(ncasillas,getCasillas(Zona.NORTE).get(i));
        taboeiro+=String.join("\n",ncasillas);
        taboeiro+="\n";

        //ESTE OESTE
        String[] ocasillas;
        for(int i=0;i<10;i++){
            ocasillas=new String[]{"","","",""};
            ReprASCII.unirCasilla(ocasillas,getCasillas(Zona.OESTE).get(9-i));
            for(int j=0;j<8;j++){
                if((i==4) && (j==0))
                    ReprASCII.debuxoMonopolinho(ocasillas,1);
                else if((i==3) && (j==0))
                    ReprASCII.debuxoMonopolinho(ocasillas,0);
                else if (i!=4 && i!=3)
                    ReprASCII.engadirCasillaVacia(ocasillas);
            }
            ReprASCII.unirCasilla(ocasillas,getCasillas(Zona.ESTE).get(i));
            taboeiro+=String.join("\n",ocasillas);
            taboeiro+="\n";
        }

        //SUR
        String[] scasillas=new String[]{"","","",""};
        for(int i=9;i>=0;i--)ReprASCII.unirCasilla(scasillas,getCasillas(Zona.SUR).get(i));
        taboeiro+=String.join("\n",scasillas);

        return taboeiro;
    }
}
