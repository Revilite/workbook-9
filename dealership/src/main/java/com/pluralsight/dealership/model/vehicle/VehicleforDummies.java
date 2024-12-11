package com.pluralsight.dealership.model.vehicle;

public class VehicleforDummies extends Vehicle {

    public VehicleforDummies(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
        super(vin, year, make, model, vehicleType, color, odometer, price);
    }


    public int getMileage() {
        return super.getOdometer();
    }
}