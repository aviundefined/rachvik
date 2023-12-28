FROM openjdk:17-jdk

COPY target/rachvik-master.jar .

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/rachvik-master.jar"]