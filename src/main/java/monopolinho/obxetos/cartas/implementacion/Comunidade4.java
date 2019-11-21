package monopolinho.obxetos.cartas.implementacion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoTransaccion;

public class Comunidade4 extends CartaComunidade {

    public Comunidade4() {
        //2. Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2000000€.
        super("Investingante por mover os marcos das fincas. Vai ó cárcere. " +
                "Vai directamente sen pasar pola casilla de Saída e sen cobrar os "
                + Valor.VOLTA_COMPLETA
        );
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        turno.setPosicion((xogo.getTaboeiro().getCasilla(8)));
        return getMensaxe() + turno.getPosicion().interpretarCasilla(xogo,0);
    }
}
