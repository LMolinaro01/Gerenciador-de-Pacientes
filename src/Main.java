import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Carregar o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelecer a conexão com o banco de dados MySQL
            String url = "jdbc:mysql://localhost:3306/database";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Conexão bem-sucedida com MySQL!");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Falha na conexão com o banco de dados: " + e.getMessage());
        } finally {
            // Fechar a conexão no bloco finally para garantir que ela seja sempre fechada, mesmo se ocorrer uma exceção
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Falha ao fechar a conexão: " + e.getMessage());
                }
            }
        }

        Pacientes p1 = new Pacientes("Leonardo", "Acupuntura", "21988998899", "Masculino", 19, "Fabio", "1 por semana");
        System.out.println(p1.toString());
    }
}
