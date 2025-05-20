-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 20, 2025 alle 18:24
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `trainer`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `esercizi`
--

CREATE TABLE `esercizi` (
  `ID` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `descrizione` varchar(100) NOT NULL,
  `gruppo_muscolare` set('petto','dorso','spalle','bicipiti','tricipiti','gambe e glutei','addome') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `esercizi`
--

INSERT INTO `esercizi` (`ID`, `nome`, `descrizione`, `gruppo_muscolare`) VALUES
(1, 'Squat con bilanciere', 'forza', 'gambe e glutei'),
(2, 'Stacchi a gambe tese con bilanciere', 'forza', 'gambe e glutei'),
(3, 'Spinta anca con bilanciere', 'ipertrofia', 'gambe e glutei'),
(4, 'Panca piana con bilanciere', 'forza', 'petto'),
(5, 'Push up', 'forza', 'petto'),
(6, 'Panca inclinata con manubri', 'forza', 'petto'),
(7, 'Trazioni alla sbarra', 'ipertrofia', 'dorso'),
(8, 'Lat machine', 'ipertrofia', 'dorso'),
(9, 'Pulley', 'ipertrofia', 'dorso'),
(10, 'Alzate laterali', 'ipetrofia', 'spalle'),
(11, 'Shulder press', 'ipertrofia', 'spalle'),
(12, 'Alzate frontali', 'ipertrofia', 'spalle'),
(13, 'Bayesian curl al cavo basso', 'ipertrofia', 'bicipiti'),
(14, 'Curl con manubri', 'ipertrofia', 'bicipiti'),
(15, 'Curl con bilanciere EZ', 'ipertrofia', 'bicipiti'),
(16, 'Cavi con spinta verso il basso', 'ipertrofia', 'tricipiti'),
(17, 'Dip su panca', 'ipertrofia', 'tricipiti'),
(18, 'Diamond push up', 'ipertrofia', 'tricipiti'),
(19, 'Situp con bilanciere', 'ipertrofia', 'addome'),
(20, 'Crunches', 'ipertrofia', 'addome'),
(21, 'Crunch in piedi sui cavi', 'ipertrofia', 'addome'),
(22, 'Stacchi a gambe tese con bilanciere', 'forza', 'gambe e glutei');

-- --------------------------------------------------------

--
-- Struttura della tabella `trainer`
--

CREATE TABLE `trainer` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `trainer`
--

INSERT INTO `trainer` (`id`, `username`, `password`) VALUES
(1, 'raffa', '1234'),
(2, 'carde', '1234');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `esercizi`
--
ALTER TABLE `esercizi`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `trainer`
--
ALTER TABLE `trainer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `esercizi`
--
ALTER TABLE `esercizi`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT per la tabella `trainer`
--
ALTER TABLE `trainer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
