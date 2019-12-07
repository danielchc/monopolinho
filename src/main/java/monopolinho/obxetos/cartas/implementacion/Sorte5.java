package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.excepcions.MonopolinhoException;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Sorte5 extends CartaSorte {
    public Sorte5() {
        //11. ¡Hora punta de tráfico! Retrocede tres casillas.
        super( "Pillaches atasco en Virxe da Cerca, retrocede 3 casillas");
        this.setMovese(true);
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Xogo.consola.imprimir(getMensaxe());
        xogo.moverModoNormal(-3);
        return "";
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        return "";
    }
}
