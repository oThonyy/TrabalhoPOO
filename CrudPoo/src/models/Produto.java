package models;

public class Produto {
    
    private Long produtoId; // Identificador único do produto
    private String nome; // Nome do produto
    private String descricao; // Descrição detalhada do produto
    private String preco; // Preço do produto
    private String qntEstoque; // Quantidade disponível em estoque
    private String categoria; // Categoria do produto

    // Construtor padrão
    public Produto() {
        super();
    }

    // Construtor com argumentos
    public Produto(Long produtoId, String nome, String descricao, String preco, String qntEstoque, String categoria) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.qntEstoque = qntEstoque;
        this.categoria = categoria;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQntEstoque() {
        return qntEstoque;
    }

    public void setQntEstoque(String qntEstoque) {
        this.qntEstoque = qntEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto [id=" + produtoId + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco
                + ", qntEstoque=" + qntEstoque + ", categoria=" + categoria + "]";
    }

}
