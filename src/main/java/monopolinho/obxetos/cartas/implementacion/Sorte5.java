package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoTransaccion;

public class Sorte5 extends CartaComunidade {
    public Sorte5() {
        //11. ¡Hora punta de tráfico! Retrocede tres casillas.
        super( "Pillaches atasco en Virxe da Cerca, retrocede 3 casillas");
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Xogo.consola.imprimir(getMensaxe());
        xogo.moverModoNormal(-3);
        return "";
    }
}
