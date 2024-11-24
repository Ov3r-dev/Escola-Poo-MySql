package controller;

import model.Produto;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {

    // Consulta para obter todos os produtos
    public List<Produto> gerarRelatorioProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, quantidade, preco_compra, preco_venda, categoria_id FROM produto";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

    // Relatório de movimentação de estoque (entradas e saídas)
    public List<String> gerarRelatorioMovimentacao() {
        List<String> movimentacoes = new ArrayList<>();
        String sql = "SELECT p.nome, m.quantidade, m.tipo_operacao, m.data_movimentacao " +
                "FROM movimentacao_estoque m " +
                "JOIN produto p ON m.produto_id = p.id " +
                "ORDER BY m.data_movimentacao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String movimentacao = "Produto: " + rs.getString("nome") +
                        ", Quantidade: " + rs.getInt("quantidade") +
                        ", Tipo: " + rs.getString("tipo_operacao") +
                        ", Data: " + rs.getString("data_movimentacao");
                movimentacoes.add(movimentacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movimentacoes;
    }

    // Relatório de produtos com baixo estoque (quantidade < 10)
    public List<Produto> gerarRelatorioBaixoEstoque() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, quantidade, preco_compra, preco_venda, categoria_id " +
                "FROM produto WHERE quantidade < 10";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

    // Relatório de vendas e lucro (baseado no preço de compra e venda)
    public List<String> gerarRelatorioVendasLucro() {
        List<String> vendasLucro = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.preco_compra, p.preco_venda, " +
                "SUM(CASE WHEN m.tipo_operacao = 'Saída' THEN m.quantidade ELSE 0 END) AS total_vendido, " +
                "SUM(CASE WHEN m.tipo_operacao = 'Saída' THEN (m.quantidade * (p.preco_venda - p.preco_compra)) ELSE 0 END) AS lucro " +
                "FROM produto p " +
                "LEFT JOIN movimentacao_estoque m ON p.id = m.produto_id " +
                "GROUP BY p.id, p.nome, p.preco_compra, p.preco_venda";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String relatorio = "Produto: " + rs.getString("nome") +
                        ", Total Vendido: " + rs.getInt("total_vendido") +
                        ", Lucro: R$" + rs.getDouble("lucro");
                vendasLucro.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendasLucro;
    }
}
