package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;

import java.util.ArrayList;

public class Taboeiro {
    public enum Zona{
        SUR,
        OESTE,
        NORTE,
        ESTE
    }
    private ArrayList<ArrayList<Casilla>> casillas;
    private float bote=0.0f;

    public Taboeiro(){
        this.casillas=new ArrayList<>();
        for(int i=0;i<4;i++)this.casillas.add(new ArrayList<>()); //AÑADIR ZONAS
        xerarCasillas();
    }
    public void engadirCasilla(Zona z,Casilla casilla){
        if(casilla!=null){
            this.casillas.get(z.ordinal()).add(casilla);
            if(z==Zona.SUR) casilla.setPosicion(this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.OESTE) casilla.setPosicion(11+this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.NORTE) casilla.setPosicion(20+this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.ESTE) casilla.setPosicion(31 + this.casillas.get(z.ordinal()).indexOf(casilla));
        }
    }

    public ArrayList<ArrayList<Casilla>> getCasillas() {
        return casillas;
    }

    public ArrayList<Casilla> getCasillas(Zona z) {
        return casillas.get(z.ordinal());
    }

    public Casilla getCasilla(int posicion){
        if((posicion>=0)&&(posicion<11)) return getCasillas(Zona.SUR).get(posicion);
        else if((posicion>=11)&&(posicion<20)) return getCasillas(Zona.OESTE).get(posicion-11);
        else if((posicion>=20)&&(posicion<31)) return getCasillas(Zona.NORTE).get(posicion-20);
        else return getCasillas(Zona.ESTE).get(posicion-31);
    }

    public Casilla buscarCasilla(String nome){
        for(ArrayList<Casilla> ac:this.casillas){
            for(Casilla c:ac){
                if(c.getNome().toLowerCase().equals(nome.toLowerCase())) return c;
            }
        }
        return null;
    }

    public float getBote() {
        return bote;
    }

    public void setBote(float bote) {
        this.bote = bote;
    }

    public void engadirBote(float imposto) {
        this.bote += imposto;
    }


    @Override
    public String toString(){
        String taboeiro="";

        //NORTE
        String[] ncasillas=new String[]{"","","","",""};
        for(int i=0;i<11;i++)
            ReprTab.unirCasilla(ncasillas,getCasillas(Zona.NORTE).get(i));
        taboeiro+=String.join("\n",ncasillas);
        taboeiro+="\n";

        //ESTE OESTE
        String[] ocasillas;
        for(int i=0;i<9;i++){
            ocasillas=new String[]{"","","","",""};
            ReprTab.unirCasilla(ocasillas,getCasillas(Zona.OESTE).get(8-i));
            for(int j=0;j<9;j++){
                if((i==4) && (j==0))
                    ReprTab.debuxoMonopolinho(ocasillas,1);
                else if((i==3) && (j==0))
                    ReprTab.debuxoMonopolinho(ocasillas,0);
                else if (i!=4 && i!=3)
                    ReprTab.engadirCasillaVacia(ocasillas);
            }
            ReprTab.unirCasilla(ocasillas,getCasillas(Zona.ESTE).get(i));
            taboeiro+=String.join("\n",ocasillas);
            taboeiro+="\n";
        }

        //SUR
        String[] scasillas=new String[]{"","","","",""};
        for(int i=0;i<11;i++) ReprTab.unirCasilla(scasillas,getCasillas(Zona.SUR).get(10-i));
        taboeiro+=String.join("\n",scasillas);

        return taboeiro;
    }

    public void xerarCasillas(){

        Grupo grupo_ocre=new Grupo("OCRE", Valor.ReprColor.ANSI_YELLOW_BACKGROUND,Valor.VALOR_GRUPO_OCRE);
        Grupo grupo_cyan=new Grupo("CYAN", Valor.ReprColor.ANSI_CYAN_BACKGROUND,Valor.VALOR_GRUPO_CYAN);
        Grupo grupo_violeta=new Grupo("VIOLETA", Valor.ReprColor.ANSI_PURPLE_BACKGROUND,Valor.VALOR_GRUPO_CYAN);
        Grupo grupo_amarillo=new Grupo("AMARELO", Valor.ReprColor.ANSI_HIGH_YELLOW_BACKGROUND,Valor.VALOR_GRUPO_AMARILLO);
        Grupo grupo_rojo=new Grupo("VERMELLO", Valor.ReprColor.ANSI_RED_BACKGROUND,Valor.VALOR_GRUPO_ROJO);
        Grupo grupo_blanco=new Grupo("GRIS", Valor.ReprColor.ANSI_WHITE_BACKGROUND,Valor.VALOR_GRUPO_BLANCO);
        Grupo grupo_verde=new Grupo("VERDE", Valor.ReprColor.ANSI_GREEN_BACKGROUND,Valor.VALOR_GRUPO_VERDE);
        Grupo grupo_azul=new Grupo("AZUL", Valor.ReprColor.ANSI_BLUE_BACKGROUND,Valor.VALOR_GRUPO_AZUL);


        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SALIDA",Casilla.TipoCasilla.SALIDA));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("GOIRIZ",grupo_ocre,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("CAIXA",Casilla.TipoCasilla.COMUNIDADE));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SADA",grupo_ocre,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("IMPOSTO1",Casilla.TipoCasilla.IMPOSTO, Valor.IMPOSTO_BARATO));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("AVE",Casilla.TipoCasilla.TRANSPORTE));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("BOIMORTO",grupo_cyan,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("SORTE",Casilla.TipoCasilla.SORTE));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("PONTEPEDRA",grupo_cyan,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("BALDAIO",grupo_cyan,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.SUR,new Casilla("CARCEL_TEIXEIRO", Casilla.TipoCasilla.CARCEL));

        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("PARKING",Casilla.TipoCasilla.PARKING));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("CASTROVERDE",grupo_rojo,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("SORTE",Casilla.TipoCasilla.SORTE));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("MUIMENTA",grupo_rojo,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("GUITIRIZ",grupo_rojo,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("HIDROAVION",Casilla.TipoCasilla.TRANSPORTE,1000));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("ALLARIZ",grupo_blanco,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("CANGAS",grupo_blanco,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("AS_PONTES",Casilla.TipoCasilla.INFRAESTRUCTURA));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("FENE",grupo_blanco,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.NORTE,new Casilla("IR_CARCEL",Casilla.TipoCasilla.IRCARCEL));

        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("CORUNA",grupo_verde,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("LUGO",grupo_verde,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("COMUNIDADE",Casilla.TipoCasilla.COMUNIDADE));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("ORENSE",grupo_verde,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("TRACTOR",Casilla.TipoCasilla.TRANSPORTE));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SORTE",Casilla.TipoCasilla.SORTE));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("VIGO",grupo_azul,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("IMPOSTO2",Casilla.TipoCasilla.IMPOSTO, Valor.IMPOSTO_CARO));
        engadirCasilla(Taboeiro.Zona.ESTE,new Casilla("SANTIAGO",grupo_azul,Casilla.TipoCasilla.SOLAR));


        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("FRADES",grupo_violeta, Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MEIRAMA", Casilla.TipoCasilla.INFRAESTRUCTURA));//servicio
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MONELOS",grupo_violeta, Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("LOURO",grupo_violeta, Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("CATAMARAN", Casilla.TipoCasilla.TRANSPORTE));//servicio
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("SANTISO",grupo_amarillo,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("COMUNIDADE", Casilla.TipoCasilla.COMUNIDADE));//servicio
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("MELIDE",grupo_amarillo,Casilla.TipoCasilla.SOLAR));
        engadirCasilla(Taboeiro.Zona.OESTE,new Casilla("SARRIA",grupo_amarillo,Casilla.TipoCasilla.SOLAR));

    }
}
