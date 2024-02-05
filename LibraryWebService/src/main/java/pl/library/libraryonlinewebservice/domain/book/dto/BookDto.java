package pl.library.libraryonlinewebservice.domain.book.dto;

public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer release_year;
    private Integer pages;
    private String description;
    private String img;
    private String genre;
    private boolean promoted;
    private double avgRating;
    private int ratingCount;


    public BookDto(Long id,
                   String title,
                   String author,
                   String publisher,
                   Integer release_year,
                   Integer pages,
                   String description,
                   String img,
                   String genre,
                   boolean promoted,
                   Double avgRating,
                   int ratingCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_year = release_year;
        this.pages = pages;
        this.description = description;
        this.img = img;
        this.genre = genre;
        this.promoted = promoted;
        this.avgRating = avgRating;
        this.ratingCount = ratingCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
