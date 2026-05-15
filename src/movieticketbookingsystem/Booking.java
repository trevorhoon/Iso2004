package movieticketbookingsystem;

public class Booking {
    private String bookingId;
    private String customerName;
    private String phoneNumber;
    private String movieTitle;
    private String showDate;
    private String showTime;
    private String hallNumber;
    private String seatNumber;
    private double ticketPrice;
    private String bookingStatus;

    public Booking(String bookingId, String customerName, String phoneNumber, String movieTitle, String showDate,
                   String showTime, String hallNumber, String seatNumber, double ticketPrice, String bookingStatus) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.movieTitle = movieTitle;
        this.showDate = showDate;
        this.showTime = showTime;
        this.hallNumber = hallNumber;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() { return bookingId; }
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getMovieTitle() { return movieTitle; }
    public String getShowDate() { return showDate; }
    public String getShowTime() { return showTime; }
    public String getHallNumber() { return hallNumber; }
    public String getSeatNumber() { return seatNumber; }
    public double getTicketPrice() { return ticketPrice; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String toFileString() {
        return bookingId + "|" + customerName + "|" + phoneNumber + "|" + movieTitle + "|" + showDate + "|"
                + showTime + "|" + hallNumber + "|" + seatNumber + "|" + String.format("%.2f", ticketPrice) + "|" + bookingStatus;
    }
}
