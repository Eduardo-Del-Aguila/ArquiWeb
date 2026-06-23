FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN MAVEN_OPTS="-Xmx512m" ./mvnw clean package -DskipTests
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -jar target/*.jar"]