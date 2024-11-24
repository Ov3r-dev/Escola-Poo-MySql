package view;

import controller.CategoriaController;
import model.Categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriaView extends JFrame {

    private JTextField txtNome, txtDescricao, txtCategoriaId;
    private JComboBox<String> cboCategoria;
    private CategoriaController categoriaController;

    public CategoriaView() {
        setTitle("Cadastro, Edição, Exclusão e Consulta de Categoria");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        categoriaController = new CategoriaController();

        // Layout do formulário
        setLayout(new GridLayout(6, 2));

        // Campos para cadastrar ou editar categoria
        add(new JLabel("Nome da Categoria:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        add(txtDescricao);

        JButton btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);

        // Ação do botão Cadastrar
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCategoria();
            }
        });

        // Campos para editar categoria
        add(new JLabel("ID da Categoria (para editar):"));
        txtCategoriaId = new JTextField();
        add(txtCategoriaId);

        JButton btnEditar = new JButton("Editar");
        add(btnEditar);

        // Ação do botão Editar
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCategoria();
            }
        });

        // Campos para excluir categoria
        JButton btnExcluir = new JButton("Excluir");
        add(btnExcluir);

        // Ação do botão Excluir
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCategoria();
            }
        });

        // Mostrar todas as categorias no ComboBox
        add(new JLabel("Categorias:"));
        cboCategoria = new JComboBox<>();
        atualizarCategorias();
        add(cboCategoria);
    }

    // Método para cadastrar categoria
    private void cadastrarCategoria() {
        // Obter dados do formulário
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        // Criar objeto Categoria e enviar para o controlador
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(descricao);

        categoriaController.cadastrarCategoria(categoria);

        JOptionPane.showMessageDialog(this, "Categoria cadastrada com sucesso!");
        atualizarCategorias(); // Atualizar ComboBox
    }

    // Método para editar categoria
    private void editarCategoria() {
        // Obter dados do formulário
        int categoriaId = Integer.parseInt(txtCategoriaId.getText());
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        Categoria categoria = categoriaController.consultarCategoriaPorId(categoriaId);
        if (categoria != null) {
            categoria.setNome(nome);
            categoria.setDescricao(descricao);
            categoriaController.editarCategoria(categoria);

            JOptionPane.showMessageDialog(this, "Categoria editada com sucesso!");
            atualizarCategorias(); // Atualizar ComboBox
        } else {
            JOptionPane.showMessageDialog(this, "Categoria não encontrada.");
        }
    }

    // Método para excluir categoria
    private void excluirCategoria() {
        int categoriaId = Integer.parseInt(txtCategoriaId.getText());
        categoriaController.excluirCategoria(categoriaId);
        JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
        atualizarCategorias(); // Atualizar ComboBox
    }

    // Atualizar o JComboBox com todas as categorias
    private void atualizarCategorias() {
        cboCategoria.removeAllItems();
        List<Categoria> categorias = categoriaController.consultarTodasCategorias();
        for (Categoria categoria : categorias) {
            cboCategoria.addItem(categoria.getNome());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CategoriaView().setVisible(true);
        });
    }
}
