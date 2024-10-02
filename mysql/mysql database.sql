-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 02, 2024 at 10:54 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coderkube`
--

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL,
  `question` text NOT NULL,
  `correct_answer_option_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`question_id`, `question`, `correct_answer_option_id`) VALUES
(1, 'Which language is commonly used for Android development?', 3),
(2, 'Which data structure uses LIFO (Last In First Out) principle?', 5),
(3, 'Which SQL command is used to update data in a database?', 8),
(4, 'What is the official IDE for Android development?', 11),
(5, 'Which of the following is a cross-platform mobile app development framework?', 13);

-- --------------------------------------------------------

--
-- Table structure for table `quiz_options`
--

CREATE TABLE `quiz_options` (
  `option_id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_text` text NOT NULL,
  `correct_answer` tinyint(1) NOT NULL,
  `user_answer` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `quiz_options`
--

INSERT INTO `quiz_options` (`option_id`, `question_id`, `option_text`, `correct_answer`, `user_answer`) VALUES
(1, 1, 'Python', 0, 0),
(2, 1, 'Swift', 0, 0),
(3, 1, 'Java', 1, 0),
(4, 2, 'Queue', 0, 0),
(5, 2, 'Stack', 1, 0),
(6, 2, 'Linked List', 0, 0),
(7, 3, 'SELECT', 0, 0),
(8, 3, 'UPDATE', 1, 0),
(9, 3, 'INSERT', 0, 0),
(10, 4, 'Eclipse', 0, 0),
(11, 4, 'Android Studio', 1, 0),
(12, 4, 'Visual Studio', 0, 0),
(13, 5, 'Flutter', 1, 0),
(14, 5, 'Django', 0, 0),
(15, 5, 'Ruby on Rails', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `ID` int(11) DEFAULT NULL,
  `NAME` varchar(60) NOT NULL,
  `CLASS` varchar(60) NOT NULL DEFAULT 'CSE',
  `ENROLLMENT_NO` bigint(20) NOT NULL,
  `ADDRESS` varchar(255) NOT NULL,
  `AGE` int(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `quiz_options`
--
ALTER TABLE `quiz_options`
  ADD PRIMARY KEY (`option_id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`ENROLLMENT_NO`),
  ADD UNIQUE KEY `ID` (`ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `quiz_options`
--
ALTER TABLE `quiz_options`
  ADD CONSTRAINT `quiz_options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
