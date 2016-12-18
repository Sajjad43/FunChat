-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 01, 2015 at 07:33 AM
-- Server version: 5.6.24
-- PHP Version: 5.5.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `funchat`
--

-- --------------------------------------------------------

--
-- Table structure for table `centraldatabase`
--

CREATE TABLE IF NOT EXISTS `centraldatabase` (
  `Name` char(20) DEFAULT NULL,
  `phoneNumber` char(20) DEFAULT NULL,
  `eclipse_connectNo` int(6) NOT NULL DEFAULT '0',
  `netbean_connectNo` int(6) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dekhi`
--

CREATE TABLE IF NOT EXISTS `dekhi` (
  `times` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dekhi`
--

INSERT INTO `dekhi` (`times`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `EventType` varchar(20) NOT NULL,
  `Title` varchar(20) NOT NULL,
  `Address` varchar(20) NOT NULL,
  `Date` varchar(20) NOT NULL,
  `Time` varchar(20) NOT NULL,
  `Location` varchar(20) NOT NULL,
  `Url` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`EventType`, `Title`, `Address`, `Date`, `Time`, `Location`, `Url`) VALUES
('Movie', 'Beautiful Mind', 'Cineplex', '25/05/15', '10:32am', 'Dhaka', 'www.facebook.com'),
('Concert', 'Health Day ', 'Army Stadium', '20/05/15', '6:00pm', 'Dhaka', 'www.facebook.com'),
('Fest', 'CSE FEST', 'BUET', '14/05/15', '02:30', 'Dhaka', 'www.gmail.com'),
('Examination', ' IELTS', 'British council', '13/06/15', '01:30', 'Dhaka', 'www.gmail.com'),
('Examination', ' IELTS', 'British council', '13/06/15', '01:30', 'Chittagong', 'www.gmail.com'),
('Concert', 'Cricket worldcup', 'Mirpur Stadium', '15/09/15', '10:30', 'Chittagong', 'www.gmail.com'),
('Fest', 'Civil_Fest', 'CUET', '15/09/15', '02:32pm', 'Chittagong', 'wwww.hotmail.com'),
('Movie', 'Philadelphia', 'Auditorium', '14/05/16', '05:36pm', 'Chittagong', 'www.gmai.com'),
('Concert', 'Pohela Boishakh', 'Banani(AIUB)', '14/05/16', '11:05pm', 'Dhaka', 'www.facebook.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
