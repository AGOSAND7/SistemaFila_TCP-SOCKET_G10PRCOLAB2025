package atendente;

import java.io.*;
import java.net.Socket;

public class Atendente {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private int servicoAtual;
    
    public boolean conectar(String host, int porta) {
        try {
            socket = new Socket(host, porta);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);
            
            String resposta = entrada.readLine();
            return resposta != null && resposta.startsWith("CONECTADO");
        } catch (IOException e) {
            return false;
        }
    }
    
    public String listarServicos() {
        enviar("LISTAR_SERVICOS:");
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("SERVICOS:")) {
            return resposta.substring(9);
        }
        return "Erro ao listar serviços";
    }
    
    public void selecionarServico(int codigo) {
        this.servicoAtual = codigo;
    }
    
    public String visualizarFilas() {
        enviar("ESTADO_FILAS:");
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("ESTADO:")) {
            return resposta.substring(7);
        }
        return "Erro ao obter estado das filas";
    }
    
    public String chamarProximo() {
        enviar("CHAMAR_PROXIMO:" + servicoAtual);
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("ATENDENDO:")) {
            String[] partes = resposta.substring(10).split(",");
            String senha = partes[0];
            String nome = partes[1];
            
            return String.format("\n✓ Atendendo senha: %s\n✓ Aluno: %s\n", senha, nome);
        }
        
        if (resposta != null && resposta.startsWith("ERRO:")) {
            return "\n✗ " + resposta.substring(5) + "\n";
        }
        
        return "Erro ao chamar próximo";
    }
    
    public String finalizarAtendimento() {
        enviar("FINALIZAR_ATENDIMENTO:" + servicoAtual);
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("OK:")) {
            return "\n✓ " + resposta.substring(3) + "\n";
        }
        
        if (resposta != null && resposta.startsWith("ERRO:")) {
            return "\n✗ " + resposta.substring(5) + "\n";
        }
        
        return "Erro ao finalizar atendimento";
    }
    
    public String pausarAtendimento() {
        enviar("PAUSAR_ATENDIMENTO:" + servicoAtual);
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("OK:")) {
            return "\n✓ " + resposta.substring(3) + "\n";
        }
        
        if (resposta != null && resposta.startsWith("ERRO:")) {
            return "\n✗ " + resposta.substring(5) + "\n";
        }
        
        return "Erro ao pausar atendimento";
    }
    
    public String obterEstatisticas() {
        enviar("ESTATISTICAS:");
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("STATS:")) {
            return resposta.substring(6);
        }
        return "Erro ao obter estatísticas";
    }
    
    public void desconectar() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void enviar(String mensagem) {
        saida.println(mensagem);
    }
    
    private String receber() {
        try {
            return entrada.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    public int getServicoAtual() {
        return servicoAtual;
    }
}
