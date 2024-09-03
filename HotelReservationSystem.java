import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String roomType;
    private boolean isBooked;

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        this.isBooked = true;
    }

    public void cancelBooking() {
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - " + (isBooked ? "Booked" : "Available");
    }
}

class Reservation {
    private String guestName;
    private Room room;

    public Reservation(String guestName, Room room) {
        this.guestName = guestName;
        this.room = room;
        room.bookRoom();
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Reservation: " + guestName + " - " + room;
    }
}

class Hotel {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Single"));
        rooms.add(new Room(201, "Double"));
        rooms.add(new Room(202, "Double"));
        rooms.add(new Room(301, "Suite"));
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room);
            }
        }
    }

    public void makeReservation(String guestName, int roomNumber) {
        Room room = findRoomByNumber(roomNumber);
        if (room != null && !room.isBooked()) {
            Reservation reservation = new Reservation(guestName, room);
            reservations.add(reservation);
            System.out.println("Reservation successful: " + reservation);
        } else {
            System.out.println("Room not available or invalid room number.");
        }
    }

    public void cancelReservation(String guestName, int roomNumber) {
        Reservation reservation = findReservation(guestName, roomNumber);
        if (reservation != null) {
            reservation.getRoom().cancelBooking();
            reservations.remove(reservation);
            System.out.println("Reservation canceled: " + reservation);
        } else {
            System.out.println("Reservation not found.");
        }
    }

    public void displayReservations() {
        System.out.println("Current Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private Reservation findReservation(String guestName, int roomNumber) {
        for (Reservation reservation : reservations) {
            if (reservation.getGuestName().equals(guestName) &&
                reservation.getRoom().getRoomNumber() == roomNumber) {
                return reservation;
            }
        }
        return null;
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();
        boolean running = true;

        while (running) {
            System.out.println("Hotel Reservation System");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    hotel.displayAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    hotel.makeReservation(guestName, roomNumber);
                    break;
                case 3:
                    System.out.print("Enter guest name: ");
                    guestName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    roomNumber = scanner.nextInt();
                    hotel.cancelReservation(guestName, roomNumber);
                    break;
                case 4:
                    hotel.displayReservations();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
