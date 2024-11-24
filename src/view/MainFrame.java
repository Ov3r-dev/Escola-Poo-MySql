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
                new ProdutoView().setVisible(true); // Abre a tela de cadastro de produto
            }
        });
        menuProduto.add(itemCadastrarProduto);

        JMenuItem itemGerenciarProdutos = new JMenuItem("Gerenciar Produtos");
        itemGerenciarProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditarView().setVisible(true); // Abre a tela de gerenciamento de produtos
            }
        });
        menuProduto.add(itemGerenciarProdutos);

        JMenuItem itemConsultarProdutos = new JMenuItem("Consultar Produtos");
        itemConsultarProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaView().setVisible(true); // Abre a tela de consulta de produtos
            }
        });
        menuProduto.add(itemConsultarProdutos);

        // Menu Categoria
        JMenu menuCategoria = new JMenu("Categoria");
        JMenuItem itemCadastrarCategoria = new JMenuItem("Cadastrar Categoria");
        itemCadastrarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CategoriaView().setVisible(true); // Abre a tela de cadastro e exclusão de categoria
            }
        });
        menuCategoria.add(itemCadastrarCategoria);

        // Menu Relatório
        JMenu menuRelatorio = new JMenu("Relatório");

        // Relatório Geral
        JMenuItem itemRelatorioGeral = new JMenuItem("Relatório Geral");
        itemRelatorioGeral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatórioView().setVisible(true); // Abre a tela de relatório geral
            }
        });
        menuRelatorio.add(itemRelatorioGeral);

        // Relatório de Lucro
        JMenuItem itemLucro = new JMenuItem("Relatório de Lucro");
        itemLucro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LucroView().setVisible(true); // Abre a tela de relatório de lucro
            }
        });
        menuRelatorio.add(itemLucro);

        // Relatório de Estoque Baixo
        JMenuItem itemEstoqueBaixo = new JMenuItem("Estoque Baixo");
        itemEstoqueBaixo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EstoqueBaixoView().setVisible(true); // Abre a tela de estoque baixo
            }
        });
        menuRelatorio.add(itemEstoqueBaixo);

        // Adiciona menus à barra de menus
        menuBar.add(menuProduto);
        menuBar.add(menuCategoria);
        menuBar.add(menuRelatorio);

        setJMenuBar(menuBar); // Define a barra de menus
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true); // Exibe a tela principal
        });
    }
}
