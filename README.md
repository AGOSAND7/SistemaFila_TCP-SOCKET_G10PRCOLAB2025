```markdown
# Sistema de Fila TCP - Atendimento Integrado

Este repositório contém uma implementação robusta de um **Sistema de Gestão de Filas de Atendimento** baseado na arquitetura Cliente-Servidor, utilizando a API de **Sockets (TCP)** do Java. O sistema foi projetado para permitir que alunos solicitem senhas para serviços específicos e que atendentes gerenciem essas filas em tempo real, garantindo a organização e o fluxo eficiente de atendimento.

---

## 🎯 Objetivo do Projeto
O sistema visa automatizar o processo de triagem e atendimento, proporcionando:
* **Escalabilidade:** Suporte a múltiplos clientes (alunos e atendentes) simultâneos via *Multithreading*.
* **Concorrência Segura:** Uso de estruturas `ConcurrentLinkedQueue` e `AtomicInteger` para evitar inconsistências nos dados de atendimento.
* **Comunicação em Tempo Real:** Sincronização instantânea entre o estado da fila no servidor e as solicitações dos clientes.

---

## 🏗️ Arquitetura Técnica

O projeto é dividido em três módulos principais:

1. **Servidor (`servidor/`):**
   * Centraliza a lógica de negócios e o armazenamento das filas.
   * Utiliza *Threads* (`ConexaoCliente`) para processar cada conexão de forma independente.
   * Gerencia estatísticas, estados de atendimento e regras de negócio.

2. **Cliente Aluno (`aluno/`):**
   * Interface via console para o estudante.
   * Permite listar serviços, solicitar senhas e verificar a posição atual na fila sem necessidade de interação humana presencial constante.

3. **Posto de Atendimento (`atendente/`):**
   * Ferramenta destinada aos funcionários.
   * Permite o controle total: chamar o próximo, finalizar, pausar atendimentos e visualizar estatísticas de desempenho.

---

## ⚙️ Tecnologias e Conceitos

* **Linguagem:** Java (JDK 8 ou superior).
* **Protocolo de Rede:** TCP (Transmission Control Protocol) via `java.net.Socket` e `ServerSocket`.
* **Programação Concorrente:** `Thread`, `AtomicInteger`, `ConcurrentLinkedQueue`, `Collections.synchronizedList`.
* **I/O Streams:** `BufferedReader` e `PrintWriter` para comunicação bidirecional de texto.

---

## 🚀 Como Executar

### 1. Compilação
No terminal, dentro da pasta raiz do projeto, execute:
```bash
javac servidor/*.java aluno/*.java atendente/*.java

```

### 2. Iniciando o Servidor

Execute a aplicação servidor para abrir a porta de escuta (padrão: 5000):

```bash
java servidor.ServidorApp

```

### 3. Iniciando os Clientes

* **Para Alunos:**
```bash
java aluno.AlunoApp

```


* **Para Atendentes:**
```bash
java atendente.AtendenteApp

```



> **Nota:** Certifique-se de que o servidor esteja rodando antes de iniciar qualquer cliente. O sistema solicitará o IP do servidor (digite `localhost` se estiverem na mesma máquina).

---

## 📂 Estrutura do Repositório

```text
├── aluno/            # Código-fonte do cliente aluno
├── atendente/        # Código-fonte do posto de atendimento
├── servidor/         # Código-fonte do servidor central e gerenciador de filas
└── README.md         # Documentação técnica

```

---

## 👨‍💻 Desenvolvedor

* **Agostinho Sande (Agosand)**
* 4º Ano de Engenharia Informática | Universidade Zambeze, Matacuane - Beira, Moçambique 🇲🇿.
* [GitHub: @AGOSAND7](https://www.google.com/search?q=https://github.com/AGOSAND7)

---

## 📝 Licença

Este projeto é de código aberto sob a licença MIT. Sinta-se à vontade para utilizar, aprender e contribuir.

```

```
