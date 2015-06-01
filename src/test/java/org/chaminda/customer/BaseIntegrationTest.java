package org.chaminda.customer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	
	final RestTemplate restTemplate = new RestTemplate();

	@Value("${local.server.port}")
	private int port;

	protected String getBaseUrl() {
		return BASE_URL + port;
	}

	protected T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass) {
		T response = null;
		
		try {
			response = restTemplate.getForObject(getBaseUrl() + requestMappingUrl, serviceReturnTypeClass);

		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return response;
	}

	protected T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Map<String, String> vars) {
		T response = null;
		
		try {
			response = restTemplate.getForObject(getBaseUrl() + requestMappingUrl, serviceReturnTypeClass, vars);

		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return response;
	}


	@SuppressWarnings("unchecked")
	protected Collection<T> getList(final String requestMappingUrl) {
		Collection<T> response = null;
		
		try {
			 response = restTemplate.getForObject(getBaseUrl() + requestMappingUrl, List.class);

		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return response;
	}

	protected T postEntity(final String requestMappingUrl, final Class<T> serviceReturnClazz, final Object postObject) {
		final ObjectMapper mapper = new ObjectMapper();
		T responseEntity = null;
		
		try {
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			final HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(postObject), headers);
			responseEntity = restTemplate.postForObject(getBaseUrl() + requestMappingUrl, entity, serviceReturnClazz);

		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		return responseEntity;
	}
	
	protected void deleteEntity(final String requestMappingUrl, final Long vars){
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("custom", true + "");
	    final HttpEntity<String> entity = new HttpEntity<>(String.valueOf(vars), headers);


		try {
			
			restTemplate.delete(getBaseUrl() + requestMappingUrl, entity);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occured while calling the API " + ex.getMessage());
		}
		
	}
}

