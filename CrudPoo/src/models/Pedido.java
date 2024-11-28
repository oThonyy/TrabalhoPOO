package models;

public class Pedido {
    
    private Long pedidoId; // Identificador único do pedido
    private String produtos; // Produtos do pedido
    private String dataPedido; // Data do pedido
    private String valorTotal; // Valor total do pedido
    private String formaPag; // Forma de pagamento do pedido
    private String statusPed; // Status do pedido (ex.: "Em processamento", "Concluído", "Cancelado")

    // Construtor padrão
    public Pedido() {
        super();
    }

    // Construtor com argumentos
    public Pedido(Long pedidoId, String produtos, String dataPedido, String valorTotal, String formaPag, String statusPed) {
        this.pedidoId = pedidoId;
        this.produtos = produtos;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
        this.formaPag  = formaPag;
        this.statusPed = statusPed;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

    public String getStatusPed() {
        return statusPed;
    }

    public void setStatusPed(String statusPed) {
        this.statusPed = statusPed;
    }

    @Override
    public String toString() {
        return "Pedido [pedidoId=" + pedidoId + ", produtos=" + produtos + ", dataPedido=" + dataPedido
                + ", valorTotal=" + valorTotal + ", formaPag=" + formaPag + ", statusPed=" + statusPed + "]";
    }

}
