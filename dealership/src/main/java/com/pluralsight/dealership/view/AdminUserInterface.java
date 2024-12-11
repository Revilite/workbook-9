package com.pluralsight.dealership.view;


import com.pluralsight.dealership.controller.contracts.LeaseContractDAOMysqlImpl;
import com.pluralsight.dealership.controller.contracts.SalesContractDAOMysqlImpl;
import com.pluralsight.dealership.model.contract.Contract;
import com.pluralsight.dealership.model.contract.LeaseContract;
import com.pluralsight.dealership.model.contract.SalesContract;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AdminUserInterface {
    //Tried to set .env file but can figure out how to import right class
    LeaseContractDAOMysqlImpl leaseContractDB;
    SalesContractDAOMysqlImpl salesContractDB;
    List<Contract> contractList;

    public AdminUserInterface(LeaseContractDAOMysqlImpl leaseContractDB, SalesContractDAOMysqlImpl salesContractDB) {
        this.leaseContractDB = leaseContractDB;
        this.salesContractDB = salesContractDB;
        contractList = new ArrayList<>();

        contractList.addAll(salesContractDB.findAllSalesContracts());
        contractList.addAll(leaseContractDB.findAllLeaseContracts());

        contractList.sort(Comparator.comparing(Contract::getDate).reversed());
    }

    public void logIn(String password) {
        //Set your password in configurations when running the file
        if (password.equalsIgnoreCase("1234")) {
            displayContracts();
        } else {
            System.out.println("Incorrect Password");
        }
    }

    public void displayContracts() {
        Scanner scan = new Scanner(System.in);


        System.out.println("""
                ━━━━━━━━━━━━━☛ Contracts ☚━━━━━━━━━━━━━━
                
                 Display all Contract               (1)
                 Display last 10 Contracts          (2)
                """);
        String userInput = scan.nextLine();

        switch (userInput) {
            case ("1"):
                for (Contract contract : contractList) {
                    System.out.print(displayContractList(contract));
                }
                break;
            case ("2"):
                for(int i = 0; i < 10; i++){
                    try{
                    System.out.print(displayContractList(contractList.get(i)));
                    }catch (IndexOutOfBoundsException e){
                       
                    }
                }
                break;
            default:
                System.out.println("Incorrect answer");
        }

    }

    public String displayContractList(Contract contract) {
        StringBuilder sb = new StringBuilder();


        //Contract variables
        String name = contract.getCustomerName();
        String date = contract.getDate();
        String email = contract.getCustomerEmail();
        double monthlyPayment = contract.getMonthlyPayment();
        double totalAmount = contract.getTotalPrice();
        int vin = contract.getVehicleSold().getVin();

        String heading = "  Type    Date          Name             Email                    Sales Tax/Ending Value   Recording Fee / Lease Fee     Total Amount    Finance Status   Monthly Payment         Vin\n";

        if (contract instanceof SalesContract) {
            double salesTax = ((SalesContract) contract).getSalesTaxAmount();
            double recordingFee = ((SalesContract) contract).getRecordingFee();
            boolean isFinancing = ((SalesContract) contract).isFinancing();
            String salesFormat = String.format("╎Sale   %-10s     %-15s    %-30s   %-20.2f   %-20.2f     %-20.2f    %-3s        %-12.2f     %-10d  ╎\n", date, name, email, salesTax, recordingFee, totalAmount, isFinancing ? "YES" : "NO", monthlyPayment, vin);

            if (contractList.size() == 1) {
                sb.append("╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮\n");
                sb.append(salesFormat);
                sb.append("╰──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╯\n");
            } else if (contractList.get(0) == contract) {
                sb.append(heading);
                sb.append("╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮\n");
                sb.append(salesFormat);
            } else if (contractList.get(contractList.size() - 1) == contract) {
                sb.append(salesFormat);
                sb.append("╰──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╯\n");
            } else {
                sb.append(salesFormat);
            }
        } else if (contract instanceof LeaseContract) {
            double expectedEndingValue = ((LeaseContract) contract).getExpectedEndingValue();
            double leaseFee = ((LeaseContract) contract).getLeaseFee();
            String leaseFormat = String.format("╎Lease  %-10s     %-15s    %-30s   %-20.2f   %-20.2f     %-20.2f    N/A        %-12.2f     %-10d  ╎\n", date, name, email, expectedEndingValue, leaseFee, totalAmount, monthlyPayment, vin);

            if (contractList.size() == 1) {
                sb.append("╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮\n");
                sb.append(leaseFormat);
                sb.append("╰──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╯\n");
            } else if (contractList.get(0) == contract) {
                sb.append(heading);
                sb.append("╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮\n");
                sb.append(leaseFormat);
            } else if (contractList.get(contractList.size() - 1) == contract) {
                sb.append(leaseFormat);
                sb.append("╰──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╯\n");
            } else {
                sb.append(leaseFormat);
            }

        }

        return sb.toString();


    }
}