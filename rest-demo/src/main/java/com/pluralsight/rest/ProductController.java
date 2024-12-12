package com.pluralsight.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.ArrayList;
import java.util.List;

//Declares that this is a rest controller
@RestController
//sets the path for the different types of requests
@RequestMapping(path = "/rest/products")
public class ProductController {

    @GetMapping
    @RequestMapping(path = "/{id}")
    public Product findById(@PathVariable("id") int id) {
        return new Product(id, "Super Dad Gift", "Gifts", 12.00);
    }


    //Declares this method as a get request
    @GetMapping
    public List<Product> findAll() {
        List<Product> results = new ArrayList<>();
        results.add(new Product(1, "Super Dad Gift", "Gifts", 12.00));
        return results;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int productId) {

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        System.out.printf("""
                id: %d
                name: %s
                category: %s
                price: %.2f
                """, product.getProductId(), product.getName(), product.getCategory(), product.getPrice());
        return product;

    }
}
