package utilitarios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import personagem.Inimigo;

/**
 * Monta a fila de inimigos da partida.
 * Os nomes são embaralhados para não repetir enquanto ainda houver opções,
 * e os atributos crescem conforme o número da batalha.
 */
public class GeradorDeInimigos {

    private static final List<String> NOMES_BASE = List.of(
            "Zargor, o Orc Brutal",
            "Morthak, o Goblin Traiçoeiro",
            "Sylvara, a Bruxa Sombria",
            "Drogan, o Troll das Cavernas",
            "Vexar, o Esqueleto Rei",
            "Grimfang, o Lobo Alfa",
            "Azgoth, o Demônio Menor",
            "Kharza, a Arqueira Negra"
    );

    /** Nomes que ainda não apareceram nesta partida. */
    private final List<String> nomesDisponiveis = new ArrayList<>(NOMES_BASE);

    public GeradorDeInimigos() {
        Collections.shuffle(nomesDisponiveis, Sorteio.gerador());
    }

    /**
     * Cria o próximo inimigo.
     *
     * @param numeroDaBatalha 1 para a primeira luta, 2 para a segunda, etc.
     */
    public Inimigo gerar(int numeroDaBatalha) {
        String nome = sortearNome();

        // A base sobe com a batalha e um pequeno sorteio deixa cada monstro diferente.
        int vida = 40 + (numeroDaBatalha * 12) + Sorteio.entre(0, 6);
        int ataque = 9 + (numeroDaBatalha * 2) + Sorteio.entre(0, 2);
        int defesa = 1 + numeroDaBatalha + Sorteio.entre(0, 2);

        return new Inimigo(nome, vida, ataque, defesa, numeroDaBatalha);
    }

    private String sortearNome() {
        if (nomesDisponiveis.isEmpty()) {
            nomesDisponiveis.addAll(NOMES_BASE);
            Collections.shuffle(nomesDisponiveis, Sorteio.gerador());
        }
        return nomesDisponiveis.remove(0);
    }
}
