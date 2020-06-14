package pelit.hi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

/**
 * Our main "entry point" and configuration. 
 * Note we shall also load the neighbouring class GreetWebConfig,
 * 
 */
@SpringBootApplication
@PropertySource("classpath:greet.properties")
public class GreetAppConfig {

	@Bean
	@Scope("prototype")
	public Logger getLogger(InjectionPoint injected) {
		Class<?> class1 = injected.getMember().getDeclaringClass();
		return LogManager.getLogger(class1);
	}

	public static void main(String[] args) {
		SpringApplication.run(GreetAppConfig.class, args);
	}
}
