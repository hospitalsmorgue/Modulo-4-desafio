import batalha.Arena;
import java.util.Scanner;
import utilitarios.Sorteio;

/**
 * Ponto de entrada da Arena dos Campeões.
 * A partida começa neste método main.
 *
 * <p>Opcionalmente, o primeiro argumento é uma semente numérica.
 * Com a mesma semente, inimigos e críticos saem na mesma ordem.</p>
 */
public class Main {

    public static void main(String[] args) {
        prepararSemente(args);

        Scanner scanner = new Scanner(System.in);
        Arena arena = new Arena(scanner);
        arena.iniciarJogo();
    }

    /**
     * Se o jogador passou um número na linha de comando, os sorteios
     * da partida passam a ser reproduzíveis. Um texto qualquer é ignorado.
     */
    private static void prepararSemente(String[] args) {
        if (args.length == 0) {
            return;
        }

        try {
            long semente = Long.parseLong(args[0]);
            Sorteio.definirSemente(semente);
            System.out.println("(Partida reproduzível. Semente: " + semente + ")");
        } catch (NumberFormatException excecao) {
            System.out.println("Semente ignorada. Use um número inteiro. A partida seguirá aleatória.");
        }
    }
}
