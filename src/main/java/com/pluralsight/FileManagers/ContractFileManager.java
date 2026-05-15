package com.pluralsight.FileManagers;

import com.pluralsight.models.Contract;
import com.pluralsight.models.LeaseContract;
import com.pluralsight.models.SalesContract;
import com.pluralsight.models.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {
    private String fileName;

    // Constructor
    public ContractFileManager(String fileName) {
        this.fileName = fileName;
    }

    public void saveContract(Contract contract) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            StringBuilder line = new StringBuilder();

            String contractType = (contract instanceof SalesContract) ? "SALES" : "LEASE";

            line.append(contractType).append("|");
            line.append(contract.getDate()).append("|");
            line.append(contract.getCustomerName()).append("|");
            line.append(contract.getCustomerEmail()).append("|");

            Vehicle v = contract.getVehicleSold();
            line.append(v.getVin()).append("|");
            line.append(v.getYear()).append("|");
            line.append(v.getMake()).append("|");
            line.append(v.getModel()).append("|");
            line.append(v.getVehicleType()).append("|");
            line.append(v.getColor()).append("|");
            line.append(v.getOdometer()).append("|");
            line.append(String.format("%.2f", v.getPrice())).append("|");

            if (contract instanceof SalesContract) {
                SalesContract salesContract = (SalesContract) contract;

                line.append(String.format("%.2f", salesContract.getSalesTaxAmount())).append("|");
                line.append(String.format("%.2f", salesContract.getRecordingFee())).append("|");
                line.append(String.format("%.2f", salesContract.getProcessFee())).append("|");
                line.append(salesContract.isFinancing() ? "YES" : "NO").append("|");
            } else if (contract instanceof LeaseContract) {
                LeaseContract leaseContract = (LeaseContract) contract;

                line.append(String.format("%.2f", leaseContract.getExpectedEndingValue())).append("|");
                line.append(String.format("%.2f", leaseContract.getLeaseFee())).append("|");
            }
            line.append(String.format("%.2f", contract.getTotalPrice())).append("|");
            line.append(String.format("%.2f", contract.getMonthlyPayment())).append("|");

            writer.write(line.toString());
            writer.newLine();
            writer.close();

            System.out.println("Contract Added Successfully!");
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
    }
}
