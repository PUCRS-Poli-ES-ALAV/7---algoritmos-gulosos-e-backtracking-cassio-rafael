package br.pucrs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Algorithms {
    public static void mergeSort(long[] arr, int l, int r) {
        if (l < r) {
            // Encontra o meio do array para dividir em duas partes
            int m = (l + r) / 2;
            // Chama recursivamente o merge sort para ordenar a metade esquerda do array
            mergeSort(arr, l, m);
            // Chama recursivamente o merge sort para ordenar a metade direita do array
            mergeSort(arr, m + 1, r);
            // Realiza o merge das duas metades ordenadas
            merge(arr, l, m, r);
        }
    }

    public static void merge(long[] arr, int l, int m, int r) {
        // Tamanhos dos subarrays a serem mesclados
        int n1 = m - l + 1;
        int n2 = r - m;

        // Criação dos subarrays a serem mesclados
        long[] L = new long[n1];
        long[] R = new long[n2];

        // Copia os elementos do array original para os subarrays a serem mesclados
        System.arraycopy(arr, l, L, 0, n1);
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        // Realiza a mescla dos subarrays em ordem crescente
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copia os elementos restantes do subarray da esquerda, se houver
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copia os elementos restantes do subarray da direita, se houver
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    @Contract(pure = true)
    public static long maxVal1(@NotNull long[] A) {
        // Inicializa a variável max com o primeiro elemento do array
        long max = A[0];
        // Percorre o array procurando o maior elemento
        for (int i = 1; i < A.length; i++) {
            if (A[i] > max)
                max = A[i];
        }
        // Retorna o maior elemento encontrado
        return max;
    }

    public static long maxVal2(long[] A, int init, int end) {
        // Se o subarray tiver no máximo 2 elementos, retorna o maior deles
        if (end - init <= 1)
            return Math.max(A[init], A[end]);
        else {
            // Divide o subarray em duas partes
            int m = (init + end) / 2;
            // Chama recursivamente a função para encontrar o maior elemento da metade esquerda
            long v1 = maxVal2(A, init, m);
            // Chama recursivamente a função para encontrar o maior elemento da metade direita
            long v2 = maxVal2(A, m + 1, end);
            // Retorna o maior elemento entre os maiores elementos das duas metades
            return Math.max(v1, v2);
        }
    }

    public static long multiply(long x, long y, int n) {
        // Se ambos os números tiverem apenas um bit, retorna o resultado da multiplicação simples
        if (n == 1)
            return x * y;
        else {
            // Divide o tamanho dos números pela metade
            int m = n / 2;
            // Separa os bits mais significativos e menos significativos dos números
            long a = x >> m;
            long b = x & ((1L << m) - 1);
            long c = y >> m;
            long d = y & ((1L << m) - 1);
            // Chama recursivamente a função para calcular as submultiplicações
            long e = multiply(a, c, m);
            long f = multiply(b, d, m);
            long g = multiply(b, c, m);
            long h = multiply(a, d, m);
            // Calcula o resultado final a partir das submultiplicações
            return (long) (Math.pow(2, 2 * m) * e + Math.pow(2, m) * (g + h) + f);
        }
    }

    public static void main(String[] args) {
        Random r = new Random();
        long[] arr32 = r.longs(32).toArray();
        long[] arr2048 = r.longs(2048).toArray();
        long[] arr1048576 = r.longs(1048576).toArray();

        long start, end, elapsed;

        // Teste do Merge Sort
        start = System.nanoTime();
        mergeSort(arr32, 0, arr32.length - 1);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("Merge Sort (32): " + elapsed + " nanoseconds");

        start = System.nanoTime();
        mergeSort(arr2048, 0, arr2048.length - 1);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("Merge Sort (2048): " + elapsed + " nanoseconds");

        start = System.nanoTime();
        mergeSort(arr1048576, 0, arr1048576.length - 1);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("Merge Sort (1048576): " + elapsed + " nanoseconds");

        // Teste do maxVal1
        long[] arr = { 1, 5, 2, 9, 7, 3 };
        start = System.nanoTime();
        long max1 = maxVal1(arr);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("maxVal1: " + max1 + ", " + elapsed + " nanoseconds");

        // Teste do maxVal2
        start = System.nanoTime();
        long max2 = maxVal2(arr, 0, arr.length - 1);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("maxVal2: " + max2 + ", " + elapsed + " nanoseconds");

        // Teste do multiply
        start = System.nanoTime();
        long m1 = multiply(13L, 29L, 4);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("multiply (4 bits): " + m1 + ", " + elapsed + " nanoseconds");

        start = System.nanoTime();
        long m2 = multiply(65535L, 65535L, 16);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("multiply (16 bits): " + m2 + ", " + elapsed + " nanoseconds");

        start = System.nanoTime();
        long m3 = multiply(9223372036854775807L, 9223372036854775807L, 64);
        end = System.nanoTime();
        elapsed = end - start;
        System.out.println("multiply (64 bits): " + m3 + ", " + elapsed + " nanoseconds");
    }


}


