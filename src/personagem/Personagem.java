package personagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe abstrata de todo lutador da arena.
 *
 * <p>Não dá para criar um {@code new Personagem(...)}: Guerreiro, Mago,
 * Arqueiro e Inimigo herdam estes atributos e completam o comportamento.
 * Os campos são privados. Quem está fora da classe só lê ou altera o estado
 * pelos métodos públicos (encapsulamento).</p>
 */
public abstract class Personagem {

    /** Nome do item de cura dentro do inventário. */
    public static final String NOME_POCAO = "Poção de Cura";

    /** Quanto uma poção recupera de vida, sem ultrapassar o máximo. */
    public static final int CURA_DA_POCAO = 20;

    /** Poções com que cada herói começa a partida. */
    public static final int POCOES_INICIAIS = 3;

    private String nome;
    private int vida;
    private int vidaMaxima;
    private int ataque;
    private int defesa;

    /** Itens carregados. As poções são entradas com o texto {@link #NOME_POCAO}. */
    private final List<String> inventario = new ArrayList<>();

    /** Verdadeiro apenas no turno em que o personagem escolheu defender. */
    private boolean defendendo;

    protected Personagem(String nome, int vida, int ataque, int defesa, int pocoesIniciais) {
        this.nome = nome;
        this.vidaMaxima = vida;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.defendendo = false;

        for (int i = 0; i < pocoesIniciais; i++) {
            inventario.add(NOME_POCAO);
        }
    }

    /** Texto curto da classe ou do monstro, usado na seleção e no encontro. */
    public abstract String getDescricao();

    /**
     * Ataque padrão: calcula o dano, avisa no terminal e aplica no alvo.
     * As classes de herói sobrescrevem este método para usar a habilidade especial.
     */
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo);
        aplicarAtaque(alvo, dano, getNome() + " atacou e causou " + dano + " de dano!");
    }

    /** Entra em postura defensiva. O próximo dano recebido usa a defesa inteira. */
    public void defender() {
        defendendo = true;
        System.out.println(nome + " se defende e sofrerá menos dano no próximo golpe.");
    }

    /**
     * Reduz a vida atual. O valor nunca fica negativo: ao chegar em zero,
     * {@link #estaVivo()} passa a devolver falso e o lutador sai da batalha.
     */
    public void tomarDano(int dano) {
        int danoAplicado = Math.max(0, dano);
        vida -= danoAplicado;
        if (vida < 0) {
            vida = 0;
        }
    }

    /** Um personagem vivo ainda tem pelo menos 1 ponto de vida. */
    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Dano básico desta investida.
     *
     * <p>Se o alvo estiver defendendo, a defesa inteira é descontada.
     * Caso contrário, só a metade conta (divisão inteira, então sobra
     * descartada). O golpe sempre causa pelo menos 1 de dano.</p>
     */
    protected int calcularDano(Personagem alvo) {
        int defesaConsiderada = alvo.isDefendendo() ? alvo.getDefesa() : alvo.getDefesa() / 2;
        int dano = ataque - defesaConsiderada;
        if (dano < 1) {
            dano = 1;
        }
        return dano;
    }

    /** Mostra a mensagem, aplica o dano e avisa se o alvo caiu. */
    protected void aplicarAtaque(Personagem alvo, int dano, String mensagem) {
        System.out.println(mensagem);
        alvo.tomarDano(dano);
        if (!alvo.estaVivo()) {
            // Frase neutra: os inimigos podem ter nome masculino ou feminino.
            System.out.println(alvo.getNome() + " está fora de combate!");
        }
    }

    /**
     * Conta as poções com um stream. O lambda {@code item -> ...} testa
     * cada elemento da lista.
     */
    public int quantidadePocoes() {
        return (int) inventario.stream()
                .filter(item -> NOME_POCAO.equals(item))
                .count();
    }

    /**
     * Gasta uma poção e recupera vida, respeitando o máximo.
     *
     * @return quanto de vida foi de fato recuperado (0 se não havia poção)
     */
    public int usarPocao() {
        boolean removeu = inventario.remove(NOME_POCAO);
        if (!removeu) {
            return 0;
        }
        int vidaAntes = vida;
        vida = Math.min(vidaMaxima, vida + CURA_DA_POCAO);
        return vida - vidaAntes;
    }

    public void adicionarPocao() {
        inventario.add(NOME_POCAO);
    }

    public void aumentarAtaque(int quantidade) {
        if (quantidade > 0) {
            ataque += quantidade;
        }
    }

    /** Sobe o teto de vida e já recupera a mesma quantia. */
    public void aumentarVidaMaxima(int quantidade) {
        if (quantidade <= 0) {
            return;
        }
        vidaMaxima += quantidade;
        vida += quantidade;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome.trim();
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        if (vida < 0) {
            this.vida = 0;
        } else if (vida > vidaMaxima) {
            this.vida = vidaMaxima;
        } else {
            this.vida = vida;
        }
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        if (vidaMaxima < 1) {
            return;
        }
        this.vidaMaxima = vidaMaxima;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        if (ataque >= 1) {
            this.ataque = ataque;
        }
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        if (defesa >= 0) {
            this.defesa = defesa;
        }
    }

    /** Devolve uma visão somente leitura, para ninguém alterar a lista por fora. */
    public List<String> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    public boolean isDefendendo() {
        return defendendo;
    }

    public void setDefendendo(boolean defendendo) {
        this.defendendo = defendendo;
    }
}
