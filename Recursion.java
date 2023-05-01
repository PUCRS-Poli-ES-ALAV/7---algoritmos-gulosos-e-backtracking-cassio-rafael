package br.pucrs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Recursion {

    // Método recursivo para determinar se uma string é um palíndromo
    public static boolean isPal(@NotNull String s) {
        // Caso base: se a ‘string’ tem tamanho 0 ou 1, então é um palíndromo
        if (s.length() <= 1) {
            return true;
        }
        // Caso recursivo: se o primeiro e o último caractere da string são diferentes,
        // então não é um palíndromo. Caso contrário, chamamos recursivamente a função
        // com a ‘string’ sem o primeiro e o último caracteres.
        else if (s.charAt(0) != s.charAt(s.length() - 1)) {
            return false;
        } else {
            return isPal(s.substring(1, s.length() - 1));
        }
    }

    // Método recursivo para converter um número inteiro para sua representação binária
    @NotNull
    public static String convBase2(int n) {
        // Caso base: se o número é menor que 2, então a sua representação binária é ele mesmo.
        if (n < 2) {
            return Integer.toString(n);
        }
        // Caso recursivo: para converter um número para binário, basta pegar o resto da
        // divisão por 2 e concatenar com a representação binária do quociente da divisão por 2.
        else {
            return convBase2(n / 2) + Integer.toString(n % 2);
        }
    }

    // Método recursivo para calcular o somatório dos elementos de um ArrayList de inteiros
    public static int sumArrayList(@NotNull ArrayList<Integer> arr) {
        // Caso base: se o ArrayList é vazio, o somatório é zero.
        if (arr.size() == 0) {
            return 0;
        }
        // Caso recursivo: o somatório é o primeiro elemento do ArrayList mais o somatório
        // dos demais elementos.
        else {
            return arr.get(0) + sumArrayList(new ArrayList<Integer>(arr.subList(1, arr.size())));
        }
    }

    // Método recursivo para encontrar o maior elemento de um ArrayList
    public static int findBiggest(@NotNull ArrayList<Integer> arr) {
        // Caso base: se o ArrayList tem tamanho 1, então o maior elemento é o próprio elemento.
        if (arr.size() == 1) {
            return arr.get(0);
        }
        // Caso recursivo: o maior elemento é o máximo entre o primeiro elemento do ArrayList
        // e o maior elemento dos demais elementos.
        else {
            int subMax = findBiggest(new ArrayList<Integer>(arr.subList(1, arr.size())));
            if (arr.get(0) > subMax) {
                return arr.get(0);
            } else {
                return subMax;
            }
        }
    }

    // Método recursivo para determinar se uma string está contida em outra
    public static boolean findSubStr(@NotNull String str, @NotNull String match) {
        // Caso base: se a ‘string’ é menor que o padrão, então não há ocorrência.
        if (str.length() < match.length()) {
            return false;
        }
        // Caso recursivo: se a ‘string’ começa com o padrão, então há ocorrência.
        // Caso contrário, chamamos recursivamente a função com a string sem o primeiro caractere.
        else if (str.startsWith(match)) {
            return true;
        } else {
            return findSubStr(str.substring(1), match);
        }
    }

    // Método recursivo para determinar o número de dígitos de um inteiro
    public static int nroDigit(int n) {
        // Caso base: se o número tem um único dígito, então o número de dígitos é 1.
        if (n < 10) {
            return 1;
        }
        // Caso recursivo: o número de dígitos é 1 mais o número de dígitos do quociente da
        // divisão por 10 (isto é, removendo o último dígito).
        else {
            return 1 + nroDigit(n / 10);
        }
    }

    // Método recursivo para gerar todas as permutações de uma string
    @NotNull
    public static ArrayList<String> permutations(@NotNull String s) {
        ArrayList<String> result = new ArrayList<>();
        // Caso base: se a ‘string’ tem tamanho 1, então ela é a única permutação possível.
        if (s.length() == 1) {
            result.add(s);
        }
        // Caso recursivo: para gerar as permutações de uma ‘string’, escolhemos um caractere
        // qualquer e geramos as permutações dos demais caracteres. Em seguida, adicionamos
        // o caractere escolhido em todas as posições das permutações geradas.
        else {
            for (int i = 0; i < s.length(); i++) {
                String c = s.substring(i, i + 1);
                ArrayList<String> subResult = permutations(s.substring(0, i) + s.substring(i + 1));
                for (String p : subResult) {
                    result.add(c + p);
                }
            }
        }
        return result;
    }

    // Exemplo de uso dos métodos
    public static void main(String[] args) {
        // Testando isPal
        System.out.println(isPal("abba")); // true
        System.out.println(isPal("abcba")); // true
        System.out.println(isPal("hello")); // false

        // Testando convBase2
        System.out.println(convBase2(10)); // 1010
        System.out.println(convBase2(42)); // 101010

        // Testando sumArrayList
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        System.out.println(sumArrayList(arr)); // 6

        // Testando findBiggest
        arr.clear();
        arr.add(1);
        arr.add(5);
        arr.add(3);
        arr.add(7);
        System.out.println(findBiggest(arr)); // 7

        // Testando findSubStr
        System.out.println(findSubStr("hello world", "wor")); // true
        System.out.println(findSubStr("hello world", "cat")); // false

        // Testando nroDigit
        System.out.println(nroDigit(12345)); // 5
        System.out.println(nroDigit(0)); // 1

        // Testando permutations
        ArrayList<String> perms = permutations("abc");
        for (String p : perms) {
            System.out.println(p);
        }
    }
}


