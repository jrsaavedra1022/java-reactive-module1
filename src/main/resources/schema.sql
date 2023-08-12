-- Eliminar las tablas
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS chapter;
DROP TABLE IF EXISTS series;
DROP TABLE IF EXISTS magazine;

-- Crear las tablas
CREATE TABLE IF NOT EXISTS book (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    edition_date DATE NOT NULL,
    editorial VARCHAR(255),
    authors VARCHAR2(500),
    isbn VARCHAR(20) NOT NULL,
    readed BOOLEAN NOT NULL,
    time_readed INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movie (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    creator VARCHAR(250) NOT NULL,
    duration INTEGER NOT NULL,
    release_year VARCHAR(4) NOT NULL,
    viewed BOOLEAN NOT NULL,
    time_viewed INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS series (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    creator VARCHAR(250) NOT NULL,
    duration INTEGER NOT NULL,
    release_year VARCHAR(4) NOT NULL,
    viewed BOOLEAN NOT NULL,
    session_quantity INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS chapter (
    id INT NOT NULL AUTO_INCREMENT,
    number INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    creator VARCHAR(250) NOT NULL,
    duration INTEGER NOT NULL,
    release_year VARCHAR(4) NOT NULL,
    viewed BOOLEAN NOT NULL,
    time_viewed INTEGER,
    session_number INTEGER NOT NULL,
    series_id INT NOT NULL,
    FOREIGN KEY (series_id) REFERENCES series (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS magazine (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    edition_date DATE NOT NULL,
    editorial VARCHAR(250),
    authors VARCHAR2(500),
    PRIMARY KEY (id)
);

-- tabla "book"
INSERT INTO book (title, edition_date, editorial, authors, isbn, readed, time_readed) VALUES
('El Gran Gatsby', '2022-01-15', 'Editorial A', 'F. Scott Fitzgerald', '978-1234567890', TRUE, 320),
('Cien Años de Soledad', '2005-09-30', 'Editorial B', 'Gabriel García Márquez', '978-9876543210', TRUE, 280),
('Orgullo y Prejuicio', '2010-06-22', 'Editorial C', 'Jane Austen', '978-5555555555', TRUE, 420),
('1984', '2018-11-10', 'Editorial A', 'George Orwell', '978-9998887776', FALSE, 0),
('Don Quijote de la Mancha', '1999-03-05', 'Editorial D', 'Miguel de Cervantes', '978-4444444444', TRUE, 600),
('Harry Potter y la Piedra Filosofal', '2001-12-18', 'Editorial E', 'J.K. Rowling', '978-7777777777', TRUE, 380),
('Crimen y Castigo', '2015-07-08', 'Editorial F', 'Fyodor Dostoevsky', '978-6666666666', FALSE, NULL),
('Matar un Ruiseñor', '2012-04-27', 'Editorial G', 'Harper Lee', '978-2222222222', TRUE, 290),
('Los Juegos del Hambre', '2014-09-14', 'Editorial H', 'Suzanne Collins', '978-3333333333', TRUE, 410),
('El Hobbit', '2008-08-02', 'Editorial I', 'J.R.R. Tolkien', '978-4448882222', FALSE, 0);

-- tabla "movie"
INSERT INTO movie (title, genre, creator, duration, release_year, viewed, time_viewed) VALUES
('El Padrino', 'Drama', 'Francis Ford Coppola', 175, '1972', TRUE, 180),
('Parasite', 'Drama', 'Bong Joon-ho', 132, '2019', TRUE, 120),
('Star Wars: Episodio IV - Una Nueva Esperanza', 'Ciencia ficción', 'George Lucas', 121, '1977', TRUE, 140),
('El Señor de los Anillos: La Comunidad del Anillo', 'Fantasía', 'Peter Jackson', 178, '2001', TRUE, 200),
('Matrix', 'Acción', 'The Wachowskis', 136, '1999', TRUE, 160),
('Titanic', 'Romance', 'James Cameron', 195, '1997', TRUE, 220),
('Toy Story', 'Animación', 'John Lasseter', 81, '1995', TRUE, 90),
('Jurassic Park', 'Aventura', 'Steven Spielberg', 127, '1993', FALSE, 0),
('Avengers: Endgame', 'Acción', 'Anthony Russo, Joe Russo', 181, '2019', TRUE, 210),
('Inception', 'Ciencia ficción', 'Christopher Nolan', 148, '2010', TRUE, 170);

-- tabla "series"
INSERT INTO series (title, genre, creator, duration, release_year, viewed, session_quantity) VALUES
('Stranger Things', 'Ciencia ficción', 'The Duffer Brothers', 60, '2016', TRUE, 3),
('Game of Thrones', 'Fantasía', 'David Benioff, D. B. Weiss', 55, '2011', TRUE, 8),
('Breaking Bad', 'Drama', 'Vince Gilligan', 50, '2008', TRUE, 5),
('Friends', 'Comedia', 'David Crane, Marta Kauffman', 22, '1994', TRUE, 10),
('The Crown', 'Drama', 'Peter Morgan', 60, '2016', TRUE, 4),
('The Office', 'Comedia', 'Greg Daniels', 30, '2005', TRUE, 9),
('Stranger Things', 'Ciencia ficción', 'The Duffer Brothers', 60, '2016', TRUE, 3),
('Black Mirror', 'Ciencia ficción', 'Charlie Brooker', 60, '2011', FALSE, 1),
('Sherlock', 'Misterio', 'Mark Gatiss, Steven Moffat', 90, '2010', TRUE, 4),
('The Mandalorian', 'Ciencia ficción', 'Jon Favreau', 40, '2019', TRUE, 2);

-- tabla "chapter"
INSERT INTO chapter (number, title, genre, creator, duration, release_year, viewed, time_viewed, session_number, series_id) VALUES
(1, 'Capítulo 1', 'Ciencia ficción', 'The Duffer Brothers', 60, '2016', TRUE, 60, 1, 1),
(2, 'Capítulo 2', 'Ciencia ficción', 'The Duffer Brothers', 55, '2016', TRUE, 50, 1, 1),
(1, 'Invierno se acerca', 'Fantasía', 'David Benioff, D. B. Weiss', 60, '2011', TRUE, 65, 1, 2),
(2, 'El Camino Real', 'Fantasía', 'David Benioff, D. B. Weiss', 57, '2011', TRUE, 52, 1, 2),
(1, 'Piloto', 'Drama', 'Vince Gilligan', 50, '2008', TRUE, 48, 1, 3),
(2, 'Cats in the Bag...', 'Drama', 'Vince Gilligan', 48, '2008', TRUE, 46, 1, 3),
(1, 'The One Where Monica Gets a Roommate', 'Comedia', 'David Crane, Marta Kauffman', 22, '1994', TRUE, 22, 1, 4),
(2, 'The One with the Sonogram at the End', 'Comedia', 'David Crane, Marta Kauffman', 20, '1994', TRUE, 20, 2, 4),
(1, 'Wolferton Splash', 'Drama', 'Peter Morgan', 55, '2016', TRUE, 58, 1, 5),
(2, 'Hyde Park Corner', 'Drama', 'Peter Morgan', 58, '2016', TRUE, 60, 2, 5);

-- tabla "magazine"
INSERT INTO magazine (title, edition_date, editorial, authors) VALUES
('Revista de Ciencia', '2023-08-01', 'Editorial X', 'Juan Pérez'),
('Moda Actual', '2023-07-15', 'Editorial Y', 'María Gómez, Carlos Rodríguez'),
('Tecnología Hoy', '2023-08-05', 'Editorial Z', 'Ana Martínez'),
('Cocina Creativa', '2023-07-20', 'Editorial A', 'Luis Ramírez'),
('Deportes en Acción', '2023-08-10', 'Editorial B', 'Andrés López, Marta Silva'),
('Viajes Exóticos', '2023-07-25', 'Editorial C', 'Elena García'),
('Negocios Globales', '2023-08-03', 'Editorial D', 'Roberto Fernández, Andrea González'),
('Arte Contemporáneo', '2023-07-30', 'Editorial E', 'Sofía Martínez'),
('Salud y Bienestar', '2023-08-08', 'Editorial F', 'Pedro Pérez, Laura Sánchez'),
('Música en Reseña', '2023-08-02', 'Editorial G', 'Daniel Ramírez');