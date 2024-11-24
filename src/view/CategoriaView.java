
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
    private JTextArea txtAreaCategorias;
    private CategoriaController categoriaController;

    public CategoriaView() {
        setTitle("Cadastro e Gerenciamento de Categorias");
        setSize(400, 400); // Ajuste do tamanho
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        categoriaController = new CategoriaController();

        // Usando BoxLayout para alinhar os componentes verticalmente
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Criando componentes e organizando
        criarCamposCadastro();
        criarCamposExclusao();
        criarCamposEdicao();
        criarBotaoConsultar();
        criarAreaExibicao();

        // Definir o espaço entre os componentes
        setSpacing();
    }

    // Criar campos de cadastro
    private void criarCamposCadastro() {
        // Nome da categoria
        add(new JLabel("Nome da Categoria:"));
        txtNome = new JTextField(20);
        add(txtNome);

        // Descrição da categoria
        add(new JLabel("Descrição:"));
        txtDescricao = new JTextField(20);
        add(txtDescricao);

        // Botão para cadastrar
        JButton btnCadastrar = new JButton("Cadastrar Categoria");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCategoria();
            }
        });
        add(btnCadastrar);
    }

    // Criar campos para exclusão de categoria
    private void criarCamposExclusao() {
        add(new JLabel("ID da Categoria (para excluir):"));
        txtCategoriaId = new JTextField(20);
        add(txtCategoriaId);

        // Botão para excluir categoria
        JButton btnExcluir = new JButton("Excluir Categoria");
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCategoria();
            }
        });
        add(btnExcluir);
    }

    // Criar campos para edição de categoria
    private void criarCamposEdicao() {
        add(new JLabel("ID da Categoria (para editar):"));
        JTextField txtCategoriaIdEdicao = new JTextField(20);
        add(txtCategoriaIdEdicao);

        // Botão para editar categoria
        JButton btnEditar = new JButton("Editar Categoria");
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCategoria(txtCategoriaIdEdicao);
            }
        });
        add(btnEditar);
    }

    // Criar botão para consultar categorias cadastradas
    private void criarBotaoConsultar() {
        JButton btnConsultar = new JButton("Consultar Categorias");
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarCategorias();
            }
        });
        add(btnConsultar);
    }

    // Criar área de texto para exibir as categorias
    private void criarAreaExibicao() {
        txtAreaCategorias = new JTextArea(10, 30);
        txtAreaCategorias.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaCategorias);
        add(scrollPane);
    }

    // Método para definir o espaçamento entre os componentes
    private void setSpacing() {
        add(Box.createVerticalStrut(10));  // Espaço entre os campos
        add(Box.createVerticalStrut(10));  // Espaço entre os botões
    }

    // Método para cadastrar categoria
    private void cadastrarCategoria() {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (nome.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(descricao);

        categoriaController.cadastrarCategoria(categoria);

        JOptionPane.showMessageDialog(this, "Categoria cadastrada com sucesso!");
        limparCampos();
    }

    // Método para excluir categoria
    private void excluirCategoria() {
        String categoriaIdText = txtCategoriaId.getText();

        if (categoriaIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o ID da categoria.");
            return;
        }

        try {
            int categoriaId = Integer.parseInt(categoriaIdText);
            categoriaController.excluirCategoria(categoriaId);
            JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido.");
        }
    }

    // Método para editar categoria
    private void editarCategoria(JTextField txtCategoriaIdEdicao) {
        String categoriaIdText = txtCategoriaIdEdicao.getText();

        if (categoriaIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o ID da categoria.");
            return;
        }

        try {
            int categoriaId = Integer.parseInt(categoriaIdText);
            Categoria categoria = categoriaController.consultarCategoriaPorId(categoriaId);

            if (categoria == null) {
                JOptionPane.showMessageDialog(this, "Categoria não encontrada.");
                return;
            }

            // Editar os dados da categoria
            String novoNome = JOptionPane.showInputDialog(this, "Novo nome para a categoria", categoria.getNome());
            String novaDescricao = JOptionPane.showInputDialog(this, "Nova descrição", categoria.getDescricao());

            categoria.setNome(novoNome);
            categoria.setDescricao(novaDescricao);

            categoriaController.editarCategoria(categoria);
            JOptionPane.showMessageDialog(this, "Categoria editada com sucesso!");

            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido.");
        }
    }

    // Método para consultar todas as categorias cadastradas
    private void consultarCategorias() {
        List<Categoria> categorias = categoriaController.consultarTodasCategorias();

        txtAreaCategorias.setText(""); // Limpar área antes de exibir

        if (categorias.isEmpty()) {
            txtAreaCategorias.append("Nenhuma categoria cadastrada.\n");
        } else {
            for (Categoria categoria : categorias) {
                txtAreaCategorias.append("ID: " + categoria.getId() + " - Nome: " + categoria.getNome() + " - Descrição: " + categoria.getDescricao() + "\n");
            }
        }
    }

    // Método para limpar os campos
    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtCategoriaId.setText("");
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoriaView().setVisible(true));
    }
}
