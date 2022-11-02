FROM openjdk:17-jdk-slim as build
COPY target/arch_ref-0.0.1-SNAPSHOT.jar arch_ref-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/arch_ref-0.0.1-SNAPSHOT.jar"]
