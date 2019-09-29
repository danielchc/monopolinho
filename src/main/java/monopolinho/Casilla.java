package monopolinho;

public class Casilla {

    /* Atributos */
    private String NombreCasilla;
    private String TipoCasilla;
    private String GrupoCasilla; //Color da casilla
    private String Propietario;
    private float Valor;
    private float Alquiler;
    private float ValorHotel;
    private float ValorCasa;
    private float ValorPiscina;
    private float ValorPistaDeporte;
    private float AlquilerUnhaCasa;
    private float AlquilerDuasCasas;
    private float AlquilerTresCasas;
    private float AlquilerCatroCasas;
    private float AlquilerHotel;
    private float AlquilerPiscina;
    private float AlquilerPistaDeporte;

    /* Constructores */
    public Casilla(String NombreCasilla,String TipoCasilla){
        this.NombreCasilla=NombreCasilla;
        this.TipoCasilla=TipoCasilla;
    }

    /* Getters e setters */
    public String getNombreCasilla(){
        return NombreCasilla;
    }
    public void setNombreCasilla(String NombreCasilla){
        this.NombreCasilla=NombreCasilla;
    }

    public String getTipoCasilla(){
        return TipoCasilla;
    }
    public void setTipoCasilla(String TipoCasilla){
        this.TipoCasilla=TipoCasilla;
    }

    public String getGrupoCasilla(){
        return GrupoCasilla;
    }
    public void setGrupoCasilla(String GrupoCasilla){
        this.GrupoCasilla=GrupoCasilla;
    }

    public String getPropietario(){
        return Propietario;
    }
    public void setPropietario(String Propietario){
        this.Propietario=Propietario;
    }

    public float getValor(){
        return Valor;
    }
    public void setValor(float Valor){
        this.Valor=Valor;
    }

    public float getAlquiler(){
        return Alquiler;
    }
    public void setAlquiler(float Alquiler){
        this.Alquiler=Alquiler;
    }

    public float getValorHotel(){
        return ValorHotel;
    }
    public void setValorHotel(float ValorHotel){
        this.ValorHotel=ValorHotel;
    }

    public float getValorCasa(){
        return ValorCasa;
    }
    public void setValorCasa(float ValorCasa){
        this.ValorCasa=ValorCasa;
    }

    public float getValorPiscina(){
        return ValorPiscina;
    }
    public void setValorPiscina(float ValorPiscina){
        this.ValorPiscina=ValorPiscina;
    }

    public float getValorPistaDeporte(){
        return ValorPistaDeporte;
    }
    public void setValorPistaDeporte(float ValorPistaDeporte){
        this.ValorPistaDeporte=ValorPistaDeporte;
    }

    public float getAlquilerUnhaCasa(){
        return AlquilerUnhaCasa;
    }
    public void setAlquilerUnhaCasa(float AlquilerUnhaCasa){
        this.AlquilerUnhaCasa=AlquilerUnhaCasa;
    }

    public float getAlquilerDuasCasas(){
        return AlquilerDuasCasas;
    }
    public void setAlquilerDuasCasas(float AlquilerDuasCasas){
        this.AlquilerDuasCasas=AlquilerDuasCasas;
    }

    public float getAlquilerTresCasas(){
        return AlquilerTresCasas;
    }
    public void setAlquilerTresCasas(float AlquilerTresCasas){
        this.AlquilerTresCasas=AlquilerTresCasas;
    }

    public float getAlquilerCatroCasas(){
        return AlquilerCatroCasas;
    }
    public void setAlquilerCatroCasas(float AlquilerCatroCasas){
        this.AlquilerCatroCasas=AlquilerCatroCasas;
    }

    public float getAlquilerHotel(){
        return AlquilerHotel;
    }
    public void setAlquilerHotel(float AlquilerHotel){
        this.AlquilerHotel=AlquilerHotel;
    }

    public float getAlquilerPiscina(){
        return AlquilerPiscina;
    }
    public void setAlquilerPiscina(float AlquilerPiscina){
        this.AlquilerPiscina=AlquilerPiscina;
    }

    public float getAlquilerPistaDeporte(){
        return AlquilerPistaDeporte;
    }
    public void setAlquilerPistaDeporte(float AlquilerPistaDeporte){
        this.AlquilerPistaDeporte=AlquilerPistaDeporte;
    }

    /* Para imprimir a información da casilla */
    @Override
    public String toString(){
        String informacion="\nTipo: "+this.TipoCasilla+"\nGrupo: "+this.GrupoCasilla+"\nPropietario: "+this.Propietario
                            +"\nValor: "+this.Valor+"\nAlquiler: "+this.Alquiler+"\nValor Casa: "+this.ValorCasa
                            +"\nValor Hotel: "+this.ValorHotel+"\nValor Piscina: "+this.ValorPiscina+"\nValor Pista de Deporte: "+this.ValorPistaDeporte
                            +"\nAlquiler 1 Casa: "+this.AlquilerUnhaCasa+"\nAlquiler 2 Casas: "+this.AlquilerDuasCasas+"\nAlquiler 3 Casas: "+this.AlquilerTresCasas+"\nAlquiler 4 Casas: "+this.AlquilerCatroCasas
                            +"\nAlquiler Hotel: "+this.AlquilerHotel+"\nAlquiler Piscina: "+this.AlquilerPiscina+"\nAlquiler Pista de Deporte: "+this.AlquilerPistaDeporte;
        return informacion;
    }
}
