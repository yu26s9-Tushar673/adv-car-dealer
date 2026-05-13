package com.pluralsight.models;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    // Constructor
    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    // Returns a List of Vehicles in inventory filtered by given price range
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getPrice() >= min && v.getPrice() <= max) {
                result.add(v);
            }
        }
        return result;
    }

    // Returns a List of Vehicles in inventory filtered by given Make, Model
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getMake().equalsIgnoreCase(make) && v.getModel().equalsIgnoreCase(model)) {
                result.add(v);
            }
        }
        return result;
    }

    // Returns a List of Vehicles in inventory filtered by given year range
    public List<Vehicle> getVehiclesByYear(int min, int max) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getYear() >= min && v.getYear() <= max) {
                result.add(v);
            }
        }
        return result;
    }

    // Returns a List of Vehicles in inventory filtered by given color
    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getColor().equalsIgnoreCase(color)) {
                result.add(v);
            }
        }
        return result;
    }

    // Returns a List of Vehicles in inventory filtered by mileage range
    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getOdometer() >= min && v.getOdometer() <= max) {
                result.add(v);
            }
        }
        return result;
    }

    // Returns a List of Vehicles in inventory filtered by Vehicle Type
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getVehicleType().equalsIgnoreCase(vehicleType)) {
                result.add(v);
            }
        }
        return result;
    }

    // Inventory Modifiers
    public List<Vehicle> getAllVehicles() {   return inventory;   }
    public void addVehicle(Vehicle vehicle) {   inventory.add(vehicle);   }
    public void removeVehicle(Vehicle vehicle) {   inventory.remove(vehicle);   }

    // Getters and Setters
    public void setName(String name) {   this.name = name;   }
    public void setAddress(String address) {   this.address = address;   }
    public void setPhone(String phone) {   this.phone = phone;   }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}
