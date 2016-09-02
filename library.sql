-- --------------------------------------------------------

--
-- Database: `library`
--

-- --------------------------------------------------------

DROP SCHEMA IF EXISTS `library` ;
CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET latin1 ;
USE `library` ;

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `ISBN` INT(11) UNSIGNED NOT NULL,
  `Title` VARCHAR(64) NOT NULL,
  `Author` VARCHAR(32) NOT NULL,
  `Edition` INT(6) UNSIGNED NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ISBN`)
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
  `CopyID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT, 
  `LendingPeriod` INT(11) NOT NULL,
  `BookISBN` INT(11) UNSIGNED NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CopyID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `copy` AUTO_INCREMENT = 1000;

ALTER TABLE `copy`
  ADD KEY `BookISBN` (`BookISBN`);

INSERT INTO `copy` (`LendingPeriod`, `BookISBN`) VALUES
(5, 2222),
(21, 1111);

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `LoanDate` DATE NOT NULL,
  `ReturnDate` DATE,
  `MembershipNr` INT(11) UNSIGNED NOT NULL,
  `CopyID` INT(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `loan`
  ADD PRIMARY KEY (`ReturnDate`,`MembershipNr`,`CopyID`),
  ADD KEY `MembershipNr` (`MembershipNr`),
  ADD KEY `CopyID` (`CopyID`);

INSERT INTO `loan` (`LoanDate`, `MembershipNr`, `CopyID`) VALUES
(NOW(), 1000, 1001);

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `MembershipNumber` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(32) NOT NULL,
  `LastName` VARCHAR(32) NOT NULL,
  `Street` VARCHAR(32) NOT NULL,
  `HouseNumber` VARCHAR(4) NOT NULL,
  `City` VARCHAR(32) NOT NULL,
  `PhoneNumber` VARCHAR(16) NOT NULL,
  `EmailAddress` VARCHAR(32) NOT NULL,
  `Fine` double NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MembershipNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `member` AUTO_INCREMENT = 1000;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`FirstName`, `LastName`, `Street`, `HouseNumber`, `City`, `PhoneNumber`, `EmailAddress`, `Fine`) VALUES
('Pascal', 'van Gastel', 'Lovensdijkstraat', '61', 'Breda', '076-5238754', 'ppth.vangastel@avans.nl', 0),
('Erco', 'Argante', 'Hogeschoollaan', '1', 'Breda', '076-5231234', 'e.argante@avans.nl', 0),
('Jan', 'Montizaan', 'Hogeschoollaan', '1', 'Breda', '076-5231234', 'j.montizaan@avans.nl', 0),
('Frans', 'Spijkerman', 'Hogeschoollaan', '1', 'Breda', '076-5231234', 'f.spijkerman@avans.nl', 0),
('Robin', 'Schellius', 'Hogeschoollaan', '1', 'Breda', '076-5231234', 'r.schellius@avans.nl', 0),
('Maurice', 'van Haperen', 'Hogeschoollaan', '1', 'Breda', '076-5231234', 'mpg.vanhaperen@avans.nl', 0),
('Marice', 'Bastiaensen', 'Lovensdijkstraat', '63', 'Breda', '076-5236789', 'mmcm.bastiaensen@avans.nl', 5);

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `ReservationID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ReservationDate` date NOT NULL,
  `MembershipNumber` INT(11) UNSIGNED NOT NULL,
  `BookISBN` INT(11) UNSIGNED NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY (`ReservationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReservationDate`,`MembershipNumber`,`BookISBN`),
  ADD KEY `MembershipNumber` (`MembershipNumber`),
  ADD KEY `BookISBN` (`BookISBN`);

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`ReservationDate`, `MembershipNumber`, `BookISBN`) VALUES
('2016-09-29', 1001, 1011);

--
-- Constraints for table `copy`
--
ALTER TABLE `copy`
  ADD CONSTRAINT `copy_book` FOREIGN KEY (`BookISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE;

--
-- Constraints for table `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `loan_member` FOREIGN KEY (`MembershipNr`) REFERENCES `member` (`MembershipNumber`) ON UPDATE CASCADE,
  ADD CONSTRAINT `loan_copy` FOREIGN KEY (`CopyID`) REFERENCES `copy` (`CopyID`) ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_member` FOREIGN KEY (`MembershipNumber`) REFERENCES `member` (`MembershipNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_book` FOREIGN KEY (`BookISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE;


-- --------------------------------------------------------
-- Gebruikersaccount maken voor Spring MVC applicatie
--
DROP USER 'spring'@'localhost';
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'test';

GRANT ALL ON `library`.* TO 'spring'@'localhost';
