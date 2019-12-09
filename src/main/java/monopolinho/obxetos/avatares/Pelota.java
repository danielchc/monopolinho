package monopolinho.obxetos.avatares;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.especiales.accion.Ir_Carcere;
import monopolinho.obxetos.casillas.especiales.cartas.CasillaCarta;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoMovemento;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public final class Pelota extends Avatar{

    public Pelota(Xogador xogador) {
        super(xogador);
    }

    public Pelota(Xogador xogador,String id) {
        super(xogador,id);
    }

    public void moverEnAvanzado(Xogo xogo, int valorDados) throws MonopolinhoException {
        Casilla next;
        Turno turno=xogo.getTurno();
        int cPos=turno.getPosicion().getPosicionIndex();

        Xogo.consola.imprimir("Encontraste en "+ turno.getPosicion().getNome());
        if(valorDados>4){
            for(int i=5;i<=valorDados;i+=2){
                next=xogo.getTaboeiro().getCasilla(cPos+i);
                Xogo.consola.imprimir("Avanzaches "+i+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(xogo,i));
                if(next instanceof Ir_Carcere)return;
                if(turno.getXogador().estadoXogador()== EstadoXogador.TEN_DEBEDAS)return;
            }
        }else{
            for(int i=1;i<=valorDados;i+=2){
                next=xogo.getTaboeiro().getCasilla(cPos+i);
                Xogo.consola.imprimir("Retrocedeches "+i+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(xogo,i));
                if(next instanceof Ir_Carcere)return;
                if(turno.getXogador().estadoXogador()==EstadoXogador.TEN_DEBEDAS)return;
                if(next instanceof CasillaCarta)if(((CasillaCarta)next).getUltimaCarta().getMovese())return;
            }
        }
    }

    @Override
    public TipoMovemento getTipo() {
        return TipoMovemento.PELOTA;
    }
}
