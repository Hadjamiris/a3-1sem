package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
    private static final String USER = "root"; // ou seu usuário
    private static final String PASSWORD = "root"; // troque pela sua senha do MySQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection conexao = getConnection();
            System.out.println("✅ Conectado com sucesso ao banco de dados!");
            conexao.close();
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}
