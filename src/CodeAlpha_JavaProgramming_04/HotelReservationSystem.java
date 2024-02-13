package CodeAlpha_JavaProgramming_04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;


class Room{
	private int numOfRooms;
   	private String categories;
   	private boolean isAvailable;
   	private double price;
   	private double totalPrice;
   	Room(){}
    Room(int numOfRooms, String categories,double price){
        this.numOfRooms = numOfRooms;
        this.categories =categories;
        this.price=price;
        this.isAvailable = true;
    }
    public double getPrice(){
        return price;
    }
    public void totalPrice(int Days,double price)
    {
        this.totalPrice = price*Days;
    }
    
    public double getTotalPrice(){
        return this.totalPrice;
    }
     public int getRoomNumber() {
        return numOfRooms;
    }

    public String getCategory() {
        return categories;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
 	public boolean bookRoom(int days) {
 		System.out.println("isAvailable "+isAvailable);
 		if (isAvailable) {
 			totalPrice(days, this.price);
 			isAvailable = false;
 			return true;
 		} else {
 			System.out.println("Room " + getRoomNumber() + " is not available for booking.");
 			isAvailable = false;
 				return false;
    }
}

    public void releaseRoom() {
        this.isAvailable = true;
    }

    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
    	if (obj == null || getClass() != obj.getClass()) return false;

    	Room otherRoom = (Room) obj;
    	return this.getRoomNumber() == otherRoom.getRoomNumber();
    }

    @Override
    public int hashCode() {
    	return Objects.hash(getRoomNumber());
    }
}

class Hotel {
    private Map<String, List<Room>> roomsByCategory;
    Hotel(){
     this.roomsByCategory = new HashMap<>();
    }
    public void addRoom(int roomNum,String category,double price){
        Room room = new Room(roomNum,category,price);
        roomsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(room);
    }
    public List<Room> getAvaRooms(String category, Room reservedRoom) {
    List<Room> availableRooms = roomsByCategory.getOrDefault(category, new ArrayList<>());

    availableRooms = availableRooms.stream()
            .filter(room -> room != reservedRoom && room.isAvailable())
            .collect(Collectors.toList());

    return availableRooms;
}
    public void removeRoom(Room room) {
        roomsByCategory.get(room.getCategory()).remove(room);
    }
    public double getPrice(int roomNumber, String category) {
        boolean roomFound = false;

        for (List<Room> rooms : roomsByCategory.values()) {
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    roomFound = true;
                    if (room.getCategory().equalsIgnoreCase(category)) {
                        return room.getPrice();
                    } else {
                        System.out.println("Invalid room category for room number " + roomNumber);
                        return 0;
                    }
                }
            }
        }

        if (!roomFound) {
            System.out.println("Room not found for room number " + roomNumber);
            for (List<Room> rooms : roomsByCategory.values()) {
                for (Room room : rooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        room.setIsAvailable(false);
                        break;
                    }
                }
            }
        }
       return 0;
    }
}
class Reservation {
	    private Room room;
	    private Hotel hotel;
	    private String person;
	    private String checkInDate;
	    private String checkOutDate;

	    public Reservation(Hotel hotel) {
	        this.hotel = hotel;
	    }

	    public Room getReservedRoom() {
	        return room;
	    }

	    public void makeReservation(Room room, int numOfDays, String checkInDate, String checkOutDate, String person) {
	        this.room = room;
	        this.checkInDate = checkInDate;
	        this.checkOutDate = checkOutDate;
	        this.person = person;

	        if (room.getPrice() == 0) {
	            System.out.println("Invalid room or room not available for reservation.");
	            return;
	        }

	        room.bookRoom(numOfDays);
	        hotel.removeRoom(room);
	        displayDetails();
	    }

	    public double calculateTotalPrice(int roomNumber, String cat, int numOfDays) {
	        double roomPrice = hotel.getPrice(roomNumber, cat);
	        return roomPrice * numOfDays;
	    }

	    public void displayDetails() {
	        System.out.println("------------Reservation Done------------");
	        System.out.println("Reservation Details:");
	        System.out.println("Guest Name: " + person);
	        System.out.println("Room Number: " + room.getRoomNumber());
	        System.out.println("Category: " + room.getCategory());
	        System.out.println("Check-In Date: " + checkInDate);
	        System.out.println("Check-Out Date: " + checkOutDate);
	        System.out.println("Total Price: " + room.getTotalPrice());
	        System.out.println("----------------------------------------------");
	    }
	}

public class HotelReservationSystem {

	public static void main(String[] args) {
		 	Hotel hotel = new Hotel();
	        try (Scanner input = new Scanner(System.in)) {
	            Reservation reservation = new Reservation(hotel);
	            hotel.addRoom(101, "Standard", 50);
	            hotel.addRoom(102, "Standard", 50);
	            hotel.addRoom(201, "Deluxe", 80);
	            hotel.addRoom(202, "Deluxe", 80);
	            hotel.addRoom(301, "Standard", 50);
	            hotel.addRoom(302, "Standard", 50);
	            hotel.addRoom(401, "Deluxe", 80);
	            hotel.addRoom(402, "Deluxe", 80);

	            System.out.println("-----------------Welcome to Our Hotel--------------------");
	            System.out.println("Enter your Name please:");
	            String userName = input.nextLine();

	            while (true) {
	                System.out.println("(Enter choice number)\n1-search for available rooms, available rooms\n2-Make reservation\n3-Exit ");
	                int choice = input.nextInt();

	                switch (choice) {
	                    case 1:
	                    System.out.println("Enter room category (Standard/Deluxe): ");
	                    String category = input.next();
	                    List<Room> availableRooms = hotel.getAvaRooms(category, reservation.getReservedRoom());
	                    if (availableRooms.isEmpty()) {
	                        System.out.println("No available rooms in the selected category.");
	                    } else {
	                        System.out.println("Available rooms in category " + category + ":");
	                        for (Room room : availableRooms) {
	                            System.out.println("Room Number: " + room.getRoomNumber());
	                        }
	                    }
	                    break;
	                    case 2:
	                        System.out.println("Enter room number for reservation: ");
	                        int roomNumber = input.nextInt();
	                        System.out.println("Enter room category (Standard/Deluxe): ");
	                        String cat = input.next();
	                        Room room = new Room(roomNumber, cat, 0);
	                        if (hotel.getAvaRooms(cat,room)!= null) {
	                            System.out.println("Enter number of days: ");
	                            int numOfDays = input.nextInt();
	                            input.nextLine();  
	                            System.out.println("Enter Check-In date: ");
	                            String checkInDate = input.nextLine();
	                            System.out.println("Enter Check-Out date: ");
	                            String checkOutDate = input.nextLine();

	                            Room selectedRoom = new Room(roomNumber, cat, hotel.getPrice(roomNumber, cat));
	                            reservation.makeReservation(selectedRoom, numOfDays, checkInDate, checkOutDate, userName);                
	                        } else {
	                            System.out.println("Invalid room number or room not available for reservation.");
	                        }
	                    break;
	                    case 3:
	                        System.exit(0);
	                    default:
	                        System.out.println("Invalid choice. Please enter a valid choice.");
	                    break;
	                }
	           }
	      }
	}
}
