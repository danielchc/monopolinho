package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;

import java.util.ArrayList;

public class Taboeiro {
    public enum Zona{
        SUR,
        OESTE,
        NORTE,
        ESTE
    }
    private ArrayList<ArrayList<Casilla>> casillas;
    private float bote;

    public Taboeiro(){
        this.casillas=new ArrayList<>();
        for(int i=0;i<4;i++)this.casillas.add(new ArrayList<>()); //AÑADIR ZONAS
        xerarCasillas();
    }
    public void engadirCasilla(Zona z,Casilla casilla){
        if(casilla!=null){
            this.casillas.get(z.ordinal()).add(casilla);
            if(z==Zona.SUR) casilla.setPosicion(this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.OESTE) casilla.setPosicion(11+this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.NORTE) casilla.setPosicion(20+this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.ESTE) casilla.setPosicion(31 + this.casillas.get(z.ordinal()).indexOf(casilla));
        }
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
        else if((posicion>=20)&&(posicion<31)) return getCasillas(Zona.NORTE).get(posicion-20);
        else return getCasillas(Zona.ESTE).get(posicion-31);
    }

    public Casilla buscarCasilla(String nome){
        for(ArrayList<Casilla> ac:this.casillas){
            for(Casilla c:ac){
                if(c.getNome().toLowerCase().equals(nome.toLowerCase())) return c;
            }
        }
        return null;
    }

    public float getBote() {
        return bote;
    }

    public void engadirBote(float imposto) {
        this.bote += imposto;
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

    public void xerarCasillas(){

        Grupo marron=new Grupo("casilla", Valor.ReprColor.ANSI_HIGH_WHITE_BACKGROUND,"Norte"); //cambiar color
        Grupo cyan=new Grupo("casilla", Valor.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");
        Grupo violeta=new Grupo("casilla", Valor.ReprColor.ANSI_PURPLE_BACKGROUND,"Norte");
        Grupo ocre=new Grupo("casilla", Valor.ReprColor.ANSI_CYAN_BACKGROUND,"Norte");   //cambiar color
        Grupo rojo=new Grupo("casilla", Valor.ReprColor.ANSI_RED_BACKGROUND,"Norte");
        Grupo amarillo=new Grupo("casilla", Valor.ReprColor.ANSI_YELLOW_BACKGROUND,"Norte");
        Grupo verde=new Grupo("casilla", Valor.ReprColor.ANSI_GREEN_BACKGROUND,"Norte");
        Grupo azul=new Grupo("casilla", Valor.ReprColor.ANSI_BLUE_BACKGROUND,"Norte");
        Grupo p=new Grupo("casilla", Valor.ReprColor.ANSI_RED_BACKGROUND,"Norte");  //provisional



        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("PARKING",p,Casilla.TipoCasilla.PARKING,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("LUGO",rojo,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("SORTE",p,Casilla.TipoCasilla.SORTE,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("FERROL",rojo,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("ORENSE",rojo,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TRENSINO",p,Casilla.TipoCasilla.INFRAESTRUCTURA,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("TUI",amarillo,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("VIGO",amarillo,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("S.AUGA",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("S.AUGA",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("A_CARCEL",p,Casilla.TipoCasilla.IRCARCEL,3000,3000));



        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SALIDA",Casilla.TipoCasilla.SALIDA));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TEST",cyan,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TEST",cyan,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SORTE",p,Casilla.TipoCasilla.SORTE,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TEST",cyan,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TRENSINO",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("PAGA",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("TEST",marron,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("COMUNIDADE",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("ADDDS",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("CARCEL", Casilla.TipoCasilla.CARCEL));



        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TOCOU",verde,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("ADIOS",verde,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("COMUNIDADE",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("HOLA",verde,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TREN",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SORTE",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SIM",azul,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("NAO",azul,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("IMPOSTO",p,Casilla.TipoCasilla.SOLAR,3000,3000));


        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre, Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COMUNIDADE",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",ocre,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("TRENSINO",p,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MATOSINHOS",violeta,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("SANTIAGO",violeta,Casilla.TipoCasilla.SOLAR,3000,3000));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COUSA",violeta,Casilla.TipoCasilla.SOLAR,3000,3000));

    }
}
