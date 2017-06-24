
import tabuleiro.Jogo;
import tabuleiro.Tabuleiro;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leandroungari
 */
public class Teste {

    public static void main(String[] args) {
        
        Jogo jogo = new Jogo();
        //int data[][] = new int[][] {{1,-1,3},{4,7,6},{5,2,8}};
        //int data[][] = new int[][] {{1,2,3},{7,4,5},{-1,8,6}};
        //int data[][] = new int[][] {{4,-1,2},{8,1,3},{7,6,5}};
        //jogo.tabuleiroInicial.setMatriz(data);
        //jogo.tabuleiroInicial.setPosVazia(6);
        //System.out.println(jogo);
        jogo.embaralhar();
        //System.out.println(jogo);
        //{{2,6,3},{1,8,4},{7,-1,5}}
        //jogo.aleatorio();
        //jogo.heuristicaEmUmNivel();
        jogo.heuristicaPessoal();
        System.out.println("##########################");
        System.out.println("Números de movimentos: " + jogo.contadorSolucao);
        System.out.println("##########################");
        //System.out.println(jogo);
        for(Tabuleiro t: jogo.getListaResultado()){
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
