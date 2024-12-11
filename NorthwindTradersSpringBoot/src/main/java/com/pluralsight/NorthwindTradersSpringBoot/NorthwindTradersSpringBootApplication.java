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
    JdbcProductDao productDB;


    @Override
    public void run(String... args) {
        Scanner scan = new Scanner(System.in);

        System.out.printf("""
                1. List Employees
                2. Add Employee
                """);
        int userChoice = scan.nextInt();
        scan.nextLine();
        switch (userChoice) {
            case 1 -> displaytList();

        }
    }

    public void displaytList(){
        for(Product product : productDB.getAll()){
            System.out.printf("""
                    ProductID: %d, Product Name: %s, Product Price: %.2f
                    """, product.getProductId(), product.getName(), product.getPrice());
        }
    }
}
