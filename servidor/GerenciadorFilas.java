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
        
        for (Servico servico : Servico.values()) {
            FilaServico fila = filas.get(servico);
            sb.append(String.format("[%s] Aguardando: %d | Atendidos: %d | ", 
                servico.getNome(), fila.tamanhoFila(), fila.totalAtendidos()));
        }
        
        return sb.toString();
    }
    
    public FilaServico obterFila(Servico servico) {
        return filas.get(servico);
    }
    
    public String obterEstatisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("ESTATISTICAS: ");
        
        for (Servico servico : Servico.values()) {
            FilaServico fila = filas.get(servico);
            sb.append(String.format("%s: %d aguardando, %d atendidos | ", 
                servico.getNome(), fila.tamanhoFila(), fila.totalAtendidos()));
        }
        
        return sb.toString();
    }
}