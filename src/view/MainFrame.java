package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Gerenciador de Estoque");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Produto
        JMenu menuProduto = new JMenu("Produto");

        JMenuItem itemCadastrarProduto = new JMenuItem("Cadastrar Produto");
        itemCadastrarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdutoView().setVisible(true);
            }
        });
        menuProduto.add(itemCadastrarProduto);

        JMenuItem itemGerenciarProdutos = new JMenuItem("Gerenciar Produtos");
        itemGerenciarProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarView().setVisible(true);
            }
        });
        menuProduto.add(itemGerenciarProdutos); // Adiciona o item de Gerenciar Produtos ao menu

        // Menu Categoria
        JMenu menuCategoria = new JMenu("Categoria");
        JMenuItem itemCadastrarCategoria = new JMenuItem("Cadastrar Categoria");
        itemCadastrarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CategoriaView().setVisible(true);
            }
        });
        menuCategoria.add(itemCadastrarCategoria);

        // Menu Relatório
        JMenu menuRelatorio = new JMenu("Relatório");
        JMenuItem itemRelatorio = new JMenuItem("Obter Relatório");
        itemRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatórioView().setVisible(true);
            }
        });
        menuRelatorio.add(itemRelatorio);

        // Adiciona menus à barra de menus
        menuBar.add(menuProduto);
        menuBar.add(menuCategoria);
        menuBar.add(menuRelatorio);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
