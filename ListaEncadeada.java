import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {
    private No<T> prim, ult;
    private int quant;
    private final boolean ordenada;
    private Comparator<T> comparador;

    public ListaEncadeada(boolean ehOrdenada, Comparator<T> comparador) {
        this.prim = this.ult = null;
        this.quant = 0;
        this.ordenada = ehOrdenada;
        this.comparador = comparador;
    }

    public boolean adicionar(T novoValor) {
        if (!this.ordenada) {
            adicionarNaoOrd(novoValor);
        } else {
            adicionarOrd(novoValor);
        }
        return true;
    }

    public T pesquisar(T valor) {
        No<T> atual = this.prim;
        while (atual != null) {
            if (atual.getValor().equals(valor)) {
                return atual.getValor();
            }
            atual = atual.getProx();
        }
        return null;
    }

    public boolean remover(T valor) {
        No<T> atual = this.prim;
        No<T> ant = null;

        while (atual != null) {
            if (atual.getValor().equals(valor)) {
                if (ant == null) {
                    this.prim = atual.getProx();
                } else {
                    ant.setProx(atual.getProx());
                }

                if (atual == this.ult) {
                    this.ult = ant;
                }

                this.quant--;
                return true;
            }
            ant = atual;
            atual = atual.getProx();
        }
        return false;
    }

    public int quantidadeNos() {
        return this.quant;
    }

    private void adicionarNaoOrd(T elem) {
        No<T> novo = new No<T>(elem);
        if (this.prim == null) {
            this.prim = this.ult = novo;
        } else {
            this.ult.setProx(novo);
            this.ult = novo;
        }
        this.quant++;
    }

    private void adicionarOrd(T elem) {
        No<T> novo = new No<T>(elem);
        No<T> atual = this.prim;
        No<T> ant = null;

        if (this.prim == null)
            this.prim = this.ult = novo;
        else {
            while (atual != null && comparador.compare(atual.getValor(), elem) < 0) {
                ant = atual;
                atual = atual.getProx();
            }

            if (ant == null) {
                novo.setProx(this.prim);
                this.prim = novo;
            } else if (atual == null) {
                this.ult.setProx(novo);
                this.ult = novo;
            } else {
                ant.setProx(novo);
                novo.setProx(atual);
            }
        }
        this.quant++;
    }

    public Object[] paraArray() {
        Object[] array = new Object[this.quant];
        No<T> atual = this.prim;
        int i = 0;
        while (atual != null) {
            array[i++] = atual.getValor();
            atual = atual.getProx();
        }
        return array;
    }

    @Override
    public String toString() {
        No<T> aux = this.prim;
        String s = "[";
        while (aux != null) {
            s += aux.getValor().toString();
            if (aux != this.ult)
                s += ", ";
            aux = aux.getProx();
        }
        return s + "]";
    }
}