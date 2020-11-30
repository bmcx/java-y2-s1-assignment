-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2020 at 07:11 PM
-- Server version: 10.4.16-MariaDB
-- PHP Version: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `medcenter`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(11) NOT NULL,
  `doctorId` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  `scheduleId` int(11) NOT NULL,
  `status` enum('ACCEPTED','PENDING','CANCELED','REJECTED') NOT NULL DEFAULT 'PENDING',
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `doctorId`, `studentId`, `scheduleId`, `status`, `createdAt`) VALUES
(1, 210002, 210003, 1, 'REJECTED', '2020-11-29 05:22:07'),
(2, 210002, 210004, 1, 'PENDING', '2020-11-29 05:22:07'),
(4, 210002, 210004, 2, 'CANCELED', '2020-11-29 19:02:50'),
(5, 210002, 210004, 7, 'CANCELED', '2020-11-30 18:02:34'),
(6, 210002, 210004, 7, 'CANCELED', '2020-11-30 18:07:19'),
(7, 210002, 210004, 7, 'PENDING', '2020-11-30 18:07:25');

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL,
  `doctorId` int(11) NOT NULL,
  `day` date NOT NULL,
  `timeFrom` time NOT NULL,
  `timeTo` time NOT NULL,
  `maxCount` tinyint(3) UNSIGNED NOT NULL DEFAULT 5
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`id`, `doctorId`, `day`, `timeFrom`, `timeTo`, `maxCount`) VALUES
(1, 210002, '2020-11-25', '10:00:00', '12:00:00', 5),
(2, 210002, '2020-11-30', '13:00:00', '15:00:00', 5),
(3, 210002, '2020-11-25', '08:00:00', '12:00:00', 1),
(4, 210002, '2020-11-30', '08:00:00', '09:00:00', 5),
(7, 210002, '2020-12-02', '08:00:00', '10:00:00', 5);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(60) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('DOCTOR','STAFF','STUDENT') NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `title` varchar(30) NOT NULL,
  `description` varchar(50) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role`, `firstname`, `lastname`, `title`, `description`, `createdAt`) VALUES
(210001, 'test', '12345', 'STAFF', 'john', 'doe', '-', '-', '2020-11-28 18:13:56'),
(210002, 'doc2', '12345', 'DOCTOR', 'Jane', 'Doe', 'Specialized in Counseling', 'Lorem ipsum dolor sit amet', '2020-11-29 05:18:07'),
(210003, 'stu1', '12345', 'STUDENT', 'Grant', 'Tanner', '', '', '2020-11-29 05:18:07'),
(210004, 'stu2', '12345', 'STUDENT', 'Kasper', 'Lennon', '', '', '2020-11-29 05:22:51'),
(210005, 'doc1', '12345', 'DOCTOR', 'Brenda', 'Horner', 'Orthopedic surgeon', 'Specializes in sports injuries, broken bones etc..', '2020-11-30 07:02:52');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `timeFrom` (`timeFrom`,`timeTo`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=210006;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
