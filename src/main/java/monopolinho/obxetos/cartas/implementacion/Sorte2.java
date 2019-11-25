package monopolinho.obxetos.cartas.implementacion;

import monopolinho.interfaz.Xogo;
import monopolinho.obxetos.Accion;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.cartas.CartaComunidade;
import monopolinho.obxetos.cartas.CartaSorte;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoSinDinheiro;
import monopolinho.tipos.TipoAccion;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoTransaccion;

public class Sorte2 extends CartaSorte {
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
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.CASA)*40000.0f;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.HOTEL)*115000.0f;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISCINA)*20000.0f;
                aPagar+=c.getNumeroEdificiosTipo(TipoEdificio.PISTA_DEPORTES)*75000.0f;
            }
        }
        if(!xogador.quitarDinheiro(aPagar, TipoTransaccion.TASAS)){
            throw new MonopolinhoSinDinheiro(getMensaxe()+ ". Non tes suficiente diñeiro para pagar "+aPagar,xogador);
        }

        turno.engadirAccion(new Accion(TipoAccion.PAGAR_CARTA,aPagar));
        return getMensaxe() + " Tes que pagar "+aPagar;
    }
}
