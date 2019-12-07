package monopolinho.excepcions.comandos;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class MonopolinhoComandoNonExiste extends MonopolinhoComandoException {
    public MonopolinhoComandoNonExiste(String mensaxe) {
        super(mensaxe);
    }
}
