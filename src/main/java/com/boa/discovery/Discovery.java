package com.boa.discovery;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Amit Balyan
 * Discovery class to communicate with the required producer registered on server
 *
 */
@Component
public class Discovery {

	private static final Logger logger = LoggerFactory.getLogger(Discovery.class);

	/**
	 * Used to establish connection with discovery client
	 */
	@Autowired
	DiscoveryClient discoveryClient;

	/**
	 * @param clientName
	 * @param producerUrl
	 * @param http
	 * @param requestBody
	 * @return
	 * Send request to server and fetch the response along with request body
	 */
	public ResponseEntity<String> discoveryResult(String clientName, String producerUrl, HttpMethod http, Map<String,Object> requestBody){
		
		logger.info("Inside discoveryResult method.");
		List<ServiceInstance> instances=discoveryClient.getInstances(clientName);

		ServiceInstance serviceInstance=instances.get(0);
		String baseUrl=serviceInstance.getUri().toString();
		baseUrl=baseUrl+producerUrl;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try {
			response=restTemplate.exchange(baseUrl,http, getHeaders(requestBody),String.class);
		} catch (Exception ex) {
			//TODO
		}
		return response;
	}

	/**
	 * @param requestBody
	 * @return
	 * @throws IOException
	 * To set headers of the request
	 */
	private static HttpEntity<?> getHeaders(Map<String,Object> requestBody) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(requestBody,headers);
	}

	/**
	 * @param clientName
	 * @param producerUrl
	 * @param http
	 * @return
	 * Overloaded method to send request without body
	 */
	public ResponseEntity<String> discoveryResult(String clientName, String producerUrl, HttpMethod http){
		logger.info("Inside discoveryResult method overloaded.");
		List<ServiceInstance> instances=discoveryClient.getInstances(clientName);

		ServiceInstance serviceInstance=instances.get(0);
		String baseUrl=serviceInstance.getUri().toString();
		baseUrl=baseUrl+producerUrl;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try {
			response=restTemplate.exchange(baseUrl,http, getHeaders(),String.class);
		} catch (Exception ex) {
			//TODO
		}
		return response;
	}

	/**
	 * @return
	 * @throws IOException
	 * Overloaded method to set empty header
	 */
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		return new HttpEntity<>(headers);
	}
}
