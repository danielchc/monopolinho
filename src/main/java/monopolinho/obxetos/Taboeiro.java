package monopolinho.obxetos;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.obxetos.casillas.*;
import monopolinho.obxetos.casillas.propiedades.Servizo;
import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.obxetos.casillas.propiedades.Transporte;
import monopolinho.tipos.Zona;

import java.util.ArrayList;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class Taboeiro {
    private ArrayList<ArrayList<Casilla>> casillas;
    private ArrayList<Grupo> grupos;
    private float bote=0.0f;

    /**
     * - Constructor de Taboeiro.
     * - Instancia o arraylist para as zonas e cada arraylist de cada zona.
     * - Xera as casillas do taboeiro.
     */
    public Taboeiro(){
        this.casillas=new ArrayList<>();
        this.grupos=new ArrayList<>();
        for(int i=0;i<4;i++)this.casillas.add(new ArrayList<>());
        xerarCasillas();
    }

    /**
     * Este metodo engade unha casilla a unha zona do taboeiro.
     * @param z Zona donde engadir a casilla
     * @param casilla Casilla a engadir
     */
    public void engadirCasilla(Zona z,Casilla casilla){
        if(casilla!=null){
            this.casillas.get(z.ordinal()).add(casilla);
            if(z== Zona.SUR) casilla.setPosicionIndex(this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.OESTE) casilla.setPosicionIndex(11+this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.NORTE) casilla.setPosicionIndex(20+this.casillas.get(z.ordinal()).indexOf(casilla));
            else if(z==Zona.ESTE) casilla.setPosicionIndex(31+this.casillas.get(z.ordinal()).indexOf(casilla));
            casilla.setTaboeiro(this);
        }
    }

    /**
     * Este metodo busca unha casilla
     * @param nome Nome da casilla
     * @return Casilla do taboeiro
     */
    public Casilla buscarCasilla(String nome){
        for(ArrayList<Casilla> ac:this.casillas){
            for(Casilla c:ac){
                if(c.getNome().toLowerCase().equals(nome.toLowerCase())) return c;
            }
        }
        return null;
    }

    /**
     * Este metodo busca un grupo do taboeiro
     * @param nome Nome do grupo
     * @return Grupo do taboeiro
     */
    public Grupo buscarGrupo(String nome){
        for(Grupo g:this.grupos){
            if(g.getNome().toLowerCase().equals(nome.toLowerCase())){
                return g;
            }
        }
        return null;
    }


    /**
     * Sobrecarga de métodos
     * @return Devolve todas as casillas
     */

    public ArrayList<ArrayList<Casilla>> getCasillas() {
        return casillas;
    }


    /**
     * @param z Zona donde se atopa a casilla
     * @return
     */
    public ArrayList<Casilla> getCasillas(Zona z) {
        return casillas.get(z.ordinal());
    }


    /**
     * Accede a unha casilla por un index(0,39)
     * @param posicion Indice
     * @return Casilla no taboeiro
     */
    public Casilla getCasilla(int posicion){
        posicion=Math.floorMod(posicion,40);
        if((posicion>=0)&&(posicion<11)) return getCasillas(Zona.SUR).get(posicion);
        else if((posicion>=11)&&(posicion<20)) return getCasillas(Zona.OESTE).get(posicion-11);
        else if((posicion>=20)&&(posicion<31)) return getCasillas(Zona.NORTE).get(posicion-20);
        else return getCasillas(Zona.ESTE).get(posicion-31);
    }


    /**
     * @return Devolve o bote do taboeiro
     */
    public float getBote() {
        return bote;
    }


    /**
     * Establece o bote do Taboeiro
     * @param bote
     */
    public void setBote(float bote) {
        this.bote = bote;
    }


    /**
     * Engade cartos o bote da casilla
     * @param imposto Cartos a engadir
     */
    public void engadirBote(float imposto) {
        this.bote += imposto;
    }

    /**
     * Devolve os grupos do tableiro
     * @return Grupos do tableiro
     */
    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    /**
     * Establece os grupos do tableiro
     * @param grupos Grupos do tableiro
     */
    public void setGrupos(ArrayList<Grupo> grupos) {
        if(grupos!=null){
            this.grupos = grupos;
        }
    }


    /**
     * Este metodo instancia todas as casillas do taboeiro e engadeas a zona que lles corresponde.
     */
    public void xerarCasillas(){

        Grupo grupo_ocre=new Grupo("OCRE", Valor.ReprColor.ANSI_YELLOW_BACKGROUND,Valor.VALOR_GRUPO_OCRE,2);
        Grupo grupo_cyan=new Grupo("CYAN", Valor.ReprColor.ANSI_CYAN_BACKGROUND,Valor.VALOR_GRUPO_CYAN,3);
        Grupo grupo_violeta=new Grupo("VIOLETA", Valor.ReprColor.ANSI_PURPLE_BACKGROUND,Valor.VALOR_GRUPO_VIOLETA,3);
        Grupo grupo_amarillo=new Grupo("AMARELO", Valor.ReprColor.ANSI_HIGH_YELLOW_BACKGROUND,Valor.VALOR_GRUPO_AMARILLO,3);
        Grupo grupo_rojo=new Grupo("VERMELLO", Valor.ReprColor.ANSI_RED_BACKGROUND,Valor.VALOR_GRUPO_ROJO,3);
        Grupo grupo_blanco=new Grupo("GRIS", Valor.ReprColor.ANSI_WHITE_BACKGROUND,Valor.VALOR_GRUPO_BLANCO,3);
        Grupo grupo_verde=new Grupo("VERDE", Valor.ReprColor.ANSI_GREEN_BACKGROUND,Valor.VALOR_GRUPO_VERDE,3);
        Grupo grupo_azul=new Grupo("AZUL", Valor.ReprColor.ANSI_BLUE_BACKGROUND,Valor.VALOR_GRUPO_AZUL,2);

        this.grupos.add(grupo_ocre);
        this.grupos.add(grupo_cyan);
        this.grupos.add(grupo_violeta);
        this.grupos.add(grupo_amarillo);
        this.grupos.add(grupo_rojo);
        this.grupos.add(grupo_blanco);
        this.grupos.add(grupo_verde);
        this.grupos.add(grupo_azul);

        engadirCasilla(Zona.SUR,new Saida());
        engadirCasilla(Zona.SUR,new Solar("GOIRIZ",grupo_ocre));
        engadirCasilla(Zona.SUR,new Comunidade());
        engadirCasilla(Zona.SUR,new Solar("SADA",grupo_ocre));
        engadirCasilla(Zona.SUR,new Imposto("I.CATASTRO",Valor.IMPOSTO_BARATO));
        engadirCasilla(Zona.SUR,new Transporte("AVE", Valor.VALOR_TRANSPORTE,Valor.USO_TRANSPORTE));
        engadirCasilla(Zona.SUR,new Solar("BOIMORTO",grupo_cyan));
        engadirCasilla(Zona.SUR,new Sorte());
        engadirCasilla(Zona.SUR,new Solar("PONTEPEDRA",grupo_cyan));
        engadirCasilla(Zona.SUR,new Solar("BALDAIO",grupo_cyan));
        engadirCasilla(Zona.SUR,new Carcere());

        engadirCasilla(Zona.NORTE,new Parking());
        engadirCasilla(Zona.NORTE,new Solar("CASTROVERDE",grupo_rojo));
        engadirCasilla(Zona.NORTE,new Sorte());
        engadirCasilla(Zona.NORTE,new Solar("MUIMENTA",grupo_rojo));
        engadirCasilla(Zona.NORTE,new Solar("GUITIRIZ",grupo_rojo));
        engadirCasilla(Zona.NORTE,new Transporte("HIDROAVION",Valor.VALOR_TRANSPORTE,Valor.USO_TRANSPORTE));
        engadirCasilla(Zona.NORTE,new Solar("ALLARIZ",grupo_blanco));
        engadirCasilla(Zona.NORTE,new Solar("CANGAS",grupo_blanco));
        engadirCasilla(Zona.NORTE,new Servizo("AS_PONTES",Valor.VALOR_SERVIZO,Valor.USO_SERVIZO));
        engadirCasilla(Zona.NORTE,new Solar("FENE",grupo_blanco));
        engadirCasilla(Zona.NORTE,new Ir_Carcere());

        engadirCasilla(Zona.ESTE,new Solar("CORUNA",grupo_verde));
        engadirCasilla(Zona.ESTE,new Solar("LUGO",grupo_verde));
        engadirCasilla(Zona.ESTE,new Comunidade());
        engadirCasilla(Zona.ESTE,new Solar("OURENSE",grupo_verde));
        engadirCasilla(Zona.ESTE,new Transporte("TRACTOR", Valor.VALOR_TRANSPORTE,Valor.USO_TRANSPORTE));
        engadirCasilla(Zona.ESTE,new Sorte());
        engadirCasilla(Zona.ESTE,new Solar("VIGO",grupo_azul));
        engadirCasilla(Zona.ESTE,new Imposto("I.SUCESIONS", Valor.IMPOSTO_CARO));
        engadirCasilla(Zona.ESTE,new Solar("SANTIAGO",grupo_azul));


        engadirCasilla(Zona.OESTE,new Solar("FRADES",grupo_violeta));
        engadirCasilla(Zona.OESTE,new Servizo("MEIRAMA", Valor.VALOR_SERVIZO,Valor.USO_SERVIZO));
        engadirCasilla(Zona.OESTE,new Solar("MONELOS",grupo_violeta));
        engadirCasilla(Zona.OESTE,new Solar("LOURO",grupo_violeta));
        engadirCasilla(Zona.OESTE,new Transporte("CATAMARAN", Valor.VALOR_TRANSPORTE,Valor.USO_TRANSPORTE));
        engadirCasilla(Zona.OESTE,new Solar("SANTISO",grupo_amarillo));
        engadirCasilla(Zona.OESTE,new Comunidade());
        engadirCasilla(Zona.OESTE,new Solar("MELIDE",grupo_amarillo));
        engadirCasilla(Zona.OESTE,new Solar("SARRIA",grupo_amarillo));

    }

    /**
     * @return Devolve a representación do taboeiro en ASCII
     */
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
}
