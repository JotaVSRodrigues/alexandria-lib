package org.example.main;

import org.example.connection.BookRepository;
import org.example.dao.BookDAO;
import org.example.models.Book;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BookRepository repo = new BookRepository();
        repo.initializeDB();

        var domCasmurro = new Book("Dom Casmurro", "Machado de Assis", 1899,
                "Romance", 256, "4.5", "1234567890123");
//        List<Book> books = repo.bookList();
//        books.forEach(System.out::println);

        var book01 = new Book("O Hobbit", "J.R.R Tolkien", 1941,
                "Fantasia", 301, "5.0","1234567890321");
        BookDAO bookDAO = new BookDAO();
        bookDAO.insert(book01);
        List<Book> bookList = bookDAO.findAll();
        bookList.forEach(System.out::println);

        bookDAO.deleteByTitle(domCasmurro);
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
}