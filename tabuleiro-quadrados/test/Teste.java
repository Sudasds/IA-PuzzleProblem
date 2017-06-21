
import tabuleiro.Jogo;

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
        int data[][] = new int[][] {{4,5,7},{8,1,2},{3,6,-1}};
        jogo.tabuleiroInicial.setMatriz(data);
        
        //t.embaralhar();
        System.out.println(jogo);
        
        jogo.aleatorio();
        //jogo.distanciaEmUmNivel();
        
        System.out.println(jogo);
    }
}
