package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.TipoTransaccion;

public class Comunidade6 extends CartaComunidade {

    public Comunidade6() {
        //4. Hacienda 50000€
        super("Hacienda estaba generosa, saleu a devolver. Recibes 50000€");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        turno.setPosicion(xogo.getTaboeiro().getCasilla(10));
        xogador.meterNoCarcere();
        turno.setPodeLanzar(false);
        return getMensaxe();
    }
}
