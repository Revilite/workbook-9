package com.pluralsight.dealership.model.vehicle;

import java.util.Objects;

public class Vehicle {
    private final int vin;
    private final int year;
    private final String make;
    private final String model;
    private final String vehicleType;
    private final String color;
    private final int odometer;
    private final double price;

    public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }


    public String getMake() {
        return make;
    }


    public String getModel() {
        return model;
    }


    public String getVehicleType() {
        return vehicleType;
    }


    public String getColor() {
        return color;
    }


    public int getOdometer() {
        return odometer;
    }


    public double getPrice() {
        return price;
    }

    //Used for the tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vin == vehicle.vin && year == vehicle.year && odometer == vehicle.odometer && Double.compare(price, vehicle.price) == 0 && Objects.equals(make, vehicle.make) && Objects.equals(model, vehicle.model) && Objects.equals(vehicleType, vehicle.vehicleType) && Objects.equals(color, vehicle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, year, make, model, vehicleType, color, odometer, price);
    }
}
