package monopolinho.interfaz;

import monopolinho.obxetos.Dados;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.obxetos.excepcions.MonopolinhoCasillaInexistente;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.obxetos.excepcions.MonopolinhoNonSePodeConstruir;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoMovemento;

import java.util.ArrayList;

public interface Comandos {

    void describirCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void describirXogador(String nome);

    void describirAvatar(String avatarId);

    void listarEdificios();

    void listarEdificiosGrupo(String cmds);

    void listarAvatares();

    void listarXogadores();

    void listarCasillaEnVenta();

    void pasarTurno();

    void sairCarcere();

    void comprarCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void mostrarTaboeiro();

    void mostrarEstadisticasXogador(String nome);

    void mostrarEstadisticasXogo();

    void hipotecarCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void deshipotecarCasilla(String nome) throws MonopolinhoCasillaInexistente, MonopolinhoException;

    void declararBancarrota();

    void edificar(TipoEdificio tipo) throws MonopolinhoNonSePodeConstruir;

    void venderEdificio(TipoEdificio tipo, String casilla, int numero) throws MonopolinhoCasillaInexistente;

    void mostrarTurno();

    boolean crearXogador(String nombre, TipoMovemento tipoMov);

    void lanzarDados();

    void cambiarModoXogo();

    void proponerTrato(String[] cmds);

    void listarTratos();

    void aceptarTrato(String id);

    void eliminarTrato(String id);

    void comezarPartida();

    void mov(int i);

    void mova(int i);

}
