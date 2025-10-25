-- Tabela principal de livros
CREATE TABLE IF NOT EXISTS books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(80) NOT NULL,
    author VARCHAR(80),
    data_release INTEGER,
    genre VARCHAR(80),
    pages INTEGER,
    rating VARCHAR(10),
    isbn VARCHAR(80) UNIQUE,      -- ISBN como identificador único

    -- Outras funcionalidades:
    description TEXT,
    google_books_id TEXT,

    -- Metadados:
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP


);