-- MySQL dump 10.16  Distrib 10.1.6-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	5.6.20
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,POSTGRESQL' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table: public.book
--
DROP TABLE IF EXISTS public.book CASCADE;
CREATE TABLE public.book
(
  "ISBN" numeric(20,0) NOT NULL,
  "Title" character varying(64) NOT NULL,
  "Author" character varying(32) NOT NULL,
  "ShortDescription" character varying(1000) DEFAULT NULL::character varying,
  "Edition" character varying(64) DEFAULT NULL::character varying,
  "ImageURL" character varying(256) DEFAULT NULL::character varying,
  "UpdatedDate" timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT book_pkey PRIMARY KEY ("ISBN")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.book
  OWNER TO postgres;


--
-- Dumping data for table "book"
--

-- LOCK TABLES "book" WRITE;
/*!40000 ALTER TABLE "book" DISABLE KEYS */;
INSERT INTO "book" VALUES (9780764570780,'Beginning Web Programming With Html, Xhtml And Css','Jon Duckett','<b>What is this book about?</b> <br /><br /> Beginning Web Programming with HTML, XHTML, and CSS teaches you how to write Web pages using HTML, XHTML, and CSS. It follows standards-based principles, but also teaches readers ways around problems they are likely to face using (X)HTML. <br /><br /> While XHTML is the \"current\" standard, the book still covers HTML because many people do not yet understand that XHTML is the official successor to HTML, and many readers will still stick with HTML for backward compatibility and simpl...','Engelstalig | Paperback | 2004','https://s.s-bol.com/imgbase0/imagebase3/large/FC/9/1/5/6/9200000005386519.jpg','2016-09-18 19:27:50'),(9780764583063,'Accessible Xhtml And Css Web Sites','Jon Duckett','* Shows Web developers how to make the transition from HTML to XHTML, an XML-based reformulation of HTML that offers greater design flexibility * Demonstrates how to work with CSS (Cascading Style Sheets)-now supported by ninety percent of browsers and integral to new site-building tools from Macromedia and others-and implement a consistent style throughout and entire site * Explains how to make a site accessible to people with impaired vision, limited hand use, dyslexia, and other issues-now a legal requirement for many sites in the U.S. and the U.K.','Engelstalig | Paperback | 2005','https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/7/9/5/9200000021775970.jpg','2016-09-18 19:29:28'),(9781118008188,'HTML & CSS','Jon Duckett','A full-color introduction to the basics of HTML and CSS from the publishers of Wrox! Every day, more and more people want to learn some HTML and CSS. Joining the professional web designers and programmers are new audiences who need to know a little bit of code at work (update a content management system or e-commerce store) and those who want to make their personal blogs more attractive. Many books teaching HTML and CSS are dry and only written for those who want to become programmers, which is ...','Engelstalig | Paperback | 2011','https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/1/7/2/1001004010712714.jpg','2016-09-16 15:46:24'),(9781118057322,'Beginning HTML, XHTML, CSS, and JavaScript','Rob Larsen','An indispensable introductory guide to creating web pages using the most up-to-date standards This beginner guide shows you how to use XHTML, CSS, and JavaScript to create compelling Web sites. While learning these technologies, you will discover coding practices such as writing code that works on multiple browsers including mobile devices, how to use AJAX frameworks to add interactivity to your pages, and how to ensure your pages meet accessible requirements. Packed with real-world examples, th...','Engelstalig | Ebook | 2015','https://s.s-bol.com/imgbase0/imagebase3/large/FC/3/5/4/6/9200000005386453.jpg','2016-09-16 15:46:46'),(9781118531648,'JavaScript & JQuery','Jon Duckett','Learn JavaScript and jQuery a nicer way This full-color book adopts a visual approach to teaching JavaScript & jQuery, showing you how to make web pages more interactive and interfaces more intuitive through the use of inspiring code examples, infographics, and photography. The content assumes no previous programming experience, other than knowing how to create a basic web page in HTML & CSS. You''ll learn how to achieve techniques seen on many popular websites (such as adding animation, tabbed p...','Engelstalig | Paperback | 2014','https://s.s-bol.com/imgbase0/imagebase3/large/FC/8/2/5/3/9200000008613528.jpg','2016-09-16 15:46:36'),(9781326244415,'Java?ö?ñ?? Programming: A Complete Project Lifecycle Guide','Nitin Shreyakar','"Java Programming: A complete project lifecycle guide" provides an accessible yet comprehensive explanation to build an industry standard application from the start till the end. The processes and methodologies used are industry standard quality implementations in various organizations like major banks and financial institutions. The book moves from explanation of Unified Process to a full implementation of FX system. It takes you through various stages of a project lifecycle from requirement ga...','Engelstalig | Ebook | 2015','https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/9/2/1/9200000043451294.jpg','2016-09-16 15:05:03'),(9781449388942,'Log4J','J. Steven Perry','Log4j has been around for a while now, and it seems like so many applications use it. I''ve used it in my applications for years now, and I''ll bet you have too. But every time I need to do something with log4j I''ve never done before I find myself searching for examples of how to do whatever that is, and I don''t usually have much luck. I believe the reason for this is that there is a not a great deal of useful information about log4j, either in print or on the Internet. The information is too simp...','Engelstalig | Ebook | 2009','https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/3/8/2/9200000010122837.jpg','2016-09-16 15:05:31'),(9781457187971,'Java 8. Leksykon kieszonkowy','Robert Liguori','??<br /><br />Java jest dzi? j?zykiem, kt??ry programi?ci wybieraj? najcz??ciej, gdy maj? przed sob? skomplikowany projekt, wymagaj?cy najwy?szej wydajno?ci, jako?ci, bezpiecze?stwa oraz integracji z innymi systemami. Rozwijany od blisko dwudziestolecia, j?zyk ten doczeka? si? wersji oznaczonej numerem 8. Ta edycja zosta?a wzbogacona o wiele nowo?ci, m.in. o d?ugo oczekiwane wyra?enia lambda. Je?eli szukasz por?cznej ksi??ki, do kt??rej mo?esz si?gn?? w przypadku w?tpliwo?ci, to trafi?e? na doskona?? pozycj?!...','Ebook | 2014','https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/7/6/3/9200000035863677.jpg','2016-09-16 15:05:39'),(9781468963830,'Java Programming','Francis John Thottungal','Java Programming for Beginners is a reference guide for quickly learning Java. It covers the basic elements including some object oriented concepts. It introduces basic applet construction and deployment.','Engelstalig | Ebook | 2015','https://s.s-bol.com/imgbase0/imagebase3/large/FC/2/6/9/1/9200000049851962.jpg','2016-09-16 15:04:51'),(9781683688815,'jQuery For Beginners','Jennifer Joyner','Web pages and all the tools they come with, together will all the mind-blowing features like virtual programs which work on-line, are pure products of genius in programming and software engineering. Such are developed by use of web application development software such as the jQuery JavaScript library. The web applications are based or stored in their respective servers. However, before storage of a web application and before it even becomes a web application, it is written by a program develope...','Engelstalig | Ebook | 2016','https://s.s-bol.com/imgbase0/imagebase3/large/FC/2/8/3/4/9200000061654382.jpg','2016-09-16 15:03:31'),(9789023400226,'De avonden','Gerard Reve','De Avonden is een van de belangrijkste boeken uit de Nederlandse literatuur; het is klassiek geworden als de aangrijpende beschrijving van de verstikkende saaiheid die Nederland in de naoorlogse jaren beheerste. In Frits van Egters weerspiegelt zich de jeugd, die zijn leven ziet verlopen in zinloosheid, eentonigheid en eenzaamheid. Humor en de hoop dat er toch een hoger en groter Iets bestaat, houden hem op de been.   De geluiden van afschuw over dit boek, bijvoorbeeld door Godfried Bomans, die ...','Nederlandstalig | Paperback','https://s.s-bol.com/imgbase0/imagebase3/large/FC/4/4/5/6/1001004007256544.jpg','2016-09-18 19:25:33'),(9789023410843,'Nooit Meer Slapen','Willem Frederik Hermans','Nooit meer slapen is het meesterlijke verhaal van de jonge geoloog Alfred Issendorf, die in het moerassige noorden van Noorwegen onderzoek wil verrichten om de hypothese van zijn leermeester en promotor Sibbelee te staven. Issendorf is ambitieus: hij hoopt dat hem op deze reis iets groots te wachten staat, dat zijn naam aan een belangrijk wetenschappelijk feit zal worden verbonden. Deze ambitie hangt samen met het verlangen het werk van zijn vader, die door een ongeluk tijdens een onderzoekstoch...','Nederlandstalig | Other Formats | 2003','https://s.s-bol.com/imgbase0/imagebase/large/FC/4/0/0/8/1001004004808004.jpg','2016-09-18 19:25:57'),(9789023438786,'De Ontdekking Van De Hemel','Harry Mulisch','Is de hemel een organisatie die op het punt staat begrepen en opgerold te worden door de technologisch hoogontwikkelde mens van de twintigste eeuw? De ontdekking van de hemel (1992) is een totaalroman waarin alle thema?ö?ç?ûs en obsessies uit het werk van Harry Mulisch in 65 hoofdstukken bijeenkomen. Dit monumentale boek is tegelijk een psychologische roman, een filosofische roman, een tijdroman, een ontwikkelingsroman, een avonturenroman en een alles overkoepelend mysteriespel.','Nederlandstalig | Hardcover | 2000','https://s.s-bol.com/imgbase0/imagebase/large/FC/2/4/5/6/1001004007256542.jpg','2016-09-18 19:22:05'),(9789023457299,'Bonita Avenue','Peter Buwalda','Joni Sigerius, de stiefdochter van de rector magnificus van de Twentse universiteit, drijft samen met haar vriend Aaron een handeltje dat ze maar liever voor haar krachtige en briljante vader verborgen houdt. Het is in het jaar van de vuurwerkramp dat ook in het gezin de boel explodeert. Niet alleen lopen Joni en Aaron tegen de lamp, het is ook de zomer dat de enige en echte zoon van Sigerius vrijkomt uit de Scheveninger strafgevangenis. <br /><br />Bonita Avenue is een weergaloos verhaal over idylle en schoonheid, noodlot en verval. Peter Buwalda schreef een wervelende debuutroman, met het verbluffende gemak van een geoefend schrijver.','Nederlandstalig | Paperback | 2010','https://s.s-bol.com/imgbase0/imagebase/large/FC/3/3/1/8/1001004008488133.jpg','2016-09-18 19:13:43'),(9789023462972,'Caesarion','Tommy Wieringa','Ludwig Unger komt voort uit het huwelijk van twee beroemdheden, en was voorbestemd van hun beider talenten de vermenigvuldiging te zijn. De zoon van Julius Caesar en Cleopatra werd Caesarion genoemd, kleine Caesar. Het is ook de koosnaam die Ludwig Unger van zijn moeder krijgt. Met haar woont hij op een eroderende klif in Oost-Engeland. Na iedere winter is de zee het huis dichter genaderd, net zo lang tot het vergaat. Een odyssee begint.Caesarion is een geschiedenis over schoonheid en verval, over de knellende band tussen moeder en kind, hun trouw en ontrouw, en ten slotte, wanneer deze dingen zijn verteld, over het belang jezelf ten offer te brengen.','Nederlandstalig | Paperback | 2010','https://s.s-bol.com/imgbase0/imagebase/large/FC/4/6/1/3/1001004008293164.jpg','2016-09-18 19:30:32'),(9789023491033,'Suzy vindt van niet','Peter Buwalda','Peter Buwalda, schrijver van de veelvuldig bekroonde internationale bestseller Bonita Avenue, is sinds een jaar columnist van de Volkskrant. In Suzy vindt van niet bundelt hij zijn eerste twee?½nvijftig eigenzinnige, messcherpe en vaak hilarische columns.<br /><br />''Volgens mij is Buwalda de nieuwe Carmiggelt.''- Sylvia Witteman<br />''Wat een feest! Eindelijk weer een columnist waarvoor ik keihard de trap af hol.''- Nico Dijkshoorn<br />''Wat je noemt geouwehoer waar Gods zegen waarlijk op rust.''- Hans Maarten van den Brink','Nederlandstalig | Ebook | 2014','https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/7/1/6/9200000031296177.jpg','2016-09-18 19:13:59'),(9789029592079,'Een gelukkige poging','Joost Zwagerman','De verhalen in Kroondomein zijn zeer divers. Zo wordt in ''Briefjes'' op onderkoelde wijze verslag gedaan van de aversie van een jonge onderwijzeres tegen haar beroep, terwijl de sfeer van het titel verhaal, waarin een schrijver zijn vrouw overmatig bejubelt, nog het beste als baldadig kan worden omschreven. Dat Zwagermans figuren vaak gefascineerd zijn door het gesproken en geschreven woord blijkt uit verhalen als ''Oudenier haakt in'' en ''Een gelukkige poging''.<br />Kroondomein, Joost Zwagermans eerste verhalenbundel, is ironisch, lyrisch, en wordt gekenmerkt door een even lichte toon die zijn debuut roman De houdgreep zo deed opvallen.','Nederlandstalig | Ebook | 2013','https://s.s-bol.com/imgbase0/imagebase3/large/FC/7/4/1/6/9200000011546147.jpg','2016-09-18 19:16:09'),(9789038826998,'Fantoompijn','Arnon Grunberg','Het leven dient in sc?¿ne te worden gezet. En je leven in sc?¿ne zetten, dat kun je niet aan anderen overlaten. Dat moet je zelf doen. Robert G. Mehlman wil de werkelijkheid produceren zoals anderen boeken, films en schilderijen produceren.\"Het waren deze kleine toneelstukjes die mij gelukkig maakten. Het moment dat ik zelf begon te geloven in de zorgvuldig door mij ge?½nsceneerde werkelijkheid, dat was het moment van de euforie. Het moment dat het verhaal dat je zelf hebt bedacht er met je vandoor...','Nederlandstalig | Paperback | 2002','https://s.s-bol.com/imgbase0/imagebase/large/FC/0/7/2/4/1001004001654270.jpg','2016-09-18 19:16:49'),(9789043017961,'Kritisch denken','T. ter Berg','Kritische denkvaardigheden bezit u niet van nature. Wel kunt u deze trainen. Dit kan met behulp van de unieke methodiek ''Kritische Denken met Rationale'', waar dit boek bij hoort.<br /><br />Rationale is de naam van een softwareprogramma dat u helpt informatie te ordenen, redeneringen te visualiseren en vervolgens een goed en gefundeerd betoog op te bouwen. Ook leert u hoe u redeneringen van anderen kunt herkennen, analyseren en beoordelen. Dit boek gaat in op de basisbegrippen van kritisch denken en geeft i...','Nederlandstalig | Paperback | 2009','https://s.s-bol.com/imgbase0/imagebase/large/FC/4/5/2/1/1001004006461254.jpg','2016-09-16 16:19:35'),(9789044622294,'Het leven van Pi','Yann Martel','Het leven van Pi vertelt het ongelooflijke verhaal van Piscine Patel (Pi), een zestienjarige Indiase jongen wiens vader een dierentuin in India heeft. Wanneer de familie besluit het land te verlaten, wordt de hele dierentuin ingescheept. Maar het schip vergaat en de enige overlevenden op de reddingssloep zijn Pi, een hyena, een zebra met een gebroken been en een tijger van 200 kilo.<br /><br />227 dagen dobbert de sloep over de Grote Oceaan _ het decor voor een buitengewoon fantasierijke roman. ''Dit verhaal doet je in God geloven,'' aldus een personage. Dat gaat misschien wat ver, maar wie Het leven van Pi leest, m??et wel geloven in de onverwoestbare kracht van verhalen.','Nederlandstalig | Paperback | 2012','https://s.s-bol.com/imgbase0/imagebase/large/FC/7/5/7/0/9200000005930757.jpg','2016-09-16 15:08:19'),(9789048803408,'De Joodse messias','Arnon Grunberg','Xavier Radek is een in Basel woonachtige, geassimileerde, niet-joodse jongeman met een missie: hij wil meer weten over het lijden van de joden. Daarom besluit hij de ''vijanden van het geluk'' te gaan troosten. Zijn grootvader was een nazi die het handwerk van de dood nog verstond. Zijn vader is een bekende architect, met een voorliefde voor massagesalons. En dan zijn moeder: een hartstochtelijke vrouw, die een aquarellerende dictator die zeventig jaar geleden actief was consequent aanduidt als ''J...','Nederlandstalig | Ebook | 2009','https://s.s-bol.com/imgbase0/imagebase3/large/FC/5/6/0/5/1001004007115065.jpg','2016-09-18 19:17:10'),(9789052269023,'De Geschiedenis Van Mijn Kaalheid','Marek van der Jagt','Hoe voelt het als jouw leugen de waarheid van de rest van de wereld wordt? In het begin v??elt het onbehaaglijk, maar die onbehaaglijkheid is van korte duur. In De geschiedenis van mijn kaalheid vertelt de Weense filosofiestudent Marek van der Jagt over zijn zoektocht naar de amour fou. Hij heeft gezien wat zijn ouders van hun huwelijk hebben gemaakt en hij wil in plaats daarvan een liefde'' die niet tot geluk leidt, die niets met geluk te maken wil hebben en die toch de moeite waard is''. Na een t...','Nederlandstalig | Hardcover | 2000','https://s.s-bol.com/imgbase0/imagebase/large/FC/1/7/5/9/666869571.jpg','2016-09-18 19:17:36'),(9789089900029,'Zoeken naar Lena','M Myant','Tsjechi?½, 1942. De elfjarige Jan is getuige van een<br />razzia in zijn dorp. Duitse soldaten schieten alle<br />mannen ter plekke dood en nemen vrouwen en<br />kinderen mee. Jan weet ternauwernood te ontkomen<br />en zwerft verloren over het platteland. Dan ontdekt<br />hij dat zijn kleine zusje Lena niet naar een concentratiekamp<br />is gestuurd, zoals zijn moeder en zijn oudste<br />zus, maar dat ze bij een Duitse boerenfamilie is<br />ondergebracht, zodat zij zal opgroeien tot een goed<br />Nazi-meisje. Vanaf dat moment heeft Jan nog maar<br />?®?®n d...','Nederlandstalig | Paperback | 2009','https://s.s-bol.com/imgbase0/imagebase/large/FC/7/1/8/4/1001004006414817.jpg','2016-09-18 19:18:28'),(9789462080089,'Nederlandse architectuur in 250 topstukken','Ole Bouman','Architectuurhistorici van het NAi nemen u, in het rijk ge?»llustreerde boek Nederlandse architectuur in 250 topstukken, mee langs 250 markante gebouwen en ontwerpen. Van Cuypers, Berlage, Kromhout, Van Eesteren, Stam en Wijdeveld, tot Weeber, Coenen en Koolhaas. Een ware ontdekkingsreis door de rijke architectuurgeschiedenis van Nederland.<br /><br />Weinig landen kennen zoÔÇÖn rijke en gevarieerde architectuurgeschiedenis als Nederland. Aan de hand van de collectie van het Nederlands Architectuurinstituut, di...','Nederlandstalig | Hardcover | 2012','https://s.s-bol.com/imgbase0/imagebase/large/FC/4/0/5/9/9200000005399504.jpg','2016-09-18 19:18:41');
/*!40000 ALTER TABLE "book" ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table "member"
--

DROP TABLE IF EXISTS public.member CASCADE;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "member" (
  "MemberID" integer NOT NULL,
  "FirstName" character varying(32) NOT NULL,
  "LastName" character varying(32) NOT NULL,
  "Street" character varying(32) NOT NULL,
  "HouseNumber" character varying(4) NOT NULL,
  "City" character varying(32) NOT NULL,
  "PhoneNumber" character varying(16) NOT NULL,
  "EmailAddress" character varying(32) NOT NULL,
  "Fine" decimal NOT NULL,
  "UpdatedDate" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("MemberID"),
  UNIQUE ("PhoneNumber"),
  UNIQUE ("EmailAddress")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.member
  OWNER TO postgres;

--
-- Dumping data for table "member"
--

-- LOCK TABLES "member" WRITE;
/*!40000 ALTER TABLE "member" DISABLE KEYS */;
INSERT INTO "member" VALUES (1000,'Pascal','van Gastel','Lovensdijkstraat','61','Breda','076-5238754','ppth.vangastel@avans.nl',0,'2016-09-20 18:32:45'),(1001,'Erco','Argante','Hogeschoollaan','1','Breda','076-5231235','e.argante@avans.nl',0,'2016-09-20 18:32:45'),(1002,'Jan','Montizaan','Hogeschoollaan','1','Breda','076-5231236','j.montizaan@avans.nl',0,'2016-09-20 18:32:45'),(1003,'Frans','Spijkerman','Hogeschoollaan','1','Breda','076-5231237','f.spijkerman@avans.nl',0,'2016-09-20 18:32:45'),(1004,'Robin','Schellius','Hogeschoollaan','1','Breda','076-5231238','r.schellius@avans.nl',0,'2016-09-20 18:32:45'),(1005,'Maurice','van Haperen','Hogeschoollaan','1','Breda','076-5231239','mpg.vanhaperen@avans.nl',0,'2016-09-20 18:32:45'),(1006,'Marice','Bastiaensen','Lovensdijkstraat','63','Breda','076-5236790','mmcm.bastiaensen@avans.nl',5,'2016-09-20 18:32:45');
/*!40000 ALTER TABLE "member" ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table "copy"
--
DROP SEQUENCE IF EXISTS CopyID; 
CREATE SEQUENCE CopyID START 1000;		-- zelfde als MySql auto_increment
DROP TABLE IF EXISTS public.copy CASCADE;
CREATE TABLE "copy" (
  "CopyID" integer NOT NULL,
  "LendingPeriod" integer NOT NULL,
  "BookISBN" numeric(20,0) NOT NULL,
  "UpdatedDate" timestamp without time zone NOT NULL DEFAULT now(),
  PRIMARY KEY ("CopyID"),
  -- UNIQUE ("BookISBN"),
  CONSTRAINT "copy_book" FOREIGN KEY ("BookISBN") REFERENCES "book" ("ISBN") ON UPDATE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.copy
  OWNER TO postgres;

--
-- Dumping data for table "copy"
--

-- LOCK TABLES "copy" WRITE;
/*!40000 ALTER TABLE "copy" DISABLE KEYS */;
INSERT INTO "copy" VALUES (1000,21,9781683688815,'2016-09-18 11:56:23'),(1001,21,9781683688815,'2016-09-18 11:56:23'),(1002,21,9781683688815,'2016-09-18 11:56:23'),(1005,21,9781468963830,'2016-09-18 11:56:23'),(1006,21,9781468963830,'2016-09-18 11:56:23'),(1007,21,9781326244415,'2016-09-18 11:56:23'),(1008,21,9781326244415,'2016-09-18 11:56:23'),(1009,21,9781449388942,'2016-09-18 11:56:23'),(1010,21,9781457187971,'2016-09-18 11:56:23'),(1011,21,9781457187971,'2016-09-18 11:56:23'),(1012,21,9789023400226,'2016-09-18 11:56:23'),(1013,21,9789023400226,'2016-09-18 11:56:23'),(1014,21,9789023438786,'2016-09-18 11:56:23'),(1015,21,9789023438786,'2016-09-18 11:56:23'),(1016,21,9789023438786,'2016-09-18 11:56:23'),(1017,21,9789044622294,'2016-09-18 11:56:23'),(1018,21,9789044622294,'2016-09-18 11:56:23'),(1019,21,9789044622294,'2016-09-18 11:56:23'),(1020,21,9781118008188,'2016-09-18 11:56:23'),(1021,21,9781118008188,'2016-09-18 11:56:23'),(1022,21,9781118531648,'2016-09-18 11:56:23'),(1023,21,9781118531648,'2016-09-18 11:56:23'),(1024,21,9781118057322,'2016-09-18 11:56:23'),(1025,21,9780764570780,'2016-09-18 11:56:23'),(1026,21,9780764583063,'2016-09-18 11:56:23'),(1027,21,9780764583063,'2016-09-18 11:56:23'),(1028,21,9789023410843,'2016-09-18 11:56:23'),(1029,21,9789043017961,'2016-09-18 11:56:23'),(1030,5,9789023457299,'2016-09-18 19:13:43'),(1031,5,9789023457299,'2016-09-18 19:13:45'),(1032,5,9789023457299,'2016-09-18 19:13:47'),(1033,5,9789023491033,'2016-09-18 19:13:59'),(1034,5,9789023491033,'2016-09-18 19:14:02'),(1035,5,9789029592079,'2016-09-18 19:16:09'),(1036,5,9789029592079,'2016-09-18 19:16:11'),(1037,5,9789038826998,'2016-09-18 19:16:49'),(1038,5,9789038826998,'2016-09-18 19:16:51'),(1039,5,9789048803408,'2016-09-18 19:17:10'),(1040,5,9789052269023,'2016-09-18 19:17:36'),(1041,5,9789089900029,'2016-09-18 19:18:28'),(1042,5,9789462080089,'2016-09-18 19:18:41'),(1043,5,9789462080089,'2016-09-18 19:18:44'),(1044,5,9789023462972,'2016-09-18 19:30:32'),(1045,5,9789023462972,'2016-09-18 19:30:35');
/*!40000 ALTER TABLE "copy" ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table "loan"
--

DROP TABLE IF EXISTS public.loan CASCADE;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "loan" (
  "LoanID" integer NOT NULL,
  "LoanDate" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "ReturnedDate" timestamp NULL DEFAULT NULL,
  "MemberID" integer NOT NULL,
  "CopyID" integer NOT NULL,
  PRIMARY KEY ("LoanID"),
--  KEY "MemberID" ("MemberID"),
--  KEY "loan_copy" ("CopyID"),
  CONSTRAINT "loan_copy" FOREIGN KEY ("CopyID") REFERENCES public.copy ("CopyID"),
  CONSTRAINT "loan_member" FOREIGN KEY ("MemberID") REFERENCES public.member ("MemberID") ON UPDATE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.loan
  OWNER TO postgres;


--
-- Dumping data for table "loan"
--

-- LOCK TABLES "loan" WRITE;
/*!40000 ALTER TABLE "loan" DISABLE KEYS */;
INSERT INTO "loan" VALUES (1,'2016-09-18 11:56:23','2016-09-17 20:00:00',1000,1001),(2,'2016-09-18 11:56:23','2016-09-17 20:00:00',1005,1005),(3,'2016-09-18 11:56:23','2016-09-17 20:00:00',1002,1011),(4,'2016-09-18 11:56:23','2016-09-17 20:00:00',1004,1012),(5,'2016-09-18 11:56:23',NULL,1001,1014),(6,'2016-09-18 11:56:23',NULL,1005,1015),(7,'2016-09-18 11:56:23','2016-09-17 20:00:00',1000,1016),(8,'2016-09-18 11:56:23',NULL,1003,1017),(9,'2016-09-18 11:56:23','2016-09-17 20:00:00',1001,1018),(10,'2016-09-18 11:56:23',NULL,1000,1021),(11,'2016-09-18 11:56:23','2016-09-17 20:00:00',1001,1026),(12,'2016-09-18 11:56:23','2016-09-17 20:00:00',1004,1000),(13,'2016-09-18 11:56:25','2016-09-17 20:00:00',1004,1000),(14,'2016-09-18 11:56:25','2016-09-17 20:00:00',1000,1001),(15,'2016-09-18 11:56:25','2016-09-17 20:00:00',1000,1001),(16,'2016-09-18 11:56:25','2016-09-17 20:00:00',1006,1002),(17,'2016-09-18 11:56:26',NULL,1006,1002),(21,'2016-09-18 13:20:50',NULL,1005,1013),(22,'2016-09-18 13:27:59',NULL,1001,1006),(23,'2016-09-18 13:34:51',NULL,1006,1027),(24,'2016-09-18 13:35:58','2016-09-17 20:00:00',1001,1001),(25,'2016-09-18 13:37:10','2016-09-17 20:00:00',1000,1001),(26,'2016-09-18 13:37:28','2016-09-17 20:00:00',1002,1007),(27,'2016-09-18 13:40:36','2016-09-17 20:00:00',1003,1008),(28,'2016-09-18 13:43:29','2016-09-17 20:00:00',1004,1022),(29,'2016-09-18 14:02:42','2016-09-17 20:00:00',1002,1029),(30,'2016-09-18 14:22:38',NULL,1003,1009),(31,'2016-09-18 16:20:55',NULL,1001,1000),(32,'2016-09-18 16:21:16','2016-09-17 20:00:00',1000,1000),(33,'2016-09-18 16:22:49','2016-09-17 20:00:00',1004,1028),(34,'2016-09-18 19:10:38','2016-09-22 22:00:00',1004,1016),(35,'2016-09-20 21:58:45',NULL,1001,1020),(36,'2016-09-23 08:56:01',NULL,1004,1024),(37,'2016-09-23 13:09:39',NULL,1002,1044);
/*!40000 ALTER TABLE "loan" ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table "reservation"
--

DROP TABLE IF EXISTS public.reservation CASCADE;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "reservation" (
  "ReservationID" integer NOT NULL,
  "ReservationDate" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "MemberID" integer NOT NULL,
  "CopyID" integer NOT NULL,
  "UpdatedDate" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("ReservationDate","MemberID","CopyID"),
--  KEY "ReservationID" ("ReservationID"),
--  KEY "MemberID" ("MemberID"),
--  KEY "CopyID" ("CopyID"),
  CONSTRAINT "reservation_book" FOREIGN KEY ("CopyID") REFERENCES "copy" ("CopyID") ON UPDATE CASCADE,
  CONSTRAINT "reservation_member" FOREIGN KEY ("MemberID") REFERENCES "member" ("MemberID") ON DELETE CASCADE ON UPDATE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.reservation
  OWNER TO postgres;


--
-- Dumping data for table "reservation"
--

-- LOCK TABLES "reservation" WRITE;
/*!40000 ALTER TABLE "reservation" DISABLE KEYS */;
/*!40000 ALTER TABLE "reservation" ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Temporary table structure for view "view_all_copies"
--

DROP TABLE IF EXISTS "view_all_copies";
/*!50001 DROP VIEW IF EXISTS "view_all_copies"*/;
-- SET @saved_cs_client     = @@character_set_client;
-- SET character_set_client = utf8;
/*!50001 CREATE TABLE "view_all_copies" */

CREATE OR REPLACE VIEW view_all_copies AS 
SELECT 
	copy.CopyID,
	copy.LendingPeriod,
	book.ISBN,
	book.Title,
	book.Author,
	book.Edition
FROM public.copy
LEFT JOIN public.book ON book.ISBN = copy.BookISBN;

-- SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view "view_all_loans"
--

DROP TABLE IF EXISTS "view_all_loans";
/*!50001 DROP VIEW IF EXISTS "view_all_loans"*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE "view_all_loans" (
  "LoanID" tinyint NOT NULL,
  "LoanDate" tinyint NOT NULL,
  "ReturnedDate" tinyint NOT NULL,
  "ISBN" tinyint NOT NULL,
  "Title" tinyint NOT NULL,
  "Author" tinyint NOT NULL,
  "Edition" tinyint NOT NULL,
  "CopyID" tinyint NOT NULL,
  "LendingPeriod" tinyint NOT NULL,
  "MemberID" tinyint NOT NULL,
  "FirstName" tinyint NOT NULL,
  "LastName" tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view "view_all_reservations"
--

DROP TABLE IF EXISTS "view_all_reservations";
/*!50001 DROP VIEW IF EXISTS "view_all_reservations"*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE "view_all_reservations" (
  "ReservationID" tinyint NOT NULL,
  "ReservationDate" tinyint NOT NULL,
  "CopyID" tinyint NOT NULL,
  "LendingPeriod" tinyint NOT NULL,
  "ISBN" tinyint NOT NULL,
  "Title" tinyint NOT NULL,
  "Author" tinyint NOT NULL,
  "Edition" tinyint NOT NULL,
  "MemberID" tinyint NOT NULL,
  "FirstName" tinyint NOT NULL,
  "LastName" tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view "view_booklending"
--

DROP TABLE IF EXISTS "view_booklending";
/*!50001 DROP VIEW IF EXISTS "view_booklending"*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE "view_booklending" (
  "ISBN" tinyint NOT NULL,
  "CopyID" tinyint NOT NULL,
  "LoanID" tinyint NOT NULL,
  "LoanDate" tinyint NOT NULL,
  "ReturnedDate" tinyint NOT NULL,
  "LendingPeriod" tinyint NOT NULL,
  "MemberID" tinyint NOT NULL,
  "FirstName" tinyint NOT NULL,
  "LastName" tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view "view_lending"
--

DROP TABLE IF EXISTS "view_lending";
/*!50001 DROP VIEW IF EXISTS "view_lending"*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE "view_lending" (
  "LoanID" tinyint NOT NULL,
  "CopyID" tinyint NOT NULL,
  "ISBN" tinyint NOT NULL,
  "LoanDate" tinyint NOT NULL,
  "ReturnedDate" tinyint NOT NULL,
  "LendingPeriod" tinyint NOT NULL,
  "MemberID" tinyint NOT NULL,
  "FirstName" tinyint NOT NULL,
  "LastName" tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view "view_all_copies"
--

/*!50001 DROP TABLE IF EXISTS "view_all_copies"*/;
/*!50001 DROP VIEW IF EXISTS "view_all_copies"*/;
/*!50001 CREATE VIEW "view_all_copies" AS select "copy"."CopyID" AS "CopyID","copy"."LendingPeriod" AS "LendingPeriod","book"."ISBN" AS "ISBN","book"."Title" AS "Title","book"."Author" AS "Author","book"."Edition" AS "Edition" from ("copy" left join "book" on(("copy"."BookISBN" = "book"."ISBN"))) */;

--
-- Final view structure for view "view_all_loans"
--

/*!50001 DROP TABLE IF EXISTS "view_all_loans"*/;
/*!50001 DROP VIEW IF EXISTS "view_all_loans"*/;
/*!50001 CREATE VIEW "view_all_loans" AS select "loan"."LoanID" AS "LoanID","loan"."LoanDate" AS "LoanDate","loan"."ReturnedDate" AS "ReturnedDate","book"."ISBN" AS "ISBN","book"."Title" AS "Title","book"."Author" AS "Author","book"."Edition" AS "Edition","copy"."CopyID" AS "CopyID","copy"."LendingPeriod" AS "LendingPeriod","member"."MemberID" AS "MemberID","member"."FirstName" AS "FirstName","member"."LastName" AS "LastName" from ((("loan" left join "copy" on(("loan"."CopyID" = "copy"."CopyID"))) left join "book" on(("copy"."BookISBN" = "book"."ISBN"))) left join "member" on(("loan"."MemberID" = "member"."MemberID"))) */;

--
-- Final view structure for view "view_all_reservations"
--

/*!50001 DROP TABLE IF EXISTS "view_all_reservations"*/;
/*!50001 DROP VIEW IF EXISTS "view_all_reservations"*/;
/*!50001 CREATE VIEW "view_all_reservations" AS select "reservation"."ReservationID" AS "ReservationID","reservation"."ReservationDate" AS "ReservationDate","copy"."CopyID" AS "CopyID","copy"."LendingPeriod" AS "LendingPeriod","book"."ISBN" AS "ISBN","book"."Title" AS "Title","book"."Author" AS "Author","book"."Edition" AS "Edition","member"."MemberID" AS "MemberID","member"."FirstName" AS "FirstName","member"."LastName" AS "LastName" from ((("reservation" left join "copy" on(("reservation"."CopyID" = "copy"."CopyID"))) left join "book" on(("copy"."BookISBN" = "book"."ISBN"))) left join "member" on(("reservation"."MemberID" = "member"."MemberID"))) */;

--
-- Final view structure for view "view_booklending"
--

/*!50001 DROP TABLE IF EXISTS "view_booklending"*/;
/*!50001 DROP VIEW IF EXISTS "view_booklending"*/;
/*!50001 CREATE VIEW "view_booklending" AS select "book"."ISBN" AS "ISBN","copy"."CopyID" AS "CopyID","loan"."LoanID" AS "LoanID","loan"."LoanDate" AS "LoanDate","loan"."ReturnedDate" AS "ReturnedDate","copy"."LendingPeriod" AS "LendingPeriod","member"."MemberID" AS "MemberID","member"."FirstName" AS "FirstName","member"."LastName" AS "LastName" from ((("book" left join "copy" on(("copy"."BookISBN" = "book"."ISBN"))) left join "loan" on(("copy"."CopyID" = "loan"."CopyID"))) left join "member" on(("loan"."MemberID" = "member"."MemberID"))) order by "copy"."CopyID","loan"."LoanID" */;

--
-- Final view structure for view "view_lending"
--

/*!50001 DROP TABLE IF EXISTS "view_lending"*/;
/*!50001 DROP VIEW IF EXISTS "view_lending"*/;
/*!50001 CREATE VIEW "view_lending" AS select "loan"."LoanID" AS "LoanID","copy"."CopyID" AS "CopyID","book"."ISBN" AS "ISBN","loan"."LoanDate" AS "LoanDate","loan"."ReturnedDate" AS "ReturnedDate","copy"."LendingPeriod" AS "LendingPeriod","member"."MemberID" AS "MemberID","member"."FirstName" AS "FirstName","member"."LastName" AS "LastName" from ((("copy" left join "loan" on(("loan"."CopyID" = "copy"."CopyID"))) left join "book" on(("copy"."BookISBN" = "book"."ISBN"))) left join "member" on(("loan"."MemberID" = "member"."MemberID"))) */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-25  0:21:52
