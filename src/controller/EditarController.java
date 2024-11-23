package controller;
import model.Produto;
import database.DatabaseConnection;

import java.sql.*;

public class EditarController {

    public void EditarProduto(Produto produto) {
        String sql = "{CALL cadastrar_produto(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setDouble(4, produto.getPrecoCompra());
            stmt.setDouble(5, produto.getPrecoVenda());
            stmt.setInt(6, produto.getCategoriaId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
