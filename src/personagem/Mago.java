package personagem;

/**
 * Herói frágil e poderoso. A mana aumenta o dano da Bola de Fogo,
 * que ignora metade da defesa considerada do alvo.
 */
public class Mago extends Personagem {

    /** Poder mágico. Cada 10 pontos somam 1 de dano à Bola de Fogo. */
    private int mana;

    public Mago(String nome) {
        super(nome, 80, 22, 4, POCOES_INICIAIS);
        this.mana = 30;
    }

    @Override
    public String getDescricao() {
        return "Mago de mana " + mana + ". A Bola de Fogo atravessa parte da defesa inimiga.";
    }

    @Override
    public void atacar(Personagem alvo) {
        // A mesma regra de defesa do Personagem, porém a magia corta essa defesa pela metade.
        int defesaConsiderada = alvo.isDefendendo() ? alvo.getDefesa() : alvo.getDefesa() / 2;
        int defesaAposMagia = defesaConsiderada / 2;
        int bonusDeMana = mana / 10;
        int dano = getAtaque() + bonusDeMana - defesaAposMagia;
        if (dano < 1) {
            dano = 1;
        }

        String mensagem = "Você lançou uma Bola de Fogo e causou " + dano + " de dano!";
        aplicarAtaque(alvo, dano, mensagem);
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        if (mana >= 0) {
            this.mana = mana;
        }
    }
}
