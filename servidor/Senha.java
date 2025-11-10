package servidor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Senha {
    private final String codigo;
    private final String nomeAluno;
    private final Servico servico;
    private final LocalDateTime horaEmissao;
    private boolean pausado;
    private boolean finalizado;
    
    public Senha(String codigo, String nomeAluno, Servico servico) {
        this.codigo = codigo;
        this.nomeAluno = nomeAluno;
        this.servico = servico;
        this.horaEmissao = LocalDateTime.now();
        this.pausado = false;
        this.finalizado = false;
    }
    
    public String getCodigo() { return codigo; }
    public String getNomeAluno() { return nomeAluno; }
    public Servico getServico() { return servico; }
    public boolean isPausado() { return pausado; }
    public boolean isFinalizado() { return finalizado; }
    
    public void pausar() { this.pausado = true; }
    public void despausar() { this.pausado = false; }
    public void finalizar() { this.finalizado = true; }
    
    public String getHoraFormatada() {
        return horaEmissao.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    @Override
    public String toString() {
        String status = pausado ? " [PAUSADO]" : "";
        return codigo + " - " + nomeAluno + status;
    }
}
