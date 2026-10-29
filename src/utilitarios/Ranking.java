package utilitarios;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ranking da arena, guardado em um {@link Map} de nome para pontuação.
 *
 * <p>Alguns campeões lendários já estão cadastrados. O herói da partida
 * entra na lista e sobe conforme vence batalhas. A ordenação usa a Stream
 * API, do maior número de pontos para o menor.</p>
 */
public class Ranking {

    /** Chave: nome do lutador. Valor: pontos acumulados. */
    private final Map<String, Integer> pontuacoes = new HashMap<>();

    public Ranking(String nomeDoJogador) {
        // Campeões que já passaram pela arena. Servem de comparação para o jogador.
        pontuacoes.put("Lyra, a Invicta", 25);
        pontuacoes.put("Borin Punho de Ferro", 20);
        pontuacoes.put("Elowen Folha-Certa", 15);
        pontuacoes.put("Mestre Kael", 10);
        pontuacoes.put(nomeDoJogador, 0);
    }

    /** Soma pontos ao nome informado. Se a chave não existir, ela é criada. */
    public void adicionarPontos(String nome, int pontos) {
        // Integer::sum é uma referência de método: (atual, extra) -> atual + extra.
        pontuacoes.merge(nome, pontos, Integer::sum);
    }

    public int pontosDe(String nome) {
        return pontuacoes.getOrDefault(nome, 0);
    }

    /**
     * Monta a lista do ranking já ordenada.
     * O critério principal é a pontuação (decrescente). Empates usam o nome,
     * para a ordem não mudar de uma execução para outra.
     */
    public List<Map.Entry<String, Integer>> ordenar() {
        return pontuacoes.entrySet().stream()
                .sorted(Comparator
                        .comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .toList();
    }

    /** Imprime o ranking numerado no terminal. */
    public void exibir() {
        System.out.println();
        System.out.println("---------- Ranking da Arena ----------");
        List<Map.Entry<String, Integer>> ordenado = ordenar();

        int posicao = 1;
        for (Map.Entry<String, Integer> entrada : ordenado) {
            System.out.println(posicao + ". " + entrada.getKey() + " — " + entrada.getValue() + " pontos");
            posicao++;
        }
    }
}
