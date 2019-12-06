package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;

import java.util.Scanner;
/**
 * @author Daniel Chenel
 * @author David Carracedo
 */
public class ConsolaNormal implements Consola {
    Scanner scanner;

    /**
     * Imprimir textxo con salto de liña
     * @param texto
     */
    @Override
    public void imprimir(String texto) {
        System.out.println(texto);
    }

    /**
     * Imprimir texto nunha linha
     * @param texto
     */
    @Override
    public void imprimirsl(String texto) {
        System.out.print(texto);
    }

    /**
     * Imprime un obxecto
     * @param o
     */
    @Override
    public void imprimir(Object o) {
        System.out.println(o);
    }

    /**
     * Imprime un mensaxe en vermello
     * @param erro
     */
    @Override
    public void imprimirErro(String erro) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_RED,erro));
    }

    /**
     * Imprime un mensaxe en amarelo
     * @param adv
     */
    @Override
    public void imprimirAdvertencia(String adv) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_YELLOW_BOLD,adv));
    }

    /**
     * Imprime un texto en verde
     * @param adv
     */
    @Override
    public void imprimirAuto(String adv) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,adv));
    }

    /**+
     * Imprime un texto en verde con un texto antes
     * @param adv
     * @param pre
     */
    @Override
    public void imprimirAuto(String adv,String pre) {
        System.out.println(pre+ " "+ ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,adv));
    }

    /**
     * Imprime un texto en negrita
     * @param texto
     */
    @Override
    public void imprimirNegrita(String texto) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,texto));
    }

    /**
     * Impriem o texto dun comando
     * @param comando
     * @param descripcion
     */
    @Override
    public void imprimirComando(String comando,String descripcion) {
        System.out.println("\t"+ "- "+ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,comando) +" "+descripcion);
    }

    /**
     * Imprimir o texto dun subcomando
     * @param comando
     * @param descripcion
     */
    @Override
    public void imprimirSubComando(String comando, String descripcion) {
        System.out.println("\t\t"+ "- "+ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,comando) +" "+descripcion);
    }

    /**
     * Lee unha función
     * @param texto
     * @return
     */
    @Override
    public String leer(String texto) {
        scanner= new Scanner(System.in);
        return scanner.nextLine();
    }
}
