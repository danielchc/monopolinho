package monopolinho.obxetos.casillas.especiales;

import monopolinho.obxetos.casillas.Casilla;
/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class Especial extends Casilla {
    /**
     * Crea unha nova instancia de casilla
     *
     * @param nome Nome da casilla
     */
    public Especial(String nome) {
        super(nome);
    }
}
