package com.pluralsight.dealership.controller.vehicles;

import com.pluralsight.dealership.model.vehicle.VehicleforDummies;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOMysqlImpl implements VehicleDao {


    private final DataSource dataSource;

    public VehicleDAOMysqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<VehicleforDummies> findAllVehicles() {
        // Create variable placeholders
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;


        //Gets the connection from the datasource
        try (Connection connection = dataSource.getConnection()) {
            //Prepares the SQL statement
            PreparedStatement findAllVehicles = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    ORDER BY price;
                    """);
            //Executes the query
            findAllVehicles.executeQuery();
            //Recieves the results  from the query
            ResultSet rs = findAllVehicles.getResultSet();

            while (rs.next()) {
                //Translates the resultset data into java data
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                vehicleType = rs.getString("vehicle_type");
                if (!sold) {
                    //If the vehicle is not sold creates the vehicle and adds to a list
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));

                }
            }
            // returns the vehicle
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Rinse and repeat for all the methods but change the sql statement each time

    }

    @Override
    public List<VehicleforDummies> findAllAvialableVehicles() {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        boolean sold;
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;


        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findAllVehicles = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    ORDER BY price;
                    """);
            findAllVehicles.executeQuery();

            ResultSet rs = findAllVehicles.getResultSet();

            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                vehicleType = rs.getString("vehicle_type");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));
                }

            }

            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VehicleforDummies> findVehiclesByDealership(int id) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vehicles.vin, vehicle_type, sold
                    FROM inventory
                    JOIN vehicles ON inventory.vin = vehicles.vin
                    WHERE dealership_id = ?;
                    """);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public List<VehicleforDummies> findVehiclesByPriceRange(double min, double max) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vehicles.vin, vehicle_type, sold
                    FROM vehicles
                    WHERE price BETWEEN ? AND ?
                    ORDER BY price;
                    """);
            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public List<VehicleforDummies> findVehiclesByMakeModel(String userMake, String userModel) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findByMakeModel = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    WHERE make = ? AND model = ?
                    ORDER BY price;
                    """);

            findByMakeModel.setString(1, userMake);
            findByMakeModel.setString(2, userModel);
            ResultSet rs = findByMakeModel.executeQuery();

            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public List<VehicleforDummies> findVehiclesByYear(int minYear, int maxYear) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findByYear = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    WHERE year BETWEEN ? AND ?
                    ORDER BY year DESC;
                    """);
            findByYear.setInt(1, minYear);
            findByYear.setInt(2, maxYear);
            ResultSet rs = findByYear.executeQuery();

            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public List<VehicleforDummies> findVehicleByColor(String userColor) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findByColor = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    WHERE color = ?
                    ORDER BY price;
                    """);
            findByColor.setString(1, userColor);
            ResultSet rs = findByColor.executeQuery();

            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public List<VehicleforDummies> findVehicleByMileRange(int minOdom, int maxOdom) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findByMileage = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    WHERE odometer BETWEEN ? AND ?
                    ORDER BY odometer;
                    """);
            findByMileage.setInt(1, minOdom);
            findByMileage.setInt(2, maxOdom);

            ResultSet rs = findByMileage.executeQuery();

            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public List<VehicleforDummies> findVehicleByVehicleType(String userVehicleType) {
        List<VehicleforDummies> vehicles = new ArrayList<>();
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findByVehicleType = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    WHERE vehicle_type = ?;
                    """);
            findByVehicleType.setString(1, userVehicleType);

            ResultSet rs = findByVehicleType.executeQuery();


            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");
                if (!sold) {
                    vehicles.add(new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price));

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public VehicleforDummies findVehicleByVIN(int userVin) {
        VehicleforDummies vehicle = null;
        String make, model, color, vehicleType;
        int year, odometer, vin;
        double price;
        boolean sold;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement findByVehicleType = connection.prepareStatement("""
                    SELECT make, model, year, color, odometer, price, vin, vehicle_type, sold
                    FROM vehicles
                    WHERE vin = ?;
                    """);
            findByVehicleType.setInt(1, userVin);

            ResultSet rs = findByVehicleType.executeQuery();

            while (rs.next()) {
                make = rs.getString("make");
                model = rs.getString("model");
                year = rs.getInt("year");
                color = rs.getString("color");
                odometer = rs.getInt("odometer");
                price = rs.getDouble("price");
                vehicleType = rs.getString("vehicle_type");
                vin = rs.getInt("vin");
                sold = rs.getBoolean("sold");

                if (!sold) {
                    vehicle = new VehicleforDummies(vin, year, make, model, vehicleType, color, odometer, price);
                }
            }
        } catch (
                SQLException e) {
            throw new

                    RuntimeException(e);
        }
        return vehicle;
    }

    @Override
    public void removeVehicleByVIN(int vin) {
        //Get connection from datasource
        try (Connection connection = dataSource.getConnection()) {
            //Prepare delete statement in sql, delete from vehicles and inventory
            PreparedStatement removeVehicle = connection.prepareStatement("""
                    DELETE vehicles, inventory
                    FROM inventory
                    JOIN vehicles ON inventory.vin = vehicles.vin
                    WHERE inventory.vin = ?;
                    """);
            removeVehicle.setInt(1, vin);
            //executes update
            removeVehicle.executeUpdate();

            //Result set is not required but,  you are able to get the amount of rows affected as a int
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addVehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
        //get connection from datasource
        try (Connection connection = dataSource.getConnection()) {
            // prepare insert statement
            PreparedStatement addVehicle = connection.prepareStatement("""
                    INSERT INTO vehicles (vin, year, make, model, vehicle_type, color, odometer, price, sold) VALUE
                    (?,?,?,?,?,?,?,?,0);
                    """);
            //fill in all the ? to fill insert statement.
            addVehicle.setInt(1, vin);
            addVehicle.setInt(2, year);
            addVehicle.setString(3, make);
            addVehicle.setString(4, model);
            addVehicle.setString(5, vehicleType);
            addVehicle.setString(6, color);
            addVehicle.setInt(7, odometer);
            addVehicle.setDouble(8, price);

            //execute statement
            addVehicle.executeUpdate();

            //ResultSet is not required but you are able to find out the rows affected represented as a int
        } catch (SQLException e) {
            System.out.println("Vehicle with the same vin already added!");
        }
    }


}


// 101257, 101261, 101236, 101237, 101238, 101239, 101240