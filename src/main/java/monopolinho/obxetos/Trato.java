package monopolinho.obxetos;

import monopolinho.obxetos.casillas.propiedades.Propiedade;

import java.util.ArrayList;
import java.util.HashMap;

public class Trato {
    private Xogador emisorTrato;
    private Xogador destinatarioTrato;
    private ArrayList<Propiedade> propiedadesOferta;
    private ArrayList<Propiedade> propiedadesDemanda;
    private float dinheiroOferta;
    private float dinheiroDemanda;
    private String ID;
    private HashMap<Propiedade,Integer> noAlquilerDemanda;
    private HashMap<Propiedade,Integer> noAlquilerOferta;


    /**
     * Constructor dos tratos
     * @param emisorTrato Xogador que crea o trato
     * @param destinatarioTrato Xogador ao que se lle propón o trato
     */
    public Trato(Xogador emisorTrato,Xogador destinatarioTrato,String ID){
        this.emisorTrato = emisorTrato;
        this.destinatarioTrato = destinatarioTrato;
        this.propiedadesOferta=new ArrayList<>();
        this.propiedadesDemanda=new ArrayList<>();
        this.dinheiroDemanda=-1f;
        this.dinheiroOferta=-1f;
        this.ID=ID;
        this.noAlquilerDemanda=new HashMap<>();
        this.noAlquilerOferta=new HashMap<>();
    }


    /**
     * Este método imprime todos os datos dun trato
     * @return String coa información do trato
     */
    public String describirTrato(){
        String texto="{";
        texto+="\n\t"+ID;
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
        texto+=")";

        if(!this.noAlquilerDemanda.isEmpty()){
            texto+="\n\tE "+this.emisorTrato.getNome()+" non paga alquiler en:";
            for(Propiedade p:this.noAlquilerDemanda.keySet()){
                texto+="\n\t\t"+p.getNome()+" durante "+this.noAlquilerDemanda.get(p)+" turnos";
            }
        }
        if(!this.noAlquilerOferta.isEmpty()){
            texto+="\n\tE "+this.destinatarioTrato.getNome()+" non paga alquiler en:";
            for(Propiedade p:this.noAlquilerOferta.keySet()){
                texto+="\n\t\t"+p.getNome()+" durante "+this.noAlquilerOferta.get(p)+" turnos";
            }
        }
        texto+="\n}";
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

    public void engadirNoAlquilerOferta(Propiedade p,Integer veces){
        if(p!=null){
            this.noAlquilerOferta.put(p,veces);
        }
    }

    public void engadirNoAlquilerDemanda(Propiedade p,Integer veces){
        if(p!=null){
            this.noAlquilerDemanda.put(p,veces);
        }
    }

    public Integer vecesNoAlqOferta(Propiedade p){
        return this.noAlquilerOferta.get(p);
    }

    public Integer vecesNoAlqDemanda(Propiedade p){
        return this.noAlquilerDemanda.get(p);
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

    public void setID(String ID) {
        if(ID!=null){
            this.ID = ID;
        }
    }

    public void setNoAlquilerDemanda(HashMap<Propiedade, Integer> noAlquilerDemanda) {
        this.noAlquilerDemanda = noAlquilerDemanda;
    }

    public HashMap<Propiedade, Integer> getNoAlquilerDemanda() {
        return noAlquilerDemanda;
    }

    public HashMap<Propiedade, Integer> getNoAlquilerOferta() {
        return noAlquilerOferta;
    }

    public void setNoAlquilerOferta(HashMap<Propiedade, Integer> noAlquilerOferta) {
        this.noAlquilerOferta = noAlquilerOferta;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString(){
        String texto=this.destinatarioTrato.getNome()+", douche ";
        for(Propiedade p:this.propiedadesOferta){
            texto+=p.getNome()+" ";
        }
        if(this.dinheiroOferta!=-1){
            if(this.propiedadesOferta.isEmpty()){
                texto+=this.dinheiroOferta;
            }
            else{
                texto+=" e "+this.dinheiroOferta;
            }
        }
        texto+=" e ti dasme ";
        for(Propiedade p:this.propiedadesDemanda){
            texto+=p.getNome()+" ";
        }
        if(this.dinheiroDemanda!=-1){
            if(this.propiedadesDemanda.isEmpty()){
                texto+=this.dinheiroDemanda;
            }
            else{
                texto+=" e "+this.dinheiroDemanda;
            }
        }

        if(!this.noAlquilerDemanda.isEmpty()){
            texto+=" e "+this.emisorTrato.getNome()+" non paga alquiler en ";
            for(Propiedade p:this.noAlquilerDemanda.keySet()){
                texto+=p.getNome()+" durante "+this.noAlquilerDemanda.get(p)+" turnos, ";
            }
        }
        if(!this.noAlquilerOferta.isEmpty()){
            texto+=" e "+this.destinatarioTrato.getNome()+" non paga alquiler en ";
            for(Propiedade p:this.noAlquilerOferta.keySet()){
                texto+=p.getNome()+" durante "+this.noAlquilerOferta.get(p)+" turnos, ";
            }
        }

        texto+="? ("+this.ID+")";
        return texto;
    }
}
