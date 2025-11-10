package aluno;

import java.util.Scanner;

public class InterfaceAluno {
    private final Aluno aluno;
    private final Scanner scanner;
    
    public InterfaceAluno(Aluno aluno) {
        this.aluno = aluno;
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        exibirBanner();
        
        while (true) {
            exibirMenu();
            int opcao = lerOpcao();
            
            if (opcao == 0) {
                System.out.println("\nEncerrando... Até logo!");
                break;
            }
            
            processarOpcao(opcao);
        }
        
        aluno.desconectar();
        scanner.close();
    }
    
    private void exibirBanner() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   PORTAL DO ALUNO - ATENDIMENTO      ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Bem-vindo(a), " + aluno.getNomeAluno() + "!\n");
    }
    
    private void exibirMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1 - Solicitar atendimento (escolher serviço)");
        System.out.println("2 - Consultar minha posição na fila");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                solicitarAtendimento();
                break;
            case 2:
                consultarPosicao();
                break;
            default:
                System.out.println("\n✗ Opção inválida!\n");
        }
    }
    
    private void solicitarAtendimento() {
        System.out.println("\n=== SERVIÇOS DISPONÍVEIS ===");
        String servicos = aluno.listarServicos();
        System.out.println(servicos);
        
        System.out.print("Escolha o código do serviço: ");
        int codigo = lerOpcao();
        
        String resultado = aluno.escolherServico(codigo);
        System.out.println(resultado);
        
        if (resultado.contains("Senha gerada")) {
            System.out.println("Aguarde ser chamado! Você pode consultar sua posição no menu.");
        }
    }
    
    private void consultarPosicao() {
        String resultado = aluno.consultarPosicao();
        System.out.println(resultado);
    }
}