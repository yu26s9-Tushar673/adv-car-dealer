package com.pluralsight.ui;

import com.pluralsight.FileManagers.ContractFileManager;
import com.pluralsight.FileManagers.DealershipFileManager;
import com.pluralsight.models.*;

import java.time.LocalDate;
import java.util.List;

import static com.pluralsight.ui.Console.*;


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
                    10. Buy / Lease a vehicle
                    99. Quit (Exit App)
                    """);
            int command = promptForInt("Enter # choice: ");

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
                case 10 -> processBuyLeaseVehicleRequest();
                case 99 -> {
                    System.out.println("Thank you! Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid # choice. Please enter from the given # choices.");
            }
        }
    }

    private void processBuyLeaseVehicleRequest() {
        int vin = promptForInt("Enter the VIN of the vehicle: ");
        Vehicle vehicle = null;

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                vehicle = v;
                break;
            }
        }

        if (vehicle == null) {
            System.out.println("Vehicle with VIN: " + vin + " not found");
            return;
        }
        System.out.printf("Vehicle Found: %d %s %s | Color: %s | Price: $%.2f%n",
                vehicle.getYear(), vehicle.getMake(), vehicle.getModel(),
                vehicle.getColor(), vehicle.getPrice());

        String date = String.valueOf(Console.promptForDate("Enter contract date (YYYY-MM-DD):"));
        String customerName = promptForString("Enter customer name: ");
        String customerEmail = promptForString("Enter customer email: ");

        boolean isSale = promptForYesNo("Are you purchasing a vehicle? (yes for purchase, no for lease): ");

        Contract contract;

        if (isSale) {
            boolean wantsFinancing = promptForYesNo("Do you want to Finance?");
            contract = new SalesContract(date, customerName, customerEmail, vehicle, wantsFinancing);
            System.out.println("\n---------- Purchase Summary ----------");
            System.out.printf("Vehicle Price : $%,.2f%n", vehicle.getPrice());
            System.out.printf("Sales Tax : $%,.2f%n", ((SalesContract) contract).getSalesTaxAmount());
            System.out.printf("Recording Fee : $%,.2f%n", ((SalesContract) contract).getRecordingFee());
            System.out.printf("Processing Fee : $%,.2f%n", ((SalesContract) contract).getProcessFee());
            System.out.printf("Total Price : $%,.2f%n", contract.getTotalPrice());
            if (wantsFinancing) {
                System.out.printf("Monthly Payment: $%,.2f%n", contract.getMonthlyPayment());
            } else {
                System.out.println("No Monthly Payment. (No Financing)");
            }
        } else {
            int currentYear = LocalDate.now().getYear();
            int vehicleAge = currentYear - vehicle.getYear();

            if (vehicleAge > 3) {
                System.out.println("Cannot Lease a vehicle greater than 3 years old.");
                System.out.println("This vehicle is " + vehicleAge + " years old.");
                return;
            }

            contract = new LeaseContract(date, customerName, customerEmail, vehicle);
            System.out.println("\n---------- Lease Summary ----------");
            System.out.printf("Vehicle Price : $%,.2f%n", vehicle.getPrice());
            System.out.printf("Expected End Value: $%,.2f%n", ((LeaseContract) contract).getExpectedEndingValue());
            System.out.printf("Lease Fee (%%7): $%,.2f%n", ((LeaseContract) contract).getLeaseFee());
            System.out.printf("Total Price: $%,.2f%n", contract.getTotalPrice());
            System.out.printf("Monthly Payment: $%,.2f%n", contract.getMonthlyPayment());
        }
        boolean confirm = Console.promptForYesNo("\nConfirm contract? ");
        if (!confirm) {
            System.out.println("Contract cancelled.");
            return;
        }
        ContractFileManager contractFileManager = new ContractFileManager("contracts.csv");
        contractFileManager.saveContract(contract);

        dealership.removeVehicle(vehicle);
        new DealershipFileManager("inventory.csv").saveDealership(dealership);
        System.out.println("Vehicle Removed from Inventory");
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
        int min = promptForInt("Enter Minimum Year: ");
        int max = promptForInt("Enter Maximum Year: ");
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    // Displays inventory filtered by color
    private void processGetByColorRequest() {
        String color = Console.promptForString("Enter Color: ");
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    // Displays inventory filtered by mileage range
    private void processGetByMileageRequest() {
        int min = promptForInt("Enter Minimum Mileage: ");
        int max = promptForInt("Enter Maximum Mileage: ");
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
        int vin = promptForInt("Enter Vehicle VIN#: ");
        int year = promptForInt("Enter Vehicle Year: ");
        String make = Console.promptForString("Enter Vehicle Make: ");
        String model = Console.promptForString("Enter Vehicle Model: ");
        String type = Console.promptForString("Enter Vehicle Type (Car, Suv, Truck.): ").toUpperCase();
        String color = Console.promptForString("Enter Vehicle Color: ");
        int odometer = promptForInt("Enter Vehicle Mileage: ");
        double price = Console.promptForDouble("Enter Vehicle Price: ");

        dealership.addVehicle(new Vehicle(vin, year, make, model, type, color, odometer, price));
        new DealershipFileManager("inventory.csv").saveDealership(dealership);
        System.out.println("Vehicle Added to Inventory.");
    }

    // Removes a Vehicle from Dealership inventory
    private void processRemoveVehicleRequest() {
        int vin = promptForInt("Enter Vehicle VIN # to remove: ");
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
