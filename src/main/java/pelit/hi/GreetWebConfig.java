package pelit.hi;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * Lower-level configuration for debugging events
 * such as startup, incoming requests, etc
 */

@Configuration
public class GreetWebConfig {
	
	@Autowired
	private Logger logger;
	
	@Bean
	public Object argsPrinter() {
		return new Object(){
			@Autowired	
			ApplicationArguments args;
			@Value("http://YOUR_HOST:${server.port}${server.servlet.context-path}")
			String url;
			@PostConstruct public void printArgs() {
				logger.info("Running with args:{}", Arrays.toString(args.getSourceArgs()));
				logger.info("Please connect to:{}", url);
			}
		};
	}
	@Bean 
	public ApplicationListener<ContextRefreshedEvent> initListner() {
		return new ApplicationListener<ContextRefreshedEvent>() {
			@Override 
			public void onApplicationEvent(ContextRefreshedEvent event) {
				logger.info("Context Refreshed!");
			}
		};
	}
	
	@Bean
	public WebMvcConfigurer adviceConfigurer() {
		return new WebMvcConfigurer() {
			 @Override 
			 public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new LoggingInterceptor());
			 }
		};
	}
	private class LoggingInterceptor extends HandlerInterceptorAdapter{
		@Override
		public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
		throws Exception {
			logger.info("Incoming request:{} {} params:{}", 
					req.getMethod(), 
					req.getRequestURL(),
					req.getParameterMap());
			return true;
		}
		@Override
		public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler,
		Exception ex) throws Exception {
			logger.info("Outgoing response:{} {}",
						resp.getStatus(),
						resp.getHeader(CONTENT_TYPE));
		}
		
	}
	
	/** Also try filter if you like:*/
	/*
	@Bean
	public GenericFilterBean logFilter() {
		return new LogFilter();
	}
	private class LogFilter extends GenericFilterBean{
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse resp=(HttpServletResponse)response;
			GreetWebConfig.this.logger.info("Handling request:{} {} params:{}", req.getMethod(), req.getContextPath(), request.getParameterMap());
	        chain.doFilter(request, response);
			GreetWebConfig.this.logger.info("Returning response:{} {}", resp.getStatus(), resp.getHeader(CONTENT_TYPE));

	    }
	}	
	*/
}
