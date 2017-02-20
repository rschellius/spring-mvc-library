-- --------------------------------------------------------

--
-- Database: `library`
--

-- --------------------------------------------------------
-- 
-- Om een export te maken van een database naar het bestand 'databasedump.sql', vanaf de commandline:
-- 
-- \xampp\mysql\bin\mysqldump.exe --host=104.155.74.161 --extended-insert=FALSE -p --password=test --user=spring --databases library > databasedump.sql
-- 
-- 

DROP SCHEMA IF EXISTS `library` ;
CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8;
USE `library` ;

-- Prevents errors at Google Cloud SQL 
SET sql_mode = '';

--
-- Table structure for table `repository`
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
-- Table structure for table `service`
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
-- Table structure for table `exception`
--

CREATE TABLE `loan` (
  `LoanID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `LoanDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ReturnedDate` TIMESTAMP NULL,
  `MemberID` INT(6) UNSIGNED NOT NULL,
  `CopyID` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`LoanID`),
  KEY (`MemberID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `repository`
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
  `MemberID` INT(6) UNSIGNED NOT NULL,
  `CopyID` INT(11) UNSIGNED NOT NULL,
  `UpdatedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY (`ReservationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReservationDate`,`MemberID`,`CopyID`),
  ADD KEY `MemberID` (`MemberID`),
  ADD KEY `CopyID` (`CopyID`);

--
-- Constraints for table `service`
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
-- 	
-- CREATE USER 'spring'@'%' IDENTIFIED BY '_avans_spring_';
CREATE USER 'spring'@'localhost' IDENTIFIED BY 'test';
GRANT ALL ON `library`.* TO 'spring'@'localhost';

-- -----------------------------------------------------
-- 
-- Toon alle boeken met het aantal copies. 
--
CREATE OR REPLACE VIEW `view_all_books` AS
SELECT 
	`book`.`ISBN`,
	COUNT(`copy`.`CopyID`) AS `NrOfCopies`,
	`book`.`Title`,
	`book`.`Author`,
	`book`.`ShortDescription`,
	`book`.`Edition`,
	`book`.`ImageURL`
FROM `book`
LEFT JOIN `copy` ON book.ISBN = copy.BookISBN
group by `book`.`ISBN`
ORDER BY `book`.`Title`;

select * from view_all_books where `ISBN` = '9789023438786';

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
-- Toon alle actueel beschikbare copies van boeken. 
-- Je ziet dus alleen de laatst geleende of ingeleverde boeken. 
--
CREATE OR REPLACE VIEW `view_available_copies` AS 
SELECT 
	`book`.`ISBN`,
	`copy`.`CopyID`,
	`loan`.`LoanID`,
	`loan`.`LoanDate`,
	`loan`.`ReturnedDate`,
	(`loan`.`ReturnedDate` IS NOT NULL OR `loan`.`LoanDate` IS NULL) AS `Available`,
	`book`.`Title`,
	`book`.`Author`,
	`book`.`Edition`,
	`copy`.`LendingPeriod`,
	`member`.`MemberID`,
	`member`.`FirstName`,
	`member`.`LastName`
FROM `book`
LEFT JOIN `copy` ON `copy`.`BookISBN` = `book`.`ISBN`
LEFT JOIN `loan` USING(`CopyID`)
LEFT JOIN `member` USING (`MemberID`)
WHERE (
	(`loan`.`LoanDate` IS NULL) 
	OR 
	(`loan`.`LoanDate` = (SELECT MAX(`LoanDate`) FROM `loan` l2 WHERE `loan`.`CopyID` = l2.CopyID))
)
ORDER BY `CopyID`;

-- select * from view_available_copies where `ISBN` = '9781683688815';

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

-- select * from view_all_copies;

-- -----------------------------------------------------
-- Toon alle copies per boek, met informatie over beschikbaarheid.
-- We willen zien of een boek beschikbaar is, en zo niet, aan wie het dan uitgeleend is.
--
CREATE OR REPLACE VIEW `view_lending` AS 
SELECT 
	`loan`.`LoanID`,
	`copy`.`CopyID`,
	`book`.`ISBN`,
	`loan`.`LoanDate`,
	`loan`.`ReturnedDate`,
	`copy`.`LendingPeriod`,
	`member`.`MemberID`,
	`member`.`FirstName`,
	`member`.`LastName`
FROM `loan`
RIGHT JOIN `copy` USING(`CopyID`)
LEFT JOIN `book` ON `copy`.`BookISBN` = `book`.`ISBN`
LEFT JOIN `member` ON `loan`.`MemberID` = `member`.`MemberID`;


-- -----------------------------------------------------
-- Toon alle copies per boek, met informatie over beschikbaarheid.
-- We willen zien of een boek beschikbaar is, en zo niet, aan wie het dan uitgeleend is.
--
CREATE OR REPLACE VIEW `view_booklending` AS 
SELECT
	`copy`.`CopyID`,
	`book`.`ISBN`,
	`loan`.`LoanID`,
	`loan`.`LoanDate`,
	`loan`.`ReturnedDate`,
	`copy`.`LendingPeriod`,
	`member`.`MemberID`,
	`member`.`FirstName`,
	`member`.`LastName`
FROM `book`
LEFT JOIN `copy` ON `copy`.`BookISBN` = `book`.`ISBN`
LEFT JOIN `loan` USING(`CopyID`)
LEFT JOIN `member` ON `loan`.`MemberID` = `member`.`MemberID`
ORDER BY `CopyID`, `LoanID`;

-- select * from view_booklending where ISBN = '9789023438786';

-- -----------------------------------------------------
-- We doen de inserts als laatste. Op dit moment zijn alle PK en FK constraints
-- in stelling gebracht. Iedere insert die lukt geeft dus een valide record 
-- in die tabel. 
--

INSERT INTO `member` (`FirstName`, `LastName`, `Street`, `HouseNumber`, `City`, `PhoneNumber`, `EmailAddress`, `Fine`) VALUES
('Pascal', 'White', 'Somestreet', '61', 'Breda', '076-5234567', 'ppth.white@theserver.nl', 0),
('Ernst', 'Green', 'Highschoollane', '10', 'Breda', '076-5231235', 'e.green@theserver.nl', 0),
('Jan', 'Schwartz', 'Highhills', '1', 'Breda', '076-5231236', 'j.schwartz@theserver.nl', 0),
('Franz', 'Spikeman', 'Somestreet', '1', 'Breda', '076-5231237', 'fr.spikeman@theserver.nl', 0),
('Rob', 'Shellfish', 'Somestreet', '1', 'Breda', '076-5231238', 'rshellfish@theserver.nl', 0),
('Maurice', 'White', 'Greenlane', '1', 'Breda', '076-5231239', 'm.white@theserver.nl', 0),
('John', 'Bastians', 'Whitehall', '63', 'Breda', '076-5236790', 'john@theserver.nl', 5);

-- -----------------------------------------------------

INSERT INTO `book` (`ISBN`, `Title`, `Author`, `ShortDescription`, `Edition`, `ImageURL`, `UpdatedDate`) VALUES
(9780764570780, 'Beginning Web Programming With Html, Xhtml And Css', 'Jon Duckett', '<b>What is this book about?</b> <br /><br /> Beginning Web Programming with HTML, XHTML, and CSS teaches you how to write Web pages using HTML, XHTML, and CSS. It follows standards-based principles, but also teaches readers ways around problems they are likely to face using (X)HTML. <br /><br /> While XHTML is the "current" standard, the book still covers HTML because many people do not yet understand that XHTML is the official successor to HTML, and many readers will still stick with HTML for backward compatibility and simpl...', 'Engelstalig | Paperback | 2004', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/9/1/5/6/9200000005386519.jpg', '2016-09-18 21:27:50'),
(9780764583063, 'Accessible Xhtml And Css Web Sites', 'Jon Duckett', '* Shows Web developers how to make the transition from HTML to XHTML, an XML-based reformulation of HTML that offers greater design flexibility * Demonstrates how to work with CSS (Cascading Style Sheets)-now supported by ninety percent of browsers and integral to new site-building tools from Macromedia and others-and implement a consistent style throughout and entire site * Explains how to make a site accessible to people with impaired vision, limited hand use, dyslexia, and other issues-now a legal requirement for many sites in the U.S. and the U.K.', 'Engelstalig | Paperback | 2005', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/7/9/5/9200000021775970.jpg', '2016-09-18 21:29:28'),
(9781118008188, 'HTML & CSS', 'Jon Duckett', 'A full-color introduction to the basics of HTML and CSS from the publishers of Wrox! Every day, more and more people want to learn some HTML and CSS. Joining the professional web designers and programmers are new audiences who need to know a little bit of code at work (update a content management system or e-commerce store) and those who want to make their personal blogs more attractive. Many books teaching HTML and CSS are dry and only written for those who want to become programmers, which is ...', 'Engelstalig | Paperback | 2011', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/1/7/2/1001004010712714.jpg', '2016-09-16 17:46:24'),
(9781118057322, 'Beginning HTML, XHTML, CSS, and JavaScript', 'Rob Larsen', 'An indispensable introductory guide to creating web pages using the most up-to-date standards This beginner guide shows you how to use XHTML, CSS, and JavaScript to create compelling Web sites. While learning these technologies, you will discover coding practices such as writing code that works on multiple browsers including mobile devices, how to use AJAX frameworks to add interactivity to your pages, and how to ensure your pages meet accessible requirements. Packed with real-world examples, th...', 'Engelstalig | Ebook | 2015', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/3/5/4/6/9200000005386453.jpg', '2016-09-16 17:46:46'),
(9781118531648, 'JavaScript & JQuery', 'Jon Duckett', 'Learn JavaScript and jQuery a nicer way This full-color book adopts a visual approach to teaching JavaScript & jQuery, showing you how to make web pages more interactive and interfaces more intuitive through the use of inspiring code examples, infographics, and photography. The content assumes no previous programming experience, other than knowing how to create a basic web page in HTML & CSS. You''ll learn how to achieve techniques seen on many popular websites (such as adding animation, tabbed p...', 'Engelstalig | Paperback | 2014', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/8/2/5/3/9200000008613528.jpg', '2016-09-16 17:46:36'),
(9781326244415, 'JavaÔäó Programming: A Complete Project Lifecycle Guide', 'Nitin Shreyakar', '''Java Programming: A complete project lifecycle guide'' provides an accessible yet comprehensive explanation to build an industry standard application from the start till the end. The processes and methodologies used are industry standard quality implementations in various organizations like major banks and financial institutions. The book moves from explanation of Unified Process to a full implementation of FX system. It takes you through various stages of a project lifecycle from requirement ga...', 'Engelstalig | Ebook | 2015', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/9/2/1/9200000043451294.jpg', '2016-09-16 17:05:03'),
(9781449388942, 'Log4J', 'J. Steven Perry', 'Log4j has been around for a while now, and it seems like so many applications use it. I''ve used it in my applications for years now, and I''ll bet you have too. But every time I need to do something with log4j I''ve never done before I find myself searching for examples of how to do whatever that is, and I don''t usually have much luck. I believe the reason for this is that there is a not a great deal of useful information about log4j, either in print or on the Internet. The information is too simp...', 'Engelstalig | Ebook | 2009', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/3/8/2/9200000010122837.jpg', '2016-09-16 17:05:31'),
(9781457187971, 'Java 8. Leksykon kieszonkowy', 'Robert Liguori', '??<br /><br />Java jest dzi? j?zykiem, kt??ry programi?ci wybieraj? najcz??ciej, gdy maj? przed sob? skomplikowany projekt, wymagaj?cy najwy?szej wydajno?ci, jako?ci, bezpiecze?stwa oraz integracji z innymi systemami. Rozwijany od blisko dwudziestolecia, j?zyk ten doczeka? si? wersji oznaczonej numerem 8. Ta edycja zosta?a wzbogacona o wiele nowo?ci, m.in. o d?ugo oczekiwane wyra?enia lambda. Je?eli szukasz por?cznej ksi??ki, do kt??rej mo?esz si?gn?? w przypadku w?tpliwo?ci, to trafi?e? na doskona?? pozycj?!...', 'Ebook | 2014', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/7/6/3/9200000035863677.jpg', '2016-09-16 17:05:39'),
(9781468963830, 'Java Programming', 'Francis John Thottungal', 'Java Programming for Beginners is a reference guide for quickly learning Java. It covers the basic elements including some object oriented concepts. It introduces basic applet construction and deployment.', 'Engelstalig | Ebook | 2015', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/2/6/9/1/9200000049851962.jpg', '2016-09-16 17:04:51'),
(9781683688815, 'jQuery For Beginners', 'Jennifer Joyner', 'Web pages and all the tools they come with, together will all the mind-blowing features like virtual programs which work on-line, are pure products of genius in programming and software engineering. Such are developed by use of web application development software such as the jQuery JavaScript library. The web applications are based or stored in their respective servers. However, before storage of a web application and before it even becomes a web application, it is written by a program develope...', 'Engelstalig | Ebook | 2016', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/2/8/3/4/9200000061654382.jpg', '2016-09-16 17:03:31'),
(9789023400226, 'De avonden', 'Gerard Reve', 'De Avonden is een van de belangrijkste boeken uit de Nederlandse literatuur; het is klassiek geworden als de aangrijpende beschrijving van de verstikkende saaiheid die Nederland in de naoorlogse jaren beheerste. In Frits van Egters weerspiegelt zich de jeugd, die zijn leven ziet verlopen in zinloosheid, eentonigheid en eenzaamheid. Humor en de hoop dat er toch een hoger en groter Iets bestaat, houden hem op de been.   De geluiden van afschuw over dit boek, bijvoorbeeld door Godfried Bomans, die ...', 'Nederlandstalig | Paperback', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/4/5/6/1001004007256544.jpg', '2016-09-18 21:25:33'),
(9789023410843, 'Nooit Meer Slapen', 'Willem Frederik Hermans', 'Nooit meer slapen is het meesterlijke verhaal van de jonge geoloog Alfred Issendorf, die in het moerassige noorden van Noorwegen onderzoek wil verrichten om de hypothese van zijn leermeester en promotor Sibbelee te staven. Issendorf is ambitieus: hij hoopt dat hem op deze reis iets groots te wachten staat, dat zijn naam aan een belangrijk wetenschappelijk feit zal worden verbonden. Deze ambitie hangt samen met het verlangen het werk van zijn vader, die door een ongeluk tijdens een onderzoekstoch...', 'Nederlandstalig | Other Formats | 2003', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/4/0/0/8/1001004004808004.jpg', '2016-09-18 21:25:57'),
(9789023438786, 'De Ontdekking Van De Hemel', 'Harry Mulisch', 'Is de hemel een organisatie die op het punt staat begrepen en opgerold te worden door de technologisch hoogontwikkelde mens van de twintigste eeuw? De ontdekking van de hemel (1992) is een totaalroman waarin alle themaÔÇÖs en obsessies uit het werk van Harry Mulisch in 65 hoofdstukken bijeenkomen. Dit monumentale boek is tegelijk een psychologische roman, een filosofische roman, een tijdroman, een ontwikkelingsroman, een avonturenroman en een alles overkoepelend mysteriespel.', 'Nederlandstalig | Hardcover | 2000', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/2/4/5/6/1001004007256542.jpg', '2016-09-18 21:22:05'),
(9789023457299, 'Bonita Avenue', 'Peter Buwalda', 'Joni Sigerius, de stiefdochter van de rector magnificus van de Twentse universiteit, drijft samen met haar vriend Aaron een handeltje dat ze maar liever voor haar krachtige en briljante vader verborgen houdt. Het is in het jaar van de vuurwerkramp dat ook in het gezin de boel explodeert. Niet alleen lopen Joni en Aaron tegen de lamp, het is ook de zomer dat de enige en echte zoon van Sigerius vrijkomt uit de Scheveninger strafgevangenis. <br /><br />Bonita Avenue is een weergaloos verhaal over idylle en schoonheid, noodlot en verval. Peter Buwalda schreef een wervelende debuutroman, met het verbluffende gemak van een geoefend schrijver.', 'Nederlandstalig | Paperback | 2010', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/3/3/1/8/1001004008488133.jpg', '2016-09-18 21:13:43'),
(9789023462972, 'Caesarion', 'Tommy Wieringa', 'Ludwig Unger komt voort uit het huwelijk van twee beroemdheden, en was voorbestemd van hun beider talenten de vermenigvuldiging te zijn. De zoon van Julius Caesar en Cleopatra werd Caesarion genoemd, kleine Caesar. Het is ook de koosnaam die Ludwig Unger van zijn moeder krijgt. Met haar woont hij op een eroderende klif in Oost-Engeland. Na iedere winter is de zee het huis dichter genaderd, net zo lang tot het vergaat. Een odyssee begint.Caesarion is een geschiedenis over schoonheid en verval, over de knellende band tussen moeder en kind, hun trouw en ontrouw, en ten slotte, wanneer deze dingen zijn verteld, over het belang jezelf ten offer te brengen.', 'Nederlandstalig | Paperback | 2010', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/4/6/1/3/1001004008293164.jpg', '2016-09-18 21:30:32'),
(9789023491033, 'Suzy vindt van niet', 'Peter Buwalda', 'Peter Buwalda, schrijver van de veelvuldig bekroonde internationale bestseller Bonita Avenue, is sinds een jaar columnist van de Volkskrant. In Suzy vindt van niet bundelt hij zijn eerste tweeënvijftig eigenzinnige, messcherpe en vaak hilarische columns.<br /><br />''Volgens mij is Buwalda de nieuwe Carmiggelt.''- Sylvia Witteman<br />''Wat een feest! Eindelijk weer een columnist waarvoor ik keihard de trap af hol.''- Nico Dijkshoorn<br />''Wat je noemt geouwehoer waar Gods zegen waarlijk op rust.''- Hans Maarten van den Brink', 'Nederlandstalig | Ebook | 2014', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/7/1/6/9200000031296177.jpg', '2016-09-18 21:13:59'),
(9789029592079, 'Een gelukkige poging', 'Joost Zwagerman', 'De verhalen in Kroondomein zijn zeer divers. Zo wordt in ''Briefjes'' op onderkoelde wijze verslag gedaan van de aversie van een jonge onderwijzeres tegen haar beroep, terwijl de sfeer van het titel verhaal, waarin een schrijver zijn vrouw overmatig bejubelt, nog het beste als baldadig kan worden omschreven. Dat Zwagermans figuren vaak gefascineerd zijn door het gesproken en geschreven woord blijkt uit verhalen als ''Oudenier haakt in'' en ''Een gelukkige poging''.<br />Kroondomein, Joost Zwagermans eerste verhalenbundel, is ironisch, lyrisch, en wordt gekenmerkt door een even lichte toon die zijn debuut roman De houdgreep zo deed opvallen.', 'Nederlandstalig | Ebook | 2013', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/4/1/6/9200000011546147.jpg', '2016-09-18 21:16:09'),
(9789038826998, 'Fantoompijn', 'Arnon Grunberg', 'Het leven dient in scène te worden gezet. En je leven in scène zetten, dat kun je niet aan anderen overlaten. Dat moet je zelf doen. Robert G. Mehlman wil de werkelijkheid produceren zoals anderen boeken, films en schilderijen produceren."Het waren deze kleine toneelstukjes die mij gelukkig maakten. Het moment dat ik zelf begon te geloven in de zorgvuldig door mij geënsceneerde werkelijkheid, dat was het moment van de euforie. Het moment dat het verhaal dat je zelf hebt bedacht er met je vandoor...', 'Nederlandstalig | Paperback | 2002', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/0/7/2/4/1001004001654270.jpg', '2016-09-18 21:16:49'),
(9789043017961, 'Kritisch denken', 'T. ter Berg', 'Kritische denkvaardigheden bezit u niet van nature. Wel kunt u deze trainen. Dit kan met behulp van de unieke methodiek ''Kritische Denken met Rationale'', waar dit boek bij hoort.<br /><br />Rationale is de naam van een softwareprogramma dat u helpt informatie te ordenen, redeneringen te visualiseren en vervolgens een goed en gefundeerd betoog op te bouwen. Ook leert u hoe u redeneringen van anderen kunt herkennen, analyseren en beoordelen. Dit boek gaat in op de basisbegrippen van kritisch denken en geeft i...', 'Nederlandstalig | Paperback | 2009', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/4/5/2/1/1001004006461254.jpg', '2016-09-16 18:19:35'),
(9789044622294, 'Het leven van Pi', 'Yann Martel', 'Het leven van Pi vertelt het ongelooflijke verhaal van Piscine Patel (Pi), een zestienjarige Indiase jongen wiens vader een dierentuin in India heeft. Wanneer de familie besluit het land te verlaten, wordt de hele dierentuin ingescheept. Maar het schip vergaat en de enige overlevenden op de reddingssloep zijn Pi, een hyena, een zebra met een gebroken been en een tijger van 200 kilo.<br /><br />227 dagen dobbert de sloep over de Grote Oceaan _ het decor voor een buitengewoon fantasierijke roman. ''Dit verhaal doet je in God geloven,'' aldus een personage. Dat gaat misschien wat ver, maar wie Het leven van Pi leest, m??et wel geloven in de onverwoestbare kracht van verhalen.', 'Nederlandstalig | Paperback | 2012', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/7/5/7/0/9200000005930757.jpg', '2016-09-16 17:08:19'),
(9789048803408, 'De Joodse messias', 'Arnon Grunberg', 'Xavier Radek is een in Basel woonachtige, geassimileerde, niet-joodse jongeman met een missie: hij wil meer weten over het lijden van de joden. Daarom besluit hij de ''vijanden van het geluk'' te gaan troosten. Zijn grootvader was een nazi die het handwerk van de dood nog verstond. Zijn vader is een bekende architect, met een voorliefde voor massagesalons. En dan zijn moeder: een hartstochtelijke vrouw, die een aquarellerende dictator die zeventig jaar geleden actief was consequent aanduidt als ''J...', 'Nederlandstalig | Ebook | 2009', 'https://s.s-bol.com/imgbase0/imagebase3/large/FC/5/6/0/5/1001004007115065.jpg', '2016-09-18 21:17:10'),
(9789052269023, 'De Geschiedenis Van Mijn Kaalheid', 'Marek van der Jagt', 'Hoe voelt het als jouw leugen de waarheid van de rest van de wereld wordt? In het begin vóelt het onbehaaglijk, maar die onbehaaglijkheid is van korte duur. In De geschiedenis van mijn kaalheid vertelt de Weense filosofiestudent Marek van der Jagt over zijn zoektocht naar de amour fou. Hij heeft gezien wat zijn ouders van hun huwelijk hebben gemaakt en hij wil in plaats daarvan een liefde'' die niet tot geluk leidt, die niets met geluk te maken wil hebben en die toch de moeite waard is''. Na een t...', 'Nederlandstalig | Hardcover | 2000', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/1/7/5/9/666869571.jpg', '2016-09-18 21:17:36'),
(9789089900029, 'Zoeken naar Lena', 'M Myant', 'Tsjechië, 1942. De elfjarige Jan is getuige van een<br />razzia in zijn dorp. Duitse soldaten schieten alle<br />mannen ter plekke dood en nemen vrouwen en<br />kinderen mee. Jan weet ternauwernood te ontkomen<br />en zwerft verloren over het platteland. Dan ontdekt<br />hij dat zijn kleine zusje Lena niet naar een concentratiekamp<br />is gestuurd, zoals zijn moeder en zijn oudste<br />zus, maar dat ze bij een Duitse boerenfamilie is<br />ondergebracht, zodat zij zal opgroeien tot een goed<br />Nazi-meisje. Vanaf dat moment heeft Jan nog maar<br />één d...', 'Nederlandstalig | Paperback | 2009', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/7/1/8/4/1001004006414817.jpg', '2016-09-18 21:18:28'),
(9789462080089, 'Nederlandse architectuur in 250 topstukken', 'Ole Bouman', 'Architectuurhistorici van het NAi nemen u, in het rijk geïllustreerde boek Nederlandse architectuur in 250 topstukken, mee langs 250 markante gebouwen en ontwerpen. Van Cuypers, Berlage, Kromhout, Van Eesteren, Stam en Wijdeveld, tot Weeber, Coenen en Koolhaas. Een ware ontdekkingsreis door de rijke architectuurgeschiedenis van Nederland.<br /><br />Weinig landen kennen zo’n rijke en gevarieerde architectuurgeschiedenis als Nederland. Aan de hand van de collectie van het Nederlands Architectuurinstituut, di...', 'Nederlandstalig | Hardcover | 2012', 'https://s.s-bol.com/imgbase0/imagebase/large/FC/4/0/5/9/9200000005399504.jpg', '2016-09-18 21:18:41');

-- --------------------------------------------------------
--
-- Gegevens worden geëxporteerd voor tabel `service`
--

INSERT INTO `copy` (`CopyID`, `LendingPeriod`, `BookISBN`, `UpdatedDate`) VALUES
(1000, 21, 9781683688815, '2016-09-18 13:56:23'),
(1001, 21, 9781683688815, '2016-09-18 13:56:23'),
(1002, 21, 9781683688815, '2016-09-18 13:56:23'),
(1005, 21, 9781468963830, '2016-09-18 13:56:23'),
(1006, 21, 9781468963830, '2016-09-18 13:56:23'),
(1007, 21, 9781326244415, '2016-09-18 13:56:23'),
(1008, 21, 9781326244415, '2016-09-18 13:56:23'),
(1009, 21, 9781449388942, '2016-09-18 13:56:23'),
(1010, 21, 9781457187971, '2016-09-18 13:56:23'),
(1011, 21, 9781457187971, '2016-09-18 13:56:23'),
(1012, 21, 9789023400226, '2016-09-18 13:56:23'),
(1013, 21, 9789023400226, '2016-09-18 13:56:23'),
(1014, 21, 9789023438786, '2016-09-18 13:56:23'),
(1015, 21, 9789023438786, '2016-09-18 13:56:23'),
(1016, 21, 9789023438786, '2016-09-18 13:56:23'),
(1017, 21, 9789044622294, '2016-09-18 13:56:23'),
(1018, 21, 9789044622294, '2016-09-18 13:56:23'),
(1019, 21, 9789044622294, '2016-09-18 13:56:23'),
(1020, 21, 9781118008188, '2016-09-18 13:56:23'),
(1021, 21, 9781118008188, '2016-09-18 13:56:23'),
(1022, 21, 9781118531648, '2016-09-18 13:56:23'),
(1023, 21, 9781118531648, '2016-09-18 13:56:23'),
(1024, 21, 9781118057322, '2016-09-18 13:56:23'),
(1025, 21, 9780764570780, '2016-09-18 13:56:23'),
(1026, 21, 9780764583063, '2016-09-18 13:56:23'),
(1027, 21, 9780764583063, '2016-09-18 13:56:23'),
(1028, 21, 9789023410843, '2016-09-18 13:56:23'),
(1029, 21, 9789043017961, '2016-09-18 13:56:23'),
(1030, 5, 9789023457299, '2016-09-18 21:13:43'),
(1031, 5, 9789023457299, '2016-09-18 21:13:45'),
(1032, 5, 9789023457299, '2016-09-18 21:13:47'),
(1033, 5, 9789023491033, '2016-09-18 21:13:59'),
(1034, 5, 9789023491033, '2016-09-18 21:14:02'),
(1035, 5, 9789029592079, '2016-09-18 21:16:09'),
(1036, 5, 9789029592079, '2016-09-18 21:16:11'),
(1037, 5, 9789038826998, '2016-09-18 21:16:49'),
(1038, 5, 9789038826998, '2016-09-18 21:16:51'),
(1039, 5, 9789048803408, '2016-09-18 21:17:10'),
(1040, 5, 9789052269023, '2016-09-18 21:17:36'),
(1041, 5, 9789089900029, '2016-09-18 21:18:28'),
(1042, 5, 9789462080089, '2016-09-18 21:18:41'),
(1043, 5, 9789462080089, '2016-09-18 21:18:44'),
(1044, 5, 9789023462972, '2016-09-18 21:30:32'),
(1045, 5, 9789023462972, '2016-09-18 21:30:35');

-- --------------------------------------------------------

