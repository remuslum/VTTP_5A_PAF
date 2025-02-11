FROM eclipse-temurin:23-noble AS builder

WORKDIR /src

# copy files
COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src

# make mvnw executable
RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true

FROM eclipse-temurin:23-jre-noble

WORKDIR /app

COPY --from=builder /src/target/assessment-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:mysql://fred:fred@host.docker.internal:3306/bedandbreakfast
ENV SPRING_DATA_MONGODB_URI=mongodb+srv://fred:fred@cluster0.fvbco.mongodb.net/bedandbreakfast?retryWrites=true&w=majority&appName=Cluster0

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar