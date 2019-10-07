package monopolinho.axuda;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Valor {
    public static final float FORTUNA_INCIAL=100000;
    public static final float SAIR_CARCERE=5000;
    public static final float IMPOSTO_BARATO=100;
    public static final float IMPOSTO_CARO=200;

    public static final float VALOR_GRUPO_OCRE=12000;
    public static final float VALOR_GRUPO_CYAN=15600;
    public static final float VALOR_GRUPO_VIOLETA=20280;
    public static final float VALOR_GRUPO_AMARILLO=26360;
    public static final float VALOR_GRUPO_ROJO=34270;
    public static final float VALOR_GRUPO_BLANCO=44550;
    public static final float VALOR_GRUPO_VERDE=57920;
    public static final float VALOR_GRUPO_AZUL=75300;

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
    public static final String[] Cores=new String[]{
            "\u001B[0m",
            "\u001B[30m","\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m",
            "\u001B[1;30m","\u001B[1;31m","\u001B[1;32m","\u001B[1;33m","\u001B[1;34m","\u001B[1;35m",
            "\u001B[1;36m","\u001B[1;37m","\u001B[40m","\u001B[41m","\u001B[42m","\u001B[43m",
            "\u001B[0;103m","\u001B[44m","\u001B[45m","\u001B[46m","\u001B[47m","\u001B[0;107m"
    };

}
