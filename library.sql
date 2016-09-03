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
(5, 2222),
(5, 2222),
(5, 1011),
(5, 1011),
(5, 1011),
(5, 1014),
(5, 1014),
(5, 1014),
(5, 1021),
(5, 1021),
(5, 1111),
(5, 1111),
(5, 2222),
(5, 2222),
(5, 2222),
(5, 2222),
(5, 2223),
(5, 2223),
(5, 2223),
(5, 3333),
(5, 3333),
(5, 4001),
(5, 4003),
(5, 4005),
(5, 4005),
(5, 4005),
(5, 8000),
(5, 8888),
(21, 1111),
(21, 1111),
(21, 1111);

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `LoanDate` TIMESTAMP NOT NULL,
  `ReturnedDate` TIMESTAMP,
  `MemberID` INT(11) UNSIGNED NOT NULL,
  `CopyID` INT(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `loan`
  ADD PRIMARY KEY (`ReturnedDate`,`MemberID`,`CopyID`),
  ADD KEY `MemberID` (`MemberID`);

INSERT INTO `loan` (`LoanDate`, `MemberID`, `CopyID`) VALUES
(NOW(), 1000, 1001),
(NOW(), 1006, 1001),
(NOW(), 1002, 1011),
(NOW(), 1001, 1014),
(NOW(), 1005, 1014),
(NOW(), 1000, 1023),
(NOW(), 1003, 1021),
(NOW(), 1001, 1021),
(NOW(), 1004, 1011),
(NOW(), 1000, 1021),
(NOW(), 1001, 1013);

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `MemberID` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(32) NOT NULL,
  `LastName` VARCHAR(32) NOT NULL,
  `Street` VARCHAR(32) NOT NULL,
  `HouseNumber` VARCHAR(4) NOT NULL,
  `City` VARCHAR(32) NOT NULL,
  `PhoneNumber` VARCHAR(16) NOT NULL,
  `EmailAddress` VARCHAR(32) NOT NULL,
  `Fine` double NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MemberID`)
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
  `ReservationDate` TIMESTAMP NOT NULL,
  `MemberID` INT(11) UNSIGNED NOT NULL,
  `CopyID` INT(11) UNSIGNED NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY (`ReservationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReservationDate`,`MemberID`,`CopyID`),
  ADD KEY `MemberID` (`MemberID`),
  ADD KEY `CopyID` (`CopyID`);

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`ReservationDate`, `MemberID`, `CopyID`) VALUES
(NOW(), 1002, 1012);

--
-- Constraints for table `copy`
--
ALTER TABLE `copy`
  ADD CONSTRAINT `copy_book` FOREIGN KEY (`BookISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE;

ALTER TABLE `loan`
  ADD CONSTRAINT `loan_copy` FOREIGN KEY (`CopyID`) REFERENCES `copy` (`CopyID`);
ALTER TABLE `loan`
  ADD CONSTRAINT `loan_member` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MemberID`) ON UPDATE CASCADE;


--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_member` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MemberID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_book` FOREIGN KEY (`CopyID`) REFERENCES `copy` (`CopyID`) ON UPDATE CASCADE;


-- --------------------------------------------------------
-- Gebruikersaccount maken voor Spring MVC applicatie
--
DROP USER 'spring'@'localhost';
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'test';

GRANT ALL ON `library`.* TO 'spring'@'localhost';



-- -----------------------------------------------------
-- Toon alle uitleningen. 
--
CREATE OR REPLACE VIEW `view_all_loans` AS 
SELECT 
	`loan`.`LoanDate`,
	`loan`.`ReturnedDate`,
	`book`.`ISBN`,
	`book`.`Title`,
	`book`.`Author`,
	`book`.`Edition`,
	`copy`.`CopyID`,
	`copy`.`LendingPeriod`,
	`member`.`MemberID`,
	`member`.`FirstName`,
	`member`.`LastName`
FROM `loan`
LEFT JOIN `copy` USING(`CopyID`)
LEFT JOIN `book` ON `copy`.`BookISBN` = `book`.`ISBN`
LEFT JOIN `member` USING (`MemberID`);

-- -----------------------------------------------------
-- Toon alle reserveringen. 
--
CREATE OR REPLACE VIEW `view_all_reservations` AS 
SELECT 
	`reservation`.`ReservationID`,
	`reservation`.`ReservationDate`,
	`copy`.`CopyID`,
	`copy`.`LendingPeriod`,
	`book`.`ISBN`,
	`book`.`Title`,
	`book`.`Author`,
	`book`.`Edition`,
	`member`.`MemberID`,
	`member`.`FirstName`,
	`member`.`LastName`
FROM `reservation`
LEFT JOIN `copy` USING(`CopyID`)
LEFT JOIN `book` ON `copy`.`BookISBN` = `book`.`ISBN`
LEFT JOIN `member` USING (`MemberID`);

-- -----------------------------------------------------
-- Toon alle copies. 
--
CREATE OR REPLACE VIEW `view_all_copies` AS 
SELECT 
	`copy`.`CopyID`,
	`copy`.`LendingPeriod`,
	`book`.`ISBN`,
	`book`.`Title`,
	`book`.`Author`,
	`book`.`Edition`
FROM `copy`
LEFT JOIN `book` ON `copy`.`BookISBN` = `book`.`ISBN`;
