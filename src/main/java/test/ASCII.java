package test;

public class ASCII {
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final int WIDTH=15;

    public  ASCII(){

    }
    private String centrarTexto(String texto){
        String sp="";
        for (int i=0;i<((WIDTH-texto.length())/2);i++)sp+=" ";
        return (sp+texto+sp).substring(0,WIDTH-1);
    }
    public enum Borde{
        SUPERIOR,
        INFERIOR
    }
    private String borde(Borde borde){
        String b=(borde==Borde.SUPERIOR)?"\u259B":"\u2599";
        for(int i=0;i<WIDTH-3;i++)b+=(borde==Borde.SUPERIOR)?"\u2580":"\u2583";
        b+=(borde==Borde.SUPERIOR)?"\u259C":"\u259F";
        return b;
    }
    public String[] generarCasilla(String color,String valor){
        return new String[]{
                color + borde(Borde.SUPERIOR)+ ANSI_RESET,
                color + "\u258C"+centrarTexto(valor).substring(1,WIDTH-2) +"\u2590"+ ANSI_RESET,
                color + "\u258C"+centrarTexto("&P").substring(1,WIDTH-2) +"\u2590"+ ANSI_RESET,
                color + borde(Borde.INFERIOR) + ANSI_RESET ,
        };
    }


    public void unirCasilla(String[] casillas,String[] casilla){
        for (int i=0;i<casillas.length;i++)casillas[i]+=casilla[i];
    }

    public String imprimirTableiro(){

        String[] sur=new String[]{"SAIDA","2","3","4","5","6","7","8","9","CARCEL"};
        String[] norte=new String[]{"PARKING","LUGO","SANTIAGO","ORENSE","PONTEVEDRA","VIGO","FERROL","CORUNA","TUI","PACARCEL"};
        String[] este=new String[]{"LEON","VALLADOLID","SALAMANCA","MADRID","ALBACETE","VALENCIA","BARCELONA","SANTANDER","BILBAO","TARRAGONA"};
        String[] oeste=new String[]{"LEON","VALLADOLID","SALAMANCA","COUSA","ALBACETE","VALENCIA","BARCELONA","SANTANDER","BILBAO","TARRAGONA"};

        String taboeiro="";

        //NORTE
        String[] ncasillas=new String[]{"","","",""};
        for(int i=0;i<5;i++)unirCasilla(ncasillas,generarCasilla(ANSI_GREEN_BACKGROUND,norte[i]));
        for(int i=0;i<5;i++)unirCasilla(ncasillas,generarCasilla(ANSI_CYAN_BACKGROUND,norte[i]));
        for(String s:ncasillas)taboeiro+=s+"\n";

        //ESTE OESTE
        String[] ocasillas;
        for(int i=9;i>=0;i--){
            ocasillas=new String[]{"","","",""};
            unirCasilla(ocasillas,generarCasilla(ANSI_RED_BACKGROUND,oeste[i]));

            for(int j=0;j<8;j++)
                unirCasilla(ocasillas,generarCasilla(ANSI_BLACK_BACKGROUND,""));
            //unirCasilla(ocasillas,new String[]{centrarTexto(""),centrarTexto(""),centrarTexto(""),centrarTexto("")});

            unirCasilla(ocasillas,generarCasilla(ANSI_PURPLE_BACKGROUND,este[9-i]));

            for(String s:ocasillas)taboeiro+=s+"\n";
        }

        //SUR
        String[] scasillas=new String[]{"","","",""};
        for(int i=9;i>=0;i--)unirCasilla(scasillas,generarCasilla(ANSI_YELLOW_BACKGROUND,sur[i]));
        for(String s:scasillas)taboeiro+=s+"\n";

        return taboeiro;
    }
}
