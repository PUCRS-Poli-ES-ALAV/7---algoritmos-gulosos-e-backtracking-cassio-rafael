package br.pucrs;

import org.jetbrains.annotations.NotNull;

public class DynamicProgramming {
    // Versão recursiva
    public static int fiboRec(int n) {
        if (n <= 1) { // Se n for 0 ou 1, retorna n
            return n;
        }
        // Chama a função recursivamente para n-1 e n-2, somando o resultado
        return fiboRec(n - 1) + fiboRec(n - 2);
    }

    // Versão iterativa
    public static int fibo(int n) {
        if (n == 0) { // Se n for 0, retorna 0
            return 0;
        }
        if (n == 1) { // Se n for 1, retorna 1
            return 1;
        }
        // Cria um array para armazenar os valores já calculados
        int[] f = new int[n + 1];
        f[0] = 0; // Define o valor de f[0] como 0
        f[1] = 1; // Define o valor de f[1] como 1
        // Itera de 2 até n, calculando os valores de f[i] como a soma dos dois valores anteriores
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        // Retorna o valor de f[n]
        return f[n];
    }

    // Versão memoizada
    public static int memoizedFibo(int n) {
        // Cria um array para armazenar os valores já calculados
        int[] f = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            f[i] = -1; // Define todos os valores como -1, indicando que ainda não foram calculados
        }
        return lookupFibo(f, n);
    }

    // Função auxiliar para realizar a busca do valor já calculado
    private static int lookupFibo(@NotNull int[] f, int n) {
        if (f[n] >= 0) { // Se o valor já foi calculado, retorna-o
            return f[n];
        }
        if (n <= 1) { // Se n for 0 ou 1, retorna n
            f[n] = n;
        } else { // Senão, calcula o valor usando a recursão e armazena no array
            f[n] = lookupFibo(f, n - 1) + lookupFibo(f, n - 2);
        }
        // Retorna o valor calculado
        return f[n];
    }

    // Função de teste
    public static void test(int n) {
        // Imprime na tela os resultados das três versões para o valor n
        System.out.println("FiboRec(" + n + "): " + fiboRec(n));
        System.out.println("Fibo(" + n + "): " + fibo(n));
        System.out.println("MemoizedFibo(" + n + "): " + memoizedFibo(n));
    }

    public static int mochilaForcaBruta(int capacidade, int[] pesos, int[] valores, int n) {
        // Caso base: se a capacidade ou o número de objetos for 0, retorna 0
        if (capacidade == 0 || n == 0) {
            return 0;
        }

        // Se o peso do último objeto for maior que a capacidade, não é possível adicioná-lo
        if (pesos[n - 1] > capacidade) {
            return mochilaForcaBruta(capacidade, pesos, valores, n - 1);
        }

        // Testa duas possibilidades: incluir ou não incluir o último objeto na mochila
        int valor1 = valores[n - 1] + mochilaForcaBruta(capacidade - pesos[n - 1], pesos, valores, n - 1);
        int valor2 = mochilaForcaBruta(capacidade, pesos, valores, n - 1);

        // Retorna o valor máximo obtido
        return Math.max(valor1, valor2);
    }

    public static int mochilaPD(int capacidade, int[] pesos, int[] valores, int n) {
        int[][] maxTab = new int[n + 1][capacidade + 1];

        // Inicializa a primeira linha e a primeira coluna da tabela com 0
        // A primeira linha representa o caso base em que não há itens
        // A primeira coluna representa o caso base em que a mochila tem capacidade 0
        for (int i = 0; i <= n; i++) {
            maxTab[i][0] = 0;
        }
        for (int j = 0; j <= capacidade; j++) {
            maxTab[0][j] = 0;
        }

        // Preenche a tabela com os valores máximos possíveis
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacidade; j++) {
                if (pesos[i - 1] <= j) {
                    // Se o ‘item’ i cabe na mochila de capacidade j, escolhe-se o melhor valor
                    // entre incluir o ‘item’ i ou não incluir o ‘item’ i
                    maxTab[i][j] = Math.max(maxTab[i - 1][j], valores[i - 1] + maxTab[i - 1][j - pesos[i - 1]]);
                } else {
                    // Se o ‘item’ i não cabe na mochila de capacidade j, não o inclui
                    maxTab[i][j] = maxTab[i - 1][j];
                }
            }
        }

        // Retorna o valor máximo possível para a capacidade da mochila
        return maxTab[n][capacidade];
    }

    public static int distanciaEdicao(@NotNull String s1, @NotNull String s2) {
        int m = s1.length();
        int n = s2.length();

        // Cria uma tabela para armazenar os custos
        int[][] tabela = new int[m + 1][n + 1];

        // Inicializa a primeira coluna da tabela com os custos de inserção
        for (int i = 0; i <= m; i++) {
            tabela[i][0] = i;
        }

        // Inicializa a primeira linha da tabela com os custos de deleção
        for (int j = 0; j <= n; j++) {
            tabela[0][j] = j;
        }

        // Preenche a tabela com os custos mínimos
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Se os caracteres da posição i em s1 e da posição j em s2 são iguais,
                    // o custo é o mesmo da posição anterior
                    tabela[i][j] = tabela[i - 1][j - 1];
                } else {
                    // Caso contrário, o custo é o mínimo entre inserção, deleção e substituição
                    int custoInsercao = tabela[i][j - 1] + 1;
                    int custoDelecao = tabela[i - 1][j] + 1;
                    int custoSubstituicao = tabela[i - 1][j - 1] + 1;
                    tabela[i][j] = Math.min(Math.min(custoInsercao, custoDelecao), custoSubstituicao);
                }
            }
        }

        // Retorna o valor mínimo na última posição da tabela
        return tabela[m][n];
    }

    public static int distEdProgDina(@NotNull String A, @NotNull String B) {

        int m = A.length(); // Comprimento da string A
        int n = B.length(); // Comprimento da string B

        // Declaração da matriz de memoização para armazenar as subestrings
        int[][] matriz = new int[m+1][n+1];

        // Inicialização da primeira linha e primeira coluna da matriz
        for (int i = 0; i <= m; i++) {
            matriz[i][0] = i; // Custo das i primeiras operações sendo todas inserções
        }
        for (int j = 0; j <= n; j++) {
            matriz[0][j] = j; // Custo das j primeiras operações sendo todas remoções
        }

        // Preenchimento da matriz com os custos mínimos de edição
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int custoExtra = (A.charAt(i-1) == B.charAt(j-1)) ? 0 : 1;
                matriz[i][j] = Math.min(matriz[i-1][j] + 1, Math.min(matriz[i][j-1] + 1, matriz[i-1][j-1] + custoExtra));
            }
        }

        // Retorna o valor mínimo na última posição da tabela
        return matriz[m][n];
    }

    public static void main(String[] args) {
        // Testa as três versões para os valores dados
        test(4);
        test(8);
        test(16);
        test(32);

        // Testa as duas últimas versões para valores maiores
        System.out.println("----------------------");
        System.out.println("Testing large inputs:");
        test(128);
        test(1000);
        test(10000);

        //Mochila
        int capacidade = 10;
        int[] pesos = {2, 3, 4, 5};
        int[] valores = {3, 4, 5, 6};
        int n = 4;

        int valorMaximoA = mochilaForcaBruta(capacidade, pesos, valores, n);
        System.out.println("Valor máximo: " + valorMaximoA);
        int valorMaximoB = mochilaPD(capacidade, pesos, valores, n);
        System.out.println("Valor máximo: " + valorMaximoB);

        String s1 = "Casablanca";
        String s2 = "Portentoso";
        int distanciaA = distanciaEdicao(s1, s2);
        int distanciaB = distEdProgDina(s1, s2);
        System.out.println("Distância de edição entre \"" + s1 + "\" e \"" + s2 + "\": " + distanciaA);
        System.out.println("Distância de edição entre \"" + s1 + "\" e \"" + s2 + "\": " + distanciaB);

        s1 = "Maven, a Yiddish word meaning accumulator of knowledge, began as an attempt to " +
                "simplify the build processes in the Jakarta Turbine project. There were several" +
                " projects, each with their own Ant build files, that were all slightly different." +
                "JARs were checked into CVS. We wanted a standard way to build the projects, a clear " +
                "definition of what the project consisted of, an easy way to publish project information" +
                "and a way to share JARs across several projects. The result is a tool that can now be" +
                "used for building and managing any Java-based project. We hope that we have created " +
                "something that will make the day-to-day work of Java developers easier and generally help " +
                "with the comprehension of any Java-based project.";
        s2 = "This post is not about deep learning. But it could be might as well. This is the power of " +
                "kernels. They are universally applicable in any machine learning algorithm. Why you might" +
                "ask? I am going to try to answer this question in this article." +
                "Go to the profile of Marin Vlastelica Pogančić" +
                "Marin Vlastelica Pogančić Jun";
        distanciaA = distanciaEdicao(s1, s2);
        distanciaB = distEdProgDina(s1,s2);
        System.out.println("Distância de edição entre \"" + s1.substring(0, 30) + "...\" e \"" + s2.substring(0, 30) + "...\": " + distanciaA);
        System.out.println("Distância de edição entre \"" + s1.substring(0, 30) + "...\" e \"" + s2.substring(0, 30) + "...\": " + distanciaB);
    }
}
