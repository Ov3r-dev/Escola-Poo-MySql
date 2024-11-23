package controller;

import database.DatabaseConnection;

import java.sql.*;

public class MovimentacaoController {

    public void registrarMovimentacao(int produtoId, String tipoMovimentacao, int quantidade) {
        String sql = "INSERT INTO movimentacao_estoque(produto_id, tipo_movimentacao, quantidade, data_movimentacao) VALUES(?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            stmt.setString(2, tipoMovimentacao);
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
