package monopolinho.obxetos.casillas.casillacartas;

import monopolinho.axuda.Valor;
import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Carta;
import monopolinho.tipos.TipoCarta;
import monopolinho.tipos.TipoCartaAccion;
import monopolinho.tipos.TipoCasilla;

public class Comunidade extends CasillaCarta {
    public Comunidade() {
        super("COMUNIDADE");
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        System.out.println("Caiches na casilla de COMUNIDADE");
        xogo.getTurno().setPosicion(this);
        return super.pedirCarta(xogo);
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.COMUNIDADE;
    }


    @Override
    public void crearCartas() {
        engadirCarta(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_ALQUILAR_VILLA,
                "Alúgaslle aos teus compañeiros un chalet no Quinto Pino(Arteixo) durante unha semana. Paga 20000€ a cada xogador"
        ));
        engadirCarta(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_TERMAS,
                "Paga 50000€ por un fin de semana nas termas de Ourense"
        ));
        engadirCarta(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_BENEFICIOS_INTERNET,
                "Google comprouche o Monopolinho. Recibe 200000€."
        ));
        engadirCarta(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_DEVOLUCION_HACIENDA,
                "Hacienda estaba generosa, saleu a devolver. Recibes 50000€"
        ));
        engadirCarta(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_FRAUDE_IDENTIDAD,
                "Investingante por mover os marcos das fincas. Vai ó cárcere. Vai directamente sen pasar pola casilla de Saída e sen cobrar os "+ Valor.VOLTA_COMPLETA
        ));
        engadirCarta(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_RETROCEDER_PONTEPEDRA,
                "Retrocede ata PONTEPEDRA para ir facer unha churrascada. "
        ));
    }
}
