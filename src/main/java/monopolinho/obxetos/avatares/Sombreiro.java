package monopolinho.obxetos.avatares;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.obxetos.casillas.especiales.accion.Ir_Carcere;
import monopolinho.obxetos.casillas.especiales.cartas.CasillaCarta;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.EstadoXogador;
import monopolinho.tipos.TipoMovemento;
import monopolinho.tipos.Zona;
/**
 * @author David Carracedo
 * @author Daniel Chenel
 */

public class Sombreiro extends Avatar{

    public Sombreiro(Xogador xogador) {
        super(xogador);
    }

    public Sombreiro(Xogador xogador,String id) {
        super(xogador,id);
    }

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
            throw new MonopolinhoGeneralException("Non podes lanzar máis veces. Tes que pasar de turno");
        }

        Xogo.consola.imprimir("Atopaste en "+turno.getPosicion().getNome());

        if(valorDados>4){
            if (zona==Zona.NORTE || zona==Zona.SUR){
                zona=(zona==Zona.NORTE)?Zona.ESTE:Zona.OESTE;
                next=taboeiro.getCasillas(zona).get(0);
                Xogo.consola.imprimir("Movicheste ao lado "+ zona + " á casilla " + next.getNome() +". "+next.interpretarCasilla(xogo,valorDados));
                valorDados--;
            }

            posicion=taboeiro.getCasillas(zona).indexOf(turno.getPosicion());
            zonaInicial=zona;

            for(int i=1;i<=valorDados;i++){
                zona=(zona==Zona.ESTE)?Zona.OESTE:Zona.ESTE;
                if(zonaInicial==Zona.ESTE){
                    cpos=Math.floorMod(((zona==Zona.ESTE)?(i+posicion):8-(i+posicion)),9);
                }else{
                    cpos=Math.floorMod(((zona==Zona.ESTE)?8-(i+posicion):(i+posicion)),9);
                }
                next=taboeiro.getCasillas(zona).get(cpos);
                Xogo.consola.imprimir("Movicheste ao lado "+ zona + " á casilla " + next.getNome() + ". "+next.interpretarCasilla(xogo,valorDados));
                if(next instanceof Ir_Carcere)return;
                if(turno.getXogador().estadoXogador()== EstadoXogador.TEN_DEBEDAS)return;
                if(next instanceof CasillaCarta)if(((CasillaCarta)next).getUltimaCarta().getMovese())return;

            }
        }else{
            System.out.println("Sacaches menos de 4, desfacendo todo o que fixeches no turno");
            Xogo.consola.imprimirNegrita(turno.desfacer(xogo));
        }

    }

    @Override
    public TipoMovemento getTipo() {
        return TipoMovemento.SOMBREIRO;
    }
}
