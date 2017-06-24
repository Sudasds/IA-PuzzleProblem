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
public class Tabuleiro implements Comparable<Tabuleiro>{
    
    public int[][] matriz;
    private int dim = 3;
    protected int posVazia;
    protected int numMovimento;
    protected Tabuleiro antecessor;
    protected Tabuleiro alvo;
    protected boolean alvoModificado;
    
    public Tabuleiro(){
        
        this.matriz = new int[this.dim][this.dim];
        
        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < dim; j++) {

                this.matriz[i][j] = i * dim + j + 1;
            }
        }
        
        this.posVazia = 8;
        this.matriz[dim - 1][dim - 1] = -1;
        this.antecessor = null;
        this.alvo = null;
        this.alvoModificado = false;
    }

    public Tabuleiro getAlvo() {
        return alvo;
    }

    public void setAlvo(Tabuleiro alvo) {
        this.alvo = alvo;
    }

    public boolean isAlvoModificado() {
        return alvoModificado;
    }

    public void setAlvoModificado(boolean alvoModificado) {
        this.alvoModificado = alvoModificado;
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

    public int getNumMovimento() {
        return numMovimento;
    }

    public void setNumMovimento(int numMovimento) {
        this.numMovimento = numMovimento;
    }
    
    

    @Override
    public int compareTo(Tabuleiro o) {
        
        if (Jogo.calcularPeso(this) + this.numMovimento < Jogo.calcularPeso(o) + o.numMovimento) return -1; 
        else if (Jogo.calcularPeso(this) + this.numMovimento < Jogo.calcularPeso(o) + o.numMovimento) return 1;
        return 0;
    }

    @Override
    protected Tabuleiro clone() {
        
        Tabuleiro t = new Tabuleiro();
        t.dim = this.dim;
        t.posVazia = this.posVazia;
        t.alvo = this.alvo;
        t.alvoModificado = this.alvoModificado;
        
        for(int i = 0; i < dim; i++){
            
            for(int j = 0; j < dim; j++){
                t.matriz[i][j] = this.matriz[i][j];
            }
        }
        return t;
    }

    @Override
    public String toString() {
    
        String s = "";
        
        for(int i = 0; i < dim; i++){
            
            for(int j = 0; j < dim; j++){
                
                
                if (this.matriz[i][j] == -1) {

                    s += "  ";

                } else {
                    s += this.matriz[i][j] + " ";
                }
            }
            
            s += "\n";
        }
        
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        
        Tabuleiro o = (Tabuleiro) obj;
        
        for(int i = 0; i < this.dim; i++){
            
            for(int j = 0; j < this.dim; j++){
                
                if (this.matriz[i][j] != o.matriz[i][j]) return false;
            }
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        
        int soma = 0;
        int fator = 1;
        for(int i = 0; i < this.dim; i++){
            
            for(int j = 0; j < this.dim; j++){
                
                if(this.matriz[i][j] == -1) continue;
                soma += fator*this.matriz[i][j];
                fator *= 10;
            }
        }
        
        return soma;
    }

    public Tabuleiro getAntecessor() {
        return antecessor;
    }

    public void setAntecessor(Tabuleiro antecessor) {
        this.antecessor = antecessor;
    }
    
    
    
    
    
}
