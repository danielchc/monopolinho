package monopolinho;

public class Avatar {
    enum TipoAvatar{
        DEFAULT,
        COCHE,
        LARANJO
    }
    private String[] avatares={"O","C","L"};
    private TipoAvatar tipoAvatar;

    public Avatar(TipoAvatar tipoAvatar){
        this.tipoAvatar =tipoAvatar;
    }

    public void setTipoAvatar(TipoAvatar tipoAvatar) {
        this.tipoAvatar = tipoAvatar;
    }

    public TipoAvatar getTipoAvatar() {
        return tipoAvatar;
    }

    public String getRepresentacion(){
        return avatares[this.tipoAvatar.ordinal()];
    }
}
