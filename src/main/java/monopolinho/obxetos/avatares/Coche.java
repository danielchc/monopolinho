package monopolinho.obxetos.avatares;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoMovemento;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public final class Coche extends Avatar{

    public Coche(Xogador xogador) {
        super(xogador);
    }

    public Coche(Xogador xogador,String id) {
        super(xogador,id);
    }

    /**
     * Establece o movemento avanzado dos xogaodres
     * @param xogo
     * @param valorDados
     * @throws MonopolinhoException
     */
    public void moverEnAvanzado(Xogo xogo, int valorDados) throws MonopolinhoException {
        Casilla next;
        Turno turno=xogo.getTurno();
        int cPos=turno.getPosicion().getPosicionIndex();
        if(turno.getVecesTiradas()>4){
            turno.setPodeLanzar(false);
            throw new MonopolinhoGeneralException("Non podes lanzar máis veces.Tes que pasar de turno");
        }

        Xogo.consola.imprimir("Encontraste en "+ turno.getPosicion().getNome());
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
