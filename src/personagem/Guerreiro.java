package personagem;

import utilitarios.Sorteio;

/**
 * Herói resistente. Tem bastante vida, defesa alta e a fúria, um bônus fixo
 * de dano corpo a corpo. De vez em quando acerta um Golpe Pesado.
 */
public class Guerreiro extends Personagem {

    /** Chance, em porcentagem, de o Golpe Pesado acontecer. */
    private int chanceGolpePesado;

    /** Dano extra somado a todo ataque do guerreiro. */
    private int furia;

    public Guerreiro(String nome) {
        super(nome, 120, 20, 10, POCOES_INICIAIS);
        this.chanceGolpePesado = 30;
        this.furia = 2;
    }

    @Override
    public String getDescricao() {
        return "Guerreiro de armadura pesada. Fúria +" + furia
                + " de dano e " + chanceGolpePesado + "% de chance de Golpe Pesado.";
    }

    /**
     * Polimorfismo: o mesmo método {@code atacar} do Personagem aqui
     * inclui a fúria e pode transformar o golpe em Golpe Pesado.
     */
    @Override
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo) + furia;
        String mensagem;

        if (Sorteio.chance(chanceGolpePesado)) {
            int extra = dano / 2;
            dano += extra;
            mensagem = "Você usou Golpe Pesado e causou " + dano + " de dano!";
        } else {
            mensagem = "Você atacou e causou " + dano + " de dano!";
        }

        aplicarAtaque(alvo, dano, mensagem);
    }

    public int getChanceGolpePesado() {
        return chanceGolpePesado;
    }

    public void setChanceGolpePesado(int chanceGolpePesado) {
        if (chanceGolpePesado < 0) {
            this.chanceGolpePesado = 0;
        } else if (chanceGolpePesado > 100) {
            this.chanceGolpePesado = 100;
        } else {
            this.chanceGolpePesado = chanceGolpePesado;
        }
    }

    public int getFuria() {
        return furia;
    }

    public void setFuria(int furia) {
        if (furia >= 0) {
            this.furia = furia;
        }
    }
}
