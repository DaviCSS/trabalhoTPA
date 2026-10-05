public class NoArvore<T> {
    private T valor;
    private NoArvore<T> esq;
    private NoArvore<T> dir;

    public NoArvore(T valor) {
        this.valor = valor;
        this.esq = null;
        this.dir = null;
    }

    public T getValor() { return valor; }
    public void setValor(T valor) { this.valor = valor; }
    public NoArvore<T> getEsq() { return esq; }
    public void setEsq(NoArvore<T> esq) { this.esq = esq; }
    public NoArvore<T> getDir() { return dir; }
    public void setDir(NoArvore<T> dir) { this.dir = dir; }
}