// Essa classe representa um "nó" da lista encadeada.
// Pensa assim: cada nó é como uma caixinha que guarda um valor
// e aponta para a próxima caixinha da fila.
public class No<T> { // O <T> significa que o nó pode guardar qualquer tipo de dado
    private T valor;    // O conteúdo guardado dentro do nó (ex: um Contato)
    private No<T> prox; // O "ponteiro" que aponta para o próximo nó da lista

    // Construtor: cria um novo nó com o valor que eu passar
    public No(T valor) {
        this.valor = valor; // Salva o valor dentro do nó
        this.prox = null;   // Por padrão, o nó não aponta para ninguém ainda
    }

    // Retorna o valor que está guardado dentro do nó
    public T getValor() {
        return valor;
    }

    // Permite trocar o valor guardado no nó
    public void setValor(T valor) {
        this.valor = valor;
    }

    // Retorna qual é o próximo nó que esse aqui aponta
    public No<T> getProx() {
        return prox;
    }

    // Define para qual nó esse aqui vai apontar como "próximo"
    public void setProx(No<T> prox) {
        this.prox = prox;
    }
}