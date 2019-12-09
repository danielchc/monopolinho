package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.excepcions.MonopolinhoException;

public final class Comunidade6 extends CartaComunidade {

    public Comunidade6() {
        super("Retrocede a Pontepedra para facer unha Churrascada");
        this.setMovese(true);
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        turno.setPosicion((xogo.getTaboeiro().getCasilla(8)));
        return getMensaxe() +" "+ turno.getPosicion().interpretarCasilla(xogo,0);
    }
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        return "";
    }
}
