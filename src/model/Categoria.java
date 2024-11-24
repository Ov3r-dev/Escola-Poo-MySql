package model;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;


public class Categoria {
    private int id;
    private String nome;
    private String descricao;

    // Getters e Setters


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public class cadastrarCategoria {

        public void cadastrarCategoria(Categoria categoria) {
            String sql = "{CALL cadastrar_categoria(?, ?)}";  // Chama a stored procedure

            try (Connection conn = DatabaseConnection.getConnection(); // Estabelecendo a conexão com o banco de dados
                 CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setString(1, categoria.getNome());   // Passa o nome da categoria
                stmt.setString(2, categoria.getDescricao()); // Passa a descrição da categoria

                stmt.execute(); // Executa a stored procedure

                System.out.println("Categoria cadastrada com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erro ao cadastrar a categoria!");
            }
        }
    }

}