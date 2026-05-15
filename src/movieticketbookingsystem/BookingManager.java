package movieticketbookingsystem;

import java.io.*;
import java.util.*;

public class BookingManager {
    private final List<Booking> bookings = new ArrayList<>();
    private final String filePath;

    public BookingManager(String filePath) {
        this.filePath = filePath;
        loadBookings();
    }

    public List<Booking> getBookings() { return bookings; }

    public boolean addBooking(Booking booking) {
        if (findById(booking.getBookingId()) != null) return false;
        if (!isSeatAvailable(booking.getMovieTitle(), booking.getShowDate(), booking.getShowTime(), booking.getHallNumber(), booking.getSeatNumber())) {
            return false;
        }
        bookings.add(booking);
        saveBookings();
        return true;
    }

    public Booking findById(String bookingId) {
        for (Booking b : bookings) if (b.getBookingId().equalsIgnoreCase(bookingId)) return b;
        return null;
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = findById(bookingId);
        if (booking == null) return false;
        booking.setBookingStatus("Cancelled");
        saveBookings();
        return true;
    }

    public boolean isSeatAvailable(String movieTitle, String date, String time, String hall, String seat) {
        for (Booking b : bookings) {
            if (b.getMovieTitle().equalsIgnoreCase(movieTitle)
                    && b.getShowDate().equalsIgnoreCase(date)
                    && b.getShowTime().equalsIgnoreCase(time)
                    && b.getHallNumber().equalsIgnoreCase(hall)
                    && b.getSeatNumber().equalsIgnoreCase(seat)
                    && b.getBookingStatus().equalsIgnoreCase("Booked")) {
                return false;
            }
        }
        return true;
    }

    public int getTotalBookedTickets() {
        int count = 0;
        for (Booking b : bookings) if (b.getBookingStatus().equalsIgnoreCase("Booked")) count++;
        return count;
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Booking b : bookings) if (b.getBookingStatus().equalsIgnoreCase("Booked")) total += b.getTicketPrice();
        return total;
    }

    public final void loadBookings() {
        bookings.clear();
        File file = new File(filePath);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length == 10) bookings.add(new Booking(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], Double.parseDouble(p[8]), p[9]));
            }
        } catch (IOException | NumberFormatException ignored) {}
    }

    public void saveBookings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Booking b : bookings) {
                writer.write(b.toFileString());
                writer.newLine();
            }
        } catch (IOException ignored) {}
    }
}
