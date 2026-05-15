package movieticketbookingsystem;

import java.io.*;
import java.util.*;

public class ShowtimeManager {
    private final List<Showtime> showtimes = new ArrayList<>();
    private final String filePath;

    public ShowtimeManager(String filePath) {
        this.filePath = filePath;
        loadShowtimes();
    }

    public List<Showtime> getShowtimes() { return showtimes; }

    public boolean addShowtime(Showtime showtime) {
        if (findById(showtime.getShowtimeId()) != null) return false;
        showtimes.add(showtime);
        saveShowtimes();
        return true;
    }

    public boolean updateShowtime(Showtime updated) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getShowtimeId().equalsIgnoreCase(updated.getShowtimeId())) {
                showtimes.set(i, updated);
                saveShowtimes();
                return true;
            }
        }
        return false;
    }

    public boolean deleteShowtime(String id) {
        boolean removed = showtimes.removeIf(s -> s.getShowtimeId().equalsIgnoreCase(id));
        if (removed) saveShowtimes();
        return removed;
    }

    public Showtime findById(String id) {
        for (Showtime s : showtimes) if (s.getShowtimeId().equalsIgnoreCase(id)) return s;
        return null;
    }

    public final void loadShowtimes() {
        showtimes.clear();
        File file = new File(filePath);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length == 7) {
                    showtimes.add(new Showtime(p[0], p[1], p[2], p[3], p[4], p[5], Double.parseDouble(p[6])));
                }
            }
        } catch (IOException | NumberFormatException ignored) {}
    }

    public void saveShowtimes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Showtime s : showtimes) {
                writer.write(s.toFileString());
                writer.newLine();
            }
        } catch (IOException ignored) {}
    }
}
