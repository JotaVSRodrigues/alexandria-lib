package org.example.connection;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Source;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:alexandria.db";
    private static final String SCHEMA_FILE = "src/main/resources/schema.sql";
    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(true);

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;"); // isso habilita foreign keys no SQLite
            }
        }
        return connection;
    }

    // Inicializa o banco executando o schema.sql
    public static void initializeDatabase(){
        try {
            System.out.println("Inicializando o banco de dados...");
            Connection connection = getConnection();

            String schema = readSchemaFile(); // lê o arquivo schema.sql
            String[] commands = schema.split(";"); // executa cada comando separadamente

            try (Statement stmt = connection.createStatement()) {
                for (String command : commands) {
                    String trimmed = command.trim(); // comandos vazios
                    if (!trimmed.isEmpty() && !trimmed.startsWith("--")) { // para não executar linhas vazias ou comentários SQL
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readSchemaFile() {
        try {
            var resource = DatabaseManager.class.getClassLoader()
                    .getResourceAsStream("schema.sql");

            if (resource != null) {
                return new String(resource.readAllBytes());
            }

            return Files.readString(Paths.get(SCHEMA_FILE));
        } catch (IOException e) {
            System.err.println("Erro ao ler schema.sql: " + e.getMessage());
            return getDefaultSchema();
        }
    }

    private static String getDefaultSchema() {
        return """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title VARCHAR(80) NOT NULL,
                author VARCHAR(80),
                data_release INTEGER,
                genre VARCHAR(80),
                pages INTEGER,
                rating VARCHAR(10),
                isbn VARCHAR(80) UNIQUE,
                description TEXT,
                google_books_id TEXT,
                added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            
            CREATE INDEX IF NOT EXISTS idx_books_title ON books(title);
            CREATE INDEX IF NOT EXISTS idx_books_isbn ON books(isbn);
            """;
    }

    public static void closeConnection(){
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com o banco de dados: " + e.getMessage());
        }
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT 1")) {
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro de teste de conexão com o banco: " + e.getMessage());
            return false;
        }
    }
}
