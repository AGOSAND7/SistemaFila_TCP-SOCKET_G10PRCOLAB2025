package servidor;

import java.io.*;
import java.net.Socket;

public class ConexaoCliente extends Thread {
    private final Socket socket;
    private final GerenciadorFilas gerenciador;
    private final String tipo; // "ALUNO" ou "ATENDENTE"
    private BufferedReader entrada;
    private PrintWriter saida;
    
    public ConexaoCliente(Socket socket, GerenciadorFilas gerenciador, String tipo) {
        this.socket = socket;
        this.gerenciador = gerenciador;
        this.tipo = tipo;
    }
    
    @Override
    public void run() {
        try {
            inicializar();
            processar();
        } catch (IOException e) {
            System.out.println("[SERVIDOR] Cliente desconectado: " + tipo);
        } finally {
            desconectar();
        }
    }
    
    private void inicializar() throws IOException {
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        saida = new PrintWriter(socket.getOutputStream(), true);
        saida.println("CONECTADO:" + tipo);
    }
    
    private void processar() throws IOException {
        String mensagem;
        while ((mensagem = entrada.readLine()) != null) {
            String resposta = processarMensagem(mensagem);
            saida.println(resposta);
        }
    }
    
    private String processarMensagem(String mensagem) {
        String[] partes = mensagem.split(":", 2);
        String comando = partes[0];
        String dados = partes.length > 1 ? partes[1] : "";
        
        switch (comando) {
            case "LISTAR_SERVICOS":
                return "SERVICOS:" + Servico.listarServicos();
                
            case "GERAR_SENHA":
                return gerarSenha(dados);
                
            case "CONSULTAR_POSICAO":
                return consultarPosicao(dados);
                
            case "ESTADO_FILAS":
                return "ESTADO:" + gerenciador.obterEstadoCompleto();
                
            case "CHAMAR_PROXIMO":
                return chamarProximo(dados);
                
            case "FINALIZAR_ATENDIMENTO":
                return finalizarAtendimento(dados);
                
            case "PAUSAR_ATENDIMENTO":
                return pausarAtendimento(dados);
                
            case "ESTATISTICAS":
                return "STATS:" + gerenciador.obterEstatisticas();
                
            default:
                return "ERRO:Comando desconhecido";
        }
    }
    
    private String gerarSenha(String dados) {
        String[] partes = dados.split(",");
        if (partes.length != 2) {
            return "ERRO:Formato inválido";
        }
        
        int codigoServico = Integer.parseInt(partes[0]);
        String nomeAluno = partes[1];
        
        Servico servico = Servico.porCodigo(codigoServico);
        if (servico == null) {
            return "ERRO:Serviço não encontrado";
        }
        
        String codigoSenha = gerenciador.gerarSenha(servico, nomeAluno);
        int posicao = gerenciador.obterPosicao(servico, codigoSenha);
        
        String msg = String.format("SENHA:%s,%s,%d,%s", 
            codigoSenha, servico.getNome(), posicao, nomeAluno);
        
        System.out.println(String.format("[SERVIDOR] Aluno '%s' recebeu senha %s - Posição: %d (%s)", 
            nomeAluno, codigoSenha, posicao, servico.getNome()));
        
        return msg;
    }
    
    private String consultarPosicao(String dados) {
        String[] partes = dados.split(",");
        if (partes.length != 2) {
            return "ERRO:Formato inválido";
        }
        
        int codigoServico = Integer.parseInt(partes[0]);
        String codigoSenha = partes[1];
        
        Servico servico = Servico.porCodigo(codigoServico);
        if (servico == null) {
            return "ERRO:Serviço não encontrado";
        }
        
        int posicao = gerenciador.obterPosicao(servico, codigoSenha);
        
        if (posicao == -1) {
            return "POSICAO:0,Você já foi atendido ou senha inválida";
        }
        
        return "POSICAO:" + posicao + ",Ainda na fila";
    }
    
    private String chamarProximo(String dados) {
        int codigoServico = Integer.parseInt(dados);
        Servico servico = Servico.porCodigo(codigoServico);
        
        if (servico == null) {
            return "ERRO:Serviço não encontrado";
        }
        
        FilaServico fila = gerenciador.obterFila(servico);
        Senha senha = fila.chamarProximo();
        
        if (senha == null) {
            return "ERRO:Fila vazia";
        }
        
        System.out.println(String.format("[SERVIDOR] Atendendo: %s - %s (%s)", 
            senha.getCodigo(), senha.getNomeAluno(), servico.getNome()));
        
        return "ATENDENDO:" + senha.getCodigo() + "," + senha.getNomeAluno();
    }
    
    private String finalizarAtendimento(String dados) {
        int codigoServico = Integer.parseInt(dados);
        Servico servico = Servico.porCodigo(codigoServico);
        
        if (servico == null) {
            return "ERRO:Serviço não encontrado";
        }
        
        FilaServico fila = gerenciador.obterFila(servico);
        boolean sucesso = fila.finalizarAtendimento();
        
        if (!sucesso) {
            return "ERRO:Nenhum atendimento em andamento";
        }
        
        System.out.println(String.format("[SERVIDOR] Atendimento finalizado (%s)", servico.getNome()));
        return "OK:Atendimento finalizado";
    }
    
    private String pausarAtendimento(String dados) {
        int codigoServico = Integer.parseInt(dados);
        Servico servico = Servico.porCodigo(codigoServico);
        
        if (servico == null) {
            return "ERRO:Serviço não encontrado";
        }
        
        FilaServico fila = gerenciador.obterFila(servico);
        boolean sucesso = fila.pausarAtendimento();
        
        if (!sucesso) {
            return "ERRO:Nenhum atendimento em andamento";
        }
        
        System.out.println(String.format("[SERVIDOR] Atendimento pausado (%s)", servico.getNome()));
        return "OK:Atendimento pausado - retornado à fila";
    }
    
    private void desconectar() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}