package personagem;

import utilitarios.Sorteio;

/**
 * Oponente gerado para cada batalha. Os atributos vêm do sorteio;
 * a inteligência é simples: ataca na maior parte dos turnos e só
 * defende de vez em quando.
 */
public class Inimigo extends Personagem {

    /** 1 na primeira luta, 2 na segunda, e assim por diante. */
    private int nivelAmeaca;

    /** Impede dois turnos seguidos só na defesa, para a luta não travar. */
    private boolean defendeuPorUltimo;

    public Inimigo(String nome, int vida, int ataque, int defesa, int nivelAmeaca) {
        super(nome, vida, ataque, defesa, 0);
        this.nivelAmeaca = nivelAmeaca;
        this.defendeuPorUltimo = false;
    }

    @Override
    public String getDescricao() {
        return "Nível de ameaça " + nivelAmeaca
                + " | Vida: " + getVida()
                + " | Ataque: " + getAtaque()
                + " | Defesa: " + getDefesa();
    }

    /**
     * Decide o turno do monstro.
     * Há 25% de chance de defender, mas nunca duas vezes seguidas.
     * No restante dos turnos, o inimigo ataca.
     */
    public Acao decidirAcao() {
        boolean vaiDefender = !defendeuPorUltimo && Sorteio.chance(25);
        if (vaiDefender) {
            defendeuPorUltimo = true;
            return Acao.DEFENDER;
        }
        defendeuPorUltimo = false;
        return Acao.ATACAR;
    }

    public int getNivelAmeaca() {
        return nivelAmeaca;
    }

    public void setNivelAmeaca(int nivelAmeaca) {
        if (nivelAmeaca >= 1) {
            this.nivelAmeaca = nivelAmeaca;
        }
    }
}
