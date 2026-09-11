import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListaEncadeada<Contato> lista = null;

        System.out.println("Deseja criar uma lista ordenada?");
        System.out.println("1 - Sim (Ordenada por Nome)");
        System.out.println("2 - Não (Desordenada)");
        System.out.print("Opção: ");
        int opOrdenacao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Comparator<Contato> comparadorNome = (c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome());

        if (opOrdenacao == 1) {
            lista = new ListaEncadeada<>(true, comparadorNome);
        } else {
            lista = new ListaEncadeada<>(false, comparadorNome);
        }

        int opcao = 0;
        while (opcao != 7) {
            System.out.println("\n--- MENU DE CONTATOS ---");
            System.out.println("1. Carregar dados de arquivo");
            System.out.println("2. Adicionar contato");
            System.out.println("3. Pesquisar contato por nome");
            System.out.println("4. Pesquisar contato por telefone");
            System.out.println("5. Remover contato por telefone");
            System.out.println("6. Alterar dados de contato");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    carregarArquivo(lista);
                    break;
                case 2:
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o telefone: ");
                    String telefone = scanner.nextLine();

                    if (nome.isEmpty() || telefone.isEmpty()) {
                        System.out.println("Erro: Não é possível cadastrar um contato sem nome ou sem telefone.");
                    } else if (lista.pesquisar(new Contato("", telefone)) != null) {
                        System.out.println("Erro: Já existe um contato com esse telefone!");
                    } else {
                        lista.adicionar(new Contato(nome, telefone));
                        System.out.println("Contato adicionado com sucesso.");
                    }
                    break;
                case 3:
                    System.out.print("Digite o nome para busca: ");
                    String buscaNome = scanner.nextLine();

                    long inicioNome = System.nanoTime();

                    Contato encontradoNome = lista.pesquisar(new Contato(buscaNome, ""));

                    long fimNome = System.nanoTime();

                    if (encontradoNome != null) {
                        System.out.println("Telefone: " + encontradoNome.getTelefone());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimNome - inicioNome) + " nanosegundos.");
                    break;
                case 4:
                    System.out.print("Digite o telefone para busca: ");
                    String buscaTel = scanner.nextLine();

                    long inicioTel = System.nanoTime();

                    Contato encontradoTel = lista.pesquisar(new Contato("", buscaTel));

                    long fimTel = System.nanoTime();

                    if (encontradoTel != null) {
                        System.out.println("Nome: " + encontradoTel.getNome());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimTel - inicioTel) + " nanosegundos.");
                    break;
                case 5:
                    System.out.print("Digite o telefone para remover: ");
                    String remTel = scanner.nextLine();

                    long inicioRem = System.nanoTime();
                    boolean removido = lista.remover(new Contato("", remTel));
                    long fimRem = System.nanoTime();

                    if (removido) {
                        System.out.println("Contato excluído com sucesso!");
                    } else {
                        System.out.println("Contato não existia na lista.");
                    }
                    System.out.println("Tempo de remoção: " + (fimRem - inicioRem) + " nanosegundos.");
                    break;
                case 6:
                    System.out.print("Digite o nome do contato que deseja alterar: ");
                    String altNome = scanner.nextLine();
                    Contato alvo = lista.pesquisar(new Contato(altNome, ""));

                    if (alvo != null) {
                        System.out.println("Telefone atual: " + alvo.getTelefone());
                        System.out.print("Digite o NOVO nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Digite o NOVO telefone: ");
                        String novoTel = scanner.nextLine();

                        lista.remover(alvo);
                        lista.adicionar(new Contato(novoNome, novoTel));
                        System.out.println("Contato alterado com sucesso!");
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 7:
                    System.out.println("Encerrando o programa...");
                    System.out.println("Total de contatos na lista: " + lista.quantidadeNos());
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    // Método para ler o arquivo
    private static void carregarArquivo(ListaEncadeada<Contato> lista) {
        File arquivo = new File("entrada.txt");
        try {
            Scanner leitor = new Scanner(arquivo);
            long inicio = System.nanoTime();

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                String[] dados = linha.split(";"); // O formato fica a seu critério, usei ";"
                if (dados.length == 2) {
                    Contato c = new Contato(dados[0].trim(), dados[1].trim());
                    // Verifica duplicidade antes de inserir
                    if (lista.pesquisar(c) == null) {
                        lista.adicionar(c);
                    }
                }
            }
            long fim = System.nanoTime();
            leitor.close();

            System.out.println("Arquivo lido e lista montada com sucesso!");
            System.out.println("Tempo total gasto: " + (fim - inicio) / 1_000_000.0 + " ms");

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo 'entrada.txt' não encontrado na raiz do projeto.");
        }
    }
}