package monopolinho.obxetos.edificios;

import monopolinho.obxetos.casillas.propiedades.Solar;
import monopolinho.tipos.TipoCasilla;
import monopolinho.tipos.TipoEdificio;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public abstract class Edificio {
    private TipoEdificio tipoEdificio;
    private Solar posicion;
    private String id;

    /**
     * Constructor da clase Edificio
     * @param posicion Casilla na que se vai colocar o edificio
     */
    public Edificio(Solar posicion){
        if(posicion!=null && (posicion instanceof Solar)){
            this.posicion=posicion;
        }
    }


    /**
     * Este metodo permite describir a info dun edificio.
     * @return Descripcion do edificio
     */
    public String describirEdificio(){
        String texto="{\n";
        texto+="\tID: "+this.id+"\n\tPropietario: "+this.posicion.getDono().getNome()+
                ",\n\tCasilla: "+this.posicion.getNome()+"\n\tGrupo: "+this.posicion.getGrupo().getNome()+
                ",\n\tCoste: "+this.getPrecio()+"\n}";
        return texto;
    }

    public abstract float getPrecio();
    /**
     * Devolve o id dun edificio
     * @return Identificador do edificio
     */
    public String getId() {
        return id;
    }

    /**
     * Establece o id dun edificio
     * @param id Identificador
     */
    public void setId(String id) {
        if(id!=null)
            this.id = id;

    }

    /**
     * Devolve o tipo de edificio
     * @return Tipo de construcción
     */
    public TipoEdificio getTipoEdificio() {
        return tipoEdificio;
    }

    /**
     * Establece o tipo de edificio
     * @param tipoEdificio Tipo de contrucción
     */
    public void setTipoEdificio(TipoEdificio tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    /**
     * Devolve a casilla na que está o edificio
     * @return Casilla na que está o edificio
     */
    public Solar getPosicion() {
        return posicion;
    }

    /**
     * Non usar esta funcion fora de casilla
     * Establece a posicion do edificio
     * @param posicion Casilla na que se encontra o edificio
     *
     */
    public void setPosicion(Solar posicion) {
        if(posicion!= null)
            this.posicion = posicion;
    }


    @Override
    public String toString(){
        return this.id;
    }

    @Override
    /**
     * Compara se os obxetos son iguais
     * @param obj Obxeto a comparar
     * @return Son iguais os obxectos
     */
    public boolean equals(Object obj){
        return ((obj instanceof Edificio) && (this.id==((Edificio) obj).id && this.getPosicion().equals(((Edificio) obj).getPosicion())));
    }
}
