
import tabuleiro.Jogo;
import tabuleiro.Tabuleiro;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Teste {

    public static void main(String[] args) {
        
        Jogo jogo = new Jogo();
        //int data[][] = new int[][] {{1,3,-1},{5,2,6},{4,7,8}};
        //jogo.tabuleiroInicial.setPosVazia(2);
        //int data[][] = new int[][] {{5,2,3},{4,1,8},{-1,7,6}};
        //jogo.tabuleiroInicial.setPosVazia(6);
        //int data[][] = new int[][] {{1,-1,2},{5,4,3},{8,7,6}};
        //jogo.tabuleiroInicial.setPosVazia(1);
        int data[][] = new int[][] {{1,3,5},{4,-1,2},{7,8,6}};
        jogo.tabuleiroInicial.setPosVazia(4);
        jogo.tabuleiroInicial.setMatriz(data);
        System.out.println(jogo);
        //jogo.embaralhar();
        //System.out.println(jogo);
        //jogo.aleatorio();
        //jogo.heuristicaEmUmNivel();
        //jogo.heuristicaEmDoisNiveis();
        jogo.heuristicaPessoal();
        System.out.println("\n##########################");
        System.out.println("Números de Solução: " + (jogo.getListaResultado().size() - 1));
        System.out.println("Números de tentativas: " + (jogo.contadorSolucao));
        System.out.println("##########################\n");
        System.out.println(jogo);
        /*for(Tabuleiro t: jogo.getListaResultado()){
            System.out.println(t);
        }
   
        /*
        Tabuleiro a = new Tabuleiro();
        a.setMatriz(new int[][]{{1,-1,3},{4,7,6},{5,2,8}});
        
        Tabuleiro b = new Tabuleiro();
        b.setMatriz(new int[][]{{1,-1,3},{4,7,6},{5,2,1}});
        
        System.out.println(a.equals(b));*/
    }
}
