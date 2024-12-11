package com.pluralsight.dealership.controller.vehicles;


import com.pluralsight.dealership.model.vehicle.Dealership;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DealershipDAOMysqlImpl implements DealershipDAO {
    private final DataSource dataSource;

    public DealershipDAOMysqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dealership findDealershipById(int id) {
        String name = "";
        String phone = "";
        String address = "";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement getDealershipById = connection.prepareStatement("""
                       SELECT * 
                       FROM dealerships
                       WHERE dealership_id = ?;
                    """);
            getDealershipById.setInt(1, id);
            ResultSet rs = getDealershipById.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                address = rs.getString("address");
                phone = rs.getString("phone");
            }

            return new Dealership(id, name, phone, address);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dealership> findAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();
        String name;
        String phone;
        String address;
        int id;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement getAllDealerships = connection.prepareStatement("""
                    SELECT * 
                    FROM dealerships;
                    """);
            getAllDealerships.executeQuery();

            ResultSet rs = getAllDealerships.getResultSet();

            while (rs.next()) {
                id = rs.getInt("dealership_id");
                name = rs.getString("name");
                address = rs.getString("address");
                phone = rs.getString("phone");
                dealerships.add(new Dealership(id, name, address, phone));
            }

            return dealerships;

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

}

