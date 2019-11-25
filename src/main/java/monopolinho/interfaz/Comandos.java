package monopolinho.interfaz;

import monopolinho.obxetos.Dados;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.excepcions.MonopolinhoCasillaInexistente;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoGeneralException;
import monopolinho.obxetos.excepcions.MonopolinhoNonSePodeConstruir;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoMovemento;

import java.util.ArrayList;

public interface Comandos {

    void describirCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void describirXogador(String nome) throws MonopolinhoGeneralException, MonopolinhoException;

    void describirAvatar(String avatarId) throws MonopolinhoException;

    void listarEdificios();

    void listarEdificiosGrupo(String cmds) throws MonopolinhoGeneralException;

    void listarAvatares();

    void listarXogadores();

    void listarCasillaEnVenta();

    void pasarTurno() throws MonopolinhoGeneralException;

    void sairCarcere() throws MonopolinhoGeneralException;

    void comprarCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void mostrarTaboeiro();

    void mostrarEstadisticasXogador(String nome) throws MonopolinhoGeneralException;

    void mostrarEstadisticasXogo();

    void hipotecarCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void deshipotecarCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void declararBancarrota() throws MonopolinhoGeneralException;

    void edificar(TipoEdificio tipo) throws MonopolinhoNonSePodeConstruir;

    void venderEdificio(TipoEdificio tipo, String casilla, int numero) throws MonopolinhoCasillaInexistente, MonopolinhoGeneralException;

    void mostrarTurno() throws MonopolinhoGeneralException;

    boolean crearXogador(String nombre, TipoMovemento tipoMov);

    void lanzarDados() throws MonopolinhoException;

    void cambiarModoXogo();

    void proponerTrato(String[] cmds) throws MonopolinhoGeneralException;

    void listarTratos();

    void aceptarTrato(String id);

    void eliminarTrato(String id) throws MonopolinhoGeneralException;

    void comezarPartida();

    void mov(int i) throws MonopolinhoException;

    void mova(int i);

}
