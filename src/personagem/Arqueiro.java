package personagem;

import utilitarios.Sorteio;

/**
 * Herói equilibrado. A precisão é a chance de transformar o disparo
 * em um Tiro Certeiro, que dobra o dano.
 */
public class Arqueiro extends Personagem {

    /** Chance percentual de acerto crítico. */
    private int precisao;

    public Arqueiro(String nome) {
        super(nome, 100, 18, 7, POCOES_INICIAIS);
        this.precisao = 35;
    }

    @Override
    public String getDescricao() {
        return "Arqueiro com " + precisao + "% de precisão. Um Tiro Certeiro dobra o dano.";
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo);
        String mensagem;

        if (Sorteio.chance(precisao)) {
            dano = dano * 2;
            mensagem = "Você usou Tiro Certeiro e causou " + dano + " de dano!";
        } else {
            mensagem = "Você atacou e causou " + dano + " de dano!";
        }

        aplicarAtaque(alvo, dano, mensagem);
    }

    public int getPrecisao() {
        return precisao;
    }

    public void setPrecisao(int precisao) {
        if (precisao < 0) {
            this.precisao = 0;
        } else if (precisao > 100) {
            this.precisao = 100;
        } else {
            this.precisao = precisao;
        }
    }
}
