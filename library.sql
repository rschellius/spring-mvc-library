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
  `ISBN` BIGINT UNSIGNED NOT NULL,
  `Title` VARCHAR(64) NOT NULL,
  `Author` VARCHAR(32) NOT NULL,
  `ShortDescription` VARCHAR(1000),
  `Edition` VARCHAR(64),
  `ImageURL` VARCHAR(256),
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `copy`
--

CREATE TABLE `copy` (
  `CopyID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `LendingPeriod` INT(11) UNSIGNED NOT NULL,
  `BookISBN` BIGINT UNSIGNED NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CopyID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `copy` AUTO_INCREMENT = 1000;

ALTER TABLE `copy`
  ADD KEY `BookISBN` (`BookISBN`);

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `LoanID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `LoanDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ReturnedDate` TIMESTAMP NULL,
  `MemberID` INT(11) UNSIGNED NOT NULL,
  `CopyID` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`LoanID`),
  KEY (`MemberID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `PhoneNumber` VARCHAR(16) UNIQUE NOT NULL,
  `EmailAddress` VARCHAR(32) UNIQUE NOT NULL,
  `Fine` double NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MemberID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `member` AUTO_INCREMENT = 1000;

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
-- DROP USER 'spring'@'localhost';
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'test';

GRANT ALL ON `library`.* TO 'spring'@'localhost';



-- -----------------------------------------------------
-- Toon alle uitleningen. 
--
CREATE OR REPLACE VIEW `view_all_loans` AS 
SELECT 
	`loan`.`LoanID`,
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


-- -----------------------------------------------------
-- We doen de inserts als laatste. Op dit moment zijn alle PK en FK constraints
-- in stelling gebracht. Iedere insert die lukt geeft dus een valide record 
-- in die tabel. 
--

INSERT INTO `member` (`FirstName`, `LastName`, `Street`, `HouseNumber`, `City`, `PhoneNumber`, `EmailAddress`, `Fine`) VALUES
('Pascal', 'van Gastel', 'Lovensdijkstraat', '61', 'Breda', '076-5238754', 'ppth.vangastel@avans.nl', 0),
('Erco', 'Argante', 'Hogeschoollaan', '1', 'Breda', '076-5231235', 'e.argante@avans.nl', 0),
('Jan', 'Montizaan', 'Hogeschoollaan', '1', 'Breda', '076-5231236', 'j.montizaan@avans.nl', 0),
('Frans', 'Spijkerman', 'Hogeschoollaan', '1', 'Breda', '076-5231237', 'f.spijkerman@avans.nl', 0),
('Robin', 'Schellius', 'Hogeschoollaan', '1', 'Breda', '076-5231238', 'r.schellius@avans.nl', 0),
('Maurice', 'van Haperen', 'Hogeschoollaan', '1', 'Breda', '076-5231239', 'mpg.vanhaperen@avans.nl', 0),
('Marice', 'Bastiaensen', 'Lovensdijkstraat', '63', 'Breda', '076-5236790', 'mmcm.bastiaensen@avans.nl', 5);

--
-- Data for table `book`
--
INSERT INTO `book` (`ISBN`, `Title`, `Author`, `ShortDescription`, `Edition`, `ImageURL`, `UpdatedDate`) VALUES
(9780764570780, 'Beginning Web Programming With Html, Xhtml And Css', 'Jon Duckett', '<b>What is this book about?</b> <br /><br /> Beginning Web Programming with HTML, XHTML, and CSS teaches you how to write Web pages using HTML, XHTML, and CSS. It follows standards-based principles, but also teaches readers ways around problems they are likely to face using (X)HTML. <br /><br /> While XHTML is the "current" standard, the book still covers HTML because many people do not yet understand that XHTML is the official successor to HTML, and many readers will still stick with HTML for backward compatibility and simpl...', 'Engelstalig | Paperback | 2004', 'http://www.modny73.com/wp-content/uploads/2014/12/shutterstock_172777709-1024x1024.jpg', '2016-09-17 07:27:51'),
(9780764583063, 'Accessible Xhtml And Css Web Sites', 'Jon Duckett', '* Shows Web developers how to make the transition from HTML to XHTML, an XML-based reformulation of HTML that offers greater design flexibility * Demonstrates how to work with CSS (Cascading Style Sheets)-now supported by ninety percent of browsers and integral to new site-building tools from Macromedia and others-and implement a consistent style throughout and entire site * Explains how to make a site accessible to people with impaired vision, limited hand use, dyslexia, and other issues-now a legal requirement for many sites in the U.S. and the U.K.', 'Engelstalig | Paperback | 2005', 'http://www.modny73.com/wp-content/uploads/2014/12/shutterstock_172777709-1024x1024.jpg', '2016-09-17 07:27:51'),
(9781118008188, 'HTML & CSS', 'Jon Duckett', 'A full-color introduction to the basics of HTML and CSS from the publishers of Wrox! Every day, more and more people want to learn some HTML and CSS. Joining the professional web designers and programmers are new audiences who need to know a little bit of code at work (update a content management system or e-commerce store) and those who want to make their personal blogs more attractive. Many books teaching HTML and CSS are dry and only written for those who want to become programmers, which is ...', 'Engelstalig | Paperback | 2011', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/1/7/2/1001004010712714.jpg', '2016-09-16 19:46:24'),
(9781118057322, 'Beginning HTML, XHTML, CSS, and JavaScript', 'Rob Larsen', 'An indispensable introductory guide to creating web pages using the most up-to-date standards This beginner guide shows you how to use XHTML, CSS, and JavaScript to create compelling Web sites. While learning these technologies, you will discover coding practices such as writing code that works on multiple browsers including mobile devices, how to use AJAX frameworks to add interactivity to your pages, and how to ensure your pages meet accessible requirements. Packed with real-world examples, th...', 'Engelstalig | Ebook | 2015', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/3/5/4/6/9200000005386453.jpg', '2016-09-16 19:46:46'),
(9781118531648, 'JavaScript & JQuery', 'Jon Duckett', 'Learn JavaScript and jQuery a nicer way This full-color book adopts a visual approach to teaching JavaScript & jQuery, showing you how to make web pages more interactive and interfaces more intuitive through the use of inspiring code examples, infographics, and photography. The content assumes no previous programming experience, other than knowing how to create a basic web page in HTML & CSS. You''ll learn how to achieve techniques seen on many popular websites (such as adding animation, tabbed p...', 'Engelstalig | Paperback | 2014', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/8/2/5/3/9200000008613528.jpg', '2016-09-16 19:46:36'),
(9781326244415, 'Java™ Programming: A Complete Project Lifecycle Guide', 'Nitin Shreyakar', '''Java Programming: A complete project lifecycle guide'' provides an accessible yet comprehensive explanation to build an industry standard application from the start till the end. The processes and methodologies used are industry standard quality implementations in various organizations like major banks and financial institutions. The book moves from explanation of Unified Process to a full implementation of FX system. It takes you through various stages of a project lifecycle from requirement ga...', 'Engelstalig | Ebook | 2015', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/9/2/1/9200000043451294.jpg', '2016-09-16 19:05:03'),
(9781449388942, 'Log4J', 'J. Steven Perry', 'Log4j has been around for a while now, and it seems like so many applications use it. I''ve used it in my applications for years now, and I''ll bet you have too. But every time I need to do something with log4j I''ve never done before I find myself searching for examples of how to do whatever that is, and I don''t usually have much luck. I believe the reason for this is that there is a not a great deal of useful information about log4j, either in print or on the Internet. The information is too simp...', 'Engelstalig | Ebook | 2009', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/3/8/2/9200000010122837.jpg', '2016-09-16 19:05:31'),
(9781457187971, 'Java 8. Leksykon kieszonkowy', 'Robert Liguori', '??<br /><br />Java jest dzi? j?zykiem, który programi?ci wybieraj? najcz??ciej, gdy maj? przed sob? skomplikowany projekt, wymagaj?cy najwy?szej wydajno?ci, jako?ci, bezpiecze?stwa oraz integracji z innymi systemami. Rozwijany od blisko dwudziestolecia, j?zyk ten doczeka? si? wersji oznaczonej numerem 8. Ta edycja zosta?a wzbogacona o wiele nowo?ci, m.in. o d?ugo oczekiwane wyra?enia lambda. Je?eli szukasz por?cznej ksi??ki, do której mo?esz si?gn?? w przypadku w?tpliwo?ci, to trafi?e? na doskona?? pozycj?!...', 'Ebook | 2014', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/7/6/3/9200000035863677.jpg', '2016-09-16 19:05:39'),
(9781468963830, 'Java Programming', 'Francis John Thottungal', 'Java Programming for Beginners is a reference guide for quickly learning Java. It covers the basic elements including some object oriented concepts. It introduces basic applet construction and deployment.', 'Engelstalig | Ebook | 2015', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/2/6/9/1/9200000049851962.jpg', '2016-09-16 19:04:51'),
(9781683688815, 'jQuery For Beginners', 'Jennifer Joyner', 'Web pages and all the tools they come with, together will all the mind-blowing features like virtual programs which work on-line, are pure products of genius in programming and software engineering. Such are developed by use of web application development software such as the jQuery JavaScript library. The web applications are based or stored in their respective servers. However, before storage of a web application and before it even becomes a web application, it is written by a program develope...', 'Engelstalig | Ebook | 2016', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/2/8/3/4/9200000061654382.jpg', '2016-09-16 19:03:31'),
(9789023400226, 'De avonden', 'Gerard Reve', 'De Avonden is een van de belangrijkste boeken uit de Nederlandse literatuur; het is klassiek geworden als de aangrijpende beschrijving van de verstikkende saaiheid die Nederland in de naoorlogse jaren beheerste. In Frits van Egters weerspiegelt zich de jeugd, die zijn leven ziet verlopen in zinloosheid, eentonigheid en eenzaamheid. Humor en de hoop dat er toch een hoger en groter Iets bestaat, houden hem op de been.   De geluiden van afschuw over dit boek, bijvoorbeeld door Godfried Bomans, die ...', 'Nederlandstalig | Paperback', 'http://www.modny73.com/wp-content/uploads/2014/12/shutterstock_172777709-1024x1024.jpg', '2016-09-17 07:27:51'),
(9789023410843, 'Nooit Meer Slapen', 'Willem Frederik Hermans', 'Nooit meer slapen is het meesterlijke verhaal van de jonge geoloog Alfred Issendorf, die in het moerassige noorden van Noorwegen onderzoek wil verrichten om de hypothese van zijn leermeester en promotor Sibbelee te staven. Issendorf is ambitieus: hij hoopt dat hem op deze reis iets groots te wachten staat, dat zijn naam aan een belangrijk wetenschappelijk feit zal worden verbonden. Deze ambitie hangt samen met het verlangen het werk van zijn vader, die door een ongeluk tijdens een onderzoekstoch...', 'Nederlandstalig | Other Formats | 2003', 'http://www.modny73.com/wp-content/uploads/2014/12/shutterstock_172777709-1024x1024.jpg', '2016-09-17 07:28:42'),
(9789023438786, 'De Ontdekking Van De Hemel', 'Harry Mulisch', 'Is de hemel een organisatie die op het punt staat begrepen en opgerold te worden door de technologisch hoogontwikkelde mens van de twintigste eeuw? De ontdekking van de hemel (1992) is een totaalroman waarin alle thema’s en obsessies uit het werk van Harry Mulisch in 65 hoofdstukken bijeenkomen. Dit monumentale boek is tegelijk een psychologische roman, een filosofische roman, een tijdroman, een ontwikkelingsroman, een avonturenroman en een alles overkoepelend mysteriespel.', 'Nederlandstalig | Hardcover | 2000', 'http://www.modny73.com/wp-content/uploads/2014/12/shutterstock_172777709-1024x1024.jpg', '2016-09-17 07:27:51'),
(9789043017961, 'Kritisch denken', 'T. ter Berg', 'Kritische denkvaardigheden bezit u niet van nature. Wel kunt u deze trainen. Dit kan met behulp van de unieke methodiek ''Kritische Denken met Rationale'', waar dit boek bij hoort.<br /><br />Rationale is de naam van een softwareprogramma dat u helpt informatie te ordenen, redeneringen te visualiseren en vervolgens een goed en gefundeerd betoog op te bouwen. Ook leert u hoe u redeneringen van anderen kunt herkennen, analyseren en beoordelen. Dit boek gaat in op de basisbegrippen van kritisch denken en geeft i...', 'Nederlandstalig | Paperback | 2009', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/4/5/2/1/1001004006461254.jpg', '2016-09-16 20:19:35'),
(9789044622294, 'Het leven van Pi', 'Yann Martel', 'Het leven van Pi vertelt het ongelooflijke verhaal van Piscine Patel (Pi), een zestienjarige Indiase jongen wiens vader een dierentuin in India heeft. Wanneer de familie besluit het land te verlaten, wordt de hele dierentuin ingescheept. Maar het schip vergaat en de enige overlevenden op de reddingssloep zijn Pi, een hyena, een zebra met een gebroken been en een tijger van 200 kilo.<br /><br />227 dagen dobbert de sloep over de Grote Oceaan _ het decor voor een buitengewoon fantasierijke roman. ''Dit verhaal doet je in God geloven,'' aldus een personage. Dat gaat misschien wat ver, maar wie Het leven van Pi leest, móet wel geloven in de onverwoestbare kracht van verhalen.', 'Nederlandstalig | Paperback | 2012', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/7/5/7/0/9200000005930757.jpg', '2016-09-16 19:08:19');

INSERT INTO `copy` (`CopyID`, `LendingPeriod`, `BookISBN`, `UpdatedDate`) VALUES
(1000, 5, 9781683688815, '2016-09-16 19:03:31'),
(1001, 5, 9781683688815, '2016-09-16 19:03:34'),
(1002, 5, 9781683688815, '2016-09-16 19:03:36'),
(1005, 5, 9781468963830, '2016-09-16 19:04:51'),
(1006, 5, 9781468963830, '2016-09-16 19:04:52'),
(1007, 5, 9781326244415, '2016-09-16 19:05:04'),
(1008, 5, 9781326244415, '2016-09-16 19:05:05'),
(1009, 5, 9781449388942, '2016-09-16 19:05:32'),
(1010, 5, 9781457187971, '2016-09-16 19:05:39'),
(1011, 5, 9781457187971, '2016-09-16 19:05:41'),
(1012, 5, 9789023400226, '2016-09-16 19:07:11'),
(1013, 5, 9789023400226, '2016-09-16 19:07:12'),
(1014, 5, 9789023438786, '2016-09-16 19:07:44'),
(1015, 5, 9789023438786, '2016-09-16 19:07:45'),
(1016, 5, 9789023438786, '2016-09-16 19:07:47'),
(1017, 5, 9789044622294, '2016-09-16 19:08:19'),
(1018, 5, 9789044622294, '2016-09-16 19:08:21'),
(1019, 5, 9789044622294, '2016-09-16 19:08:23'),
(1020, 5, 9781118008188, '2016-09-16 19:46:24'),
(1021, 5, 9781118008188, '2016-09-16 19:46:26'),
(1022, 5, 9781118531648, '2016-09-16 19:46:37'),
(1023, 5, 9781118531648, '2016-09-16 19:46:38'),
(1024, 5, 9781118057322, '2016-09-16 19:46:46'),
(1025, 5, 9780764570780, '2016-09-16 19:46:57'),
(1026, 5, 9780764583063, '2016-09-16 20:06:32'),
(1027, 5, 9780764583063, '2016-09-16 20:08:43'),
(1028, 5, 9789023410843, '2016-09-16 20:09:50'),
(1029, 5, 9789043017961, '2016-09-16 20:19:35');

--
-- Dumping data for table `loan`
--

-- INSERT INTO `loan` (`MemberID`, `CopyID`) VALUES
-- (1000, 1001),
-- (1006, 1001),
-- (1002, 1011),
-- (1001, 1014),
-- (1005, 1014),
-- (1000, 1023),
-- (1003, 1021),
-- (1001, 1021),
-- (1004, 1011),
-- (1000, 1021),
-- (1001, 1022);


--
-- Dumping data for table `reservation`
--

-- INSERT INTO `reservation` (`ReservationDate`, `MemberID`, `CopyID`) VALUES
-- (NOW(), 1002, 1012);

