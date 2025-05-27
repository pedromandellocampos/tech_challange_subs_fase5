# -------- BUILD STAGE --------
FROM eclipse-temurin:24-jdk-alpine AS builder

WORKDIR /app
COPY . /app

# Executa AOT e build do jar com classes otimizadas
RUN ./mvnw clean package -Daot

# -------- RUNTIME STAGE --------
FROM eclipse-temurin:24-jre-alpine

ENV JAVA_OPTS="\
  -XX:MaxRAMPercentage=80.0 \
  -Xshare:on \
  -XX:SharedArchiveFile=/app/app-cds.jsa"

WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

# Gera CDS com base no JAR AOT
RUN java -Xshare:off -XX:ArchiveClassesAtExit=/app/app-cds.jsa -jar /app/app.jar || true

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
