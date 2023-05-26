CREATE DATABASE  IF NOT EXISTS `firePunchFitnessClub` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `firePunchFitnessClub`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: firePunchFitnessClub
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `categoriaEjercicio`
--

DROP TABLE IF EXISTS `categoriaEjercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoriaEjercicio` (
  `idCategoria` int NOT NULL,
  `nombreCategoria` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoriaEjercicio_SEQ`
--

DROP TABLE IF EXISTS `categoriaEjercicio_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoriaEjercicio_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoriaEjercicio_ejercicio`
--

DROP TABLE IF EXISTS `categoriaEjercicio_ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoriaEjercicio_ejercicio` (
  `Categoria_idCategoria` int NOT NULL,
  `ejercicios_idEjercicio` int NOT NULL,
  UNIQUE KEY `UK_i6iibtkahxwhsbiut00rawcts` (`ejercicios_idEjercicio`),
  KEY `FK9v313wqfff6ph8886locj0x19` (`Categoria_idCategoria`),
  CONSTRAINT `FK9v313wqfff6ph8886locj0x19` FOREIGN KEY (`Categoria_idCategoria`) REFERENCES `categoriaEjercicio` (`idCategoria`),
  CONSTRAINT `FKqych9vfbls4ghhyeg8w5nbomp` FOREIGN KEY (`ejercicios_idEjercicio`) REFERENCES `ejercicio` (`idEjercicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellidos` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `edad` int NOT NULL,
  `altura` int NOT NULL,
  `peso` double NOT NULL,
  `fotoPerfil` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `idEntrenador` int NOT NULL,
  PRIMARY KEY (`idCliente`),
  KEY `idEntrenador` (`idEntrenador`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`idEntrenador`) REFERENCES `entrenador` (`idEntrenador`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente_SEQ`
--

DROP TABLE IF EXISTS `cliente_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ejercicio`
--

DROP TABLE IF EXISTS `ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicio` (
  `idEjercicio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `series` int NOT NULL,
  `repeticiones` int NOT NULL,
  `cargaKg` double NOT NULL,
  `fotoPerfil` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `idCategoria` int NOT NULL,
  PRIMARY KEY (`idEjercicio`),
  KEY `idCategoria` (`idCategoria`),
  CONSTRAINT `ejercicio_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoriaEjercicio` (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ejercicio_SEQ`
--

DROP TABLE IF EXISTS `ejercicio_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicio_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrenador`
--

DROP TABLE IF EXISTS `entrenador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrenador` (
  `idEntrenador` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellidos` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `edad` int NOT NULL,
  `usuario` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `contraseña` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fotoPerfil` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idEntrenador`),
  UNIQUE KEY `usuario` (`usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrenador_SEQ`
--

DROP TABLE IF EXISTS `entrenador_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrenador_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrenador_cliente`
--

DROP TABLE IF EXISTS `entrenador_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrenador_cliente` (
  `Entrenador_idEntrenador` int NOT NULL,
  `clientes_idCliente` int NOT NULL,
  UNIQUE KEY `UK_m979t875sltc3uc0kf6bybve8` (`clientes_idCliente`),
  KEY `FKjnjr23wd2hbmmsfkumyxsfowt` (`Entrenador_idEntrenador`),
  CONSTRAINT `FKjnjr23wd2hbmmsfkumyxsfowt` FOREIGN KEY (`Entrenador_idEntrenador`) REFERENCES `entrenador` (`idEntrenador`),
  CONSTRAINT `FKl4rci8ejtlrhe850jvr2unlck` FOREIGN KEY (`clientes_idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `realiza_ejercicio`
--

DROP TABLE IF EXISTS `realiza_ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `realiza_ejercicio` (
  `idEjercicio` int NOT NULL,
  `idCliente` int NOT NULL,
  KEY `FKpqmebm5u200pcper8qalykil1` (`idCliente`),
  KEY `FK1ogkqi2w3yiq75f1xkdgyqf8p` (`idEjercicio`),
  CONSTRAINT `FK1ogkqi2w3yiq75f1xkdgyqf8p` FOREIGN KEY (`idEjercicio`) REFERENCES `ejercicio` (`idEjercicio`),
  CONSTRAINT `FKpqmebm5u200pcper8qalykil1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-24 17:01:10

INSERT INTO `categoriaEjercicio` VALUES (1,'Pierna'),(2,'Pecho'),(3,'Espalda'),(4,'Brazo'),(5,'Abdomen');
INSERT INTO `categoriaEjercicio_SEQ` VALUES (1);
INSERT INTO `ejercicio` VALUES (1,'Sentadillas',3,12,50.5,'src/main/resources/image_21.png',1),(2,'Press de banca',4,8,75,'src/main/resources/image_22.png',2),(3,'Remo con barra',3,10,60,'src/main/resources/image_23.png',3),(4,'Curl de bíceps con mancuernas',3,12,15,'src/main/resources/image_24.png',4),(5,'Press militar',4,8,40,'src/main/resources/image_25.png',2),(6,'Extensiones de tríceps en polea alta',3,12,25,'src/main/resources/image_26.png',4),(7,'Zancadas con mancuernas',3,12,12.5,'src/main/resources/image_27.png',1),(8,'Elevaciones laterales con mancuernas',3,12,10,'src/main/resources/image_28.png',4),(9,'Peso muerto',3,10,80,'src/main/resources/image_29.png',3),(10,'Flexiones de brazos',3,15,0,'src/main/resources/image_30.png',2),(11,'Curl de piernas acostado',3,12,30,'src/main/resources/image_31.png',1),(12,'Elevaciones frontales con barra',3,12,20,'src/main/resources/image_32.png',2),(13,'Polea al pecho',3,12,30,'src/main/resources/image_33.png',3),(14,'Abdominales',3,15,0,'src/main/resources/image_34.png',5),(15,'Remo en polea baja',3,12,40,'src/main/resources/image_35.png',3),(16,'Fondos en paralelas',3,12,0,'src/main/resources/image_36.png',2),(17,'Prensa de piernas',3,12,50,'src/main/resources/image_37.png',1),(18,'Press de hombros con mancuernas',3,12,25,'src/main/resources/image_38.png',4),(19,'Curl de bíceps con barra',3,12,25,'src/main/resources/image_39.png',4),(20,'Extensiones de tríceps con mancuernas',3,12,20,'src/main/resources/image_40.png',4);
INSERT INTO `ejercicio_SEQ` VALUES (51);
INSERT INTO `entrenador` VALUES (1,'Pablo','Molero Marín',21,'bleras','bleras@mail.com','vmpgwUWvJ00=','src/main/resources/image_10.png'),(2,'Jesús','Ramirez Pacual',56,'jesusramirez','jramirezp@mail.com','vmpgwUWvJ00=','src/main/resources/image_12.png'),(3,'María','García García',34,'mariagarcia','mgarciag@mail.com','vmpgwUWvJ00=','src/main/resources/image_11.png'),(4,'Raquel','Navarro López',21,'raquelnavarro','rnavarrol@mail.com','vmpgwUWvJ00=','src/main/resources/image_13.png'),(5,'Moisés','Mascuñán',21,'moi','moijava@mail.com','vePPj54i1Sw=','src/main/resources/image_14.png'),(10000,'Nadie','Nadie Nadie',99,'NoAsignado','nadie@noasignado.com','vmpgwUWvJ00=',NULL);
INSERT INTO `entrenador_SEQ` VALUES (51);
INSERT INTO `cliente` VALUES (1,'María','García López',30,165,60,'src/main/resources/image_1.png',1),(2,'Juan','Pérez Rodríguez',25,178,75,'src/main/resources/image_2.png',1),(3,'Sofía','Martínez Torres',28,170,65,'src/main/resources/image_3.png',2),(4,'Carlos','Gómez García',35,175,80,'src/main/resources/image_4.png',2),(5,'Ana','González Díaz',29,163,58,'src/main/resources/image_5.png',3),(6,'Pedro','Fernández Sánchez',32,180,85,'src/main/resources/image_6.png',3),(7,'Lucía','Ruiz Torres',27,168,62,'src/main/resources/image_7.png',4),(8,'Miguel','Sánchez López',26,176,77,'src/main/resources/image_8.png',4),(9,'Adriana','González Fernández',31,172,70,'src/main/resources/image_9.png',5),(10,'David','Hernández García',33,182,88,'src/main/resources/image_10.png',5),(11,'Laura','Pérez Ruiz',24,160,55,'src/main/resources/image_11.png',1),(12,'Alejandro','Torres Martínez',29,176,75,'src/main/resources/image_12.png',2),(13,'Carolina','García Sánchez',30,165,60,'src/main/resources/image_13.png',3),(14,'José','Pérez García',25,178,75,'src/main/resources/image_14.png',4),(15,'Silvia','Martínez Ruiz',28,170,65,'src/main/resources/image_15.png',5),(16,'Javier','Gómez Torres',35,175,80,'src/main/resources/image_16.png',1),(17,'Patricia','González Sánchez',29,163,58,'src/main/resources/image_17.png',2),(18,'Ismael','Fernández Díaz',32,180,85,'src/main/resources/image_18.png',3),(19,'Natalia','Ruiz García',27,168,62,'src/main/resources/image_19.png',4),(20,'Rubén','Sánchez Pérez',26,176,77,'src/main/resources/image_20.png',5);
INSERT INTO `cliente_SEQ` VALUES (30);
