package com.pluralsight.dealership.view;

import com.pluralsight.dealership.controller.contracts.LeaseContractDAOMysqlImpl;
import com.pluralsight.dealership.controller.contracts.SalesContractDAOMysqlImpl;
import com.pluralsight.dealership.controller.vehicles.VehicleDAOMysqlImpl;
import com.pluralsight.dealership.model.vehicle.Vehicle;
import com.pluralsight.dealership.model.vehicle.VehicleforDummies;
import com.pluralsight.dealership.view.JavaHelpers.ColorCodes;
import com.pluralsight.dealership.model.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;



public class UserInterface {
    private VehicleDAOMysqlImpl vehicleDB;
    private SalesContractDAOMysqlImpl salesContractDB;
    private LeaseContractDAOMysqlImpl leaseContractDB;

    public UserInterface(DataSource dataSource) {
        this.vehicleDB = new VehicleDAOMysqlImpl(dataSource);
        this.salesContractDB = new SalesContractDAOMysqlImpl(dataSource);
        this.leaseContractDB = new LeaseContractDAOMysqlImpl(dataSource);
    }


    public void display() {
        Scanner scan = new Scanner(System.in);
        String userChoice = "";
        System.out.printf("""
                       %s╔════════════════════════════╗
                       ║ Welcome to my dealership!! ║
                       ╚════════════════════════════╝%s
                %n""", ColorCodes.GREEN, ColorCodes.RESET);

        while (!userChoice.equals("99")) {
            System.out.printf("""
                    %s╔══════════════════════════════════════════╗
                    ║ Please select what you want to search by!║
                    ╠══════════════════════════════════════════╣
                    ║ Price                              (1)   ║
                    ║ Make/Model                         (2)   ║
                    ║ Year                               (3)   ║
                    ║ Color                              (4)   ║
                    ║ Mileage                            (5)   ║
                    ║ Type                               (6)   ║
                    ║ All                                (7)   ║
                    ║ Add a vehicle                      (8)   ║
                    ║ Remove a vehicle                   (9)   ║
                    ║ Sell/Lease a vehicle               (10)  ║
                    ║ Admin Interface                    (11)  ║
                    ║ Quit                               (99)  ║
                    ╚══════════════════════════════════════════╝%s
                    %n""", ColorCodes.BLUE, ColorCodes.RESET);
            userChoice = scan.nextLine();

            switch (userChoice) {
                case ("1"):
                    processGetByPriceRequest();
                    break;
                case ("2"):
                    processGetByMakeModelRequest();
                    break;
                case ("3"):
                    processGetByYearRequest();
                    break;
                case ("4"):
                    processGetByColorRequest();
                    break;
                case ("5"):
                    processGetByMileageRequest();
                    break;
                case ("6"):
                    processGetByTypeRequest();
                    break;
                case ("7"):
                    processGetAllRequest();
                    break;
                case ("8"):
                    processAddVehicleRequest();
                    break;
                case ("9"):
                    processRemoveVehicleRequest();
                    break;
                case ("10"):
                    processSellLeaseVehicle();
                    break;
                case ("11"):
                    processAdminUI();
                    break;
                case ("99"):
                    break;
                default:
                    System.out.println("Please choose one of the options");


            }
        }
    }

    public String displayVehicles(List<VehicleforDummies> listOfVehicle) {
        StringBuilder sb = new StringBuilder();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        for (VehicleforDummies vehicle : listOfVehicle) {
            //Gets the color and the variables formatted
            String make = String.format(ColorCodes.YELLOW + "  %-16s ", vehicle.getMake() + ColorCodes.RESET);
            String model = String.format(ColorCodes.GREEN + " %-14s ", vehicle.getModel() + ColorCodes.RESET);
            String year = String.format(ColorCodes.BLUE + "      %-14s ", vehicle.getYear() + ColorCodes.RESET);
            String color = String.format(ColorCodes.RED + " %-18s ", vehicle.getColor() + ColorCodes.RESET);
            String mileage = String.format(ColorCodes.PURPLE + " %-18s ", vehicle.getMileage() + ColorCodes.RESET);
            String price = String.format(ColorCodes.WHITE + " %-24s ", nf.format(vehicle.getPrice()) + ColorCodes.RESET);
            String vin = String.format(ColorCodes.CYAN + "%-15s ", vehicle.getVin());

            //Used if there is only 1 item in the list
            if (listOfVehicle.size() == 1) {
                sb.append("╔═════════════╦══════════════╦════════════╦══════════════╦═════════════╦══════════════════════╦═══════════════════╗\n");
                sb.append("║    Make:    ║   Model:     ║    Year:   ║    Color:    ║   Mileage:  ║       Price:         ║      VIN:         ║\n");
                sb.append("╠═════════════╩══════════════╩════════════╩══════════════╩═════════════╩══════════════════════╩═══════════════════╣\n");
                sb.append("║").append(make).append(model).append(year).append(color).append(mileage).append(price).append(vin).append(ColorCodes.RESET).append("║").append("\n");
                sb.append("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");
            }
            //Used as the heading for the list
            else if (listOfVehicle.get(0) == vehicle) {
                sb.append("╔═════════════╦══════════════╦════════════╦══════════════╦═════════════╦══════════════════════╦════════════════════╗\n");
                sb.append("║    Make:    ║   Model:     ║    Year:   ║    Color:    ║   Mileage:  ║       Price:         ║      VIN:          ║\n");
                sb.append("╠═════════════╩══════════════╩════════════╩══════════════╩═════════════╩══════════════════════╩════════════════════╣\n");
                sb.append("║").append(make).append(model).append(year).append(color).append(mileage).append(price).append(vin).append(ColorCodes.RESET).append("║").append("\n");

                //Used as the footer for the list
            } else if (listOfVehicle.get(listOfVehicle.size() - 1) == vehicle) {
                sb.append("║").append(make).append(model).append(year).append(color).append(mileage).append(price).append(vin).append(ColorCodes.RESET).append("║").append("\n");
                sb.append("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

                return sb.toString();
            }
            //Used as each individual middle list.
            else {
                sb.append("║").append(make).append(model).append(year).append(color).append(mileage).append(price).append(vin).append(ColorCodes.RESET).append("║").append("\n");
            }

        }


        return sb.toString();
    }

    public int[] getUserRange(String prompt) {
        Scanner scan = new Scanner(System.in);
        boolean userMinNaN = true;
        boolean userMaxNaN = true;
        int minNumber = 0;
        int maxNumber = 0;
        while (userMinNaN) {
            System.out.println("What is the minimum " + prompt + " you are looking for? (Enter 'x' to break)");
            String min = scan.nextLine();
            try {
                //Returns an unsearchable value if the user inputs x
                if (min.equalsIgnoreCase("x")) {
                    return new int[]{-1, -1};
                }
                minNumber = Integer.parseInt(min);
                //checks if the users number is positive
                if (minNumber < 0) {
                    System.out.println("Please enter a positive number");
                } else {
                    userMinNaN = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a number");
            }
        }
        while (userMaxNaN) {
            System.out.println("What is the maximum " + prompt + " you are looking for? (Enter 'x' to break)");
            try {
                String max = scan.nextLine();
                //Returns an unsearchable value if the user inputs x
                if (max.equalsIgnoreCase("x")) {
                    return new int[]{-1, -1};
                }
                maxNumber = Integer.parseInt(max);
                //checks if the users number is positive
                if (maxNumber < 0) {
                    System.out.println("Please enter a positive number");
                } else {
                    userMaxNaN = false;
                }

            } catch (NumberFormatException e) {
                System.out.println("That is not a number");
            }
        }
        return new int[]{minNumber, maxNumber};
    }

    public void processGetByPriceRequest() {
        int[] userRange = getUserRange("price");
        System.out.println(displayVehicles(vehicleDB.findVehiclesByPriceRange(userRange[0], userRange[1])));
    }

    public void processGetByMakeModelRequest() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the make and the model of the car? (Enter 'x' to exit)");
        String userInput = scan.nextLine();
        if (userInput.equalsIgnoreCase("x")) {
            return;
        }
        String[] makeModel = userInput.trim().split(" ");

        System.out.println(displayVehicles(vehicleDB.findVehiclesByMakeModel(makeModel[0], makeModel[1])));
    }

    public void processGetByYearRequest() {
        int[] userRange = getUserRange("year");
        System.out.println(displayVehicles(vehicleDB.findVehiclesByYear(userRange[0], userRange[1])));
    }

    public void processGetByColorRequest() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the color of the car? (Enter 'x' to exit)");
        String color = scan.nextLine();
        if (color.equalsIgnoreCase("x")) {
            return;
        }
        System.out.println(displayVehicles(vehicleDB.findVehicleByColor(color)));
    }

    public void processGetByMileageRequest() {
        int[] userRange = getUserRange("mileage");
        System.out.println(displayVehicles(vehicleDB.findVehicleByMileRange(userRange[0], userRange[1])));
    }

    public void processGetByTypeRequest() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the vehicle type? (car, SUV, Truck, Van, Hypercar) (Enter 'x' to exit)");
        String type = scan.nextLine();
        if (type.equalsIgnoreCase("x")) {
            return;
        }

        System.out.println(displayVehicles(vehicleDB.findVehicleByVehicleType(type)));
    }

    public void processGetAllRequest() {
        System.out.println(displayVehicles(vehicleDB.findAllAvialableVehicles()));
    }

    public String prompt(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the " + prompt + " (Enter 'x' to exit)");
        String input = scan.nextLine();
        if (input.equalsIgnoreCase("x")) {
            return null;
        }
        return input;
    }

    public int convertToInt(String input) {
        Scanner scan = new Scanner(System.in);

        //Checks if the user enters 'x' returns an unsearchable number
        if (input == null) {
            return -1;
        }
        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Not a number, Please enter a number");
                input = scan.nextLine();
                if (input.equalsIgnoreCase("x")) {
                    return -1;
                }
            }
        }
    }

    public double convertToDouble(String input) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Not a number, Please enter a number");
                input = scan.nextLine();
                if (input.equalsIgnoreCase("x")) {
                    return -1;
                }
            }
        }
    }

    public void processAddVehicleRequest() {

        int vin = convertToInt(prompt("vin number"));
        if (vin == -1) {
            return;
        }
        String make = prompt("make");
        if (make == null) {
            return;
        }
        String model = prompt("model");
        if (model == null) {
            return;
        }
        String type = prompt("type");
        if (type == null) {
            return;
        }
        String color = prompt("color");
        if (color == null) {
            return;
        }
        int year = convertToInt(prompt("year"));
        if (year == -1) {
            return;
        }
        int mileage = convertToInt(prompt("mileage"));
        if (mileage == -1) {
            return;
        }
        double price = convertToDouble(prompt("price"));
        if (price == -1) {
            return;
        }

        vehicleDB.addVehicle(vin, year, make, model, type, color, mileage, price);
        System.out.println(ColorCodes.GREEN + "Car added!" + ColorCodes.RESET);

    }

    public void processRemoveVehicleRequest() {

        int vin = convertToInt(prompt("vin number"));
        if (vin == -1) {
            return;
        }

        vehicleDB.removeVehicleByVIN(vin);
        System.out.println(ColorCodes.RED + "Car removed" + ColorCodes.RESET);
    }

    public String salesPrompt(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.println(prompt + " (Enter 'x' to exit)");
        String input = scan.nextLine();
        if (input.equalsIgnoreCase("x")) {
            return null;
        }
        return input;
    }

    public boolean convertToBoolean(String input) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (input.equalsIgnoreCase("yes")) {
                return true;
            } else if (input.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Not Yes or No, Please enter Yes or No");
                input = scan.nextLine();
            }

        }
    }

    public void processSellLeaseVehicle() {
        Vehicle soldVehicle;
        String customerName = salesPrompt("What is the customers full name?");
        if (customerName == null) {
            return; //Returns to main menu
        }

        String customerEmail = salesPrompt("What is the customers email?");
        if (customerEmail == null) {
            return; //Returns to main menu
        }


        int vin = convertToInt(salesPrompt("What is the vin number of th vehicle?"));
        if (vin == -1) {
            System.out.println("Could not find car!");
            return; //Returns to main menu
        } else {

            soldVehicle = vehicleDB.findVehicleByVIN(vin);
        }


        while (true) {
            String sellOrLeaseInput = salesPrompt("Would the customer like to sell or lease a vehicle?  (sell/lease)");
            if (sellOrLeaseInput == null) {
                return;
            } else if (sellOrLeaseInput.equalsIgnoreCase("sell")) {
                boolean isFinancing = convertToBoolean(salesPrompt("Is the customer financing?  (Yes/No)   "));
                salesContractDB.saveSalesContract(new SalesContract(customerName, customerEmail, soldVehicle, isFinancing));
                break;
            } else if (sellOrLeaseInput.equalsIgnoreCase("lease")) {
                leaseContractDB.addLeaseContract(new LeaseContract(customerName, customerEmail, soldVehicle));
                break;
            } else {
                System.out.println("Wrong Input");

            }
        }

        System.out.println("Vehicle Sold!");
    }

    public void processAdminUI() {
        Scanner scan = new Scanner(System.in);
        new AdminUserInterface(leaseContractDB, salesContractDB).logIn(salesPrompt("What is the password?"));

    }
}
