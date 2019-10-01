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


    public void Taboeiro(){
        this.casillas=new ArrayList<>();
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
}
