package servidor;

public enum Servico {
    MATRICULA("Matrícula", "M", 1),
    PAGAMENTO("Pagamento de Propina", "P", 2),
    SECRETARIA("Secretaria Geral", "S", 3),
    BIBLIOTECA("Biblioteca", "B", 4);
    
    private final String nome;
    private final String prefixo;
    private final int codigo;
    
    Servico(String nome, String prefixo, int codigo) {
        this.nome = nome;
        this.prefixo = prefixo;
        this.codigo = codigo;
    }
    
    public String getNome() { return nome; }
    public String getPrefixo() { return prefixo; }
    public int getCodigo() { return codigo; }
    
    public static Servico porCodigo(int codigo) {
        for (Servico s : values()) {
            if (s.codigo == codigo) return s;
        }
        return null;
    }
}