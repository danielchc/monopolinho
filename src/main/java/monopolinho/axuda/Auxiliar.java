package monopolinho.axuda;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public final class Auxiliar {
    /**
     * Este metodo comproba si un string é un número
     * @param str string a comprobar
     * @return true si é un número, false se non
     */
    public static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
