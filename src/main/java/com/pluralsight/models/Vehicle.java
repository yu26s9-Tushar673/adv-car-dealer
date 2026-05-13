package com.pluralsight.models;

public class Vehicle {
    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    // Constructor
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

    // Getters and Setters
    public void setVin(int vin) {   this.vin = vin; }
    public int getVin() {   return vin;   }

    public void setYear(int year) {   this.year = year;   }
    public int getYear() {   return year;   }

    public void setMake(String make) {   this.make = make;  }
    public String getMake() {   return make;    }

    public void setModel(String model) {    this.model = model;   }
    public String getModel() {   return model;  }

    public void setVehicleType(String vehicleType) {    this.vehicleType = vehicleType;   }
    public String getVehicleType() {    return vehicleType;   }

    public void setColor(String color) {    this.color = color;   }
    public String getColor() {   return color;   }

    public void setOdometer(int odometer) {   this.odometer = odometer;   }
    public int getOdometer() {   return odometer;   }

    public void setPrice(double price) {    this.price = price;   }
    public double getPrice() {   return price;   }
}
