import java.util.Comparator;

// Essa classe é a nossa lista encadeada.
// Ela implementa a interface IColecao, que define quais operações toda coleção deve ter.
// O <T> significa que ela é genérica: funciona com qualquer tipo de dado (Contato, Integer, etc.)

public class ListaEncadeada<T> implements IColecao<T> {
    private No<T> prim, ult; // "prim" aponta pro primeiro nó, "ult" aponta pro último
    private int quant; // Guarda quantos elementos tem na lista no momento
    private final boolean ordenada; // Define se a lista deve se manter em ordem ou não
    private Comparator<T> comparador; // A "régua" usada para comparar os elementos e definir a ordem

    // Construtor: cria uma lista passando se ela será ordenada e qual comparador
    // usar
    public ListaEncadeada(boolean ehOrdenada, Comparator<T> comparador) {
        this.prim = this.ult = null; // A lista começa vazia, sem nenhum nó
        this.quant = 0; // Quantidade inicial é zero
        this.ordenada = ehOrdenada; // Guarda se é pra ser ordenada
        this.comparador = comparador;// Guarda o comparador que vai usar
    }

    // Adiciona um novo valor à lista.
    // Se a lista for ordenada, chama o método de inserção ordenada.
    // Se não, só joga no final.
    public boolean adicionar(T novoValor) {
        if (!this.ordenada) {
            adicionarNaoOrd(novoValor); // Lista normal: vai pro final
        } else {
            adicionarOrd(novoValor); // Lista ordenada: encontra o lugar certo
        }
        return true;
    }

    // Procura um valor na lista percorrendo nó por nó.
    // Retorna o valor se achar, ou null se não encontrar nada.
    public T pesquisar(T valor) {
        No<T> atual = this.prim; // Começa a busca pelo primeiro nó

        while (atual != null) { // Enquanto não chegar no fim da lista...
            if (atual.getValor().equals(valor)) { // Se o nó atual tem o valor que procuramos...
                return atual.getValor(); // ...retorna ele!
            }
            atual = atual.getProx(); // Passa para o próximo nó
        }
        return null; // Chegou no fim e não achou: retorna null
    }

    // Remove um elemento da lista.
    // Percorre a lista procurando o valor e, quando acha, "desliga" o nó da cadeia.
    public boolean remover(T valor) {
        No<T> atual = this.prim; // Nó que estamos olhando agora
        No<T> ant = null; // O nó anterior ao atual (começa sem nenhum)

        while (atual != null) { // Percorre até o fim
            if (atual.getValor().equals(valor)) { // Achou o nó que queremos remover!

                if (ant == null) {
                    // O nó removido era o primeiro! O novo primeiro é o seguinte.
                    this.prim = atual.getProx();
                } else {
                    // O nó anterior "pula" o atual e aponta direto para o próximo
                    ant.setProx(atual.getProx());
                }

                if (atual == this.ult) {
                    // Se o nó removido era o último, o anterior vira o novo último
                    this.ult = ant;
                }

                this.quant--; // Diminui a contagem de elementos
                return true; // Remoção feita com sucesso
            }
            ant = atual; // O atual vira o "anterior"
            atual = atual.getProx(); // Avança para o próximo nó
        }
        return false; // Não encontrou o valor na lista
    }

    // Retorna simplesmente quantos elementos existem na lista agora
    public int quantidadeNos() {
        return this.quant;
    }

    // Método privado: adiciona no FINAL da lista (sem ordenação)
    private void adicionarNaoOrd(T elem) {
        No<T> novo = new No<T>(elem); // Cria o novo nó com o valor

        if (this.prim == null) {
            // A lista estava vazia: o novo nó é o primeiro E o último ao mesmo tempo
            this.prim = this.ult = novo;
        } else {
            // A lista já tem elementos: o último nó aponta para o novo, e o novo vira o
            // último
            this.ult.setProx(novo);
            this.ult = novo;
        }
        this.quant++; // Aumenta a contagem de elementos
    }

    // Método privado: adiciona em ORDEM, no lugar certo da lista
    private void adicionarOrd(T elem) {
        No<T> novo = new No<T>(elem); // Cria o novo nó
        No<T> atual = this.prim; // Nó que estamos verificando agora
        No<T> ant = null; // Nó anterior ao atual

        if (this.prim == null) {
            // A lista está vazia: o novo nó é o único
            this.prim = this.ult = novo;
        } else {
            // Avança enquanto o nó atual for "menor" que o novo elemento
            // (o comparador diz quem vem antes de quem)
            while (atual != null && comparador.compare(atual.getValor(), elem) < 0) {
                ant = atual;
                atual = atual.getProx();
            }

            if (ant == null) {
                // O novo elemento é o menor de todos: vai para o início da lista
                novo.setProx(this.prim);
                this.prim = novo;
            } else if (atual == null) {
                // O novo elemento é o maior de todos: vai para o final da lista
                this.ult.setProx(novo);
                this.ult = novo;
            } else {
                // Caso do meio: encaixa o novo nó entre "ant" e "atual"
                ant.setProx(novo);
                novo.setProx(atual);
            }
        }
        this.quant++; // Aumenta a contagem de elementos
    }

    // Converte a lista encadeada em um array normal do Java.
    // Útil quando precisamos usar as ferramentas do Java que só aceitam arrays.
    public Object[] paraArray() {
        Object[] array = new Object[this.quant]; // Cria um array do tamanho da lista
        No<T> atual = this.prim; // Começa do primeiro nó
        int i = 0;

        while (atual != null) { // Percorre toda a lista
            array[i++] = atual.getValor(); // Coloca o valor do nó na posição i e avança
            atual = atual.getProx();
        }
        return array;
    }

    // Define como a lista aparece quando usamos System.out.println()
    // O resultado fica no formato: [João - 111, Maria - 222, ...]
    @Override
    public String toString() {
        No<T> aux = this.prim; // Começa do primeiro
        String s = "[";

        while (aux != null) {
            s += aux.getValor().toString(); // Adiciona o valor do nó na string
            if (aux != this.ult)
                s += ", "; // Adiciona vírgula entre os elementos (menos no último)
            aux = aux.getProx(); // Avança para o próximo
        }
        return s + "]"; // Fecha o colchete e retorna
    }
}