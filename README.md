# Trabalhos 1 e 2 — Técnicas de Programação e Análise de Algoritmos

Este repositório reúne os dois trabalhos desenvolvidos em Java para a disciplina de Técnicas de Programação e Análise de Algoritmos (TPA). O projeto utiliza uma **agenda de contatos** para aplicar estruturas de dados genéricas e analisar o desempenho das operações de inserção, pesquisa e remoção.

- **Trabalho 1 — Listas Encadeadas:** implementação de listas genéricas ordenadas e desordenadas.
- **Trabalho 2 — Árvores Binárias e AVL:** implementação de árvores binárias de busca, árvores AVL e comparação de complexidade entre diferentes estruturas e formatos de árvore.

A agenda do Trabalho 1 foi aproveitada no Trabalho 2. As classes das listas continuam disponíveis, e o programa principal permite escolher a estrutura utilizada.

## Objetivos

### Trabalho 1 — Listas Encadeadas

- Implementar uma lista encadeada genérica em Java, utilizando **Generics**.
- Permitir listas ordenadas ou desordenadas, definidas no construtor por um `Comparator<T>` e um valor booleano.
- Implementar a interface `IColecao<T>`, com os métodos `adicionar`, `pesquisar`, `remover` e `quantidadeNos`.
- Utilizar uma agenda de contatos com nome e telefone, evitando o cadastro de telefones duplicados.
- Analisar o tempo de carregamento, pesquisa e remoção com diferentes volumes de dados.

### Trabalho 2 — Árvores Binárias e AVL

- Implementar a classe genérica `ArvoreBinaria<T>`, herdando de `ArvoreBinariaBase<T>` e utilizando `Comparator<T>` para definir a indexação.
- Implementar os métodos de inserção, pesquisa, remoção, contagem de nós, altura e caminhamentos em nível e em ordem.
- Adaptar a agenda para permitir a escolha de uma árvore binária, além das listas do Trabalho 1.
- Implementar `ArvoreAVL<T>`, herdando de `ArvoreBinaria<T>`, com inserção e remoção balanceadas por rotações.
- Comparar o desempenho de árvores binárias degeneradas, árvores binárias perfeitamente balanceadas e árvores AVL.
- Analisar matematicamente a complexidade dos algoritmos e pesquisar implementações de árvores disponíveis na biblioteca padrão de Java.

## Organização dos arquivos

Os arquivos Java ficam na raiz do repositório e não utilizam pacotes.

| Arquivo | Descrição |
|---|---|
| `Main.java` | Programa principal: menu da agenda, operações com contatos, leitura do arquivo e medição de tempos. |
| `Contato.java` | Representa um contato, com nome e telefone. |
| `IColecao.java` | Interface compartilhada pelas listas e árvores, com as operações básicas. |
| `ListaEncadeada.java` | Implementação genérica das listas ordenadas e desordenadas do Trabalho 1. |
| `No.java` | Nó utilizado pela lista encadeada. |
| `ArvoreBinariaBase.java` | Classe abstrata fornecida pelo professor, com o comparador e os métodos obrigatórios das árvores. |
| `ArvoreBinaria.java` | Árvore binária de busca genérica, com operações recursivas, cálculo de altura e caminhamentos. |
| `NoArvore.java` | Nó utilizado nas árvores, com valor, filhos esquerdo e direito e altura para a AVL. |
| `ArvoreAVL.java` | Árvore AVL com inserção, remoção, cálculo do fator de balanceamento e rotações. |
| `GeradorContatos.java` | Gera arquivos com contatos em ordem crescente ou em uma ordem de inserção que produz uma árvore binária balanceada. |

O relatório do Trabalho 1 também está disponível no repositório, no arquivo `Relatório de Implementação de Listas Genéricas e Análise de Complexidade de Listas.pdf`.

## Funcionamento da agenda

Ao iniciar `Main.java`, o usuário escolhe a estrutura que armazenará os contatos:

```text
1 - Lista Encadeada Ordenada (Nome)
2 - Lista Encadeada Desordenada
3 - Árvore Binária (Indexada por Telefone)
4 - Árvore AVL (Indexada por Telefone)
```

Em seguida, o menu permite:

1. Carregar contatos do arquivo `entrada.txt`.
2. Adicionar um contato manualmente.
3. Pesquisar um contato pelo nome.
4. Pesquisar um contato pelo telefone.
5. Remover um contato pelo telefone.
6. Alterar os dados de um contato existente.
7. Sair e mostrar a quantidade de contatos.
8. Consultar a altura da árvore (disponível quando uma árvore binária ou AVL é escolhida).

**Formato dos contatos:** o mesmo arquivo funciona para listas e árvores. Cada linha deve conter um nome e um telefone separados por ponto e vírgula:

```text
Ana Almeida;27900000001
Bruno Almeida;27900000002
Carlos Almeida;27900000003
```

O programa verifica se o telefone já existe antes de inserir um contato. A leitura do arquivo também preserva essa checagem de duplicidade.

### Critérios de pesquisa

- **Listas:** a lista ordenada utiliza o nome como critério de organização; as pesquisas da agenda percorrem os nós usando a comparação de `Contato`.
- **Árvore binária e AVL:** os nós são indexados pelo **telefone**, usando um `Comparator<Contato>`.
- **Pesquisa por nome nas árvores:** como os nós estão organizados por telefone, a busca por nome precisa percorrer as subárvores até encontrar o contato. Por isso, ela não tem a mesma vantagem da busca pelo telefone.

## Implementação das árvores — Trabalho 2

### Árvore binária de busca

A classe `ArvoreBinaria<T>` herda de `ArvoreBinariaBase<T>` e implementa `IColecao<T>` por meio dessa classe-base. O comparador recebido no construtor define a ordenação dos elementos.

A implementação inclui:

- `adicionar`: insere um valor respeitando a ordem definida pelo comparador e não insere chaves repetidas;
- `pesquisar`: encontra um valor pelo critério de indexação;
- `remover`: remove um nó, tratando os casos de zero, um ou dois filhos;
- `quantidadeNos`: retorna a quantidade de elementos armazenados;
- `altura`: calcula a maior distância da raiz até uma folha (árvore vazia tem altura `-1`);
- `caminharEmNivel`: percorre a árvore por níveis;
- `caminharEmOrdem`: percorre os nós na sequência esquerda, raiz e direita.

### Árvore AVL

A classe `ArvoreAVL<T>` herda de `ArvoreBinaria<T>` e sobrescreve `adicionar` e `remover` para manter a árvore balanceada após essas operações.

A AVL armazena a altura em cada nó e utiliza o fator de balanceamento:

```text
FB = altura do filho direito - altura do filho esquerdo
```

Quando necessário, realiza rotações à esquerda, à direita, esquerda-direita ou direita-esquerda. Os métodos herdados de pesquisa, caminhamento e contagem continuam disponíveis.

### Complexidade esperada

Os valores abaixo se referem às operações **individuais na estrutura**, sem incluir leitura de arquivos nem a checagem de duplicidade feita pela agenda.

| Estrutura / situação | Adicionar | Pesquisar pela chave | Remover | Quantidade de nós |
|---|---|---|---|---|
| Árvore binária degenerada | `O(n)` | `O(n)` | `O(n)` | `O(1)` |
| Árvore binária perfeitamente balanceada | `O(log n)` | `O(log n)` | `O(log n)` | `O(1)` |
| Árvore AVL | `O(log n)` | `O(log n)` | `O(log n)` | `O(1)` |

Para as árvores indexadas por telefone, uma **pesquisa por nome** pode exigir `O(n)`, já que o nome não é a chave de organização.

## Geração dos arquivos de teste — Trabalho 2

O arquivo `GeradorContatos.java` foi adaptado da estratégia de geração de dados apresentada pelo professor Victorio Albani de Carvalho em [GeradorArquivos](https://github.com/victoriocarvalho/GeradorArquivos).

O gerador produz **dois arquivos para cada tamanho**, sempre com os mesmos contatos e no formato `Nome;Telefone`:

- **Ordenado:** telefones em ordem crescente. A inserção em uma árvore binária comum produz uma árvore degenerada.
- **Balanceado:** contatos inseridos começando pelo elemento central de cada intervalo e seguindo recursivamente pelas duas metades. A inserção em uma árvore binária comum produz uma árvore perfeitamente balanceada.

São utilizados quatro tamanhos:

| Quantidade de contatos | Arquivo ordenado | Arquivo balanceado |
|---:|---|---|
| 8.191 | `contatos_8191_ordenado.txt` | `contatos_8191_balanceado.txt` |
| 16.383 | `contatos_16383_ordenado.txt` | `contatos_16383_balanceado.txt` |
| 32.767 | `contatos_32767_ordenado.txt` | `contatos_32767_balanceado.txt` |
| 65.535 | `contatos_65535_ordenado.txt` | `contatos_65535_balanceado.txt` |

Os arquivos balanceados são utilizados para avaliar a árvore binária comum com menor altura. Os ordenados são utilizados para comparar a árvore binária degenerada com a AVL.

## Como executar

### 1. Preparar o ambiente

É necessário ter um **JDK** instalado, com os comandos `javac` e `java` disponíveis no terminal.

Clone o repositório e entre na pasta:

```bash
git clone https://github.com/DaviCSS/trabalhoTPA.git
cd trabalhoTPA
```

Compile os arquivos Java:

```bash
javac *.java
```

### 2. Executar a agenda

```bash
java Main
```

Escolha uma das quatro estruturas e utilize as opções do menu.

Para carregar contatos, coloque o arquivo `entrada.txt` na pasta em que o programa será executado.

### 3. Gerar os arquivos de teste

Depois de compilar, execute:

```bash
java GeradorContatos
```

O gerador criará os oito arquivos `.txt` indicados na tabela anterior, na pasta de execução.

### 4. Testar um arquivo

O programa `Main` lê especificamente o arquivo `entrada.txt`. Portanto, antes de um teste, faça uma cópia do arquivo desejado com esse nome.

**Windows (Prompt de Comando):**

```bat
copy contatos_8191_ordenado.txt entrada.txt
java Main
```

**Linux/macOS:**

```bash
cp contatos_8191_ordenado.txt entrada.txt
java Main
```

Selecione a árvore binária ou AVL, escolha **1 - Carregar dados de arquivo** e execute as pesquisas e a remoção desejadas.

### 5. Cuidados com árvores degeneradas

Na árvore binária comum, os arquivos ordenados formam uma cadeia muito profunda. Como as operações foram implementadas de forma recursiva, testes com dezenas de milhares de nós podem ocasionar `StackOverflowError` na configuração padrão do Java.

Para reproduzir os experimentos com uma pilha maior, pode ser necessário iniciar o programa assim:

```bash
java -Xss96m Main
```

Esse parâmetro aumenta a pilha da JVM, sem modificar os algoritmos. A disponibilidade de memória e o limite necessário dependem do computador utilizado.

## Análise empírica

O programa utiliza `System.nanoTime()` para medir as operações.

- **Carregamento:** o menu exibe o tempo total em milissegundos; essa medição inclui a leitura do arquivo e a checagem de duplicidade antes de cada inserção.
- **Pesquisa por telefone:** busca por um contato utilizando a chave de indexação das árvores.
- **Pesquisa por nome:** busca pelo nome do mesmo contato, exigindo percorrer as subárvores quando a estrutura é indexada por telefone.
- **Remoção por telefone:** remove o contato após as pesquisas.

No Trabalho 2, a análise empírica compara árvores degeneradas, árvores balanceadas e AVL nos quatro tamanhos gerados, além de permitir uma comparação com as listas do Trabalho 1.

As medições detalhadas, os gráficos e o relatório específico do Trabalho 2 deverão ser disponibilizados no repositório junto à entrega final, conforme as orientações da disciplina.

## Referências

- [Biblioteca-base de árvores e interface utilizadas na disciplina](https://github.com/victoriocarvalho/tpa_bsi)
- [Geradores de arquivos do professor](https://github.com/victoriocarvalho/GeradorArquivos)

## Integrantes do projeto

| Integrantes |
|---|
| Alice Lourenço dos Reis |
| Davi Campos Sutil |
| Karyna Martins Carbas |
