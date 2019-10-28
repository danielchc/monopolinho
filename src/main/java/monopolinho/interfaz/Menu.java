package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;
import monopolinho.obxetos.Xogador;
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
                    System.err.println("Xa existe un usuario que se chama así");
                    i--;
                }
            }
            xogo.comezarPartida();
        }else {
            System.err.println("Debe haber polo menos dous xogadores");
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
     * Este metodo sirve como consola de comandos.
     */
    public void consola(){
        mostrarComandos();
        Scanner scanner= new Scanner(System.in);
        while(true){
            System.out.print("$> ");
            interpretarComando(scanner.nextLine());
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
                System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,leido));
                interpretarComando(leido);
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
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"sair carcel")+"\tO xogador sae do cárcere pagando unha tasa"+
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
                "\n\t+ " +ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"comandos")+"\tMostra esta mensaxe de axuda";
        System.out.println(comandos);
    }

    /**
     * Este metodo interpreta o comando escrito e chama as funcions necesarias para realizar a accion do comando.
     * @param comando
     */
    private void interpretarComando(String comando) {
        String[] cmds=comando.split(" ");
        if ((!xogo.partidaComezada())&&(cmds[0].toLowerCase().equals("crear"))){
            if(cmds.length!=4){
                System.out.println("Sintaxe: crear xogador <nome> <avatar>");
                return;
            }
            xogo.crearXogador(cmds[2],interpretarMov(cmds[3]));
            return;
        }
        if(!xogo.partidaComezada()){
            if(xogo.getNumeroXogadores()>=2)xogo.comezarPartida();
            else {
                System.err.println("Non hai suficientes xogadores");
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
                    System.out.println("Sintaxe: listar <xogadores/avatares/enventa/edificios>");
                    System.out.println("Sintaxe: listar <edificios> <grupo>");
                    return;
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
                        System.out.println("\nOpción de listaxe non válida");
                        break;
                }
                break;
            case "lanzar":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: lanzar dados");
                    return;
                }
                xogo.lanzarDados();
                break;

            case "acabar":
                if(cmds.length!=2) {
                    System.out.println("Sintaxe: acabar turno");
                    return;
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
                    xogo.salirCarcel();
                }else{
                    System.out.println("Sintaxe: sair carcel");
                    System.out.println("Sintaxe: sair");
                    return;
                }
                break;
            case "describir":
                if(cmds.length<2){
                    System.out.println("Sintaxe: describir <xogador/avatar> <nome>");
                    System.out.println("Sintaxe: describir <casilla>");
                    return;
                }
                switch (cmds[1]){
                    case "xogador":
                    case "jugador":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir xogador <nome>");
                            return;
                        }
                        xogo.describirXogador(cmds[2]);
                        break;
                    case "avatar":
                        if(cmds.length!=3){
                            System.out.println("Sintaxe: describir avatar <id>");
                            return;
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
                    System.out.println("Sintaxe: comprar <casilla>");
                    return;
                }
                xogo.comprarCasilla(cmds);
                break;
            case "edificar":
                if(cmds.length==2){
                    switch (cmds[1]){
                        case "casa":
                            xogo.edificarCasa();
                            return;
                        case "hotel":
                            xogo.edificarHotel();
                            return;
                        case "piscina":
                            xogo.edificarPiscina();
                            return;
                        case "pista":
                            xogo.edificarPistaDeportes();
                            return;
                    }
                    System.out.println("Sintaxe: edificar <casa/hotel/piscina/pista>");
                }
                break;
            case "vender":
                if(cmds.length!=4){
                    System.out.println("Sintaxe: vender <casa/hotel/piscina/pista> <casilla> <numero> ");
                    return;
                }
                xogo.venderEdificio(cmds[1],cmds[2],Integer.parseInt(cmds[3]));
                break;
            case "bancarrota":
                xogo.declararBancarrota();
                break;
            case "hipotecar":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: hipotecar <casilla>");
                    return;
                }
                xogo.hipotecarCasilla(cmds[1]);
                break;
            case "deshipotecar":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: deshipotecar <casilla>");
                    return;
                }
                xogo.deshipotecarCasilla(cmds[1]);
                break;
            case "ver":
                if(cmds.length!=2){
                    System.out.println("Sintaxe: ver taboeiro/tableiro");
                    return;
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
                    System.out.println("cambiar modo");
                    return;
                }
                xogo.cambiarModoXogo();
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
                System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_RED,"Comando non recoñecido, para máis información escribe ")+ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,"comandos"));
        }
    }
}
