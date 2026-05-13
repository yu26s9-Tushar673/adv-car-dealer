package com.pluralsight.models;

public class SalesContract extends Contract {
    private static final double salesTaxRate = 0.05;
    private static final double recordingFee = 100.00;
    private static final double processFeeLow = 295.00;
    private static final double processFeeHigh = 495.00;
    private boolean isFinancing;


    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicle, boolean isFinancing) {
        super(date, customerName, customerEmail, vehicle);
        this.isFinancing = isFinancing;
    }

    // Getters
    public double getSalesTaxAmount() {   return getVehicleSold().getPrice() * salesTaxRate;   }
    public double getRecordingFee() {   return recordingFee;   }

    public double getProcessFee() {
        if (getVehicleSold().getPrice() < 10000) {   return processFeeLow;   }
        else {   return processFeeHigh;   }
    }

    public boolean isFinancing() {   return isFinancing;   }
    public void setFinancing(boolean financing) {   isFinancing = financing;   }

    // Abstract Methods
    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + getSalesTaxAmount() + getRecordingFee() + getProcessFee();
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinancing) {   return 0;   }

        double totalPrice = getTotalPrice();
        double monthlyRate;
        int months;

        if (getVehicleSold().getPrice() >= 10000) {
            monthlyRate = .0425 / 12;
            months = 48;
        } else {
            monthlyRate = .0525 / 12;
            months = 24;
        }
        return (totalPrice * monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
    }
}
