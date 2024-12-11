package com.pluralsight.dealership.controller.contracts;

import com.pluralsight.dealership.model.contract.LeaseContract;
import com.pluralsight.dealership.model.vehicle.Vehicle;
import com.pluralsight.dealership.model.vehicle.VehicleforDummies;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaseContractDAOMysqlImpl implements LeaseContractDao {
    private DataSource dataSource;

    public LeaseContractDAOMysqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addLeaseContract(LeaseContract contract) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement addLeaseContract = connection.prepareStatement("""
                    INSERT INTO `lease_contracts`(`date`, `customer_name`, `customer_email`, `vin`, `total_price`, `expected_ending_value`, `lease_fee`, `monthly_payment`)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                    """);
            addLeaseContract.setString(1, contract.getDate());
            addLeaseContract.setString(2, contract.getCustomerName());
            addLeaseContract.setString(3, contract.getCustomerEmail());
            addLeaseContract.setInt(4, contract.getVehicleSold().getVin());
            addLeaseContract.setDouble(5, contract.getTotalPrice());
            addLeaseContract.setDouble(6, contract.getExpectedEndingValue());
            addLeaseContract.setDouble(7, contract.getLeaseFee());
            addLeaseContract.setDouble(8, contract.getMonthlyPayment());

            PreparedStatement updateVehicle = connection.prepareStatement("""
                    UPDATE vehicles
                    set sold = 1
                    WHERE vin = ?;
                    """);

            updateVehicle.setInt(1, contract.getVehicleSold().getVin());

            addLeaseContract.executeUpdate();
            updateVehicle.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LeaseContract> findAllLeaseContracts() {

        List<LeaseContract> contracts = new ArrayList<>();

        String date, customerName, customerEmail, make, model, vehicleType, color;
        int vin, year, odometer;
        double price;
        Vehicle vehicleSold;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findAllSales = connection.prepareStatement("""
                    SELECT * FROM lease_contracts
                    JOIN vehicles ON vehicles.vin = lease_contracts.vin
                    ORDER BY date;
                    """);

            ResultSet resultSet = findAllSales.executeQuery();

            while (resultSet.next()) {
                date = resultSet.getString("date");
                customerName = resultSet.getString("customer_name");
                customerEmail = resultSet.getString("customer_email");
                make = resultSet.getString("make");
                model = resultSet.getString("model");
                vehicleType = resultSet.getString("vehicle_type");
                color = resultSet.getString("color");
                vin = resultSet.getInt("vin");
                year = resultSet.getInt("year");
                odometer = resultSet.getInt("odometer");
                price = resultSet.getDouble("price");


                vehicleSold = new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price);
                contracts.add(new LeaseContract(date, customerName, customerEmail, vehicleSold));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contracts;
    }
}