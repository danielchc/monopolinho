package monopolinho.interfaz;

import monopolinho.obxetos.Dados;
import monopolinho.obxetos.Taboeiro;
import monopolinho.obxetos.Turno;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.avatares.Avatar;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoMovemento;

import java.util.ArrayList;

public interface Comandos {
    void describirCasilla(String nome);

    void describirXogador(String nome);

    void describirAvatar(String avatarId);

    void listarEdificios();

    void listarEdificiosGrupo(String cmds);

    void listarAvatares();

    void listarXogadores();

    void listarCasillaEnVenta();

    void pasarTurno();

    void sairCarcere();

    void comprarCasilla(String[] cmds);

    void mostrarTaboeiro();

    void mostrarEstadisticasXogador(String nome);

    void mostrarEstadisticasXogo();

    void hipotecarCasilla(String nome);

    void deshipotecarCasilla(String nome);

    void declararBancarrota();

    void edificar(TipoEdificio tipo);

    void venderEdificio(TipoEdificio tipo, String casilla, int numero);

    void mostrarTurno();

    boolean crearXogador(String nombre, TipoMovemento tipoMov);

    void lanzarDados();

    void cambiarModoXogo();

    void proponerTrato(String[] cmds);

    void listarTratos();

    Taboeiro getTaboeiro();

    Dados getDados();

    Xogador getBanca();

    ArrayList<Xogador> getXogadores();

    Turno getTurno();

    int getNumeroXogadores();

    boolean partidaComezada();

    void comezarPartida();

    void moverModoNormal(int valorDados);

    boolean comprobarAvatarRepetido(Avatar avatar);

    //BORRAR
    void mov(int i);

    void mova(int i);
}
