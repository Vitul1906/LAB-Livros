package br.vitor_costa_lemos.acervo.repositorio;
import br.vitor_costa_lemos.acervo.entidade.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    // URL de conexão com o banco de dados Supabase
    private static final String URL = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:5432/postgres?user=postgres.pnawqjhztpbrhlduwzsz&password=[YOUR-PASSWORD]";

    /**
     * Salva um novo livro no banco de dados.
     */
    public void save(Livro livro) {
        // Comando SQL para inserir um novo livro
        String sql = "INSERT INTO livros (titulo, autor, ano_publicacao, editora) VALUES (?, ?, ?, ?)";
        
        // Tenta estabelecer uma conexão com o banco de dados
        try (Connection conn = DriverManager.getConnection(URL);
             // Prepara o comando SQL com parâmetros dinâmicos
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Define os valores dos parâmetros da consulta SQL
            stmt.setString(1, livro.getTitulo()); // Define o primeiro parâmetro como o título do livro
            stmt.setString(2, livro.getAutor()); // Define o segundo parâmetro como o autor
            stmt.setInt(3, livro.getAnoPublicacao()); // Define o terceiro parâmetro como o ano de publicação
            stmt.setString(4, livro.getEditora()); // Define o quarto parâmetro como a editora
            
            // Executa a inserção no banco de dados
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Exibe erros caso ocorram
        }
    }

    /**
     * Recupera todos os livros do banco de dados.
     * @return Lista de livros cadastrados
     */
    public List<Livro> findAll() {
        List<Livro> livros = new ArrayList<>();
        // Comando SQL para selecionar todos os livros da tabela
        String sql = "SELECT * FROM livros";
        
        try (Connection conn = DriverManager.getConnection(URL);
             // Cria um objeto Statement para executar a consulta
             Statement stmt = conn.createStatement();
             // Executa a consulta e armazena os resultados em um ResultSet
             ResultSet resultado = stmt.executeQuery(sql)) {
            
            // Itera sobre os resultados e cria objetos Livro
            while (resultado.next()) {
                Long id = resultado.getLong("id"); // Obtém o ID do livro
                String titulo = resultado.getString("titulo"); // Obtém o título
                String autor = resultado.getString("autor"); // Obtém o autor
                int anoPublicacao = resultado.getInt("ano_publicacao"); // Obtém o ano de publicação
                String editora = resultado.getString("editora"); // Obtém a editora
                
                // Adiciona o livro à lista
                livros.add(new Livro(id, titulo, autor, anoPublicacao, editora));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
    
    /**
     * Verifica se um livro com determinado título e autor já existe no banco de dados.
     * @param titulo O título do livro
     * @param autor O autor do livro
     * @return true se o livro já existir, false caso contrário
     */
    public boolean existsByTituloAndAutor(String titulo, String autor) {
        // Comando SQL para verificar se um livro com o mesmo título e autor já existe
        String sql = "SELECT COUNT(*) FROM livros WHERE titulo = ? AND autor = ?";
        
        try (Connection conn = DriverManager.getConnection(URL);
             // Prepara o comando SQL com parâmetros dinâmicos
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Define os parâmetros da consulta
            stmt.setString(1, titulo); // Define o primeiro parâmetro como o título do livro
            stmt.setString(2, autor); // Define o segundo parâmetro como o autor
            
            // Executa a consulta e obtém o resultado
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) { // Verifica se há resultado
                    int count = resultado.getInt(1); // Obtém o número de registros encontrados
                    return count > 0; // Retorna true se pelo menos um registro existir
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
