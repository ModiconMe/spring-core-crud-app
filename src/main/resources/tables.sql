CREATE TABLE person (
id SERIAL PRIMARY KEY,
name VARCHAR(50),
dateOfBirth INT,
email VARCHAR(50) NOT NULL UNIQUE,
address VARCHAR(100)
);

CREATE TABLE book (
id SERIAL PRIMARY KEY,
label VARCHAR(50),
author VARCHAR(50),
releaseDate INT,
person_id INT REFERENCES person(id) ON DELETE SET NULL
);

INSERT INTO person(name, dateOfBirth, email) VALUES('Dmitry Popov', 1999, 'rebeelsc@gmail.com');
INSERT INTO person(name, dateOfBirth, email) VALUES('Polina Popova', 2000, 'akhremenko@gmail.com');
INSERT INTO book(label, author, releaseDate) VALUES('Java', 'Bruce Ekkel', 2011);
INSERT INTO book(label, author, releaseDate) VALUES('Clean Code', 'Bob Martin', 2005);