package view;

import controller.ProdutoController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntradaSaidaView extends JFrame {

    private JTextField txtProdutoId, txtQuantidade;
    private ProdutoController produtoController;

    public EntradaSaidaView() {
        setTitle("Registro de Entrada e Saída de Produtos");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        produtoController = new ProdutoController();

        // Layout do formulário
        setLayout(new GridLayout(4, 2));

        add(new JLabel("ID do Produto:"));
        txtProdutoId = new JTextField();
        add(txtProdutoId);

        add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField();
        add(txtQuantidade);

        JButton btnEntrada = new JButton("Registrar Entrada");
        add(btnEntrada);

        JButton btnSaida = new JButton("Registrar Saída");
        add(btnSaida);

        // Ação do botão Registrar Entrada
        btnEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEntrada();
            }
        });

        // Ação do botão Registrar Saída
        btnSaida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarSaida();
            }
        });
    }

    private void registrarEntrada() {
        try {
            int produtoId = Integer.parseInt(txtProdutoId.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            // Verifica se a quantidade é maior que zero
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chama o método do controller para registrar a entrada
            produtoController.registrarEntradaProduto(produtoId, quantidade);
            JOptionPane.showMessageDialog(this, "Entrada registrada com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o ID do produto e a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarSaida() {
        try {
            int produtoId = Integer.parseInt(txtProdutoId.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            // Verifica se a quantidade é maior que zero
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chama o método do controller para registrar a saída
            produtoController.registrarSaidaProduto(produtoId, quantidade);
            JOptionPane.showMessageDialog(this, "Saída registrada com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o ID do produto e a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EntradaSaidaView().setVisible(true);
        });
    }
}
