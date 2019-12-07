package monopolinho.interfaz;

import monopolinho.excepcions.MonopolinhoException;
import monopolinho.excepcions.MonopolinhoGeneralException;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoMovemento;

/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public interface Comandos {

    void describirCasilla(String nome) throws MonopolinhoException;

    void describirXogador(String nome) throws MonopolinhoException;

    void describirAvatar(String avatarId) throws MonopolinhoException;

    void listarEdificios();

    void listarEdificiosGrupo(String cmds) throws MonopolinhoGeneralException;

    void listarAvatares();

    void listarXogadores();

    void listarCasillaEnVenta();

    void pasarTurno() throws MonopolinhoGeneralException;

    void sairCarcere() throws MonopolinhoGeneralException;

    void comprarCasilla(String nome) throws MonopolinhoException;

    void mostrarTaboeiro();

    void mostrarEstadisticasXogador(String nome) throws MonopolinhoGeneralException;

    void mostrarEstadisticasXogo();

    void hipotecarCasilla(String nome) throws  MonopolinhoException;

    void deshipotecarCasilla(String nome) throws MonopolinhoException;

    void declararBancarrota() throws MonopolinhoGeneralException;

    void edificar(TipoEdificio tipo) throws MonopolinhoException;

    void venderEdificio(TipoEdificio tipo, String casilla, int numero) throws MonopolinhoException;

    void mostrarTurno() throws MonopolinhoGeneralException;

    boolean crearXogador(String nombre, TipoMovemento tipoMov);

    void lanzarDados() throws MonopolinhoException;

    void cambiarModoXogo();

    void proponerTrato(String[] cmds) throws MonopolinhoException;

    void listarTratos();

    void aceptarTrato(String id) throws MonopolinhoGeneralException;

    void eliminarTrato(String id) throws MonopolinhoGeneralException;

    void comezarPartida();

    void mov(int i) throws MonopolinhoException;

    void mova(int i) throws MonopolinhoException;

}
