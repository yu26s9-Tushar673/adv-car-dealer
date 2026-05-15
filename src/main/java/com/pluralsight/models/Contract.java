package com.pluralsight.models;

public abstract class Contract {
    private String date;
    private String customerName;
    private String customerEmail;
    private static Vehicle vehicleSold;
    private double price;
    private double monthlyPayment;

    public Contract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicle;
    }

    // Getters and Setters
    public void setDate(String date) {   this.date = date;   }
    public String getDate() {   return date;   }

    public void setCustomerName(String customerName) {   this.customerName = customerName;   }
    public String getCustomerName() {   return customerName;   }

    public void setCustomerEmail(String customerEmail) {   this.customerEmail = customerEmail;   }
    public String getCustomerEmail() {   return customerEmail;   }

    public void setSold(Vehicle vehicle) {   vehicleSold = vehicle;   }
    public static Vehicle getVehicleSold() {   return vehicleSold;   }

    // Abstract methods
    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}
