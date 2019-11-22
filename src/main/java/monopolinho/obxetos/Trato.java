package monopolinho.obxetos;

import monopolinho.obxetos.casillas.propiedades.Propiedade;

import java.util.ArrayList;

public class Trato {
    private Xogador emisorTrato;
    private Xogador destinatarioTrato;
    private ArrayList<Propiedade> propiedadesOferta;
    private ArrayList<Propiedade> propiedadesDemanda;
    private float dinheiroOferta;
    private float dinheiroDemanda;

    public Trato(Xogador emisorTrato,Xogador destinatarioTrato){
        this.emisorTrato = emisorTrato;
        this.destinatarioTrato = destinatarioTrato;
        this.propiedadesOferta=new ArrayList<>();
        this.propiedadesDemanda=new ArrayList<>();
        this.dinheiroDemanda=-1f;
        this.dinheiroOferta=-1f;
    }


    public String describirTrato(){
        String texto="{";
        texto+="\n\txogadorPropon: " + emisorTrato.getNome();
        texto+="\n\ttrato: cambiar(";
        for(Propiedade p:this.propiedadesOferta){
            texto+=p.getNome()+" ";
        }
        if(this.dinheiroOferta!=-1){
            if(this.propiedadesOferta.isEmpty()){
                texto+=this.dinheiroOferta;
            }
            else{
                texto+=" y "+this.dinheiroOferta;
            }
        }
        texto+=", ";
        for(Propiedade p:this.propiedadesDemanda){
            texto+=p.getNome()+" ";
        }
        if(this.dinheiroDemanda!=-1){
            if(this.propiedadesDemanda.isEmpty()){
                texto+=this.dinheiroDemanda;
            }
            else{
                texto+=" y "+this.dinheiroDemanda;
            }
        }
        texto+=")\n}";
        return texto;
    }


    public void engadirPropiedadeOferta(Propiedade p){
        if(p!=null){
            this.propiedadesOferta.add(p);
        }
    }

    public void engadirPropiedadeDemanda(Propiedade p){
        if(p!=null){
            this.propiedadesDemanda.add(p);
        }
    }




    ////////////getters e setters//////////////////77

    public void setEmisorTrato(Xogador emisorTrato) {
        this.emisorTrato = emisorTrato;
    }

    public Xogador getEmisorTrato() {
        return emisorTrato;
    }

    public void setDestinatarioTrato(Xogador destinatarioTrato) {
        this.destinatarioTrato = destinatarioTrato;
    }

    public Xogador getDestinatarioTrato() {
        return destinatarioTrato;
    }

    public ArrayList<Propiedade> getPropiedadesOferta() {
        return propiedadesOferta;
    }

    public void setPropiedadesOferta(ArrayList<Propiedade> propiedadesOferta) {
        this.propiedadesOferta = propiedadesOferta;
    }

    public ArrayList<Propiedade> getPropiedadesDemanda() {
        return propiedadesDemanda;
    }

    public void setPropiedadesDemanda(ArrayList<Propiedade> propiedadesDemanda) {
        this.propiedadesDemanda = propiedadesDemanda;
    }

    public float getDinheiroDemanda() {
        return dinheiroDemanda;
    }

    public float getDinheiroOferta() {
        return dinheiroOferta;
    }

    public void setDinheiroDemanda(float dinheiroDemanda) {
        this.dinheiroDemanda = dinheiroDemanda;
    }

    public void setDinheiroOferta(float dinheiroOferta) {
        this.dinheiroOferta = dinheiroOferta;
    }
}
