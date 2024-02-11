FROM openjdk21:alpine

# Definition of the maintainer and author of the image
LABEL authors="Brody Gaudel MOUNANGA BOUKA"
LABEL maintainer="Brody Gaudel https://github.com/BrodyGaudel"

# Setting environment variables
ENV EUREKA_URI=http://localhost:8761/eureka/
ENV MYSQL_USER=root
ENV MYSQL_PWD=brody2250
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=bank_customer_db

# Copying your jar application into the container
COPY target/customer-service-0.0.1.jar customer-service.jar

# Command to launch the application when running the container
ENTRYPOINT ["java","-jar","customer-service.jar"]
