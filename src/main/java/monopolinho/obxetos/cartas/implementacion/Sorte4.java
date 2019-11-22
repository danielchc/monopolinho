package monopolinho.obxetos.cartas.implementacion;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Accion;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoTransaccion;

public class Sorte4 extends CartaComunidade {
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
        turno.engadirAccion(new Accion(TipoAccion.RECIBIR_PREMIO,PREMIO));

        return getMensaxe();
    }
}
