package persistence;

import java.util.List;

import models.Pedido;
import tools.ComercioException;

public interface PedidoDAO {
    
    void inserir (Pedido p) throws ComercioException;
    void atualizar( Pedido p ) throws ComercioException;
    void remover( Pedido p ) throws ComercioException;
    List<Pedido> pesquisarPorNome( String produtos ) throws ComercioException;

}
