
package view;

import controller.ProdutoController;
import model.Produto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ConsultaView extends JFrame {

    private JTextField txtNome, txtCategoria, txtQuantidadeMinima;
    private JTable table;
    private DefaultTableModel tableModel;
    private ProdutoController produtoController;

    public ConsultaView() {
        setTitle("Consulta de Produtos");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        produtoController = new ProdutoController();

        // Layout do formulário
        setLayout(new BorderLayout());

        // Painel de filtros
        JPanel filterPanel = new JPanel(new GridLayout(4, 2));

        filterPanel.add(new JLabel("Nome do Produto:"));
        txtNome = new JTextField();
        filterPanel.add(txtNome);

        filterPanel.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        filterPanel.add(txtCategoria);

        filterPanel.add(new JLabel("Quantidade Mínima:"));
        txtQuantidadeMinima = new JTextField();
        filterPanel.add(txtQuantidadeMinima);

        JButton btnPesquisar = new JButton("Pesquisar");
        filterPanel.add(btnPesquisar);
        add(filterPanel, BorderLayout.NORTH);

        // Tabela para exibir os resultados
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

        // Ação do botão Pesquisar
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarProdutos();
            }
        });
    }

    private void consultarProdutos() {
        // Obter dados dos filtros
        String nome = txtNome.getText();
        String categoria = txtCategoria.getText();
        String quantidadeMinimaText = txtQuantidadeMinima.getText();
        int quantidadeMinima = quantidadeMinimaText.isEmpty() ? 0 : Integer.parseInt(quantidadeMinimaText);

        // Buscar produtos com base nos filtros
        List<Produto> produtos = produtoController.consultarProdutos(nome, categoria, quantidadeMinima);

        // Limpar a tabela antes de adicionar os resultados
        tableModel.setRowCount(0);

        // Adicionar os produtos encontrados na tabela
        for (Produto produto : produtos) {
            Object[] row = {
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getQuantidade(),
                    produto.getPrecoCompra(),
                    produto.getPrecoVenda(),
                    produto.getCategoriaId()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultaView().setVisible(true);
        });
    }
}
