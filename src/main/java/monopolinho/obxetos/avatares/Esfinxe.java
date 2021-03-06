package monopolinho.obxetos.avatares;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.especiales.accion.Ir_Carcere;
import monopolinho.obxetos.casillas.especiales.cartas.CasillaCarta;
import monopolinho.excepcions.MonopolinhoException;
import monopolinho.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoMovemento;
import monopolinho.tipos.Zona;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public final class Esfinxe extends Avatar{

    public Esfinxe(Xogador xogador) {
        super(xogador);
    }

    public Esfinxe(Xogador xogador,String id) {
        super(xogador,id);
    }

    /**
     * Establece o movemento avanzado dos xogaodres
     * @param xogo
     * @param valorDados
     * @throws MonopolinhoException
     */
    public void moverEnAvanzado(Xogo xogo, int valorDados) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Taboeiro taboeiro=xogo.getTaboeiro();
        Casilla next;
        Zona zona=turno.getPosicion().getZona();
        Zona zonaInicial;
        int posicion;
        int cpos=0;
        if(turno.getVecesTiradas()>3){
            turno.setPodeLanzar(false);
            throw new MonopolinhoGeneralException("Non podes lanzar máis veces.Tes que pasar de turno");
        }

        Xogo.consola.imprimir("Atopaste en "+turno.getPosicion().getNome());

        if(valorDados>4){
            if (zona==Zona.ESTE || zona==Zona.OESTE){
                zona=(zona==Zona.ESTE)?Zona.SUR:Zona.NORTE;
                next=taboeiro.getCasillas(zona).get(1);
                Xogo.consola.imprimir("Movicheste ao lado "+ zona + " á casilla " + next.getNome() +". "+next.interpretarCasilla(xogo,valorDados));
                valorDados--;
            }

            posicion=taboeiro.getCasillas(zona).indexOf(turno.getPosicion());
            zonaInicial=zona;

            for(int i=1;i<=valorDados;i++){
                zona=(zona==Zona.NORTE)?Zona.SUR:Zona.NORTE;
                if(zonaInicial==Zona.NORTE){
                    cpos=Math.floorMod(((zona==Zona.NORTE)?(i+posicion):10-(i+posicion)),11);
                }else{
                    cpos=Math.floorMod(((zona==Zona.NORTE)?10-(i+posicion):(i+posicion)),11);
                }
                next=taboeiro.getCasillas(zona).get(cpos);
                Xogo.consola.imprimir("Movicheste ao lado "+ zona + " á casilla " + next.getNome() + ". "+next.interpretarCasilla(xogo,valorDados));
                if(next instanceof Ir_Carcere)return;
                if(turno.getXogador().estadoXogador()== EstadoXogador.TEN_DEBEDAS)return;
                if(next instanceof CasillaCarta)if(((CasillaCarta)next).getUltimaCarta().getMovese())return;
            }
            turno.setPodeLanzar(true);
        }else{
            System.out.println("Sacaches menos de 4, desfacendo todo o que fixeches no turno");
            Xogo.consola.imprimirNegrita(turno.desfacer(xogo));
        }

    }

    @Override
    public TipoMovemento getTipo() {
        return TipoMovemento.ESFINXE;
    }
}
