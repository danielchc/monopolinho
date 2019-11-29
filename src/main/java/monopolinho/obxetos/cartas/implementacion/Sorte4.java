package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.accions.Accion;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoTransaccion;

public class Sorte4 extends CartaSorte {
    private final float PREMIO=50000f;
    public Sorte4() {
        //3. Vendes tu billete de avión para Terrassa en una subasta por Internet. Cobra 500000€.
        super("Vendes o teu billete de avión para CASTROVERDE nunha subasta de Internet. Cobra 50000€. ");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        xogador.engadirDinheiro( PREMIO, TipoTransaccion.BOTE_PREMIO);
        turno.engadirAccion(new AccionCarta(this));

        return getMensaxe();
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        xogo.getTurno().getXogador().engadirDinheiro( PREMIO, TipoTransaccion.BOTE_PREMIO);
        return "Quitaronseche os "+PREMIO+ " de vender o billete de avión a CASTROVERDE";
    }
}
