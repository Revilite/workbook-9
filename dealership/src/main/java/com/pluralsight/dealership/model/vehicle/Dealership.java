package com.pluralsight.dealership.model.vehicle;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dealership {
    private final int id;
    private final String name;
    private final String address;
    private final String phoneNumber;

    private final ArrayList<VehicleforDummies> inventory = new ArrayList<>();

    public Dealership(int id, String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
