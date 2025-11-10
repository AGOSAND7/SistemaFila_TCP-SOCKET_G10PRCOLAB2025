package atendente;

import java.util.Scanner;

public class InterfaceAtendente {
    private final Atendente atendente;
    private final Scanner scanner;
    
    public InterfaceAtendente(Atendente atendente) {
        this.atendente = atendente;
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        exibirBanner();
        selecionarServico();
        
        while (true) {
            exibirMenu();
            int opcao = lerOpcao();
            
            if (opcao == 0) {
                System.out.println("\nEncerrando atendimento... Até logo!");
                break;
            }
            
            processarOpcao(opcao);
        }
        
        atendente.desconectar();
        scanner.close();
    }
    
    private void exibirBanner() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE ATENDIMENTO - POSTO     ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
    
    private void selecionarServico() {
        System.out.println("\n=== SELECIONE SEU SERVIÇO ===");
        String servicos = atendente.listarServicos();
        System.out.println(servicos);
        
        System.out.print("Digite o código do serviço: ");
        int codigo = lerOpcao();
        atendente.selecionarServico(codigo);
        
        System.out.println("\n✓ Serviço selecionado com sucesso!");
    }
    
    private void exibirMenu() {
        System.out.println("\n=== MENU ATENDIMENTO ===");
        System.out.println("1 - Ver estado das filas");
        System.out.println("2 - Chamar próximo da fila");
        System.out.println("3 - Finalizar atendimento atual");
        System.out.println("4 - Pausar atendimento (retorna à fila)");
        System.out.println("5 - Ver estatísticas");
        System.out.println("0 - Encerrar");
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
                visualizarFilas();
                break;
            case 2:
                chamarProximo();
                break;
            case 3:
                finalizarAtendimento();
                break;
            case 4:
                pausarAtendimento();
                break;
            case 5:
                verEstatisticas();
                break;
            default:
                System.out.println("\n✗ Opção inválida!\n");
        }
    }
    
    private void visualizarFilas() {
        String filas = atendente.visualizarFilas();
        System.out.println(filas);
    }
    
    private void chamarProximo() {
        String resultado = atendente.chamarProximo();
        System.out.println(resultado);
    }
    
    private void finalizarAtendimento() {
        String resultado = atendente.finalizarAtendimento();
        System.out.println(resultado);
    }
    
    private void pausarAtendimento() {
        String resultado = atendente.pausarAtendimento();
        System.out.println(resultado);
    }
    
    private void verEstatisticas() {
        String stats = atendente.obterEstatisticas();
        System.out.println(stats);
    }
}