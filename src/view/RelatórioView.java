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
            RelatórioView relatórioView = new RelatórioView();
            relatórioView.setVisible(true);
        });
    }
}



