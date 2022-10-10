FROM openjdk:18
EXPOSE 8080
RUN mkdir -p /upload/market/
RUN mkdir -p /upload/product
ADD /target/MyShop-0.0.1-SNAPSHOT.jar MyShop.jar

ENTRYPOINT ["java","-jar","MyShop.jar"]