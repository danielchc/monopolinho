package monopolinho;

public class Grupo {
    private String tipo; //solar ou especial
    private String posicion;    //norte sure este ou oeste
    private String color;
    private int numeroSolares;


    public void Grupo(String tipo,String color,String posicion){
        if(tipo==null || color==null || posicion==null){
            System.err.println("Error: algun elemento non inicializado");
            return;
        }
        this.tipo=tipo;
        this.color=color;
        //this.posicion=determinarPoscicion(color);
        //this.numeroSolares=determinarSolares(color);
    }

    /*public String determinarPosicion(String color){
        if(color!=null){
            //facer un switch que segun o color devolva a posicion
        }
    }

    public int determinarSolares(Strin color){
        if(color!=null){
           //facer un switch que segun o color devolva o numero de solares
        }
    }


    */


    public void setNumeroSolares(int numeroSolares) {
        this.numeroSolares = numeroSolares;
    }

    public int getNumeroSolares() {
        return numeroSolares;
    }

    public void setPosicion(String posicion) {
        if(posicion!=null)this.posicion = posicion;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setTipo(String tipo) {
        if (tipo!=null)this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setColor(String color) {
        if (color!=null)this.color = color;
    }

    public String getColor() {
        return color;
    }
}
