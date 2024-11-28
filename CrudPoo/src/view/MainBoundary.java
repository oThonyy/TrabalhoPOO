package view;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tools.Tela;

public class MainBoundary extends Application {
    private Map<String, Tela> telas = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {

        // Adiciona ProdutoBoundary e PedidoBoundary ao mapa de telas
        telas.put("produto", new ProdutoBoundary());
        telas.put("pedido", new PedidoBoundary() );
    
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu mnuCadastro = new Menu("Cadastro");
        Menu mnuCredito = new Menu("Créditos");
    
        // MenuItem para Produto
        MenuItem mnuItemProduto = new MenuItem("Produto");
        mnuItemProduto.setOnAction(e -> 
           panePrincipal.setCenter(telas.get("produto").render()) //Alterado para "produto"
        );
    
        // MenuItem para Pedido
        MenuItem mnuItemPedido = new MenuItem("Pedido");
        mnuItemPedido.setOnAction(e -> 
             panePrincipal.setCenter(telas.get("pedido").render()) // Alterado para "Pedido"
        );
    
        MenuItem mnuItemNome = new MenuItem("Anthony");
        MenuItem mnuItemRA = new MenuItem("1110482313032");
    
        mnuCadastro.getItems().addAll(mnuItemProduto); // Adiciona Produto no menu Cadastro
        mnuCadastro.getItems().addAll(mnuItemPedido); // Adiciona Pedido no menu Cadastro
        mnuCredito.getItems().add(mnuItemNome);
        mnuCredito.getItems().add(mnuItemRA);
    
        menuBar.getMenus().addAll(mnuCadastro, mnuCredito);
        panePrincipal.setTop(menuBar);
    
        // Define a tela inicial para Produto
        // panePrincipal.setCenter(telas.get("produto").render());
    
        Scene scn = new Scene(panePrincipal, 800, 600); //Configura e ajusta tamanho da tela
        stage.setScene(scn);
        stage.setTitle("Controle de Comércio");
        stage.show();

    }

    // Classe principal de execução
    public static void main(String[] args) {
        Application.launch(MainBoundary.class, args);
    }
    
}