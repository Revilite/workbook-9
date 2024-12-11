package com.pluralsight.dealership.model.contract;

import com.pluralsight.dealership.model.vehicle.Vehicle;

public class SalesContract extends Contract {
    protected double salesTaxAmount;
    protected double recordingFee;
    protected double processingFee;
    protected boolean isFinancing;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean isFinancing) {
        super(date, customerName, customerEmail, vehicleSold);
        salesTaxAmount = .05 * vehicleSold.getPrice();
        recordingFee = 100;
        processingFee = vehicleSold.getPrice() >= 10000 ? 495 : 295;
        this.isFinancing = isFinancing;

        if (isFinancing && vehicleSold.getPrice() >= 10000) {
            monthlyPayment = (getTotalPrice() * .0425) / 12;
        } else if (isFinancing && vehicleSold.getPrice() < 10000) {
            monthlyPayment = getTotalPrice() * Math.pow((1 + (.0525 / 12)), 24) - getTotalPrice();
        } else {
            monthlyPayment = 0;
        }

    }
    public SalesContract(String customerName, String customerEmail, Vehicle vehicleSold, boolean isFinancing) {
        super(customerName, customerEmail, vehicleSold);
        salesTaxAmount = .05 * vehicleSold.getPrice();
        recordingFee = 100;
        processingFee = vehicleSold.getPrice() >= 10000 ? 495 : 295;
        this.isFinancing = isFinancing;

        if (isFinancing && vehicleSold.getPrice() >= 10000) {
            monthlyPayment = (getTotalPrice() * .0425) / 12;
        } else if (isFinancing && vehicleSold.getPrice() < 10000) {
            monthlyPayment = getTotalPrice() * Math.pow((1 + (.0525 / 12)), 24) - getTotalPrice();
        } else {
            monthlyPayment = 0;
        }

    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }


    public double getProcessingFee() {
        return processingFee;
    }


    public boolean isFinancing() {
        return isFinancing;
    }


    @Override
    public double getTotalPrice() {
        return vehicleSold.getPrice() + recordingFee + processingFee + salesTaxAmount;
    }

    @Override
    public double getMonthlyPayment() {
        return Math.floor(monthlyPayment * 100) / 100;
    }

    @Override
    public String toString() {

        return String.format("""
                SALE|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f\n
                """, date, customerName, customerEmail, vehicleSold.getVin(), vehicleSold.getYear(), vehicleSold.getMake(), vehicleSold.getModel(), vehicleSold.getVehicleType(), vehicleSold.getColor(), vehicleSold.getOdometer(), vehicleSold.getPrice(), salesTaxAmount, recordingFee, processingFee, totalPrice, isFinancing ? "YES" : "NO", monthlyPayment);
    }
}