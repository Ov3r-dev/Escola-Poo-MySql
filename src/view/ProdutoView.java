package view;

import controller.ProdutoController;
import model.Produto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoView extends JFrame {

    private JTextField txtNome, txtDescricao, txtQuantidade, txtPrecoCompra, txtPrecoVenda;
    private JComboBox<String> cboCategoria;
    private ProdutoController produtoController;

    public ProdutoView() {
        setTitle("Cadastro de Produto");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        produtoController = new ProdutoController();

        // Layout do formulário
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Nome do Produto:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        add(txtDescricao);

        add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        add(txtQuantidade);

        add(new JLabel("Preço de Compra:"));
        txtPrecoCompra = new JTextField();
        add(txtPrecoCompra);

        add(new JLabel("Preço de Venda:"));
        txtPrecoVenda = new JTextField();
        add(txtPrecoVenda);

        add(new JLabel("Categoria:"));
        cboCategoria = new JComboBox<>(new String[] {"Categoria 1", "Categoria 2", "Categoria 3"});
        add(cboCategoria);

        JButton btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);

        // Ação do botão Cadastrar
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarProduto();
            }
        });
    }

    private void cadastrarProduto() {
        // Obter dados do formulário
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();
        int quantidade = Integer.parseInt(txtQuantidade.getText());
        double precoCompra = Double.parseDouble(txtPrecoCompra.getText());
        double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
        int categoriaId = cboCategoria.getSelectedIndex() + 1; // Apenas para exemplo

        // Criar objeto Produto e enviar para o controlador
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setQuantidade(quantidade);
        produto.setPrecoCompra(precoCompra);
        produto.setPrecoVenda(precoVenda);
        produto.setCategoriaId(categoriaId);

        produtoController.cadastrarProduto(produto);

        JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProdutoView().setVisible(true);
        });
    }
}
