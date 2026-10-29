package batalha;

import java.util.Scanner;
import personagem.Personagem;
import utilitarios.Entrada;

/**
 * Loja aberta entre uma batalha e outra.
 * As melhorias são pagas com as moedas ganhas ao vencer um inimigo.
 */
public class Loja {

    public static final int PRECO_ATAQUE = 20;
    public static final int PRECO_VIDA = 15;
    public static final int PRECO_POCAO = 10;

    public static final int BONUS_ATAQUE = 5;
    public static final int BONUS_VIDA = 20;

    /**
     * Mantém a loja aberta até o jogador escolher seguir em frente.
     *
     * @return as moedas que sobraram depois das compras
     */
    public int abrir(Scanner scanner, Personagem heroi, int moedas) {
        boolean comprando = true;

        while (comprando) {
            exibirVitrine(heroi, moedas);
            int opcao = Entrada.lerInteiroNoIntervalo(scanner, "Digite o número da compra: ", 1, 4);

            switch (opcao) {
                case 1 -> moedas = comprarAtaque(heroi, moedas);
                case 2 -> moedas = comprarVida(heroi, moedas);
                case 3 -> moedas = comprarPocao(heroi, moedas);
                case 4 -> {
                    System.out.println("Você deixa a loja e segue para a próxima batalha.");
                    comprando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }

        return moedas;
    }

    private void exibirVitrine(Personagem heroi, int moedas) {
        System.out.println();
        System.out.println("---------- Loja da Arena ----------");
        System.out.println("Moedas: " + moedas
                + " | Vida: " + heroi.getVida() + "/" + heroi.getVidaMaxima()
                + " | Ataque: " + heroi.getAtaque()
                + " | Poções: " + heroi.quantidadePocoes());
        System.out.println("1. Amolar arma (+" + BONUS_ATAQUE + " de ataque) — " + PRECO_ATAQUE + " moedas");
        System.out.println("2. Treino de vitalidade (+" + BONUS_VIDA
                + " de vida máxima e cura " + BONUS_VIDA + ") — " + PRECO_VIDA + " moedas");
        System.out.println("3. Poção de Cura — " + PRECO_POCAO + " moedas");
        System.out.println("4. Seguir para a próxima batalha");
    }

    private int comprarAtaque(Personagem heroi, int moedas) {
        if (!temMoedas(moedas, PRECO_ATAQUE)) {
            return moedas;
        }
        heroi.aumentarAtaque(BONUS_ATAQUE);
        moedas -= PRECO_ATAQUE;
        System.out.println("Arma amolada. Ataque agora: " + heroi.getAtaque() + ". Moedas restantes: " + moedas + ".");
        return moedas;
    }

    private int comprarVida(Personagem heroi, int moedas) {
        if (!temMoedas(moedas, PRECO_VIDA)) {
            return moedas;
        }
        heroi.aumentarVidaMaxima(BONUS_VIDA);
        moedas -= PRECO_VIDA;
        System.out.println("Vitalidade treinada. Vida: " + heroi.getVida() + "/" + heroi.getVidaMaxima()
                + ". Moedas restantes: " + moedas + ".");
        return moedas;
    }

    private int comprarPocao(Personagem heroi, int moedas) {
        if (!temMoedas(moedas, PRECO_POCAO)) {
            return moedas;
        }
        heroi.adicionarPocao();
        moedas -= PRECO_POCAO;
        System.out.println("Poção guardada no inventário. Você tem " + heroi.quantidadePocoes()
                + " poções. Moedas restantes: " + moedas + ".");
        return moedas;
    }

    private boolean temMoedas(int moedas, int preco) {
        if (moedas < preco) {
            System.out.println("Moedas insuficientes. Você tem " + moedas + " e este item custa " + preco + ".");
            return false;
        }
        return true;
    }
}
