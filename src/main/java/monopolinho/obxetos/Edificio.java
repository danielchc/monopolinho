package monopolinho.obxetos;

public class Edificio {
    public enum TipoEdificio{
        CASA,
        HOTEL,
        PISCINA,
        PISTA_DEPORTES,
        DEFAULT
    }
    private TipoEdificio tipoEdificio;
    private Xogador propietario;
    private float precio;
    private Casilla posicion;


    /**
     * Constructor da clase Edificio
     * @param tipoEdificio Tipo de contrucción
     * @param propietario Dono do edificio
     * @param precio Precio do edificio
     * @param posicion Casilla na que se vai colocar o edificio
     */
    public Edificio(TipoEdificio tipoEdificio,Xogador propietario,float precio,Casilla posicion){
        if(propietario!=null && posicion!=null && posicion.getTipoCasilla()== Casilla.TipoCasilla.SOLAR){
            this.tipoEdificio=tipoEdificio;
            this.propietario=propietario;
            this.precio=precio;
            this.posicion=posicion;
        }
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
     * Devolve o propietario do edificio
     * @return Dono do edificio
     */
    public Xogador getPropietario() {
        return propietario;
    }

    /**
     * Establece o dono do edificio
     * @param propietario Dono do edificio
     */
    public void setPropietario(Xogador propietario) {
        if(propietario!=null){
            this.propietario = propietario;
        }
    }

    /**
     * Devolve o precio dun edificio
     * @return Precio do edificio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Establece o precio dun edificio
     * @param precio Precio do edificio
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * Devolve a casilla na que está o edificio
     * @return Casilla na que está o edificio
     */
    public Casilla getPosicion() {
        return posicion;
    }

    /**
     * Non usar esta funcion fora de casilla
     * Establece a posicion do edificio
     * @param posicion Casilla na que se encontra o edificio
     *
     */
    public void setPosicion(Casilla posicion) {
        if(posicion!= null){
            this.posicion = posicion;
        }
    }


    @Override
    public String toString(){
        String rep=this.tipoEdificio.toString();
        return rep;
    }

    @Override
    /**
     * Compara se os obxetos son iguais
     * @param obj Obxeto a comparar
     * @return Son iguais os obxectos
     */
    public boolean equals(Object obj){
        if(obj instanceof Edificio){
            if(this.tipoEdificio==((Edificio) obj).tipoEdificio)return true;  //esto vai haber que compara polo id do edificio que hai que implementar
        }
        return false;
    }
}
