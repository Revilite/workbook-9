package com.pluralsight.dealership;

import com.pluralsight.dealership.view.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class DealershipApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DealershipApplication.class, args);
	}

	@Autowired
	private DataSource datasource;

	@Override
	public void run(String... args) throws Exception {
		new UserInterface(datasource).display();
	}
}
