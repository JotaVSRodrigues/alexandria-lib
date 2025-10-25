package org.example.models;

public record Book(
        String title,
        String author,
        int dataRelease,
        String genre,
        int pages,
        String rating,
        String isbn
) {
}
