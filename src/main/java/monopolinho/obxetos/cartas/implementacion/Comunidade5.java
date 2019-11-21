package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.TipoTransaccion;

public class Comunidade5 extends CartaComunidade {

    public Comunidade5() {
        //4. Hacienda 50000€
        super("Hacienda estaba generosa, saleu a devolver. Recibes 50000€");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        xogador.engadirDinheiro( 50000, TipoTransaccion.BOTE_PREMIO);
        return getMensaxe();
    }
}
