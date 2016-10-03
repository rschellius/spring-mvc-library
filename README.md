Avans Spring Library Worked Example
====================================

Forked from https://github.com/kolorobot/spring-boot-thymeleaf.

About
-------------
This is a worked example of a library case that is used at [Avans Hogeschool](http://www.avans.nl) in Breda, Netherlands. Some of the content in the app is in Dutch.

It uses the following technologies:

- Spring Boot MVC with Maven and Spring IO
- Thymeleaf templating engine with Java 8 Time (Java8TimeDialect)
- MySql database
- WebJars for css and js includes
- MockMVC tests

Demo
--------------------
You can see a deployed version running at [Heroku](https://spring-mvc-library.herokuapp.com/).

!(https://github.com/rschellius/spring-mvc-library/blob/master/src/main/resources/static/img/screenprint.png)

Tests
--------------------

For more information on the tests, see [this page](https://github.com/rschellius/spring-mvc-library/tree/master/src/test/java/nl/avans/ivh5/springmvc)

Prerequisites
-------------

- JDK 8 and JAVA_HOME environment variable set
- Maven installed
- MySql database with library.sql script imported and running on local host
- Importing the .sql script should provide a user 'spring' with pwd 'test' and access to 'library' database.

Building the project
--------------------

Clone the repository: open a command prompt and type:

> git clone [url to this repo]

Navigate to the newly created folder:

> cd spring-boot-thymeleaf

Run the project with:

> mvn clean compile spring-boot:run

To package the project into a fat jar run:

> mvn clean package

To test the project run:

> mvn clean compile test

Referenced articles:
--------------------

- [Java 8 Date & Time with Thymeleaf](http://blog.codeleak.pl/2015/11/how-to-java-8-date-time-with-thymeleaf.html)
- [Spring Boot and Thymeleaf with Maven](http://blog.codeleak.pl/2014/04/how-to-spring-boot-and-thymeleaf-with-maven.html)
- [Unit Testing of Spring MVC Controllers](https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/) 
- [Spring Boot Integration Testing with Selenium](http://blog.codeleak.pl/2015/03/spring-boot-integration-testing-with.html)
