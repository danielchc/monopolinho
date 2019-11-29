package monopolinho.obxetos.cartas.implementacion;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.accions.Accion;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoAccion;

public class Comunidade4 extends CartaComunidade {
    public Comunidade4() {
        //2. Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2000000€.
        super("Investingante por mover os marcos das fincas. Vai ó cárcere. " +
                "Vai directamente sen pasar pola casilla de Saída e sen cobrar os "
                + Valor.VOLTA_COMPLETA
        );
        this.setMovese(true);
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        turno.setPosicion(xogo.getTaboeiro().getCasilla(10));
        xogador.meterNoCarcere();
        turno.engadirAccion(new AccionCarta(this));
        turno.setPodeLanzar(false);
        return getMensaxe();
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().sairDoCarcere();
        return "Declaronte inocente de mover os marcos, xa non te atopas no cárcere";
    }
}
