# Spring Boot Demo: AWS deployment!

## Overview
This is a simple Spring Boot Demo.
Its main purpose is to to demonstrate deployment to AWS!

The app's endpoints are pretty simple, but some of them use network or file resources, which
may fail depending on setup.


|Spring Boot App|AWS Deployment|
|---------------|--------------|
|<img alt="app" raw="true" src="docs/doc-img/tester_app_large.png" width="350"/>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;nbsp;<img alt="diagram" raw="true" src="docs/doc-img/diagram-lb.png" width="450"/>|



## Testing the Spring Boot app locally
First, try the app locally:
* Pull the project code
* View the application.properties - feel free to change ports if needed:
```
spring-aws-demo/src/main/resources/application.properties
spring-aws-demo/src/main/resources/application-test.properties
 
* Open the command prompt in the project root (/spring-aws-demo/)
* Packaging: 
``` 
> mvnw clean package spring-boot:repackage
```
* Running: 
```
Using maven:
> mvnw spring-boot:run
Proper execution through the jar:
>java -jar target/spring-aws-demo-0.0.1.jar
You can also control some arguments, e.g.:
>java -jar target/spring-aws-demo-0.0.1.jar --server.port=8081 --GREET_ENDING="Have a good one!"

```
* Point browser at http://localhost:8080/greet

* Running from jar: 


