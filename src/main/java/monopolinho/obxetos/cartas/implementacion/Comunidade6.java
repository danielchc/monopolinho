package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoException;

public class Comunidade6 extends CartaComunidade {
    private final float PREMIO=50000;

    public Comunidade6() {
        //4. Hacienda 50000€
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
