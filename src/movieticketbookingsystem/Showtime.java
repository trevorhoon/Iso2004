package movieticketbookingsystem;

public class Showtime {
    private String showtimeId;
    private String movieId;
    private String movieTitle;
    private String showDate;
    private String showTime;
    private String hallNumber;
    private double ticketPrice;

    public Showtime(String showtimeId, String movieId, String movieTitle, String showDate, String showTime, String hallNumber, double ticketPrice) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.showDate = showDate;
        this.showTime = showTime;
        this.hallNumber = hallNumber;
        this.ticketPrice = ticketPrice;
    }

    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }
    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getShowDate() { return showDate; }
    public void setShowDate(String showDate) { this.showDate = showDate; }
    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }
    public String getHallNumber() { return hallNumber; }
    public void setHallNumber(String hallNumber) { this.hallNumber = hallNumber; }
    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public String toFileString() {
        return showtimeId + "|" + movieId + "|" + movieTitle + "|" + showDate + "|" + showTime + "|" + hallNumber + "|" + String.format("%.2f", ticketPrice);
    }

    @Override
    public String toString() {
        return showtimeId + " - " + movieTitle + " (" + showDate + " " + showTime + ")";
    }
}
