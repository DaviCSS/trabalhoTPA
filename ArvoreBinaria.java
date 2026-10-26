import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

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
        // Guardamos a quantidade para saber se um nó foi inserido.
        int quantidadeAnterior = this.quant;
        this.raiz = adicionarRecursivo(this.raiz, novoValor);
        return this.quant > quantidadeAnterior;
    }

    // código do professor
    private NoArvore<T> adicionarRecursivo(NoArvore<T> no, T novoValor) {
        // Se encontramos um espaço vazio, criamos o nó.
        if (no == null) {
            this.quant++;
            return new NoArvore<>(novoValor);
        }

        // Comparamos o novo valor com o nó atual.
        int cmp = comparador.compare(novoValor, no.getValor());

        // Menores vão à esquerda, maiores vão à direita.
        if (cmp < 0) {
            no.setEsq(adicionarRecursivo(no.getEsq(), novoValor));
        } else if (cmp > 0) {
            no.setDir(adicionarRecursivo(no.getDir(), novoValor));
        }

        // Se for igual, não criamos outro nó.
        return no;
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisarRecursivo(this.raiz, valor);
    }

    // código do professor
    private T pesquisarRecursivo(NoArvore<T> no, T valor) {
        // Se chegamos ao fim, o valor não existe.
        if (no == null) return null;

        // A comparação indica qual caminho seguir.
        int cmp = comparador.compare(valor, no.getValor());
        if (cmp == 0) return no.getValor();
        if (cmp < 0) return pesquisarRecursivo(no.getEsq(), valor);
        return pesquisarRecursivo(no.getDir(), valor);
    }

    public T pesquisar(T valor, Comparator<T> outroComparador) {
        return pesquisarOutro(this.raiz, valor, outroComparador);
    }

    private T pesquisarOutro(NoArvore<T> no, T valor, Comparator<T> outroComparador) {
        // Essa busca usa outro critério, então confere os dois lados.
        if (no == null) return null;
        if (outroComparador.compare(valor, no.getValor()) == 0) return no.getValor();

        // Procuramos primeiro na esquerda.
        T encontrado = pesquisarOutro(no.getEsq(), valor, outroComparador);
        if (encontrado != null) return encontrado;

        // Se não achamos, procuramos na direita.
        return pesquisarOutro(no.getDir(), valor, outroComparador);
    }

    @Override
    public boolean remover(T valor) {
        // Guardamos a quantidade para saber se houve remoção.
        int quantidadeAnterior = this.quant;
        this.raiz = removerRecursivo(this.raiz, valor);
        return this.quant < quantidadeAnterior;
    }

    // código do professor
    private NoArvore<T> removerRecursivo(NoArvore<T> no, T valor) {
        // Se o nó não existe, não há nada para remover.
        if (no == null) return null;

        // Primeiro procuramos o valor pelo caminho correto.
        int cmp = comparador.compare(valor, no.getValor());
        if (cmp < 0) {
            no.setEsq(removerRecursivo(no.getEsq(), valor));
        } else if (cmp > 0) {
            no.setDir(removerRecursivo(no.getDir(), valor));
        } else {
            // Se não há filho esquerdo, retorna o direito.
            if (no.getEsq() == null) {
                this.quant--;
                return no.getDir();
            }

            // Se não há filho direito, retorna o esquerdo.
            if (no.getDir() == null) {
                this.quant--;
                return no.getEsq();
            }

            // Com dois filhos, buscamos o maior da subárvore esquerda.
            NoArvore<T> maiorEsquerda = no.getEsq();
            while (maiorEsquerda.getDir() != null) {
                maiorEsquerda = maiorEsquerda.getDir();
            }

            // Copiamos seu valor e removemos o nó antigo.
            no.setValor(maiorEsquerda.getValor());
            no.setEsq(removerRecursivo(no.getEsq(), maiorEsquerda.getValor()));
        }

        // Retornamos a raiz atualizada da subárvore.
        return no;
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
        // Uma árvore vazia tem altura -1.
        if (no == null) return -1;

        // A altura usa o lado que chega mais longe.
        return 1 + Math.max(calculaAltura(no.getEsq()), calculaAltura(no.getDir()));
    }

    @Override
    public String caminharEmNivel() {
        // A fila permite visitar todos os nós de cada nível.
        if (this.raiz == null) return "[]";
        StringBuilder resultado = new StringBuilder("[");
        Queue<NoArvore<T>> fila = new LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            // Guardamos quantos nós pertencem ao nível atual.
            int quantidadeNivel = fila.size();

            for (int i = 0; i < quantidadeNivel; i++) {
                NoArvore<T> atual = fila.remove();
                if (i > 0) resultado.append(", ");
                resultado.append(atual.getValor());

                // Os filhos ficam na fila para o próximo nível.
                if (atual.getEsq() != null) fila.add(atual.getEsq());
                if (atual.getDir() != null) fila.add(atual.getDir());
            }

            // Se houver mais nós, começamos uma nova linha.
            if (!fila.isEmpty()) resultado.append("\n");
        }

        return resultado.append("]").toString();
    }

    @Override
    public String caminharEmOrdem() {
        StringBuilder resultado = new StringBuilder("[");
        montarEmOrdem(this.raiz, resultado);
        return resultado.append("]").toString();
    }

    // código do professor
    private void montarEmOrdem(NoArvore<T> no, StringBuilder resultado) {
        // Primeiro percorremos a esquerda.
        if (no == null) return;
        montarEmOrdem(no.getEsq(), resultado);

        // Depois adicionamos o nó atual.
        if (resultado.length() > 1) resultado.append(", ");
        resultado.append(no.getValor());

        // Por último percorremos a direita.
        montarEmOrdem(no.getDir(), resultado);
    }
}
