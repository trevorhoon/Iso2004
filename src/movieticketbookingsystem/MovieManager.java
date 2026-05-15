package movieticketbookingsystem;

import java.io.*;
import java.util.*;

public class MovieManager {
    private final List<Movie> movies = new ArrayList<>();
    private final String filePath;

    public MovieManager(String filePath) {
        this.filePath = filePath;
        loadMovies();
    }

    public List<Movie> getMovies() { return movies; }

    public boolean addMovie(Movie movie) {
        if (findById(movie.getMovieId()) != null) return false;
        movies.add(movie);
        saveMovies();
        return true;
    }

    public boolean updateMovie(Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getMovieId().equalsIgnoreCase(updatedMovie.getMovieId())) {
                movies.set(i, updatedMovie);
                saveMovies();
                return true;
            }
        }
        return false;
    }

    public boolean deleteMovie(String movieId) {
        boolean removed = movies.removeIf(m -> m.getMovieId().equalsIgnoreCase(movieId));
        if (removed) saveMovies();
        return removed;
    }

    public Movie findById(String movieId) {
        for (Movie m : movies) if (m.getMovieId().equalsIgnoreCase(movieId)) return m;
        return null;
    }

    public List<Movie> searchByIdOrTitle(String keyword) {
        List<Movie> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Movie m : movies) {
            if (m.getMovieId().toLowerCase().contains(k) || m.getTitle().toLowerCase().contains(k)) result.add(m);
        }
        return result;
    }

    public final void loadMovies() {
        movies.clear();
        File file = new File(filePath);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length == 6) movies.add(new Movie(p[0], p[1], p[2], p[3], p[4], p[5]));
            }
        } catch (IOException ignored) {}
    }

    public void saveMovies() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Movie m : movies) {
                writer.write(m.toFileString());
                writer.newLine();
            }
        } catch (IOException ignored) {}
    }
}
