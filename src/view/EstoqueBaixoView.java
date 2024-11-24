
package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import database.DatabaseConnection;

public class EstoqueBaixoView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    // SQL para buscar produtos com estoque <= 5
    private final String sql = "SELECT id, nome, descricao, quantidade, preco_compra, preco_venda, categoria_id " +
            "FROM produto WHERE quantidade <= 5";

    public EstoqueBaixoView() {
        setTitle("Produtos com Estoque Baixo");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuração da tabela
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Adicionando colunas à tabela
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Preço Compra");
        tableModel.addColumn("Preço Venda");
        tableModel.addColumn("Categoria ID");

        // Adicionando a tabela à janela dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar dados do banco de dados
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Limpa a tabela antes de adicionar novos dados
            tableModel.setRowCount(0);

            // Preenchendo a tabela com os dados
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        resultSet.getInt("quantidade"),
                        resultSet.getDouble("preco_compra"),
                        resultSet.getDouble("preco_venda"),
                        resultSet.getInt("categoria_id")
                };
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do banco: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EstoqueBaixoView estoqueBaixoView = new EstoqueBaixoView();
            estoqueBaixoView.setVisible(true);
        });
    }
}
