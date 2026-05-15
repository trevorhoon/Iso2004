package movieticketbookingsystem;

public class Movie {
    private String movieId;
    private String title;
    private String genre;
    private String duration;
    private String language;
    private String rating;

    public Movie(String movieId, String title, String genre, String duration, String language, String rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.rating = rating;
    }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    @Override
    public String toString() {
        return movieId + " - " + title;
    }

    public String toFileString() {
        return movieId + "|" + title + "|" + genre + "|" + duration + "|" + language + "|" + rating;
    }
}
