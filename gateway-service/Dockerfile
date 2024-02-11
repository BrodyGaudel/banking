FROM openjdk21:alpine

# Definition of the maintainer and author of the image
LABEL authors="Brody Gaudel MOUNANGA BOUKA"
LABEL maintainer="Brody Gaudel https://github.com/BrodyGaudel"

# Setting environment variables
ENV ALLOWED_ORIGINS=*
ENV ALLOWED_HEADERS=CONTENT_TYPE,AUTHORIZATION
ENV EXPOSED_HEADERS=CONTENT_TYPE,AUTHORIZATION
ENV MAX_AGE=3600
ENV ALLOWED_METHODS=GET,POST,PUT,DELETE
ENV ALLOW_CREDENTIALS=true
ENV EUREKA_URI=http://localhost:8761/eureka/

# Copying your jar application into the container
COPY target/gateway-service-0.0.1.jar gateway-service.jar

# Command to launch the application when running the container
ENTRYPOINT ["java","-jar","gateway-service.jar"]
