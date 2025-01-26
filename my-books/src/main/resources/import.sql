INSERT INTO author (name) VALUES ('Newton C. Braga');
INSERT INTO author (name) VALUES ('Marck Richards');
INSERT INTO author (name) VALUES ('Neal Ford');
INSERT INTO author (name) VALUES ('Eric Evans');
INSERT INTO author (name) VALUES ('Robert C. Martin');

INSERT INTO publisher (name) VALUES ('Alta Books');
INSERT INTO publisher (name) VALUES ('Casa do Código');
INSERT INTO publisher (name) VALUES ('Mc Graw Hill');
INSERT INTO publisher (name) VALUES ('Novatec');

INSERT INTO book_subjects (subject) VALUES ('Programação');
INSERT INTO book_subjects (subject) VALUES ('Matemática');
INSERT INTO book_subjects (subject) VALUES ('Eletrônica');

INSERT INTO books (title, pages, language, url, publisher_id, subject_id ) VALUES ('Fundamentos da arquitetura de Software', '611', 'Portugues', 'htpps://localhost/0001', 1, 1);
INSERT INTO books (title, pages, language, url, publisher_id, subject_id ) VALUES ('Arquitetura Limpa', '495', 'Portugues', 'htpps://localhost/0001', 1, 1);
INSERT INTO books (title, pages, language, url, publisher_id, subject_id ) VALUES ('Eletrônica Básica', '375', 'Portugues', 'htpps://localhost/0003', 4, 3);

INSERT INTO books_authors (author_id, book_id) VALUES (1, 3);
INSERT INTO books_authors (author_id, book_id) VALUES (1, 2);
INSERT INTO books_authors (author_id, book_id) VALUES (3, 1);
INSERT INTO books_authors (author_id, book_id) VALUES (2, 5);


