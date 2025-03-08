DROP DATABASE IF EXISTS diccionario_db;
CREATE DATABASE diccionario_db;
USE diccionario_db;

DROP TABLE IF EXISTS definition;
DROP TABLE IF EXISTS word;

CREATE TABLE word (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term VARCHAR(250),
    gramatic ENUM('verb', 'noun', 'adjective', 'adverb', 'pronoun', 'preposition', 'conjunction', 'interjection', 'article')
);

CREATE TABLE definition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description TEXT,
    example TEXT,
    word BIGINT,
    CONSTRAINT definition_fk FOREIGN KEY (word) REFERENCES word(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);