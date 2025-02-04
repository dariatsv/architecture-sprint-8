FROM gradle:jdk17 AS build
WORKDIR /api
COPY . /api
RUN gradle clean build -x test

FROM amazoncorretto:17-al2023-jdk

COPY --from=build /api/build/libs/ /api/

ENTRYPOINT ["java","-jar","/api/api-0.0.1-SNAPSHOT.jar"]