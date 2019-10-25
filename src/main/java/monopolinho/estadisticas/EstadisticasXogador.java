package monopolinho.estadisticas;

public class EstadisticasXogador {
    private float dineroGastado;
    private float dineroInvertido;
    private float pagoTasasEImpuestos;
    private float pagoDeAlquileres;
    private float cobroDeAlquileres;
    private float pasarPolaCasillaDeSalida;
    private float premiosInversionesOBote;
    private int vecesEnLaCarcel;

    public EstadisticasXogador() {
        this.dineroInvertido=0;
        this.pagoTasasEImpuestos=0;
        this.pagoDeAlquileres=0;
        this.cobroDeAlquileres=0;
        this.pasarPolaCasillaDeSalida=0;
        this.premiosInversionesOBote=0;
        this.vecesEnLaCarcel=0;
    }

    public void engadirDineroGastado(float engadir){
        this.dineroGastado+=engadir;
    }
    public void engadirDineroInvertido(float engadir){
        this.dineroInvertido+=engadir;
    }

    public void engadirPagoTasasEImpuestos(float engadir){
        this.pagoTasasEImpuestos+=engadir;
    }

    public void engadirCobroDeAlquileres(float engadir){
        this.cobroDeAlquileres+=engadir;
    }

    public void engadirPagoDeAlquileres(float engadir){
        this.pagoDeAlquileres+=engadir;
    }

    public void engadirPasarPolaCasillaDeSalida(float engadir){
        this.pasarPolaCasillaDeSalida+=engadir;
    }

    public void engadirPremiosInversionesOBote(float engadir){
        this.premiosInversionesOBote+=engadir;
    }

    public void engadirVecesCarcel(float engadir){
        this.vecesEnLaCarcel+=engadir;
    }

    public float getDineroInvertido() {
        return dineroInvertido;
    }

    public void setDineroInvertido(float dineroInvertido) {
        this.dineroInvertido = dineroInvertido;
    }

    public float getPagoTasasEImpuestos() {
        return pagoTasasEImpuestos;
    }

    public void setPagoTasasEImpuestos(float pagoTasasEImpuestos) {
        this.pagoTasasEImpuestos = pagoTasasEImpuestos;
    }

    public float getPagoDeAlquileres() {
        return pagoDeAlquileres;
    }

    public void setPagoDeAlquileres(float pagoDeAlquileres) {
        this.pagoDeAlquileres = pagoDeAlquileres;
    }

    public float getCobroDeAlquileres() {
        return cobroDeAlquileres;
    }

    public void setCobroDeAlquileres(float cobroDeAlquileres) {
        this.cobroDeAlquileres = cobroDeAlquileres;
    }

    public float getPasarPolaCasillaDeSalida() {
        return pasarPolaCasillaDeSalida;
    }

    public void setPasarPolaCasillaDeSalida(float pasarPolaCasillaDeSalida) {
        this.pasarPolaCasillaDeSalida = pasarPolaCasillaDeSalida;
    }

    public float getPremiosInversionesOBote() {
        return premiosInversionesOBote;
    }

    public void setPremiosInversionesOBote(float premiosInversionesOBote) {
        this.premiosInversionesOBote = premiosInversionesOBote;
    }

    public int getVecesEnLaCarcel() {
        return vecesEnLaCarcel;
    }

    public void setVecesEnLaCarcel(int vecesEnLaCarcel) {
        this.vecesEnLaCarcel = vecesEnLaCarcel;
    }

    public float getDineroGastado() {
        return dineroGastado;
    }

    public void setDineroGastado(float dineroGastado) {
        this.dineroGastado = dineroGastado;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t dineroInvertido: " + dineroInvertido +
                ",\n\t pagoTasasEImpuestos: " + pagoTasasEImpuestos +
                ",\n\t pagoDeAlquileres: " + pagoDeAlquileres +
                ",\n\t cobroDeAlquileres: " + cobroDeAlquileres +
                ",\n\t pasarPolaCasillaDeSalida: " + pasarPolaCasillaDeSalida +
                ",\n\t premiosInversionesOBote: " + premiosInversionesOBote +
                ",\n\t vecesEnLaCarcel: " + vecesEnLaCarcel +
                "\n}";
    }
}
