package monopolinho.obxetos.avatares;

import monopolinho.axuda.ReprTab;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.casillas.Casilla;
import monopolinho.tipos.TipoMovemento;

public class Sombreiro extends Avatar{

    public Sombreiro(Xogador xogador) {
        super(xogador,TipoMovemento.SOMBREIRO);
    }

    public void interpretarMovementoAvanzado(Xogo xogo,int valorDados) {

    }
}
