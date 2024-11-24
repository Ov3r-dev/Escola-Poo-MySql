package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import database.DatabaseConnection;

public class RelatórioView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private final String sql = "SELECT id, nome, descricao, quantidade, preco_compra, preco_venda, categoria_id FROM produto";

    public RelatórioView() {
        setTitle("Obter Relatório");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Preço Compra");
        tableModel.addColumn("Preço Venda");
        tableModel.addColumn("Categoria ID");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection(); // Usando a classe de conexão existente
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            tableModel.setRowCount(0);

            boolean estoqueBaixo = false;  // Flag para verificar se algum produto tem estoque abaixo de 5

            while (resultSet.next()) {
                int quantidade = resultSet.getInt("quantidade");
                if (quantidade < 5) {
                    estoqueBaixo = true; // Marcar que existe algum produto com estoque baixo
                }

                Object[] row = {
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("descricao"),
                        quantidade,
                        resultSet.getDouble("preco_compra"),
                        resultSet.getDouble("preco_venda"),
                        resultSet.getInt("categoria_id")
                };
                tableModel.addRow(row);
            }

            // Se algum produto tiver estoque abaixo de 5, exibe um alerta
            if (estoqueBaixo) {
                JOptionPane.showMessageDialog(this, "Alerta: Alguns produtos estão com estoque abaixo do mínimo (menos de 5 itens).",
                        "Alerta de Estoque", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do banco: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RelatórioView relatórioView = new RelatórioView();
            relatórioView.setVisible(true);
        });
    }
}
