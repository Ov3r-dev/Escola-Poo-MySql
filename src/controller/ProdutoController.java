package controller;

import model.Produto;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    public void cadastrarProduto(Produto produto) {
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

    public List<Produto> consultarProdutos(String nome, String categoria, int quantidadeMinima) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE 1=1"; // Filtro flexível para permitir filtros vazios

        if (nome != null && !nome.isEmpty()) {
            sql += " AND nome LIKE ?";
        }
        if (categoria != null && !categoria.isEmpty()) {
            sql += " AND categoria_id = ?";
        }
        if (quantidadeMinima > 0) {
            sql += " AND quantidade >= ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1;

            if (nome != null && !nome.isEmpty()) {
                stmt.setString(index++, "%" + nome + "%");
            }
            if (categoria != null && !categoria.isEmpty()) {
                stmt.setString(index++, categoria);
            }
            if (quantidadeMinima > 0) {
                stmt.setInt(index++, quantidadeMinima);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setPrecoCompra(rs.getDouble("preco_compra"));
                produto.setPrecoVenda(rs.getDouble("preco_venda"));
                produto.setCategoriaId(rs.getInt("categoria_id"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }
}
