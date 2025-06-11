FROM maven:3.9.9-eclipse-temurin-17
COPY . .
RUN mvn clean package
ENTRYPOINT ["java", "-jar", "target/movies-management-0.0.1-SNAPSHOT.jar"]