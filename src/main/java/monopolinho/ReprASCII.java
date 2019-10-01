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
        BLANCO,
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
    public static String centrarTexto(String texto){
        String sp="";
        for (int i=0;i<((WIDTH-texto.length())/2);i++)sp+=" ";
        return (sp+texto+sp).substring(0,WIDTH-1);
    }
    public static String colorear(ColorCasilla color, String texto){
        return  CORESCASILLA[color.ordinal()] + texto + CORESCASILLA[color.ordinal()];
    }
}
