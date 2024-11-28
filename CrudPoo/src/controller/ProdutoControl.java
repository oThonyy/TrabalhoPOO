package controller;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Produto;
import persistence.ProdutoDAO;
import persistence.ProdutoDAOImpl;
import tools.ComercioException;

public class ProdutoControl {
    
    private ObservableList<Produto> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty produtoId = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty preco = new SimpleStringProperty("");
    private StringProperty qntEstoque = new SimpleStringProperty("");
    private StringProperty categoria = new SimpleStringProperty("");

    private ProdutoDAO produtoDAO = null;

    public ProdutoControl() throws ComercioException { 
        produtoDAO = new ProdutoDAOImpl();
    }

    // Converte os dados da tela para a entidade Produto
    public Produto paraEntidade() { 
        Produto p = new Produto();
        p.setProdutoId( produtoId.get() );
        p.setNome( nome.get() );
        p.setDescricao( descricao.get() );
        p.setPreco( preco.get() );
        p.setQntEstoque( qntEstoque.get() );
        p.setCategoria( categoria.get() );
        return p;
    }

    // Remove um produto e atualiza a lista
    public void excluir( Produto p ) throws ComercioException { 
        produtoDAO.remover( p );
        pesquisarTodos();
    }

    // Limpa os campos
    public void limparTudo() { 
        produtoId.set( 0 );
        nome.set( "" );
        descricao.set( "" );
        preco.set( "" );
        qntEstoque.set( "" );
        categoria.set( "" );
    }

    // Preenche os campos da tela com os dados da entidade Produto
    public void paraTela(Produto p) { 
        if (p != null) {
            produtoId.set( p.getProdutoId() );
            nome.set( p.getNome() );
            descricao.set( p.getDescricao() );
            preco.set( p.getPreco() );
            qntEstoque.set( p.getQntEstoque() );
            categoria.set( p.getCategoria() );
        }
    }

    // Salva um novo pedido ou atualiza um existente
    public void gravar() throws ComercioException { 
        Produto p = paraEntidade();
        if (p.getProdutoId() == 0 ) { 
            this.contador += 1;
            p.setProdutoId( this.contador );
            produtoDAO.inserir( p );
        } else { 
            produtoDAO.atualizar( p );
        }
        pesquisarTodos();
    }

    // Pesquisar produto por nome
    public void pesquisar() throws ComercioException { 
        lista.clear();
        lista.addAll( produtoDAO.pesquisarPorNome( nome.get() ) );
    }

    // Pesquisar produtos
    public void pesquisarTodos() throws ComercioException { 
        lista.clear();
        lista.addAll( produtoDAO.pesquisarPorNome( "" ) );
    }

    // Propriedades para vinculação com a interface (JavaFX)
    public LongProperty produtoIdProperty() { 
        return this.produtoId;
    }

    public StringProperty nomeProperty() { 
        return this.nome;
    }

    public StringProperty descricaoProperty() { 
        return this.descricao;
    }

    public StringProperty precoProperty() { 
        return this.preco;
    }

    public StringProperty qntEstoqueProperty() { 
        return this.qntEstoque;
    }

    public StringProperty categoriaProperty() { 
        return this.categoria;
    }

    public ObservableList<Produto> getLista() { 
        return this.lista;
    }

}