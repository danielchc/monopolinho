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

    /**
     * Constructor dos tratos
     * @param emisorTrato Xogador que crea o trato
     * @param destinatarioTrato Xogador ao que se lle propón o trato
     */
    public Trato(Xogador emisorTrato,Xogador destinatarioTrato){
        this.emisorTrato = emisorTrato;
        this.destinatarioTrato = destinatarioTrato;
        this.propiedadesOferta=new ArrayList<>();
        this.propiedadesDemanda=new ArrayList<>();
        this.dinheiroDemanda=-1f;
        this.dinheiroOferta=-1f;
    }


    /**
     * Este método imprime todos os datos dun trato
     * @return String coa información do trato
     */
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


    /**
     * Este metodo engade unha propiedade á oferta do trato
     * @param p Propiedade a engadir
     */
    public void engadirPropiedadeOferta(Propiedade p){
        if(p!=null){
            this.propiedadesOferta.add(p);
        }
    }

    /**
     * Este metodo engade unha propiedade á demanda do trato
     * @param p Propiedade a engadir
     */
    public void engadirPropiedadeDemanda(Propiedade p){
        if(p!=null){
            this.propiedadesDemanda.add(p);
        }
    }




    /**
     * GETTERS E SETTERS
     */

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
