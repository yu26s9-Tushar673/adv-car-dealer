package com.pluralsight.FileManagers;

import com.pluralsight.models.Dealership;
import com.pluralsight.models.Vehicle;

import java.io.*;

public class DealershipFileManager {
    private String fileName;

    // Constructor
    public DealershipFileManager(String fileName) {
        this.fileName = fileName;
    }

    // Return Current Dealership Inventory
    public Dealership getDealership() {
        Dealership dealership = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String firstLine = reader.readLine();
            String[] dealershipParts = firstLine.split("\\|");
            dealership = new Dealership(dealershipParts[0], dealershipParts[1], dealershipParts[2]);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int vin = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                String make = parts[2];
                String model = parts[3];
                String type = parts[4];
                String color = parts[5];
                int odometer = Integer.parseInt(parts[6]);
                double price = Double.parseDouble(parts[7]);

                dealership.addVehicle(new Vehicle(vin, year, make, model, type, color, odometer, price));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return  dealership;
    }

    // Update Dealership Inventory
    public void saveDealership(Dealership dealership) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            writer.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            writer.newLine();

            for(Vehicle v : dealership.getAllVehicles()){
                writer.write(v.getVin() + "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getModel() + "|" +
                        v.getVehicleType() + "|" +
                        v.getColor() + "|" +
                        v.getOdometer() + "|" +
                        v.getPrice());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
    }
}
