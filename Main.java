import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IColecao<Contato> lista = null;

        System.out.println("Deseja criar qual estrutura?");
        System.out.println("1 - Lista Encadeada Ordenada (Nome)");
        System.out.println("2 - Lista Encadeada Desordenada");
        System.out.println("3 - Árvore Binária (Indexada por Telefone)");
        System.out.println("4 - Árvore AVL (Indexada por Telefone)");
        System.out.print("Opção: ");
        int opEstrutura = scanner.nextInt();
        scanner.nextLine();

        Comparator<Contato> comparadorNome = (c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome());
        Comparator<Contato> comparadorTelefone = (c1, c2) -> c1.getTelefone().compareToIgnoreCase(c2.getTelefone());

        if (opEstrutura == 1) {
            lista = new ListaEncadeada<>(comparadorNome, true);
        } else if (opEstrutura == 2) {
            lista = new ListaEncadeada<>(comparadorNome, false);
        } else if (opEstrutura == 3) {
            lista = new ArvoreBinaria<>(comparadorTelefone);
        } else if (opEstrutura == 4) {
            lista = new ArvoreAVL<>(comparadorTelefone);
        } else {
            // Uma opção inválida não cria nenhuma estrutura.
            System.out.println("Opção inválida!");
            scanner.close();
            return;
        }

        int opcao = 0;
        // O menu continua até o usuário escolher sair.
        while (opcao != 7) {
            System.out.println("\n--- MENU DE CONTATOS ---");
            System.out.println("1. Carregar dados de arquivo");
            System.out.println("2. Adicionar contato");
            System.out.println("3. Pesquisar contato por nome");
            System.out.println("4. Pesquisar contato por telefone");
            System.out.println("5. Remover contato por telefone");
            System.out.println("6. Alterar dados de contato");
            System.out.println("7. Sair");
            if (lista instanceof ArvoreBinaria) {
                System.out.println("8. Ver altura da árvore");
            }
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer após ler o número

            // Executa a opção escolhida.
            switch (opcao) {

                case 1:
                    // Carrega os contatos do arquivo.
                    carregarArquivo(lista);
                    break;

                case 2:
                    // Pede o nome e o telefone do novo contato.
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o telefone: ");
                    String telefone = scanner.nextLine();

                    if (nome.isEmpty() || telefone.isEmpty()) {
                        // Não aceita contato sem nome ou sem telefone
                        System.out.println("Erro: Não é possível cadastrar um contato sem nome ou sem telefone.");
                    } else if (lista.pesquisar(new Contato("", telefone)) != null) {
                        // Verifica se já existe um contato com esse telefone (evita duplicata)
                        System.out.println("Erro: Já existe um contato com esse telefone!");
                    } else {
                        // Adiciona o contato na estrutura escolhida.
                        lista.adicionar(new Contato(nome, telefone));
                        System.out.println("Contato adicionado com sucesso.");
                    }
                    break;

                case 3:
                    // Pesquisa pelo nome e mede o tempo da busca.
                    System.out.print("Digite o nome para busca: ");
                    String buscaNome = scanner.nextLine();

                    long inicioNome = System.nanoTime(); // Marca o tempo de início

                    // Usa o nome informado para encontrar o contato.
                    Contato encontradoNome = pesquisarPorNome(lista, comparadorNome, new Contato(buscaNome, ""));

                    long fimNome = System.nanoTime(); // Marca o tempo de fim

                    if (encontradoNome != null) {
                        System.out.println("Telefone: " + encontradoNome.getTelefone());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimNome - inicioNome) + " nanosegundos.");
                    break;

                case 4:
                    // Pesquisa pelo telefone e mede o tempo da busca.
                    System.out.print("Digite o telefone para busca: ");
                    String buscaTel = scanner.nextLine();

                    long inicioTel = System.nanoTime(); // Marca o tempo de início

                    // A estrutura procura pelo telefone informado.
                    Contato encontradoTel = lista.pesquisar(new Contato("", buscaTel));

                    long fimTel = System.nanoTime(); // Marca o tempo de fim

                    if (encontradoTel != null) {
                        System.out.println("Nome: " + encontradoTel.getNome());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimTel - inicioTel) + " nanosegundos.");
                    break;

                case 5:
                    // Remove pelo telefone e mede o tempo da operação.
                    System.out.print("Digite o telefone para remover: ");
                    String remTel = scanner.nextLine();

                    long inicioRem = System.nanoTime(); // Marca o tempo de início
                    boolean removido = lista.remover(new Contato("", remTel)); // Tenta remover
                    long fimRem = System.nanoTime(); // Marca o tempo de fim

                    if (removido) {
                        System.out.println("Contato excluído com sucesso!");
                    } else {
                        System.out.println("Contato não existia na lista.");
                    }
                    System.out.println("Tempo de remoção: " + (fimRem - inicioRem) + " nanosegundos.");
                    break;

                case 6:
                    // Atualiza o contato removendo o antigo e adicionando o novo.
                    System.out.print("Digite o nome do contato que deseja alterar: ");
                    String altNome = scanner.nextLine();

                    // Primeiro procura o contato pelo nome.
                    Contato alvo = pesquisarPorNome(lista, comparadorNome, new Contato(altNome, ""));

                    if (alvo != null) {
                        System.out.println("Telefone atual: " + alvo.getTelefone());
                        System.out.print("Digite o NOVO nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Digite o NOVO telefone: ");
                        String novoTel = scanner.nextLine();

                        // Evita que o usuário deixe os campos em branco na alteração
                        if (novoNome.trim().isEmpty() || novoTel.trim().isEmpty()) {
                            System.out.println("Erro: Não é possível deixar o contato sem nome ou sem telefone.");
                        } else {
                            // Verifica se o novo telefone digitado já existe na lista
                            Contato donoDoTelefone = lista.pesquisar(new Contato("", novoTel));

                            // Não permite usar o telefone de outro contato.
                            if (donoDoTelefone != null && !novoTel.equals(alvo.getTelefone())) {
                                System.out.println("Erro: Já existe outro contato com esse telefone!");
                            } else {
                                lista.remover(alvo); // Remove o contato antigo
                                lista.adicionar(new Contato(novoNome, novoTel)); // Adiciona com os novos dados
                                System.out.println("Contato alterado com sucesso!");
                            }
                        }
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 7:
                    // Mostra quantos contatos ficaram na estrutura.
                    System.out.println("Encerrando o programa...");
                    System.out.println("Total de contatos na lista: " + lista.quantidadeNos());
                    break;

                case 8:
                    // Só mostra a altura se a estrutura for uma árvore.
                    if (lista instanceof ArvoreBinaria) {
                        System.out.println("Altura da árvore: " + ((ArvoreBinaria<Contato>) lista).altura());
                    } else {
                        System.out.println("Opção inválida!");
                    }
                    break;

                default:
                    // Qualquer número fora do menu cai aqui
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close(); // Fecha o scanner ao terminar (boa prática!)
    }

    // Na árvore, pesquisa o nome em todos os nós.
    // Nas listas, usa a pesquisa normal.
    private static Contato pesquisarPorNome(IColecao<Contato> lista, Comparator<Contato> comparadorNome, Contato chave) {
        if (lista instanceof ArvoreBinaria) {
            return ((ArvoreBinaria<Contato>) lista).pesquisar(chave, comparadorNome);
        }
        return lista.pesquisar(chave);
    }

    // Carrega os contatos do arquivo entrada.txt.
    // Cada linha deve ter o formato Nome;Telefone.
    private static void carregarArquivo(IColecao<Contato> lista) {
        File arquivo = new File("entrada.txt"); // Procura o arquivo na pasta do projeto
        try {
            Scanner leitor = new Scanner(arquivo);
            long inicio = System.nanoTime(); // Marca o tempo de início da leitura

            while (leitor.hasNextLine()) { // Enquanto houver linhas no arquivo...
                String linha = leitor.nextLine();
                String[] dados = linha.split(";"); // Separa o nome e o telefone.
                if (dados.length == 2) { // Confere se temos nome e telefone.
                    // Cria o contato retirando os espaços extras.
                    Contato c = new Contato(dados[0].trim(), dados[1].trim());
                    // Só insere se o contato ainda não existir.
                    if (lista.pesquisar(c) == null) {
                        lista.adicionar(c);
                    }
                }
            }
            long fim = System.nanoTime(); // Marca o tempo de fim da leitura
            leitor.close(); // Fecha o arquivo (boa prática!)

            System.out.println("Arquivo lido e lista montada com sucesso!");
            // Mostra o tempo convertido para milissegundos.
            System.out.println("Tempo total gasto: " + (fim - inicio) / 1_000_000.0 + " ms");

        } catch (FileNotFoundException e) {
            // Se o arquivo não for encontrado, avisa o usuário
            System.out.println("Arquivo 'entrada.txt' não encontrado na raiz do projeto.");
        }
    }
}