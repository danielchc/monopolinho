package monopolinho.obxetos.avatares;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoMovemento;
import monopolinho.tipos.Zona;

import java.util.ArrayList;

public class Esfinxe extends Avatar{

    public Esfinxe(Xogador xogador) {
        super(xogador);
    }

    public void moverEnAvanzado(Xogo xogo, int valorDados) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Taboeiro taboeiro=xogo.getTaboeiro();
        Casilla next;
        Zona zona=turno.getPosicion().getZona();
        int posicion;
        int cpos=0;
        Xogo.consola.imprimir("Atopaste en "+turno.getPosicion().getNome());
        if(zona==Zona.ESTE){
            turno.setPosicion(taboeiro.getCasillas(Zona.SUR).get(0));
            zona=Zona.SUR;
        }
        if(zona==Zona.OESTE){
            turno.setPosicion(taboeiro.getCasillas(Zona.SUR).get(1));
            zona=Zona.SUR;
        }
        posicion=taboeiro.getCasillas(zona).indexOf(turno.getPosicion());
        for(int i=1;i<=valorDados;i++){
            zona=(zona==Zona.NORTE)?Zona.SUR:Zona.NORTE;
            cpos=Math.floorMod(((zona==Zona.NORTE)?10-(i+posicion):(i+posicion)),11);
            next=taboeiro.getCasillas(zona).get(cpos);
            Xogo.consola.imprimir("Movecheste o lado "+ zona + " a casilla " + next.getNome());
            Xogo.consola.imprimir(next.interpretarCasilla(xogo,valorDados));
            if(next.getTipoCasilla()== TipoCasilla.IRCARCERE)return;
        }


    }

    @Override
    public TipoMovemento getTipo() {
        return TipoMovemento.ESFINXE;
    }
}
