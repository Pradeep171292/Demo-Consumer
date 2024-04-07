package com.boa.restclient;


import java.io.IOException;
import java.util.Collections;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.boa.discovery.Discovery;

import jakarta.ws.rs.core.MediaType;

@RestController
@RequestMapping("/helloConsumer")
@CrossOrigin
public class ConsumerController {

private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	/**
	 * Discovery class to handle the communication with server and produceer client
	 */
	@Autowired
	Discovery discoveryClass;
	
	/**
	 * @return
	 * @throws RestClientException
	 * @throws IOException
	 * Connects with producer to fetch all student details
	 */
	@RequestMapping(value = "/getAllCustomer", produces = "plain/text", method = RequestMethod.GET)
	public ResponseEntity<String> getAllCustomer() throws RestClientException, IOException {
		logger.info("Inside getAllCustomer method.");
		return discoveryClass.discoveryResult("boa-helloproducer","/getdetails", HttpMethod.GET);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws RestClientException
	 * @throws IOException
	 * Sends request to producer to approve the provided student
	 */
	@RequestMapping(value = "/validateStudent/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<String> validateStudent(@PathVariable int id) throws RestClientException, IOException {
		logger.info("Inside validateStudent method.");
		System.out.println("Inside Consumer Validate Student:---->"+id);
		return discoveryClass.discoveryResult("admin-producer","/admin/validateStudent", HttpMethod.PUT,Collections.singletonMap("Id", id));
	}

	/**
	 * @param courseMap
	 * @return
	 * @throws RestClientException
	 * @throws IOException
	 * Send the course details to the producer to store in database
	 */
	@RequestMapping(value = "/addCourse", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<String> addCourse(@RequestBody Map<String,Object> courseMap) throws RestClientException, IOException {
		logger.info("Inside addCourse method.");
		return discoveryClass.discoveryResult("admin-producer","/admin/addCourse", HttpMethod.POST,courseMap);
	}
	
	/**
	 * @param courseId
	 * @param courseName
	 * @return
	 * @throws RestClientException
	 * @throws IOException
	 * Send the course details to the producer to delete from database
	 */
	@RequestMapping(value = "/deleteCourse/{courseId}/{courseName}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCourse(@PathVariable int courseId, @PathVariable String courseName) throws RestClientException, IOException {
		logger.info("Inside deleteCourse method.");
		return discoveryClass.discoveryResult("admin-producer","/admin/deleteCourse", HttpMethod.DELETE, Collections.singletonMap(String.valueOf(courseId), courseName));
	}
}
