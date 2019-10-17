package monopolinho.axuda;


public class Valor {


    /*
        Esto son os precios de cada grupo.
        Cada grupo é un 30% máis caro que o anterior no sentido horario.
     */
    public static final float VALOR_GRUPO_OCRE=120000;
    public static final float VALOR_GRUPO_CYAN=VALOR_GRUPO_OCRE*1.6f;
    public static final float VALOR_GRUPO_VIOLETA=VALOR_GRUPO_CYAN*1.3f;
    public static final float VALOR_GRUPO_AMARILLO=VALOR_GRUPO_VIOLETA*1.3f;
    public static final float VALOR_GRUPO_ROJO=VALOR_GRUPO_AMARILLO*1.3f;
    public static final float VALOR_GRUPO_BLANCO=VALOR_GRUPO_ROJO*1.3f;
    public static final float VALOR_GRUPO_VERDE=VALOR_GRUPO_BLANCO*1.3f;
    public static final float VALOR_GRUPO_AZUL=VALOR_GRUPO_VERDE*1.3f;


    /* Fortuna inicial coa que empeza a partida cada xogador */
    public static final float FORTUNA_INCIAL=(VALOR_GRUPO_OCRE+VALOR_GRUPO_CYAN+VALOR_GRUPO_VIOLETA+VALOR_GRUPO_AMARILLO+VALOR_GRUPO_ROJO+VALOR_GRUPO_BLANCO+VALOR_GRUPO_VERDE+VALOR_GRUPO_AZUL);

    /* Fortuna que recibe un xogador se completa unha volta ao tableiro */
    public static final float VOLTA_COMPLETA=FORTUNA_INCIAL/22.0f;


    /*
        Esto son os valores que se deben pagar.
        SAIR_CARCERE págase á banca.
        IMPOSTOS páganse ao bote do parking.
     */
    public static final float SAIR_CARCERE=VOLTA_COMPLETA*0.25f;
    public static final float IMPOSTO_BARATO=VOLTA_COMPLETA/2.0f;
    public static final float IMPOSTO_CARO=VOLTA_COMPLETA;


    /*
        Valores casillas transporte.
        Igual que o valor por unha volta completa
    */
    public static final float VALOR_TRANSPORTE=VOLTA_COMPLETA;
    /*
        Valores casillas servicio.
        75% do valor volta completa.
    */
    public static final float VALOR_SERVICIO=VOLTA_COMPLETA*0.75f;
    /*
        Factor de multiplicacion para calcular a hipoteca.
        Metade do solar no que se edifica.
     */
    public static final float FACTOR_HIPOTECA=0.5f;
    /*
        Factor para calcular o alquiler dos solares.
        10% do valor inicial.
     */
    public static final float FACTOR_ALQUILER=0.1f;
    /*
        Factor para calcular a cantidade a pagar se se cae nunha casilla dun oponente.
     */
    public static final float FACTOR_PAGO_ALQUILER=2.0f;    //valor a pagar=valor solar*FACTOR_ALQUILER*FACTOR_PAGO_ALQUILER
    /*
        Cantidade a pagar ao caer en servicios.
     */
    public static final float USO_SERVICIO=VOLTA_COMPLETA*0.002f;

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
