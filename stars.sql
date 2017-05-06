-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 06, 2017 at 07:09 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stars`
--

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `course_name` text NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `deadline` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`course_name`, `instructor_id`, `deadline`) VALUES
('CS3337', 17, '08:45:00');

-- --------------------------------------------------------

--
-- Table structure for table `cs3337_17`
--

CREATE TABLE `cs3337_17` (
  `cin` int(11) DEFAULT NULL,
  `firstname` varchar(32) DEFAULT NULL,
  `lastname` varchar(32) NOT NULL DEFAULT 'A',
  `5_5_2017` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cs3337_17`
--

INSERT INTO `cs3337_17` (`cin`, `firstname`, `lastname`, `5_5_2017`) VALUES
(222234555, 'ROGERIA', 'MAHIXO-DERTIGO', 'L'),
(9879765, 'JIN', 'YOHHO-DER', 'L'),
(232456777, 'SUKI', 'YOKOSKA-MUYHKI', 'L'),
(789676453, 'YIKO', 'PARTER-MUYHKI', 'L'),
(323456444, 'RIOPE', 'PARTER-MATRER', 'L'),
(456666543, 'PADHMED', 'RAHMIREZ-HANNY', 'L'),
(456776887, 'LUCIANA', 'BENNY-HILL', 'L'),
(789777111, 'ROGER', 'TAHIREX-MANNYPO', 'L'),
(222333456, 'DAISY', 'AWK-FRANKEN', 'L'),
(333111213, 'RUHIKIE', 'AOYTAS-MIRTENOK', 'L'),
(345012908, 'LUCY', 'AQZSER-IOKPO', 'L'),
(323456754, 'REGIE', 'ARTER-MARTINOK', 'L'),
(301209459, 'REFUGIO', 'ARROYO-MARTINEZ', 'L');

-- --------------------------------------------------------

--
-- Table structure for table `instructors`
--

CREATE TABLE `instructors` (
  `username` text NOT NULL,
  `password` text NOT NULL,
  `id` int(11) NOT NULL,
  `first_name` text NOT NULL,
  `last_name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `instructors`
--

INSERT INTO `instructors` (`username`, `password`, `id`, `first_name`, `last_name`) VALUES
('fugi', 'haha123', 17, 'Refugio', 'Arroyo');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `first_name` text NOT NULL,
  `last_name` text NOT NULL,
  `cin` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`first_name`, `last_name`, `cin`, `instructor_id`) VALUES
('REFUGIO', 'ARROYO-MARTINEZ', 301209459, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `instructors`
--
ALTER TABLE `instructors`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `instructors`
--
ALTER TABLE `instructors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
