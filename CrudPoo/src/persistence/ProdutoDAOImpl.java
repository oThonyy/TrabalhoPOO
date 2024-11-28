package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Produto;
import tools.ComercioException;

public class ProdutoDAOImpl implements ProdutoDAO {
    
    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/comerciodb";
    private final static String DB_USER = "thony";
    private final static String DB_PASS = "123456";

    private Connection con = null;

    public ProdutoDAOImpl() throws ComercioException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new ComercioException( e );
        }
    }

    @Override
    public void inserir(Produto p) throws ComercioException {
        try { 
            String SQL = """
                    INSERT INTO produtos (produtoId, nome, descricao, preco, qntEstoque, categoria)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong(1, p.getProdutoId());
            stm.setString(2, p.getNome());
            stm.setString(3, p.getDescricao());
            stm.setString(4, p.getPreco());
            stm.setString(5, p.getQntEstoque());
            stm.setString(6, p.getCategoria());

            int rowsAffected = stm.executeUpdate(); // Verifica quantas linhas foram afetadas
            System.out.println("Linhas inseridas: " + rowsAffected);

        } catch (SQLException e) { 
            throw new ComercioException( e );
        }
    }

    @Override
    public void atualizar(Produto p) throws ComercioException {
        try { 
            String SQL = """
                    UPDATE produtos SET nome = ?, descricao = ?, preco = ?, qntEstoque = ?, categoria = ?,
                    WHERE produtoId = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, p.getNome());
            stm.setString(2, p.getDescricao());
            stm.setString(3, p.getPreco());
            stm.setString(4, p.getQntEstoque());
            stm.setString(5, p.getCategoria());
            stm.setLong(6, p.getProdutoId());
            
            stm.executeUpdate();

        } catch (SQLException e) { 
            throw new ComercioException( "Erro ao atualizar o Produto" );
        }        
    }

    @Override
    public void remover(Produto p) throws ComercioException {
        try { 
            String SQL = """
                    DELETE FROM produtos WHERE produtoId = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong( 1, p.getProdutoId() );
            
            stm.executeUpdate();

        } catch (SQLException e) { 
            throw new ComercioException( "Erro ao remover Produto" );
        }
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome) throws ComercioException {
        List<Produto> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM produtos WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Produto p = new Produto();
                p.setProdutoId( rs.getLong("produtoId") );
                p.setNome( rs.getString("nome") );
                p.setDescricao( rs.getString("descricao") );
                p.setPreco( rs.getString("preco") );
                p.setQntEstoque( rs.getString("qntEstoque"));
                p.setCategoria( rs.getString ( "categoria"));

                lista.add( p );
            }
        } catch (SQLException e) { 
            throw new ComercioException( e );
        }
        return lista;
    }

}
