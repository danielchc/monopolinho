package monopolinho;

import java.util.ArrayList;

public class Taboeiro {
    private ArrayList<Casilla> casillas;


    public void Taboeiro(){
        this.casillas=new ArrayList<>();
        //Xerador de casillas
    }

    /* DAME SEGMENTATION
    public void anhadirCasilla(Casilla casilla){
        if(casilla!=null) {
            this.casillas.add(casilla);
        }
    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

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
