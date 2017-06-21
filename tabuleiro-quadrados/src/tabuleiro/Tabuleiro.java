/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabuleiro;

/**
 *
 * @author bruno
 */
public class Tabuleiro {
    
    public int[][] matriz;
    private int dim = 3;
    private int posVazia;
    
    public Tabuleiro(){
        
        this.matriz = new int[this.dim][this.dim];
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public int getSize() {
        return dim;
    }

    public void setSize(int dim) {
        this.dim = dim;
    }

    public int getPosVazia() {
        return posVazia;
    }

    public void setPosVazia(int posVazia) {
        this.posVazia = posVazia;
    }
    
}
