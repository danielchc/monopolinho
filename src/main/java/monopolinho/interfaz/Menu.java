package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.obxetos.Xogador;
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
        System.out.println(ReprTab.debuxoSimple());
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
        System.out.print("\n\nInserta o numero de xogadores: ");
        Scanner input=new Scanner(System.in);
        while (!input.hasNextInt()) input.next();
        int numXogadores = input.nextInt();
        input.nextLine();
        if (numXogadores>1){
            for (int i=1;i<=numXogadores;i++){
                System.out.println("Introduce o nome do xogador "+i+": ");
                String nome=input.nextLine();
                System.out.println("Introduce o tipo de movemento do xogador <coche/sombreiro/esfinxe/pelota> "+i+": ");
                String mov=input.nextLine();
                if((nome.toLowerCase().equals("banca"))||!xogo.crearXogador(nome,interpretarMov(mov))){
                    ReprTab.imprimirErro("Xa existe un usuario que se chama así");
                    i--;
                }
            }
            xogo.comezarPartida();
        }else {
            ReprTab.imprimirErro("Debe haber polo menos dous xogadores");
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
                System.out.println("\nAvatar inválido, asignouseche o coche por defecto.");
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
        Scanner scanner= new Scanner(System.in);
        while(true){
            System.out.print("$> ");
            try{
                interpretarComando(scanner.nextLine());
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
            System.out.println(notFound.getMessage());
            System.exit(0);
        }
        String leido = null;
        while ((leido = buffRead.readLine()) != null) {
            if (leido.equals("stop")){
                System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,"Esperando a entrada para continuar..."));
                new Scanner(System.in).nextLine();
            }else{
                System.out.println("$> " + ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,leido));
                try{
                    interpretarComando(leido);
                }catch (MonopolinhoException e){
                    e.imprimirErro();
                }
            }
        }
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,"Volvendo o modo consola..."));
        consola();
    }

    /**
     * Este metodo mostra por pantalla todos os comandos dispoñibles.
     */
    private void mostrarComandos(){
        String comandos="\n\nComandos dispoñibles:" +
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"xogador" )+"\tIndica o xogador que ten o turno ten turno"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"listar" )+
                "\n\t\t- " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"listar xogadores" )+"\tMostra os xogadores"+
                "\n\t\t- " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"listar avatares" )+"\tMostra os avatares dos xogadores"+
                "\n\t\t- " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"listar enventa" )+"\tMostra as propiedades en venta"+
                "\n\t\t- " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"listar edificios" )+"\tMostra os edificios"+
                "\n\t\t- " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"listar edificios <grupo>" )+"\tMostra os edificios dun grupo"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"lanzar dados")+ "\tLanza os dados"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"acabar turno")+ "\tAcaba o turno"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"sair" )+"\tSae do xogo"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"sair carcere")+"\tO xogador sae do cárcere pagando unha tasa"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"describir <casilla>" )+"\tDescribe unha casilla"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"describir xogador <nome>" )+"\tDescribe un xogador"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"describir avatar <avatar>")+"\tDescribe un avatar"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"comprar <casilla>" )+"\tCompra unha casilla"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"edificar <casa/hotel/piscina/pista>" )+"\tConstrue un edificio"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"vender <casa/hotel/piscina/pista> <casilla> <numero>" )+"\tVende un edificio"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"bancarrota" )+ "\tDeclara o xogador en bancarrota"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"hipotecar <casilla>" )+"\tHipoteca unha propiedade"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"deshipotecar <casilla>" )+"\tDeshipoteca unha propiedade"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"estadisticas" )+"\tMostra as estadisticas do xogo"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"estadisticas <xogador>" )+"\tMostra as estadisticas do xogo"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"ver taboeiro" )+"\tMostra a representación do taboeiro"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"cambiar modo" )+"\tCambia de modo de xogo"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"trato <nombre>: cambiar" )+
                "\n\t\t " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"(<propiedade1>,<propiedade2>)" )+ "\tCambia propiedade1 por propiedade2"+
                "\n\t\t " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"(<propiedade>,diñeiro)" )+"\tCambia propiedade por X diñeiro"+
                "\n\t\t " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"(diñeiro,<propiedade>)" )+"\tCambia X diñeiro por propiedade"+
                "\n\t\t " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"(<propiedade1>,<propiedade2> e diñeiro)" )+ "\tCambia propiedade1 por propiedade2 mais diñeiro"+
                "\n\t\t " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"(<propiedade1> e diñeiro,<propiedade>)" )+ "\tCambia propiedade1 mais diñerio por propiedade2"+
                "\n\t\t " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"(<propiedade1>,<propiedade2> e nonalquiler(<propiedade3>,turnos))" )+ "\tCambia propiedade1 por propiedade2 e non pagar alquiler en propiedade3 X turnos"+

                "\n\tPropoñer un trato a outro xogador"+
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"comandos")+"\tMostra esta mensaxe de axuda";
        System.out.println(comandos);
    }

    /**
     * Este metodo interpreta o comando escrito e chama as funcions necesarias para realizar a accion do comando.
     * @param comando
     */
    private void interpretarComando(String comando) throws MonopolinhoComandoIncorrecto {
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
                ReprTab.imprimirErro("Non hai suficientes xogadores");
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
                    System.out.println("Saindo...");
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
                xogo.comprarCasilla(cmds);
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
            case "comandos":
            case "axuda":
                mostrarComandos();
                break;
            default:
               throw new MonopolinhoComandoIncorrecto(ReprTab.colorear(Valor.ReprColor.ANSI_RED,"Comando non recoñecido, para máis información escribe ")+ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"comandos"));
        }
    }
}
