package br.pucrs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GreedyAlgorithms {
    @NotNull
    public static List<Integer> troco(List<Integer> moedas, int valor) {
        List<Integer> troco = new ArrayList<>();
        int i = 0; // índice da moeda atual
        int iteracoes = 0; // contador de iterações
        while (valor > 0 && i < moedas.size()) {
            iteracoes++;
            int moeda = moedas.get(i);
            if (valor >= moeda) {
                troco.add(moeda);
                valor -= moeda;
            } else {
                i++;
            }
        }
        System.out.println("Iterações: " + iteracoes);
        return troco;
    }

    @NotNull
    public static ArrayList<Integer> sdmGuloso(int[] s, int[] f, int number) {
        int f0 = Integer.MIN_VALUE; // inicializa o primeiro término com um valor muito pequeno
        ArrayList<Integer> X = new ArrayList<Integer>(); // inicializa a SDM como uma lista vazia

        for (int k = 0; k < number; k++) { // itera sobre todos os intervalos
            if (s[k] >= f0) { // verifica se o início do intervalo atual é maior ou igual ao término do anterior
                X.add(k); // adiciona o intervalo atual à SDM
                f0 = f[k]; // atualiza o término do último intervalo adicionado
            }
        }

        return X; // retorna a SDM
    }

    // Método principal para resolver o problema das number-rainhas usando backtracking
    @Nullable
    public static int[][] solveNQueens(int number) {
        int[][] board = new int[number][number];
        if (!solveNQueensUtil(board, 0)) {
            // Se não existe solução, retorna null
            return null;
        }
        return board;
    }

    // Método auxiliar para resolver o problema das number-rainhas usando backtracking
    private static boolean solveNQueensUtil(@NotNull int[][] board, int col) {
        // Se todas as colunas forem preenchidas, retorna true
        if (col >= board.length) {
            return true;
        }

        // Testa todas as linhas na coluna atual
        for (int row = 0; row < board.length; row++) {
            if (isSafe(board, row, col)) {
                // Se a posição for segura, coloca uma rainha nesta posição
                board[row][col] = 1;

                // Recursivamente testa a próxima coluna
                if (solveNQueensUtil(board, col + 1)) {
                    return true;
                }

                // Se colocar uma rainha na posição (row, col) não levar a uma solução, remove a rainha
                board[row][col] = 0;
            }
        }

        // Se não houver nenhuma posição segura na coluna atual, retorna false
        return false;
    }

    // Verifica se é seguro colocar uma rainha na posição (row, col) do tabuleiro
    private static boolean isSafe(int[][] board, int row, int col) {
        int i, j;

        // Verifica a linha nesta coluna
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Verifica a diagonal superior nesta coluna
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Verifica a diagonal inferior nesta coluna
        for (i = row, j = col; j >= 0 && i < board.length; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Se a posição for segura, retorna true
        return true;
    }

    // Método para imprimir o tabuleiro com a disposição das rainhas
    public static void printBoard(@NotNull int[][] board) {
        for (int[] ints : board) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    /// Retorna todas as soluções possíveis para o problema das N-rainhas
    @NotNull
    public static List<int[]> solveNAllQueens(int n) {
        List<int[]> result = new ArrayList<>();
        int[] rows = new int[n];
        solveNAllQueensHelper(n, 0, rows, result);
        return result;
    }

    // Função auxiliar para resolver o problema das N-rainhas usando backtracking
    private static void solveNAllQueensHelper(int n, int col, int[] rows, List<int[]> result) {
        if (col == n) { // Todas as colunas foram preenchidas com sucesso
            result.add(rows.clone()); // Adiciona uma cópia da solução atual à lista de resultados
            return;
        }

        for (int row = 0; row < n; row++) { // Tenta colocar uma rainha em cada linha da coluna atual
            if (isValid(rows, row, col)) { // Verifica se a posição (row, col) é segura
                rows[col] = row; // Coloca a rainha na posição (row, col)
                solveNAllQueensHelper(n, col + 1, rows, result); // Continua recursivamente para a próxima coluna
            }
        }
    }

    // Verifica se é seguro colocar uma rainha na posição (row, col) do tabuleiro
    private static boolean isValid(int[] rows, int row, int col) {
        for (int i = 0; i < col; i++) {
            // Verifica se outra rainha está na mesma linha ou diagonal da posição (row, col)
            if (rows[i] == row || Math.abs(rows[i] - row) == Math.abs(i - col)) {
                return false; // Não é uma posição segura
            }
        }
        return true; // É uma posição segura
    }


    public static void main(String[] args) {
        List<Integer> moedas = Arrays.asList(100, 50, 25, 10, 5, 1);
        int valor = 289;
        System.out.println(troco(moedas, valor)); // [100, 100, 25, 25, 25, 10, 1, 1, 1, 1]

        moedas = Arrays.asList(25, 10, 5, 1);
        valor = 63;
        System.out.println(troco(moedas, valor)); // [25, 25, 10, 1, 1, 1]

        int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        int[] f = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        ArrayList<Integer> sdm = sdmGuloso(s, f, s.length);

        System.out.println("SDM: " + sdm);
        System.out.println("Tamanho da SDM: " + sdm.size());

        int n = 4;
        List<int[]> solutions = solveNAllQueens(n);
        System.out.println("Soluções encontradas para N = " + n + ":");
        for (int[] solution : solutions) {
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (solution[col] == row) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}

