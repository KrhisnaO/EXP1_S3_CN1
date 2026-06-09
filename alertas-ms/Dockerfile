FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el jar generado
COPY target/alertas-ms-0.0.1-SNAPSHOT.jar app.jar

# Copiar el wallet (carpeta completa)
COPY src/main/resources/Wallet_BBDD2026 /app/wallet

# Exponer el puerto del microservicio
EXPOSE 8081

# Variable de entorno para apuntar al wallet dentro del contenedor
ENV WALLET_PATH=/app/wallet

ENTRYPOINT ["java", "-jar", "app.jar"]
