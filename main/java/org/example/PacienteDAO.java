package org.example;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PacienteDAO {

    private static final Map<String, String> queries = new HashMap<>();

    static {
        queries.put("DATABASE_FILE", "Pacientes.db");
        queries.put("CREATE_TABLE_SQL", "CREATE TABLE IF NOT EXISTS Pacientes " + "(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, celular TEXT, genero TEXT, idade INTEGER, tratamento TEXT, dia TEXT)");
        queries.put("INSERT_Paciente_SQL", "INSERT INTO Pacientes (nome, celular, genero, idade, tratamento, dia) VALUES (?,?,?,?,?,?)");
        queries.put("SELECT_ALL_Pacientes_SQL", "SELECT * FROM Pacientes");
        queries.put("UPDATE_Paciente_SQL", "UPDATE Pacientes SET nome = ?, celular = ?, genero = ?, idade = ?, tratamento = ?, dia = ? WHERE id = ?");
        queries.put("DELETE_Paciente_SQL", "DELETE FROM Pacientes WHERE id = ?");
        queries.put("SELECT_Paciente_BY_ID_SQL", "SELECT * FROM Pacientes WHERE id = ?");
    }

    public static String getQuery(String queryKey) {
        return queries.get(queryKey);
    }

    private static Connection connection;

    static {
        try {
            iniciarConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados:");
            e.printStackTrace();
        }
    }

    private static void iniciarConexao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + getQuery("DATABASE_FILE"));
    }


    public static void fecharConexao() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarTabela() {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = PacienteDAO.getQuery("CREATE_TABLE_SQL");
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarPaciente(Pacientes paciente) {
        try (PreparedStatement statement = connection.prepareStatement(getQuery("INSERT_Paciente_SQL"))) {
            statement.setString(1, paciente.getNome());
            statement.setString(2, paciente.getCelular());
            statement.setString(3, paciente.getGenero());
            statement.setInt(4, paciente.getIdade());
            statement.setString(5, paciente.getTratamento());
            statement.setString(6, paciente.getDia());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void atualizarPaciente(Pacientes paciente) {
        try (PreparedStatement statement = connection.prepareStatement(getQuery("UPDATE_Paciente_SQL"))) {
            statement.setString(1, paciente.getNome());
            statement.setString(2, paciente.getCelular());
            statement.setString(3, paciente.getGenero());
            statement.setInt(4, paciente.getIdade());
            statement.setString(5, paciente.getTratamento());
            statement.setString(6, paciente.getDia());
            statement.setInt(7, paciente.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Pacientes> listarPacientes() {
        List<Pacientes> pacientes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getQuery("SELECT_ALL_Pacientes_SQL"))) {
            while (resultSet.next()) {
                Pacientes paciente = new Pacientes();
                paciente.setId(resultSet.getInt("id"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setCelular(resultSet.getString("celular"));
                paciente.setGenero(resultSet.getString("genero"));
                paciente.setIdade(resultSet.getInt("idade"));
                paciente.setTratamento(resultSet.getString("tratamento"));
                paciente.setDia(resultSet.getString("dia"));

                paciente.setFaixaEtaria();
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public static Pacientes buscarPacientePorId(int id) {
        Pacientes paciente = null;
        try (PreparedStatement statement = connection.prepareStatement(getQuery("SELECT_Paciente_BY_ID_SQL"))) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    paciente = new Pacientes(
                            resultSet.getInt("id"),
                            resultSet.getString("nome"),
                            resultSet.getString("tratamento"),
                            resultSet.getString("celular"),
                            resultSet.getString("genero"),
                            resultSet.getInt("idade"),
                            resultSet.getString("dia")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    public static void excluirPaciente(int id) {
        try (PreparedStatement statement = connection.prepareStatement(getQuery("DELETE_Paciente_SQL"))) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
