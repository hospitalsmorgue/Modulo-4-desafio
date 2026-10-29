ARENA DOS CAMPEÕES - BATALHA EM TURNOS
Desafio final do Módulo 4 (Java e Orientação a Objetos) - 3035 Teach


1. DESCRIÇÃO

Jogo de terminal em Java puro. O jogador escolhe um nome e uma classe
(Guerreiro, Mago ou Arqueiro), enfrenta três inimigos aleatórios em
batalhas por turnos e tenta se tornar o campeão da arena.

A partida termina quando todos os inimigos são derrotados ou quando
o herói fica sem vida. Não usa bibliotecas externas nem ferramenta
de build: apenas o JDK 17 ou superior.


2. FUNCIONALIDADES

- Seleção de personagem com descrição de cada classe.
- Três classes com atributos e habilidades próprias:
  Guerreiro (Golpe Pesado), Mago (Bola de Fogo) e Arqueiro (Tiro Certeiro).
- Inimigos com nomes e atributos sorteados, por exemplo
  "Zargor, o Orc Brutal".
- Batalha por turnos. Em cada turno o jogo mostra a vida dos dois lados
  e as ações: 1 Atacar, 2 Defender, 3 Usar Poção de Cura.
- A poção restaura 20 de vida. O herói começa com 3 poções na partida.
- Inteligência simples do inimigo: ataca na maior parte dos turnos
  e defende raramente.
- Várias batalhas em sequência, com mensagens de vitória ou derrota.
- Contagem de batalhas, vida, pontos, moedas e itens usados.
- Pontuação: 10 pontos por vitória.
- Ranking com Map, ordenado com Stream API.
- Loja entre as batalhas: +ataque, +vida e poções, pagas com moedas
  ganhas nas vitórias (25 moedas por inimigo derrotado).
- Validação de toda entrada. Texto no lugar de número e opção fora
  do intervalo são recusados, e o jogo pergunta de novo sem encerrar.


3. ESTRUTURA DE PASTAS

src/
  Main.java                          Ponto de entrada (método main)
  personagem/
    Personagem.java                  Classe abstrata com vida, ataque, defesa e inventário
    Guerreiro.java                   Subclasse com fúria e Golpe Pesado
    Mago.java                        Subclasse com mana e Bola de Fogo
    Arqueiro.java                    Subclasse com precisão e Tiro Certeiro
    Inimigo.java                     Subclasse com atributos sorteados e IA simples
    ClasseHeroi.java                 Enum das classes escolhidas pelo jogador
    Acao.java                        Enum das ações do turno
  batalha/
    Arena.java                       Fluxo da partida e dos turnos
    Loja.java                        Compras entre uma batalha e outra
  utilitarios/
    Entrada.java                     Leitura com Scanner e validação
    Sorteio.java                     Números aleatórios (aceita semente)
    Ranking.java                     Map de pontuação e ordenação com stream
    GeradorDeInimigos.java           Nomes e atributos dos inimigos
    FimDeEntradaException.java       Aviso de fim de entrada, sem derrubar o jogo
README.txt                           Este arquivo
README.md                            O mesmo conteúdo, em Markdown
.gitignore                           Ignora out/ e arquivos .class


4. COMO COMPILAR E EXECUTAR

No terminal, a partir da pasta do projeto:

  javac -encoding UTF-8 -d out $(find src -name "*.java")
  java -cp out Main

Opcional: repetir a mesma partida informando uma semente inteira.

  java -cp out Main 42

Para encerrar no meio do jogo, use Ctrl+C.


5. CONCEITOS DE ORIENTAÇÃO A OBJETOS E DE JAVA USADOS

- Encapsulamento: atributos privados, getters, setters e construtores.
- Herança: Guerreiro, Mago, Arqueiro e Inimigo estendem Personagem.
- Classe abstrata: Personagem não pode ser instanciada direto e obriga
  cada subclasse a informar a própria descrição.
- Polimorfismo: atacar é sobrescrito. O mesmo método produz Golpe Pesado,
  Bola de Fogo, Tiro Certeiro ou o ataque comum do inimigo.
- Enum: ClasseHeroi e Acao representam escolhas fixas do jogo.
- Coleções: List para o inventário e Map para o ranking.
- Lambda e Stream API: contagem de poções e ordenação do ranking.
- Modularização: pacotes personagem, batalha e utilitarios, com métodos
  iniciarJogo, exibirStatus, turnoDoJogador, turnoDoInimigo e fimDeJogo.
- Tratamento de erros: try/catch para entrada não numérica e nova
  pergunta quando a opção está fora do intervalo.
- Laços, condicionais e leitura com Scanner para o controle da partida.
