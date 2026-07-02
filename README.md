# Sistema de Fila TCP - Atendimento Integrado

Este repositório contém uma implementação robusta de um **Sistema de Gestão de Filas de Atendimento** baseado na arquitetura **Cliente-Servidor**, utilizando a API de **Sockets TCP** do Java.

O sistema foi desenvolvido para permitir que alunos solicitem senhas para diferentes serviços e que atendentes gerenciem essas filas em tempo real, garantindo organização, agilidade e eficiência no processo de atendimento.

---

## 🎯 Objetivo do Projeto

O sistema automatiza o processo de triagem e atendimento, eliminando problemas comuns de filas físicas e desorganização.

### Principais características

- ✅ **Escalabilidade:** suporte a múltiplos alunos e atendentes simultaneamente utilizando **Multithreading**.
- ✅ **Concorrência Segura:** utilização de `ConcurrentLinkedQueue` e `AtomicInteger` para evitar inconsistências nos dados.
- ✅ **Comunicação em Tempo Real:** sincronização instantânea entre clientes e servidor através de conexões TCP.

---

# 🏗️ Arquitetura Técnica

O projeto foi dividido em módulos para facilitar a manutenção e separar responsabilidades.

## 1. Servidor (`servidor/`)

Responsável por toda a lógica do sistema.

### Funcionalidades

- Centraliza o gerenciamento das filas;
- Processa múltiplas conexões simultaneamente;
- Mantém as filas e estatísticas em memória.

### Recursos utilizados

- `ServerSocket`
- `Socket`
- `Thread`
- `AtomicInteger`
- `ConcurrentLinkedQueue`

---

## 2. Cliente Aluno (`aluno/`)

Aplicação executada via terminal.

### Funcionalidades

- Listar serviços disponíveis;
- Solicitar senha;
- Consultar posição na fila em tempo real.

---

## 3. Posto de Atendimento (`atendente/`)

Aplicação destinada aos atendentes.

### Funcionalidades

- Chamar próximo aluno;
- Finalizar atendimento;
- Pausar atendimento;
- Exibir estatísticas do sistema.

---

# ⚙️ Tecnologias Utilizadas

- **Java** (JDK 8 ou superior)
- **TCP/IP**
- **Sockets (`Socket` e `ServerSocket`)**
- **Multithreading**
- **AtomicInteger**
- **ConcurrentLinkedQueue**
- **Collections.synchronizedList**
- **BufferedReader**
- **PrintWriter**

---

# 🚀 Como Executar

## 1. Compilar o projeto

No terminal, dentro da pasta raiz:

```bash
javac servidor/*.java aluno/*.java atendente/*.java
```

---

## 2. Iniciar o servidor

```bash
java servidor.ServidorApp
```

O servidor iniciará a escuta na porta **5000**.

---

## 3. Executar os clientes

### Cliente Aluno

```bash
java aluno.AlunoApp
```

### Cliente Atendente

```bash
java atendente.AtendenteApp
```

> **Importante:** Inicie sempre o servidor antes dos clientes.
>
> Quando solicitado, informe o endereço do servidor:
>
> - `localhost` (mesma máquina)
> - ou o IP da máquina onde o servidor estiver executando.

---

# 📂 Estrutura do Projeto

```text
.
├── aluno/
│   └── Cliente do aluno
│
├── atendente/
│   └── Cliente do atendente
│
├── servidor/
│   └── Servidor central
│
└── README.md
```

---

# 🧠 Conceitos Aplicados

- Arquitetura Cliente-Servidor
- Programação Concorrente
- Comunicação TCP
- Threads em Java
- Estruturas concorrentes
- Sincronização de dados
- Filas de atendimento
- Programação em rede

---

# 👨‍💻 Desenvolvedor

**Agostinho Sande (Agosand)**

*Estudante do 4º Ano de Engenharia Informática*

**Universidade Zambeze**
Matacuane – Beira, Moçambique 🇲🇿

### GitHub

<https://github.com/AGOSAND7>

---

# 📝 Licença

Este projeto é distribuído sob a licença **MIT**.

Sinta-se à vontade para estudar, modificar, utilizar e contribuir com melhorias.
