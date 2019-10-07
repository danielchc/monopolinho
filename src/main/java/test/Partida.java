package test;

import monopolinho.obxetos.Avatar;
import monopolinho.obxetos.Xogador;

import java.util.HashMap;

public class Partida {
    private HashMap<String, Xogador> xogadores;
    private HashMap<String, Avatar> avatares;
    private Xogador banca;
    public Partida(){

    }

    public void setXogadores(HashMap<String, Xogador> xogadores) {
        this.xogadores = xogadores;
    }

    public HashMap<String, Xogador> getXogadores() {
        return xogadores;
    }
}
