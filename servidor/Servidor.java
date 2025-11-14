package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PORTA = 5000;
    private final GerenciadorFilas gerenciador;
    
    public Servidor() {
        this.gerenciador = new GerenciadorFilas();
    }
    
    public void iniciar() {
        exibirBanner();
        
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("[SERVIDOR] Escutando na porta " + PORTA);
            System.out.println("[SERVIDOR] Aguardando conexões...\n");
            
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                String tipo = identificarCliente(clienteSocket);
                
                ConexaoCliente conexao = new ConexaoCliente(clienteSocket, gerenciador, tipo);
                conexao.start();
                
                System.out.println("[SERVIDOR] " + tipo + " conectado");
            }
        } catch (IOException e) {
            System.err.println("[SERVIDOR] Erro: " + e.getMessage());
        }
    }
    
    private String identificarCliente(Socket socket) {
        // Simplificado: primeiro cliente é aluno, segundo atendente
        // Em produção, seria feito via autenticação
        return "CLIENTE";
    }
    
    private void exibirBanner() {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE FILAS DE ATENDIMENTO ACADÊMICO        ║");
        System.out.println("║  Servidor Central de Gerenciamento                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }
}
