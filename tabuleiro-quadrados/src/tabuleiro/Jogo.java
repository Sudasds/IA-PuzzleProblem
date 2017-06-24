package tabuleiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leandroungari
 */
public class Jogo {

    public int contadorSolucao;
    public Tabuleiro tabuleiroInicial;
    public boolean matchFirst;

    private HashMap<Tabuleiro, Integer> listaDeVizitados;
    private ArrayList<Tabuleiro> listaResultado;
    private PriorityQueue<Tabuleiro> lista;

    private PriorityQueue<Tabuleiro> listaOrigem;
    private PriorityQueue<Tabuleiro> listaDestino;
    private HashMap<Tabuleiro, Integer> listaDeVizitadosOrigem;
    private HashMap<Tabuleiro, Integer> listaDeVizitadosDestino;

    /**
     * Inicializa o tabuleiro com a solução do problema.
     */
    public Jogo() {

        this.contadorSolucao = 0;
        this.tabuleiroInicial = new Tabuleiro();

    }

    /**
     * Inicializa o contadorSolução que representa a quantidade de movimentos
     * feitos até conseguir a solução.
     *
     */
    
    
    public void inicializarContadorSolucao() {

        this.contadorSolucao = 0;
    }

    protected static int calcularPeso(Tabuleiro tabuleiro) {
        int soma = 0;
        int size = tabuleiro.getSize();

        if (tabuleiro.alvoModificado) {
            for (int i = 0; i < size; i++) {

                for (int j = 0; j < size; j++) {

                    soma += Math.pow(tabuleiro.getAlvo().matriz[i][j] - tabuleiro.matriz[i][j], 2);
                }
            }
        } else {

            for (int i = 0; i < size; i++) {

                for (int j = 0; j < size; j++) {

                    if (tabuleiro.matriz[i][j] == -1) {
                        continue;
                    }
                    soma += Math.pow((i * size + j + 1) - tabuleiro.matriz[i][j], 2);
                }
            }

        }

        return soma;
    }

    /*protected static int calcularPeso(Tabuleiro tabuleiro) {
        int soma = 0;
        int size = tabuleiro.getSize();

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (tabuleiro.matriz[i][j] == -1) {
                    continue;
                }
                soma += Math.pow(tabuleiro.getAlvo().matriz[i][j] - tabuleiro.matriz[i][j], 2);
            }
        }

        return soma;
    }*/
    /**
     * Verifica se o tabuleiro está solucionado, se o peso é igual a zero.
     *
     * @return Verdadeiro se o peso é igual ao zero, caso contrário, falso.
     */
    public boolean estaResolvido() {

        return (Jogo.estaResolvido(tabuleiroInicial));
    }

    private static boolean estaResolvido(Tabuleiro t) {

        return (Jogo.calcularPeso(t) == 0);
    }

    public void embaralhar() {

        int numPassos = (int) (Math.random() * 20 + 10);
        //System.out.println(numPassos);
        while (numPassos > 0) {

            int[] possibilidades = this.possiveisMovimentos(tabuleiroInicial.getPosVazia());
            int casa = possibilidades[(int) (Math.random() * possibilidades.length)];
            this.troca(casa);
            numPassos--;
        }
    }

    public void embaralhar(int numPassos) {

        while (numPassos > 0) {

            int[] possibilidades = this.possiveisMovimentos(tabuleiroInicial.getPosVazia());
            int casa = possibilidades[(int) (Math.random() * possibilidades.length)];
            this.troca(casa);
            numPassos--;
        }
    }

    private void troca(int posicao) {

        Jogo.troca(tabuleiroInicial, posicao);
    }

    private static void troca(Tabuleiro t, int posicao) {

        t.matriz[t.getPosVazia() / t.getSize()][t.getPosVazia() % t.getSize()] = t.matriz[posicao / t.getSize()][posicao % t.getSize()];
        t.matriz[posicao / t.getSize()][posicao % t.getSize()] = -1;
        t.setPosVazia(posicao);
    }

    /**
     * Algoritmo de solução baseando-se em uma escolha aleatória do próximo
     * elemento.
     */
    public void aleatorio() {

        int posicao;
        this.contadorSolucao = 0;
        this.listaResultado = new ArrayList<>();
        listaResultado.add(tabuleiroInicial);
        
        while (!this.estaResolvido(this.tabuleiroInicial)) {

            int[] lista = this.possiveisMovimentos(this.tabuleiroInicial.getPosVazia());
            posicao = lista[(int) (Math.random() * (lista.length))];
            this.troca(posicao);
            listaResultado.add(tabuleiroInicial);
            this.contadorSolucao++;
        }
        
    }

    public void heuristicaEmUmNivel() {

        listaResultado = new ArrayList<>();
        listaDeVizitados = new HashMap<>();
        lista = new PriorityQueue<>();

        /*Tabuleiro t = distanciaUmNivel(tabuleiroInicial, 0);
        listaResultado.add(0, t);
        
        this.tabuleiroInicial = listaResultado.get(listaResultado.size() - 1);*/
        this.tabuleiroInicial.setNumMovimento(0);
        lista.add(this.tabuleiroInicial);
        this.tabuleiroInicial = distanciaUmNivel();

        //Coloca o caminho certo na lista
        Tabuleiro t = this.tabuleiroInicial;
        while (t != null) {
            listaResultado.add(0, t);
            t = t.getAntecessor();
        }

    }

    private Tabuleiro distanciaUmNivel() {

        Tabuleiro t = null;
        int numMovimento = 0;
        this.contadorSolucao = 0;

        while (!lista.isEmpty()) {

            t = lista.poll();

            //System.out.println(t);
            listaDeVizitados.put(t, numMovimento);

            if (estaResolvido(t)) {
                return t;
            }

            numMovimento++;
            int[] possiveis = possiveisMovimentos(t.getPosVazia());
            for (Integer e : possiveis) {

                Tabuleiro novo = t.clone();
                novo.setNumMovimento(numMovimento);
                novo.setAntecessor(t);
                troca(novo, e);
                if (listaDeVizitados.containsKey(novo)) {
                    continue;
                }
                lista.add(novo);
            }

            
            this.contadorSolucao++;
        }

        return t;
    }

    ////////////////////////////////////
    // HEURISTICA PESSOAL
    // A* COM MENOR DISTANCIA BIDIRECIONAL
    public void heuristicaPessoal() {

        listaOrigem = new PriorityQueue<>();
        listaDestino = new PriorityQueue<>();
        listaDeVizitadosOrigem = new HashMap<>();
        listaDeVizitadosDestino = new HashMap<>();
        listaResultado = new ArrayList<>();

        tabuleiroInicial.alvoModificado = true;

        Tabuleiro resultado = new Tabuleiro();

        listaOrigem.add(this.tabuleiroInicial);
        listaDestino.add(resultado);

        tabuleiroInicial.alvoModificado = true;
        resultado.alvoModificado = true;
        Tabuleiro resposta = distanciaAEstrelaBidirecional();
        tabuleiroInicial.alvoModificado = false;
        resultado.alvoModificado = false;
        
        
        //System.out.println(resposta);

        Tabuleiro t = resposta;
        if (matchFirst) {

            while (t != null) {

                listaResultado.add(0, t);
                t = t.getAntecessor();
            }

            t = resposta.getAlvo();
            while (t.getAntecessor() != null) {
                listaResultado.add(t.getAntecessor());
                t = t.getAntecessor();
            }
        }
        else{
            
            while (t != null) {

                listaResultado.add(t);
                t = t.getAntecessor();
            }
            
            t = resposta.getAlvo();
            while (t.getAntecessor() != null) {
                listaResultado.add(0,t.getAntecessor());
                t = t.getAntecessor();
            }
        }

        
        tabuleiroInicial = listaResultado.get(listaResultado.size()-1);
        tabuleiroInicial.alvoModificado = false;
        
        tabuleiroInicial.numMovimento = resposta.numMovimento;
        /*this.tabuleiroInicial*/
    }

    private Tabuleiro distanciaAEstrelaBidirecional() {

        Tabuleiro tOrigem;
        Tabuleiro tDestino;
        int[] possiveis;
        int numOrigem = 0, numDestino = 0;
        int count = -1;

        tOrigem = listaOrigem.poll();
        listaDeVizitadosOrigem.put(tOrigem, numOrigem);

        tDestino = listaDestino.poll();
        listaDeVizitadosDestino.put(tDestino, numDestino);

        tOrigem.setAlvo(tDestino);
        tDestino.setAlvo(tOrigem);
        
        this.contadorSolucao = 0;

        while (tOrigem != null && tDestino != null) {

            /*tOrigem.setAlvo(tDestino);
            tDestino.setAlvo(tOrigem);*/
            if (estaResolvido(tOrigem)) {

                matchFirst = true;
                //System.out.println(tOrigem);
                //System.out.println(tOrigem.getAlvo());
                return tOrigem;
            } else if (estaResolvido(tDestino)) {

                matchFirst = false;
                //System.out.println(tDestino);
                //System.out.println(tDestino.getAlvo());
                return tDestino;
            }

            count++;
            if (count % 2 == 0) {
                possiveis = possiveisMovimentos(tDestino.posVazia);
                numDestino++;
                for (Integer e : possiveis) {

                    Tabuleiro novo = tDestino.clone();
                    novo.setNumMovimento(numDestino);
                    novo.setAntecessor(tDestino);
                    troca(novo, e);
                    if (listaDeVizitadosDestino.containsKey(novo)) {
                        continue;
                    }
                    listaDestino.add(novo);
                }

                tDestino = listaDestino.poll();
                listaDeVizitadosDestino.put(tDestino, numDestino);
            } else {
                possiveis = possiveisMovimentos(tOrigem.posVazia);
                numOrigem++;
                for (Integer e : possiveis) {

                    Tabuleiro novo = tOrigem.clone();
                    novo.setNumMovimento(numOrigem);
                    novo.setAntecessor(tOrigem);
                    troca(novo, e);
                    if (listaDeVizitadosOrigem.containsKey(novo)) {
                        continue;
                    }
                    listaOrigem.add(novo);
                }

                tOrigem = listaOrigem.poll();
                listaDeVizitadosOrigem.put(tOrigem, numOrigem);

            }
            
            this.contadorSolucao++;

        }

        return tOrigem;
    }

    /*private Tabuleiro distanciaUmNivel(Tabuleiro t, int numMovimentos) {

        System.gc();
        System.out.println(t);
        listaDeVizitados.add(t);

        if (Jogo.estaResolvido(t)) {
            return t;
        }

        //Calculando os possíveis estados
         int[] possiveis = this.possiveisMovimentos(t.getPosVazia());
         for (Integer i : possiveis) {

            Tabuleiro n = t.clone();
            Jogo.troca(n, i);
            n.setNumMovimento(numMovimentos);
            lista.add(n);
        }
        /**
         * Corrigir, tem que escolher o menor custo, revisar
         
        Tabuleiro escolha = null;

        while (escolha == null) {
            
            escolha = lista.poll();
            if (escolha != null && !listaDeVizitados.contains(escolha)) {
                
                Tabuleiro result = distanciaUmNivel(escolha, numMovimentos + 1);
                if (result != null) {

                    listaResultado.add(0,result);
                    return t;
                }
            }
            else escolha = null;

        }

        return null;
    }*/
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
        for (int i = 0; i < this.tabuleiroInicial.getSize(); i++) {

            for (int j = 0; j < this.tabuleiroInicial.getSize(); j++) {

                if (this.tabuleiroInicial.matriz[i][j] == -1) {

                    s += "  ";

                } else {
                    s += this.tabuleiroInicial.matriz[i][j] + " ";
                }
            }

            s += "\n";
        }

        return s;
    }

    private int[][] copiarDados() {

        int size = this.tabuleiroInicial.getSize();
        int[][] dados = new int[size][size];

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                dados[i][j] = this.tabuleiroInicial.matriz[i][j];
            }

        }

        return dados;
    }

    public ArrayList<Tabuleiro> getListaResultado() {
        return listaResultado;
    }

}
