-- Database Name:
CREATE DATABASE user;

USE user;

GRANT ALL PRIVILEGES ON user* TO 'root'@'localhost' IDENTIFIED BY 'root'

-- USER TABLE STRUCTURE:

CREATE TABLE users(
    user_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
    PRIMARY KEY(user_id)
);