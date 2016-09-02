CREATE DATABASE IF NOT EXISTS `library`;

-- --------------------------------------------------------

USE `library`;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `ISBN` int(11) NOT NULL,
  `Title` varchar(64) NOT NULL,
  `Author` varchar(32) NOT NULL,
  `Edition` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`ISBN`, `Title`, `Author`, `Edition`) VALUES
(1011, 'De avonden', 'Gerard van het Reve', 9),
(1014, 'Het boek van violet en dood', 'Gerard Reve', 9),
(1021, 'Ik heb nooit iets gelezen', 'Karel van het Reve', 2),
(1111, 'Het leven is vurrukkulluk', 'Remco Campert', 1),
(2222, 'De Ontdekking van de Hemel', 'Harry Mulisch', 5),
(2223, 'De Aanslag', 'Harry Mulisch', 8),
(3333, 'De Aanslag', 'Belastingdienst', 1),
(4001, 'De geverfde vogel', 'Jerzy Kosinski', 2),
(4003, 'Cockpit', 'Jerzy Kosinski', 1),
(4005, 'Aanwezig', 'Jerzy Kosinski', 4),
(8000, 'Ik, Jan Cremer', 'Jan Cremer', 22),
(8888, 'Ik, Jan Klaassen', 'Herman Finkers', 22);

-- --------------------------------------------------------

--
-- Table structure for table `copy`
--

CREATE TABLE `copy` (
  `CopyID` int(11) NOT NULL,
  `LendingPeriod` int(11) NOT NULL,
  `BookISBN` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Dumping data for table `copy`
--

INSERT INTO `copy` (`CopyID`, `LendingPeriod`, `BookISBN`) VALUES
(10001, 5, 2222),
(10002, 21, 1111);

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `ReturnDate` date NOT NULL,
  `MembershipNr` int(11) NOT NULL,
  `CopyID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan`
--

INSERT INTO `loan` (`ReturnDate`, `MembershipNr`, `CopyID`) VALUES
('2013-10-16', 1000, 10001);

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `MembershipNumber` int(11) NOT NULL,
  `FirstName` varchar(32) NOT NULL,
  `LastName` varchar(32) NOT NULL,
  `Street` varchar(32) NOT NULL,
  `HouseNumber` varchar(4) NOT NULL,
  `City` varchar(32) NOT NULL,
  `PhoneNumber` varchar(16) NOT NULL,
  `EmailAddress` varchar(32) NOT NULL,
  `Fine` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`MembershipNumber`, `FirstName`, `LastName`, `Street`, `HouseNumber`, `City`, `PhoneNumber`, `EmailAddress`, `Fine`) VALUES
(1000, 'Pascal', 'van Gastel', 'Lovensdijkstraat', '61', 'Breda', '0765238754', 'ppth.vangastel@avans.nl', 0),
(1001, 'Erco', 'Argante', 'Hogeschoollaan', '1', 'Breda', '0765231234', 'e.argante@avans.nl', 0),
(1002, 'Marice', 'Bastiaensen', 'Lovensdijkstraat', '63', 'Breda', '0765236789', 'mmcm.bastiaensen@avans.nl', 5);

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `ReservationDate` date NOT NULL,
  `MembershipNumber` int(11) NOT NULL,
  `BookISBN` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`ReservationDate`, `MembershipNumber`, `BookISBN`) VALUES
('2013-09-29', 1001, 1111);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`ISBN`);

--
-- Indexes for table `copy`
--
ALTER TABLE `copy`
  ADD PRIMARY KEY (`CopyID`),
  ADD KEY `BookISBN` (`BookISBN`);

--
-- Indexes for table `loan`
--
ALTER TABLE `loan`
  ADD PRIMARY KEY (`ReturnDate`,`MembershipNr`,`CopyID`),
  ADD KEY `MembershipNr` (`MembershipNr`),
  ADD KEY `CopyID` (`CopyID`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`MembershipNumber`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReservationDate`,`MembershipNumber`,`BookISBN`),
  ADD KEY `MembershipNumber` (`MembershipNumber`),
  ADD KEY `BookISBN` (`BookISBN`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `copy`
--
ALTER TABLE `copy`
  ADD CONSTRAINT `copy_ibfk_1` FOREIGN KEY (`BookISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE;

--
-- Constraints for table `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`MembershipNr`) REFERENCES `member` (`MembershipNumber`) ON UPDATE CASCADE,
  ADD CONSTRAINT `loan_ibfk_2` FOREIGN KEY (`CopyID`) REFERENCES `copy` (`CopyID`) ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`MembershipNumber`) REFERENCES `member` (`MembershipNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`BookISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE;

