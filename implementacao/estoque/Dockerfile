FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/estoque.jar estoque.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/estoque.jar"]