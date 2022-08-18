-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `returned`
--

DROP TABLE IF EXISTS `returned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `returned` (
  `bookid` int NOT NULL,
  `bookname` varchar(45) DEFAULT NULL,
  `studentid` int DEFAULT NULL,
  `studentname` varchar(45) DEFAULT NULL,
  `issuedate` date DEFAULT NULL,
  `duedate` date DEFAULT NULL,
  `returndate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returned`
--

LOCK TABLES `returned` WRITE;
/*!40000 ALTER TABLE `returned` DISABLE KEYS */;
INSERT INTO `returned` VALUES (1,'Jannat K Patty',1,'Rafia Ashraf','2022-07-06','2022-07-15','2022-07-01'),(1,'Jannat K Patty',2,'Zuha Sohail','2022-06-26','2022-07-13','2022-07-15'),(11,'Mushaf',1,'Rafia Ashraf','2022-07-15','2022-07-22','2022-07-08'),(1,'Jannat K Patty',1,'Rafia Ashraf','2022-06-30','2022-07-09','2022-07-08'),(15,'Aab e Hayyat',1,'Rafia Ashraf','2022-07-03','2022-07-13','2022-07-16'),(1,'Jannat K Patty',1,'Rafia Ashraf','2022-07-15','2022-07-21','2022-07-16'),(9,'Peer e Kaamil',10,'Nadia Ashraf','2022-07-07','2022-07-14','2022-07-16'),(15,'Aab e Hayyat',2,'Zuha Sohail','2022-06-26','2022-07-28','2022-07-16');
/*!40000 ALTER TABLE `returned` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-18 18:54:15
