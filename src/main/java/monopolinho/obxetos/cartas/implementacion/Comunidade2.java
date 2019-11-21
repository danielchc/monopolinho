package monopolinho.obxetos.cartas.implementacion;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoTransaccion;

public class Comunidade2 extends CartaComunidade {

    public Comunidade2() {
        super("Paga 50000€ por un fin de semana nas termas de Ourense");
    }

    @Override
    public String accion(Xogo xogo) {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        if(!xogador.quitarDinheiro(50000, TipoTransaccion.TASAS)){
            ReprTab.imprimirErro(getMensaxe()+ ". Non tes suficiente diñeiro para pagar a acción");
            xogador.setEstadoXogador(EstadoXogador.TEN_DEBEDAS);
            return "";
        }
        return getMensaxe();
    }
}
