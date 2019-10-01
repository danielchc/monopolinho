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

    public  ASCII(){

    }
    private String padTexto(String texto){
        int s=(15-texto.length())/2;
        String sp="";
        for (int i=0;i<s;i++)sp+=" ";
        return (sp+texto+sp).substring(1,14);
    }
    public String[] generarCasilla(String color,String valor){
        return new String[]{
                color + padTexto("") + ANSI_RESET ,
                color + padTexto(valor) + ANSI_RESET ,
                color + padTexto("") + ANSI_RESET ,
        };
    }
    public String[] casillaVacia(){
        return new String[]{padTexto(""),padTexto(""),padTexto("")};
    }

    public String[] unirCasillaHorizontal(String[] casillas,String[] casilla){
        for (int i=0;i<casillas.length;i++){
            casillas[i]+=casilla[i];
        }
        return casillas;
    }


    public String imprimirTableiro(){

        String[] sur=new String[]{"SAIDA","2","3","4","5","6","7","8","9","CARCEL"};
        String[] norte=new String[]{"PARKING","LUGO","SANTIAGO","ORENSE","PONTEVEDRA","VIGO","FERROL","CORUNA","TUI","PACARCEL"};
        String[] este=new String[]{"LEON","VALLADOLID","SALAMANCA","MADRID","ALBACETE","VALENCIA","BARCELONA","SANTANDER","BILBAO","TARRAGONA"};
        String[] oeste=new String[]{"LEON","VALLADOLID","SALAMANCA","MADRID","ALBACETE","VALENCIA","BARCELONA","SANTANDER","BILBAO","TARRAGONA"};

        String taboeiro="";

        //NORTE
        String[] ncasillas=new String[]{"","",""};
        for(int i=0;i<10;i++)ncasillas=unirCasillaHorizontal(ncasillas,generarCasilla(ANSI_GREEN_BACKGROUND,norte[i]));
        for(String s:ncasillas)taboeiro+=s+"\n";

        //ESTE OESTE
        String[] ocasillas;
        for(int i=9;i>=0;i--){
            ocasillas=new String[]{"","",""};
            unirCasillaHorizontal(ocasillas,generarCasilla(ANSI_RED_BACKGROUND,oeste[i]));
            for(int j=0;j<8;j++)unirCasillaHorizontal(ocasillas,casillaVacia());
            unirCasillaHorizontal(ocasillas,generarCasilla(ANSI_PURPLE_BACKGROUND,este[9-i]));
            for(String s:ocasillas)taboeiro+=s+"\n";
        }

        //SUR
        String[] scasillas=new String[]{"","",""};
        for(int i=9;i>=0;i--)scasillas=unirCasillaHorizontal(scasillas,generarCasilla(ANSI_YELLOW_BACKGROUND,sur[i]));
        for(String s:scasillas)taboeiro+=s+"\n";

        return taboeiro;
    }
}
