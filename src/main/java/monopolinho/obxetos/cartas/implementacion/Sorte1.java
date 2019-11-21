package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;

public class Sorte1 extends CartaComunidade {
    public Sorte1() {
        //2. Decides hacer un viaje de placer. Avanza hasta Cádiz.
        super("Ganaches un viaxe de placer a MONELOS");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        float dinheiroPagar;
        turno.setPosicion((xogo.getTaboeiro().getCasilla(13)));
        return getMensaxe()+" "+(turno.getPosicion().interpretarCasilla(xogo,0));
    }
}
