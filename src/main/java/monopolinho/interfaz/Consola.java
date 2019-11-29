package monopolinho.interfaz;

public interface Consola {
    void imprimir(String texto);
    void imprimirsl(String texto);
    void imprimir(Object o);
    void imprimirErro(String erro);
    void imprimirAdvertencia(String adv);
    void imprimirAuto(String adv);
    void imprimirAuto(String adv,String pre);
    void imprimirNegrita(String texto);
    void imprimirComando(String comando,String descripcion);
    void imprimirSubComando(String comando,String descripcion);
    String leer(String descripcion);
}
