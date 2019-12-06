package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoTransaccion;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Comunidade2 extends CartaComunidade {
    private final float PREZO=50000;
    public Comunidade2() {
        super("Paga 50000€ por un fin de semana nas termas de Ourense");
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        if(!xogador.quitarDinheiro(PREZO, TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiro(getMensaxe()+ ". Non tes suficiente diñeiro para pagar a acción",xogador);
        }
        turno.engadirAccion(new AccionCarta(this));
        return getMensaxe();
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().engadirDinheiro(PREZO, TipoTransaccion.OTROS);
        return "Devolveronseche "+ PREZO+ " de pasar un finde semana na termas de Ourense";
    }
}
