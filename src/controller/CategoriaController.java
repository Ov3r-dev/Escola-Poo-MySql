package controller;

import model.Categoria;
import database.DatabaseConnection;

import java.sql.*;

public class CategoriaController {

    public void cadastrarCategoria(Categoria categoria) {
        String sql = "{CALL cadastrar_categoria(?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
