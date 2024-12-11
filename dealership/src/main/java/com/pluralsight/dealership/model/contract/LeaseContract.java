package com.pluralsight.dealership.model.contract;

import com.pluralsight.dealership.model.vehicle.Vehicle;

public class LeaseContract extends Contract {
    protected double expectedEndingValue;
    protected double leaseFee;


    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        expectedEndingValue = vehicleSold.getPrice() / 2;
        leaseFee = vehicleSold.getPrice() * .07;
    }

    public LeaseContract(String customerName, String customerEmail, Vehicle vehicleSold) {
        super(customerName, customerEmail, vehicleSold);
        expectedEndingValue = vehicleSold.getPrice() / 2;
        leaseFee = vehicleSold.getPrice() * .07;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return Math.floor(leaseFee * 100) / 100;
    }

    @Override
    public double getMonthlyPayment() {
        return expectedEndingValue * Math.pow((1 + (.04 / 12)), 36) - expectedEndingValue; // monthly compound interest formula;
    }

    @Override
    public double getTotalPrice() {
        return expectedEndingValue + leaseFee;
    }

    @Override
    public String toString() {
        return String.format("""
                LEASE|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f\n
                """, date, customerName, customerEmail, vehicleSold.getVin(), vehicleSold.getYear(), vehicleSold.getMake(), vehicleSold.getModel(), vehicleSold.getVehicleType(), vehicleSold.getColor(), vehicleSold.getOdometer(), vehicleSold.getPrice(), expectedEndingValue, leaseFee, totalPrice, monthlyPayment);
    }
}