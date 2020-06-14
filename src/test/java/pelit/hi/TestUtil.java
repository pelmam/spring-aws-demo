package pelit.hi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestUtil {
	/**
	 * Usage example
	 * HttpEntity<MultiValueMap<String, Object>> req = new HttpEntityBuilder()
	 * 			.withHeader(HttpHeaders.CONTENT_TYPE,MediaType.MULTIPART_FORM_DATA)
	 * 			.withBodyParam("queryFile", new ClassPathResource("package/hi.jpg");
	 * 		
	 */
	static class HttpEntityBuilder{
		private HttpHeaders headers = new HttpHeaders();
		private MultiValueMap<String, Object> body=new LinkedMultiValueMap<>();
		
		public HttpEntityBuilder withHeader(String name, String value) {
			headers.set(name, value);
			return this;
		}
		public HttpEntityBuilder withBodyParam(String name, Object value) {
			body.add(name, value);
			return this;
		}
		public HttpEntity<MultiValueMap<String, Object>> buildEntity(){
			HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
			return requestEntity;
		}
		
	}

}
