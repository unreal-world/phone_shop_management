# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage (Tomcat)
FROM tomcat:10.1-jdk17
# Tắt cổng shutdown 8005 để Render không định tuyến nhầm vào cổng này
RUN sed -i 's/<Server port="8005"/<Server port="-1"/' /usr/local/tomcat/conf/server.xml
# Dọn dẹp các ứng dụng mẫu của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/phonestore.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
ENV PORT=8080
CMD ["catalina.sh", "run"]
