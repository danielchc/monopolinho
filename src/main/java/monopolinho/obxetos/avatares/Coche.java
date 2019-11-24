package monopolinho.obxetos.avatares;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoMovemento;

public class Coche extends Avatar{

    public Coche(Xogador xogador) {
        super(xogador);
    }

    public void moverEnAvanzado(Xogo xogo, int valorDados) throws MonopolinhoException {
        Casilla next;
        Turno turno=xogo.getTurno();
        int cPos=turno.getPosicion().getPosicionIndex();
        Xogo.consola.imprimir("Encontraste en "+ turno.getPosicion().getNome());

        if(turno.getVecesTiradas()>4){
            Xogo.consola.imprimirErro("Non podes lanzar máis veces.Tes que pasar de turno");
            turno.setPodeLanzar(false);
            return;
        }
        if(valorDados>4){
            turno.setPodeLanzar(true);
            next=xogo.getTaboeiro().getCasilla(cPos+valorDados);
            Xogo.consola.imprimir("Avanzaches "+valorDados+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(xogo,valorDados));
            if(turno.getVecesTiradas()<4)Xogo.consola.imprimir("Podes lanzar outra vez");
        }else{
            turno.setPodeLanzar(false);
            turno.getXogador().setTurnosInvalidado(2);
            next=xogo.getTaboeiro().getCasilla(cPos-valorDados);
            Xogo.consola.imprimir("Retrocediches "+valorDados+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(xogo,valorDados));
            Xogo.consola.imprimir("Non podes lanzar nos seguintes dous turnos.");
        }
    }

    @Override
    public TipoMovemento getTipo() {
        return TipoMovemento.COCHE;
    }
}
