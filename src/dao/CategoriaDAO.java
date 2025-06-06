package dao;

import model.Categoria;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.executeUpdate();

            // Obter o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir categoria: " + e.getMessage());
        }
    }

    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTamanho(rs.getString("tamanho"));
                c.setEmbalagem(rs.getString("embalagem"));
                categorias.add(c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar categorias: " + e.getMessage());
        }
        return categorias;
    }

    public void atualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ?";
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.setInt(4, categoria.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sqlVerifica = "SELECT COUNT(*) FROM produto WHERE id_categoria = ?";
        String sqlExclui = "DELETE FROM categoria WHERE id = ?";
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica);
             PreparedStatement stmtExclui = conn.prepareStatement(sqlExclui)) {
            stmtVerifica.setInt(1, id);
            ResultSet rs = stmtVerifica.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException("Não é possível excluir a categoria, pois ela está associada a produtos.");
            }
            stmtExclui.setInt(1, id);
            stmtExclui.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir categoria: " + e.getMessage());
        }
    }
}