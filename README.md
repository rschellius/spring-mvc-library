Library Worked Example using Spring Boot MVC and Thymeleaf
====================================

Forked from https://github.com/kolorobot/spring-boot-thymeleaf.

- Spring Boot MVC with Maven and Spring IO
- Thymeleaf templating engine with Java 8 Time (Java8TimeDialect)
- WebJars
- Selenium configuration included

Prerequisites
-------------

- JDK 8 and JAVA_HOME environment variable set
- Maven installed
- MySql database with library.sql script imported and running on local host
- Importing the .sql script should provide a user 'spring' with pwd 'test' and access to 'library' database.

Building the project
--------------------

Clone the repository:

> git clone [url to this repo]

Navigate to the newly created folder:

> cd spring-boot-thymeleaf

Run the project with:

> mvn clean compile spring-boot:run

To package the project into a fat jar run:

> mvn clean package


Referenced articles:
--------------------

- [Java 8 Date & Time with Thymeleaf](http://blog.codeleak.pl/2015/11/how-to-java-8-date-time-with-thymeleaf.html)
- [Spring Boot and Thymeleaf with Maven](http://blog.codeleak.pl/2014/04/how-to-spring-boot-and-thymeleaf-with-maven.html)
- [Spring Boot Integration Testing with Selenium](http://blog.codeleak.pl/2015/03/spring-boot-integration-testing-with.html)