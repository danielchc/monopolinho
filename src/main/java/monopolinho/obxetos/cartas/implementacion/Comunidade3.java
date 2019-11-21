package monopolinho.obxetos.cartas.implementacion;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoTransaccion;

public class Comunidade3 extends CartaComunidade {

    public Comunidade3() {
        //4. Tu compañía de Internet obtiene beneficios. Recibe 2000000€.
        super("Google comprouche o Monopolinho. Recibe 200000€.");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        xogador.engadirDinheiro( 200000, TipoTransaccion.BOTE_PREMIO);
        return getMensaxe();
    }
}
