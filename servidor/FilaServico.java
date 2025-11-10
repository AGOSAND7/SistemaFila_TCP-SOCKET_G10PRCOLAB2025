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
}