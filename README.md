# Spring Cloud AWS Demo
This reference application acts as a showcase for the features provided by Spring Cloud AWS.

To check out the project and build it from source, do the following:

    git clone https://github.com/doughuang168/spring-cloud-aws
    cd spring-cloud-aws
    gradlew build (Linux) or .\gradlew.bat build(Windows)

## Build docker container from Dockerfile ##
- Edit Dockerfile, set environment variable with your AWS setting 
 
- docker build -t spring-boot-aws .  


## Invoke the docker instance


- docker run -d -p 8081:8081  -t spring-boot-aws

 
