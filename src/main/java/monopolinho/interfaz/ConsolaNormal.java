package monopolinho.interfaz;

import monopolinho.axuda.ReprTab;
import monopolinho.axuda.Valor;

import java.util.Scanner;

public class ConsolaNormal implements Consola {
    Scanner scanner;
    @Override
    public void imprimir(String texto) {
        System.out.println(texto);
    }

    @Override
    public void imprimirsl(String texto) {
        System.out.print(texto);
    }

    @Override
    public void imprimir(Object o) {
        System.out.println(o);
    }

    @Override
    public void imprimirErro(String erro) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_RED,erro));
    }

    @Override
    public void imprimirAdvertencia(String adv) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_YELLOW_BOLD,adv));
    }

    @Override
    public void imprimirAuto(String adv) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,adv));
    }

    @Override
    public void imprimirAuto(String adv,String pre) {
        System.out.println(pre+ " "+ ReprTab.colorear(Valor.ReprColor.ANSI_GREEN_BOLD,adv));
    }

    @Override
    public void imprimirNegrita(String texto) {
        System.out.println(ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,texto));
    }

    @Override
    public void imprimirComando(String comando,String descripcion) {
        System.out.println("\t"+ "- "+ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,comando) +" "+descripcion);
    }

    @Override
    public void imprimirSubComando(String comando, String descripcion) {
        System.out.println("\t\t"+ "- "+ReprTab.colorear(Valor.ReprColor.ANSI_BLACK_BOLD,comando) +" "+descripcion);
    }

    @Override
    public String leer(String texto) {
        scanner= new Scanner(System.in);
        return scanner.nextLine();
    }
}
