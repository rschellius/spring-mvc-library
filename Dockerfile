#
# This is the image for RaspberryPi on ARM architecture.
# See https://hub.docker.com/r/hypriot
#
# FROM hypriot/rpi-java

#
# This is the image for plain X86 machines.
#
FROM java:8
VOLUME /tmp

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

# Warning: JAVA_HOME environment variable is not set.
# ENV JAVA_HOME /usr/java/default
# ENV PATH $PATH:$JAVA_HOME/bin

# Prepare by downloading dependencies
ADD pom.xml *.iml /code/pom.xml
# RUN ["mvn", "dependency:resolve"]
# RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /code/src
# RUN ["mvn", "clean"]
RUN ["mvn", "compile"]
RUN ["mvn", "package"]

EXPOSE 8080
CMD ["java", "-jar", "target/spring-boot-thymeleaf-1.0.jar"]
# done