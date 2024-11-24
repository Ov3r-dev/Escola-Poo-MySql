package view;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class EntradaSaidaView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    // SQL para pegar todas as movimentações de estoque
    private final String sql = "SELECT p.nome, m.quantidade, m.tipo, m.data_movimentacao " +
            "FROM movimentacao_estoque m " +
            "JOIN produto p ON m.produto_id = p.id " +
            "ORDER BY m.data_movimentacao DESC";

    public EntradaSaidaView() {
        setTitle("Relatório de Operações");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura a tabela e seu modelo de dados
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Definindo as colunas da tabela
        tableModel.addColumn("Produto");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Tipo de Movimentação");
        tableModel.addColumn("Data");

        // Adiciona a tabela em um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Carrega os dados da movimentação de estoque no banco de dados
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Limpa as linhas da tabela antes de adicionar os novos dados
            tableModel.setRowCount(0);

            // Preenche a tabela com os dados de movimentação de estoque
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("nome"),            // Nome do produto
                        resultSet.getInt("quantidade"),         // Quantidade
                        resultSet.getString("tipo"),            // Tipo da operação (entrada/saída)
                        resultSet.getTimestamp("data")          // Data da movimentação
                };
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do banco: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Exibe a janela do relatório de operações
            EntradaSaidaView relatorioView = new EntradaSaidaView();
            relatorioView.setVisible(true);
        });
    }
}
