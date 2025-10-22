package org.example.models;

public record Book(
        String title,
        String author,
        int dataRelease,
        String gender,
        String rating,
        String ISNB
) {
}
