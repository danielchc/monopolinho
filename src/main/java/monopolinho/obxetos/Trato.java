package monopolinho.obxetos;

import com.sun.istack.internal.NotNull;
import monopolinho.obxetos.avatares.*;
import monopolinho.obxetos.casillas.propiedades.Propiedade;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoTransaccion;

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
    private boolean cambioAvatares;



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
        this.cambioAvatares=false;
    }

    public Trato(Xogador emisorTrato,Xogador destinatarioTrato,String ID,boolean avatar){
        this.emisorTrato = emisorTrato;
        this.destinatarioTrato = destinatarioTrato;
        this.propiedadesOferta=new ArrayList<>();
        this.propiedadesDemanda=new ArrayList<>();
        this.dinheiroDemanda=-1f;
        this.dinheiroOferta=-1f;
        this.ID=ID;
        this.noAlquilerDemanda=new HashMap<>();
        this.noAlquilerOferta=new HashMap<>();
        this.cambioAvatares=avatar;
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
        if(this.cambioAvatares){
            texto+="\n\tE intercambiar os avatares";
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

    /**
     * Este método engade unha propiedade á oferta de non pagar alquiler
     * @param p Propiedade
     * @param veces Número de turnos
     */
    public void engadirNoAlquilerOferta(Propiedade p,Integer veces){
        if(p!=null){
            this.noAlquilerOferta.put(p,veces);
        }
    }

    /**
     * Este método engade unha propiedade á demanda de non pagar alquiler
     * @param p Propiedade
     * @param veces Número de turnos
     */
    public void engadirNoAlquilerDemanda(Propiedade p,Integer veces){
        if(p!=null){
            this.noAlquilerDemanda.put(p,veces);
        }
    }


    /**
     * Este método devolve o número de veces que non pagas nun trato de non pagar alquiler
     * @param p Propiedade
     * @return devolve o número de turnos que non pagas
     */
    public Integer vecesNoAlqOferta(Propiedade p){
        return this.noAlquilerOferta.get(p);
    }

    /**
     * Este método devolve o número de veces que non pagas nun trato  de non pagar alquiler
     * @param p Propiedade
     * @return devolve o número de turnos que non pagas
     */
    public Integer vecesNoAlqDemanda(Propiedade p){
        return this.noAlquilerDemanda.get(p);
    }



    public String aceptarTrato() throws MonopolinhoGeneralException {
        Xogador emisor=this.emisorTrato;
        Xogador destinatario=this.destinatarioTrato;
        if(!comprobarPerteneceXogador(this.propiedadesOferta,emisor)){
            throw new MonopolinhoGeneralException("Non se pode aceptar este trato porque as propiedades de oferta non son de "+emisor.getNome());
        }
        if(!comprobarPerteneceXogador(this.propiedadesDemanda,destinatario)){
            throw new MonopolinhoGeneralException("Non se pode aceptar este trato porque as propiedades de demanda non son de "+destinatario.getNome());
        }

        if(emisor.getFortuna()<this.dinheiroOferta || destinatario.getFortuna()<this.dinheiroDemanda){
            throw new MonopolinhoGeneralException("Non se pode aceptar este trato porque non se dispón dos cartos necesarios");
        }
        if(this.dinheiroOferta!=-1){
            emisor.quitarDinheiro(this.dinheiroOferta, TipoTransaccion.COMPRA);
            destinatario.engadirDinheiro(this.dinheiroOferta,TipoTransaccion.VENTA);
        }
        if(this.dinheiroDemanda!=-1){
            destinatario.quitarDinheiro(this.dinheiroDemanda,TipoTransaccion.COMPRA);
            emisor.engadirDinheiro(this.dinheiroDemanda,TipoTransaccion.VENTA);
        }
        
        for(Propiedade p:this.propiedadesOferta){
            p.setDono(destinatario);
        }
        for (Propiedade p:this.propiedadesDemanda){
            p.setDono(emisor);
        }
        for(Propiedade p:this.noAlquilerOferta.keySet()){
            destinatario.engadirNonAlquiler(p,this.vecesNoAlqOferta(p));
        }
        for(Propiedade p:this.noAlquilerDemanda.keySet()){
            emisor.engadirNonAlquiler(p,this.vecesNoAlqDemanda(p));
        }
        if(this.cambioAvatares){
            Avatar avatarEmisor=emisor.getAvatar();
            Avatar avatarDestinatario=destinatario.getAvatar();
            //System.out.println("Antes trato");
            //System.out.println(emisor.getAvatar());
            //System.out.println(destinatario.getAvatar());
            novoAvatar(emisor,avatarEmisor,avatarDestinatario);
            novoAvatar(destinatario,avatarDestinatario,avatarEmisor);
            //System.out.println("Despois trato");
            //System.out.println(emisor.getAvatar());
            //System.out.println(destinatario.getAvatar());
        }

        this.destinatarioTrato.eliminarTrato(this.ID);
        String mensaxe = "Aceptouse o trato " + this.ID + " con " + emisor.getNome() + ": ( " + this + " )";
        return mensaxe;
    }

    private void novoAvatar(Xogador x,Avatar orixinal,Avatar avatarACopiar){
        if(avatarACopiar instanceof Coche){
            Coche c=new Coche(x);
            c.setId(orixinal.getId());
            c.setModoXogo(orixinal.getModoXogo());
            c.setPosicion(orixinal.getPosicion());
            c.setVoltasTaboeiro(orixinal.getVoltasTaboeiro());
            x.setAvatar(c);
        }
        else if(avatarACopiar instanceof Pelota){
            Pelota p=new Pelota(x);
            p.setId(orixinal.getId());
            p.setModoXogo(orixinal.getModoXogo());
            p.setPosicion(orixinal.getPosicion());
            p.setVoltasTaboeiro(orixinal.getVoltasTaboeiro());
            x.setAvatar(p);        }
        else if(avatarACopiar instanceof Sombreiro){
            Sombreiro s=new Sombreiro(x);
            s.setId(orixinal.getId());
            s.setModoXogo(orixinal.getModoXogo());
            s.setPosicion(orixinal.getPosicion());
            s.setVoltasTaboeiro(orixinal.getVoltasTaboeiro());
            x.setAvatar(s);
        }
        else if(avatarACopiar instanceof Esfinxe){
            Esfinxe e=new Esfinxe(x);
            e.setId(orixinal.getId());
            e.setModoXogo(orixinal.getModoXogo());
            e.setPosicion(orixinal.getPosicion());
            e.setVoltasTaboeiro(orixinal.getVoltasTaboeiro());
            x.setAvatar(e);
        }
    }


    /**
     * Este método comproba se un conxunto de propiedades lle pertenecen a un xogador
     * @param propiedades Arraylist de propiedades a comprobar
     * @param x Xogador a comporbar
     * @return true se todas lle pertenecen, false se non
     */
    private boolean comprobarPerteneceXogador(ArrayList<Propiedade> propiedades,Xogador x){
        for(Propiedade p:propiedades){
            if(!p.pertenceXogador(x)) return false;
        }
        return true;
    }




    /**
     * GETTERS E SETTERS
     */

    public boolean isCambioAvatares() {
        return cambioAvatares;
    }

    public void setCambioAvatares(boolean cambioAvatares) {
        this.cambioAvatares = cambioAvatares;
    }


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
        String texto=this.destinatarioTrato.getNome();
        if(cambioAvatares) texto+=", intercambiamos os avatares";
        texto+=", douche ";
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
