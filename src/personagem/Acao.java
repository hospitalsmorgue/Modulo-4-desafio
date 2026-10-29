package personagem;

/**
 * Ações que um lutador pode escolher no turno.
 * O número de cada constante é o mesmo que aparece no menu do jogo.
 */
public enum Acao {
    ATACAR(1, "Atacar"),
    DEFENDER(2, "Defender"),
    USAR_POCAO(3, "Usar Poção de Cura");

    private final int numero;
    private final String descricao;

    Acao(int numero, String descricao) {
        this.numero = numero;
        this.descricao = descricao;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    /** Converte a opção digitada no menu para a ação correspondente. */
    public static Acao peloNumero(int numero) {
        for (Acao acao : values()) {
            if (acao.numero == numero) {
                return acao;
            }
        }
        return null;
    }
}
