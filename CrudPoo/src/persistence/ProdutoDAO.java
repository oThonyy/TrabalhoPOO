package persistence;

import java.util.List;

import models.Produto;
import tools.ComercioException;

public interface ProdutoDAO {

    void inserir( Produto p ) throws ComercioException;
    void atualizar( Produto p ) throws ComercioException;
    void remover( Produto p ) throws ComercioException;
    List<Produto> pesquisarPorNome( String nome ) throws ComercioException;

}