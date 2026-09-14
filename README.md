# Trabalho 1 – Análise de Complexidade em Estruturas de Listas

Este repositório contém a implementação prática e a análise de complexidade de uma biblioteca customizada de listas genéricas em Java. O projeto foi desenvolvido como requisito de avaliação, focando na aplicação de conceitos de estruturas de dados e programação orientada a objetos, além da avaliação empírica de complexidade de algoritmos.

## Objetivos do Projeto

* **Desenvolvimento de Biblioteca:** Implementar uma lista encadeada genérica em Java utilizando Generics (`<T>`).
* **Flexibilidade de Ordenação:** Garantir que a lista suporte comportamentos ordenados e não ordenados, configurados no momento de sua instanciação através de um `Comparator<T>` e um parâmetro booleano.
* **Implementação de Interface:** Seguir estritamente o contrato da interface `IColecao`, implementando os métodos `adicionar`, `pesquisar`, `remover` e `quantidadeNos`.
* **Aplicação Prática:** Construir um programa de agenda de contatos para validar a biblioteca, gerenciando entidades com nome e telefone e aplicando a regra de negócio que impede a duplicidade de números.
* **Análise de Desempenho:** Mensurar o tempo de execução (em nanossegundos e milissegundos) para operações de carga de arquivo, busca e remoção, viabilizando a análise de complexidade teórica e empírica.

## Estrutura de Arquivos

O projeto está organizado com as seguintes classes principais:

* `Contato.java`: Classe de domínio que representa uma pessoa na agenda, contendo os atributos nome e telefone. Possui lógica customizada no método `equals` para permitir a comparação pelo nome ou pelo telefone, dependendo do campo que estiver preenchido.
* `IColecao.java`: Interface obrigatória que estabelece os métodos fundamentais que a estrutura de dados fornece.
* `ListaEncadeada.java`: A estrutura principal do projeto, responsável por interligar os elementos e gerenciar a adição, busca e remoção de forma sequencial ou ordenada (via busca da posição correta de inserção).
* `No.java`: Classe que atua como o contêiner de cada elemento (`T`) da lista, encapsulando o valor e o ponteiro para o próximo nó.
* `Main.java`: O programa que apresenta o menu interativo no console, processa as entradas do usuário, executa a leitura do arquivo `entrada.txt` e contabiliza o tempo de execução de cada operação.

## Funcionalidades da Agenda

Através do menu interativo no console, o sistema permite:
1. **Carregar dados de arquivo:** Realiza a leitura do arquivo `entrada.txt` (esperando o formato `Nome;Telefone` em cada linha) e exibe o tempo total gasto na montagem da estrutura de dados.
2. **Adicionar contato:** Insere novos registros manualmente no sistema, recusando a inserção caso campos estejam vazios ou o telefone já esteja cadastrado.
3. **Pesquisar contato:** Realiza a busca pelo registro por nome ou por telefone, e imprime na tela a métrica de tempo, em nanossegundos, exigida pela operação de varredura.
4. **Remover contato:** Busca e exclui um registro específico através do número de telefone, exibindo também o tempo gasto na finalização do processo.
5. **Alterar dados:** Atualiza os dados de um contato existente através de um ciclo de remoção do registro antigo e inserção do atualizado, mantendo as regras de validação de duplicidade.

## Análise Empírica (Testes de Mesa)

O sistema foi estruturado com marcadores temporais (`System.nanoTime()`) para extrair métricas de desempenho. O fluxo de validação exige que o programa seja alimentado com arquivos de texto de volumes variados (ex: 100.000, 200.000, 400.000 contatos). Isso permite gerar as tabelas e gráficos necessários para comparar empiricamente a ordem de complexidade entre o comportamento da lista ordenada e não ordenada durante as tarefas de pesquisa e remoção no pior cenário possível (o último elemento).

## Como Executar

1. Certifique-se de ter o Java (JDK) instalado no seu ambiente.
2. Clone este repositório e compile todos os arquivos `.java`.
3. (Opcional) Na raiz do diretório onde o programa for executado, adicione um arquivo chamado `entrada.txt` com as linhas no formato `Nome;Telefone` se desejar testar o carregamento em massa.
4. Execute o arquivo principal rodando a classe `Main`.

## Integrantes do Projeto

| Aluno | 
|-------|
| Alice Lourenço dos Reis | 
| Davi Campos Sutil | 
| Karyna Martins Carbas| 