package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.obxetos.excepcions.MonopolinhoException;

public class Sorte1 extends CartaSorte {
    public Sorte1() {
        //2. Decides hacer un viaje de placer. Avanza hasta Cádiz.
        super("Ganaches un viaxe de placer a MONELOS");
        this.setMovese(true);
    }

    @Override
    public String accion(Xogo xogo)  throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        turno.setPosicion((xogo.getTaboeiro().getCasilla(13)));
        return getMensaxe()+" "+(turno.getPosicion().interpretarCasilla(xogo,0));
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        return "";
    }
}
