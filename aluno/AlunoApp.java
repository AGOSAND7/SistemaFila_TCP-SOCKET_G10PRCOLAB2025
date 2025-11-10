package aluno;

import java.util.Scanner;

public class AlunoApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== CLIENTE ALUNO ===\n");
        
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine().trim();
        
        System.out.print("Digite o IP do servidor (padrão: localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) host = "localhost";
        
        System.out.print("Digite a porta (padrão: 5000): ");
        String portaStr = scanner.nextLine().trim();
        int porta = portaStr.isEmpty() ? 5000 : Integer.parseInt(portaStr);
        
        Aluno aluno = new Aluno();
        
        System.out.println("\nConectando ao servidor...");
        if (aluno.conectar(host, porta, nome)) {
            System.out.println("✓ Conectado com sucesso!\n");
            InterfaceAluno interfaceAluno = new InterfaceAluno(aluno);
            interfaceAluno.executar();
        } else {
            System.out.println("✗ Falha ao conectar ao servidor.");
        }
        
        scanner.close();
    }
}