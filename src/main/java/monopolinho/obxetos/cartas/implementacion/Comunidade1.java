package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;

public class Comunidade1 extends CartaComunidade {

    public Comunidade1() {
        //8. Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 200000€ a cada jugador. 4
        super("Alúgaslle aos teus compañeiros un chalet no Quinto Pino(Arteixo) durante unha semana. Paga 20000€ a cada xogador");
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
