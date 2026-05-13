package com.pluralsight.ui;

import com.pluralsight.inventoryFileManager.DealershipFileManager;
import com.pluralsight.models.Dealership;
import com.pluralsight.models.Vehicle;

import java.util.List;


public class UserInterface {
    private Dealership dealership;
    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager("inventory.csv");
        this.dealership = fileManager.getDealership();
    }

    // Dealership Menu
    public void display() {
        init();
        while (true) {
            System.out.println("""
                    
                    ---------- Car Dealer ----------
                    1. Search By Price
                    2. Search By Make/Model
                    3. Search By Year
                    4. Search By Color
                    5. Search By Mileage
                    6. Search By Vehicle Type
                    7. List All Vehicles
                    8. Add Vehicle
                    9. Remove Vehicle
                    99. Quit (Exit App)
                    """);
            int command = Console.promptForInt("Enter # choice: ");

            switch (command) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByVehicleTypeRequest();
                case 7 -> processGetAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 99 -> {
                    System.out.println("Thank you! Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid # choice. Please enter from the given # choices.");
            }
        }
    }

    // Displays all Vehicles in inventory
    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()){
            System.out.println("No vehicles found.");
        }
        System.out.println("-------- Matching Inventory --------");
        for (Vehicle v : vehicles) {
            System.out.printf("VIN: %d | %d %s %s | Type: %s | Color: %s | Miles: %d | Price: $%.2f%n",
                    v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                    v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
        }
    }

    // Displays inventory filtered by price range
    private void processGetByPriceRequest() {
        double min = Console.promptForDouble("Enter Minimum Price: ");
        double max = Console.promptForDouble("Enter Maximum Price: ");
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    // Displays inventory filtered by Make/model
    private void processGetByMakeModelRequest() {
        String make = Console.promptForString("Enter Vehicle Make: ");
        String model = Console.promptForString("Enter Vehicle Model: ");
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    // Displays inventory filtered by year range
    private void processGetByYearRequest() {
        int min = Console.promptForInt("Enter Minimum Year: ");
        int max = Console.promptForInt("Enter Maximum Year: ");
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    // Displays inventory filtered by color
    private void processGetByColorRequest() {
        String color = Console.promptForString("Enter Color: ");
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    // Displays inventory filtered by mileage range
    private void processGetByMileageRequest() {
        int min = Console.promptForInt("Enter Minimum Mileage: ");
        int max = Console.promptForInt("Enter Maximum Mileage: ");
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    // Displays inventory filtered by Vehicle Type
    private void processGetByVehicleTypeRequest() {
        String type = Console.promptForString("Enter Type Of Vehicle (car, truck, suv, etc): ");
        displayVehicles(dealership.getVehiclesByType(type));
    }

    // Displays all Vehicles by retrieving inventory List and calling displayVehicles method
    private void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    // Adds a Vehicle to Dealership inventory
    private void processAddVehicleRequest() {
        int vin = Console.promptForInt("Enter Vehicle VIN#: ");
        int year = Console.promptForInt("Enter Vehicle Year: ");
        String make = Console.promptForString("Enter Vehicle Make: ");
        String model = Console.promptForString("Enter Vehicle Model: ");
        String type = Console.promptForString("Enter Vehicle Type (Car, Suv, Truck.): ").toUpperCase();
        String color = Console.promptForString("Enter Vehicle Color: ");
        int odometer = Console.promptForInt("Enter Vehicle Mileage: ");
        double price = Console.promptForDouble("Enter Vehicle Price: ");

        dealership.addVehicle(new Vehicle(vin, year, make, model, type, color, odometer, price));
        new DealershipFileManager("inventory.csv").saveDealership(dealership);
        System.out.println("Vehicle Added to Inventory.");
    }

    // Removes a Vehicle from Dealership inventory
    private void processRemoveVehicleRequest() {
        int vin = Console.promptForInt("Enter Vehicle VIN # to remove: ");
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                boolean confirm = Console.promptForYesNo("Are you sure you would like to remove Vehicle with VIN# " + v.getVin() + ": ");
                if (confirm) {
                    dealership.removeVehicle(v);
                    new DealershipFileManager("inventory.csv").saveDealership(dealership);
                    System.out.println("Vehicle Removed from Inventory.");
                } else {
                    System.out.println("Removal Cancelled.");
                }
                return;
            }
        }
        System.out.println("Vehicle with VIN #" + vin + " not found.");
    }
}
