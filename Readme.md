# Spring Boot Demo: AWS deployment!
## By Pelit Mamani

## Overview
This is a simple Spring Boot Demo.
Its main purpose is to to demonstrate deployment to AWS!

The app's endpoints are pretty simple, but some of them use network or file resources, which
might fail depending on setup.

<br/>

|Spring Boot App|AWS Deployment|
|---------------|--------------|
|<img alt="app" raw="true" src="docs/doc-img/boot-app.png" width="350"/>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img alt="diagram" raw="true" src="docs/doc-img/diagram-lb.png" width="450"/>|

<br/>

## Testing the Spring Boot app locally
First try the app locally:
Pull the code, and view the application.properties - there you can change ports if needed:
```
spring-aws-demo/src/main/resources/application.properties
spring-aws-demo/src/main/resources/application-test.properties
```
Open the command prompt in the project root (/spring-aws-demo/)
And package the app into a jar:
``` 
> mvnw clean package spring-boot:repackage
```
Running: 
```
Using maven:
> mvnw spring-boot:run

Or jar execution:
> java -jar target/spring-aws-demo-0.0.1.jar

You can also control some arguments, e.g.:
> java -jar target/spring-aws-demo-0.0.1.jar --server.port=8081 --GREET_ENDING="Have a good one!"
```
Now point the browser at the app (assuming you chose port 8080):
```
http://localhost:8080/greet-app
```
<img alt="app" raw="true" src="docs/doc-img/boot-app.png" width="350"/>
 
Feel free to press the items - 'Greet' is the simplest and rarely fails.
Other actions would access resources such as files or network, and might fail if access is restricted (that's the whole point! E.g. if we later deploy into an AWS network that limits outbound http connections, than the corresponding action will fail).
<br/>
<br/>

## AWS Deployments
Our purpose is to explore various deployment options, such as
*	Single instance, versus using a load balancer (ALB), versus auto scaling
*	Manual deployment
*	CloudFormation (under construction - coming soon)
*	Docker deployment (under construction - coming soon)

Prerequisites & Disclaimer:
* If you don't have an AWS account yet, you might consider [AWS trial free tier](https://aws.amazon.com/free/).
* However, if you are new to AWS, please be **very careful with billing**: consult the AWS documentation & support, double-check if items are eligible for the free tier (even items mentioned in this demo - there could be errors, oversights, or info might become outdated). 
* Please explore billing best practices such as: setting up **billing alerts**, terminating unused resources, **not leaving unnecessary instances running overnight** etc.
* Please note this demo makes **security compromises for the sake of simplicity.** This is not production-ready.

<br/>

## AWS Deployment - manual, single ec2 instance
Let's start with the simplest option: manual deployment through the AWS console,
using the simplest single-instance configuration: 

<br/><br/>
<img alt="app" raw="true" src="docs/doc-img/diagram-simple.png" width="350"/>
<br/>

This will require:
1. An **ec2 instance**, representing the virtual server where our Spring Boot app will run. It will be provided with most items you'd expect on a computer: CPU, disk space (ESB), network, operating system etc. We'll set it up last - after our network is ready.
1. A **vpc** (virtual private cloud): representing a virtual network that will be provided for us, spanning a range of IP addresses. It can contain several subnets in several availability zones (though it will be confined to one region)
1. A **subnet**: representing the internal network where our ec2 instance would run. A subnet uses a sub-range of IP addresses from the vpc. Each subnet resides in one physical location (availability zone) - so an architecture based on a single subnet is not the best protection against natural disasters.
1. An **internet gateway**: geteway to the outside world - the internet. Once we connect this gateway to our subnet (with some routing & security configurations), our Spring Boot app will be able to serve browsers all over the world! 
1. **Routing & security rules** - in AWS entities such as: **Route Table, Network ACL, Security Group**.
<br/>
Effects on our app:<br/><br/>
<img raw="true" src="src/main/resources/static/img/greet.png" width="20"/>Greet: Our Spring Boot app will only respond if the subnet is properly configured for external http communication! Feel free to see how it stops responding if you misconfigure the gateway, route table, NACL or security group. <br/><br/>
<img raw="true" src="src/main/resources/static/img/disk.ico" width="20"/>Disk I/O: Our ec2 is provided with storage, so this should work (unless you generate lots of huge files). But how long will your file be available? This depends on the storage attachment policy - if the storage is "attached" to the ec2, and the ec2 is terminated, you'd lose your data. That's a big 'Gotcha' that is better discovered in this test, and not in production...<br/><br/>
<img raw="true" src="src/main/resources/static/img/env.ico" width="20"/>Environment variables: Feel free to set them up, and watch the effect. <br/><br/>
<img raw="true" src="src/main/resources/static/img/cpu.ico" width="20"/>No Scaling Yet: If we overload the CPU, well, tough luck - all other requests will slow down. This can be solved by horizontal scaling - namely more ec2 instances. Better still, if we ask AWS to automatically add/remove ec2's depending on the load - that's elasticity! <br/><br/>

## Under construction
Coming up soon
*	Setting it up, with both the AWS console (video) and with CloudFormation 
*	Auto scaling. We'll set up auto scaling, overload the cpu, and see if new instances will be automatically launched
*	Load balancer (ALB)
*	(Just please remember to stop instances and other resources!!)

