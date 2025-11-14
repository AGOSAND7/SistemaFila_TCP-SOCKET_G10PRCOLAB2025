package servidor;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FilaServico {
    private final Servico servico;
    private final Queue<Senha> fila;
    private final AtomicInteger contadorSenhas;
    private final List<Senha> historico;
    private Senha senhaEmAtendimento;
    
    public FilaServico(Servico servico) {
        this.servico = servico;
        this.fila = new ConcurrentLinkedQueue<>();
        this.contadorSenhas = new AtomicInteger(1);
        this.historico = Collections.synchronizedList(new ArrayList<>());
        this.senhaEmAtendimento = null;
    }
    
    public synchronized String adicionarAluno(String nomeAluno) {
        int numero = contadorSenhas.getAndIncrement();
        String codigo = String.format("%s%03d", servico.getPrefixo(), numero);
        
        Senha senha = new Senha(codigo, nomeAluno, servico);
        fila.offer(senha);
        
        return codigo;
    }
    
    public synchronized Senha chamarProximo() {
        if (fila.isEmpty()) return null;
        
        senhaEmAtendimento = fila.poll();
        return senhaEmAtendimento;
    }
    
    // Método para obter posição de uma senha na fila
    public synchronized int obterPosicao(String codigoSenha) {
        // Verifica se a senha está em atendimento
        if (senhaEmAtendimento != null && 
            senhaEmAtendimento.getCodigo().equals(codigoSenha)) {
            return 0; // Em atendimento
        }
        
        // Verifica se já foi finalizada
        for (Senha s : historico) {
            if (s.getCodigo().equals(codigoSenha) && s.isFinalizado()) {
                return -1; // Já atendida
            }
        }
        
        // Procura na fila
        int posicao = 1;
        for (Senha senha : fila) {
            if (senha.getCodigo().equals(codigoSenha)) {
                return posicao;
            }
            posicao++;
        }
        
        return -1; // Senha não encontrada
    }
    
    // Método para finalizar atendimento
    public synchronized boolean finalizarAtendimento() {
        if (senhaEmAtendimento == null) {
            return false;
        }
        
        senhaEmAtendimento.finalizar();
        historico.add(senhaEmAtendimento);
        senhaEmAtendimento = null;
        
        return true;
    }
    
    // Método para pausar atendimento e retornar à fila
    public synchronized boolean pausarAtendimento() {
        if (senhaEmAtendimento == null) {
            return false;
        }
        
        senhaEmAtendimento.pausar();
        senhaEmAtendimento.despausar(); // Reseta o status de pausado
        fila.offer(senhaEmAtendimento); // Retorna para o final da fila
        senhaEmAtendimento = null;
        
        return true;
    }
    
    // Método para obter estado da fila
    public synchronized String obterEstadoFila() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n─────────────────────────────────────────\n");
        sb.append(String.format("Serviço: %s\n", servico.getNome()));
        sb.append(String.format("Aguardando: %d | Atendidos: %d\n", 
                  tamanhoFila(), totalAtendidos()));
        
        if (senhaEmAtendimento != null) {
            sb.append(String.format("Em atendimento: %s\n", 
                      senhaEmAtendimento.toString()));
        } else {
            sb.append("Em atendimento: Nenhum\n");
        }
        
        if (!fila.isEmpty()) {
            sb.append("Próximos na fila:\n");
            int count = 0;
            for (Senha senha : fila) {
                sb.append(String.format("  %d. %s\n", ++count, senha.toString()));
                if (count >= 5) break; // Mostra apenas os 5 primeiros
            }
        } else {
            sb.append("Fila vazia\n");
        }
        
        return sb.toString();
    }
    
    // Getter para o serviço
    public Servico getServico() {
        return servico;
    }
    
    // Método para obter tamanho da fila
    public synchronized int tamanhoFila() {
        return fila.size();
    }
    
    // Método para obter total de atendidos
    public synchronized int totalAtendidos() {
        int count = 0;
        for (Senha senha : historico) {
            if (senha.isFinalizado()) {
                count++;
            }
        }
        return count;
    }
}