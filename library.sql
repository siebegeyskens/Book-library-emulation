DROP DATABASE IF EXISTS `library`; 

CREATE DATABASE `library`
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */
/*!80016 DEFAULT ENCRYPTION='N' */;

USE `library`; 

CREATE TABLE `authors` (
  `id` int unsigned AUTO_INCREMENT NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `birthday` date NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_birthday_UNIQUE` (`first_name`, `last_name`, `birthday`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci KEY_BLOCK_SIZE=16;

CREATE TABLE `books` (
  `id` int unsigned AUTO_INCREMENT NOT NULL,
  `title` varchar(50) NOT NULL, 
  `selling_price` double unsigned NOT NULL,
  `ISBN13` varchar(13) NOT NULL,
  `pages` int unsigned NOT NULL,
  `genre` ENUM('NOVEL', 'SCIENCE', 'CLASSICS', 'PHILOSOPHY', 'MAGAZINE') NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  `author` int unsigned null,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `ISBN13_UNIQUE` (`ISBN13`), 
  FOREIGN KEY `author_id` (`author`) REFERENCES authors(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci KEY_BLOCK_SIZE=16;


INSERT INTO `authors` (`id`, `first_name`, `last_name`, `birthday`) VALUES (1,'George','Orwell','1903-06-25');
INSERT INTO `authors` (`id`, `first_name`, `last_name`, `birthday`) VALUES (2,'Kris','Pint','1981-01-01');
INSERT INTO `authors` (`id`, `first_name`, `last_name`, `birthday`) VALUES (3,'Tony','Butt','1961-03-01');
INSERT INTO `authors` (`id`, `first_name`, `last_name`, `birthday`) VALUES (4,'Charles','Dickens','1812-02-07');
INSERT INTO `authors` (`id`, `first_name`, `last_name`, `birthday`) VALUES (5,'Jake','Phelps','1981-01-01');

INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (1,'Surf science','25','1111111111111','205','SCIENCE',3);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (2,'Animal farm','9.90','9999999999999','115','CLASSICS',1);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (3,'De wilde tuin van de verbeelding','18.90','3456789123456','201','PHILOSOPHY',2);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (4,'Coming up for air','12.50','8888888888888','307','CLASSICS',1);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (5,'Coming up for air','12.50','7777777777777','307','CLASSICS',1);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (6,'Thrasher magazine oktober 2020','12.50','0000000000001','30','MAGAZINE',5);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (7,'Thrasher magazine november 2020','12.50','0000000000002','30','MAGAZINE',5);
INSERT INTO `books` (`id`, `title`, `selling_price`, `ISBN13`, `pages`, `genre`, `author`) VALUES (8,'Thrasher magazine december 2020','12.50','0000000000003','30','MAGAZINE',5);
