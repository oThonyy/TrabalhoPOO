package view;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import controller.ProdutoControl;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import models.Produto;
import tools.ComercioException;
import tools.Tela;

public class ProdutoBoundary implements Tela {
    
    private Label lblProdutoId = new Label();
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtPreco = new TextField();
    private TextField txtQntEstoque = new TextField();
    private TextField txtCategoria = new TextField();

    private ProdutoControl control = null;

    private TableView<Produto> tableView = new TableView<>();

    @Override
    public Pane render() {
        try { 
            control = new ProdutoControl();
        } catch (ComercioException e) { 
            // Mostra um alerta de erro ao usuário
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
            
            // Imprime detalhes do erro no console
            System.out.println(e); // ou e.printStackTrace();
        
            // Repropaga a exceção para ser tratada em outro lugar
            throw new RuntimeException("Erro ao inicializar o ProdutoControl", e);
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction( e -> { 
            try { 
                control.gravar();
            } catch (ComercioException err) { 
                new Alert(AlertType.ERROR, "Erro ao gravar o produto", ButtonType.OK).showAndWait();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            tableView.refresh();
        }); 

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction( e -> { 
            try { 
                control.pesquisar();
            } catch (ComercioException err) { 
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }});

        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );

        paneForm.add(new Label("ProdutoId: "), 0, 0);
        paneForm.add(lblProdutoId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Descrição: "), 0, 2);
        paneForm.add(txtDescricao, 1, 2);
        paneForm.add(new Label("Preço: "), 0, 3);
        paneForm.add(txtPreco, 1, 3);
        paneForm.add(new Label("Quantidade Estoque: "), 0, 4);
        paneForm.add(txtQntEstoque, 1, 4);
        paneForm.add(new Label("Categoria: "), 0, 5);
        paneForm.add(txtCategoria, 1, 5);

        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop( paneForm );
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    @SuppressWarnings("unchecked")
    public void gerarColunas() { 
        TableColumn<Produto, Long> col1 = new TableColumn<>("ProdutoId");
        col1.setCellValueFactory( new PropertyValueFactory<Produto, Long>("produtoId") );

        TableColumn<Produto, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<Produto, String>("nome"));

        TableColumn<Produto, String> col3 = new TableColumn<>("Descrição");
        col3.setCellValueFactory( new PropertyValueFactory<Produto, String>("descricao"));

        TableColumn<Produto, String> col4 = new TableColumn<>("Preço");
        col4.setCellValueFactory( new PropertyValueFactory<Produto, String>("preco"));

        TableColumn<Produto, String> col5 = new TableColumn<>("Quantidade Estoque");
        col5.setCellValueFactory( new PropertyValueFactory<Produto, String>("qntEstoque"));

        TableColumn<Produto, String> col6 = new TableColumn<>("Categoria");
        col6.setCellValueFactory( new PropertyValueFactory<Produto, String>("categoria"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("Nome: " + novo.getNome());
                    control.paraTela(novo);
                }
            });
        Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> cb = new Callback<>() {
                @Override
                public TableCell<Produto, Void> call(TableColumn<Produto, Void> param) {
                    TableCell<Produto, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Produto produto = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( produto ); 
                                } catch (ComercioException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir o produto", ButtonType.OK).showAndWait();
                                }
                            });
                        }

                        @Override
                        public void updateItem( Void item, boolean empty) {      
                            super.updateItem(item, empty);                       
                            if (!empty) { 
                                setGraphic(btnApagar);
                            } else { 
                                setGraphic(null);
                            }
                        }
                        
                    };
                    return celula;            
                } 
            };

        TableColumn<Produto, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems( control.getLista() );
    }

    public void ligacoes() { 
        control.produtoIdProperty().addListener( (obs, antigo, novo) -> {
            lblProdutoId.setText( String.valueOf(novo) );
        });
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.descricaoProperty(), txtDescricao.textProperty());
        Bindings.bindBidirectional(control.precoProperty(), txtPreco.textProperty());
        Bindings.bindBidirectional(control.qntEstoqueProperty(), txtQntEstoque.textProperty());
        Bindings.bindBidirectional(control.categoriaProperty(), txtCategoria.textProperty());
    }

}
