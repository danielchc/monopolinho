package monopolinho.axuda;

import monopolinho.obxetos.Casilla;


/**
 * @author Daniel Chenel
 * @author David Carracedo
 */



public class ReprTab {


    public static final int WIDTH=20;


    /**
     * Este metodo permite centrar texto segun o ancho
     * @param texto texto a centrar
     * @param w ancho
     * @return texto centrado
     */
    private static String centrarTexto(String texto,int w){
        String sp="";
        for (int i=0;i<((w-texto.length())/2);i++)sp+=" ";
        return (sp+texto+sp).substring(0,w-1);
    }


    /**
     * Este metodo permite crear o borde das casillas
     * @return devolve un String co borde
     */
    public static String borde(){
        String b="";
        for(int i=0;i<WIDTH-1;i++)b+="-";
        return b;
    }


    /**
     * Este metodo permite poñer o lateral a un texto centrado
     * @param texto texto a poñer lateral
     * @return String co texto centrado e con borde
     */
    public static String bordeTextoCentrado(String texto){
        return "│"+centrarTexto(texto,WIDTH-2)+"│";
    }


    /**
     * Este metodo permite colorear un texto
     * @param color color a aplicar
     * @param texto texto a colorear
     * @return String co texto coloreado
     */
    public static String colorear(Valor.ReprColor color, String texto){
        String re=Valor.Cores[color.ordinal()] + texto + Valor.Cores[Valor.ReprColor.ANSI_RESET.ordinal()];
        if(color== Valor.ReprColor.ANSI_BLUE_BACKGROUND || color== Valor.ReprColor.ANSI_BLACK_BACKGROUND)
            re=Valor.Cores[Valor.ReprColor.ANSI_WHITE.ordinal()]+re+Valor.Cores[Valor.ReprColor.ANSI_RESET.ordinal()];
        return re;
    }


    /**
     * Este metodo concatena casillas
     * @param casillas array coas casillas
     * @param casilla casilla para unir
     */
    public static void unirCasilla(String[] casillas, Casilla casilla){
        for (int i=0;i<casillas.length;i++)casillas[i]+=casilla.getRepresentacion()[i];
    }


    /**
     * Este metodo permite añadir casillas vacias a un array
     * @param casillas array de casillas
     */
    public static void engadirCasillaVacia(String[] casillas){
        for (int i=0;i<casillas.length;i++)casillas[i]+=centrarTexto("",WIDTH);
    }


    /**
     * Este metodo permite representar o texto "Monopolinho" en grande por pantalla
     * @param casillas array de casillas para gardar a palabra
     * @param parte
     */
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


    /**
     * Este metodo permite imprimir a palabra "Monopolinho"
     * @return String co texto coloreado
     */
    public static String debuxoSimple(){
        String[] p1=new String[]{"","","","",""};
        String[] p2=new String[]{"","","","",""};
        debuxoMonopolinho(p1,0);
        debuxoMonopolinho(p2,1);
        return colorear(Valor.ReprColor.ANSI_PURPLE_BOLD,String.join("\n",p1)+"\n"+String.join("\n",p2));
    }
    public static String formatearNumeros(float f){
        if(f>1000000){
            return String.format("%.2fM",f/1000000);
        }else if(f>1000){
            return String.format("%.2fK",f/1000);
        }else{
            return String.format("%.2f",f);
        }
    }
    public static void imprimirErro(String erro){
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_RED,erro));
    }
}
