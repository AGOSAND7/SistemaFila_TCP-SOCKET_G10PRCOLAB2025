package servidor;

import java.util.*;

public class GerenciadorFilas {
    private final Map<Servico, FilaServico> filas;
    
    public GerenciadorFilas() {
        filas = new HashMap<>();
        for (Servico servico : Servico.values()) {
            filas.put(servico, new FilaServico(servico));
        }
    }
    
    public String gerarSenha(Servico servico, String nomeAluno) {
        FilaServico fila = filas.get(servico);
        return fila.adicionarAluno(nomeAluno);
    }
    
    public int obterPosicao(Servico servico, String codigoSenha) {
        FilaServico fila = filas.get(servico);
        return fila.obterPosicao(codigoSenha);
    }
    
    public String obterEstadoCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════╗\n");
        sb.append("║    ESTADO GERAL DAS FILAS              ║\n");
        sb.append("╚════════════════════════════════════════╝\n");
        
        for (FilaServico fila : filas.values()) {
            sb.append(fila.obterEstadoFila());
        }
        
        return sb.toString();
    }
    
    public FilaServico obterFila(Servico servico) {
        return filas.get(servico);
    }
    
    public String obterEstatisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ESTATÍSTICAS ===\n");
        for (FilaServico fila : filas.values()) {
            sb.append(fila.getServico().getNome())
              .append(": ").append(fila.tamanhoFila()).append(" aguardando, ")
              .append(fila.totalAtendidos()).append(" atendidos\n");
        }
        return sb.toString();
    }
}