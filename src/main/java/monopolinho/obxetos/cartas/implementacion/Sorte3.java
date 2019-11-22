package monopolinho.obxetos.cartas.implementacion;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Accion;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiroException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

public class Sorte3 extends CartaComunidade {
    public Sorte3() {
        //10. Has sido elegido presidente de la junta directiva. Paga a cada jugador 250000€.
        super("Saliche escollido conselleiro de urbanismo, soborna a cada xogador con 25000€");
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoSinDinheiroException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        float dinheiroPagar=25000f;
        if(!xogador.quitarDinheiro(dinheiroPagar*(xogo.getNumeroXogadores()-1), TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiroException(getMensaxe() + ". Non tes suficiente diñeiro para pagar os xogadores",xogador);
        }
        for(Xogador x:xogo.getXogadores()){
            if(!x.equals(turno.getXogador()))
                x.engadirDinheiro(dinheiroPagar, TipoTransaccion.OTROS);
        }
        turno.engadirAccion(new Accion(TipoAccion.PAGAR_XOGADORES,dinheiroPagar));
        return getMensaxe();
    }
}
