package atendente;

import java.util.Scanner;

public class AtendenteApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== POSTO DE ATENDIMENTO ===\n");
        
        System.out.print("Digite o IP do servidor (padrão: localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) host = "localhost";
        
        System.out.print("Digite a porta (padrão: 5000): ");
        String portaStr = scanner.nextLine().trim();
        int porta = portaStr.isEmpty() ? 5000 : Integer.parseInt(portaStr);
        
        Atendente atendente = new Atendente();
        
        System.out.println("\nConectando ao servidor...");
        if (atendente.conectar(host, porta)) {
            System.out.println("✓ Conectado com sucesso!\n");
            InterfaceAtendente interfaceAtendente = new InterfaceAtendente(atendente);
            interfaceAtendente.executar();
        } else {
            System.out.println("✗ Falha ao conectar ao servidor.");
        }
        
        scanner.close();
    }
}
