package testes;

import estruturas.FilaEncadeada;
import estruturas.ListaEncadeada;

public class TesteEstruturas {
    private static void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }

    public static void main(String[] args) {
        ListaEncadeada<String> lista = new ListaEncadeada<>(null, false);
        verificar(lista.estaVazia(), "Lista deve iniciar vazia");
        lista.adicionar("A");
        lista.adicionar("B");
        lista.adicionar("C");

        StringBuilder percurso = new StringBuilder();
        for (String item : lista) {
            percurso.append(item);
        }
        verificar("ABC".contentEquals(percurso), "Iterador deve respeitar ordem de inserção");
        verificar(lista.quantidadeNos() == 3, "Quantidade incorreta");
        verificar(lista.remover("B"), "Deve remover o elemento B");
        verificar(lista.pesquisar("B") == null, "B não deve existir após remoção");

        FilaEncadeada<String> fila = new FilaEncadeada<>();
        verificar(fila.estaVazia(), "Fila deve iniciar vazia");
        fila.enfileirar("A");
        fila.enfileirar("B");
        fila.enfileirar("C");
        verificar(fila.tamanho() == 3, "Tamanho da fila incorreto");
        verificar("A".equals(fila.desenfileirar()), "Ordem FIFO: A");
        verificar("B".equals(fila.desenfileirar()), "Ordem FIFO: B");
        fila.enfileirar("D");
        verificar("C".equals(fila.desenfileirar()), "Ordem FIFO: C");
        verificar("D".equals(fila.desenfileirar()), "Ordem FIFO: D");
        verificar(fila.estaVazia(), "Fila deve estar vazia ao final");

        System.out.println("OK: testes das estruturas concluídos.");
    }
}
