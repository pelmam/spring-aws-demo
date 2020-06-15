package pelit.hi.controllers;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pelit.hi.GreetUtil.redirectingRestTemplate;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pelit.hi.logic.GenericMsg;
/**
 * Have a bit of fun, seeing whether your application can 
 * access various resources on the production environment.
 * For example:
 * -	Can it read/write files? On which directories?
 * -	Can it open an http connection to Google?
 * -	Can it report its own hostname (interesting in a load-balanced environment)
 *
 */
@RestController
@RequestMapping(value="/resource", produces = APPLICATION_JSON_VALUE )

public class ResourceTestController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Logger logger;
	
	@GetMapping("/disk-write")
	public GenericMsg diskWrite(@RequestParam String file, @RequestParam String text) 
	throws Exception{
		Path path=Paths.get(file);
		Files.write(path, text.getBytes());
		return GenericMsg.builder()
				.message("File write success!")
				.details(path.toAbsolutePath())
				.build();
		// Note: in real-life projects, make that 'POST' rather than 'GET', because it creates data
	}
	
	@GetMapping("/disk-read")
	public GenericMsg diskRead(@RequestParam String file) throws Exception{
		Path path=Paths.get(file);
		return GenericMsg.builder()
				.message(format("Successfully read %s", path.toAbsolutePath())) 
				.details(Files.readAllLines(path))
				.build();
	}

	@GetMapping("/http-client")
	public GenericMsg httpClient(String url) throws Exception{
		String resp=redirectingRestTemplate()
				.getForEntity(url, String.class)
				.getBody();
		return GenericMsg.builder()
				.message("Response from " + url)
				.details(resp.substring(0, 200))
				.build();
	}
	
	@GetMapping("/ping")
	public GenericMsg ping(String host) throws Exception{
		InetAddress addrObj=InetAddress.getByName(host);
		int timeout=5000;
		return GenericMsg.builder()
				.message("Ping to "+host)
				.details(format("Reachable:%s", addrObj.isReachable(timeout)))
				.build();
	}
		
	@GetMapping("/env")
	public GenericMsg getEnv1(@RequestParam String name) throws Exception{
		return GenericMsg.builder()
				.message("Env variable")
				.details(format("%s=%s", name, env.getProperty(name)))
				.build();
	}
	
	@GetMapping("/my-host")
	public GenericMsg getHost() throws Exception{
		return GenericMsg.builder()
				.message("My host:"+thisHost())
				.build();
	}

	@GetMapping("/cpu-load")
	public GenericMsg cpuLoad(@RequestParam long loadTime, @RequestParam int threads)
	throws Exception{
		loadCpu(loadTime, threads);
		return GenericMsg.builder()
				.message(format("Loaded cpu for time:%s threads:%s", loadTime, threads))
				.build();
	}

	private String thisHost() throws Exception {
		return InetAddress.getLocalHost().toString();
	}
	
	private void loadCpu(long loadTime, int threads) throws Exception{
		ExecutorService exec=newFixedThreadPool(threads);
		List<Callable<Integer>> callables=Stream.iterate(0, i->i+1)
			.limit(threads)
			.map(i->new Callable<Integer>() {
				public Integer call() {
					loadCpu1(loadTime);
					return 1;
				}
			})
			.collect(toList());
		List<Future<Integer>> futures=exec.invokeAll(callables);
		joinAll(futures);
		exec.shutdownNow();
	}

	private  void loadCpu1(long loadTime) {
		logger.debug("Loading cpu in thread {}", Thread.currentThread());
		long start=currentTimeMillis();
		while(currentTimeMillis()-start<loadTime) {
			Math.random();
		}
	}
	private <T> void joinAll(List<Future<T>> futures){
		for(Future<T> future:futures) {
			try {
				future.get();
			}
			catch(Exception ex) {
				logger.error(ex);
			}
		}
	}
}
