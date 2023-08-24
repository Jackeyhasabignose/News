CREATE TABLE `news` (
   `news_id` int NOT NULL AUTO_INCREMENT,
   `title` varchar(50) NOT NULL,
   `content` varchar(500) NOT NULL,
   `public_time` date DEFAULT NULL,
   `category` varchar(10) DEFAULT NULL,
   PRIMARY KEY (`news_id`)
 );