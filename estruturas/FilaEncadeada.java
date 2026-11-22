package estruturas;

/**
 * Fila FIFO implementada com nós encadeados, sem LinkedList ou Queue do Java.
 * Todas as operações públicas são O(1).
 */
public class FilaEncadeada<T> {
    private No<T> inicio;
    private No<T> fim;
    private int quantidade;

    public void enfileirar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Não é possível enfileirar null.");
        }
        No<T> novo = new No<>(valor);
        if (fim == null) {
            inicio = novo;
        } else {
            fim.setProx(novo);
        }
        fim = novo;
        quantidade++;
    }

    public T desenfileirar() {
        if (estaVazia()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        T valor = inicio.getValor();
        inicio = inicio.getProx();
        quantidade--;
        if (inicio == null) {
            fim = null;
        }
        return valor;
    }

    public boolean estaVazia() {
        return quantidade == 0;
    }

    public int tamanho() {
        return quantidade;
    }
}
