package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);
    }

    @Autowired
    SimpleProductDao spd;

    @Autowired
    private ProductDao prodDao;


    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.printf("""
                1. List Employees
                2. Add Employee
                """);
        int userChoice = scan.nextInt();
        scan.nextLine();

        spd.add(new Product());

        spd.getAll();

    }
}
