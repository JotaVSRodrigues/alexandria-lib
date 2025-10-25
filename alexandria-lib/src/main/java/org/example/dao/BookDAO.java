package org.example.dao;

import org.example.connection.DatabaseManager;
import org.example.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void insert(Book book){
        String query = "INSERT INTO books (title, author, data_release, genre, pages, " +
                "rating, isbn) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, book.title());
            statement.setString(2, book.author());
            statement.setInt(3, book.dataRelease());
            statement.setString(4, book.genre());
            statement.setInt(5, book.pages());
            statement.setString(6, book.rating());
            statement.setString(7, book.isbn());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByTitle(Book book) {
        String query = "DELETE FROM books WHERE title = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, book.title());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Livro deletado: " + book.title());
            } else {
                System.out.println("Livro não encontrado: " + book.title());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT title, author, data_release, genre, pages, rating, isbn FROM books";

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("data_release"),
                        rs.getString("genre"),
                        rs.getInt("pages"),
                        rs.getString("rating"),
                        rs.getString("isbn")
                );
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

}
