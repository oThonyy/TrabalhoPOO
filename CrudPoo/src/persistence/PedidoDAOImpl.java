package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Pedido;
import tools.ComercioException;

public class PedidoDAOImpl implements PedidoDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/comerciodb";
    private final static String DB_USER = "thony";
    private final static String DB_PASS = "123456";

    private Connection con = null;

    public PedidoDAOImpl() throws ComercioException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new ComercioException( e );
        }
    }

    @Override
    public void inserir(Pedido p) throws ComercioException {
        try { 
            String SQL = """
                    INSERT INTO pedidos (pedidoId, produtos, dataPedido, valorTotal, formaPag, statusPed)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong(1, p.getPedidoId());
            stm.setString(2, p.getProdutos());
            stm.setString(3, p.getDataPedido());
            stm.setString(4, p.getValorTotal());
            stm.setString(5, p.getFormaPag());
            stm.setString(6, p.getStatusPed());

            int rowsAffected = stm.executeUpdate(); // Verifica quantas linhas foram afetadas
            System.out.println("Linhas inseridas: " + rowsAffected);

        } catch (SQLException e) { 
            throw new ComercioException( e );
        }
    }

    @Override
    public void atualizar(Pedido p) throws ComercioException {
        try { 
            String SQL = """
                    UPDATE pedidos SET produtos = ?, dataPedido = ?, valorTotal = ?, formaPag = ?, statusPed = ?,
                    WHERE pedidoId = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, p.getProdutos());
            stm.setString(2, p.getDataPedido());
            stm.setString(3, p.getValorTotal());
            stm.setString(4, p.getFormaPag());
            stm.setString(5, p.getStatusPed());
            stm.setLong(6, p.getPedidoId());
            
            stm.executeUpdate();

        } catch (SQLException e) { 
            throw new ComercioException( "Erro ao atualizar o Pedido" );
        }        
    }

    @Override
    public void remover(Pedido p) throws ComercioException {
        try { 
            String SQL = """
                    DELETE FROM pedidos WHERE pedidoId = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong( 1, p.getPedidoId() );
            
            stm.executeUpdate();

        } catch (SQLException e) { 
            throw new ComercioException( "Erro ao remover Pedido" );
        }
    }

    @Override
    public List<Pedido> pesquisarPorNome(String nome) throws ComercioException {
        List<Pedido> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM pedidos WHERE produtos LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Pedido p = new Pedido();
                p.setPedidoId( rs.getLong("pedidoId") );
                p.setProdutos( rs.getString("produtos") );
                p.setDataPedido( rs.getString("dataPedido") );
                p.setValorTotal( rs.getString("valorTotal") );
                p.setFormaPag( rs.getString("formaPag"));
                p.setStatusPed( rs.getString ( "statusPed"));

                lista.add( p );
            }
        } catch (SQLException e) { 
            throw new ComercioException( e );
        }
        return lista;
    }
    
}