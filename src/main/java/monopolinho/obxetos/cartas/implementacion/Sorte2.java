package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.accions.AccionCarta;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

public class Sorte2 extends CartaSorte {
    private final float PRECIO_CASA=40000.0f;
    private final float PRECIO_HOTEL=115000.0f;
    private final float PRECIO_PISCINA=20000.0f;
    private final float PRECIO_PISTA_DEPORTES=75000.0f;
    public Sorte2() {
        //8. El  aumento  del  impuesto  sobre  bienes  inmuebles  afecta  a  todas  tus  propiedades.  Paga  400000€  por casa, 1150000M€ por hotel, 200.000€ por piscina y 750000€ por pista de deportes.
        super("Pilloute Hacienda, ahora aumenta o pago por cada edificio que teñas.\n" +
                "Paga 40000€ por casa, 115000€ por hotel, 20000€ por piscina e 75000€ por pista de deportes."
        );
    }

    @Override
    public String accion(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        float aPagar=0;
        for (Propiedade p:xogador.getPropiedades()){
            if(p instanceof Solar){
                Solar c=(Solar)p;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.CASA)*PRECIO_CASA;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*PRECIO_HOTEL;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*PRECIO_PISCINA;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*PRECIO_PISTA_DEPORTES;
            }
        }
        if(!xogador.quitarDinheiro(aPagar, TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiro(getMensaxe()+ ". Non tes suficiente diñeiro para pagar "+aPagar,xogador);
        }

        turno.engadirAccion(new AccionCarta(this));
        return getMensaxe() + " Tes que pagar "+aPagar;
    }

    @Override
    public String desfacer(Xogo xogo) throws MonopolinhoException {
        Turno turno=xogo.getTurno();
        Xogador xogador=turno.getXogador();
        float aPagar=0;
        for (Propiedade p:xogador.getPropiedades()){
            if(p instanceof Solar){
                Solar c=(Solar)p;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.CASA)*PRECIO_CASA;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*PRECIO_HOTEL;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*PRECIO_PISCINA;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*PRECIO_PISTA_DEPORTES;
            }
        }
        xogador.engadirDinheiro(aPagar, TipoTransaccion.TASAS);
        return "Devolveronseche os "+aPagar+ " de impostos de propiedades";
    }
}
