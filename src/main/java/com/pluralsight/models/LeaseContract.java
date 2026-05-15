package com.pluralsight.models;

public class LeaseContract extends Contract{
    private static final double endingValueRate = .50;
    private static final double leaseFeeRate = .07;
    private static final double leaseRate = .04;
    private static final double leaseMonths = 36;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    public static double getExpectedEndingValue() { return getVehicleSold().getPrice() * endingValueRate;   }
    public static double getLeaseFee() {   return getVehicleSold().getPrice() * leaseFeeRate;   }


    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() - getExpectedEndingValue() + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyRate = leaseRate / 12;
        double totalPrice = getTotalPrice();
        return (totalPrice * monthlyRate * Math.pow(1 + monthlyRate, leaseMonths)) / (Math.pow(1 + monthlyRate, leaseMonths) - 1);
    }
}
