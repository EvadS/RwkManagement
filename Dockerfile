# Start with a base image containing Java runtime
####  A minimal docker image with command to run the app
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="skiba.eugeniy@gmail.com"

# Add a volume pointing to /tmp
#application writes log files to a location /var/log/app.log
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8000

# The application's jar file
ARG JAR_FILE=target/rwr-app.jar

# Add the application's jar to the container
ADD ${JAR_FILE} rwr-app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rwr-app.jar"]

