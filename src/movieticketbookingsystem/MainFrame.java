package movieticketbookingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class MainFrame extends JFrame {
    private final MovieManager movieManager = new MovieManager("movies.txt");
    private final ShowtimeManager showtimeManager = new ShowtimeManager("showtimes.txt");
    private final BookingManager bookingManager = new BookingManager("bookings.txt");
    private final DecimalFormat moneyFormat = new DecimalFormat("0.00");

    private JLabel lblTotalMovies, lblTotalShowtimes, lblTotalBookings, lblTotalRevenue;
    private JTextField txtMovieId, txtMovieTitle, txtDuration, txtLanguage, txtRating, txtShowtimeId, txtShowDate, txtShowTime, txtHallNumber, txtTicketPrice, txtBookingId, txtCustomerName, txtPhone;
    private JComboBox<String> cmbGenre, cmbSeatNumber;
    private JComboBox<Movie> cmbMovie;
    private JComboBox<Showtime> cmbShowtime;
    private JTable tblMovies, tblShowtimes, tblBookings;

    public MainFrame() {
        setTitle("Movie Ticket Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setLocationRelativeTo(null);
        initUI();
        refreshAllData();
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Dashboard", createDashboardPanel());
        tabs.add("Movie Management", createMoviePanel());
        tabs.add("Showtime Management", createShowtimePanel());
        tabs.add("Ticket Booking", createBookingPanel());
        tabs.add("Booking History", createBookingHistoryPanel());
        add(tabs);
    }
    private JPanel createDashboardPanel() { JPanel p=new JPanel(new GridLayout(2,2,10,10)); p.setBorder(BorderFactory.createEmptyBorder(30,30,30,30)); lblTotalMovies=new JLabel(); lblTotalShowtimes=new JLabel(); lblTotalBookings=new JLabel(); lblTotalRevenue=new JLabel(); p.add(card("Total Movies",lblTotalMovies)); p.add(card("Total Showtimes",lblTotalShowtimes)); p.add(card("Total Bookings",lblTotalBookings)); p.add(card("Total Revenue",lblTotalRevenue)); return p; }
    private JPanel card(String t,JLabel v){ JPanel p=new JPanel(new BorderLayout()); p.setBorder(BorderFactory.createTitledBorder(t)); v.setHorizontalAlignment(SwingConstants.CENTER); v.setFont(new Font("Arial",Font.BOLD,24)); p.add(v,BorderLayout.CENTER); return p; }

    private JPanel createMoviePanel() { /* concise for brevity */
        JPanel panel = new JPanel(new BorderLayout(10,10)); JPanel form = new JPanel(new GridLayout(7,2,5,5)); txtMovieId=new JTextField(); txtMovieTitle=new JTextField(); cmbGenre=new JComboBox<>(new String[]{"Action","Comedy","Drama","Horror","Romance","Sci-Fi","Animation"}); txtDuration=new JTextField(); txtLanguage=new JTextField(); txtRating=new JTextField();
        form.add(new JLabel("Movie ID")); form.add(txtMovieId); form.add(new JLabel("Movie Title")); form.add(txtMovieTitle); form.add(new JLabel("Genre")); form.add(cmbGenre); form.add(new JLabel("Duration")); form.add(txtDuration); form.add(new JLabel("Language")); form.add(txtLanguage); form.add(new JLabel("Rating")); form.add(txtRating);
        JPanel btns = new JPanel(); JButton btnAddMovie=new JButton("Add Movie"); JButton btnUpdateMovie=new JButton("Update Movie"); JButton btnDeleteMovie=new JButton("Delete Movie"); JButton btnSearchMovie=new JButton("Search Movie"); JButton btnClear=new JButton("Clear"); btns.add(btnAddMovie); btns.add(btnUpdateMovie); btns.add(btnDeleteMovie); btns.add(btnSearchMovie); btns.add(btnClear);
        tblMovies=new JTable(new DefaultTableModel(new Object[]{"Movie ID","Title","Genre","Duration","Language","Rating"},0));
        btnAddMovie.addActionListener(e->addMovie()); btnUpdateMovie.addActionListener(e->updateMovie()); btnDeleteMovie.addActionListener(e->deleteMovie()); btnSearchMovie.addActionListener(e->searchMovie()); btnClear.addActionListener(e->clearMovieFields());
        panel.add(form,BorderLayout.NORTH); panel.add(new JScrollPane(tblMovies),BorderLayout.CENTER); panel.add(btns,BorderLayout.SOUTH); return panel;
    }

    private JPanel createShowtimePanel() { JPanel panel=new JPanel(new BorderLayout(10,10)); JPanel form=new JPanel(new GridLayout(8,2,5,5)); txtShowtimeId=new JTextField(); cmbMovie=new JComboBox<>(); txtShowDate=new JTextField(); txtShowTime=new JTextField(); txtHallNumber=new JTextField(); txtTicketPrice=new JTextField(); form.add(new JLabel("Showtime ID")); form.add(txtShowtimeId); form.add(new JLabel("Movie")); form.add(cmbMovie); form.add(new JLabel("Show Date (YYYY-MM-DD)")); form.add(txtShowDate); form.add(new JLabel("Show Time (HH:mm)")); form.add(txtShowTime); form.add(new JLabel("Hall Number")); form.add(txtHallNumber); form.add(new JLabel("Ticket Price")); form.add(txtTicketPrice);
        JPanel btns=new JPanel(); JButton btnAddShowtime=new JButton("Add Showtime"); JButton btnUpdateShowtime=new JButton("Update Showtime"); JButton btnDeleteShowtime=new JButton("Delete Showtime"); JButton btnClear=new JButton("Clear"); btns.add(btnAddShowtime);btns.add(btnUpdateShowtime);btns.add(btnDeleteShowtime);btns.add(btnClear);
        tblShowtimes=new JTable(new DefaultTableModel(new Object[]{"Showtime ID","Movie ID","Movie Title","Date","Time","Hall","Price"},0)); btnAddShowtime.addActionListener(e->addShowtime()); btnUpdateShowtime.addActionListener(e->updateShowtime()); btnDeleteShowtime.addActionListener(e->deleteShowtime()); btnClear.addActionListener(e->clearShowtimeFields());
        panel.add(form,BorderLayout.NORTH); panel.add(new JScrollPane(tblShowtimes),BorderLayout.CENTER); panel.add(btns,BorderLayout.SOUTH); return panel; }

    private JPanel createBookingPanel(){ JPanel panel=new JPanel(new BorderLayout(10,10)); JPanel form=new JPanel(new GridLayout(8,2,5,5)); txtBookingId=new JTextField(); txtCustomerName=new JTextField(); txtPhone=new JTextField(); cmbShowtime=new JComboBox<>(); cmbSeatNumber=new JComboBox<>(new String[]{"A1","A2","A3","A4","A5","B1","B2","B3","B4","B5"}); JLabel price=new JLabel("0.00");
        form.add(new JLabel("Booking ID")); form.add(txtBookingId); form.add(new JLabel("Customer Name")); form.add(txtCustomerName); form.add(new JLabel("Phone")); form.add(txtPhone); form.add(new JLabel("Showtime")); form.add(cmbShowtime); form.add(new JLabel("Seat Number")); form.add(cmbSeatNumber); form.add(new JLabel("Ticket Price")); form.add(price);
        cmbShowtime.addActionListener(e->{ Showtime s=(Showtime)cmbShowtime.getSelectedItem(); if(s!=null) price.setText(moneyFormat.format(s.getTicketPrice())); });
        JPanel btns=new JPanel(); JButton btnBookTicket=new JButton("Book Ticket"); JButton btnCancelTicket=new JButton("Cancel Ticket"); JButton btnClear=new JButton("Clear"); btns.add(btnBookTicket);btns.add(btnCancelTicket);btns.add(btnClear);
        btnBookTicket.addActionListener(e->bookTicket()); btnCancelTicket.addActionListener(e->cancelTicket()); btnClear.addActionListener(e->clearBookingFields());
        panel.add(form,BorderLayout.NORTH); panel.add(btns,BorderLayout.SOUTH); return panel; }

    private JPanel createBookingHistoryPanel(){ JPanel p=new JPanel(new BorderLayout()); tblBookings=new JTable(new DefaultTableModel(new Object[]{"Booking ID","Customer","Phone","Movie","Date","Time","Hall","Seat","Price","Status"},0)); p.add(new JScrollPane(tblBookings),BorderLayout.CENTER); return p; }

    private void addMovie(){ if(txtMovieId.getText().trim().isEmpty()||txtMovieTitle.getText().trim().isEmpty()){msg("Movie ID and Title are required."); return;} boolean ok=movieManager.addMovie(new Movie(txtMovieId.getText().trim(),txtMovieTitle.getText().trim(),cmbGenre.getSelectedItem().toString(),txtDuration.getText().trim(),txtLanguage.getText().trim(),txtRating.getText().trim())); msg(ok?"Movie added successfully.":"Duplicate movie ID."); if(ok){clearMovieFields();refreshAllData();}}
    private void updateMovie(){ boolean ok=movieManager.updateMovie(new Movie(txtMovieId.getText().trim(),txtMovieTitle.getText().trim(),cmbGenre.getSelectedItem().toString(),txtDuration.getText().trim(),txtLanguage.getText().trim(),txtRating.getText().trim())); msg(ok?"Movie updated successfully.":"Movie not found."); if(ok){clearMovieFields();refreshAllData();}}
    private void deleteMovie(){ boolean ok=movieManager.deleteMovie(txtMovieId.getText().trim()); msg(ok?"Movie deleted successfully.":"Movie not found."); if(ok){clearMovieFields();refreshAllData();}}
    private void searchMovie(){ List<Movie> data=movieManager.searchByIdOrTitle(txtMovieId.getText().trim().isEmpty()?txtMovieTitle.getText().trim():txtMovieId.getText().trim()); fillMovieTable(data); }
    private void addShowtime(){ if(txtShowtimeId.getText().trim().isEmpty()||txtShowDate.getText().trim().isEmpty()||txtShowTime.getText().trim().isEmpty()||txtHallNumber.getText().trim().isEmpty()){msg("Showtime ID, Date, Time and Hall are required.");return;} try{ double pr=Double.parseDouble(txtTicketPrice.getText().trim()); if(pr<=0){msg("Price must be positive.");return;} Movie m=(Movie)cmbMovie.getSelectedItem(); if(m==null){msg("Please add movie first.");return;} boolean ok=showtimeManager.addShowtime(new Showtime(txtShowtimeId.getText().trim(),m.getMovieId(),m.getTitle(),txtShowDate.getText().trim(),txtShowTime.getText().trim(),txtHallNumber.getText().trim(),pr)); msg(ok?"Showtime added successfully.":"Duplicate showtime ID."); if(ok){clearShowtimeFields();refreshAllData();}}catch(NumberFormatException ex){msg("Invalid ticket price.");}}
    private void updateShowtime(){ try{ double pr=Double.parseDouble(txtTicketPrice.getText().trim()); Movie m=(Movie)cmbMovie.getSelectedItem(); if(m==null)return; boolean ok=showtimeManager.updateShowtime(new Showtime(txtShowtimeId.getText().trim(),m.getMovieId(),m.getTitle(),txtShowDate.getText().trim(),txtShowTime.getText().trim(),txtHallNumber.getText().trim(),pr)); msg(ok?"Showtime updated successfully.":"Showtime not found."); if(ok){clearShowtimeFields();refreshAllData();}}catch(Exception ex){msg("Invalid input.");}}
    private void deleteShowtime(){ boolean ok=showtimeManager.deleteShowtime(txtShowtimeId.getText().trim()); msg(ok?"Showtime deleted successfully.":"Showtime not found."); if(ok){clearShowtimeFields();refreshAllData();}}
    private void bookTicket(){ if(txtBookingId.getText().trim().isEmpty()||txtCustomerName.getText().trim().isEmpty()||txtPhone.getText().trim().isEmpty()){msg("Booking ID, Customer Name and Phone are required.");return;} Showtime s=(Showtime)cmbShowtime.getSelectedItem(); if(s==null){msg("Please select showtime.");return;} String seat=cmbSeatNumber.getSelectedItem().toString(); Booking b=new Booking(txtBookingId.getText().trim(),txtCustomerName.getText().trim(),txtPhone.getText().trim(),s.getMovieTitle(),s.getShowDate(),s.getShowTime(),s.getHallNumber(),seat,s.getTicketPrice(),"Booked"); boolean ok=bookingManager.addBooking(b); msg(ok?"Ticket booked successfully.":"Duplicate booking ID or seat already booked."); if(ok){clearBookingFields();refreshAllData();}}
    private void cancelTicket(){ String id=txtBookingId.getText().trim(); if(id.isEmpty()){msg("Enter Booking ID to cancel.");return;} boolean ok=bookingManager.cancelBooking(id); msg(ok?"Booking cancelled successfully.":"Booking not found."); if(ok){clearBookingFields();refreshAllData();}}

    private void refreshAllData(){ fillMovieTable(movieManager.getMovies()); fillShowtimeTable(); fillBookingTable(); cmbMovie.removeAllItems(); for(Movie m:movieManager.getMovies()) cmbMovie.addItem(m); cmbShowtime.removeAllItems(); for(Showtime s:showtimeManager.getShowtimes()) cmbShowtime.addItem(s); lblTotalMovies.setText(String.valueOf(movieManager.getMovies().size())); lblTotalShowtimes.setText(String.valueOf(showtimeManager.getShowtimes().size())); lblTotalBookings.setText(String.valueOf(bookingManager.getTotalBookedTickets())); lblTotalRevenue.setText(moneyFormat.format(bookingManager.getTotalRevenue())); }
    private void fillMovieTable(List<Movie> movies){ DefaultTableModel model=(DefaultTableModel)tblMovies.getModel(); model.setRowCount(0); for(Movie m:movies) model.addRow(new Object[]{m.getMovieId(),m.getTitle(),m.getGenre(),m.getDuration(),m.getLanguage(),m.getRating()}); }
    private void fillShowtimeTable(){ DefaultTableModel model=(DefaultTableModel)tblShowtimes.getModel(); model.setRowCount(0); for(Showtime s:showtimeManager.getShowtimes()) model.addRow(new Object[]{s.getShowtimeId(),s.getMovieId(),s.getMovieTitle(),s.getShowDate(),s.getShowTime(),s.getHallNumber(),moneyFormat.format(s.getTicketPrice())}); }
    private void fillBookingTable(){ DefaultTableModel model=(DefaultTableModel)tblBookings.getModel(); model.setRowCount(0); for(Booking b:bookingManager.getBookings()) model.addRow(new Object[]{b.getBookingId(),b.getCustomerName(),b.getPhoneNumber(),b.getMovieTitle(),b.getShowDate(),b.getShowTime(),b.getHallNumber(),b.getSeatNumber(),moneyFormat.format(b.getTicketPrice()),b.getBookingStatus()}); }

    private void clearMovieFields(){ txtMovieId.setText(""); txtMovieTitle.setText(""); txtDuration.setText(""); txtLanguage.setText(""); txtRating.setText(""); }
    private void clearShowtimeFields(){ txtShowtimeId.setText(""); txtShowDate.setText(""); txtShowTime.setText(""); txtHallNumber.setText(""); txtTicketPrice.setText(""); }
    private void clearBookingFields(){ txtBookingId.setText(""); txtCustomerName.setText(""); txtPhone.setText(""); }
    private void msg(String m){ JOptionPane.showMessageDialog(this,m); }
}
