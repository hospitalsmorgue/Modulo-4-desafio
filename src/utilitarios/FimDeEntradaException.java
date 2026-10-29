package utilitarios;

/**
 * Sinaliza que não há mais linhas para ler (fim do teclado ou do arquivo
 * de entrada). O jogo captura esta exceção e encerra sem estourar erro.
 */
public class FimDeEntradaException extends RuntimeException {

    /** Identificador pedido pelo compilador para classes de exceção. */
    private static final long serialVersionUID = 1L;

    public FimDeEntradaException() {
        super("A entrada de dados foi encerrada.");
    }
}
