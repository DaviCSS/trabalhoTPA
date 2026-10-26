import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GeradorContatos {
    private static final int[] TAMANHOS = {255, 511, 1023, 2047};

    // Nomes e sobrenomes utilizados para gerar os contatos.
    private static final String[] NOMES = {
        "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Isabela", "Joao",
        "Juliana", "Lucas", "Mariana", "Nathan", "Olivia", "Paulo", "Quesia", "Rafael", "Sofia", "Thiago",
        "Victor", "William", "Xavier", "Yasmin", "Zuleica", "Alfredo", "Beatriz", "Caio", "Denise", "Eliana",
        "Felipe", "Gustavo", "Heitor", "Igor", "Jessica", "Kevin", "Larissa", "Mateus", "Natalia", "Otavio",
        "Patricia", "Renato", "Sandra", "Tadeu", "Ursula", "Vinicius", "Wellington", "Zilda", "Adriana", "Benicio"
    };

    private static final String[] SOBRENOMES = {
        "Almeida", "Barbosa", "Campos", "Dias", "Evangelista", "Ferreira", "Gomes", "Henrique", "Iglesias", "Junqueira",
        "Klein", "Lima", "Medeiros", "Nascimento", "Oliveira", "Pereira", "Queiroz", "Rodrigues", "Silva", "Teixeira",
        "Uchoa", "Vasconcelos", "Watanabe", "Ximenes", "Yamamoto", "Zanetti", "Araujo", "Borges", "Coelho", "Dantas",
        "Esteves", "Farias", "Guimaraes", "Holanda", "Ivo", "Jardim", "Krieger", "Lacerda", "Monteiro", "Neves",
        "Porto", "Quintana", "Ramos", "Sanches", "Torrico", "Urbano", "Vieira", "Wanderley", "Yunes", "Zampieri"
    };

    public static void main(String[] args) {
        // Geramos dois arquivos para cada quantidade de contatos.
        for (int tamanho : TAMANHOS) {
            gerarOrdenado(tamanho);
            gerarBalanceado(tamanho);
        }
    }

    private static void gerarOrdenado(int tamanho) {
        String arquivo = "contatos_" + tamanho + "_ordenado.txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            // Os telefones são escritos em ordem crescente.
            for (int i = 1; i <= tamanho; i++) {
                escreverContato(escritor, i);
            }
            System.out.println("Arquivo gerado: " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo: " + e.getMessage());
        }
    }

    private static void gerarBalanceado(int tamanho) {
        String arquivo = "contatos_" + tamanho + "_balanceado.txt";
        ArrayList<Integer> ids = new ArrayList<>();

        // Montamos a ordem de inserção que forma uma árvore balanceada.
        gerarIdentificadoresBalanceados(ids, 1, tamanho);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            // Escrevemos os mesmos contatos em outra ordem.
            for (int id : ids) {
                escreverContato(escritor, id);
            }
            System.out.println("Arquivo gerado: " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo: " + e.getMessage());
        }
    }

    // código do professor
    private static void gerarIdentificadoresBalanceados(ArrayList<Integer> ids, int inicio, int fim) {
        // Colocamos o elemento central antes das duas metades.
        if (inicio > fim) return;
        int meio = (inicio + fim) / 2;
        ids.add(meio);
        gerarIdentificadoresBalanceados(ids, inicio, meio - 1);
        gerarIdentificadoresBalanceados(ids, meio + 1, fim);
    }

    private static void escreverContato(BufferedWriter escritor, int id) throws IOException {
        // O mesmo identificador sempre gera o mesmo nome e telefone.
        String nome = NOMES[(id - 1) % NOMES.length] + " " + SOBRENOMES[(id - 1) / NOMES.length];
        String telefone = String.format("279%08d", id);

        // Mantemos o formato do Trabalho 1: Nome;Telefone.
        escritor.write(nome + ";" + telefone);
        escritor.newLine();
    }
}
