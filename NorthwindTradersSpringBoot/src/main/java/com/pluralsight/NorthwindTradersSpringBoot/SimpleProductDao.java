package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleProductDao implements ProductDao {
    private List<Product> employees;


    public SimpleProductDao(List<Product> employees) {
        this.employees = employees;
    }

    @Override
    public void add(Product product) {
        employees.add(product);
    }

    @Override
    public List<Product> getAll() {
        return employees;
    }
}
