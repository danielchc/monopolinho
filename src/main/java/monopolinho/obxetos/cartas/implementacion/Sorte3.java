package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.excepcions.xogador.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public final class Sorte3 extends CartaSorte {
    private final float PREZO=25000f;
    public Sorte3() {
        //10. Has sido elegido presidente de la junta directiva. Paga a cada jugador 250000€.
        super("Saliche escollido conselleiro de urbanismo, soborna a cada xogador con 25000€");
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoSinDinheiro {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        if(!xogador.quitarDinheiro(PREZO*(xogo.getNumeroXogadores()-1), TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiro(getMensaxe() + ". Non tes suficiente diñeiro para pagar os xogadores",xogador);
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
        return "Devolvenche "+PREZO +" cada xogador do soborno";
    }
}
