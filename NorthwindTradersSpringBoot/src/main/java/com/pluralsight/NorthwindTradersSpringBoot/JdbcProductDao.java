package com.pluralsight.NorthwindTradersSpringBoot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {
    private DataSource datasource;

    @Autowired
    public JdbcProductDao(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void add(Product product) {
        try (Connection connection = datasource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO products(ProductID, ProductName, UnitPrice)
                    VALUES(?, ?, ?);
                    """);
            statement.setInt(1, product.getProductId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        int id;
        String name;
        double price;

        try (Connection connection = datasource.getConnection()) {
//            System.out.println(datasource.getConnection());
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT ProductID, ProductName, UnitPrice
                    FROM products;
                    """);
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                id = rs.getInt("ProductID");
                name = rs.getString("ProductName");
                price = rs.getDouble("UnitPrice");

                products.add(new Product(id, name, "N/A", price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
