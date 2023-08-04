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