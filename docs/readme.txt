connect using: http://localhost:8080/myapp/salary
Where the prefix "myapp" is defined n application.properties

-	Startup:
	
	A.	@SpringBootApplication // @Configuration, enableAutoConfiguration, component scan
		public class MyRootCfig{
			... @Bean, @Resource, other sibling classes with @Configuration, @Component, @Service ...
			... top can have @PropertySource("classpath:myprops.properties")
		}
	
	B.	Then main: StringBootApplication.run(MyRootCfg.class)
	
	C.	application.properties
		application-test.properties

	D.	@RestController, @GetMapping, @AutoWired ...
		
	E.	browser: http://localhost:8080/myapp/ctrl...

	F.	static pages /resources/static/
	
-	test:

-	Listener:
	@Bean that implements ApplicationListener<ContextRefreshedEvent> or ContextClosedEvent