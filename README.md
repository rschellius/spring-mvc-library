Avans Spring Library Worked Example
====================================

[![Build Status](https://travis-ci.org/avansinformatica/spring-mvc-library.svg?branch=master)](https://travis-ci.org/avansinformatica/spring-mvc-library)

About
----------------
This is a worked example of a library case that is used at [Avans Hogeschool](http://www.avans.nl) in Breda, Netherlands. Some of the content in the app is in Dutch.

It uses the following technologies:

- Spring Boot MVC with Maven
- Thymeleaf templating engine with Java 8 Time
- MySql database using plain old SQL queries, so without JPA/Hibernate.
- WebJars for css and js includes
- [maven-git-commit-id-plugin](https://github.com/ktoso/maven-git-commit-id-plugin) to display Git commit info in our Spring application footer
- Swagger to describe the (minimal) REST API 
- MockMVC tests
- [Travis CI](http://https://travis-ci.org) for automatic test CI

Demo
--------------------
You can see a deployed version running at [Heroku](https://spring-mvc-library.herokuapp.com/). See [the textfile](https://github.com/rschellius/spring-mvc-library/blob/master/Heroku_cloud.md) and the Procfile for more info. 

![screenprint of the application](https://github.com/rschellius/spring-mvc-library/blob/master/src/main/resources/static/img/screenprint.png)

Tests
--------------------

For more information on the tests, see [this page](https://github.com/rschellius/spring-mvc-library/tree/master/src/test/java/nl/avans/ivh5/springmvc)

Prerequisites
-------------

- JDK 8 and JAVA_HOME environment variable set
- Maven installed and configured
- MySql database with library.sql script imported and running on local host (when running the app locally)
- Importing the .sql script should provide a user 'spring' with pwd 'test' and access to the 'library' database.

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

Production Environment
--------------------

To test your application running in a production environment, type

> java -Dspring.profiles.active=production -jar target\spring-boot-thymeleaf-1.0.jar

Referenced articles:
--------------------

- [Java 8 Date & Time with Thymeleaf](http://blog.codeleak.pl/2015/11/how-to-java-8-date-time-with-thymeleaf.html)
- [Spring Boot and Thymeleaf with Maven](http://blog.codeleak.pl/2014/04/how-to-spring-boot-and-thymeleaf-with-maven.html)
- [Unit Testing of Spring MVC Controllers](https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/) 
- [Spring Boot Integration Testing with Selenium](http://blog.codeleak.pl/2015/03/spring-boot-integration-testing-with.html)
