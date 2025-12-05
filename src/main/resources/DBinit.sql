CREATE DATABASE it_department;

USE it_department;

CREATE TABLE departments (
    department_id CHAR(3) PRIMARY KEY CHECK (LENGTH(department_id) = 3),
    department_name VARCHAR(100) NOT NULL UNIQUE,
    location TEXT NOT NULL
);

INSERT INTO departments (department_id, department_name, location) 
VALUES 
    ('A01', 'Инженеры', 'rondo Daszyńskiego 1, 00-843 Warszawa'),
    ('A07', 'Тестировщики', 'rondo Daszyńskiego 1, 00-843 Warszawa'),
    ('B04', 'Очень умные человеки', 'J.E. Irausquin Boulevard 20-A Oranjestad, Aruba');

CREATE TABLE developers (
  id int PRIMARY KEY AUTO_INCREMENT,
  name  varchar(50) DEFAULT NULL,
  specialty varchar(50) DEFAULT NULL,
  experience int DEFAULT NULL,
  department_id CHAR(3) REFERENCES departments(department_id)
);

CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    user_role ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES developers(id)
);

INSERT INTO developers (name, specialty, experience, department_id) VALUES
 ('Это я', 'Студент', 1, 'B04'),
 ('Владимир', 'Java-dev', 5, 'B04'),
 ('Серый', 'Scrum-мастер', 3, 'A01');

INSERT INTO users VALUES
 (1, 'admin', 'admin', 'ROLE_ADMIN'),
 (2, 'user', 'user', 'ROLE_USER');
