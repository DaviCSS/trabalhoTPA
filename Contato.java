// Essa classe representa um contato da agenda telefônica.
// Ela guarda o nome e o telefone de uma pessoa.
public class Contato {
    private String nome;     // O nome do contato
    private String telefone; // O telefone do contato

    // Construtor: cria um novo contato com nome e telefone
    public Contato(String nome, String telefone) {
        this.nome = nome;         // Salva o nome
        this.telefone = telefone; // Salva o telefone
    }

    // Retorna o nome do contato
    public String getNome() {
        return nome;
    }

    // Permite alterar o nome do contato
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o telefone do contato
    public String getTelefone() {
        return telefone;
    }

    // Permite alterar o telefone do contato
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Esse método define como o contato aparece quando a gente faz um System.out.println()
    // Vai mostrar algo como: "João - (11) 99999-9999"
    @Override
    public String toString() {
        return this.nome + " - " + this.telefone;
    }

    // Esse método serve para comparar se dois contatos são iguais.
    // O Java usa ele por baixo dos panos quando chamamos o .equals()
    @Override
    public boolean equals(Object obj) {
        // Se eu estou comparando o contato com ele mesmo, é óbvio que são iguais
        if (this == obj)
            return true;

        // Se o objeto passado for nulo ou for de uma classe diferente, não são iguais
        if (obj == null || getClass() != obj.getClass())
            return false;

        // Converte o objeto genérico para um Contato para conseguir acessar os atributos
        Contato outro = (Contato) obj;

        // Caso especial: se o telefone do "outro" estiver vazio,
        // a comparação é feita pelo NOME (ignora maiúsculas/minúsculas)
        if (outro.telefone.isEmpty()) {
            return this.nome.equalsIgnoreCase(outro.nome);
        }

        // Caso padrão: compara pelo TELEFONE
        return this.telefone.equals(outro.telefone);
    }

}
