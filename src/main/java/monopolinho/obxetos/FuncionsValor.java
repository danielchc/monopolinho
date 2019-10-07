package monopolinho.obxetos;

public class FuncionsValor {

    //FUNCIONES DINERITO//////////////////////////////////////////////

    public float fortunaInicialXogador(Taboeiro taboeiro){
        float suma=0;
        for (int i=0;i<40;i++){
            if(taboeiro.getCasilla(i).getTipoCasilla()== Casilla.TipoCasilla.SOLAR){
                suma+=taboeiro.getCasilla(i).getValor();
            }
        }
        return suma/3;
    }

    public void cobrarSalida(Taboeiro taboeiro,Xogador x){
        float suma=0;
        int contador=0;
        for (int i=0;i<40;i++) {
            if (taboeiro.getCasilla(i).getTipoCasilla() == Casilla.TipoCasilla.SOLAR) {
                suma += taboeiro.getCasilla(i).getValor();
                contador++;
            }
        }
        x.engadirDinheiro(suma/contador);   //engadeselle a media do valor dos solares
    }

    public float valorInicialTransporte(Taboeiro taboeiro){
        float suma=0;
        int contador=0;
        for (int i=0;i<40;i++) {
            if (taboeiro.getCasilla(i).getTipoCasilla() == Casilla.TipoCasilla.SOLAR) {
                suma += taboeiro.getCasilla(i).getValor();
                contador++;
            }
        }
        return suma/contador;
    }

/*    public float valorInicialServicio(){
        return 0.75f*valorInicialTransporte();
    }*/

    /*  SIN COMPLETAR, FALTA O DE VALOR INICIAL DOS SOLARES
    public float valorInicialCasaHotel(Casilla c){
        return 0.6f*valorInicialSolar();
    }
    */

    /*  SIN COMPLETAR, FALTA O DE VALOR INICIAL DOS SOLARES
    public float valorInicialPiscina(Casilla c){
        return 0.4f*valorInicialSolar();
    }
    */

    /*  SIN COMPLETAR, FALTA O DE VALOR INICIAL DOS SOLARES
    public float valorInicialPistaDeporte(Casilla c){
        return 1.25f*valorInicialSolar();
    }
    */











    ////////////////////////////////////////////////////////////////////
}
