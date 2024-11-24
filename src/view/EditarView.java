package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import database.DatabaseConnection;

public class EditarView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public EditarView() {
        setTitle("Gerenciamento de Produtos");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Aba para Editar e Excluir Produtos
        JPanel editarExcluirPanel = new JPanel(new BorderLayout());
        editarExcluirPanel.add(createEditarExcluirPanel(), BorderLayout.CENTER);
        tabbedPane.addTab("Editar e Excluir Produtos", editarExcluirPanel);

        add(tabbedPane);
    }

    private JPanel createEditarExcluirPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Colunas da tabela
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Preço Compra");
        tableModel.addColumn("Preço Venda");
        tableModel.addColumn("Categoria ID");

        loadDataFromDatabase();

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botões de Edição e Exclusão
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar Produto");
        JButton deleteButton = new JButton("Excluir Produto");

        editButton.addActionListener(e -> editarProduto());
        deleteButton.addActionListener(e -> excluirProduto());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadDataFromDatabase() {
        String sql = "SELECT id, nome, descricao, quantidade, preco_compra, preco_venda, categoria_id FROM produto";
        try (Connection connection = DatabaseConnection.getConnection();
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

    private void editarProduto() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obter dados da linha selecionada
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);
        String descricao = (String) tableModel.getValueAt(selectedRow, 2);
        int quantidade = (int) tableModel.getValueAt(selectedRow, 3);
        double precoCompra = (double) tableModel.getValueAt(selectedRow, 4);
        double precoVenda = (double) tableModel.getValueAt(selectedRow, 5);
        int categoriaId = (int) tableModel.getValueAt(selectedRow, 6);

        // Exibir formulário de edição
        JTextField nomeField = new JTextField(nome);
        JTextField descricaoField = new JTextField(descricao);
        JTextField quantidadeField = new JTextField(String.valueOf(quantidade));
        JTextField precoCompraField = new JTextField(String.valueOf(precoCompra));
        JTextField precoVendaField = new JTextField(String.valueOf(precoVenda));
        JTextField categoriaField = new JTextField(String.valueOf(categoriaId));

        Object[] fields = {
                "Nome:", nomeField,
                "Descrição:", descricaoField,
                "Quantidade:", quantidadeField,
                "Preço de Compra:", precoCompraField,
                "Preço de Venda:", precoVendaField,
                "Categoria ID:", categoriaField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Editar Produto", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String updateSQL = "UPDATE produto SET nome = ?, descricao = ?, quantidade = ?, preco_compra = ?, preco_venda = ?, categoria_id = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
                    preparedStatement.setString(1, nomeField.getText());
                    preparedStatement.setString(2, descricaoField.getText());
                    preparedStatement.setInt(3, Integer.parseInt(quantidadeField.getText()));
                    preparedStatement.setDouble(4, Double.parseDouble(precoCompraField.getText()));
                    preparedStatement.setDouble(5, Double.parseDouble(precoVendaField.getText()));
                    preparedStatement.setInt(6, Integer.parseInt(categoriaField.getText()));
                    preparedStatement.setInt(7, id);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                    loadDataFromDatabase();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void excluirProduto() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza de que deseja excluir o produto selecionado?", "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String deleteSQL = "DELETE FROM produto WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                    loadDataFromDatabase();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditarView view = new EditarView();
            view.setVisible(true);
        });
    }
}