package view;

import java.util.Optional;

import controller.PedidoControl;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.Pedido;
import tools.ComercioException;
import tools.Tela;

public class PedidoBoundary implements Tela {

    private Label lblPedidoId = new Label();
    private TextField txtProdutos = new TextField();
    private TextField txtDataPedido = new TextField();
    private TextField txtValorTotal = new TextField();
    private TextField txtFormaPag = new TextField(); // Para minutos ou segundos
    private TextField txtStatusPed = new TextField();

    private PedidoControl control = null;
    private TableView<Pedido> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new PedidoControl();
        } catch (ComercioException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (ComercioException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar a pedido", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (ComercioException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar pedido", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("Limpar tudo");
        btnNovo.setOnAction(e -> control.limparTudo());

        paneForm.add(new Label("PedidoId: "), 0, 0);
        paneForm.add(lblPedidoId, 1, 0);
        paneForm.add(new Label("Produtos: "), 0, 1);
        paneForm.add(txtProdutos, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Data Pedido: "), 0, 2);
        paneForm.add(txtDataPedido, 1, 2);
        paneForm.add(new Label("Valor Total: "), 0, 3);
        paneForm.add(txtValorTotal, 1, 3);
        paneForm.add(new Label("Forma de Pagamento: "), 0, 4);
        paneForm.add(txtFormaPag, 1, 4);
        paneForm.add(new Label("Status do Pedido: "), 0, 5);
        paneForm.add(txtStatusPed, 1, 5);

        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    
    @SuppressWarnings("unchecked")
    public void gerarColunas() {
        TableColumn<Pedido, Long> col1 = new TableColumn<>("PedidoId");
        col1.setCellValueFactory(new PropertyValueFactory<>("pedidoId"));

        TableColumn<Pedido, String> col2 = new TableColumn<>("Produtos");
        col2.setCellValueFactory(new PropertyValueFactory<>("produtos"));

        TableColumn<Pedido, String> col3 = new TableColumn<>("Data Pedido");
        col3.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));

        TableColumn<Pedido, String> col4 = new TableColumn<>("Valor Total");
        col4.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        TableColumn<Pedido, String> col5 = new TableColumn<>("Forma de Pagamento");
        col5.setCellValueFactory(new PropertyValueFactory<>("formaPag"));

        TableColumn<Pedido, String> col6 = new TableColumn<>("Status do Pedido");
        col6.setCellValueFactory(new PropertyValueFactory<>("statusPed"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Selecionado: " + novo.getProdutos());
                        control.paraTela(novo);
                    }
                });

        Callback<TableColumn<Pedido, Void>, TableCell<Pedido, Void>> cb = 
                new Callback<>() {
                    @Override
                    public TableCell<Pedido, Void> call(TableColumn<Pedido, Void> param) {
                        return new TableCell<>() {
                            final Button btnApagar = new Button("Apagar");

                            {
                                btnApagar.setOnAction(e -> {
                                    Pedido pedido = tableView.getItems().get(getIndex());
                                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                                            "Tem certeza que deseja apagar o pedido?", 
                                            ButtonType.YES, ButtonType.NO);
                                    Optional<ButtonType> result = confirm.showAndWait();
                                    if (result.isPresent() && result.get() == ButtonType.YES) {
                                        try {
                                            control.excluir( pedido );
                                        } catch (ComercioException err) {
                                            new Alert(Alert.AlertType.ERROR, "Erro ao excluir o pedido", ButtonType.OK).showAndWait();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setGraphic(btnApagar);
                                } else {
                                    setGraphic(null);
                                }
                            }
                        };
                    }
                };

        TableColumn<Pedido, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems(control.getLista());
    }

    public void ligacoes() {
        control.pedidoIdProperty().addListener( (obs, antigo, novo) -> {
            lblPedidoId.setText(String.valueOf(novo) );
        });

        Bindings.bindBidirectional(control.produtosProperty(), txtProdutos.textProperty());
        Bindings.bindBidirectional(control.dataPedidProperty(), txtDataPedido.textProperty());
        Bindings.bindBidirectional(control.valorTotalProperty(), txtValorTotal.textProperty());
        Bindings.bindBidirectional(control.formaPagProperty(), txtFormaPag.textProperty());
        Bindings.bindBidirectional(control.statusPedProperty(), txtStatusPed.textProperty());

    }
    
}
