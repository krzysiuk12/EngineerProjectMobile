package pl.edu.agh.asynctasks.builders.requests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class HttpRequestBuilder {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static HttpHeaders getHttpHeadersWithHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        return headers;
    }

    public static HttpHeaders getHttpHeadersWithHeaderAndJsonContent(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(new MediaType("application", "json"));
        return headers;
    }

    public static RestTemplate getRestTemplateWithJacksonConverter() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    public static RestTemplate getRestTemplateWithJacksonAndStringConverter() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }

}
