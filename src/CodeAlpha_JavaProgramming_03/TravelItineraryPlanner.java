package CodeAlpha_JavaProgramming_03;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Destination {
    String name;
    String startDate;
    String endDate;
    double budget;
    String preferences;

    Destination(String name, String startDate, String endDate, String preferences,double budget) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget=budget;
        this.preferences = preferences;
    }
   public String fetchWeatherInfo(String name) {
        try {
            String apiKey = "4181f4e5bf68163db9507d6301013036";
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + name + "&appid=" + apiKey;

            @SuppressWarnings("deprecation")
			URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
               String output=response.toString();
                reader.close();
                return output;
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

 public static double getTemperature(String json) {
    int startIndex = json.indexOf("\"temp\":") + 7;
    int endIndex = json.indexOf(",", startIndex);
    String temperatureString = json.substring(startIndex, endIndex);

    double temperatureCelsius = Double.parseDouble(temperatureString)-273.15;
    return temperatureCelsius;
}
    public static String getWeatherDescription(String json) {
    int weatherStartIndex = json.indexOf("\"weather\":") + 10;
    int weatherEndIndex = json.indexOf("]", weatherStartIndex) + 1;
    String weatherArray = json.substring(weatherStartIndex, weatherEndIndex);

    int mainStartIndex = weatherArray.indexOf("\"main\":") + 8;
    int mainEndIndex = weatherArray.indexOf("\"", mainStartIndex);
    String mainWeather = weatherArray.substring(mainStartIndex, mainEndIndex);

    return mainWeather;
    }
}

class TravelPlan {
    String userName;
    List<Destination> destinations;

    public TravelPlan(String userName) {
        this.userName = userName;
        this.destinations = new ArrayList<>();
    }

    public void addDestination(String name, String startDate, String endDate, String preferences,double budget) {
        Destination destination = new Destination(name, startDate, endDate, preferences,budget);
        destinations.add(destination);
    }
    public double calculateTotalBudget() {
        double totalBudget = 0.0;
        for (Destination destination : destinations) {
            double destinationBudget = destination.budget; 
            totalBudget += destinationBudget;
        }
        return totalBudget;
    }
    public void displayPlan() {
        System.out.println("---------------TravelPlan-----------------------");
        System.out.println("Travel Itinerary for " + userName + ":");
        for (Destination destination : destinations) {

            System.out.println("Destination: " + destination.name);
            System.out.println("Start Date: " + destination.startDate);
            System.out.println("End Date: " + destination.endDate);
            System.out.println("Preferences: " + destination.preferences);
            System.out.println("Destination budget: " + destination.budget);
            String weatherInfo = destination.fetchWeatherInfo(destination.name);
            double temperature = Destination.getTemperature(weatherInfo);
            String weatherDescription = Destination.getWeatherDescription(weatherInfo);

            System.out.println("Temperature: " + temperature + " C");
            System.out.println("Weather Description: " + weatherDescription);
            System.out.println("---------------------");
        }
    }
}

public class TravelItineraryPlanner {
    public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {
        System.out.print("Enter your name: ");
        String userName = input.nextLine();

        TravelPlan travelPlanner = new TravelPlan(userName);
       
        while (true) {
            System.out.print("Enter destination name (or 'exit' to finish): ");
            String destinationName = input.nextLine();

            if (destinationName.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter start date (e.g., YYYY-MM-DD): ");
            String startDate = input.nextLine();

            System.out.print("Enter end date (e.g., YYYY-MM-DD): ");
            String endDate = input.nextLine();

            System.out.print("Enter preferences for " + destinationName + ": ");
            String preferences = input.nextLine();
            System.out.print("Enter Budget for :" + destinationName + ": ");
            double budget = input.nextDouble();
            input.nextLine();
            travelPlanner.addDestination(destinationName, startDate, endDate, preferences,budget);
               }

        travelPlanner.displayPlan();
        double totalBudget = travelPlanner.calculateTotalBudget();
        System.out.println("\nTotal Budget for " + userName + ": $" + totalBudget);

    }

        System.out.println("Thank you for using the travel planner!");
    }
}