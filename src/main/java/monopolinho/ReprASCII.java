package monopolinho;

public class ReprASCII {
    //CORES ASCII
    public static enum ColorCasilla {
        NEGRO,
        VERMELLO,
        VERDE,
        AMARELO,
        AZUL,
        VIOLETA,
        CYAN,
        GRIS,
        RESET
    }
    public static final String[] CORESCASILLA=new String[]{
        "\u001B[40m",
        "\u001B[41m",
        "\u001B[42m",
        "\u001B[43m",
        "\u001B[44m",
        "\u001B[45m",
        "\u001B[46m",
        "\u001B[47m",
        "\u001B[0m"
    };
    public static final int WIDTH=15;
    public enum Borde{
        SUPERIOR,
        INFERIOR
    }

    private static String centrarTexto(String texto,int w){
        String sp="";
        for (int i=0;i<((w-texto.length())/2);i++)sp+=" ";
        return (sp+texto+sp).substring(0,w-1);
    }
    public static String borde(Borde borde){
        String b="+";
        for(int i=0;i<WIDTH-3;i++)b+="=";
        b+="+";
        return b;
    }
    public static String bordeTextoCentrado(String texto){
        return "|"+centrarTexto(texto,WIDTH-2)+"|";
    }
    public static String colorear(ColorCasilla color, String texto){
        return  CORESCASILLA[color.ordinal()] + texto + CORESCASILLA[ColorCasilla.RESET.ordinal()];
    }
    public static void unirCasilla(String[] casillas,Casilla casilla){
        for (int i=0;i<casillas.length;i++)casillas[i]+=casilla.getRepresentacion()[i];
    }
    public static void engadirCasillaVacia(String[] casillas){
        for (int i=0;i<casillas.length;i++)casillas[i]+=colorear(ColorCasilla.NEGRO,centrarTexto("",WIDTH));
    }
}
