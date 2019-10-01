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
    /*
    public boolean existeNombreCasilla(String casilla){
        if(casilla==null){
            System.err.println("\nString non inicializado");
            return false;
        }
        for(int i=0;i<casillas.size();i++){
           if(casillas.get(i).getNome().equals(casilla)) {
               return true;
           }
        }
        return false;
    }*/
    @Override
    public String toString(){
        String taboeiro="";

        //NORTE
        String[] ncasillas=new String[]{"","",""};
        for(int i=0;i<getCasillas().size();i++)
            ReprASCII.unirCasilla(ncasillas,getCasillas(Zona.NORTE).get(i));
        for(String s:ncasillas)taboeiro+=s+"\n";
        return taboeiro;
        //TA COMENTAO PORQUE NON PUXEN CASILLAS NAS OUTRAS ZONAS E DA PEREZA FACER UN IF
        /*ESTE OESTE
        String[] ocasillas;
        for(int i=0;i<10;i--){
            ocasillas=new String[]{"","",""};
            ReprASCII.unirCasilla(ocasillas,getCasillas(Zona.OESTE).get(9-i));
            for(int j=0;j<8;j++)ReprASCII.engadirCasillaVacia(ocasillas);
            ReprASCII.unirCasilla(ocasillas,getCasillas(Zona.ESTE).get(i));
            for(String s:ocasillas)taboeiro+=s+"\n";
        }

        //SUR
        String[] scasillas=new String[]{"","",""};
        for(int i=9;i>=0;i--)ReprASCII.unirCasilla(scasillas,getCasillas(Zona.SUR).get(i));
        for(String s:scasillas)taboeiro+=s+"\n";

        return taboeiro;*/
    }
}
