import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    @Override
    public boolean adicionar(T novoValor) {
        // Guardamos a quantidade para saber se a inserção aconteceu.
        int quantidadeAnterior = this.quant;
        this.raiz = adicionarAVL(this.raiz, novoValor);
        return this.quant > quantidadeAnterior;
    }

    // código do professor
    private NoArvore<T> adicionarAVL(NoArvore<T> no, T novoValor) {
        // Quando encontramos um espaço vazio, criamos o nó.
        if (no == null) {
            this.quant++;
            return new NoArvore<>(novoValor);
        }

        // A comparação escolhe o lado onde vamos inserir.
        int cmp = comparador.compare(novoValor, no.getValor());
        if (cmp < 0) {
            no.setEsq(adicionarAVL(no.getEsq(), novoValor));
        } else if (cmp > 0) {
            no.setDir(adicionarAVL(no.getDir(), novoValor));
        } else {
            return no; // Não inserimos valores repetidos.
        }

        // Depois da inserção, verificamos o balanceamento.
        return balancear(no);
    }

    @Override
    public boolean remover(T valor) {
        // Guardamos a quantidade para saber se houve remoção.
        int quantidadeAnterior = this.quant;
        this.raiz = removerAVL(this.raiz, valor);
        return this.quant < quantidadeAnterior;
    }

    private NoArvore<T> removerAVL(NoArvore<T> no, T valor) {
        // Se chegamos a um espaço vazio, não encontramos o valor.
        if (no == null) return null;

        // Procuramos o valor seguindo a ordenação da árvore.
        int cmp = comparador.compare(valor, no.getValor());
        if (cmp < 0) {
            no.setEsq(removerAVL(no.getEsq(), valor));
        } else if (cmp > 0) {
            no.setDir(removerAVL(no.getDir(), valor));
        } else {
            // Sem filho esquerdo, retornamos o filho direito.
            if (no.getEsq() == null) {
                this.quant--;
                return no.getDir();
            }

            // Sem filho direito, retornamos o filho esquerdo.
            if (no.getDir() == null) {
                this.quant--;
                return no.getEsq();
            }

            // Com dois filhos, usamos o menor valor do lado direito.
            NoArvore<T> menorDireita = no.getDir();
            while (menorDireita.getEsq() != null) {
                menorDireita = menorDireita.getEsq();
            }

            // Copiamos o valor e removemos o nó que foi substituído.
            no.setValor(menorDireita.getValor());
            no.setDir(removerAVL(no.getDir(), menorDireita.getValor()));
        }

        // Ao voltar da recursão, balanceamos os nós do caminho.
        return balancear(no);
    }

    private int alturaNo(NoArvore<T> no) {
        // Uma subárvore vazia tem altura -1.
        if (no == null) return -1;
        return no.getAltura();
    }

    private void atualizarAltura(NoArvore<T> no) {
        // A altura depende do filho mais alto.
        int he = alturaNo(no.getEsq());
        int hd = alturaNo(no.getDir());
        no.setAltura(1 + Math.max(he, hd));
    }

    private int fatorBalanceamento(NoArvore<T> no) {
        // Usamos a regra do professor: altura direita menos esquerda.
        if (no == null) return 0;
        return alturaNo(no.getDir()) - alturaNo(no.getEsq());
    }

    // código do professor
    private NoArvore<T> rotacaoDireita(NoArvore<T> y) {
        // O filho esquerdo sobe e o nó atual vai para a direita.
        NoArvore<T> x = y.getEsq();
        y.setEsq(x.getDir());
        x.setDir(y);

        // Atualizamos as alturas depois de trocar os filhos.
        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    // código do professor
    private NoArvore<T> rotacaoEsquerda(NoArvore<T> y) {
        // O filho direito sobe e o nó atual vai para a esquerda.
        NoArvore<T> x = y.getDir();
        y.setDir(x.getEsq());
        x.setEsq(y);

        // Atualizamos as alturas depois de trocar os filhos.
        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    // código do professor
    private NoArvore<T> rotacaoEsquerdaDireita(NoArvore<T> no) {
        // Primeiro giramos o filho à esquerda e depois a raiz à direita.
        no.setEsq(rotacaoEsquerda(no.getEsq()));
        return rotacaoDireita(no);
    }

    // código do professor
    private NoArvore<T> rotacaoDireitaEsquerda(NoArvore<T> no) {
        // Primeiro giramos o filho à direita e depois a raiz à esquerda.
        no.setDir(rotacaoDireita(no.getDir()));
        return rotacaoEsquerda(no);
    }

    // código do professor
    private NoArvore<T> balancear(NoArvore<T> no) {
        // Atualizamos a altura e calculamos o fator de balanceamento.
        atualizarAltura(no);
        int fb = fatorBalanceamento(no);

        // Se a esquerda ficou alta demais, giramos para a direita.
        if (fb < -1) {
            if (fatorBalanceamento(no.getEsq()) > 0) {
                return rotacaoEsquerdaDireita(no);
            }
            return rotacaoDireita(no);
        }

        // Se a direita ficou alta demais, giramos para a esquerda.
        if (fb > 1) {
            if (fatorBalanceamento(no.getDir()) < 0) {
                return rotacaoDireitaEsquerda(no);
            }
            return rotacaoEsquerda(no);
        }

        // Sem desequilíbrio, mantemos a raiz atual.
        return no;
    }
}
