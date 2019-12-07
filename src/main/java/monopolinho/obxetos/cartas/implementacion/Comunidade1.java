package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.excepcions.xogador.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Comunidade1 extends CartaComunidade {
    private final float PREZO=20000f;

    public Comunidade1() {
        //8. Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 200000€ a cada jugador. 4
        super("Alúgaslle aos teus compañeiros un chalet no Quinto Pino(Arteixo) durante unha semana. Paga 20000€ a cada xogador");
    }

    /**
     * Establece acción da carta
     * @param xogo
     * @return
     * @throws MonopolinhoException
     */
    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        if(!xogador.quitarDinheiro(PREZO*(xogo.getNumeroXogadores()-1), TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiro(getMensaxe()+ ". Non tes suficiente diñeiro para pagar os xogadores",xogador);
        }
        for(Xogador x:xogo.getXogadores()){
            if(!x.equals(turno.getXogador()))
                x.engadirDinheiro(PREZO, TipoTransaccion.OTROS);
        }
        turno.engadirAccion(new AccionCarta(this));
        return getMensaxe();
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        xogador.engadirDinheiro(PREZO*(xogo.getNumeroXogadores()-1), TipoTransaccion.OTROS);
        for(Xogador x:xogo.getXogadores()){
            if(!x.equals(turno.getXogador()))
                x.quitarDinheiro(PREZO, TipoTransaccion.OTROS);
        }
        return "Devolvenche "+PREZO +" cada xogador en concepto de Alugar a villa no quinto pino";
    }
}
