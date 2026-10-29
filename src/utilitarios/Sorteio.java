package utilitarios;

import java.util.Random;

/**
 * Centraliza os sorteios do jogo.
 *
 * <p>O restante do código pede números por aqui em vez de criar vários
 * objetos {@link Random}. Assim, se o jogador informar uma semente na
 * linha de comando, a mesma partida pode ser repetida.</p>
 */
public final class Sorteio {

    /** Gerador usado por todos os sorteios da partida. */
    private static Random aleatorio = new Random();

    private Sorteio() {
        // Classe de utilidade: não faz sentido criar objetos dela.
    }

    /**
     * Troca o gerador por um com semente fixa.
     * Útil para testar a mesma sequência de inimigos e críticos.
     */
    public static void definirSemente(long semente) {
        aleatorio = new Random(semente);
    }

    /** Devolve um inteiro entre 0 (inclusive) e limiteExclusivo (exclusive). */
    public static int numero(int limiteExclusivo) {
        return aleatorio.nextInt(limiteExclusivo);
    }

    /** Devolve um inteiro no intervalo fechado [minimo, maximo]. */
    public static int entre(int minimo, int maximo) {
        if (maximo < minimo) {
            throw new IllegalArgumentException("Intervalo de sorteio inválido.");
        }
        return minimo + aleatorio.nextInt(maximo - minimo + 1);
    }

    /**
     * Testa uma chance percentual.
     * Exemplo: chance(25) é verdadeiro em cerca de 25% das chamadas.
     */
    public static boolean chance(int porcentagem) {
        if (porcentagem <= 0) {
            return false;
        }
        if (porcentagem >= 100) {
            return true;
        }
        return numero(100) < porcentagem;
    }

    /** Expõe o gerador para métodos da biblioteca padrão, como Collections.shuffle. */
    public static Random gerador() {
        return aleatorio;
    }
}
