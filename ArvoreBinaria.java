import java.util.Comparator;

public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {
    private NoArvore<T> raiz;
    private int quant;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
        this.quant = 0;
    }

    @Override
    public boolean adicionar(T novoValor) {
        if (this.raiz == null) {
            this.raiz = new NoArvore<>(novoValor);
            this.quant++;
            return true;
        }

        NoArvore<T> atual = this.raiz;
        while (true) {
            int cmp = comparador.compare(novoValor, atual.getValor());
            if (cmp == 0) return false; 

            if (cmp < 0) {
                if (atual.getEsq() == null) {
                    atual.setEsq(new NoArvore<>(novoValor));
                    this.quant++;
                    return true;
                }
                atual = atual.getEsq();
            } else {
                if (atual.getDir() == null) {
                    atual.setDir(new NoArvore<>(novoValor));
                    this.quant++;
                    return true;
                }
                atual = atual.getDir();
            }
        }
    }

    @Override
    public T pesquisar(T valor) {
        NoArvore<T> atual = this.raiz;
        while (atual != null) {
            int cmp = comparador.compare(valor, atual.getValor());
            if (cmp == 0) return atual.getValor();
            if (cmp < 0) atual = atual.getEsq();
            else atual = atual.getDir();
        }
        return null;
    }

    /**
     * Pesquisa por um critério diferente do usado para indexar a árvore.
     * Percorre todos os nós (O(n)) usando o comparador informado.
     */
    public T pesquisar(T valor, Comparator<T> outroComparador) {
        java.util.ArrayDeque<NoArvore<T>> pilha = new java.util.ArrayDeque<>();
        if (this.raiz != null) pilha.push(this.raiz);
        while (!pilha.isEmpty()) {
            NoArvore<T> atual = pilha.pop();
            if (outroComparador.compare(valor, atual.getValor()) == 0) return atual.getValor();
            if (atual.getDir() != null) pilha.push(atual.getDir());
            if (atual.getEsq() != null) pilha.push(atual.getEsq());
        }
        return null;
    }

    @Override
    public boolean remover(T valor) {
        NoArvore<T> atual = this.raiz;
        NoArvore<T> pai = null;

        while (atual != null && comparador.compare(valor, atual.getValor()) != 0) {
            pai = atual;
            if (comparador.compare(valor, atual.getValor()) < 0) atual = atual.getEsq();
            else atual = atual.getDir();
        }

        if (atual == null) return false;

        if (atual.getEsq() == null || atual.getDir() == null) {
            NoArvore<T> substituto = (atual.getEsq() != null) ? atual.getEsq() : atual.getDir();
            if (pai == null) this.raiz = substituto;
            else if (atual == pai.getEsq()) pai.setEsq(substituto);
            else pai.setDir(substituto);
        } else {
            NoArvore<T> paiSucessor = atual;
            NoArvore<T> sucessor = atual.getDir();
            while (sucessor.getEsq() != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.getEsq();
            }
            atual.setValor(sucessor.getValor());
            if (paiSucessor.getEsq() == sucessor) paiSucessor.setEsq(sucessor.getDir());
            else paiSucessor.setDir(sucessor.getDir());
        }

        this.quant--;
        return true;
    }

    @Override
    public int quantidadeNos() {
        return this.quant;
    }

    @Override
    public int altura() {
        return calculaAltura(this.raiz);
    }

    private int calculaAltura(NoArvore<T> no) {
        if (no == null) return -1;
        return 1 + Math.max(calculaAltura(no.getEsq()), calculaAltura(no.getDir()));
    }

    @Override
    public String caminharEmNivel() {
        return "[]"; 
    }

    @Override
    public String caminharEmOrdem() {
        return "[]"; 
    }
}