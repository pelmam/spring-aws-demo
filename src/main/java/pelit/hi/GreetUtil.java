package pelit.hi;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class GreetUtil {

	/**
	 * RestTeamplate that follows redirects
	 */
	public static RestTemplate redirectingRestTemplate() {
		HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory();
		factory.setHttpClient(HttpClientBuilder.create()
				.setRedirectStrategy(new LaxRedirectStrategy())
				.build());
		RestTemplate template=new RestTemplate(factory);
		return template;
	}
	
	public static HttpHeaders buildHeaders(String...keyVal) {
		HttpHeaders headers=new HttpHeaders();
		for(int i=0; i<keyVal.length; i+=2)
			headers.add(keyVal[i], keyVal[1]);
		return headers;
	}


}
