package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;

import java.util.ArrayList;

public class Taboeiro {
    public enum Zona{
        SUR,
        OESTE,
        NORTE,
        ESTE
    }
    private ArrayList<ArrayList<Casilla>> casillas;
    private Xogador banca;

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

    public Casilla getCasilla(int posicion){
        if((posicion>=0)&&(posicion<11)) return getCasillas(Zona.SUR).get(posicion);
        else if((posicion>=11)&&(posicion<20)) return getCasillas(Zona.OESTE).get(posicion-11);
        else if((posicion>=20)&&(posicion<31)) return getCasillas(Zona.SUR).get(posicion-20);
        else return getCasillas(Zona.ESTE).get(posicion-31);
    }

    public void xerarCasillas(){

        Grupo marron=new Grupo("casilla", ReprTab.ReprColor.ANSI_HIGH_WHITE_BACKGROUND,"Norte"); //cambiar color
        Grupo cyan=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");
        Grupo violeta=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");
        Grupo ocre=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");   //cambiar color
        Grupo rojo=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");
        Grupo amarillo=new Grupo("casilla", ReprTab.ReprColor.ANSI_PURPLE,"Norte");
        Grupo verde=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");
        Grupo azul=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");
        Grupo p=new Grupo("casilla", ReprTab.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");  //provisional



        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("PARKING",p,Casilla.TipoCasilla.PARKING,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("LUGO",rojo,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("SORTE",p,Casilla.TipoCasilla.SORTE,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("FERROL",rojo,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("ORENSE",rojo,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TRENSINO",p,Casilla.TipoCasilla.INFRAESTRUCTURA,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TUI",amarillo,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("VIGO",amarillo,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("S.AUGA",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("S.AUGA",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("A_CARCEL",p,Casilla.TipoCasilla.IRCARCEL,10000,3000));



        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SALIDA",p,Casilla.TipoCasilla.SALIDA,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("ELISARDINHO",cyan,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("DORA",cyan,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SORTE",p,Casilla.TipoCasilla.SORTE,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LOSADA",cyan,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TRENSINO",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("PAGA",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("LADRA",marron,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("COMUNIDADE",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("KAK",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("CARCEL",p,Casilla.TipoCasilla.CARCEL,10000,3000));



        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TOCOU",verde,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("DITE AI",verde,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("COMUNIDADE",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("DITE",verde,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TRENSINO",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SORTE",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SIM",azul,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("NAO",azul,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("IMPOSTO",p,Casilla.TipoCasilla.SOLAR,10000,3000));


        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre, Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COMUNIDADE",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("TRENSINO",p,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",violeta,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("NAO",violeta,Casilla.TipoCasilla.SOLAR,10000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COUSA",violeta,Casilla.TipoCasilla.SOLAR,10000,3000));
    }

    @Override
    public String toString(){
        String taboeiro="";

        //NORTE
        String[] ncasillas=new String[]{"","","","",""};
        for(int i=0;i<11;i++)
            ReprTab.unirCasilla(ncasillas,getCasillas(Zona.NORTE).get(i));
        taboeiro+=String.join("\n",ncasillas);
        taboeiro+="\n";

        //ESTE OESTE
        String[] ocasillas;
        for(int i=0;i<9;i++){
            ocasillas=new String[]{"","","","",""};
            ReprTab.unirCasilla(ocasillas,getCasillas(Zona.OESTE).get(8-i));
            for(int j=0;j<9;j++){
                if((i==4) && (j==0))
                    ReprTab.debuxoMonopolinho(ocasillas,1);
                else if((i==3) && (j==0))
                    ReprTab.debuxoMonopolinho(ocasillas,0);
                else if (i!=4 && i!=3)
                    ReprTab.engadirCasillaVacia(ocasillas);
            }
            ReprTab.unirCasilla(ocasillas,getCasillas(Zona.ESTE).get(i));
            taboeiro+=String.join("\n",ocasillas);
            taboeiro+="\n";
        }

        //SUR
        String[] scasillas=new String[]{"","","","",""};
        for(int i=0;i<11;i++) ReprTab.unirCasilla(scasillas,getCasillas(Zona.SUR).get(10-i));
        taboeiro+=String.join("\n",scasillas);

        return taboeiro;
    }
}
