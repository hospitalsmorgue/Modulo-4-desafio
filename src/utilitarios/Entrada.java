package utilitarios;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Lê o que o jogador digita e só devolve valores válidos.
 *
 * <p>Toda leitura numérica passa por try/catch. Se a pessoa digitar letra,
 * um número fora das opções ou uma linha vazia, o método explica o problema
 * e pergunta de novo. O jogo em si não precisa se preocupar com entrada
 * quebrada.</p>
 */
public final class Entrada {

    private Entrada() {
        // Classe de utilidade: apenas métodos estáticos.
    }

    /**
     * Lê o nome do herói.
     * Aceita letras, espaços e acentos, com tamanho entre 2 e 30 caracteres.
     */
    public static String lerNome(Scanner scanner) {
        while (true) {
            perguntar("Digite seu nome: ");
            String nome = lerLinha(scanner);
            nome = nome.trim();

            if (nome.length() < 2) {
                System.out.println("Nome inválido. Digite pelo menos 2 caracteres.");
                continue;
            }
            if (nome.length() > 30) {
                System.out.println("Nome muito longo. Use no máximo 30 caracteres.");
                continue;
            }
            return nome;
        }
    }

    /**
     * Lê um número inteiro e repete a pergunta enquanto o valor estiver
     * fora do intervalo ou não for um número.
     */
    public static int lerInteiroNoIntervalo(Scanner scanner, String mensagem, int minimo, int maximo) {
        while (true) {
            perguntar(mensagem);
            String texto = lerLinha(scanner).trim();

            try {
                int valor = Integer.parseInt(texto);
                if (valor < minimo || valor > maximo) {
                    System.out.println("Opção inválida. Digite um número entre " + minimo + " e " + maximo + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException excecao) {
                // Letras, vazio, decimal ou texto misturado caem aqui.
                System.out.println("Entrada inválida. Digite apenas números.");
            }
        }
    }

    /** Lê uma linha ou avisa o jogo de que a entrada acabou. */
    private static String lerLinha(Scanner scanner) {
        try {
            if (!scanner.hasNextLine()) {
                throw new FimDeEntradaException();
            }
            return scanner.nextLine();
        } catch (NoSuchElementException excecao) {
            throw new FimDeEntradaException();
        } catch (IllegalStateException excecao) {
            throw new FimDeEntradaException();
        }
    }

    /** Mostra um prompt e força a saída, para funcionar também com entrada redirecionada. */
    private static void perguntar(String mensagem) {
        System.out.print(mensagem);
        System.out.flush();
    }
}
