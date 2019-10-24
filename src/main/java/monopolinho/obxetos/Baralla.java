package monopolinho.obxetos;

import monopolinho.tipos.TipoCarta;
import monopolinho.tipos.TipoCartaAccion;

import java.util.ArrayList;
import java.util.Collections;

public class Baralla {
    private ArrayList<Carta> cartas;
    private TipoCarta tipoBaralla;
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
    public void barallar(){
        Collections.shuffle(cartas);
    }
    public Carta getCarta(int i){
        return cartas.get(i);
    }
    private void crearCartasSorte(){
        cartas.add(new Carta(TipoCarta.SORTE, TipoCartaAccion.S_VIAXE_PLACER,"Viaxe de placer a MONELOS"));
        cartas.add(new Carta(TipoCarta.SORTE, TipoCartaAccion.S_AUMENTO_BENS_IMUEBLES,""));
        cartas.add(new Carta(TipoCarta.SORTE, TipoCartaAccion.S_PRESIDENTE,""));
        cartas.add(new Carta(TipoCarta.SORTE, TipoCartaAccion.S_VENDER_BILLETE,""));
        cartas.add(new Carta(TipoCarta.SORTE, TipoCartaAccion.S_RETROCEDER3,""));
        cartas.add(new Carta(TipoCarta.SORTE, TipoCartaAccion.S_BOTE,""));
    }
    private void crearCartasComunidade(){

    }
}
