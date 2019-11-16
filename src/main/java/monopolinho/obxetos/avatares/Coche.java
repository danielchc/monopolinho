package monopolinho.obxetos.avatares;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoMovemento;

public class Coche extends Avatar{

    public Coche(Xogador xogador) {
        super(xogador,TipoMovemento.COCHE);
    }

    public void interpretarMovementoAvanzado(Xogo xogo,int valorDados) {
        Casilla next;
        Turno turno=xogo.getTurno();
        int cPos=turno.getPosicion().getPosicionIndex();
        System.out.println("Encontraste en "+ turno.getPosicion().getNome());

        if(turno.getVecesTiradas()>4){
            ReprTab.imprimirErro("Non podes lanzar máis veces.Tes que pasar de turno");
            turno.setPodeLanzar(false);
            return;
        }
        if(valorDados>4){
            turno.setPodeLanzar(true);
            next=xogo.getTaboeiro().getCasilla(cPos+valorDados);
            System.out.println("Avanzaches "+valorDados+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(xogo,valorDados));
            if(turno.getVecesTiradas()<4)System.out.println("Podes lanzar outra vez");
        }else{
            turno.setPodeLanzar(false);
            turno.getXogador().setTurnosInvalidado(2);
            next=xogo.getTaboeiro().getCasilla(cPos-valorDados);
            System.out.println("Retrocediches "+valorDados+" posicións. Caiches en "+ next.getNome() + ". " +next.interpretarCasilla(xogo,valorDados));
            System.out.println("Non podes lanzar nos seguintes dous turnos.");
        }
    }
}
