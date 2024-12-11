package com.pluralsight.dealership.model.contract;


import com.pluralsight.dealership.model.vehicle.Vehicle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Contract {
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;
    protected double totalPrice;
    protected double monthlyPayment;

    public Contract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (date.isEmpty()) {
            this.date = dateNow.format(dtf);
        } else {
            this.date = date;
        }
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }
    public Contract(String customerName, String customerEmail, Vehicle vehicleSold) {
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        date = dateNow.format(dtf);
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();
}