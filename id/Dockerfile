FROM openjdk:21-jdk

COPY target/rachvik-id.jar .

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/rachvik-id.jar"]