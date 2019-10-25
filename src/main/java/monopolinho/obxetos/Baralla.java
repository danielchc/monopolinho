package monopolinho.obxetos;

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
                "O aumento do impuesto sobre os bens inmobles afecta a todas as túas propiedades.\nPaga 400000€ por casa, 1150000€ por hotel, 200.000€ por piscina e 750000€ por pista de deportes."
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_PRESIDENTE,
                "Fuches escollido como presidente da xunta directiva. Paga a cada jugador 250000€"
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_VENDER_BILLETE,
                "Vendes o teu billete de avión para o Carbalinho nunha subaste de Internet. Cobra 500000€. "
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_RETROCEDER3,
                "Pillaches atasco en Virxe da Cerca, retrocede 3 casillas"
        ));
        cartas.add(new Carta(
                TipoCarta.SORTE,
                TipoCartaAccion.S_BOTE,
                "A POLO BOTEEE!! Ganaches o bote da Loteria, recibes 1000000€"
        ));
    }

    /**
     * Crea as cartas de Comunidade
     */
    private void crearCartasComunidade() {
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_ALQUILAR_VILLA,
                "Alugas os teus compañeiros un chalet no Quinto Pino(Arteixo) durante unha semana. Paga 200000€ a cada xogador"
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_TERMAS,
                "Paga 500000€ por un fin de semana nas termas de Ourense"
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_BENEFICIOS_INTERNET,
                "A túa compañía de Internet obtén beneficios. Recibe 2000000€."
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_DEVOLUCION_HACIENDA,
                "Saleu a devolver. Recibes 500000€"
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_FRAUDE_IDENTIDAD,
                "Investingante por fraude de identidade. Vai a Cárcel. Ve directamente sen pasar pola casilla de Saída e sen cobrar os 2000000€."
        ));
        cartas.add(new Carta(
                TipoCarta.COMUNIDADE,
                TipoCartaAccion.C_RETROCEDER_PONTEPEDRA,
                "Retrocede ata Pontepedra para facer un trompos co jabalí. "
        ));
    }
}
