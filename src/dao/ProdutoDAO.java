package dao;

import model.Categoria;
import model.Produto;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoDAO {

    public void inserir(Produto p) {
        String sql = "INSERT INTO produto (nome, preco_unitario, unidade, quantidade, quantidade_minima, quantidade_maxima, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPrecoUnitario());
            stmt.setString(3, p.getUnidade());
            stmt.setInt(4, p.getQuantidade());
            stmt.setInt(5, p.getQuantidadeMinima());
            stmt.setInt(6, p.getQuantidadeMaxima());
            // Verifica se a categoria é nula antes de tentar pegar o ID
            if (p.getCategoria() != null) {
                stmt.setInt(7, p.getCategoria().getId());
            } else {
                stmt.setNull(7, Types.INTEGER); // Define como NULL no banco se não houver categoria
            }
            stmt.executeUpdate();

            System.out.println("✅ Produto inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir produto: " + e.getMessage());
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nome AS categoria_nome, c.tamanho, c.embalagem FROM produto p LEFT JOIN categoria c ON p.id_categoria = c.id"; // Usar LEFT JOIN para incluir produtos sem categoria

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria c = null;
                // Verifica se há categoria associada antes de criar o objeto Categoria
                if (rs.getObject("id_categoria") != null) {
                    c = new Categoria();
                    c.setId(rs.getInt("id_categoria"));
                    c.setNome(rs.getString("categoria_nome"));
                    c.setTamanho(rs.getString("tamanho"));
                    c.setEmbalagem(rs.getString("embalagem"));
                }

                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco_unitario"));
                p.setUnidade(rs.getString("unidade"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                p.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                p.setCategoria(c); // Pode ser null se não houver categoria

                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE produto SET nome=?, preco_unitario=?, unidade=?, quantidade=?, quantidade_minima=?, quantidade_maxima=?, id_categoria=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPrecoUnitario());
            stmt.setString(3, p.getUnidade());
            stmt.setInt(4, p.getQuantidade());
            stmt.setInt(5, p.getQuantidadeMinima());
            stmt.setInt(6, p.getQuantidadeMaxima());
            // Verifica se a categoria é nula antes de tentar pegar o ID
            if (p.getCategoria() != null) {
                stmt.setInt(7, p.getCategoria().getId());
            } else {
                stmt.setNull(7, Types.INTEGER); // Define como NULL no banco se não houver categoria
            }
            stmt.setInt(8, p.getId());
            stmt.executeUpdate();

            System.out.println("✅ Produto atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("✅ Produto deletado com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao deletar produto: " + e.getMessage());
        }
    }

    // NOVO MÉTODO para contar produtos por categoria
    public Map<String, Integer> contarProdutosPorCategoria() {
        Map<String, Integer> contagemPorCategoria = new HashMap<>();
        String sql = "SELECT c.nome AS categoria_nome, SUM(p.quantidade) AS total_quantidade " +
                "FROM produto p " +
                "JOIN categoria c ON p.id_categoria = c.id " +
                "GROUP BY c.nome";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String categoriaNome = rs.getString("categoria_nome");
                int totalQuantidade = rs.getInt("total_quantidade");
                contagemPorCategoria.put(categoriaNome, totalQuantidade);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao contar produtos por categoria: " + e.getMessage());
            // Pode lançar uma exceção ou retornar um mapa vazio em caso de erro
        }
        return contagemPorCategoria;
    }
}