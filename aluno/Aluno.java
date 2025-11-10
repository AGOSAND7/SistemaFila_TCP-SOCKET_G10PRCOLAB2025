package aluno;

import java.io.*;
import java.net.Socket;

public class Aluno {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private String nomeAluno;
    private String senhaAtual;
    private int servicoAtual;
    
    public boolean conectar(String host, int porta, String nome) {
        try {
            this.nomeAluno = nome;
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
    
    public String escolherServico(int codigoServico) {
        enviar("GERAR_SENHA:" + codigoServico + "," + nomeAluno);
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("SENHA:")) {
            String[] partes = resposta.substring(6).split(",");
            senhaAtual = partes[0];
            String nomeServico = partes[1];
            String posicao = partes[2];
            
            servicoAtual = codigoServico;
            
            return String.format("\n✓ Senha gerada: %s\n✓ Serviço: %s\n✓ Posição na fila: %s\n",
                senhaAtual, nomeServico, posicao);
        }
        
        return "Erro ao gerar senha";
    }
    
    public String consultarPosicao() {
        if (senhaAtual == null) {
            return"Você ainda não possui senha. Escolha um serviço primeiro.";
        }
        
        enviar("CONSULTAR_POSICAO:" + servicoAtual + "," + senhaAtual);
        String resposta = receber();
        
        if (resposta != null && resposta.startsWith("POSICAO:")) {
            String[] partes = resposta.substring(8).split(",");
            String posicao = partes[0];
            String mensagem = partes[1];
            
            if (posicao.equals("0")) {
                return "\n" + mensagem + "\n";
            }
            
            return String.format("\n✓ Sua senha: %s\n✓ Posição atual na fila: %s\n✓ Status: %s\n",
                senhaAtual, posicao, mensagem);
        }
        
        return "Erro ao consultar posição";
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
    
    public String getNomeAluno() { return nomeAluno; }
    public String getSenhaAtual() { return senhaAtual; }
}