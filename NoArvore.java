public class NoArvore<T> {
    // Cada nó guarda um valor e aponta para dois filhos.
    private T valor;
    private NoArvore<T> esq;
    private NoArvore<T> dir;

    public NoArvore(T valor) {
        // O nó começa sem filhos.
        this.valor = valor;
        this.esq = null;
        this.dir = null;
    }

    // Os métodos abaixo consultam ou alteram os dados do nó.
    public T getValor() { return valor; }
    public void setValor(T valor) { this.valor = valor; }
    public NoArvore<T> getEsq() { return esq; }
    public void setEsq(NoArvore<T> esq) { this.esq = esq; }
    public NoArvore<T> getDir() { return dir; }
    public void setDir(NoArvore<T> dir) { this.dir = dir; }
}
