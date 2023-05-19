- Database Name:
CREATE DATABASE cardswapshop;

USE cardswapshop;

GRANT ALL PRIVILEGES ON user* TO 'root'@'localhost' IDENTIFIED BY 'root'

-- USER TABLE STRUCTURE:

CREATE TABLE USER(
                      id INT NOT NULL AUTO_INCREMENT,
                      first_name VARCHAR(50) NOT NULL,
                      last_name VARCHAR(50) NOT NULL,
                      username VARCHAR(50) NOT NULL,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      create_at TIMESTAMP,
                      update_at TIMESTAMP,
                      PRIMARY KEY(id)
);

CREATE TABLE CARD(
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        user INT NOT NULL,
                        collection INT NOT NULL,
                        edition VARCHAR(100),
                        card_number VARCHAR(50),
                        file_name VARCHAR(50),
                        file_type VARCHAR(50),
                        image MEDIUMBLOB,
                        create_at TIMESTAMP,
                        update_at TIMESTAMP,
                        PRIMARY KEY (id),
                        FOREIGN KEY (user) REFERENCES USER(id),
                        FOREIGN KEY (collection) REFERENCES COLLECTION(id)


);

CREATE TABLE COLLECTION(
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(100),
                        create_at TIMESTAMP,
                        update_at TIMESTAMP,
                        PRIMARY KEY (id)

);