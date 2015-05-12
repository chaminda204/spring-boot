package org.chaminda.customer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerServiceApplication.class)
@IntegrationTest("server.port:8080")
@WebAppConfiguration
public abstract class BaseIntegrationTest<T> {

	private static final String BASE_URL = "http://localhost:";
	
	private static final Logger LOGGER = Logger.getLogger(BaseIntegrationTest.class.getName());

	@Value("${local.server.port}")
	private int port;

	protected String getBaseUrl() {
		return BASE_URL + port;
	}

	protected T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass) {
		final RestTemplate restTemplate = new RestTemplate();

		try {

			final ResponseEntity<T> response = restTemplate.getForEntity(getBaseUrl() + requestMappingUrl, serviceReturnTypeClass);

			return response.getBody();
		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return null;
	}
	
	protected T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Map<String, String> vars) {
		final RestTemplate restTemplate = new RestTemplate();
		T response = null;
		try {
			response = restTemplate.getForObject(getBaseUrl() + requestMappingUrl, serviceReturnTypeClass, vars);

		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Collection<T> getList(final String requestMappingUrl) {

		try {
			final RestTemplate restTemplate = new TestRestTemplate();
			
			final ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + requestMappingUrl, List.class);
			return response.getBody();
		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return null;
	}

	protected T postEntity(final String requestMappingUrl, final Class<T> serviceReturnClazz, final Object postObject) {
		T responseEntity = null;
		final TestRestTemplate restTemplate = new TestRestTemplate();
		final ObjectMapper mapper = new ObjectMapper();

		try {
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			final HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(postObject), headers);
			final ResponseEntity<T> response = restTemplate.postForEntity(getBaseUrl() + requestMappingUrl, entity, serviceReturnClazz);

			responseEntity = response.getBody();
		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return responseEntity;
	}
}