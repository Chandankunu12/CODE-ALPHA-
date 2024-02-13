package CodeAlpha_JavaProgramming_02;

import java.util.*;

public class OnlineQuizPlatform_SimpleBankingApplication {

	    private static double balance = 0;
	    private static Scanner sc = new Scanner(System.in);

	    public static void main(String[] args) {
	        boolean isRunning = true;

	        while (isRunning) {
	            System.out.println("Welcome to Simple Banking Application");
	            System.out.println("1. Deposit");
	            System.out.println("2. Withdraw");
	            System.out.println("3. Check Balance");
	            System.out.println("4. Exit");
	            System.out.print("Enter your choice: ");

	            int choice = sc.nextInt();

	            switch (choice) {
	                case 1:
	                    deposit();
	                    break;
	                case 2:
	                    withdraw();
	                    break;
	                case 3:
	                    checkBalance();
	                    break;
	                case 4:
	                    isRunning = false;
	                    break;
	                default:
	                    System.out.println("Invalid choice, please try again.");
	            }
	        }

	        System.out.println("Thank you for using Simple Banking Application.");
	        sc.close();
	    }

	    private static void deposit() {
	        System.out.print("Enter the amount to deposit: ");
	        double amount = sc.nextDouble();
	        if (amount > 0) {
	            balance += amount;
	            System.out.println("Deposit successful. Current balance: " + balance);
	        } else {
	            System.out.println("Invalid amount. Deposit failed.");
	        }
	    }

	    private static void withdraw() {
	        System.out.print("Enter the amount to withdraw: ");
	        double amount = sc.nextDouble();
	        if (amount > 0 && balance >= amount) {
	            balance -= amount;
	            System.out.println("Withdrawal successful. Current balance: " + balance);
	        } else {
	            System.out.println("Invalid amount or insufficient funds. Withdrawal failed.");
	        }
	    }

	    private static void checkBalance() {
	        System.out.println("Current balance: " + balance);
	    }
	}
