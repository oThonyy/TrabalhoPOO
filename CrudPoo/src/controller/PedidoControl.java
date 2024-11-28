package controller;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Pedido;
import persistence.PedidoDAO;
import persistence.PedidoDAOImpl;
import tools.ComercioException;

public class PedidoControl {
    
    private ObservableList<Pedido> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty pedidoId = new SimpleLongProperty(0);
    private StringProperty produtos = new SimpleStringProperty("");
    private StringProperty dataPedido = new SimpleStringProperty("");
    private StringProperty valorTotal = new SimpleStringProperty("");
    private StringProperty formaPag = new SimpleStringProperty("");
    private StringProperty statusPed = new SimpleStringProperty("");

    private PedidoDAO pedidoDAO = null;

    public PedidoControl() throws ComercioException {
        pedidoDAO = new PedidoDAOImpl();
    }

    // Converte os dados da tela para a entidade Pedido
    public Pedido paraEntidade() {
        Pedido p = new Pedido();
        p.setPedidoId(pedidoId.get() );
        p.setProdutos( produtos.get() );
        p.setDataPedido( dataPedido.get() );
        p.setValorTotal( valorTotal.get() );
        p.setFormaPag( formaPag.get() );
        p.setStatusPed( statusPed.get() );
        return p;
    }

    // Remove um pedido e atualiza a lista
    public void excluir( Pedido p ) throws ComercioException { 
        pedidoDAO.remover( p );
        pesquisarTodos();
    }

    // Limpa os campos
    public void limparTudo() { 
        pedidoId.set( 0 );
        produtos.set( "" );
        dataPedido.set( "" );
        valorTotal.set( "" );
        formaPag.set( "" );
        statusPed.set( "" );
    }

    // Preenche os campos da tela com os dados da entidade Pedido
    public void paraTela(Pedido p) { 
        if (p != null) {
            pedidoId.set( p.getPedidoId() );
            produtos.set( p.getProdutos() );
            dataPedido.set( p.getDataPedido() );
            valorTotal.set( p.getValorTotal() );
            formaPag.set( p.getFormaPag() );
            statusPed.set( p.getStatusPed() );
        }
    }

    // Salva um novo pedido ou atualiza um existente
    public void gravar() throws ComercioException { 
        Pedido p = paraEntidade();
        if (p.getPedidoId() == 0 ) { 
            this.contador += 1;
            p.setPedidoId( this.contador );
            pedidoDAO.inserir( p );
        } else { 
            pedidoDAO.atualizar( p );
        }
        pesquisarTodos();
    }

    // Pesquisar pedido por produtos
    public void pesquisar() throws ComercioException { 
        lista.clear();
        lista.addAll( pedidoDAO.pesquisarPorNome( produtos.get() ) );
    }

    // Pesquisar pedidos
    public void pesquisarTodos() throws ComercioException { 
        lista.clear();
        lista.addAll( pedidoDAO.pesquisarPorNome( "" ) );
    }

    // Propriedades para vinculação com a interface (JavaFX)
    public LongProperty pedidoIdProperty() {
        return this.pedidoId;
    }

    public StringProperty produtosProperty() {
        return this.produtos;
    }

    public StringProperty dataPedidProperty() {
        return this.dataPedido;
    }

    public StringProperty valorTotalProperty() {
        return this.valorTotal;
    }

    public StringProperty formaPagProperty() {
        return this.formaPag;
    }

    public StringProperty statusPedProperty() {
        return this.statusPed;
    }

    public ObservableList<Pedido> getLista() {
        return this.lista;
    }

}