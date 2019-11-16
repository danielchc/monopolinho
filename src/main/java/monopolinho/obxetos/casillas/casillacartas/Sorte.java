package monopolinho.obxetos.casillas.casillacartas;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Carta;
import monopolinho.tipos.TipoCarta;
import monopolinho.tipos.TipoCartaAccion;
import monopolinho.tipos.TipoCasilla;

public class Sorte extends CasillaCarta {
    public Sorte() {
        super("SORTE");
    }

    @Override
    public String interpretarCasilla(Xogo xogo, int valorDados) {
        System.out.println("Caiches na casilla de SORTE");
        xogo.getTurno().setPosicion(this);
        return super.pedirCarta(xogo);
    }

    @Override
    public TipoCasilla getTipoCasilla() {
        return TipoCasilla.SORTE;
    }

    @Override
    public void crearCartas() {
        engadirCarta(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_VIAXE_PLACER,
                "Ganaches un viaxe de placer a MONELOS"
        ));
        engadirCarta(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_AUMENTO_BENS_IMUEBLES,
                "Pilloute Hacienda, ahora aumenta o pago por cada edificio que teñas.\nPaga 40000€ por casa, 115000€ por hotel, 20000€ por piscina e 75000€ por pista de deportes."
        ));
        engadirCarta(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_PRESIDENTE,
                "Saliche escollido conselleiro de urbanismo, soborna a cada xogador con 25000€"
        ));
        engadirCarta(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_VENDER_BILLETE,
                "Vendes o teu billete de avión para Castroverde nunha subasta de Internet. Cobra 50000€. "
        ));
        engadirCarta(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_RETROCEDER3,
                "Pillaches atasco en Virxe da Cerca, retrocede 3 casillas"
        ));
        engadirCarta(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_BOTE,
                "Tocou a rifa da excursión da túa filla, recibes 100000€"
        ));
    }
}
