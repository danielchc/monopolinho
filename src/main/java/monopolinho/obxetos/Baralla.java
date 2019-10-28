package monopolinho.obxetos;

import monopolinho.axuda.Valor;
import monopolinho.tipos.TipoCarta;
import monopolinho.tipos.TipoCartaAccion;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Baralla {
    private ArrayList<Carta> cartas;
    private TipoCarta tipoBaralla;

    /**
     * Crea unha nova baralla
     * @param tipoCarta SORTE ou COMUNIDADE
     */
    public Baralla(TipoCarta tipoCarta){
        this.cartas=new ArrayList<>();
        this.tipoBaralla=tipoCarta;
        switch (tipoCarta){
            case SORTE:
                crearCartasSorte();
                break;
            case COMUNIDADE:
                crearCartasComunidade();
                break;
        }
    }

    /**
     * Baralla as cartas
     */
    public void barallar(){
        Collections.shuffle(cartas);
    }

    /**
     * Lista as cartas dispoñibles
     */
    public void listarCartas(){
        for(Carta c:this.cartas){
            System.out.println(c.getTipoCartaAccion());
        }
    }

    /**
     * @param i Número de carta
     * @return Carta na posicion @i
     */
    public Carta getCarta(int i){
        return cartas.get(i);
    }

    /**
     * Crea as cartas de SORTE
     */
    private void crearCartasSorte(){
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_VIAXE_PLACER,
                "Ganaches un viaxe de placer a MONELOS"
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_AUMENTO_BENS_IMUEBLES,
                "Pilloute Hacienda, ahora aumenta o pago por cada edificio que teñas.\nPaga 40000€ por casa, 115000€ por hotel, 20000€ por piscina e 75000€ por pista de deportes."
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_PRESIDENTE,
                "Saliche escollido concejal de urbanismo, soborta a cada xogador con 25000€"
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_VENDER_BILLETE,
                "Vendes o teu billete de avión para Castroverde nunha subasta de Internet. Cobra 50000€. "
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_RETROCEDER3,
                "Pillaches atasco en Virxe da Cerca, retrocede 3 casillas"
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_BOTE,
                "Tocou a rifa da excursión da túa filla, recibes 100000€"
        ));
    }

    /**
     * Crea as cartas de Comunidade
     */
    private void crearCartasComunidade() {
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_ALQUILAR_VILLA,
                "Alúgaslle aos teus compañeiros un chalet no Quinto Pino(Arteixo) durante unha semana. Paga 20000€ a cada xogador"
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_TERMAS,
                "Paga 50000€ por un fin de semana nas termas de Ourense"
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_BENEFICIOS_INTERNET,
                "Google comprouche o Monopolinho. Recibe 200000€."
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_DEVOLUCION_HACIENDA,
                "Hacienda estaba generosa, saleu a devolver. Recibes 50000€"
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_FRAUDE_IDENTIDAD,
                "Investingante por mover os marcos das fincas. Vai a Cárcel. Vai directamente sen pasar pola casilla de Saída e sen cobrar os "+ Valor.VOLTA_COMPLETA
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_RETROCEDER_PONTEPEDRA,
                "Retrocede ata Pontepedra para facer un trompos co jabalí. "
        ));
    }
}
