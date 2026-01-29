-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: criptocant
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `tb_assets`
--

DROP TABLE IF EXISTS `tb_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_assets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `symbol` varchar(255) NOT NULL,
  `type` enum('CRYPTO','FIAT','STOCK') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbxcbs2nmqta3k19go4ps2v2kw` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_assets`
--

LOCK TABLES `tb_assets` WRITE;
/*!40000 ALTER TABLE `tb_assets` DISABLE KEYS */;
INSERT INTO `tb_assets` VALUES (1,'BTC','BTC','CRYPTO'),(2,'ETH','ETH','CRYPTO'),(3,'SOL','SOL','CRYPTO'),(4,'THT','THT','CRYPTO'),(5,'USDT','USDT','CRYPTO');
/*!40000 ALTER TABLE `tb_assets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_transactions`
--

DROP TABLE IF EXISTS `tb_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_transactions` (
  `asset_id` int(11) NOT NULL,
  `price_per_unit` decimal(19,2) NOT NULL,
  `quantity` decimal(19,8) NOT NULL,
  `total_value` decimal(19,2) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` datetime(6) DEFAULT NULL,
  `wallet_id` bigint(20) NOT NULL,
  `type` enum('BUY','DEPOSIT','SELL','WITHDRAW') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh9dbe9qr469bflaun19quab7k` (`asset_id`),
  KEY `FKb8bknoji4t68axtsraryu6xww` (`wallet_id`),
  CONSTRAINT `FKb8bknoji4t68axtsraryu6xww` FOREIGN KEY (`wallet_id`) REFERENCES `tb_wallets` (`id`),
  CONSTRAINT `FKh9dbe9qr469bflaun19quab7k` FOREIGN KEY (`asset_id`) REFERENCES `tb_assets` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_transactions`
--

LOCK TABLES `tb_transactions` WRITE;
/*!40000 ALTER TABLE `tb_transactions` DISABLE KEYS */;
INSERT INTO `tb_transactions` VALUES (1,457000.00,1.00000000,457000.00,1,'2026-01-29 03:26:03.000000',1,'BUY'),(2,200000.00,1.00000000,200000.00,2,'2026-01-29 03:26:19.000000',1,'BUY'),(3,600.00,1.00000000,600.00,3,'2026-01-29 03:26:51.000000',1,'BUY'),(4,5.20,5.00000000,26.00,4,'2026-01-29 03:55:37.000000',3,'BUY'),(5,5.20,5.00000000,26.00,5,'2026-01-29 03:56:41.000000',2,'BUY'),(5,20.00,2.00000000,40.00,6,'2026-01-29 03:57:07.000000',2,'SELL');
/*!40000 ALTER TABLE `tb_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_wallets`
--

DROP TABLE IF EXISTS `tb_wallets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_wallets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_wallets`
--

LOCK TABLES `tb_wallets` WRITE;
/*!40000 ALTER TABLE `tb_wallets` DISABLE KEYS */;
INSERT INTO `tb_wallets` VALUES (1,'Cateira Gabriel'),(2,'A'),(3,'tht');
/*!40000 ALTER TABLE `tb_wallets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-29  2:03:39
