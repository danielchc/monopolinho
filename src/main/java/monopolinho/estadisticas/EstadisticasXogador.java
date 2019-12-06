package monopolinho.estadisticas;

/**
 * @author David Carracedo
 * @author Daniel Chenel
 */
public class EstadisticasXogador {
    private float dineroGastado;
    private float dineroInvertido;
    private float pagoTasasEImpuestos;
    private float pagoDeAlquileres;
    private float cobroDeAlquileres;
    private float pasarPolaCasillaDeSalida;
    private float premiosInversionesOBote;
    private int vecesNoCarcere;
    private int lazamentosDeDadosTotales;

    /**
     * Estadisticas dun Xogador
     */
    public EstadisticasXogador() {
        this.dineroInvertido=0;
        this.pagoTasasEImpuestos=0;
        this.pagoDeAlquileres=0;
        this.cobroDeAlquileres=0;
        this.pasarPolaCasillaDeSalida=0;
        this.premiosInversionesOBote=0;
        this.lazamentosDeDadosTotales =0;
        this.vecesNoCarcere =0;
    }

    /**
     * Aumenta o diñeiro gastado
     * @param engadir
     */
    public void engadirDineroGastado(float engadir){
        this.dineroGastado+=engadir;
    }
    /**
     * Aumenta o diñeiro gastado
     * @param engadir
     */
    public void restarDineroGastado(float engadir){
        this.dineroGastado-=engadir;
    }

    /**
     * Aumenta o diñeiro que se invirtiu
     * @param engadir
     */
    public void engadirDineroInvertido(float engadir){
        this.dineroInvertido+=engadir;
    }

    /**
     * Aumenta os cartos polo pago de impostos
     * @param engadir
     */
    public void engadirPagoTasasEImpuestos(float engadir){
        this.pagoTasasEImpuestos+=engadir;
    }

    /**
     * Aumenta os cartos polo cobro de alquileres
     * @param engadir
     */
    public void engadirCobroDeAlquileres(float engadir){
        this.cobroDeAlquileres+=engadir;
    }

    /**
     * Aumenta os cartos polo pago de alquileres
     * @param engadir
     */
    public void engadirPagoDeAlquileres(float engadir){
        this.pagoDeAlquileres+=engadir;
    }

    /**
     * Aumenta os cartos por pasar pola casilla de salida
     * @param engadir
     */
    public void engadirPasarPolaCasillaDeSaida(float engadir){
        this.pasarPolaCasillaDeSalida+=engadir;
    }

    /**
     * Aumenta os valor dos cartos recibidos por o Bote
     * @param engadir
     */
    public void engadirPremiosInversionesOBote(float engadir){
        this.premiosInversionesOBote+=engadir;
    }

    /**
     * Aumenta o número de veces que un xogador estuvo no cárcere
     */
    public void engadirVecesNoCarcere(){
        this.vecesNoCarcere++;
    }

    /**
     * Aumenta o número de lanzamento de dados totales
     */
    public void engadirLazamentosDeDadosTotales(){
        this.lazamentosDeDadosTotales++;
    }

    /**
     * @return Obtén o diñeiro obtido
     */
    public float getDineroInvertido() {
        return dineroInvertido;
    }

    /**
     * Establece o diñeiro invertido
     * @param dineroInvertido
     */
    public void setDineroInvertido(float dineroInvertido) {
        this.dineroInvertido = dineroInvertido;
    }

    /**
     * @return Obtén os cartos gastados no pago de impostos
     */
    public float getPagoTasasEImpuestos() {
        return pagoTasasEImpuestos;
    }

    /**
     * Establece os cartos gastados no pago de impostos
     * @param pagoTasasEImpuestos
     */
    public void setPagoTasasEImpuestos(float pagoTasasEImpuestos) {
        this.pagoTasasEImpuestos = pagoTasasEImpuestos;
    }

    /**
     * @return Obtén os cartos gastados polo pago de alquileres
     */
    public float getPagoDeAlquileres() {
        return pagoDeAlquileres;
    }

    /**
     * Establece os cartos gastados polo pago de alquileres
     * @param pagoDeAlquileres
     */
    public void setPagoDeAlquileres(float pagoDeAlquileres) {
        this.pagoDeAlquileres = pagoDeAlquileres;
    }

    /**
     * @return Obtén os cartos obtido polos cobros de alquileres
     */
    public float getCobroDeAlquileres() {
        return cobroDeAlquileres;
    }

    /**
     * Establece os cartos obtido polos cobros de alquileres
     * @param cobroDeAlquileres
     */
    public void setCobroDeAlquileres(float cobroDeAlquileres) {
        this.cobroDeAlquileres = cobroDeAlquileres;
    }

    /**
     * @return Obtén os cartos provintes de pasar pola casilla de salida
     */
    public float getPasarPolaCasillaDeSalida() {
        return pasarPolaCasillaDeSalida;
    }

    /**
     *Establece os cartos provintes de pasar pola casilla de salida
     * @param pasarPolaCasillaDeSalida
     */
    public void setPasarPolaCasillaDeSalida(float pasarPolaCasillaDeSalida) {
        this.pasarPolaCasillaDeSalida = pasarPolaCasillaDeSalida;
    }

    /**
     * @return Obtén os cartos recibidos por premios ou inversións
     */
    public float getPremiosInversionesOBote() {
        return premiosInversionesOBote;
    }

    /**
     * Establece os cartos recibidos por premios ou inversións
     * @param premiosInversionesOBote
     */
    public void setPremiosInversionesOBote(float premiosInversionesOBote) {
        this.premiosInversionesOBote = premiosInversionesOBote;
    }

    /**
     * @return Devolve o número de veces que un xogador estuvo no cárcere
     */
    public int getVecesNoCarcere() {
        return vecesNoCarcere;
    }

    /**
     * Establece o número de veces que un xogador foi o cárcere
     * @param vecesNoCarcere
     */
    public void setVecesNoCarcere(int vecesNoCarcere) {
        this.vecesNoCarcere = vecesNoCarcere;
    }

    /**
     * @return Devolve o diñeiro total gastado por un xogador
     */
    public float getDineroGastado() {
        return dineroGastado;
    }

    /**
     * Establece o diñeiro total gastado por un xogador
     * @param dineroGastado
     */
    public void setDineroGastado(float dineroGastado) {
        this.dineroGastado = dineroGastado;
    }

    /**
     * @return Devolve o número de veces totales que se lanzou un dado
     */
    public int getVecesLanzamentoDados() {
        return lazamentosDeDadosTotales;
    }

    /**
     * Establece o número de veces totales que se lanzou un dado
     * @param lazamentosDeDadosTotales
     */
    public void setLazamentosDeDadosTotales(int lazamentosDeDadosTotales) {
        this.lazamentosDeDadosTotales = lazamentosDeDadosTotales;
    }

    /**
     * @return Devolve as estadisticas en texto
     */
    @Override
    public String toString() {
        return "{" +
                "\n\t dineroInvertido: " + dineroInvertido +
                ",\n\t pagoTasasEImpuestos: " + pagoTasasEImpuestos +
                ",\n\t pagoDeAlquileres: " + pagoDeAlquileres +
                ",\n\t cobroDeAlquileres: " + cobroDeAlquileres +
                ",\n\t pasarPolaCasillaDeSalida: " + pasarPolaCasillaDeSalida +
                ",\n\t premiosInversionesOBote: " + premiosInversionesOBote +
                ",\n\t vecesNoCarcere: " + vecesNoCarcere +
                "\n}";
    }
}
