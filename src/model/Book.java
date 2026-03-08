package model;

public class Book {

    private int id;
    private String title;
    private String author;
    private String status;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.status = "Available";
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }
}