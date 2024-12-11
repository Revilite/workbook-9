package com.pluralsight.dealership.controller.vehicles;

import com.pluralsight.dealership.model.vehicle.VehicleforDummies;

import java.util.List;


    public interface VehicleDao {
        List<VehicleforDummies> findAllVehicles();
        List<VehicleforDummies> findAllAvialableVehicles();
        List<VehicleforDummies> findVehiclesByDealership(int id); //Not needed
        List<VehicleforDummies> findVehiclesByPriceRange(double min, double max);
        List<VehicleforDummies> findVehiclesByMakeModel(String make, String model);
        List<VehicleforDummies> findVehiclesByYear(int minYear, int maxYear);
        List<VehicleforDummies> findVehicleByColor(String color);
        List<VehicleforDummies> findVehicleByMileRange(int minOdom, int maxOdom);
        List<VehicleforDummies> findVehicleByVehicleType(String vehicleType);
        VehicleforDummies findVehicleByVIN(int vin);

        void removeVehicleByVIN(int vin);
        void addVehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price);
}
