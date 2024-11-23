package view;

import controller.CategoriaController;
import model.Categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriaView extends JFrame {

    private JTextField txtNome, txtDescricao;
    private CategoriaController categoriaController;

    public CategoriaView() {
        setTitle("Cadastro de Categoria");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        categoriaController = new CategoriaController();

        // Layout do formulário
        setLayout(new GridLayout(3, 2));

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
    }

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CategoriaView().setVisible(true);
        });
    }
}
