import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Cria o leitor de entradas do usuário pelo teclado
        IColecao<Contato> lista = null; // A lista que vai guardar todos os contatos (começa vazia)

        // Pergunta ao usuário se ele quer a lista em ordem alfabética ou não
        System.out.println("Deseja criar uma lista ordenada?");
        System.out.println("1 - Sim (Ordenada por Nome)");
        System.out.println("2 - Não (Desordenada)");
        System.out.print("Opção: ");
        int opOrdenacao = scanner.nextInt();
        scanner.nextLine(); // Limpa o "Enter" que sobrou no buffer após o nextInt()

        // Esse comparador ensina a lista como comparar dois contatos:
        // ele compara os nomes ignorando se é maiúscula ou minúscula
        Comparator<Contato> comparadorNome = (c1, c2) -> c1.getNome().compareToIgnoreCase(c2.getNome());

        // Cria a lista de acordo com a escolha do usuário
        if (opOrdenacao == 1) {
            lista = new ListaEncadeada<>(comparadorNome, true); // Ordenada
        } else {
            lista = new ListaEncadeada<>(comparadorNome, false); // Não ordenada
        }

        int opcao = 0;
        // O menu fica rodando em loop até o usuário escolher a opção 7 (Sair)
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
            scanner.nextLine(); // Limpa o buffer após ler o número

            // Analisa qual opção foi escolhida e executa a ação correspondente
            switch (opcao) {

                case 1:
                    // Chama o método que lê os contatos de um arquivo de texto
                    carregarArquivo(lista);
                    break;

                case 2:
                    // Pede os dados do novo contato ao usuário
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
                        // Tudo certo: adiciona o contato na lista
                        lista.adicionar(new Contato(nome, telefone));
                        System.out.println("Contato adicionado com sucesso.");
                    }
                    break;

                case 3:
                    // Busca um contato pelo NOME e mede quanto tempo levou a busca
                    System.out.print("Digite o nome para busca: ");
                    String buscaNome = scanner.nextLine();

                    long inicioNome = System.nanoTime(); // Marca o tempo de início

                    // Pesquisa usando um Contato com nome preenchido e telefone vazio
                    // (o equals() da classe Contato sabe que deve comparar pelo nome nesse caso)
                    Contato encontradoNome = lista.pesquisar(new Contato(buscaNome, ""));

                    long fimNome = System.nanoTime(); // Marca o tempo de fim

                    if (encontradoNome != null) {
                        System.out.println("Telefone: " + encontradoNome.getTelefone());
                    } else {
                        System.out.println("Contato não existe.");
                    }
                    System.out.println("Tempo de busca: " + (fimNome - inicioNome) + " nanosegundos.");
                    break;

                case 4:
                    // Busca um contato pelo TELEFONE e mede o tempo da busca
                    System.out.print("Digite o telefone para busca: ");
                    String buscaTel = scanner.nextLine();

                    long inicioTel = System.nanoTime(); // Marca o tempo de início

                    // Pesquisa usando um Contato com telefone preenchido e nome vazio
                    // (o equals() vai comparar pelo telefone nesse caso)
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
                    // Remove um contato pelo telefone e mede o tempo da operação
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
                    // Altera os dados de um contato existente
                    // A estratégia aqui é: remove o contato antigo e adiciona um novo com os dados
                    // atualizados
                    System.out.print("Digite o nome do contato que deseja alterar: ");
                    String altNome = scanner.nextLine();

                    // Busca o contato pelo nome (telefone vazio para o equals do Contato comparar
                    // pelo nome)
                    Contato alvo = lista.pesquisar(new Contato(altNome, ""));

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

                            // Se achou alguém com esse telefone E esse telefone NÃO É o do próprio contato
                            // que estamos editando agora, então é uma duplicata inválida.
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
                    // Encerra o programa e mostra quantos contatos ficaram na lista
                    System.out.println("Encerrando o programa...");
                    System.out.println("Total de contatos na lista: " + lista.quantidadeNos());
                    break;

                default:
                    // Qualquer número fora do menu cai aqui
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close(); // Fecha o scanner ao terminar (boa prática!)
    }

    // Método para ler o arquivo
    // Lê um arquivo chamado "entrada.txt" e carrega os contatos na lista.
    // Cada linha do arquivo deve ter o formato: Nome;Telefone
    private static void carregarArquivo(IColecao<Contato> lista) {
        File arquivo = new File("entrada.txt"); // Procura o arquivo na pasta do projeto
        try {
            Scanner leitor = new Scanner(arquivo);
            long inicio = System.nanoTime(); // Marca o tempo de início da leitura

            while (leitor.hasNextLine()) { // Enquanto houver linhas no arquivo...
                String linha = leitor.nextLine();
                String[] dados = linha.split(";"); // O formato fica a seu critério, usei ";"
                if (dados.length == 2) { // A linha tem exatamente 2 partes (nome e telefone)?
                    Contato c = new Contato(dados[0].trim(), dados[1].trim()); // Cria o contato (trim() remove espaços
                                                                               // extras)
                    // Verifica duplicidade antes de inserir
                    if (lista.pesquisar(c) == null) { // Só adiciona se o contato ainda não existir
                        lista.adicionar(c);
                    }
                }
            }
            long fim = System.nanoTime(); // Marca o tempo de fim da leitura
            leitor.close(); // Fecha o arquivo (boa prática!)

            System.out.println("Arquivo lido e lista montada com sucesso!");
            System.out.println("Tempo total gasto: " + (fim - inicio) / 1_000_000.0 + " ms"); // Converte de
                                                                                              // nanosegundos para
                                                                                              // milissegundos

        } catch (FileNotFoundException e) {
            // Se o arquivo não for encontrado, avisa o usuário
            System.out.println("Arquivo 'entrada.txt' não encontrado na raiz do projeto.");
        }
    }
}