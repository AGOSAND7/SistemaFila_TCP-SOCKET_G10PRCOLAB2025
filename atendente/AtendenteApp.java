package atendente;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class AtendenteApp {
    private static Socket socket;
    private static BufferedReader entrada;
    private static PrintWriter saida;
    private static Scanner scanner;
    private static int codigoServicoAtual;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        System.out.println("=== POSTO DE ATENDIMENTO ===");
        
        // Conectar ao servidor
        if (!conectarServidor()) {
            System.out.println("✗ Falha ao conectar. Encerrando...");
            return;
        }
        
        // Selecionar serviço
        if (!selecionarServico()) {
            System.out.println("✗ Falha ao selecionar serviço. Encerrando...");
            return;
        }
        
        // Menu principal
        executarMenu();
        
        desconectar();
    }
    
    private static boolean conectarServidor() {
        try {
            System.out.print("Digite o IP do servidor (padrão: localhost): ");
            String ip = scanner.nextLine().trim();
            if (ip.isEmpty()) ip = "localhost";
            
            System.out.print("Digite a porta (padrão: 5000): ");
            String portaStr = scanner.nextLine().trim();
            int porta = portaStr.isEmpty() ? 5000 : Integer.parseInt(portaStr);
            
            System.out.println("Conectando ao servidor...");
            socket = new Socket(ip, porta);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);
            
            // Ler mensagem de confirmação
            String confirmacao = entrada.readLine();
            System.out.println("✓ Conectado com sucesso!");
            
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean selecionarServico() {
        try {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║   SISTEMA DE ATENDIMENTO - POSTO     ║");
            System.out.println("╚═══════════════════════════════════════╝");
            
            // Listar serviços disponíveis
            saida.println("LISTAR_SERVICOS");
            String resposta = entrada.readLine();
            
            if (resposta == null || !resposta.startsWith("SERVICOS:")) {
                System.out.println("Erro ao obter lista de serviços");
                return false;
            }
            
            String listaServicos = resposta.substring(9); // Remove "SERVICOS:"
            System.out.println("\n=== SELECIONE SEU SERVIÇO ===");
            System.out.println(listaServicos);
            
            System.out.print("Digite o código do serviço: ");
            codigoServicoAtual = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.println("✓ Serviço selecionado com sucesso!");
            return true;
            
        } catch (Exception e) {
            System.out.println("Erro ao selecionar serviço: " + e.getMessage());
            return false;
        }
    }
    
    private static void executarMenu() {
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n=== MENU ATENDIMENTO ===");
            System.out.println("1 - Ver estado das filas");
            System.out.println("2 - Chamar próximo da fila");
            System.out.println("3 - Finalizar atendimento atual");
            System.out.println("4 - Pausar atendimento (retorna à fila)");
            System.out.println("5 - Ver estatísticas");
            System.out.println("0 - Encerrar");
            System.out.print("Escolha uma opção: ");
            
            try {
                String opcao = scanner.nextLine().trim();
                
                switch (opcao) {
                    case "1":
                        verEstadoFilas();
                        break;
                    case "2":
                        chamarProximo();
                        break;
                    case "3":
                        finalizarAtendimento();
                        break;
                    case "4":
                        pausarAtendimento();
                        break;
                    case "5":
                        verEstatisticas();
                        break;
                    case "0":
                        continuar = false;
                        System.out.println("Encerrando sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
    
    private static void verEstadoFilas() {
        try {
            saida.println("ESTADO_FILAS");
            
            // Ler a primeira linha para verificar o tipo de resposta
            String primeiraLinha = entrada.readLine();
            
            if (primeiraLinha == null) {
                System.out.println("Erro: sem resposta do servidor");
                return;
            }
            
            if (primeiraLinha.startsWith("ERRO:")) {
                System.out.println("✗ " + primeiraLinha.substring(5));
                return;
            }
            
            if (primeiraLinha.startsWith("ESTADO:")) {
                // Exibir todo o conteúdo multilinha
                String conteudo = primeiraLinha.substring(7);
                System.out.println(conteudo);
            } else {
                System.out.println("Resposta inesperada: " + primeiraLinha);
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter estado das filas: " + e.getMessage());
        }
    }
    
    private static void chamarProximo() {
        try {
            saida.println("CHAMAR_PROXIMO:" + codigoServicoAtual);
            String resposta = entrada.readLine();
            
            if (resposta == null) {
                System.out.println("Erro: sem resposta do servidor");
                return;
            }
            
            if (resposta.startsWith("ATENDENDO:")) {
                String[] partes = resposta.substring(10).split(",");
                String codigoSenha = partes[0];
                String nomeAluno = partes[1];
                
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║    CHAMANDO PARA ATENDIMENTO      ║");
                System.out.println("╚════════════════════════════════════╝");
                System.out.println("Senha: " + codigoSenha);
                System.out.println("Aluno: " + nomeAluno);
                System.out.println("════════════════════════════════════");
            } else if (resposta.startsWith("ERRO:")) {
                System.out.println("✗ " + resposta.substring(5));
            } else {
                System.out.println("Resposta inesperada: " + resposta);
            }
        } catch (Exception e) {
            System.out.println("Erro ao chamar próximo: " + e.getMessage());
        }
    }
    
    private static void finalizarAtendimento() {
        try {
            saida.println("FINALIZAR_ATENDIMENTO:" + codigoServicoAtual);
            String resposta = entrada.readLine();
            
            if (resposta == null) {
                System.out.println("Erro: sem resposta do servidor");
                return;
            }
            
            if (resposta.startsWith("OK:")) {
                System.out.println("✓ " + resposta.substring(3));
            } else if (resposta.startsWith("ERRO:")) {
                System.out.println("✗ " + resposta.substring(5));
            } else {
                System.out.println("Resposta inesperada: " + resposta);
            }
        } catch (Exception e) {
            System.out.println("Erro ao finalizar atendimento: " + e.getMessage());
        }
    }
    
    private static void pausarAtendimento() {
        try {
            saida.println("PAUSAR_ATENDIMENTO:" + codigoServicoAtual);
            String resposta = entrada.readLine();
            
            if (resposta == null) {
                System.out.println("Erro: sem resposta do servidor");
                return;
            }
            
            if (resposta.startsWith("OK:")) {
                System.out.println("✓ " + resposta.substring(3));
            } else if (resposta.startsWith("ERRO:")) {
                System.out.println("✗ " + resposta.substring(5));
            } else {
                System.out.println("Resposta inesperada: " + resposta);
            }
        } catch (Exception e) {
            System.out.println("Erro ao pausar atendimento: " + e.getMessage());
        }
    }
    
    private static void verEstatisticas() {
        try {
            saida.println("ESTATISTICAS");
            String resposta = entrada.readLine();
            
            if (resposta == null) {
                System.out.println("Erro: sem resposta do servidor");
                return;
            }
            
            if (resposta.startsWith("STATS:")) {
                String stats = resposta.substring(6);
                System.out.println(stats);
            } else if (resposta.startsWith("ERRO:")) {
                System.out.println("✗ " + resposta.substring(5));
            } else {
                System.out.println("Resposta inesperada: " + resposta);
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter estatísticas: " + e.getMessage());
        }
    }
    
    private static void desconectar() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}