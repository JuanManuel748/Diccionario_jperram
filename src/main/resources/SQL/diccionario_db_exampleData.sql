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

INSERT INTO word (term, gramatic) VALUES
('correr', 'verb'),
('casa', 'noun'),
('rápido', 'adjective'),
('rápidamente', 'adverb'),
('él', 'pronoun'),
('en', 'preposition'),
('y', 'conjunction'),
('¡ah!', 'interjection'),
('el', 'article');

INSERT INTO definition (description, example, word) VALUES
('Moverse rápidamente con las piernas.', 'Él puede correr muy rápido.', 1),
('Edificio para habitar.', 'Vivo en una casa grande.', 2),
('Que se mueve a gran velocidad.', 'El coche es muy rápido.', 3),
('De manera rápida.', 'Él terminó su tarea rápidamente.', 4),
('Pronombre personal de tercera persona.', 'Él es mi amigo.', 5),
('Indica lugar, tiempo o modo.', 'El libro está en la mesa.', 6),
('Conjunción copulativa.', 'Pedro y Juan son amigos.', 7),
('Expresa sorpresa o emoción.', '¡Ah! No lo sabía.', 8),
('Artículo definido masculino singular.', 'El perro es grande.', 9);