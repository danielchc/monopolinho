package monopolinho.axuda;

import monopolinho.obxetos.Casilla;

public class ReprTab {


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
    public static String colorear(Valor.ReprColor color, String texto){
        return Valor.Cores[color.ordinal()] + texto + Valor.Cores[Valor.ReprColor.ANSI_RESET.ordinal()];
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
