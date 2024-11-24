package controller;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimentacaoController {

    // Método para registrar movimentação de entrada ou saída
    public void registrarMovimentacao(int produtoId, int quantidade, String tipo) {
        String sql = "INSERT INTO movimentacao_estoque (produto_id, quantidade, tipo_operacao, data_movimentacao) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);   // Produto ID
            stmt.setInt(2, quantidade);  // Quantidade
            stmt.setString(3, tipo);     // Tipo de movimentação (Entrada ou Saída)

            stmt.executeUpdate();  // Executa a atualização no banco
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
