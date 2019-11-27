package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.obxetos.Xogador;
import monopolinho.obxetos.excepcions.MonopolinhoComando;
import monopolinho.obxetos.excepcions.MonopolinhoComandoIncorrecto;
import monopolinho.obxetos.excepcions.MonopolinhoException;
import monopolinho.tipos.TipoEdificio;
import monopolinho.tipos.TipoMovemento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author Daniel Chenel
 * @author David Carracedo
 */

public class Menu {
    private Xogo xogo;
    private ArrayList<Xogador> xogadores;

    /**
     * Este é o constructor da clase Menu.
     * Insta o arraylist de xogadores do xogo.
     */
    public Menu(){
        xogadores=new ArrayList<>();
        Xogo.consola.imprimir(ReprTab.debuxoSimple());
        xogo=new Xogo();
    }


    /**
     * Este metodo inicia a partida.
     * Pregunta polos xogadores, crea o xogo e a consola de comandos.
     */

    /**
     * Este metodo permite preguntar o numero de xogadores e crealos.
     */
    public void preguntarXogadores(){
        Xogo.consola.imprimir("\n\nInserta o numero de xogadores: ");
        Scanner input=new Scanner(System.in);
        while (!input.hasNextInt()) input.next();
        int numXogadores = input.nextInt();
        input.nextLine();
        if (numXogadores>1){
            for (int i=1;i<=numXogadores;i++){
                Xogo.consola.imprimir("Introduce o nome do xogador "+i+": ");
                String nome=input.nextLine();
                Xogo.consola.imprimir("Introduce o tipo de movemento do xogador <coche/sombreiro/esfinxe/pelota> "+i+": ");
                String mov=input.nextLine();
                if((nome.toLowerCase().equals("banca"))||!xogo.crearXogador(nome,interpretarMov(mov))){
                    Xogo.consola.imprimirErro("Xa existe un usuario que se chama así");
                    i--;
                }
            }
            xogo.comezarPartida();
        }else {
            Xogo.consola.imprimirErro("Debe haber polo menos dous xogadores");
            System.exit(1);
        }
    }


    /**
     * Este metodo interpreta que tipo de movemento ten un avatar
     * @param tipomov tipo de movemento do avatar
     * @return TipoMovemento dun avatar
     */
    private TipoMovemento interpretarMov(String tipomov){
        switch (tipomov.toLowerCase()){
            case "pelota":
                return TipoMovemento.PELOTA;
            case "esfinxe":
            case "esfinge":
                return TipoMovemento.ESFINXE;
            case "sombreiro":
            case "sombrero":
                return TipoMovemento.SOMBREIRO;
            case "coche":
                return TipoMovemento.COCHE;
            default:
                Xogo.consola.imprimir("\nAvatar inválido, asignouseche o coche por defecto.");
                return TipoMovemento.COCHE;
        }
    }

    /**
     * Convirte un String a un TipoEdificio
     * @param tipo String edificio a convertir
     * @return Tipo de Edificio
     */
    private TipoEdificio interpretarEdificio(String tipo){
        switch (tipo){
            case "casa":
                return TipoEdificio.CASA;
            case "hotel":
                return TipoEdificio.HOTEL;
            case "piscina":
                return TipoEdificio.PISCINA;
            case "pista":
                return TipoEdificio.PISTA_DEPORTES;
        }
        return null;
    }

    /**
     * Este metodo sirve como consola de comandos.
     */
    public void consola() {
        mostrarComandos();

        while(true){
            Xogo.consola.imprimirsl("$> ");
            try{
                interpretarComando(Xogo.consola.leer(""));
            }catch (MonopolinhoException e){
                e.imprimirErro();
            }
        }
    }

    /**
     * Lee os comando dun arquvio
     */
    public void leerComandosArchivo(String directorio) throws IOException {
        BufferedReader buffRead = null;
        try{
            FileReader fileRead = new FileReader(directorio);
            buffRead = new BufferedReader(fileRead);
        } catch (FileNotFoundException notFound) {
            Xogo.consola.imprimir(notFound.getMessage());
            System.exit(0);
        }
        String leido = null;
        while ((leido = buffRead.readLine()) != null) {
            if (leido.equals("stop")){
                Xogo.consola.imprimirAuto("Esperando a entrada para continuar...");
                new Scanner(System.in).nextLine();
            }else{
                if(leido.startsWith("#"))continue;
                Xogo.consola.imprimirAuto(leido,"$> " );
                try{
                    interpretarComando(leido);
                }catch (MonopolinhoException e){
                    e.imprimirErro();
                }
            }
        }
        Xogo.consola.imprimirAuto("Volvendo o modo consola...");
        consola();
    }

    /**
     * Este metodo mostra por pantalla todos os comandos dispoñibles.
     */
    private void mostrarComandos(){
        Xogo.consola.imprimir("\n\nComandos dispoñibles:");
        Xogo.consola.imprimirComando("xogador" ,"Indica o xogador que ten o turno ten turno");
        Xogo.consola.imprimirComando("listar","" );
        Xogo.consola.imprimirSubComando("listar xogadores" ,"Mostra os xogadores");
        Xogo.consola.imprimirSubComando("listar avatares" ,"Mostra os avatares dos xogadores");
        Xogo.consola.imprimirSubComando("listar enventa" ,"Mostra as propiedades en venta");
        Xogo.consola.imprimirSubComando("listar edificios" ,"Mostra os edificios");
        Xogo.consola.imprimirSubComando("listar edificios <grupo>" ,"Mostra os edificios dun grupo");
        Xogo.consola.imprimirComando("lanzar dados","Lanza os dados");
        Xogo.consola.imprimirComando("acabar turno","Acaba o turno");
        Xogo.consola.imprimirComando("sair" ,"Sae do xogo");
        Xogo.consola.imprimirComando("sair carcere","O xogador sae do cárcere pagando unha tasa");
        Xogo.consola.imprimirComando("describir <casilla>" ,"Describe unha casilla");
        Xogo.consola.imprimirComando("describir xogador <nome>" ,"Describe un xogador");
        Xogo.consola.imprimirComando("describir avatar <avatar>","Describe un avatar");
        Xogo.consola.imprimirComando("comprar <casilla>" ,"Compra unha casilla");
        Xogo.consola.imprimirComando("edificar <casa/hotel/piscina/pista>" ,"Construe un edificio");
        Xogo.consola.imprimirComando("vender <casa/hotel/piscina/pista> <casilla> <numero>" ,"Vende un edificio");
        Xogo.consola.imprimirComando("bancarrota","Declara o xogador en bancarrota");
        Xogo.consola.imprimirComando("hipotecar <casilla>" ,"Hipoteca unha propiedade");
        Xogo.consola.imprimirComando("deshipotecar <casilla>" ,"Deshipoteca unha propiedade");
        Xogo.consola.imprimirComando("estadisticas" ,"Mostra as estadisticas do xogo");
        Xogo.consola.imprimirComando("estadisticas <xogador>" ,"Mostra as estadisticas do xogo");
        Xogo.consola.imprimirComando("ver taboeiro" ,"Mostra a representación do taboeiro");
        Xogo.consola.imprimirComando("cambiar modo" ,"Cambia de modo de xogo");
        Xogo.consola.imprimirComando("trato <nombre>: cambiar","" );
        Xogo.consola.imprimirSubComando("(<propiedade1>,<propiedade2>)","Cambia propiedade1 por propiedade2");
        Xogo.consola.imprimirSubComando("(<propiedade>,diñeiro)" ,"Cambia propiedade por X diñeiro");
        Xogo.consola.imprimirSubComando("(diñeiro,<propiedade>)" ,"Cambia X diñeiro por propiedade");
        Xogo.consola.imprimirSubComando("(<propiedade1>,<propiedade2> e diñeiro)","Cambia propiedade1 por propiedade2 mais diñeiro");
        Xogo.consola.imprimirSubComando("(<propiedade1> e diñeiro,<propiedade>)","Cambia propiedade1 mais diñerio por propiedade2");
        Xogo.consola.imprimirSubComando("(<propiedade1>,<propiedade2> e nonalquiler(<propiedade3>,turnos))" ,"Cambia propiedade1 por propiedade2 e non pagar alquiler en propiedade3 X turnos");
        Xogo.consola.imprimirComando("aceptar <trato>" ,"Acepta un trato que se che propuxo");
        Xogo.consola.imprimirComando("eliminar <trato>", "Elimina un trato que propuxeches");
        Xogo.consola.imprimirComando("comandos","Mostra esta mensaxe de axuda");
    }

    /**
     * Este metodo interpreta o comando escrito e chama as funcions necesarias para realizar a accion do comando.
     * @param comando
     */
    private void interpretarComando(String comando) throws MonopolinhoException {
        String[] cmds=comando.split(" ");
        if ((!xogo.partidaComezada())&&(cmds[0].toLowerCase().equals("crear"))){
            if(cmds.length!=4){
                throw new MonopolinhoComandoIncorrecto("Sintaxe: crear xogador <nome> <avatar>");
            }
            xogo.crearXogador(cmds[2],interpretarMov(cmds[3]));
            return;
        }
        if(!xogo.partidaComezada()){
            if(xogo.getNumeroXogadores()>=2)xogo.comezarPartida();
            else {
                Xogo.consola.imprimirErro("Non hai suficientes xogadores");
                return;
            }
        }
        switch (cmds[0].toLowerCase()){
            case "xogador":
            case "jugador":
                xogo.mostrarTurno();
                break;
            case "listar":
                if(cmds.length<2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: listar <xogadores/avatares/enventa/edificios>\n" +
                            "Sintaxe: listar <edificios> <grupo>");
                }
                switch (cmds[1].toLowerCase()){
                    case "xogadores":
                    case "jugadores":
                        xogo.listarXogadores();
                        break;
                    case "avatares":
                        xogo.listarAvatares();
                        break;
                    case "enventa":
                        xogo.listarCasillaEnVenta();
                        break;
                    case "edificios":
                        if(cmds.length==3){
                            xogo.listarEdificiosGrupo(cmds[2]);
                            return;
                        }
                        xogo.listarEdificios();
                        break;
                    default:
                        throw new MonopolinhoComandoIncorrecto("\nOpción de listaxe non válida");
                }
                break;
            case "lanzar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: lanzar dados");
                }
                xogo.lanzarDados();
                break;

            case "acabar":
                if(cmds.length!=2) {
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: acabar turno");
                }
                xogo.pasarTurno();
                break;
            case "salir":
            case "sair":
            case "exit":
                if(cmds.length==1){
                    Xogo.consola.imprimir("Saindo...");
                    System.exit(0);
                }else if (cmds.length==2){
                    xogo.sairCarcere();
                }else{
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: sair carecere\nSintaxe: sair");
                }
                break;
            case "describir":
                if(cmds.length<2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: describir <xogador/avatar> <nome>\n" +
                            "Sintaxe: describir <casilla>"
                    );
                }
                switch (cmds[1]){
                    case "xogador":
                    case "jugador":
                        if(cmds.length!=3){
                            throw new MonopolinhoComandoIncorrecto("Sintaxe: describir xogador <nome>");
                        }
                        xogo.describirXogador(cmds[2]);
                        break;
                    case "avatar":
                        if(cmds.length!=3){
                            throw new MonopolinhoComandoIncorrecto("Sintaxe: describir avatar <id>");
                        }
                        xogo.describirAvatar(cmds[2]);
                        break;
                    default:
                        xogo.describirCasilla(cmds[1]);
                        break;
                }
                break;
            case "comprar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: comprar <casilla>");
                }
                xogo.comprarCasilla(cmds[1]);
                break;
            case "edificar":
                if(cmds.length==2){
                    xogo.edificar(interpretarEdificio(cmds[1]));
                }else{
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: edificar <casa/hotel/piscina/pista>");
                }
                break;
            case "vender":
                if(cmds.length!=4){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: vender <casa/hotel/piscina/pista> <casilla> <numero> ");
                }
                xogo.venderEdificio(interpretarEdificio(cmds[1]),cmds[2],Integer.parseInt(cmds[3]));
                break;
            case "bancarrota":
                xogo.declararBancarrota();
                break;
            case "hipotecar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: hipotecar <casilla>");
                }
                xogo.hipotecarCasilla(cmds[1]);
                break;
            case "deshipotecar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: deshipotecar <casilla>");
                }
                xogo.deshipotecarCasilla(cmds[1]);
                break;
            case "ver":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("Sintaxe: ver taboeiro/tableiro");
                }
                xogo.mostrarTaboeiro();
                break;
            case "estadisticas":
                if(cmds.length==2){
                    xogo.mostrarEstadisticasXogador(cmds[1]);
                }else{
                    xogo.mostrarEstadisticasXogo();
                }
                break;
            case "cambiar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("cambiar modo");
                }
                xogo.cambiarModoXogo();
                break;
            case "trato":
                if(cmds.length<3){
                    throw new MonopolinhoComandoIncorrecto("trato <nombre>: cambiar (opcions)");
                }
                xogo.proponerTrato(cmds);
                break;
            case "tratos":
                xogo.listarTratos();
                break;
            case "eliminar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("eliminar <trato>");
                }
                xogo.eliminarTrato(cmds[1]);
                break;
            case "aceptar":
                if(cmds.length!=2){
                    throw new MonopolinhoComandoIncorrecto("aceptar <trato>");
                }
                xogo.aceptarTrato(cmds[1]);
                break;
            case "accions":
                xogo.listarAccions();
                break;
            case "mov":
                //BORRARR
                xogo.mov(Integer.parseInt(cmds[1]));
                break;
            case "mova":
                //BORRARR
                xogo.mova(Integer.parseInt(cmds[1]));
                break;
            case "desfacer":
                xogo.desfacer();
                break;
            case "comandos":
            case "axuda":
                mostrarComandos();
                break;
            default:
                throw new MonopolinhoComando(ReprTab.colorear(Valor.ReprColor.ANSI_RED,"Comando non recoñecido, para máis información escribe comandos"));
        }
    }
}
