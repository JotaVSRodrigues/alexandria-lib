package org.example.connection;

import org.example.dao.BookDAO;
import org.example.models.Book;

import java.util.List;

public class BookRepository {
    private final BookDAO dao = new BookDAO();

    public void initializeDB() {
        DatabaseManager.initializeDatabase();
    }

    public void addBook(Book book){
        dao.insert(book);
    }

    public List<Book> bookList(){
        return dao.findAll();
    }
}
