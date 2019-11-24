package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Accion;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoTransaccion;

public class Comunidade1 extends CartaComunidade {

    public Comunidade1() {
        //8. Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 200000€ a cada jugador. 4
        super("Alúgaslle aos teus compañeiros un chalet no Quinto Pino(Arteixo) durante unha semana. Paga 20000€ a cada xogador");
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoSinDinheiro {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        float dinheiroPagar=20000f;
        if(!xogador.quitarDinheiro(dinheiroPagar*(xogo.getNumeroXogadores()-1), TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiro(getMensaxe()+ ". Non tes suficiente diñeiro para pagar os xogadores",xogador);
        }
        for(Xogador x:xogo.getXogadores()){
            if(!x.equals(turno.getXogador()))
                x.engadirDinheiro(dinheiroPagar, TipoTransaccion.OTROS);
        }
        turno.engadirAccion(new Accion(TipoAccion.PAGAR_XOGADORES,dinheiroPagar));
        return getMensaxe();
    }
}
