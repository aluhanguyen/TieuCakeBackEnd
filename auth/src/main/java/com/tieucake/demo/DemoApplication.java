package com.tieucake.demo;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

@SpringBootApplication
@RestController
public class DemoApplication {
	
	private DatastoreService datastore;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/")
	public String hello() throws EntityNotFoundException {
		Entity employee = new Entity("Employee", "asalieri");
		employee.setProperty("firstName", "Antonio");
		employee.setProperty("lastName", "Salieri");
		employee.setProperty("hireDate", new Date());
		employee.setProperty("attendedHrTraining", true);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key id = datastore.put(employee);
		Entity tmpemployee = datastore.get(id);
		return tmpemployee.getProperty("firstName").toString();
	}
}
