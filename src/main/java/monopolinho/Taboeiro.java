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
        //Xerador de casillas
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
