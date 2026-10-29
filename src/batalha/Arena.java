package batalha;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import personagem.Acao;
import personagem.ClasseHeroi;
import personagem.Inimigo;
import personagem.Personagem;
import utilitarios.Entrada;
import utilitarios.FimDeEntradaException;
import utilitarios.GeradorDeInimigos;
import utilitarios.Ranking;

/**
 * Controla uma partida inteira da Arena dos Campeões.
 *
 * <p>O fluxo está dividido nos métodos pedidos pelo desafio:
 * {@link #iniciarJogo()}, {@link #exibirStatus()}, {@link #turnoDoJogador()},
 * {@link #turnoDoInimigo()} e {@link #fimDeJogo()}.</p>
 */
public class Arena {

    /** Quantidade de inimigos que o herói precisa vencer para ser campeão. */
    public static final int TOTAL_DE_INIMIGOS = 3;

    /** Pontos ganhos ao derrubar um inimigo. */
    public static final int PONTOS_POR_VITORIA = 10;

    /** Moedas ganhas ao derrubar um inimigo. Servem para a loja. */
    public static final int MOEDAS_POR_VITORIA = 25;

    private final Scanner scanner;
    private final Loja loja = new Loja();

    private Personagem jogador;
    private Inimigo inimigoAtual;
    private Ranking ranking;

    private int turno;
    private int batalhasVencidas;
    private int pontos;
    private int moedas;
    private int itensUsados;
    private boolean venceuAArena;
    private boolean jogoEncerrado;

    public Arena(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prepara o herói, enfrenta os inimigos em sequência e encerra
     * quando todos caem ou quando o jogador morre.
     */
    public void iniciarJogo() {
        try {
            exibirBoasVindas();

            String nome = Entrada.lerNome(scanner);
            ClasseHeroi classe = escolherClasse();
            jogador = classe.criarPersonagem(nome);
            ranking = new Ranking(nome);

            System.out.println();
            System.out.println("Você escolheu: " + classe.getNome());
            System.out.println(jogador.getDescricao());
            System.out.println("Vida: " + jogador.getVida()
                    + " | Ataque: " + jogador.getAtaque()
                    + " | Defesa: " + jogador.getDefesa()
                    + " | Poções: " + jogador.quantidadePocoes());

            GeradorDeInimigos gerador = new GeradorDeInimigos();

            for (int numero = 1; numero <= TOTAL_DE_INIMIGOS && jogador.estaVivo(); numero++) {
                inimigoAtual = gerador.gerar(numero);
                apresentarInimigo(numero);
                executarBatalha();

                if (!jogador.estaVivo()) {
                    break;
                }

                registrarVitoria();

                boolean aindaHaInimigos = numero < TOTAL_DE_INIMIGOS;
                if (aindaHaInimigos) {
                    moedas = loja.abrir(scanner, jogador, moedas);
                }
            }

            venceuAArena = jogador.estaVivo() && batalhasVencidas == TOTAL_DE_INIMIGOS;
            fimDeJogo();
        } catch (FimDeEntradaException excecao) {
            System.out.println();
            System.out.println("Entrada encerrada antes do fim da aventura.");
            fimDeJogo();
        }
    }

    /** Mostra a vida dos dois lados no começo do turno. */
    public void exibirStatus() {
        System.out.println();
        System.out.println("Turno " + turno + ": Sua vida: " + jogador.getVida()
                + "/" + jogador.getVidaMaxima()
                + " | Vida do inimigo: " + inimigoAtual.getVida()
                + "/" + inimigoAtual.getVidaMaxima());
    }

    /**
     * Pergunta a ação do jogador até que uma ação válida seja executada.
     * Poção sem estoque ou usada com a vida cheia não gasta o turno:
     * o menu é exibido de novo.
     */
    public void turnoDoJogador() {
        boolean agiu = false;

        while (!agiu) {
            exibirMenuDeAcoes();
            int opcao = Entrada.lerInteiroNoIntervalo(scanner, "Digite o número da ação: ", 1, 3);
            Acao acao = Acao.peloNumero(opcao);

            if (acao == null) {
                System.out.println("Opção inválida. Escolha 1, 2 ou 3.");
                continue;
            }

            switch (acao) {
                case ATACAR -> {
                    jogador.atacar(inimigoAtual);
                    agiu = true;
                }
                case DEFENDER -> {
                    jogador.defender();
                    agiu = true;
                }
                case USAR_POCAO -> agiu = tentarUsarPocao();
                default -> System.out.println("Opção inválida. Escolha 1, 2 ou 3.");
            }
        }

        // A defesa do inimigo só vale contra o golpe deste turno.
        inimigoAtual.setDefendendo(false);
    }

    /** A inteligência simples do inimigo escolhe entre atacar e defender. */
    public void turnoDoInimigo() {
        if (!inimigoAtual.estaVivo() || !jogador.estaVivo()) {
            return;
        }

        Acao acao = inimigoAtual.decidirAcao();
        if (acao == Acao.DEFENDER) {
            inimigoAtual.defender();
        } else {
            inimigoAtual.atacar(jogador);
        }

        // A defesa do jogador só vale contra o golpe que acabou de vir.
        jogador.setDefendendo(false);
    }

    /** Mensagem final, com o placar da partida e o ranking ordenado. */
    public void fimDeJogo() {
        if (jogoEncerrado) {
            return;
        }
        jogoEncerrado = true;

        System.out.println();
        System.out.println("========== FIM DE JOGO ==========");

        if (venceuAArena) {
            System.out.println("Você venceu! Parabéns, campeão da arena!");
        } else if (jogador != null && !jogador.estaVivo()) {
            System.out.println("Você foi derrotado! A arena clama por outro campeão.");
        } else {
            System.out.println("A aventura terminou antes da hora.");
        }

        System.out.println("Batalhas vencidas: " + batalhasVencidas + " de " + TOTAL_DE_INIMIGOS);
        System.out.println("Pontos: " + pontos);
        System.out.println("Moedas restantes: " + moedas);
        System.out.println("Itens usados: " + itensUsados);

        if (jogador != null) {
            System.out.println("Vida restante: " + jogador.getVida() + "/" + jogador.getVidaMaxima());
            System.out.println("Poções restantes: " + jogador.quantidadePocoes());
        }

        if (ranking != null) {
            ranking.exibir();
            if (jogador != null && estaEmPrimeiro()) {
                System.out.println();
                System.out.println(jogador.getNome() + " está no topo do ranking!");
            }
        }
    }

    private void executarBatalha() {
        turno = 1;
        while (jogador.estaVivo() && inimigoAtual.estaVivo()) {
            exibirStatus();
            turnoDoJogador();
            if (!inimigoAtual.estaVivo() || !jogador.estaVivo()) {
                break;
            }
            turnoDoInimigo();
            turno++;
        }
    }

    private void registrarVitoria() {
        batalhasVencidas++;
        pontos += PONTOS_POR_VITORIA;
        moedas += MOEDAS_POR_VITORIA;
        ranking.adicionarPontos(jogador.getNome(), PONTOS_POR_VITORIA);

        System.out.println();
        System.out.println("Batalha vencida! +" + PONTOS_POR_VITORIA + " pontos e +"
                + MOEDAS_POR_VITORIA + " moedas.");
        System.out.println("Placar: " + pontos + " pontos | " + moedas + " moedas | batalhas "
                + batalhasVencidas + "/" + TOTAL_DE_INIMIGOS + ".");
    }

    private boolean tentarUsarPocao() {
        if (jogador.quantidadePocoes() <= 0) {
            System.out.println("Você não tem Poção de Cura. Escolha outra ação.");
            return false;
        }
        if (jogador.getVida() >= jogador.getVidaMaxima()) {
            System.out.println("Sua vida já está no máximo. A poção não foi gasta. Escolha outra ação.");
            return false;
        }

        int recuperado = jogador.usarPocao();
        itensUsados++;
        System.out.println("Você usou uma Poção de Cura e recuperou " + recuperado
                + " de vida. Vida atual: " + jogador.getVida()
                + ". Poções restantes: " + jogador.quantidadePocoes() + ".");
        return true;
    }

    private void exibirBoasVindas() {
        System.out.println("========================================");
        System.out.println("   BEM-VINDO À ARENA DOS CAMPEÕES!");
        System.out.println("        Batalha em Turnos");
        System.out.println("========================================");
        System.out.println("Escolha um herói e enfrente " + TOTAL_DE_INIMIGOS + " inimigos.");
        System.out.println("A cada turno: ataque, defenda ou use uma poção.");
        System.out.println("Cada vitória vale " + PONTOS_POR_VITORIA + " pontos e "
                + MOEDAS_POR_VITORIA + " moedas.");
        System.out.println();
    }

    private ClasseHeroi escolherClasse() {
        System.out.println("Escolha sua classe:");
        for (ClasseHeroi classe : ClasseHeroi.values()) {
            System.out.println(classe.getOpcao() + ". " + classe.getNome());
            System.out.println("   " + classe.getDescricao());
        }

        int opcao = Entrada.lerInteiroNoIntervalo(scanner, "Digite o número da classe: ", 1, 3);
        ClasseHeroi classe = ClasseHeroi.peloNumero(opcao);
        if (classe == null) {
            // O leitor já limita o intervalo; este retorno só protege o compilador.
            return ClasseHeroi.GUERREIRO;
        }
        return classe;
    }

    private void apresentarInimigo(int numero) {
        System.out.println();
        System.out.println("---------- Batalha " + numero + " de " + TOTAL_DE_INIMIGOS + " ----------");
        System.out.println("Inimigo encontrado: " + inimigoAtual.getNome());
        System.out.println(inimigoAtual.getDescricao());
    }

    private void exibirMenuDeAcoes() {
        System.out.println("Escolha sua ação:");
        System.out.println("1. " + Acao.ATACAR.getDescricao());
        System.out.println("2. " + Acao.DEFENDER.getDescricao());
        System.out.println("3. " + Acao.USAR_POCAO.getDescricao()
                + " (restaura " + Personagem.CURA_DA_POCAO
                + " de vida, " + jogador.quantidadePocoes() + " restantes)");
    }

    private boolean estaEmPrimeiro() {
        List<Map.Entry<String, Integer>> ordenado = ranking.ordenar();
        if (ordenado.isEmpty()) {
            return false;
        }
        return ordenado.get(0).getKey().equals(jogador.getNome())
                && ordenado.get(0).getValue() == pontos;
    }
}
