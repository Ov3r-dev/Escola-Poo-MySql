package view;

import controller.ProdutoController;
import model.Produto;
import controller.CategoriaController;
import model.Categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdutoView extends JFrame {

    private JTextField txtNome, txtDescricao, txtQuantidade, txtPrecoCompra, txtPrecoVenda;
    private JComboBox<String> cboCategoria;
    private ProdutoController produtoController;
    private CategoriaController categoriaController;

    public ProdutoView() {
        setTitle("Cadastro de Produto");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        produtoController = new ProdutoController();
        categoriaController = new CategoriaController();

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
        cboCategoria = new JComboBox<>();
        add(cboCategoria);

        // Carregar categorias ao iniciar
        carregarCategorias();

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

    private void carregarCategorias() {
        List<Categoria> categorias = categoriaController.consultarTodasCategorias();
        for (Categoria categoria : categorias) {
            cboCategoria.addItem(categoria.getNome());
        }
    }

    private void cadastrarProduto() {
        // Obter dados do formulário
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();
        int quantidade = Integer.parseInt(txtQuantidade.getText());
        double precoCompra = Double.parseDouble(txtPrecoCompra.getText());
        double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
        int categoriaId = cboCategoria.getSelectedIndex() + 1; // Usando o índice do JComboBox para obter o ID da categoria

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
