package monopolinho.axuda;

import monopolinho.obxetos.Casilla;

public class ReprTab {
    //CORES ASCII
    public enum ReprColor{
        ANSI_RESET,
        ANSI_BLACK,
        ANSI_RED,
        ANSI_GREEN,
        ANSI_YELLOW,
        ANSI_BLUE,
        ANSI_PURPLE,
        ANSI_CYAN,
        ANSI_WHITE,
        ANSI_BLACK_BOLD,
        ANSI_RED_BOLD,
        ANSI_GREEN_BOLD,
        ANSI_YELLOW_BOLD,
        ANSI_BLUE_BOLD ,
        ANSI_PURPLE_BOLD,
        ANSI_CYAN_BOLD ,
        ANSI_WHITE_BOLD,
        ANSI_BLACK_BACKGROUND,
        ANSI_RED_BACKGROUND,
        ANSI_GREEN_BACKGROUND,
        ANSI_YELLOW_BACKGROUND,
        ANSI_HIGH_YELLOW_BACKGROUND,
        ANSI_BLUE_BACKGROUND,
        ANSI_PURPLE_BACKGROUND,
        ANSI_CYAN_BACKGROUND,
        ANSI_WHITE_BACKGROUND,
        ANSI_HIGH_WHITE_BACKGROUND
    }
    private static final String[] Cores=new String[]{
            "\u001B[0m",
            "\u001B[30m","\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m",
            "\u001B[1;30m","\u001B[1;31m","\u001B[1;32m","\u001B[1;33m","\u001B[1;34m","\u001B[1;35m",
            "\u001B[1;36m","\u001B[1;37m","\u001B[40m","\u001B[41m","\u001B[42m","\u001B[43m",
            "\u001B[0;103m","\u001B[44m","\u001B[45m","\u001B[46m","\u001B[47m","\u001B[0;107m"};

    public static final int WIDTH=20;

    private static String centrarTexto(String texto,int w){
        String sp="";
        for (int i=0;i<((w-texto.length())/2);i++)sp+=" ";
        return (sp+texto+sp).substring(0,w-1);
    }
    public static String borde(){
        String b="";
        for(int i=0;i<WIDTH-1;i++)b+="-";
        return b;
    }
    public static String bordeTextoCentrado(String texto){
        return "│"+centrarTexto(texto,WIDTH-2)+"│";
    }
    public static String colorear(ReprColor color, String texto){
        return Cores[color.ordinal()] + texto + Cores[ReprColor.ANSI_RESET.ordinal()];
    }
    public static void unirCasilla(String[] casillas, Casilla casilla){
        for (int i=0;i<casillas.length;i++)casillas[i]+=casilla.getRepresentacion()[i];
    }
    public static void engadirCasillaVacia(String[] casillas){
        for (int i=0;i<casillas.length;i++)casillas[i]+=centrarTexto("",WIDTH);
    }
    public static void debuxoMonopolinho(String[] casillas,int parte) {
        int nw=(WIDTH * 9) - 8;
        if (parte == 0) {
            casillas[0] += centrarTexto("", nw);
            casillas[1] += centrarTexto("", nw);
            casillas[2] += centrarTexto("", nw);
            casillas[3] += centrarTexto("", nw);
            casillas[4] += centrarTexto("__  __                               _ _       _          ", nw);
        }else if (parte==1) {
            casillas[0] += centrarTexto("|  \\/  | ___  _ __   ___  _ __   ___ | (_)_ __ | |__   ___  ", nw);
            casillas[1] += centrarTexto("| |\\/| |/ _ \\| '_ \\ / _ \\| '_ \\ / _ \\| | | '_ \\| '_ \\ / _ \\ ", nw);
            casillas[2] += centrarTexto("| |  | | (_) | | | | (_) | |_) | (_) | | | | | | | | | (_) |", nw);
            casillas[3] += centrarTexto("|_|  |_|\\___/|_| |_|\\___/| .__/ \\___/|_|_|_| |_|_| |_|\\___/ ", nw);
            casillas[4] += centrarTexto("                         |_|                                ", nw);
        }
    }
}
