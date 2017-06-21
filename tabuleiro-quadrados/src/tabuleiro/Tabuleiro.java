package tabuleiro;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leandroungari
 */
public class Tabuleiro {

    private int[][] data;
    private static final int SIZE = 3;
    private int posicaoVazia;

    private int contadorSolucao;

    private static final int DIREITA = 100;
    private static final int ESQUERDA = 101;
    private static final int CIMA = 102;
    private static final int BAIXO = 103;

    /**
     * Inicializa o tabuleiro com a solução do problema.
     */
    public Tabuleiro() {

        this.data = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                this.data[i][j] = i * SIZE + j + 1;
            }
        }

        this.posicaoVazia = 8;
        this.data[SIZE - 1][SIZE - 1] = -1;
        
        this.contadorSolucao = 0;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public void inicializarContadorSolucao(){
        
        this.contadorSolucao = 0;
    }
    
    /**
     * Calcula o peso da configuração atual dos quadrados no tabuleiro.
     *
     * @return Valor do peso.
     */
    public int calcularPeso() {

        int soma = 0;

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                if (this.data[i][j] == -1) {
                    continue;
                }
                soma += Math.pow((i * SIZE + j + 1) - this.data[i][j], 2);
            }
        }

        return soma;
    }

    private int calcularPeso(int[][] dados) {
        int soma = 0;

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                if (dados[i][j] == -1) {
                    continue;
                }
                soma += Math.pow((i * SIZE + j + 1) - dados[i][j], 2);
            }
        }

        return soma;
    }

    /**
     * Verifica se o tabuleiro está solucionado, se o peso é igual a zero.
     *
     * @return Verdadeiro se o peso é igual ao zero, caso contrário, falso.
     */
    public boolean estaResolvido() {

        return(this.calcularPeso() == 0);
    }

    public void embaralhar() {

        ArrayList<Integer> fila = new ArrayList<>();

        for (int i = 1; i < SIZE * SIZE; i++) {
            fila.add(i);
        }

        fila.add(-1);

        int count = 0;
        while (!fila.isEmpty()) {

            this.data[count / SIZE][count % SIZE] = fila.remove((int) (Math.random() * (fila.size())));
            if (this.data[count / SIZE][count % SIZE] == -1) {
                this.posicaoVazia = count;
            }
            count++;
        }
    }

    /**
     * Algoritmo de solução baseando-se em uma escolha aleatória do próximo
     * elemento.
     */
    public void aleatorio() {

        int posicao;
        this.contadorSolucao = 0;

        while (!this.estaResolvido()) {

            int[] lista = this.possiveisMovimentos(this.posicaoVazia);
            posicao = lista[(int) (Math.random() * (lista.length))];
            this.data[this.posicaoVazia / SIZE][this.posicaoVazia % SIZE] = this.data[posicao / SIZE][posicao % SIZE];
            this.data[posicao / SIZE][posicao % SIZE] = -1;
            this.posicaoVazia = posicao;

            this.contadorSolucao++;
        }
    }
    
    public void heuristicaEmUmNivel(){
        
        int lista[] = this.possiveisMovimentos(this.posicaoVazia);
        
        int posicaoNova;
        int dados[][];
        int pesos[] = new int[lista.length];
        int posicaoAntiga[] = new int[lista.length];
        
        for (int i = 0; i < lista.length; i++) {
                
                dados = this.copiarDados();
                posicaoAntiga[i] = this.posicaoVazia;
                posicaoNova = lista[i];
                dados[posicaoAntiga[i] / SIZE][posicaoAntiga[i] % SIZE] = dados[posicaoNova / SIZE][posicaoNova % SIZE];
                dados[posicaoNova / SIZE][posicaoNova % SIZE] = -1;
                posicaoAntiga[i] = posicaoNova;
                
                pesos[i] = this.calcularPeso(dados);
                
        }

        Arrays.sort(pesos);
        
        heuristicaEmUmNivel();
        
    }
    
    public void distanciaEmUmNivel() {

        this.contadorSolucao = 0;

        int posicaoNova; 
        int[] posicaoAntiga;
        int[][] dados;
        int[] movimentoAnterior;
        
        int[] lista;
        int[] pesos;
        int[] posicoes;
        
        int verificaPos, verificaNum, numeroAtual;
        verificaPos = -1;
        verificaNum = -2;
        
        while (!this.estaResolvido()) {

            lista = this.possiveisMovimentos(this.posicaoVazia);
            pesos = new int[lista.length];
            posicoes = new int[lista.length];
            posicaoAntiga = new int[lista.length];
            
            for (int i = 0; i < pesos.length; i++) {
                
                dados = this.copiarDados();
                posicaoAntiga[i] = this.posicaoVazia;
                posicaoNova = lista[i];
                numeroAtual = dados[posicaoAntiga[i] / SIZE][posicaoAntiga[i] % SIZE] = dados[posicaoNova / SIZE][posicaoNova % SIZE];
                dados[posicaoNova / SIZE][posicaoNova % SIZE] = -1;
                posicaoAntiga[i] = posicaoNova;
                
                posicoes[i] = posicaoNova;
                //Verifica ciclo
                if( (this.posicaoVazia == verificaPos) && (numeroAtual == verificaNum) ){
                    pesos[i] = Integer.MAX_VALUE;
                }
                else pesos[i] = this.calcularPeso(dados);
                
            }
            
            int menorPeso = Integer.MAX_VALUE - 1;
            int pos = 0;
            for(int i = 0; i < pesos.length; i++){
                if (pesos[i] < menorPeso) {
                    
                    menorPeso = pesos[i];
                    pos = posicoes[i];
                }
            }
            
            //Demarca verificações
            verificaPos = pos;
            verificaNum = this.data[pos / SIZE][pos % SIZE];
            
            //Aplica o movimento definitivo
            this.data[this.posicaoVazia / SIZE][this.posicaoVazia % SIZE] = this.data[pos / SIZE][pos % SIZE];
            this.data[pos / SIZE][pos % SIZE] = -1;
            this.posicaoVazia = pos;
            
            System.out.println(this);
            
            this.contadorSolucao++;
            
        }
        
    }

    /**
     * Posições movimentos em um tabuleiro 3x3
     *
     * @param posicao Posição central
     * @return Array com inteiros que indicam as posições que podem ser
     * deslocadas.
     */
    private int[] possiveisMovimentos(int posicao) {

        switch (posicao) {

            case 0:

                return new int[]{1, 3};

            case 1:

                return new int[]{0, 2, 4};

            case 2:

                return new int[]{1, 5};

            case 3:

                return new int[]{0, 4, 6};

            case 4:

                return new int[]{1, 3, 5, 7};

            case 5:

                return new int[]{2, 4, 8};

            case 6:

                return new int[]{3, 7};

            case 7:

                return new int[]{4, 6, 8};

            default:

                return new int[]{5, 7};
        }
        
    }

    @Override
    public String toString() {

        String s = "";
        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                if (this.data[i][j] == -1) {

                    s += "  ";

                } else {
                    s += this.data[i][j] + " ";
                }
            }

            s += "\n";
        }

        return s;
    }

    private int[][] copiarDados() {

        int[][] dados = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                dados[i][j] = this.data[i][j];
            }

        }

        return dados;
    }
}
