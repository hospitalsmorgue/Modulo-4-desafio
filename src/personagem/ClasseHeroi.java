package personagem;

/**
 * Classes que o jogador pode escolher antes da primeira batalha.
 * Cada constante conhece o próprio texto de apresentação e sabe criar
 * o objeto concreto (Guerreiro, Mago ou Arqueiro).
 */
public enum ClasseHeroi {
    GUERREIRO(
            1,
            "Guerreiro",
            "Vida alta e defesa forte. Habilidade: Golpe Pesado, com chance de causar dano extra."
    ),
    MAGO(
            2,
            "Mago",
            "Ataque mágico elevado e pouca defesa. Habilidade: Bola de Fogo, que ignora parte da armadura."
    ),
    ARQUEIRO(
            3,
            "Arqueiro",
            "Atributos equilibrados. Habilidade: Tiro Certeiro, com chance de acerto crítico."
    );

    private final int opcao;
    private final String nome;
    private final String descricao;

    ClasseHeroi(int opcao, String nome, String descricao) {
        this.opcao = opcao;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getOpcao() {
        return opcao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    /** Cria o herói da classe escolhida. O nome é o que o jogador digitou. */
    public Personagem criarPersonagem(String nomeDoJogador) {
        return switch (this) {
            case GUERREIRO -> new Guerreiro(nomeDoJogador);
            case MAGO -> new Mago(nomeDoJogador);
            case ARQUEIRO -> new Arqueiro(nomeDoJogador);
        };
    }

    public static ClasseHeroi peloNumero(int numero) {
        for (ClasseHeroi classe : values()) {
            if (classe.opcao == numero) {
                return classe;
            }
        }
        return null;
    }
}
